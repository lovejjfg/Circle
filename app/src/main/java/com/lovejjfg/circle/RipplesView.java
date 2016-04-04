package com.lovejjfg.circle;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Property;
import android.view.View;
import android.view.animation.LinearInterpolator;

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
    private float result;
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

        mWidthAnimator = ObjectAnimator.ofFloat(this, mAngleProperty, 10, 200);
        mWidthAnimator.setInterpolator(new LinearInterpolator());
        mWidthAnimator.setDuration(800);
        mWidthAnimator.setRepeatCount(Integer.MAX_VALUE);
        mWidthAnimator.setRepeatMode(ValueAnimator.REVERSE);
        mWidthAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        int height = getHeight();
        int width = getWidth();
        Log.e("result:", result + "");
        angle++;
        // 最小半径
        int minRadius = (multipleRadius * cirRadius / 10);
        paint.setStrokeWidth(mWidth);
        innerPaint.setStrokeWidth(innerResult + innerResult);
        int SecondRadius = (int) (minRadius + innerResult);
        paint.setAlpha(80);
        float strokeWidth = getStrokeWidth() / 2;
        rectF.set(width / 2 - (SecondRadius + strokeWidth), height / 2 - (SecondRadius + strokeWidth), width
                / 2 + (SecondRadius + strokeWidth), height / 2 + (SecondRadius + strokeWidth));
        innerRectf.set((float) (width / 2 - (minRadius + innerResult)), (float) (height / 2 - (minRadius + innerResult)), (float) (width
                / 2 + (minRadius + innerResult)), (float) (height / 2 + (minRadius + innerResult)));
//        canvas.drawArc(innerRectf, 0, 360, false, innerPaint);
        canvas.drawArc(rectF, 0, 360, false, paint);
//        // 最小圆形
//        canvas.drawCircle(width / 2, height / 2, minRadius, circlePaint);
//        circlePaint.setAlpha(120);
    }


    private Property<RipplesView, Float> mAngleProperty = new Property<RipplesView, Float>(Float.class, "width") {
        @Override
        public Float get(RipplesView object) {
            return object.getStrokeWidth();
        }

        @Override
        public void set(RipplesView object, Float value) {
            object.setStrokeWidth(value);
        }
    };

    private float getStrokeWidth() {
        return mWidth;
    }

    public void setStrokeWidth(float strokeWidth) {
        this.mWidth = strokeWidth;
        invalidate();
    }
}
