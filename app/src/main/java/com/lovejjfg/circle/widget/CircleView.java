package com.lovejjfg.circle.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
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

    /**
     * Layout info for the arrowhead in dp
     */
    private static final int ARROW_WIDTH = 10 * 2;
    private static final int ARROW_HEIGHT = 5 * 2;
    private static final float ARROW_OFFSET_ANGLE = 5;

    /**
     * Layout info for the arrowhead for the large spinner in dp
     */
    private static final int ARROW_WIDTH_LARGE = 12;
    private static final int ARROW_HEIGHT_LARGE = 6;
    private static final float MAX_PROGRESS_ARC = .8f;

    private Paint paint;
    private RectF rectF;
    private RectF innerRectf;
    private int width;
    private int height;
    private int SecondRadius = 200;
    private int result = 10;
    private float mStrokeInset = 2.5f;
    private final Paint mArrowPaint = new Paint();
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
    private Path mArrow;
    private float centerX;
    private float centerY;
    private int radio;
    private int mRingCenterRadius;
    private float mArrowScale = 0.6f;

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
        mArrowPaint.setStyle(Paint.Style.FILL);
        mArrowPaint.setAntiAlias(true);
        mArrowPaint.setStrokeWidth(20);

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
//        canvas.drawArc(rectF, 0, 360, false, paint);
//        canvas.drawArc(rectF, 0, currentAngle, false, outPaint);
        drawTriangle(canvas, 0, currentAngle);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
        mRingCenterRadius = Math.min(width, height);
        rectF.set((float) (width / 2 - (SecondRadius + result)), (float) (height / 2 - (SecondRadius + result)), (float) (width
                / 2 + (SecondRadius + result)), (float) (height / 2 + (SecondRadius + result)));
        centerX = rectF.centerX();
        centerY = rectF.centerY();
    }

    public float getCurrentAngle() {
        return currentAngle;
    }

    public void start() {
        if (!mAngleAnimator.isRunning()) {
            mAngleAnimator.start();
        }
    }

    public void drawTriangle(Canvas c, float startAngle, float sweepAngle) {
        if (mArrow == null) {
            mArrow = new android.graphics.Path();
            mArrow.setFillType(android.graphics.Path.FillType.EVEN_ODD);
        } else {
            mArrow.reset();
        }

        // Adjust the position of the triangle so that it is inset as
        // much as the arc, but also centered on the arc.
        float inset = (int) mStrokeInset / 2 * mArrowScale;
        float x = (float) (mRingCenterRadius * Math.cos(0) + centerX);
        float y = (float) (mRingCenterRadius * Math.sin(0) + centerY);

        // Update the path each time. This works around an issue in SKIA
        // where concatenating a rotation matrix to a scale matrix
        // ignored a starting negative rotation. This appears to have
        // been fixed as of API 21.
        mArrow.moveTo(0, 0);
        mArrow.lineTo(ARROW_WIDTH * mArrowScale, 0);
        mArrow.lineTo((ARROW_WIDTH * mArrowScale / 2), (ARROW_HEIGHT
                * mArrowScale));
        mArrow.offset(x - inset, y);
        mArrow.close();
        // draw a triangle
        mArrowPaint.setColor(Color.RED);
        c.rotate(startAngle + sweepAngle - ARROW_OFFSET_ANGLE, centerX,
               centerY);
        c.drawPath(mArrow, mArrowPaint);
    }
}
