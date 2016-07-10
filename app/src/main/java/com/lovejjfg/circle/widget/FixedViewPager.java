package com.lovejjfg.circle.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Joe on 2016/7/10.
 * Email lovejjfg@gmail.com
 */
public class FixedViewPager extends ViewPager {

    private int startX;
    private int startY;

    public FixedViewPager(Context context) {
        super(context);
    }

    public FixedViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN://有事件先拦截再说！！
//                getParent().requestDisallowInterceptTouchEvent(true);
//                startX = (int) ev.getRawX();
//                startY = (int) ev.getRawY();
//                break;
//            case MotionEvent.ACTION_MOVE://移动的时候
//                int endX = (int) ev.getRawX();
//                int endY = (int) ev.getRawY();
//                //判断四种情况：
//                //3.上下互动，需要ListView来响应。
//                if (Math.abs(endX - startX) < (Math.abs(endY - startY))) {
//                    getParent().requestDisallowInterceptTouchEvent(false);
//                }
//                break;
//        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int height = 0;
//        // 下面遍历所有child的高度
//        for (int i = 0; i < getChildCount(); i++) {
//            View child = getChildAt(i);
//            child.measure(widthMeasureSpec,
//                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//            int h = child.getMeasuredHeight();
//            // 采用最大的view的高度
//            if (h > height) {
//                height = h;
//            }
//        }
//        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
//                MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
