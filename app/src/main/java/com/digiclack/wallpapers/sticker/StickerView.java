package com.digiclack.wallpapers.sticker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.digiclack.wallpapers.R;

import yuku.ambilwarna.BuildConfig;

public abstract class StickerView extends FrameLayout {
    private static final int BUTTON_SIZE_DP = 30;
    private static final int SELF_SIZE_DP = 100;
    public static final String TAG = "com.knef.stickerView";
    private double centerX;
    private double centerY;
    private BorderView iv_border;
    private ImageView iv_delete;
    private ImageView iv_move;
    private ImageView iv_rotate;
    private ImageView iv_scale;
    private OnTouchListener mTouchListener = new OnTouchListener() {
        public boolean onTouch(View view, MotionEvent event) {
            StickerView.this.bringToFront();
            if (!view.getTag().equals("DraggableViewGroup")) {
                if (!view.getTag().equals("iv_move")) {
                    if (!view.getTag().equals("iv_rotate")) {
                        if (view.getTag().equals("iv_scale")) {
                            switch (event.getAction()) {
                                case 0 :
                                    StickerView.this.move_orgX = event.getRawX();
                                    StickerView.this.rlX = ((View) view.getParent()).getX();
                                    break;
                                case 2 :
                                    float offsetX = event.getRawX() - StickerView.this.move_orgX;
                                    ((View) view.getParent()).setX(StickerView.this.rlX);
                                    ((View) view.getParent()).getLayoutParams().width = (int) (((float) ((View) view.getParent()).getLayoutParams().width) + offsetX);
                                    ((View) view.getParent()).getLayoutParams().height = (int) (((float) ((View) view.getParent()).getLayoutParams().height) + offsetX);
                                    ((View) view.getParent()).postInvalidate();
                                    ((View) view.getParent()).requestLayout();
                                    StickerView.this.move_orgX = event.getRawX();
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                    switch (event.getAction()) {
                        case 0 :
                            StickerView.this.centerX = (double) ((((float) ((View) view.getParent()).getWidth()) / 2.0f) + (((View) view.getParent()).getX() + ((View) ((View) view.getParent()).getParent()).getX()));
                            int result = 0;
                            int resourceId = StickerView.this.getResources().getIdentifier("status_bar_height", "dimen", "android");
                            if (resourceId > 0) {
                                result = StickerView.this.getResources().getDimensionPixelSize(resourceId);
                            }
                            double statusBarHeight = (double) result;
                            StickerView.this.centerY = (((double) (((View) ((View) view.getParent()).getParent()).getY() + ((View) view.getParent()).getY())) + statusBarHeight) + ((double) (((float) ((View) view.getParent()).getHeight()) / 2.0f));
                            break;
                        case 2 :
                            ((View) view.getParent()).setRotation(((float) ((Math.atan2(((double) event.getRawY()) - StickerView.this.centerY, ((double) event.getRawX()) - StickerView.this.centerX) * 180.0d) / 3.141592653589793d)) - 180.0f);
                            ((View) view.getParent()).postInvalidate();
                            ((View) view.getParent()).requestLayout();
                            break;
                        default:
                            break;
                    }
                }
                switch (event.getAction()) {
                    case 0 :
                        Log.v(StickerView.TAG, "sticker view action down");
                        StickerView.this.move_orgX = event.getRawX();
                        StickerView.this.move_orgY = event.getRawY();
                        break;
                    case 1 :
                        Log.v(StickerView.TAG, "sticker view action up");
                        break;
                    case 2 :
                        Log.v(StickerView.TAG, "sticker view action move");
                        float offsetY = event.getRawY() - StickerView.this.move_orgY;
                        StickerView.this.setX(StickerView.this.getX() + (event.getRawX() - StickerView.this.move_orgX));
                        StickerView.this.setY(StickerView.this.getY() + offsetY);
                        StickerView.this.move_orgX = event.getRawX();
                        StickerView.this.move_orgY = event.getRawY();
                        break;
                    default:
                        break;
                }
            }
            switch (event.getAction()) {
                case 0 :
                    Log.v(StickerView.TAG, "sticker view action down");
                    StickerView.this.move_orgX = event.getRawX();
                    StickerView.this.move_orgY = event.getRawY();
                    break;
                case 1 :
                    Log.v(StickerView.TAG, "sticker view action up");
                    break;
                case 2 :
                    Log.v(StickerView.TAG, "sticker view action move");
                    Float offsetY = event.getRawY() - StickerView.this.move_orgY;
                    StickerView.this.setX(StickerView.this.getX() + (event.getRawX() - StickerView.this.move_orgX));
                    StickerView.this.setY(StickerView.this.getY() + offsetY);
                    StickerView.this.move_orgX = event.getRawX();
                    StickerView.this.move_orgY = event.getRawY();
                    break;
            }
            return true;
        }
    };
    private float move_orgX = -1.0f;
    private float move_orgY = -1.0f;
    float rlX;
    private float rotate_newX = -1.0f;
    private float rotate_newY = -1.0f;
    private float rotate_orgX = -1.0f;
    private float rotate_orgY = -1.0f;
    private double scale_orgHeight = -1.0d;
    private double scale_orgWidth = -1.0d;
    private float scale_orgX = -1.0f;
    private float scale_orgY = -1.0f;
    private float this_orgX = -1.0f;
    private float this_orgY = -1.0f;

    private class BorderView extends View {
        public BorderView(Context context) {
            super(context);
        }

        public BorderView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public BorderView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }

        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            LayoutParams params = (LayoutParams) getLayoutParams();
            Drawable d = getResources().getDrawable(com.digiclack.wallpapers.R.drawable.border);
            d.setBounds(getLeft() - params.leftMargin, getTop() - params.topMargin, getRight() - params.rightMargin, getBottom() - params.bottomMargin);
            d.draw(canvas);
        }
    }

    protected abstract View getMainView();

    public StickerView(Context context) {
        super(context);
        init(context);
    }

    public StickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public StickerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        this.iv_border = new BorderView(context);
        this.iv_scale = new ImageView(context);
        this.iv_move = new ImageView(context);
        this.iv_delete = new ImageView(context);
        this.iv_rotate = new ImageView(context);
        this.iv_scale.setImageResource(R.drawable.scale);
        this.iv_move.setImageResource(R.drawable.move);
        this.iv_delete.setImageResource(R.drawable.remove);
        this.iv_rotate.setImageResource(R.drawable.rotate);
        setTag("DraggableViewGroup");
        this.iv_border.setTag("iv_border");
        this.iv_scale.setTag("iv_scale");
        this.iv_move.setTag("iv_move");
        this.iv_delete.setTag("iv_delete");
        this.iv_rotate.setTag("iv_rotate");
        int size = convertDpToPixel(100.0f, getContext()) + 200;
        Log.e("margin is", BuildConfig.FLAVOR + size);
        LayoutParams this_params = new LayoutParams(size, size);
        this_params.gravity = 17;
        LayoutParams iv_main_params = new LayoutParams(-1, -1);
        iv_main_params.setMargins(BUTTON_SIZE_DP, BUTTON_SIZE_DP, BUTTON_SIZE_DP, BUTTON_SIZE_DP);
        LayoutParams iv_border_params = new LayoutParams(-1, -1);
        iv_border_params.setMargins(0, 0, 0, 0);
        LayoutParams iv_scale_params = new LayoutParams(convertDpToPixel(30.0f, getContext()), convertDpToPixel(30.0f, getContext()));
        iv_scale_params.gravity = 85;
        iv_scale_params.setMargins(20, 20, 20, 20);
        LayoutParams iv_move_params = new LayoutParams(convertDpToPixel(30.0f, getContext()), convertDpToPixel(30.0f, getContext()));
        iv_move_params.gravity = 83;
        iv_move_params.setMargins(20, 20, 20, 20);
        LayoutParams iv_delete_params = new LayoutParams(convertDpToPixel(30.0f, getContext()), convertDpToPixel(30.0f, getContext()));
        iv_delete_params.gravity = 53;
        iv_delete_params.setMargins(20, 20, 20, 20);
        LayoutParams iv_flip_params = new LayoutParams(convertDpToPixel(30.0f, getContext()), convertDpToPixel(30.0f, getContext()));
        iv_flip_params.gravity = 51;
        iv_flip_params.setMargins(20, 20, 20, 20);
        setLayoutParams(this_params);
        addView(getMainView(), iv_main_params);
        addView(this.iv_border, iv_border_params);
        addView(this.iv_scale, iv_scale_params);
        addView(this.iv_move, iv_move_params);
        addView(this.iv_delete, iv_delete_params);
        addView(this.iv_rotate, iv_flip_params);
        this.iv_move.setOnTouchListener(this.mTouchListener);
        this.iv_rotate.setOnTouchListener(this.mTouchListener);
        this.iv_scale.setOnTouchListener(this.mTouchListener);
        this.iv_delete.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (StickerView.this.getParent() != null) {
                    ((ViewGroup) StickerView.this.getParent()).removeView(StickerView.this);
                }
            }
        });
        this.iv_rotate.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                float f = -180.0f;
                Log.v(StickerView.TAG, "flip the view");
                StickerView.this.bringToFront();
                View mainView = StickerView.this.getMainView();
                if (mainView.getRotationY() == -180.0f) {
                    f = 0.0f;
                }
                mainView.setRotationY(f);
                mainView.invalidate();
                StickerView.this.requestLayout();
            }
        });
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private double getLength(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(y2 - y1, 2.0d) + Math.pow(x2 - x1, 2.0d));
    }

    private float[] getRelativePos(float absX, float absY) {
        return new float[]{absX - ((View) getParent()).getX(), absY - ((View) getParent()).getY()};
    }

    public void setControlItemsHidden(boolean isHidden) {
        if (isHidden) {
            this.iv_border.setVisibility(View.INVISIBLE);
            this.iv_scale.setVisibility(View.INVISIBLE);
            this.iv_move.setVisibility(View.INVISIBLE);
            this.iv_delete.setVisibility(View.INVISIBLE);
            this.iv_rotate.setVisibility(View.INVISIBLE);
            return;
        }
        this.iv_border.setVisibility(View.VISIBLE);
        this.iv_scale.setVisibility(View.VISIBLE);
        this.iv_move.setVisibility(View.VISIBLE);
        this.iv_delete.setVisibility(View.VISIBLE);
        this.iv_rotate.setVisibility(View.VISIBLE);
    }

    protected void onScaling(boolean scaleUp) {
    }

    protected void onRotating() {
    }

    private static int convertDpToPixel(float dp, Context context) {
        return (int) (dp * (((float) context.getResources().getDisplayMetrics().densityDpi) / 160.0f));
    }
}
