package com.lovejjfg.circle.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.lovejjfg.circle.R;
import com.lovejjfg.circle.anim.drawable.MaterialProgressDrawable;

/**
 * Created by Joe on 2016/4/3.
 * Email lovejjfg@gmail.com
 */
public class TouchCircleView extends View {

    private RectF rectF;
    private Paint secondPain;
    private RectF innerRectf;
    private Paint innerPaint;
    private float innerResult;
    private float mWidth;
    private ObjectAnimator mWidthAnimator;
    private ObjectAnimator mInnerWidthAnimator;
    private Bitmap bitmap;
    private Rect rect;
    private RectF bitmapRectf;
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
    //    private static final int State_Draw_Path_Arc = 4;
    private int currentState;
    private int measuredHeight;
    private int measuredWidth;
    MaterialProgressDrawable drawable;


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
    public void setCirRadius(int cirRadius) {
        this.cirRadius = cirRadius;
        Log.i("默认半径：", cirRadius + "");

    }

    public int getCirRadius() {
        return cirRadius;
    }

    private int cirRadius;
    private Paint circlePaint;
    private Paint wavePaint;
    //    private float result;
    private Paint containPaint;
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
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent ev) {
                switch (ev.getAction()) {
                    case MotionEvent.ACTION_DOWN://有事件先拦截再说！！
                        startX = (int) ev.getRawX();
                        startY = (int) ev.getRawY();
                        rectF.set(measuredWidth - 100, measuredHeight - 100, measuredWidth + 100
                                , measuredHeight + 100);
                        break;
                    case MotionEvent.ACTION_MOVE://移动的时候
                        int endY = (int) ev.getRawY();
                        int dy = endY - startY;
                        if (dy < 0) {
                            dy = 0;
                        }
//                        if (dy > 360) {
//                            dy = 360;
//                        }
//                        if (dy!=angle && dy >= -360 && dy <= 360) {
                        if (dy != angle && dy >= 0 && dy <= 360) {
                            rectF.set(measuredWidth - 100, measuredHeight - 100, measuredWidth + 100
                                    , measuredHeight + 100);
                            currentState = State_Draw_Arc;
                            angle = dy;
                            Log.i("TAG", "onTouchEvent: " + angle);
                            invalidate();
                            break;
                        }
                        if (dy != paths && dy >= 400 && dy <= 760) {
                            currentState = State_Draw_Path;

                            paths = dy - 360;
                            if (dy > 560) {//360+半径？？
                                float precent = dy * 1.0f / 760;
                                rectF.set(measuredWidth - (100 - precent * 40), measuredHeight - 100, measuredWidth + (100 - precent * 40)
                                        , measuredHeight + 100);

                            }
                            invalidate();
                            Log.i("TAG", "onTouchEvent->pathsssss:: " + paths);
                            break;
                        }

                        if (dy >= 780) {
                            currentState = State_Draw_Circle;
                            invalidate();
                            Log.i("TAG", "onTouchEvent->pathsssss:: " + paths);
                            break;
                        }


                        break;
                }
                return true;
            }
        });
        rectF = new RectF();
        post(new Runnable() {


            @Override
            public void run() {
                measuredWidth = getMeasuredWidth() / 2;
                measuredHeight = getMeasuredHeight() / 2 - 200;
                Log.e("TAG111", "run: " + measuredWidth);
                rectF.set(measuredWidth - 100, measuredHeight - 100, measuredWidth + 100
                        , measuredHeight + 100);
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

        innerRectf = new RectF();
        bitmapRectf = new RectF();
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        rect = new Rect(0, 0, bitmap.getWidth() + 100, bitmap.getHeight() + 100);


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


    }

    //    注意：onDraw每次被调用时canvas画布都是一个干净的、空白的、透明的，他不会记录以前画上去的
    @Override
    protected void onDraw(Canvas canvas) {
//        setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//        angle++;
//        // 最小半径
//        paint.setStrokeWidth(mWidth);
//        innerPaint.setStrokeWidth(innerResult + innerResult);
//        paint.setAlpha(80);
        //动画的
//        canvas.drawArc(rectF, 0, angle1 == null ? 0 : angle1, true, paint);
        switch (currentState) {
            case State_Draw_Arc:
//                canvas.drawArc(rectF, 0, angle, true, paint);
//                canvas.drawArc(rectF, 0, angle, false, circlePaint);
//                float inset = (int) mStrokeInset / 2 * mArrowScale;
                canvas.rotate(angle, rectF.centerX(), rectF.centerY());
                mArrow.reset();
                float arrowX = (float) (Math.cos(180 / Math.PI * angle) * (rectF.centerX() - rectF.left));
                float arrowY = (float) (Math.sin(180 / Math.PI * angle) * (rectF.centerX() - rectF.left));
                mArrow.moveTo(arrowX, arrowY);
                mArrow.lineTo(rectF.centerX()+20, rectF.centerY());
                mArrow.lineTo(rectF.centerX()+20, rectF.centerY()+20);
//                mArrow.offset(x - inset, y);
                mArrow.close();
                canvas.drawPath(mArrow, circlePaint);
                break;
            case State_Draw_Path:
                path.reset();
                path.moveTo((float) (rectF.centerX() - Math.cos(180 / Math.PI * 30) * (rectF.centerX() - rectF.left)), (float) (rectF.centerY() - Math.sin(180 / Math.PI * 30) * (rectF.centerY() - rectF.top)));
                path.quadTo(rectF.centerX(), rectF.centerY() + paths, (float) (rectF.centerX() + Math.cos(180 / Math.PI * 30) * (rectF.centerX() - rectF.left)), (float) (rectF.centerY() - Math.sin(180 / Math.PI * 30) * (rectF.centerY() - rectF.top)));
                canvas.drawPath(path, paint);
                canvas.drawArc(rectF, 0, 360, true, paint);
                break;
            case State_Draw_Circle:
                canvas.drawCircle(rectF.centerX(), rectF.centerY() + 250, 80, paint);
                break;
        }


//        // 最小圆形
//        canvas.drawCircle(width / 2, height / 2, minRadius, circlePaint);
//        circlePaint.setAlpha(120);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//
//    }

}
