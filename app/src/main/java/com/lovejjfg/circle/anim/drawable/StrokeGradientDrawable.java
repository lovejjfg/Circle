package com.lovejjfg.circle.anim.drawable;

import android.graphics.drawable.GradientDrawable;

/**
 * Created by Joe on 2016-06-13
 * Email: zhangjun166@pingan.com.cn
 */
public class StrokeGradientDrawable {
    private int mStrokeWidth;
    private int mStrokeColor;
    private GradientDrawable mGradientDrawable;
    public StrokeGradientDrawable(GradientDrawable drawable) {
        this.mGradientDrawable = drawable;
    }

    public int getStrokeWidth() {
        return this.mStrokeWidth;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.mStrokeWidth = strokeWidth;
        this.mGradientDrawable.setStroke(strokeWidth, this.getStrokeColor());
    }

    public int getStrokeColor() {
        return this.mStrokeColor;
    }

    public void setStrokeColor(int strokeColor) {
        this.mStrokeColor = strokeColor;
        this.mGradientDrawable.setStroke(this.getStrokeWidth(), strokeColor);
    }

    public GradientDrawable getGradientDrawable() {
        return this.mGradientDrawable;
    }


}
