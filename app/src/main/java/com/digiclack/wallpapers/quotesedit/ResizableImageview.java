package com.digiclack.wallpapers.quotesedit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

import com.digiclack.wallpapers.R;
import com.digiclack.wallpapers.activity.AddTextQuotesActivity;

import yuku.ambilwarna.BuildConfig;

public class ResizableImageview extends RelativeLayout {
    private static final int SELF_SIZE_DP = 30;
    public static final String TAG = "ResizableImageview";
    int alphaProg = 255;
    double angle = 0.0d;
    int baseh;
    int basew;
    int basex;
    int basey;
    private ImageView border_iv;
    float cX = 0.0f;
    float cY = 0.0f;
    private double centerX;
    private double centerY;
    private Context context;
    double dAngle = 0.0d;
    private ImageView delete_iv;
    private String drawableId;
    private ImageView flip_iv;
    private int he;
    float heightMain;
    int hueProg = 1;
    private boolean isBorderVisible = false;
    private boolean isColorFilterEnable = false;
    public boolean isMultiTouchEnabled = true;
    private boolean isSticker = true;
    private TouchEventListener listener = null;
    private OnTouchListener mTouchListener = new OnTouchListener() {
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case 0 :
                    Rect rect = new Rect();
                    ((View) view.getParent()).getGlobalVisibleRect(rect);
                    ResizableImageview.this.cX = rect.exactCenterX();
                    ResizableImageview.this.cY = rect.exactCenterY();
                    ResizableImageview.this.vAngle = (double) ((View) view.getParent()).getRotation();
                    ResizableImageview.this.tAngle = (Math.atan2((double) (ResizableImageview.this.cY - event.getRawY()), (double) (ResizableImageview.this.cX - event.getRawX())) * 180.0d) / 3.141592653589793d;
                    ResizableImageview.this.dAngle = ResizableImageview.this.vAngle - ResizableImageview.this.tAngle;
                    break;
                case 2 :
                    ResizableImageview.this.angle = (Math.atan2((double) (ResizableImageview.this.cY - event.getRawY()), (double) (ResizableImageview.this.cX - event.getRawX())) * 180.0d) / 3.141592653589793d;
                    float rotation = (float) (ResizableImageview.this.angle + ResizableImageview.this.dAngle);
                    if (rotation > -5.0f && rotation < 5.0f) {
                        rotation = 0.0f;
                    }
                    Log.i("rotation", BuildConfig.FLAVOR + (ResizableImageview.this.angle + ResizableImageview.this.dAngle));
                    ((View) view.getParent()).setRotation(rotation);
                    break;
            }
            return true;
        }
    };
    private OnTouchListener mTouchListener1 = new OnTouchListener() {
        @SuppressLint({"NewApi"})
        public boolean onTouch(View view, MotionEvent event) {
            ResizableImageview rl = (ResizableImageview) view.getParent();
            int j = (int) event.getRawX();
            int i = (int) event.getRawY();
            LayoutParams layoutParams = (LayoutParams) ResizableImageview.this.getLayoutParams();
            switch (event.getAction()) {
                case 0 :
                    if (rl != null) {
                        rl.requestDisallowInterceptTouchEvent(true);
                    }
                    ResizableImageview.this.invalidate();
                    ResizableImageview.this.basex = j;
                    ResizableImageview.this.basey = i;
                    ResizableImageview.this.basew = ResizableImageview.this.getWidth();
                    ResizableImageview.this.baseh = ResizableImageview.this.getHeight();
                    ResizableImageview.this.getLocationOnScreen(new int[2]);
                    ResizableImageview.this.margl = layoutParams.leftMargin;
                    ResizableImageview.this.margt = layoutParams.topMargin;
                    break;
                case 1 :
                    ResizableImageview.this.wi = ResizableImageview.this.getLayoutParams().width;
                    ResizableImageview.this.he = ResizableImageview.this.getLayoutParams().height;
                    ResizableImageview.this.margl = ((LayoutParams) ResizableImageview.this.getLayoutParams()).leftMargin;
                    ResizableImageview.this.margt = ((LayoutParams) ResizableImageview.this.getLayoutParams()).topMargin;
                    break;
                case 2 :
                    if (rl != null) {
                        rl.requestDisallowInterceptTouchEvent(true);
                    }
                    float f2 = (float) Math.toDegrees(Math.atan2((double) (i - ResizableImageview.this.basey), (double) (j - ResizableImageview.this.basex)));
                    float f1 = f2;
                    if (f2 < 0.0f) {
                        f1 = f2 + 360.0f;
                    }
                    j -= ResizableImageview.this.basex;
                    int k = i - ResizableImageview.this.basey;
                    i = (int) (Math.sqrt((double) ((j * j) + (k * k))) * Math.cos(Math.toRadians((double) (f1 - ResizableImageview.this.getRotation()))));
                    j = (int) (Math.sqrt((double) ((i * i) + (k * k))) * Math.sin(Math.toRadians((double) (f1 - ResizableImageview.this.getRotation()))));
                    k = (i * 2) + ResizableImageview.this.basew;
                    int m = (j * 2) + ResizableImageview.this.baseh;
                    if (k > ResizableImageview.this.s) {
                        layoutParams.width = k;
                        layoutParams.leftMargin = ResizableImageview.this.margl - i;
                    }
                    if (m > ResizableImageview.this.s) {
                        layoutParams.height = m;
                        layoutParams.topMargin = ResizableImageview.this.margt - j;
                    }
                    ResizableImageview.this.setLayoutParams(layoutParams);
                    ResizableImageview.this.performLongClick();
                    break;
            }
            return true;
        }
    };
    private OnTouchListener mTouchListener2 = new OnTouchListener() {
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case 0 :
                    ResizableImageview.this.scale_orgX = event.getRawX();
                    ResizableImageview.this.scale_orgY = event.getRawY();
                    ResizableImageview.this.this_orgX = ((View) view.getParent()).getX();
                    ResizableImageview.this.this_orgY = ((View) view.getParent()).getY();
                    break;
                case 1 :
                    ResizableImageview.this.wi = ResizableImageview.this.getLayoutParams().width;
                    ResizableImageview.this.he = ResizableImageview.this.getLayoutParams().height;
                    break;
                case 2 :
                    float offsetX = event.getRawX() - ResizableImageview.this.scale_orgX;
                    float offsetY = event.getRawY() - ResizableImageview.this.scale_orgY;
                    ((View) view.getParent()).setX(ResizableImageview.this.this_orgX);
                    ((View) view.getParent()).setY(ResizableImageview.this.this_orgY);
                    ((View) view.getParent()).getLayoutParams().width = (int) (((float) ((View) view.getParent()).getLayoutParams().width) + offsetX);
                    ((View) view.getParent()).getLayoutParams().height = (int) (((float) ((View) view.getParent()).getLayoutParams().height) + offsetY);
                    ((View) view.getParent()).postInvalidate();
                    ((View) view.getParent()).requestLayout();
                    ResizableImageview.this.scale_orgX = event.getRawX();
                    ResizableImageview.this.scale_orgY = event.getRawY();
                    break;
            }
            return true;
        }
    };
    public ImageView main_iv;
    int margl;
    int margt;
    private ImageView move_iv;
    private float rotation;
    private int s;
    private ImageView scale_iv;
    private float scale_orgX = -1.0f;
    private float scale_orgY = -1.0f;
    private String stickerType;
    double tAngle = 0.0d;
    private float this_orgX = -1.0f;
    private float this_orgY = -1.0f;
    double vAngle = 0.0d;
    float view_width;
    private int wi;
    float widthMain;
    private float yRotation;
    Animation zoomInScale;
    Animation zoomOutScale;

    public interface TouchEventListener {
        void onDelete(View view);
    }

    public ResizableImageview setOnTouchCallbackListener(TouchEventListener l) {
        this.listener = l;
        return this;
    }

    public ResizableImageview(Context context) {
        super(context);
        init(context);
    }

    public ResizableImageview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ResizableImageview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setMainLayoutWH(float wMLay, float hMLay) {
        this.widthMain = wMLay;
        this.heightMain = hMLay;
    }

    public float getMainWidth() {
        return this.widthMain;
    }

    public float getMainHeight() {
        return this.heightMain;
    }

    public void init(Context ctx) {
        this.context = ctx;
        this.main_iv = new ImageView(this.context);
        this.scale_iv = new ImageView(this.context);
        this.border_iv = new ImageView(this.context);
        this.flip_iv = new ImageView(this.context);
        this.delete_iv = new ImageView(this.context);
        this.move_iv = new ImageView(this.context);
        this.s = dpToPx(this.context, 25);
        this.wi = dpToPx(this.context, 200);
        this.he = dpToPx(this.context, 200);
        this.view_width = (float) dpToPx(this.context, 25);
        this.scale_iv.setImageResource(R.drawable.scale);
        this.border_iv.setImageResource(R.drawable.border);
        this.flip_iv.setImageResource(R.drawable.flip);
        this.delete_iv.setImageResource(R.drawable.remove);
        this.move_iv.setImageResource(R.drawable.rotate);
        LayoutParams lp = new LayoutParams(this.wi, this.he);
        LayoutParams mlp = new LayoutParams(-1, -1);
        mlp.setMargins(10, 10, 10, 10);
        mlp.addRule(17);
        LayoutParams slp = new LayoutParams(this.s, this.s);
        slp.addRule(12);
        slp.addRule(11);
        slp.setMargins(10, 10, 10, 10);
        LayoutParams flp = new LayoutParams(this.s, this.s);
        flp.addRule(10);
        flp.addRule(9);
        flp.setMargins(10, 10, 10, 10);
        LayoutParams dlp = new LayoutParams(this.s, this.s);
        dlp.addRule(10);
        dlp.addRule(11);
        dlp.setMargins(10, 10, 10, 10);
        LayoutParams mlp1 = new LayoutParams(this.s, this.s);
        mlp1.addRule(12);
        mlp1.addRule(9);
        mlp1.setMargins(10, 10, 10, 10);
        LayoutParams blp = new LayoutParams(-1, -1);
        setLayoutParams(lp);
        setBackgroundResource(R.drawable.border_gray);
        addView(this.main_iv);
        this.main_iv.setLayoutParams(mlp);
        this.main_iv.setTag("main_iv");
        addView(this.border_iv);
        this.border_iv.setLayoutParams(blp);
        this.border_iv.setScaleType(ScaleType.FIT_XY);
        this.border_iv.setTag("border_iv");
        addView(this.flip_iv);
        this.flip_iv.setLayoutParams(flp);
        this.flip_iv.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                float f = -180.0f;
                ImageView imageView = ResizableImageview.this.main_iv;
                if (ResizableImageview.this.main_iv.getRotationY() == -180.0f) {
                    f = 0.0f;
                }
                imageView.setRotationY(f);
                ResizableImageview.this.main_iv.invalidate();
                ResizableImageview.this.requestLayout();
            }
        });
        addView(this.delete_iv);
        this.delete_iv.setLayoutParams(dlp);
        this.delete_iv.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                final ViewGroup parent = (ViewGroup) ResizableImageview.this.getParent();
                ResizableImageview.this.zoomInScale.setAnimationListener(new AnimationListener() {
                    public void onAnimationStart(Animation animation) {
                    }

                    public void onAnimationEnd(Animation animation) {
                        parent.removeView(ResizableImageview.this);
                    }

                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                ResizableImageview.this.main_iv.startAnimation(ResizableImageview.this.zoomInScale);
                ResizableImageview.this.setBorderVisibility(false);
                if (ResizableImageview.this.listener != null) {
                    ResizableImageview.this.listener.onDelete(ResizableImageview.this);
                }
            }
        });
        addView(this.move_iv);
        this.move_iv.setLayoutParams(mlp1);
        this.move_iv.setOnTouchListener(this.mTouchListener);
        this.rotation = getRotation();
        addView(this.scale_iv);
        this.scale_iv.setLayoutParams(slp);
        this.scale_iv.setOnTouchListener(this.mTouchListener1);
        this.scale_iv.setTag("scale_iv");
        this.isMultiTouchEnabled = setDefaultTouchListener(true);
        this.zoomOutScale = AnimationUtils.loadAnimation(getContext(), R.anim.sticker_scale_zoom_out);
        this.zoomInScale = AnimationUtils.loadAnimation(getContext(), R.anim.sticker_scale_zoom_in);
    }

    public boolean setDefaultTouchListener(boolean enable) {
        if (enable) {
            setOnTouchListener(new MultiTouchListener().enableRotation(true).setOnTouchCallbackListener((AddTextQuotesActivity) this.context));
            setOnTouchCallbackListener((AddTextQuotesActivity) this.context);
            return true;
        }
        setOnTouchListener(null);
        return false;
    }

    public void setBorderVisibility(boolean ch) {
        this.isBorderVisible = ch;
        if (!ch) {
            this.border_iv.setVisibility(View.GONE);
            this.scale_iv.setVisibility(View.GONE);
            this.flip_iv.setVisibility(View.GONE);
            this.delete_iv.setVisibility(View.GONE);
            this.move_iv.setVisibility(View.GONE);
            setBackgroundResource(0);
        } else if (this.border_iv.getVisibility() != View.VISIBLE) {
            this.border_iv.setVisibility(View.VISIBLE);
            this.scale_iv.setVisibility(View.VISIBLE);
            this.move_iv.setVisibility(View.VISIBLE);
            if (this.isSticker) {
                this.flip_iv.setVisibility(View.VISIBLE);
            }
            this.delete_iv.setVisibility(View.VISIBLE);
            setBackgroundResource(R.drawable.border_gray);
        }
    }

    public boolean getBorderVisbilty() {
        return this.isBorderVisible;
    }

    public void setBgDrawable(String redId) {
        this.main_iv.setImageResource(getResources().getIdentifier(redId, "drawable", this.context.getPackageName()));
        this.drawableId = redId;
    }

    public void setComponentInfo(ComponentInfo ci) {
        this.wi = ci.getWIDTH();
        this.he = ci.getHEIGHT();
        this.drawableId = ci.getRES_ID();
        this.rotation = ci.getROTATION();
        this.yRotation = ci.getY_ROTATION();
        this.hueProg = ci.getHUE();
        this.alphaProg = ci.getOPACITY();
        setBgDrawable(this.drawableId);
        setRotation(this.rotation);
        this.margl = ci.getLEFT();
        this.margt = ci.getTOP();
        Log.i("margintop", BuildConfig.FLAVOR + ci.getPOS_X());
        Log.i("margintop", BuildConfig.FLAVOR + ci.getPOS_Y());
        this.flip_iv.setVisibility(View.VISIBLE);
        this.isSticker = true;
        this.stickerType = ci.getTYPE();
        if (this.stickerType.equals("COLOR")) {
            setColorFilter(this.hueProg);
        } else {
            setHueProg(this.hueProg);
        }
        settransparency(this.alphaProg);
        if (ci.getTYPE() == "SHAPE") {
            this.flip_iv.setVisibility(View.GONE);
            this.isSticker = false;
        }
        if (ci.getTYPE() == "STICKER") {
            this.flip_iv.setVisibility(View.VISIBLE);
            this.isSticker = true;
        }
        this.main_iv.setRotationY(this.yRotation);
        ((LayoutParams) getLayoutParams()).leftMargin = this.margl;
        ((LayoutParams) getLayoutParams()).topMargin = this.margt;
        getLayoutParams().width = this.wi;
        getLayoutParams().height = this.he;
        setX(ci.getPOS_X() + ((float) (this.margl * -1)));
        setY(ci.getPOS_Y() + ((float) (this.margt * -1)));
        this.main_iv.startAnimation(this.zoomOutScale);
    }


    public void setComponentInfoBitmap(ComponentInfo ci, Bitmap bbbb) {
        this.wi = ci.getWIDTH();
        this.he = ci.getHEIGHT();
        this.drawableId = ci.getRES_ID();
        this.rotation = ci.getROTATION();
        this.yRotation = ci.getY_ROTATION();
        this.hueProg = ci.getHUE();
        this.alphaProg = ci.getOPACITY();
        setBgBitmap(bbbb);
        setRotation(this.rotation);
        this.margl = ci.getLEFT();
        this.margt = ci.getTOP();
        Log.i("margintop", BuildConfig.FLAVOR + ci.getPOS_X());
        Log.i("margintop", BuildConfig.FLAVOR + ci.getPOS_Y());
        this.flip_iv.setVisibility(View.VISIBLE);
        this.isSticker = true;
        this.stickerType = ci.getTYPE();
        if (this.stickerType.equals("COLOR")) {
            setColorFilter(this.hueProg);
        } else {
            setHueProg(this.hueProg);
        }
        settransparency(this.alphaProg);
        if (ci.getTYPE() == "SHAPE") {
            this.flip_iv.setVisibility(View.GONE);
            this.isSticker = false;
        }
        if (ci.getTYPE() == "STICKER") {
            this.flip_iv.setVisibility(View.VISIBLE);
            this.isSticker = true;
        }
        this.main_iv.setRotationY(this.yRotation);
        ((LayoutParams) getLayoutParams()).leftMargin = this.margl;
        ((LayoutParams) getLayoutParams()).topMargin = this.margt;
        getLayoutParams().width = this.wi;
        getLayoutParams().height = this.he;
        setX(ci.getPOS_X() + ((float) (this.margl * -1)));
        setY(ci.getPOS_Y() + ((float) (this.margt * -1)));
        this.main_iv.startAnimation(this.zoomOutScale);
    }


    public void setBgBitmap(Bitmap biiii) {
        this.main_iv.setImageBitmap(biiii);
    }

    public void optimize(float wr, float hr) {
        setX(getX() * wr);
        setY(getY() * hr);
        getLayoutParams().width = (int) (((float) this.wi) * wr);
        getLayoutParams().height = (int) (((float) this.he) * hr);
    }

    public ComponentInfo getComponentInfo() {
        ComponentInfo ci = new ComponentInfo();
        ci.setPOS_X(getX());
        ci.setPOS_Y(getY());
        ci.setWIDTH(this.wi);
        ci.setHEIGHT(this.he);
        ci.setRES_ID(this.drawableId);
        ci.setROTATION(getRotation());
        ci.setY_ROTATION(this.main_iv.getRotationY());
        ci.setTOP(((LayoutParams) getLayoutParams()).topMargin);
        ci.setLEFT(((LayoutParams) getLayoutParams()).leftMargin);
        ci.setHUE(this.hueProg);
        ci.setTYPE(this.stickerType);
        ci.setOPACITY(this.alphaProg);
        Log.i("margintop", BuildConfig.FLAVOR + getX());
        Log.i("margintop", BuildConfig.FLAVOR + getY());
        Log.i("margintop", BuildConfig.FLAVOR + this.margt);
        Log.i("margintopL", BuildConfig.FLAVOR + this.margl);
        return ci;
    }

    public void incrX() {
        setX(getX() + 1.0f);
    }

    public void decX() {
        setX(getX() - 1.0f);
    }

    public void incrY() {
        setY(getY() + 1.0f);
    }

    public void decY() {
        setY(getY() - 1.0f);
    }

    public int dpToPx(Context c, int dp) {
        float f = (float) dp;
        c.getResources();
        return (int) (f * Resources.getSystem().getDisplayMetrics().density);
    }

    private double getLength(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(y2 - y1, 2.0d) + Math.pow(x2 - x1, 2.0d));
    }

    public void enableColorFilter(boolean b) {
        this.isColorFilterEnable = b;
    }

    public void setHueProg(int hueProg) {
        this.hueProg = hueProg;
        if (this.hueProg == 0) {
            this.main_iv.setColorFilter(-1);
        } else if (this.hueProg == 100) {
            this.main_iv.setColorFilter(-16777216);
        } else {
            this.main_iv.setColorFilter(ColorFilterGenerator.adjustHue((float) hueProg));
        }
    }

    public void setColorFilter(int hueProg) {
        this.hueProg = hueProg;
        this.main_iv.setColorFilter(hueProg);
    }

    public void settransparency(int alphaProg) {
        try {
            this.main_iv.setImageAlpha(alphaProg);
            this.alphaProg = alphaProg;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
