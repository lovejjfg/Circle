package com.lovejjfg.circle.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Joe on 2016/6/19.
 * Email lovejjfg@gmail.com
 */
public class StickyNestedScrollView extends NestedScrollView {
    private static final String TAG = StickyNestedScrollView.class.getSimpleName();
    private ArrayList<View> mStickViews;
    @Nullable
    private View currenStickyView;
    private int startX;
    private int startY;

    public StickyNestedScrollView(Context context) {
        this(context, null);
    }

    public StickyNestedScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StickyNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mStickViews = new ArrayList<>();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN://有事件先拦截再说！！
                getParent().requestDisallowInterceptTouchEvent(true);
                startX = (int) ev.getRawX();
                startY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE://移动的时候
                int endX = (int) ev.getRawX();
                int endY = (int) ev.getRawY();
                //判断四种情况：
                //3.上下互动，需要ListView来响应。
                if (Math.abs(endX - startX) < (Math.abs(endY - startY))) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                } else {
                     getParent().requestDisallowInterceptTouchEvent(false);
                }
//                } else if (endX - startX > 0) {//右滑动
//                    if (getCurrentItem() == 0) {// 1.1 & 1.2的情况，具体逻辑和父类系统自己会处理的！
//                        getParent().requestDisallowInterceptTouchEvent(false);
//                    }
//                } else {//2、最后一张，向左滑动，需要父亲，滑到下一个内容。
//                    if (getCurrentItem() == getAdapter().getCount() - 1) {
//                        getParent().requestDisallowInterceptTouchEvent(false);
//                    }
//                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN://有事件先拦截再说！！
//                Log.i(TAG, "onTouchEvent: DOWN");
//                getParent().requestDisallowInterceptTouchEvent(true);
//                startX = (int) ev.getX();
//                startY = (int) ev.getY();
//                break;
//            case MotionEvent.ACTION_MOVE://移动的时候
//                Log.i(TAG, "onTouchEvent: MOVE");
//                int endX = (int) ev.getX();
//                int endY = (int) ev.getY();
//                if (Math.abs(endX - startX) < (Math.abs(endY - startY))) {
//                    getParent().requestDisallowInterceptTouchEvent(false);
//                    return false;
//                } else {
//                    super.onTouchEvent(ev);
//                    return true;
//                }
//        }
        return super.onTouchEvent(ev);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        currenStickyView = null;
        View childAt = getChildAt(0);
        if (childAt instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) childAt).getChildCount(); i++) {
                View view = ((ViewGroup) childAt).getChildAt(i);
                Object tag = view.getTag();
                Log.i(TAG, "onFinishInflate: " + view.getTag());
                if (tag != null && "stick".equalsIgnoreCase(tag.toString())) {
                    mStickViews.add(view);
                }
            }
        } else if (childAt.getTag() != null && "stick".equalsIgnoreCase(childAt.getTag().toString())) {
            mStickViews.add(childAt);
        }
    }

    @Override
    protected void onScrollChanged(int l, int scrollY, int oldl, int oldt) {
        super.onScrollChanged(l, scrollY, oldl, oldt);
        int dy = scrollY - oldt;
        // TODO: 2016/6/19 完善锁定
        for (View view : mStickViews) {
            if (currenStickyView != null) {//第一个已经固定在那个位置上了！
                if (view != currenStickyView) {//第二个View已经过来了！
                    Log.i(TAG, "onScrollChanged: 第二个到达了！！");
                    if (scrollY >= view.getTop() - currenStickyView.getHeight()) {
                        currenStickyView.setTranslationY(scrollY - currenStickyView.getTop());
                    }

                }
            }

            if (scrollY >= (view.getTop())) {
                if (currenStickyView == null) {
                    currenStickyView = view;
                }
                view.setTranslationY(scrollY - view.getTop());//加了是向下
//                if (currenStickyView != view) {
//                    currenStickyView.setTranslationY(scrollY - currenStickyView.getTop() + dy);
//                }
            } else {
                view.setTranslationY(0);
            }
//            if (currenStickyView != null && scrollY >= currenStickyView.getTop() + currenStickyView.getHeight()) {
//                currenStickyView = view;
//            }
        }

    }
}
