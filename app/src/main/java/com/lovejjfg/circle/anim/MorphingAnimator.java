package com.lovejjfg.circle.anim;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.Log;
import android.widget.TextView;

import com.lovejjfg.circle.anim.drawable.StrokeGradientDrawable;
import com.lovejjfg.circle.listener.OnAnimationEndListener;

/**
 * Created by Joe on 2016-06-13
 * Email: zhangjun166@pingan.com.cn
 */
public class MorphingAnimator {
    public static final int DURATION_NORMAL = 400;
    public static final int DURATION_INSTANT = 1;
    private OnAnimationEndListener mListener;
    private int mDuration;
    private int mFromWidth;
    private int mToWidth;
    private int mFromColor;
    private int mToColor;
    private int mFromStrokeColor;
    private int mToStrokeColor;
    private float mFromCornerRadius;
    private float mToCornerRadius;
    private float mPadding;
    private TextView mView;
    private StrokeGradientDrawable mDrawable;
    private int left;
    private int right;
    private int offset;
    private AnimatorSet animatorSet = new AnimatorSet();
    private boolean isStart;

    public MorphingAnimator(TextView viewGroup, StrokeGradientDrawable drawable) {
        this.mView = viewGroup;
        this.mDrawable = drawable;
    }

    public void setDuration(int duration) {
        this.mDuration = duration;
    }

    public void setListener(OnAnimationEndListener listener) {
        this.mListener = listener;
    }

    public void setFromWidth(int fromWidth) {
        this.mFromWidth = fromWidth;
    }

    public void setToWidth(int toWidth) {
        this.mToWidth = toWidth;
    }

    public void setFromColor(int fromColor) {
        this.mFromColor = fromColor;
    }

    public void setToColor(int toColor) {
        this.mToColor = toColor;
    }

    public void setFromStrokeColor(int fromStrokeColor) {
        this.mFromStrokeColor = fromStrokeColor;
    }

    public void setToStrokeColor(int toStrokeColor) {
        this.mToStrokeColor = toStrokeColor;
    }

    public void setFromCornerRadius(float fromCornerRadius) {
        this.mFromCornerRadius = fromCornerRadius;
    }

    public void setToCornerRadius(float toCornerRadius) {
        this.mToCornerRadius = toCornerRadius;
    }

    public void setPadding(float padding) {
        this.mPadding = padding;
    }

    public void start() {
//        ValueAnimator widthAnimation = ValueAnimator.ofInt(0, offset);
        ValueAnimator widthAnimation = ValueAnimator.ofInt(mFromWidth, mToWidth);
        final GradientDrawable gradientDrawable = this.mDrawable.getGradientDrawable();
        Log.e("TAG", "onAnimationUpdate: Right " + gradientDrawable.getBounds().right);
        Log.e("TAG", "onAnimationUpdate: Left " + gradientDrawable.getBounds().left);
        widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
//                Integer value = (Integer) animation.getAnimatedValue();
//                int i = left - value;
//                int i1 = right +value*3;
//
//                Log.i("TAG", "onAnimationUpdate:left " + i + ";;;Right:" + i1 + ";;value::" + value);
//                gradientDrawable.setBounds(left - value , 0, right +value, mView.getHeight());
//                mView.setTextColor(blendColors(Color.TRANSPARENT, Color.WHITE, animation.getAnimatedFraction()));

                Integer value = (Integer) animation.getAnimatedValue();
                int leftOffset;
                int rightOffset;
                int padding;
                if (mFromWidth > mToWidth) {
                    leftOffset = (mFromWidth - value) / 2;
                    rightOffset = mFromWidth - leftOffset;
                    padding = (int) (mPadding * animation.getAnimatedFraction());
                } else {
                    leftOffset = (mToWidth - value) / 2;
                    rightOffset = mToWidth - leftOffset;
                    padding = (int) (mPadding - mPadding * animation.getAnimatedFraction());
                }
                Log.i("TAG", "onAnimationUpdate: Left::" + leftOffset + "right:" + rightOffset);
                // TODO: 2016-06-14 在HTC_M9上面出现了意外的效果。
                gradientDrawable.setBounds(leftOffset, 0, rightOffset, mView.getHeight());
//                    gradientDrawable.invalidateSelf();
//                    gradientDrawable.setBounds(gradientDrawable.getBounds().left, 0, rightOffset, mView.getHeight());


            }
        });
        ObjectAnimator bgColorAnimation = ObjectAnimator.ofInt(gradientDrawable, "color", this.mFromColor, this.mToColor);
        bgColorAnimation.setEvaluator(new ArgbEvaluator());
        ObjectAnimator strokeColorAnimation = ObjectAnimator.ofInt(this.mDrawable, "strokeColor", this.mFromStrokeColor, this.mToStrokeColor);
        strokeColorAnimation.setEvaluator(new ArgbEvaluator());
        ObjectAnimator cornerAnimation = ObjectAnimator.ofFloat(gradientDrawable, "cornerRadius", this.mFromCornerRadius, this.mToCornerRadius);

        animatorSet.setInterpolator(new LinearOutSlowInInterpolator());
        animatorSet.setDuration((long) this.mDuration);
        animatorSet.playTogether(widthAnimation, bgColorAnimation, strokeColorAnimation, cornerAnimation);
        animatorSet.addListener(new Animator.AnimatorListener() {
            public void onAnimationStart(Animator animation) {
//                mView.setVisibility(View.INVISIBLE);
                isStart = true;
                mView.setTextColor(Color.TRANSPARENT);
            }

            public void onAnimationEnd(Animator animation) {
                mView.setTextColor(Color.WHITE);
                isStart = false;
                if (mListener != null) {
                    mListener.onAnimationEnd();
                }

            }

            public void onAnimationCancel(Animator animation) {
            }

            public void onAnimationRepeat(Animator animation) {
            }
        });
        if (!animatorSet.isRunning()) {
            animatorSet.start();
        }

    }


    public void setLeft(int left) {
        this.left = left;
    }

    public int getLeft() {
        return left;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getRight() {
        return right;
    }

    public void setoffset(int offset) {
        this.offset = offset;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public static
    @ColorInt
    int blendColors(@ColorInt int color1,
                    @ColorInt int color2,
                    @FloatRange(from = 0f, to = 1f) float ratio) {
        final float inverseRatio = 1f - ratio;
        float a = (Color.alpha(color1) * inverseRatio) + (Color.alpha(color2) * ratio);
        float r = (Color.red(color1) * inverseRatio) + (Color.red(color2) * ratio);
        float g = (Color.green(color1) * inverseRatio) + (Color.green(color2) * ratio);
        float b = (Color.blue(color1) * inverseRatio) + (Color.blue(color2) * ratio);
        return Color.argb((int) a, (int) r, (int) g, (int) b);
    }
}
