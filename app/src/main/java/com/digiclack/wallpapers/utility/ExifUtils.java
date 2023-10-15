package com.digiclack.wallpapers.utility;

import android.media.ExifInterface;
import android.text.TextUtils;

import com.digiclack.wallpapers.scale.SubsamplingScaleImageView;

public class ExifUtils {
    private ExifUtils() {
    }

    public static int getExifRotation(String imgPath) {
        try {
            String rotationAmount = new ExifInterface(imgPath).getAttribute("Orientation");
            if (TextUtils.isEmpty(rotationAmount)) {
                return 0;
            }
            switch (Integer.parseInt(rotationAmount)) {
                case 3 :
                    return SubsamplingScaleImageView.ORIENTATION_180;
                case 6 :
                    return 90;
                case 8 :
                    return SubsamplingScaleImageView.ORIENTATION_270;
                default:
                    return 0;
            }
        } catch (Exception e) {
            return 0;
        }
    }
}
