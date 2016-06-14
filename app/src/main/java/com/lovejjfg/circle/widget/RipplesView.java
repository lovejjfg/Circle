package com.lovejjfg.circle.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Property;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.lovejjfg.circle.R;

/**
 * Created by Joe on 2016/4/3.
 * Email lovejjfg@gmail.com
 */
public class RipplesView extends View {

    private RectF rectF;
    private Paint secondPain;
    private RectF innerRectf;
    private Paint innerPaint;
    private float innerResult;
    private float mWidth;
    private ObjectAnimator mWidthAnimator;
    private ObjectAnimator mInnerWidthAnimator;
    private Bitmap bitmap;
    private Rect rect;
    private RectF bitmapRectf;
    private int rotate = 30;
    private int count;


    public int getMultipleRadius() {
        return multipleRadius;
    }

    public void setMultipleRadius(int multipleRadius) {
        this.multipleRadius = multipleRadius;
        Log.i("最小半径改变：", multipleRadius + "");
    }

    public int getMaxMul() {
        return maxMul;
    }

    public void setMaxMul(int maxMul) {
        this.maxMul = maxMul;
    }

    public int getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(int acceleration) {
        this.acceleration = acceleration;
        if (null != mWidthAnimator) {
            mWidthAnimator.setFloatValues(10, acceleration * 18);
        }
    }

    private int multipleRadius = 7;
    private int maxMul = 5;
    private int acceleration = 2;

    //设置默认半径
    public void setCirRadius(int cirRadius) {
        this.cirRadius = cirRadius;
        Log.i("默认半径：", cirRadius + "");

    }

    public int getCirRadius() {
        return cirRadius;
    }

    private int cirRadius;
    private Paint circlePaint;
    private Paint wavePaint;
    //    private float result;
    private Paint containPaint;
    private long angle;
    private Paint paint;

    public RipplesView(Context context) {
        super(context);
        initView();
    }

    public RipplesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public RipplesView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private float firstCount;
    private boolean isAdd = true;
    private boolean isReduce;

    private void initView() {
        rectF = new RectF();
        innerRectf = new RectF();
        bitmapRectf = new RectF();
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        rect = new Rect(0, 0, bitmap.getWidth() + 100, bitmap.getHeight() + 100);


        cirRadius = 200;
        circlePaint = new Paint();
        circlePaint.setColor(Color.WHITE);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setAntiAlias(true);
        circlePaint.setAlpha(50);
        wavePaint = new Paint(circlePaint);
        wavePaint.setStyle(Paint.Style.FILL);
        containPaint = new Paint(circlePaint);
        containPaint.setStrokeWidth(10);
        containPaint.setAntiAlias(true);
        containPaint.setAlpha(1);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(20);
        paint.setColor(Color.WHITE);
        paint.setAlpha(50);
        paint.setStrokeCap(Paint.Cap.ROUND);


        innerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        innerPaint.setStyle(Paint.Style.STROKE);
        innerPaint.setAntiAlias(true);
        innerPaint.setColor(Color.WHITE);

        mWidthAnimator = ObjectAnimator.ofFloat(this, mWidthProperty, 10, 200);
        mWidthAnimator.setInterpolator(new LinearInterpolator());
        mWidthAnimator.setDuration(800);
        mWidthAnimator.setRepeatCount(Integer.MAX_VALUE);
        mWidthAnimator.setRepeatMode(ValueAnimator.REVERSE);
        mWidthAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mWidthAnimator.setRepeatCount(Integer.MAX_VALUE);
                mWidthAnimator.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
//                animation.pause();
//                mInnerWidthAnimator.resume();
            }
        });


        mInnerWidthAnimator = ObjectAnimator.ofFloat(this, mInnerWidthProperty, 0, 20);
        mInnerWidthAnimator.setInterpolator(new LinearInterpolator());
        mInnerWidthAnimator.setDuration(300);
        mInnerWidthAnimator.setRepeatCount(Integer.MAX_VALUE);
        mInnerWidthAnimator.setRepeatMode(ValueAnimator.REVERSE);
        mInnerWidthAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mInnerWidthAnimator.setRepeatCount(Integer.MAX_VALUE);
                mInnerWidthAnimator.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
//                animation.pause();
//                mWidthAnimator.start();
            }
        });

        mWidthAnimator.start();
        mInnerWidthAnimator.start();
    }

    //    注意：onDraw每次被调用时canvas画布都是一个干净的、空白的、透明的，他不会记录以前画上去的
    @Override
    protected void onDraw(Canvas canvas) {
        setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        int height = getHeight();
        int width = getWidth();
        angle++;
        // 最小半径
        int minRadius = (multipleRadius * cirRadius / 10);
        paint.setStrokeWidth(mWidth);
        innerPaint.setStrokeWidth(innerResult + innerResult);
        int SecondRadius = (int) (minRadius + getInnerStrokeWidth() / 2);
        paint.setAlpha(80);
        float strokeWidth = getStrokeWidth() / 2;
        rectF.set(width / 2 - (SecondRadius + strokeWidth), height / 2 - (SecondRadius + strokeWidth), width
                / 2 + (SecondRadius + strokeWidth), height / 2 + (SecondRadius + strokeWidth));
        innerRectf.set(width / 2 - (minRadius + innerResult), height / 2 - (minRadius + innerResult), width
                / 2 + (minRadius + innerResult), height / 2 + (minRadius + innerResult));
        /*bitmap使用*/
        bitmapRectf.set(width / 2 - (minRadius + innerResult - 80), height / 2 - (minRadius + innerResult - 80), width
                / 2 + (minRadius + innerResult - 80), height / 2 + (minRadius + innerResult - 80));
//        rect.union((int) innerRectf.left, (int) innerRectf.top, (int) innerRectf.right, (int) innerRectf.bottom);
//        canvas.drawArc(innerRectf, 0, 360, false, innerPaint);
        canvas.drawArc(rectF, 0, 360, false, paint);

        rotate += 0.1;
        canvas.rotate(rotate, getWidth() / 2, getHeight() / 2);
        canvas.drawBitmap(bitmap, null, bitmapRectf, null);
//        // 最小圆形
//        canvas.drawCircle(width / 2, height / 2, minRadius, circlePaint);
//        circlePaint.setAlpha(120);
    }


    private Property<RipplesView, Float> mWidthProperty = new Property<RipplesView, Float>(Float.class, "width") {
        @Override
        public Float get(RipplesView object) {
            return object.getStrokeWidth();
        }

        @Override
        public void set(RipplesView object, Float value) {
            object.setStrokeWidth(value);
        }
    };
    private Property<RipplesView, Float> mInnerWidthProperty = new Property<RipplesView, Float>(Float.class, "innerWidth") {
        @Override
        public Float get(RipplesView object) {
            return object.getInnerStrokeWidth();
        }

        @Override
        public void set(RipplesView object, Float value) {
            object.setInnerStrokeWidth(value);
        }
    };

    private void setInnerStrokeWidth(float value) {
        innerResult = value;
        invalidate();
    }

    private float getInnerStrokeWidth() {
        return innerResult;
    }

    private float getStrokeWidth() {
        return mWidth;
    }

    public void setStrokeWidth(float strokeWidth) {
        this.mWidth = strokeWidth;
        invalidate();
    }
}
