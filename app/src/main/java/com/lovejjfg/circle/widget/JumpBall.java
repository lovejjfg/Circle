package com.lovejjfg.circle.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.lovejjfg.circle.R;

/**
 * Created by Joe on 2017/2/21.
 * Email lovejjfg@gmail.com
 */

public class JumpBall extends View {
    private CirclePoint p0;
    private CirclePoint p1;
    private CirclePoint p2;
    private CirclePoint p3;
    private CirclePoint p4;
    private CirclePoint p5;
    private CirclePoint p6;
    private CirclePoint p7;
    private CirclePoint p8;
    private CirclePoint p9;
    private CirclePoint p10;
    private CirclePoint p11;

    /**
     * 当前绘制的Path
     */
    private Path mPath;
    private Paint mPaint;

    /**
     * 中心圆的半径
     */
    private float mCircleRadius = 90;
    private int mWidth;
    private int mHeight;
    private float mTranslateValue = 0;
    private boolean mIsPath = false;
    private double currentY;
    private int mChange;
    private int mTopChange;
    private int mLeftChange;
    private int mRightChange;
    private int mBottomChange;
    private float mPathDistance;
    private boolean isEnd;
    private int dropHeight = 300;
    private int pullRange = (int) (dropHeight * 0.15);
    private ValueAnimator pullAnimator;
    private ValueAnimator dropAnimator;
    private ValueAnimator radioAnimator;
    private float mCurrentRadio;

    public JumpBall(Context context) {
        this(context, null);
    }

    public JumpBall(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public JumpBall(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void resetPoints() {
        float m = mCircleRadius * CIRCLE_VALUE;

        p0.setPoint(mWidth / 2, Math.abs(mHeight / 2 - mCircleRadius));
        p1.setPoint(mWidth / 2 + m, Math.abs(mHeight / 2 - mCircleRadius));
        p2.setPoint(mWidth / 2 + mCircleRadius, Math.abs(mHeight / 2 - m));
        p3.setPoint(mWidth / 2 + mCircleRadius, mHeight / 2);

        p4.setPoint(mWidth / 2 + mCircleRadius, mHeight / 2 + m);
        p5.setPoint(mWidth / 2 + m, mHeight / 2 + mCircleRadius);
        p6.setPoint(mWidth / 2, mHeight / 2 + mCircleRadius);

        p7.setPoint(Math.abs(mWidth / 2 - m), mHeight / 2 + mCircleRadius);
        p8.setPoint(Math.abs(mWidth / 2 - mCircleRadius), mHeight / 2 + m);
        p9.setPoint(Math.abs(mWidth / 2 - mCircleRadius), mHeight / 2);

        p10.setPoint(Math.abs(mWidth / 2 - mCircleRadius), Math.abs(mHeight / 2 - m));
        p11.setPoint(Math.abs(mWidth / 2 - m), Math.abs(mHeight / 2 - mCircleRadius));
    }

    /**
     * 通过贝塞尔曲线绘制圆
     */
    private static final float CIRCLE_VALUE = 0.551915024494f;

    private void initPoints() {
        float m = mCircleRadius * CIRCLE_VALUE;

        p0 = new CirclePoint(mWidth / 2, mHeight / 2 - mCircleRadius);
        p1 = new CirclePoint(mWidth / 2 + m, mHeight / 2 - mCircleRadius);
        p2 = new CirclePoint(mWidth / 2 + mCircleRadius, mHeight / 2 - m);
        p3 = new CirclePoint(mWidth / 2 + mCircleRadius, mHeight / 2);

        p4 = new CirclePoint(mWidth / 2 + mCircleRadius, mHeight / 2 + m);
        p5 = new CirclePoint(mWidth / 2 + m, mHeight / 2 + mCircleRadius);
        p6 = new CirclePoint(mWidth / 2, mHeight / 2 + mCircleRadius);

        p7 = new CirclePoint(mWidth / 2 - m, mHeight / 2 + mCircleRadius);
        p8 = new CirclePoint(mWidth / 2 - mCircleRadius, mHeight / 2 + m);
        p9 = new CirclePoint(mWidth / 2 - mCircleRadius, mHeight / 2);

        p10 = new CirclePoint(mWidth / 2 - mCircleRadius, mHeight / 2 - m);
        p11 = new CirclePoint(mWidth / 2 - m, mHeight / 2 - mCircleRadius);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHeight = h;
        initPoints();
        if (radioAnimator == null) {
            radioAnimator = ValueAnimator.ofFloat(mCircleRadius, mWidth*2);
        } else {
            radioAnimator.setFloatValues(mCircleRadius, mWidth*2);
        }
        super.onSizeChanged(w, h, oldw, oldh);
    }

    private void init(Context context) {
        if (mPaint == null) {
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
            mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        }

        if (mPath == null) {
            mPath = new Path();
        }

        pullAnimator = ValueAnimator.ofInt(0, pullRange, 0);
//        pullAnimator.setRepeatMode(ValueAnimator.REVERSE);
//        pullAnimator.setRepeatCount(20);
        pullAnimator.setDuration(300);
//        pullAnimator.setInterpolator(new BounceInterpolator());
        pullAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mChange = (Integer) animation.getAnimatedValue();
                if (animation.getAnimatedFraction() > 0.3) {
                    dropAnimator.setIntValues(dropHeight, 0, dropHeight);
                    dropAnimator.setDuration(800);
                    dropAnimator.start();
                }
                Log.e("TAG", "onAnimationUpdate: " + mChange);
                invalidate();
            }
        });

