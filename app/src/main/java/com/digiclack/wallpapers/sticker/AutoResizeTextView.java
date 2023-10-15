package com.digiclack.wallpapers.sticker;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.RectF;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.widget.TextView;

public class AutoResizeTextView extends TextView {
    private static final int NO_LINE_LIMIT = -1;
    private RectF mAvailableSpaceRect;
    private boolean mEnableSizeCache = true;
    private boolean mInitiallized;
    private int mMaxLines;
    private float mMaxTextSize;
    private float mMinTextSize = 20.0f;
    private TextPaint mPaint;
    private final SizeTester mSizeTester = new SizeTester() {
        @TargetApi(16)
        public int onTestSize(int suggestedSize, RectF availableSPace) {
            AutoResizeTextView.this.mPaint.setTextSize((float) suggestedSize);
            String text = AutoResizeTextView.this.getText().toString();
            if (AutoResizeTextView.this.getMaxLines() == 1) {
                AutoResizeTextView.this.mTextRect.bottom = AutoResizeTextView.this.mPaint.getFontSpacing();
                AutoResizeTextView.this.mTextRect.right = AutoResizeTextView.this.mPaint.measureText(text);
            } else {
                StaticLayout layout = new StaticLayout(text, AutoResizeTextView.this.mPaint, AutoResizeTextView.this.mWidthLimit, Alignment.ALIGN_NORMAL, AutoResizeTextView.this.mSpacingMult, AutoResizeTextView.this.mSpacingAdd, true);
                if (AutoResizeTextView.this.getMaxLines() != AutoResizeTextView.NO_LINE_LIMIT && layout.getLineCount() > AutoResizeTextView.this.getMaxLines()) {
                    return 1;
                }
                AutoResizeTextView.this.mTextRect.bottom = (float) layout.getHeight();
                int maxWidth = AutoResizeTextView.NO_LINE_LIMIT;
                for (int i = 0; i < layout.getLineCount(); i++) {
                    if (((float) maxWidth) < layout.getLineWidth(i)) {
                        maxWidth = (int) layout.getLineWidth(i);
                    }
                }
                AutoResizeTextView.this.mTextRect.right = (float) maxWidth;
            }
            AutoResizeTextView.this.mTextRect.offsetTo(0.0f, 0.0f);
            return availableSPace.contains(AutoResizeTextView.this.mTextRect) ? AutoResizeTextView.NO_LINE_LIMIT : 1;
        }
    };
    private float mSpacingAdd = 0.0f;
    private float mSpacingMult = 1.0f;
    private SparseIntArray mTextCachedSizes;
    private RectF mTextRect = new RectF();
    private int mWidthLimit;

    private interface SizeTester {
        int onTestSize(int i, RectF rectF);
    }

    public AutoResizeTextView(Context context) {
        super(context);
        initialize();
    }

    public AutoResizeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public AutoResizeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    private void initialize() {
        this.mPaint = new TextPaint(getPaint());
        this.mMaxTextSize = getTextSize();
        this.mAvailableSpaceRect = new RectF();
        this.mTextCachedSizes = new SparseIntArray();
        if (this.mMaxLines == 0) {
            this.mMaxLines = NO_LINE_LIMIT;
        }
        this.mInitiallized = true;
    }

    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        adjustTextSize(text.toString());
    }

    public void setTextSize(float size) {
        this.mMaxTextSize = size;
        this.mTextCachedSizes.clear();
        adjustTextSize(getText().toString());
    }

    public void setMaxLines(int maxlines) {
        super.setMaxLines(maxlines);
        this.mMaxLines = maxlines;
        reAdjust();
    }

    public int getMaxLines() {
        return this.mMaxLines;
    }

    public void setSingleLine() {
        super.setSingleLine();
        this.mMaxLines = 1;
        reAdjust();
    }

    public void setSingleLine(boolean singleLine) {
        super.setSingleLine(singleLine);
        if (singleLine) {
            this.mMaxLines = 1;
        } else {
            this.mMaxLines = NO_LINE_LIMIT;
        }
        reAdjust();
    }

    public void setLines(int lines) {
        super.setLines(lines);
        this.mMaxLines = lines;
        reAdjust();
    }

    public void setTextSize(int unit, float size) {
        Resources r;
        Context c = getContext();
        if (c == null) {
            r = Resources.getSystem();
        } else {
            r = c.getResources();
        }
        this.mMaxTextSize = TypedValue.applyDimension(unit, size, r.getDisplayMetrics());
        this.mTextCachedSizes.clear();
        adjustTextSize(getText().toString());
    }

    public void setLineSpacing(float add, float mult) {
        super.setLineSpacing(add, mult);
        this.mSpacingMult = mult;
        this.mSpacingAdd = add;
    }

    public void setMinTextSize(float minTextSize) {
        this.mMinTextSize = minTextSize;
        reAdjust();
    }

    private void reAdjust() {
        adjustTextSize(getText().toString());
    }

    private void adjustTextSize(String string) {
        if (this.mInitiallized) {
            int startSize = (int) this.mMinTextSize;
            int heightLimit = (getMeasuredHeight() - getCompoundPaddingBottom()) - getCompoundPaddingTop();
            this.mWidthLimit = (getMeasuredWidth() - getCompoundPaddingLeft()) - getCompoundPaddingRight();
            this.mAvailableSpaceRect.right = (float) this.mWidthLimit;
            this.mAvailableSpaceRect.bottom = (float) heightLimit;
            super.setTextSize(0, (float) efficientTextSizeSearch(startSize, (int) this.mMaxTextSize, this.mSizeTester, this.mAvailableSpaceRect));
        }
    }

    public void enableSizeCache(boolean enable) {
        this.mEnableSizeCache = enable;
        this.mTextCachedSizes.clear();
        adjustTextSize(getText().toString());
    }

    private int efficientTextSizeSearch(int start, int end, SizeTester sizeTester, RectF availableSpace) {
        if (!this.mEnableSizeCache) {
            return binarySearch(start, end, sizeTester, availableSpace);
        }
        String text = getText().toString();
        int key = text == null ? 0 : text.length();
        int size = this.mTextCachedSizes.get(key);
        if (size != 0) {
            return size;
        }
        size = binarySearch(start, end, sizeTester, availableSpace);
        this.mTextCachedSizes.put(key, size);
        return size;
    }

    private static int binarySearch(int start, int end, SizeTester sizeTester, RectF availableSpace) {
        int lastBest = start;
        int lo = start;
        int hi = end + NO_LINE_LIMIT;
        while (lo <= hi) {
            int mid = (lo + hi) >>> 1;
            int midValCmp = sizeTester.onTestSize(mid, availableSpace);
            if (midValCmp < 0) {
                lastBest = lo;
                lo = mid + 1;
            } else if (midValCmp <= 0) {
                return mid;
            } else {
                hi = mid + NO_LINE_LIMIT;
                lastBest = hi;
            }
        }
        return lastBest;
    }

    protected void onTextChanged(CharSequence text, int start, int before, int after) {
        super.onTextChanged(text, start, before, after);
        reAdjust();
    }

    protected void onSizeChanged(int width, int height, int oldwidth, int oldheight) {
        this.mTextCachedSizes.clear();
        super.onSizeChanged(width, height, oldwidth, oldheight);
        if (width != oldwidth || height != oldheight) {
            reAdjust();
        }
    }
}
