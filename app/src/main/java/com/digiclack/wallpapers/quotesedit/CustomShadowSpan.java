package com.digiclack.wallpapers.quotesedit;

import android.text.TextPaint;
import android.text.style.CharacterStyle;

public class CustomShadowSpan extends CharacterStyle {
    public float Dx;
    public float Dy;
    public float Radius;
    public int color;

    public CustomShadowSpan(float radius, float dx, float dy, int color) {
        this.Radius = radius;
        this.Dx = dx;
        this.Dy = dy;
        this.color = color;
    }

    public void updateDrawState(TextPaint tp) {
        tp.setShadowLayer(this.Radius, this.Dx, this.Dy, this.color);
    }
}
