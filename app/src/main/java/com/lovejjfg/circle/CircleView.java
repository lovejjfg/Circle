package com.lovejjfg.circle;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.animation.OvershootInterpolator;

/**
 * Created by Joe on 2016-04-04
 * Email: lovejjfg@gmail.com
 */
public class CircleView extends View {

    private Paint paint;
    private RectF rectF;
    private RectF innerRectf;
    private int width;
    private int height;
    private int SecondRadius = 200;
    private int result = 10;
    private Paint outPaint;

    private Property<CircleView, Float> mAngleProperty = new Property<CircleView, Float>(Float.class, "angle") {
        @Override
        public Float get(CircleView object) {
            return object.getCurrentAngle();
        }

        @Override
        public void set(CircleView object, Float value) {
            object.setCurrentAngle(value);
        }
    };
    private ObjectAnimator mAngleAnimator;

    private void setCurrentAngle(float value) {
        currentAngle = value;
        invalidate();
    }

    private float currentAngle;

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        outPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        outPaint.setStyle(Paint.Style.STROKE);
        outPaint.setAntiAlias(true);
        outPaint.setStrokeWidth(20);
        outPaint.setColor(Color.RED);
        outPaint.setAlpha(50);
        outPaint.setStrokeCap(Paint.Cap.ROUND);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(20);
        paint.setColor(Color.GRAY);
        paint.setAlpha(50);
        paint.setStrokeCap(Paint.Cap.ROUND);
        rectF = new RectF();
        innerRectf = new RectF();

        mAngleAnimator = ObjectAnimator.ofFloat(this, mAngleProperty, 0, 180);
        mAngleAnimator.setInterpolator(new OvershootInterpolator());
        mAngleAnimator.setDuration(2000);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(rectF, 0, 360, false, paint);
        canvas.drawArc(rectF, 0, currentAngle, false, outPaint);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
        rectF.set((float) (width / 2 - (SecondRadius + result)), (float) (height / 2 - (SecondRadius + result)), (float) (width
                / 2 + (SecondRadius + result)), (float) (height / 2 + (SecondRadius + result)));
    }

    public float getCurrentAngle() {
        return currentAngle;
    }

    public void start() {
        if (!mAngleAnimator.isRunning()) {
            mAngleAnimator.start();
        }
    }
}
