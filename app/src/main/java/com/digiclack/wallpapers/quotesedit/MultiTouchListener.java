package com.digiclack.wallpapers.quotesedit;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;

import com.digiclack.wallpapers.quotesedit.ScaleGestureDetector.SimpleOnScaleGestureListener;

public class MultiTouchListener implements OnTouchListener {
    private static final int INVALID_POINTER_ID = -1;
    Bitmap bitmap = null;
    boolean bt = false;
    boolean checkstickerWH = false;
    private boolean disContinueHandleTransparecy = true;
    GestureDetector gd = null;
    public boolean isRotateEnabled = true;
    public boolean isRotationEnabled = false;
    public boolean isScaleEnabled = true;
    public boolean isTranslateEnabled = true;
    public boolean isZoomEnabled = false;
    private TouchCallbackListener listener = null;
    private int mActivePointerId = INVALID_POINTER_ID;
    private float mPrevX;
    private float mPrevY;
    private ScaleGestureDetector mScaleGestureDetector = new ScaleGestureDetector(new ScaleGestureListener());
    public float maximumScale = 8.0f;
    public float minimumScale = 0.5f;
    private TouchMoveListener tmListener = null;

    public interface TouchCallbackListener {
        void onTouchCallback(View view);

        void onTouchMoveCallback(View view);

        void onTouchUpCallback(View view);
    }

    private class ScaleGestureListener extends SimpleOnScaleGestureListener {
        private float mPivotX;
        private float mPivotY;
        private Vector2D mPrevSpanVector;

        private ScaleGestureListener() {
            this.mPrevSpanVector = new Vector2D();
        }

        public boolean onScaleBegin(View view, ScaleGestureDetector detector) {
            this.mPivotX = detector.getFocusX();
            this.mPivotY = detector.getFocusY();
            this.mPrevSpanVector.set(detector.getCurrentSpanVector());
            return true;
        }

        public boolean onScale(View view, ScaleGestureDetector detector) {
            float angle;
            float f = 0.0f;
            TransformInfo info = new TransformInfo();
            info.deltaScale = MultiTouchListener.this.isScaleEnabled ? detector.getScaleFactor() : 1.0f;
            if (MultiTouchListener.this.isRotateEnabled) {
                angle = Vector2D.getAngle(this.mPrevSpanVector, detector.getCurrentSpanVector());
            } else {
                angle = 0.0f;
            }
            info.deltaAngle = angle;
            if (MultiTouchListener.this.isTranslateEnabled) {
                angle = detector.getFocusX() - this.mPivotX;
            } else {
                angle = 0.0f;
            }
            info.deltaX = angle;
            if (MultiTouchListener.this.isTranslateEnabled) {
                f = detector.getFocusY() - this.mPivotY;
            }
            info.deltaY = f;
            info.pivotX = this.mPivotX;
            info.pivotY = this.mPivotY;
            info.minimumScale = MultiTouchListener.this.minimumScale;
            info.maximumScale = MultiTouchListener.this.maximumScale;
            MultiTouchListener.this.move(view, info);
            return false;
        }
    }

    public interface TouchMoveListener {
        boolean onTouchDown(View view, MotionEvent motionEvent);

        void onTouchMove(View view, MotionEvent motionEvent);
    }

    private class TransformInfo {
        public float deltaAngle;
        public float deltaScale;
        public float deltaX;
        public float deltaY;
        public float maximumScale;
        public float minimumScale;
        public float pivotX;
        public float pivotY;

        private TransformInfo() {
        }
    }

    public MultiTouchListener setGestureListener(GestureDetector gd) {
        this.gd = gd;
        return this;
    }

    public MultiTouchListener setOnTouchCallbackListener(TouchCallbackListener l) {
        this.listener = l;
        return this;
    }

    public MultiTouchListener setOnTouchMoveListener(TouchMoveListener l) {
        this.tmListener = l;
        return this;
    }

    public MultiTouchListener enableRotation(boolean b) {
        this.isRotationEnabled = b;
        return this;
    }

