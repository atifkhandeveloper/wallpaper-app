package com.digiclack.wallpapers.quotesedit;

import android.graphics.Shader;
import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.text.style.UpdateAppearance;

public class CustomShaderSpan extends CharacterStyle implements UpdateAppearance {
    Shader shader;

    public CustomShaderSpan(Shader shader) {
        this.shader = shader;
    }

    public void updateDrawState(TextPaint paint) {
        paint.setShader(this.shader);
    }
}
