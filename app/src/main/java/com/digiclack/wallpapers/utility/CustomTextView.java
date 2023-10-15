package com.digiclack.wallpapers.utility;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.digiclack.wallpapers.R;

public class CustomTextView extends TextView {
    @TargetApi(21)
    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomTextView(Context context) {
        super(context);
        init(context, null);
    }

    private void init(Context ctx, AttributeSet attrs) {
        TypedArray typedAttr = ctx.obtainStyledAttributes(attrs, R.styleable.CustomTextView);
        if (typedAttr.hasValue(0)) {
            String assetName = typedAttr.getString(R.styleable.CustomTextView_assetFontTextName);
            if (assetName != null && assetName.length() > 0) {
                if (assetName.equals("Header")) {
                    setTypeface(Typeface.createFromAsset(getContext().getAssets(), "DroidSans.ttf"));
                } else {
                    setTypeface(Typeface.createFromAsset(getContext().getAssets(), "DroidSans.ttf"));
                }
            }
        }
        if (typedAttr.hasValue(R.styleable.CustomTextView_customColor)) {
            setTextColor(typedAttr.getColor(R.styleable.CustomTextView_customColor, Color.argb(0, 0, 0, 0)));
        }
        typedAttr.recycle();
    }
}
