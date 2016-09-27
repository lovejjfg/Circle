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
import android.view.MotionEvent;
import android.view.View;

import com.lovejjfg.circle.listener.SimpleAnimatorListener;

import static android.transition.Fade.IN;

/**
 * Created by Joe on 2016-06-16
 * Email: zhangjun166@pingan.com.cn
 */
public class TestView extends View {
    private static final String TAG = "TEST";
    private Paint paint;
    private Path path;
    private float currentX;
    private float currentY;

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
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(5);
        paint.setColor(Color.RED);
        path = new Path();


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                return true;
            case MotionEvent.ACTION_MOVE:
                currentX = event.getRawX();
                float rawY = event.getRawY();
                currentY = rawY >= 100 ? 100 : rawY;
                Log.e(TAG, "onTouchEvent: " + currentX + ";;;" + currentY);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                currentX = 0;
                currentY = 0;
                invalidate();
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        if (pointF != null) {
//            canvas.drawCircle(pointF.x, pointF.y, radio, paint);
//        }
//        canvas.drawLine(0, 400, getMeasuredWidth(), 400, paint);
        path.reset();
        path.moveTo(0, getMeasuredHeight());
        path.quadTo(currentX, currentY + getMeasuredHeight(), getWidth(), getMeasuredHeight());
        canvas.drawPath(path, paint);
    }
}
