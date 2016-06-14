package com.lovejjfg.circle.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Property;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

/**
 * Created by Joe on 2016-06-14
 * Email: zhangjun166@pingan.com.cn
 */
public class PathTextView extends View {
    private static String TEST = "这就是一个测试哎哟不错哦哦测试！！哎哟";
    private static int[] COLOR = {Color.BLUE, Color.RED, Color.GRAY, Color.GREEN, Color.BLUE};
    DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator();
    LinearInterpolator linearInterpolator = new LinearInterpolator();
    LinearOutSlowInInterpolator linearOutSlowInInterpolator = new LinearOutSlowInInterpolator();
    private Path path;
    private float textWidth;

    private float defaultRadio = 20;
    private float defaultX = 0;
    private float defaultY = 0;
    private Paint textPaint;
    private float currentOffset = -1;
    private Paint paint;
    private ObjectAnimator offsetAnimator;
    private float textHeight;
    private Paint cilclePaint;
    private float radioCenterX;
    private float radioCenterY = defaultRadio;
    private ObjectAnimator distanceAnimator;
    private float amplitude = 20.0f;//振幅
    private BounceInterpolator bounceInterpolator;

    public PathTextView(Context context) {
        this(context, null);
    }

    public PathTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        path = new Path();
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(30);
        textPaint.setColor(Color.RED);
        textPaint.setStrokeCap(Paint.Cap.ROUND);
        textPaint.setTextAlign(Paint.Align.LEFT);

        textWidth = textPaint.measureText(TEST);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(5);
        paint.setColor(Color.GREEN);

        cilclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        cilclePaint.setStyle(Paint.Style.FILL);
        cilclePaint.setStrokeWidth(5);
        cilclePaint.setColor(Color.GREEN);

        textWidth = textPaint.measureText(TEST);
        offsetAnimator = ObjectAnimator.ofFloat(this, mOffsetProperty, 0);
        offsetAnimator.setDuration(1000);
        bounceInterpolator = new BounceInterpolator();
        offsetAnimator.setInterpolator(bounceInterpolator);

        distanceAnimator = ObjectAnimator.ofFloat(this, mDistanceProperty, 0);
        distanceAnimator.setDuration(1000);
        distanceAnimator.setInterpolator(linearInterpolator);
        distanceAnimator.setRepeatCount(Integer.MAX_VALUE);
        distanceAnimator.setRepeatMode(ValueAnimator.REVERSE);
        distanceAnimator.addListener(new Animator.AnimatorListener() {

            private int count;

            @Override
            public void onAnimationStart(Animator animation) {
                Log.i("TAG", "onAnimationEnd: 开始了！！");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.i("TAG", "onAnimationEnd: 结束了！！");
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.i("TAG", "onAnimationEnd: 取消了！！");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Log.i("TAG", "onAnimationEnd: 重复了！！");
                count += 1;
                if (count % 2 == 0) {
                    cilclePaint.setColor(COLOR[count % 5]);
//                    distanceAnimator.setInterpolator(linearOutSlowInInterpolator);
                }
                if (!offsetAnimator.isRunning() && count % 2 == 1) {
                    offsetAnimator.start();
                }

            }
        });
        distanceAnimator.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        textHeight = textPaint.getFontMetrics().bottom - textPaint.getFontMetrics().top;
        defaultY = h - textHeight; //(h+textHeight*0.5f) / 2.0f;
        offsetAnimator.setFloatValues(defaultY, defaultY + amplitude, defaultY);
        distanceAnimator.setFloatValues(radioCenterY, defaultY - textHeight);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        textHeight = textPaint.getFontMetrics().bottom - textPaint.getFontMetrics().top;
        widthMeasureSpec = MeasureSpec.makeMeasureSpec((int) textPaint.measureText(TEST), MeasureSpec.EXACTLY);
//        MeasureSpec.getMode(heightMeasureSpec)==MeasureSpec.EXACTLY
//        heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) (textHeight*2), MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        path.reset();
        path.moveTo(defaultX, defaultY);
        radioCenterX = (defaultX + textWidth) / 2.0f;
        if (currentOffset !=-1) {
            path.quadTo(radioCenterX, currentOffset, textWidth, defaultY);
        } else {
            path.lineTo(textWidth, defaultY);
        }
//        canvas.drawPoint(radioCenterX, currentOffset, paint);
//        canvas.drawPath(path, paint);
        canvas.drawTextOnPath(TEST, path, 0, 0, textPaint);

        canvas.drawCircle(radioCenterX, radioCenterY, defaultRadio, cilclePaint);
    }

    private Property<PathTextView, Float> mOffsetProperty = new Property<PathTextView, Float>(Float.class, "offset") {
        @Override
        public Float get(PathTextView object) {
            return object.getCurrentOffset();
        }

        @Override
        public void set(PathTextView object, Float value) {

            object.setCurrentOffset(value);
        }
    };
    private Property<PathTextView, Float> mDistanceProperty = new Property<PathTextView, Float>(Float.class, "distance") {
        @Override
        public Float get(PathTextView object) {
            return object.getCurrentDistance();
        }

        @Override
        public void set(PathTextView object, Float value) {
            object.setCurrentDistance(value);

        }
    };

    public Float getCurrentOffset() {
        return currentOffset;
    }

    public void setCurrentOffset(Float currentOffset) {
        this.currentOffset = currentOffset;
        invalidate();
    }

    public void setCurrentDistance(Float currentDistance) {
        this.radioCenterY = currentDistance;
        invalidate();
    }

    public Float getCurrentDistance() {
        return radioCenterY;
    }
}
