package com.lovejjfg.circle.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Joe on 2016-06-16
 * Email: zhangjun166@pingan.com.cn
 */
public class TestView extends View implements CurveLayout.Dispatcher {
    private static final String TAG = "TEST";
    private Paint paint;
    private Path path;
    private float currentX;
    private float currentY;
    private View targetView;

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
        paint.setColor(Color.BLUE);
        path = new Path();
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                getParent().requestDisallowInterceptTouchEvent(true);
//                return true;
//            case MotionEvent.ACTION_MOVE:
//                currentX = event.getRawX();
//                float rawY = event.getRawY();
//                currentY = rawY;
//                currentY = rawY >= 200 ? 200 : rawY;
//                Log.e(TAG, "onTouchEvent: " + currentX + ";;;" + currentY);
//                targetView.setTranslationY(currentY*0.5f);
//                invalidate();
//                break;
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_CANCEL:
//                currentX = 0;
//                currentY = 0;
//                targetView.setTranslationY(0);
//                invalidate();
//                break;
//        }
//        return true;
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        path.reset();
        path.moveTo(0, getMeasuredHeight());
        path.quadTo(currentX, currentY + getMeasuredHeight(), getWidth(), getMeasuredHeight());
        canvas.drawPath(path, paint);
    }

    public void setTarget(View mView) {
        targetView = mView;
    }

    @Override
    public void onDispatch(float dx, float dy) {
//        if (currentY == dy) {
//            return;
//        }
        currentY += dy;
        currentX = dx;
        targetView.setTranslationY(currentY * 0.5f);
        invalidate();
    }

    @Override
    public void onDispatchUp() {
        currentY = 0;
        targetView.setTranslationY(currentY);
        invalidate();
    }
}
