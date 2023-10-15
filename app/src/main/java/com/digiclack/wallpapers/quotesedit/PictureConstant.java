package com.digiclack.wallpapers.quotesedit;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.digiclack.wallpapers.activity.MainActivity;
import com.digiclack.wallpapers.sticker.CustomImageViewBook;

public class PictureConstant {
    private static int sh = 1920;
    private static int sw = 1080;

    public static void removeTextViewControll(RelativeLayout fortext) {
        int childCount = fortext.getChildCount();
        for (int i = 0; i < childCount; i++) {
            try {
                if (fortext.getChildAt(i) instanceof CustomImageViewBook) {
                    ((CustomImageViewBook) fortext.getChildAt(i)).setControlItemsHidden(true);
                } else {
                    int noch = ((ViewGroup) fortext.getChildAt(i)).getChildCount();
                    for (int j = 1; j < noch; j++) {
                        try {
                            ((ViewGroup) fortext.getChildAt(j)).getChildAt(j).setVisibility(View.INVISIBLE);
                        } catch (NullPointerException e) {
                        }
                    }
                }
            } catch (ClassCastException e2) {
            }
        }
    }

    public static float convertPixelsToDp(float px, Context context) {
        return px / (((float) context.getResources().getDisplayMetrics().densityDpi) / 160.0f);
    }

    public static float getNewWidth(float width) {
        return ((float) MainActivity.width) * (width / ((float) sw));
    }

    public static float getNewHeight(float height) {
        sh = (int) (((float) sw) / MainActivity.ratio);
        return ((float) MainActivity.height1) * (height / ((float) sh));
    }

    public static float getNewSize(Context context, float size) {
        return (context.getResources().getDisplayMetrics().density / 3.0f) * size;
    }
}
