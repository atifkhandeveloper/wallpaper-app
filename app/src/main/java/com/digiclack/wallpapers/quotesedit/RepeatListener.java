package com.digiclack.wallpapers.quotesedit;

import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class RepeatListener implements OnTouchListener {
    private final OnClickListener clickListener;
    private View downView;
    private ImageView guideline;
    private Handler handler = new Handler();
    private Runnable handlerRunnable = new Runnable() {
        public void run() {
            RepeatListener.this.handler.postDelayed(this, (long) RepeatListener.this.normalInterval);
            RepeatListener.this.clickListener.onClick(RepeatListener.this.downView);
        }
    };
    private int initialInterval;
    private final int normalInterval;

    public RepeatListener(int initialInterval, int normalInterval, OnClickListener clickListener) {
        if (clickListener == null) {
            throw new IllegalArgumentException("null runnable");
        } else if (initialInterval < 0 || normalInterval < 0) {
            throw new IllegalArgumentException("negative interval");
        } else {
            this.initialInterval = initialInterval;
            this.normalInterval = normalInterval;
            this.clickListener = clickListener;
        }
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case 0 :
                this.handler.removeCallbacks(this.handlerRunnable);
                this.handler.postDelayed(this.handlerRunnable, (long) this.initialInterval);
                this.downView = view;
                this.downView.setPressed(true);
                this.clickListener.onClick(view);
                return true;
            case 1 :
            case 3 :
                this.handler.removeCallbacks(this.handlerRunnable);
                this.downView.setPressed(false);
                this.downView = null;
                return true;
            default:
                return false;
        }
    }
}