        pullAnimator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {

            }
        });


        dropAnimator = ValueAnimator.ofInt(0, dropHeight);
//        dropAnimator.setRepeatMode(ValueAnimator.REVERSE);
//        dropAnimator.setRepeatCount(20);
        dropAnimator.setDuration(400);
        dropAnimator.setInterpolator(new LinearInterpolator());
        dropAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mTranslateValue = (Integer) animation.getAnimatedValue();
                invalidate();
            }
        });

        dropAnimator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
//                dropAnimator = ValueAnimator.ofInt(dropHeight, 0, dropHeight);
                pullAnimator.start();
            }
        });
        dropAnimator.start();

        radioAnimator = ValueAnimator.ofFloat(mCircleRadius, mWidth);
//        radioAnimator.setRepeatMode(ValueAnimator.REVERSE);
//        radioAnimator.setRepeatCount(20);
        radioAnimator.setDuration(600);
        radioAnimator.setInterpolator(new LinearInterpolator());
        radioAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentRadio = (float) animation.getAnimatedValue();
                invalidate();
            }
        });

        radioAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                pullAnimator.cancel();
                dropAnimator.cancel();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isEnd = false;
                mCurrentRadio = mCircleRadius;
                setVisibility(GONE);
            }
        });
//        radioAnimator.start();
        postDelayed(new Runnable() {
            @Override
            public void run() {
                pullAnimator.cancel();
                dropAnimator.cancel();
                radioAnimator.start();
                isEnd = true;
            }
        }, 5000);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isEnd) {
            drawCirclePath(canvas, mPathDistance);
        } else {
            canvas.drawCircle(mWidth / 2, mHeight / 2 + mTranslateValue, mCurrentRadio, mPaint);
        }
    }


    /**
     * 通过贝塞尔曲线画圆
     * 下落到最低点 8 9 10 （左 - dx）  4 3 2 （右 + dx）  5 6 7（上 + dy）  11 0 1（下 ）
     */
    private void drawCirclePath(Canvas canvas, float distance) {
        resetPoints();
        p0.y += mTranslateValue;
        p6.y += mTranslateValue;
        p1.y += mTranslateValue;
        p2.y += mTranslateValue + mChange * 0.5;
        p3.y += mTranslateValue + mChange * 0.5;
        p4.y += mTranslateValue + mChange * 0.5;
        p5.y += mTranslateValue;
        p7.y += mTranslateValue;
        p8.y += mTranslateValue + mChange * 0.5;
        p9.y += mTranslateValue + mChange * 0.5;
        p10.y += mTranslateValue + mChange * 0.5;
        p11.y += mTranslateValue;

        p8.x -= mChange;
        p9.x -= mChange;
        p10.x -= mChange;

        p4.x += mChange;
        p3.x += mChange;
        p2.x += mChange;

        p11.y += mChange;
        p0.y += mChange;
        p1.y += mChange;

        mPath.reset();
        mPath.moveTo(p0.x, p0.y);
        mPath.cubicTo(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y);
        mPath.cubicTo(p4.x, p4.y, p5.x, p5.y, p6.x, p6.y);
        mPath.cubicTo(p7.x, p7.y, p8.x, p8.y, p9.x, p9.y);
        mPath.cubicTo(p10.x, p10.y, p11.x, p11.y, p0.x, p0.y);
        canvas.drawPath(mPath, mPaint);
//        canvas.translate(0, mTranslateValue);
    }

    public void setBottomChange(int progress) {
        mBottomChange = progress;
    }

    public void setLeftChange(int progress) {
        mLeftChange = progress;
    }

    public void setRightChange(int progress) {
        mRightChange = progress;
    }

    public void setTopChange(int progress) {
        dropHeight = 300 + progress;
        pullRange = (int) (dropHeight * 0.15);
    }

    private class CirclePoint {
        float x;
        float y;

        public CirclePoint(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public void setPoint(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        dropAnimator.cancel();
        pullAnimator.cancel();

    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        if (visibility == INVISIBLE || visibility == GONE) {
            pullAnimator.cancel();
            dropAnimator.cancel();
            radioAnimator.cancel();
        }
        super.onVisibilityChanged(changedView, visibility);
    }
}