    public MultiTouchListener enableZoom(boolean b) {
        this.isZoomEnabled = b;
        return this;
    }

    public MultiTouchListener setMinScale(float f) {
        this.minimumScale = f;
        return this;
    }

    private static float adjustAngle(float degrees) {
        if (degrees > 180.0f) {
            return degrees - 360.0f;
        }
        if (degrees < -180.0f) {
            return degrees + 360.0f;
        }
        return degrees;
    }

    private void move(View view, TransformInfo info) {
        if (this.isZoomEnabled) {
            computeRenderOffset(view, info.pivotX, info.pivotY);
            adjustTranslation(view, info.deltaX, info.deltaY);
            float scale = Math.max(info.minimumScale, Math.min(info.maximumScale, view.getScaleX() * info.deltaScale));
            view.setScaleX(scale);
            view.setScaleY(scale);
        }
        if (this.isRotationEnabled) {
            float rotation = adjustAngle(view.getRotation() + info.deltaAngle);
            Log.i("testing", "Rotation : " + rotation);
            view.setRotation(rotation);
        }
    }

    private static void adjustTranslation(View view, float deltaX, float deltaY) {
        float[] deltaVector = new float[]{deltaX, deltaY};
        view.getMatrix().mapVectors(deltaVector);
        view.setTranslationX(view.getTranslationX() + deltaVector[0]);
        view.setTranslationY(view.getTranslationY() + deltaVector[1]);
    }

    private static void computeRenderOffset(View view, float pivotX, float pivotY) {
        if (view.getPivotX() != pivotX || view.getPivotY() != pivotY) {
            float[] prevPoint = new float[]{0.0f, 0.0f};
            view.getMatrix().mapPoints(prevPoint);
            view.setPivotX(pivotX);
            view.setPivotY(pivotY);
            float[] currPoint = new float[]{0.0f, 0.0f};
            view.getMatrix().mapPoints(currPoint);
            float offsetY = currPoint[1] - prevPoint[1];
            view.setTranslationX(view.getTranslationX() - (currPoint[0] - prevPoint[0]));
            view.setTranslationY(view.getTranslationY() - offsetY);
        }
    }

