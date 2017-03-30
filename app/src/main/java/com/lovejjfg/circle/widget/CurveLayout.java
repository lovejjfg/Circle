package com.lovejjfg.circle.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Joe on 2016/9/30.
 * Email lovejjfg@gmail.com
 */

public class CurveLayout extends FrameLayout {

    private float currentY;
    private float currentX;
    private float totalY;

    public CurveLayout(Context context) {
        super(context);
    }

    public CurveLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CurveLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                currentY = event.getY();
                currentX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float rawY = event.getY();
                float rawX = event.getX();
                float dy = rawY - currentY;
                float dx = rawX - currentX;
                Log.e(TAG, "onInterceptTouchEvent: " + ";;;" + dy);
                return dy > dx;
        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                currentY = event.getY();
                currentX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float rawY = event.getY();
                float rawX = event.getX();
                float dy = rawY - currentY;
                Log.e(TAG, "onTouchEvent: " + ";;;" + dy);
                totalY += dy;
                dispatcher(rawX, totalY);
                currentY = rawY;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                Log.e(TAG, "onTouchEvent: " + ";;;" + "松手了!");
                currentY = 0;
                totalY = 0;
                dispatcher(0, totalY);
                break;
        }
        return true;
    }

    private void dispatcher(float dx, float dy) {
        if (mDispatchers != null) {
            for (Dispatcher d : mDispatchers) {
                d.onDispatch(dx, dy);
            }
        }
    }

    private void dispatcherUp() {
        if (mDispatchers != null) {
            for (Dispatcher d : mDispatchers) {
                d.onDispatchUp();
            }
        }
    }

    public void addmDispatcher(@Nullable Dispatcher d) {
        if (mDispatchers == null) {
            mDispatchers = new ArrayList<>();
        }
        mDispatchers.add(d);
    }

    @Nullable
    private List<Dispatcher> mDispatchers;

    public interface Dispatcher {
        void onDispatch(float dx, float dy);

        void onDispatchUp();
    }

    @Override
    protected void onFinishInflate() {
        getChildAt(0);
        super.onFinishInflate();

    }
}
