package com.lovejjfg.circle.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.lovejjfg.circle.R;


/**
 * leo linxiaotao1993@vip.qq.com
 * Created on 16-8-24 下午9:58
 */
public class NewTouchCircleView extends View {
    private Paint mPaint;
    /**
     * 当前状态
     */
    private int mCurrentStatus;
    /**
     * 中心圆的半径
     */
    private float mCircleRadius;
    /**
     * 是否需要绘制Path曲线
     */
    private boolean mIsPath;
    /**
     * 当前绘制的Path
     */
    private Path mPath;
    /**
     * 绘制Path的左边距离
     */
    private float mPathDistance;
    /**
     * View的宽
     */
    private int mWidth;
    /**
     * View的高
     */
    private int mHeight;
    /**
     * 画布移动值
     */
    private float mTranslateValue;

    /**
     * 绘制第一步
     */
    private static final int STEP_ONE = 0;
    /**
     * 绘制第二步
     */
    private static final int STEP_TWO = 1;
    /**
     * 绘制第三步
     */
    private static final int STEP_THREE = 2;
    /**
     * 绘制第四步
     */
    private static final int STEP_FOUR = 3;
    /**
     * 贝塞尔曲线改变值
     */
    private static final int PATH_CHANGE_VAL = 1;
    /**
     * 大圆大小改变值
     */
    private static final int CIRCLE_VAL = 15;
    /**
     * 小圆的数量
     */
    private static final int CIRCLE_COUNT = 3;
    /**
     * 正常状态
     */
    private static final int STATUS_NORNAL = 0;
    /**
     * loading状态
     */
    private static final int STATUS_LOADING = 1;
    /**
     * 通过贝塞尔曲线绘制圆
     */
    private static final float CIRCLE_VALUE = 0.551915024494f;
    /**
     * 线条颜色
     */
    private final static int[] COLORS = new int[]{0xFF7ECBDA, 0xFFE6A92C, 0xFFF0A0A5, 0xFF5ABA94};
    private float rawX;
    private CirclePoint p0;
    private CirclePoint p1;
    private CirclePoint p2;
    private CirclePoint p3;
    private CirclePoint p4;
    private CirclePoint p5;
    private CirclePoint p6;
    private CirclePoint p7;
    private CirclePoint p8;
    private CirclePoint p9;
    private CirclePoint p10;
    private CirclePoint p11;
    private int mChange;
    private float currentY;

    public NewTouchCircleView(Context context) {
        this(context, null);
    }