    public boolean handleTransparency(View view, MotionEvent event) {
        try {
            Log.i("MOVE_TESTs", "touch test: " + view.getWidth() + " / " + ((ResizableImageview) view).getMainWidth());
            boolean isSmaller = ((float) view.getWidth()) < ((ResizableImageview) view).getMainWidth() && ((float) view.getHeight()) < ((ResizableImageview) view).getMainHeight();
            if (isSmaller && ((ResizableImageview) view).getBorderVisbilty()) {
                return false;
            }
            if (!(event.getAction() != 0 || isSmaller || this.listener == null)) {
                this.listener.onTouchMoveCallback(view);
            }
            if (event.getAction() == 2 && this.bt) {
                return true;
            }
            if (event.getAction() == 1 && this.bt) {
                this.bt = false;
                if (this.bitmap != null) {
                    this.bitmap.recycle();
                }
                return true;
            }
            int[] posXY = new int[2];
            view.getLocationOnScreen(posXY);
            int rx = (int) (event.getRawX() - ((float) posXY[0]));
            int ry = (int) (event.getRawY() - ((float) posXY[1]));
            float r = view.getRotation();
            Matrix mat = new Matrix();
            mat.postRotate(-r);
            float[] point = new float[]{(float) rx, (float) ry};
            mat.mapPoints(point);
            rx = (int) point[0];
            ry = (int) point[1];
            if (event.getAction() == 0) {
                this.bt = false;
                boolean borderVisbilty = ((ResizableImageview) view).getBorderVisbilty();
                if (borderVisbilty) {
                    ((ResizableImageview) view).setBorderVisibility(false);
                }
                this.bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Config.ARGB_8888);
                view.draw(new Canvas(this.bitmap));
                if (borderVisbilty) {
                    ((ResizableImageview) view).setBorderVisibility(true);
                }
                rx = (int) (((float) rx) * (((float) this.bitmap.getWidth()) / (((float) this.bitmap.getWidth()) * view.getScaleX())));
                ry = (int) (((float) ry) * (((float) this.bitmap.getHeight()) / (((float) this.bitmap.getHeight()) * view.getScaleX())));
            }
            if (rx < 0 || ry < 0 || rx > this.bitmap.getWidth() || ry > this.bitmap.getHeight()) {
                return false;
            }
            boolean b = this.bitmap.getPixel(rx, ry) == 0;
            if (event.getAction() != 0) {
                return b;
            }
            this.bt = b;
            if (!b || isSmaller) {
                return b;
            }
            ((ResizableImageview) view).setBorderVisibility(false);
            return b;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean onTouch(View view, MotionEvent event) {
        this.mScaleGestureDetector.onTouchEvent(view, event);
        RelativeLayout rl = (RelativeLayout) view.getParent();

        Log.e("aaa","aaa");
        if ((view instanceof ResizableImageview) && !((ResizableImageview) view).getBorderVisbilty() && this.disContinueHandleTransparecy) {

            Log.e("bbbb","bbbb1");





            this.disContinueHandleTransparecy = false;
        }
        if (!this.isTranslateEnabled) {
            Log.e("bbbb","bbbb3");
            return true;
        }
        int action = event.getAction();
        int pointerIndex;
        switch (event.getActionMasked() & action) {
            case 0 :

                Log.e("aaa","aaa0");
                if (rl != null) {
                    rl.requestDisallowInterceptTouchEvent(true);
                }
                if (this.listener != null) {
                    this.listener.onTouchCallback(view);
                }
                view.bringToFront();
                if (view instanceof ResizableImageview) {
                    ((ResizableImageview) view).setBorderVisibility(true);
                }
                this.mPrevX = event.getX();
                this.mPrevY = event.getY();
                this.mActivePointerId = event.getPointerId(0);
                break;
            case 1 :

                Log.e("aaa","aaa1");
                this.mActivePointerId = INVALID_POINTER_ID;
                this.disContinueHandleTransparecy = true;
                if (this.listener != null) {
                    this.listener.onTouchUpCallback(view);
                }
                float rotation = view.getRotation();
                if (Math.abs(90.0f - Math.abs(rotation)) <= 5.0f) {
                    if (rotation > 0.0f) {
                        rotation = 90.0f;
                    } else {
                        rotation = -90.0f;
                    }
                }
                if (Math.abs(0.0f - Math.abs(rotation)) <= 5.0f) {
                    if (rotation > 0.0f) {
                        rotation = 0.0f;
                    } else {
                        rotation = -0.0f;
                    }
                }
                if (Math.abs(180.0f - Math.abs(rotation)) <= 5.0f) {
                    if (rotation > 0.0f) {
                        rotation = 180.0f;
                    } else {
                        rotation = -180.0f;
                    }
                }
                view.setRotation(rotation);
                Log.i("testing", "Final Rotation : " + rotation);
                break;
            case 2 :
                Log.e("aaa","aaa2");

                if (rl != null) {
                    rl.requestDisallowInterceptTouchEvent(true);
                }
                pointerIndex = event.findPointerIndex(this.mActivePointerId);
                if (pointerIndex != INVALID_POINTER_ID) {
                    float currX = event.getX(pointerIndex);
                    float currY = event.getY(pointerIndex);
                    if (!this.mScaleGestureDetector.isInProgress()) {
                        adjustTranslation(view, currX - this.mPrevX, currY - this.mPrevY);
                        break;
                    }
                }
                break;
            case 3 :
                Log.e("aaa","aaa3");

                this.mActivePointerId = INVALID_POINTER_ID;
                break;
            case 6 :
                Log.e("aaa","aaa6");

                pointerIndex = (65280 & action) >> 8;
                if (event.getPointerId(pointerIndex) == this.mActivePointerId) {
                    int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    this.mPrevX = event.getX(newPointerIndex);
                    this.mPrevY = event.getY(newPointerIndex);
                    this.mActivePointerId = event.getPointerId(newPointerIndex);
                    break;
                }
                break;
        }
        return true;
    }
}
