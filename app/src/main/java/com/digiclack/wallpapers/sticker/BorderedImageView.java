package com.digiclack.wallpapers.sticker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.widget.ImageView;

public class BorderedImageView extends ImageView {
    private static final int PADDING = 8;
    private static final float STROKE_WIDTH = 2.0f;
    private boolean border;
    private Paint mBorderPaint;

    public BorderedImageView(Context context) {
        this(context, null);
        initBorderPaint();
    }

    public BorderedImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        setPadding(PADDING, PADDING, PADDING, PADDING);
        initBorderPaint();
    }

    public BorderedImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.border = true;
        initBorderPaint();
    }

    private void initBorderPaint() {
        this.mBorderPaint = new Paint();
        this.mBorderPaint.setAntiAlias(true);
        this.mBorderPaint.setStyle(Style.STROKE);
        this.mBorderPaint.setColor(-1);
        this.mBorderPaint.setStrokeWidth(STROKE_WIDTH);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.border) {
            canvas.drawRect(8.0f, 8.0f, (float) (getWidth() - 8), (float) (getHeight() - 8), this.mBorderPaint);
        }
    }

    public void removeBorder(boolean b) {
        this.border = b;
        invalidate();
    }
}
