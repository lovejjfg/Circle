package com.lovejjfg.circle.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.lovejjfg.circle.anim.drawable.MaterialProgressDrawable;

/**
 * Created by Joe on 2016/4/3.
 * Email lovejjfg@gmail.com
 */
public class TouchCircleView extends View {

    private RectF outRectF;
    private RectF innerRectf;
    private RectF secondRectf;

    private float mCurrentGlobalAngleOffset;
    private float mCurrentGlobalAngle;
    private float mCurrentSweepAngle;
    private static final int MIN_SWEEP_ANGLE = 100;


    private Paint secondPain;
    private Paint innerPaint;
    private float innerResult;
    private float mWidth;
    private ObjectAnimator mWidthAnimator;
    private ObjectAnimator mInnerWidthAnimator;
    private Bitmap bitmap;
    private Rect rect;
    private int rotate = 30;
    private int count;
    private Integer angle1;
    private ValueAnimator animator;
    private int startX;
    private int startY;
    private Path path;
    private Path mArrow;
    private static final int State_Draw_Arc = 1;
    private static final int State_Draw_Path = 2;
    private static final int State_Draw_Circle = 3;
    private static final int State_Draw_Arrow = 4;
    private static final int State_Draw_Progress = 5;
    //    private static final int State_Draw_Path_Arc = 4;
    private int currentState;
    private int centerY;
    private int centerX;
    MaterialProgressDrawable drawable;
    private float mBorderWidth =4;
    private float mRingCenterRadius;
    private boolean mModeAppearing;
    private float mArrowScale = 1.0f;


    public int getMultipleRadius() {
        return multipleRadius;
    }

    public void setMultipleRadius(int multipleRadius) {
        this.multipleRadius = multipleRadius;
        Log.i("最小半径改变：", multipleRadius + "");
    }

    public int getMaxMul() {
        return maxMul;
    }

    public void setMaxMul(int maxMul) {
        this.maxMul = maxMul;
    }

    public int getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(int acceleration) {
        this.acceleration = acceleration;
        if (null != mWidthAnimator) {
            mWidthAnimator.setFloatValues(10, acceleration * 18);
        }
    }

    private int multipleRadius = 7;
    private int maxMul = 5;
    private int acceleration = 2;

    //设置默认半径
    public void setOutCirRadius(int outCirRadius) {
        this.outCirRadius = outCirRadius;
        Log.i("默认半径：", outCirRadius + "");

    }

    public int getOutCirRadius() {
        return outCirRadius;
    }

    private int outCirRadius = 100;
    private int innerCirRadius = outCirRadius-30;
    private static int ARROW_WIDTH = 20 * 2;
    private static int ARROW_HEIGHT = 10 * 2;


    private Paint circlePaint;
    private Paint wavePaint;
    //    private float result;
    private long angle;
    private long paths;
    private Paint paint;

    public TouchCircleView(Context context) {
        super(context);
        initView();
    }

    public TouchCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public TouchCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();

    }

    private float firstCount;
    private boolean isAdd = true;
    private boolean isReduce;

    private void initView() {
        innerRectf = new RectF();
        secondRectf = new RectF();
        outRectF = new RectF();
        ARROW_WIDTH = (int) (mBorderWidth * 2);
        ARROW_HEIGHT = (int) mBorderWidth;
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent ev) {
                switch (ev.getAction()) {
                    case MotionEvent.ACTION_DOWN://有事件先拦截再说！！
                        startX = (int) ev.getRawX();
                        startY = (int) ev.getRawY();
                        outRectF.set(centerX - outCirRadius, 0, centerX + outCirRadius
                                , centerY + outCirRadius);
                        break;
                    case MotionEvent.ACTION_MOVE://移动的时候
                        int endY = (int) ev.getRawY();
                        int dy = (int) ((endY - startY) * 1.5f);
                        if (dy < 0) {
                            dy = 0;
                        }
//                        if (dy > 360) {
//                            dy = 360;
//                        }
//                        if (dy!=angle && dy >= -360 && dy <= 360) {
                        if (dy != angle && dy >= 0 && dy <= 360) {
                            outRectF.set(centerX - outCirRadius, 0, centerX + outCirRadius
                                    , centerY + outCirRadius);
                            currentState = State_Draw_Arc;
                            angle = dy;
                            Log.i("TAG", "onTouchEvent: " + angle);
                            invalidate();
                            break;
                        }
                        if (dy > 360 && dy < 400) {
                            currentState = State_Draw_Arrow;
                            mCurrentGlobalAngle++;
                            invalidate();
                            break;
                        }
                        //正常顺序到达这里
                        if ( dy >= 400 && dy <= 600) {
                            currentState = State_Draw_Path;

                            paths = dy - 300;
                            if (dy >= 400) {//360+半径？？
                                float precent = (dy-400) * 1.0f / 200;
                                outRectF.set(centerX - outCirRadius + precent * 40,    30 * precent, centerX + outCirRadius - precent * 40
                                        , centerY + outCirRadius - 20 * precent);

                            }
                            invalidate();
                            break;
                        }

                        if (dy >= 600) {
                            currentState = State_Draw_Circle;
                            invalidate();
                            break;
                        }


                        break;
                }
                return true;
            }
        });
        post(new Runnable() {


            @Override
            public void run() {
//                centerX = getMeasuredWidth() / 2;
//                centerY = getMeasuredHeight() / 2 - 200;
                Log.e("TAG111", "run: " + centerX);
//                outRectF.set(centerX - outCirRadius, centerY - outCirRadius, centerX + outCirRadius
//                        , centerY + outCirRadius);
                animator = ValueAnimator.ofInt(0, 360);
                animator.setInterpolator(new AccelerateDecelerateInterpolator());
                animator.setDuration(5000);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        angle1 = (Integer) animation.getAnimatedValue();
                        TouchCircleView.this.invalidate();
                        Log.i("TAG", "onAnimationUpdate: " + angle1);

                    }
                });
