package com.lovejjfg.circle.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.lovejjfg.circle.listener.SimpleAnimatorListener;

/**
 * Created by Joe on 2016-06-16
 * Email: zhangjun166@pingan.com.cn
 */
public class TestView extends View {
    private static String TEST = "这就是一个测试 哎哟不错哦";
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
    private Path path;

    public TestView(Context context) {
        this(context, null);
    }

    public TestView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        evaluator = new BezierEvaluator(new PointF(100, 400), new PointF(200, 0), new PointF(300, 400));
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(5);
        paint.setColor(Color.RED);

        paint.setTextSize(50);

        path = new Path();


    }

    @Override
    protected void onDraw(Canvas canvas) {
//        if (pointF != null) {
//            canvas.drawCircle(pointF.x, pointF.y, radio, paint);
//        }
//        canvas.drawLine(0, 400, getMeasuredWidth(), 400, paint);
        path.reset();
        path.moveTo(100, 100);
        path.lineTo(300, 200);
        path.lineTo(700, 600);
        canvas.translate(0, 100);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawText(TEST, 0, 0, paint);
        canvas.translate(0, 300);
        canvas.drawTextOnPath(TEST, path, 0, 0, paint);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, paint);
        path.reset();
        path.moveTo(0, 500);
        path.quadTo(400, 800, 800, 500);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawTextOnPath(TEST, path, 0, 0, paint);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, paint);
    }
}
