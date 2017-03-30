package com.lovejjfg.circle.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.lovejjfg.circle.listener.SimpleAnimatorListener;

/**
 * Created by Joe on 2016-06-16
 * Email: lovejjfg@gmail.com
 */
public class LoadingView extends View {

    private BezierEvaluator evaluator;
    private Paint paint;
    private float fraction;
    private PointF pointF;
    private ValueAnimator animator;
    private int count;
    private boolean reduce;
    private int radio = 20;
    private int each;
    private int offset;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        evaluator = new BezierEvaluator(new PointF(100, 400), new PointF(200, 0), new PointF(300, 400));
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(15);
        paint.setColor(Color.RED);
        animator = ValueAnimator.ofInt(0, 100);
        animator.setDuration(2000);
//        animator.setRepeatCount(Integer.MAX_VALUE);
//        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.addListener(new SimpleAnimatorListener() {


            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
//                evaluator.evaluate()


                Log.i("TAG", "onAnimationEnd: " + count);

                if (count == 4) {
                    count--;
                    reduce = true;
                } else {
                    count++;
                    reduce = false;
                }
                if (reduce) {
                    evaluator.setPoints(new PointF(300 + count * each, 400), new PointF((200 + count * each), 0), new PointF(100 + count * each, 400));
                } else {
                    evaluator.setPoints(new PointF(radio + count * each, 400), new PointF((radio + count * each), 0), new PointF(radio + (count+1) * each, 400));
                }

                post(new Runnable() {
                    @Override
                    public void run() {
                        animator.start();

                    }
                });
            }
        });
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                fraction = animation.getAnimatedFraction();
                pointF = evaluator.evaluate(fraction, null, null);
                invalidate();
//                Log.i("TAG", "onAnimationUpdate: " + pointF.x + ";;;;" + pointF.y);
            }
        });
        post(new Runnable() {

            private int measuredWidth;

            @Override
            public void run() {
                measuredWidth = getMeasuredWidth() ;
                each = (measuredWidth - 2 * radio)/ 4;
                //offset
                offset = each / 2;
                animator.start();
            }
        });

    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (pointF != null) {
            canvas.drawCircle(pointF.x, pointF.y, radio, paint);
        }
        canvas.drawLine(0, 400, getMeasuredWidth(), 400, paint);
    }
}