//                animator.start();

            }
        });
        circlePaint = new Paint();
        circlePaint.setColor(Color.BLUE);
        circlePaint.setStrokeWidth(10);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setAntiAlias(true);
        circlePaint.setAlpha(50);
        path = new Path();
        mArrow = new Path();


        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(20);
        paint.setColor(Color.RED);
//        paint.setAlpha(50);
        paint.setStrokeCap(Paint.Cap.SQUARE);


        innerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        innerPaint.setStyle(Paint.Style.STROKE);
        innerPaint.setAntiAlias(true);
        innerPaint.setColor(Color.WHITE);
        innerPaint.setStrokeWidth(mBorderWidth);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int min = Math.min(w, h);
        centerX = w/2;
        centerY = outCirRadius;

//        outRectF.set(min-outCirRadius, min-outCirRadius, min+outCirRadius, min+outCirRadius);
//        secondRectf.set(min-80, min-80, min+80, min +80);
//        innerRectf.left = mBorderWidth * 2f + .5f;
//        innerRectf.right = min - mBorderWidth * 2f - .5f;
//        innerRectf.top = mBorderWidth * 2f + .5f;
//        innerRectf.bottom = min - mBorderWidth * 2f - .5f;
        innerRectf.set(centerX - innerCirRadius, centerY - innerCirRadius, centerX + innerCirRadius, centerY + innerCirRadius);


//        mRingCenterRadius = Math.min(w, h)+mBorderWidth/2;
        mRingCenterRadius = Math.min(innerRectf.centerX() - innerRectf.left, innerRectf.centerY() - innerRectf.top) - mBorderWidth;

    }

    //    注意：onDraw每次被调用时canvas画布都是一个干净的、空白的、透明的，他不会记录以前画上去的
    @Override
    protected void onDraw(Canvas canvas) {
        //动画的
        switch (currentState) {
            case State_Draw_Arc:
                canvas.drawArc(outRectF, 0, angle, true, paint);
                canvas.drawArc(outRectF, 0, angle, false, circlePaint);
                break;
            case State_Draw_Arrow:
                canvas.drawArc(outRectF, 0, 360, true, paint);
                drawArc(canvas);
                break;
            case State_Draw_Path:
                path.reset();
                path.moveTo((float) (outRectF.centerX() - Math.cos(180 / Math.PI * 30) * (outRectF.centerX() - outRectF.left)), (float) (outRectF.centerY() - Math.sin(180 / Math.PI * 30) * (outRectF.centerY() - outRectF.top)));
                path.quadTo(outRectF.centerX(), outRectF.centerY() + paths, (float) (outRectF.centerX() + Math.cos(180 / Math.PI * 30) * (outRectF.centerX() - outRectF.left)), (float) (outRectF.centerY() - Math.sin(180 / Math.PI * 30) * (outRectF.centerY() - outRectF.top)));
                canvas.drawPath(path, paint);
                canvas.drawArc(outRectF, 0, 360, true, paint);
                break;
            case State_Draw_Circle:
                canvas.drawCircle(outRectF.centerX(), outRectF.centerY() + outRectF.bottom - outRectF.top, 80, paint);
                break;
        }


//        // 最小圆形
//        canvas.drawCircle(width / 2, height / 2, minRadius, circlePaint);
//        circlePaint.setAlpha(120);
    }

    private void drawArc(Canvas canvas) {
        float startAngle = mCurrentGlobalAngle - mCurrentGlobalAngleOffset;
        float sweepAngle = mCurrentSweepAngle;
        if (mModeAppearing) {
//            paint.setColor(gradient(mColors[mCurrentColorIndex], mColors[mNextColorIndex],
//                    mCurrentSweepAngle / (360 - MIN_SWEEP_ANGLE * 2)));
            sweepAngle += MIN_SWEEP_ANGLE;
        } else {
            startAngle = startAngle + sweepAngle;
            sweepAngle = 360 - sweepAngle - MIN_SWEEP_ANGLE;
        }
        canvas.drawArc(innerRectf, startAngle, sweepAngle, false, innerPaint);
        drawTriangle(canvas, startAngle, sweepAngle);
    }

    public void drawTriangle(Canvas c, float startAngle, float sweepAngle) {
        if (mArrow == null) {
            mArrow = new Path();
            mArrow.setFillType(Path.FillType.EVEN_ODD);
        } else {
            mArrow.reset();
        }

        // Adjust the position of the triangle so that it is inset as
        // much as the arc, but also centered on the arc.
//        float inset = (int) mStrokeInset / 2 * mArrowScale;
        float x = (float) (mRingCenterRadius * Math.cos(0) + innerRectf.centerX());
        float y = (float) (mRingCenterRadius * Math.sin(0) + innerRectf.centerY());

        // Update the path each time. This works around an issue in SKIA
        // where concatenating a rotation matrix to a scale matrix
        // ignored a starting negative rotation. This appears to have
        // been fixed as of API 21.
        mArrow.moveTo(0, 0);

        mArrow.lineTo(ARROW_WIDTH * mArrowScale, 0);
        mArrow.lineTo((ARROW_WIDTH * mArrowScale / 2), (ARROW_HEIGHT
                * mArrowScale));
        mArrow.offset(x, y);
        mArrow.close();
        // draw a triangle
        c.rotate(startAngle + sweepAngle, innerRectf.centerX(),
                innerRectf.centerY());
        c.drawPath(mArrow, innerPaint);

    }

//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//
//    }

}