    public NewTouchCircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NewTouchCircleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        if (mPathDistance == 0) {
//            return;
//        }
        super.onDraw(canvas);
        Log.e("TAG", "onDraw: " + mPathDistance);
        drawCirclePath(canvas, mPathDistance);
//        drawCircle(canvas, mCurrentStep);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHeight = h;
        initPoints();
        super.onSizeChanged(w, h, oldw, oldh);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // private method
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 初始化
     */
    private void init() {
        initView();
        initData();
    }

    /**
     * 初始化View
     */
    private void initView() {
        if (mPaint == null) {
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
            mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        }

        if (mPath == null) {
            mPath = new Path();
        }
    }

    /**
     * 初始化参数
     */
    private void initData() {
        mTranslateValue = 0;
        mCurrentStatus = STATUS_NORNAL;
        mCircleRadius = dp2px(getContext(), 40);
        mPathDistance = 0;

    }

    private void initPoints() {
        float m = mCircleRadius * CIRCLE_VALUE;

        p0 = new CirclePoint(mWidth / 2, mHeight / 2 - mCircleRadius);
        p1 = new CirclePoint(mWidth / 2 + m, mHeight / 2 - mCircleRadius);
        p2 = new CirclePoint(mWidth / 2 + mCircleRadius, mHeight / 2 - m);
        p3 = new CirclePoint(mWidth / 2 + mCircleRadius, mHeight / 2);

        p4 = new CirclePoint(mWidth / 2 + mCircleRadius, mHeight / 2 + m);
        p5 = new CirclePoint(mWidth / 2 + m, mHeight / 2 + mCircleRadius);
        p6 = new CirclePoint(mWidth / 2, mHeight / 2 + mCircleRadius);

        p7 = new CirclePoint(mWidth / 2 - m, mHeight / 2 + mCircleRadius);
        p8 = new CirclePoint(mWidth / 2 - mCircleRadius, mHeight / 2 + m);
        p9 = new CirclePoint(mWidth / 2 - mCircleRadius, mHeight / 2);

        p10 = new CirclePoint(mWidth / 2 - mCircleRadius, mHeight / 2 - m);
        p11 = new CirclePoint(mWidth / 2 - m, mHeight / 2 - mCircleRadius);
    }


    /**
     * 通过贝塞尔曲线画圆
     */
    private void drawCirclePath(Canvas canvas, float distance) {
        initPoints();
        if (mIsPath) {
            // 0 5  y轴加 变宽
            // 2 8 x轴改变
            //旋转90度
            //9 2 x增加
            //11 5 Y周

            //0->9 1->10 2->
            if (distance > 0) {
                p0.y -= mChange*0.1f;
                p11.y = p1.y = p0.y;//竖直方向变大
                //上边 +
                p5.y += distance;
                p7.y = p6.y = p5.y;
                //左边 +
                p8.x += mChange / 2;
                p10.x = p9.x = p8.x;

                //右边 -
                p2.x -= mChange / 2;
                p3.x = p4.x = p2.x;


            } else {
                //下边
                p0.y += distance;
                p11.y = p1.y = p0.y;//竖直方向变大
//                //上边 +
                p5.y += mChange*0.1f;
                p7.y = p6.y = p5.y;
                //左边 +
                p8.x -= mChange / 2;
                p10.x = p9.x = p8.x;

                //右边 -
                p2.x += mChange / 2;
                p3.x = p4.x = p2.x;

            }
        } else {
            p0.x += mTranslateValue;
            p6.x += mTranslateValue;
            if (mTranslateValue > 0) {
                p1.x += mTranslateValue;
                p2.x += mTranslateValue;
                p3.x += mTranslateValue;
                p4.x += mTranslateValue;
                p5.x += mTranslateValue;

                p7.x += mTranslateValue / 6;
                p8.x += mTranslateValue / 6;
                p9.x += mTranslateValue / 6;
                p10.x += mTranslateValue / 6;
                p11.x += mTranslateValue / 6;

            } else {
                p1.x += mTranslateValue / 5;
                p2.x += mTranslateValue / 5;
                p3.x += mTranslateValue / 5;
                p4.x += mTranslateValue / 5;
                p5.x += mTranslateValue / 5;

                p7.x += mTranslateValue;
                p8.x += mTranslateValue;
                p9.x += mTranslateValue;
                p10.x += mTranslateValue;
                p11.x += mTranslateValue;
            }


        }

        mPath.reset();
        mPath.moveTo(p0.x, p0.y);
        mPath.cubicTo(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y);
        mPath.cubicTo(p4.x, p4.y, p5.x, p5.y, p6.x, p6.y);
        mPath.cubicTo(p7.x, p7.y, p8.x, p8.y, p9.x, p9.y);
        mPath.cubicTo(p10.x, p10.y, p11.x, p11.y, p0.x, p0.y);
        canvas.drawPath(mPath, mPaint);
    }

    private void resetPoint() {

    }

    /**
     * dp==>px
     */
    private int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
//        Logger.d(scale);
        return (int) (dp * scale + 0.5f);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("TAG", "onTouchEvent: mPathDistance");
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_DOWN:
                mIsPath = true;
                getParent().requestDisallowInterceptTouchEvent(true);
                float rawX = event.getRawX();
                float rawY = event.getY();
                if (currentY > rawY) {
                    mChange -= PATH_CHANGE_VAL;
                } else if (currentY < rawY) {
                    mChange += PATH_CHANGE_VAL;
                }
                currentY = rawY;
                Log.e("TAG", "onTouchEvent: " + rawY + ";;width:" + mHeight / 2);
//                mPathDistance = rawX - mWidth/2;
                mPathDistance = (rawY - mHeight / 2) * 0.65f;
                Log.e("TAG", "onTouchEvent: mChange::" + mChange);
                if (mChange > 50) {
                    mChange = 50;
                } else if (mChange < -50) {
                    mChange = -50;
                }

                Log.e("TAG", "onTouchEvent: " + mPathDistance);

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mIsPath = false;
                mPathDistance = 0;
                mChange = 0;
                break;
        }
        invalidate();
        return true;
    }


    private class CirclePoint {
        float x;
        float y;

        public CirclePoint(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }
}
