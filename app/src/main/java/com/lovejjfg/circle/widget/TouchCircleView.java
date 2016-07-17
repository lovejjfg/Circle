package com.lovejjfg.circle.widget;

import android.animation.Animator;
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
import android.util.Property;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.lovejjfg.circle.anim.drawable.MaterialProgressDrawable;
import com.lovejjfg.circle.listener.SimpleAnimatorListener;

/**
 * Created by Joe on 2016/4/3.
 * Email lovejjfg@gmail.com
 */
public class TouchCircleView extends View {

    private RectF outRectF;
    private RectF innerRectf;
    private RectF secondRectf;
    private Paint innerPaint;
    private Paint paint;
    private Paint circlePaint;

    private Path path;
    private Path mArrow;
    private Path mHook;
    private Path mError;

    private ObjectAnimator mObjectAnimatorSweep;
    private ObjectAnimator mObjectAnimatorAngle;
    private ValueAnimator fractionAnimator;

    private static final Interpolator ANGLE_INTERPOLATOR = new LinearInterpolator();
    private static final Interpolator SWEEP_INTERPOLATOR = new AccelerateDecelerateInterpolator();
    private static final int ANGLE_ANIMATOR_DURATION = 1000;//转速
    private static final int SWEEP_ANIMATOR_DURATION = 800;
    private static final int DELAY_TIME = 5000;

    private float mCurrentGlobalAngleOffset;
    private float mCurrentGlobalAngle;
    private float mCurrentSweepAngle;
    private static final int MIN_SWEEP_ANGLE = 30;

    private float mStrokeInset = 5f;


    private Paint secondPain;

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
    private static final int STATE_DRAW_IDLE = 0;
    private static final int STATE_DRAW_ARC = 1;
    private static final int STATE_DRAW_PATH = 2;//大圆到小圆的渐变
    private static final int STATE_DRAW_OUT_PATH = 3;//小圆到大圆的渐变
    private static final int STATE_DRAW_CIRCLE = 4;
    private static final int STATE_DRAW_ARROW = 5;
    private static final int STATE_DRAW_PROGRESS = 6;
    private static final int STATE_DRAW_ERROR = 7;
    private static final int STATE_DRAW_SUCCESS = 8;
    private int currentState;
    private int centerY;
    private int centerX;
    private float mBorderWidth = 4;
    private float mRingCenterRadius;
    private boolean mModeAppearing = true;
    private float mArrowScale = 1.0f;
    private float fraction;
    private Paint mHookPaint;
    private boolean mRunning;
    private boolean isDrawTriangle;


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
    private int innerCirRadius = outCirRadius - 30;
    private static int ARROW_WIDTH = 20 * 2;
    private static int ARROW_HEIGHT = 10 * 2;


    private long angle;
    private long paths;

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


    private void initView() {
        setupAnimations();
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
                        startX = (int) ev.getX();
                        startY = (int) ev.getY();
                        outRectF.set(centerX - outCirRadius, 0, centerX + outCirRadius
                                , centerY + outCirRadius);
                        break;
                    case MotionEvent.ACTION_MOVE://移动的时候
                        int endY = (int) ev.getY();
                        int dy = (int) ((endY - startY) * 1.5f);
                        if (dy < 0) {
                            dy = 0;
                        }
//                        if (dy > 360) {
//                            dy = 360;
//                        }
//                        if (dy!=angle && dy >= -360 && dy <= 360) {
                        if (dy != angle && dy >= 0 && dy <= 360) {
                            currentState = STATE_DRAW_ARC;
                            resetAngle();
                            outRectF.set(centerX - outCirRadius, 0, centerX + outCirRadius
                                    , centerY + outCirRadius);
                            angle = dy;
                            Log.i("TAG", "onTouchEvent: " + angle);
                            invalidate();
                            break;
                        }
                        if (dy > 360 && dy < 460) {
                            currentState = STATE_DRAW_ARROW;
                            mCurrentGlobalAngle++;
                            mCurrentSweepAngle++;
                            invalidate();
                            break;
                        }
                        //正常顺序到达这里
                        if (dy >= 460 && dy <= 560) {
                            currentState = STATE_DRAW_PATH;
                            paths = dy - 300;
                            if (dy >= 460) {//360+半径？？
                                float precent = (dy - 460) * 1.0f / 100;
                                innerPaint.setAlpha((int) ((1 - precent) * 255));
                                outRectF.set(centerX - outCirRadius + precent * 20, 15 * precent, centerX + outCirRadius - precent * 20
                                        , centerY + outCirRadius - 10 * precent);
                            }
                            invalidate();
                            break;
                        }

                        if (dy >= 600) {
                            currentState = STATE_DRAW_CIRCLE;
                            invalidate();
                            break;
                        }


                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        if (STATE_DRAW_ARROW == currentState) {
                            currentState = STATE_DRAW_PROGRESS;
                            innerPaint.setAlpha(255);
                            start();
                            return true;
                        }
                        stop();
                        currentState = STATE_DRAW_IDLE;
                        invalidate();
                        break;
                }
                return true;
            }
        });
        initPaintPath();


    }

    private void initPaintPath() {
        circlePaint = new Paint();
        circlePaint.setColor(Color.BLUE);
        circlePaint.setStrokeWidth(10);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setAntiAlias(true);
        circlePaint.setAlpha(50);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(20);
        paint.setColor(Color.RED);
        paint.setStrokeCap(Paint.Cap.SQUARE);

        innerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        innerPaint.setStyle(Paint.Style.STROKE);
        innerPaint.setAntiAlias(true);
        innerPaint.setColor(Color.WHITE);
        innerPaint.setStrokeWidth(mBorderWidth);


        mHookPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHookPaint.setStyle(Paint.Style.STROKE);
        mHookPaint.setStrokeCap(Paint.Cap.ROUND);
        mHookPaint.setStrokeWidth(mBorderWidth);
        mHookPaint.setColor(Color.WHITE);

        path = new Path();
        mArrow = new Path();
        mHook = new Path();
        mError = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int min = Math.min(w, h);
        centerX = w / 2;
        centerY = outCirRadius;
        innerRectf.set(centerX - innerCirRadius, centerY - innerCirRadius, centerX + innerCirRadius, centerY + innerCirRadius);
        mRingCenterRadius = Math.min(innerRectf.centerX() - innerRectf.left, innerRectf.centerY() - innerRectf.top) - mBorderWidth;

    }

    //    注意：onDraw每次被调用时canvas画布都是一个干净的、空白的、透明的，他不会记录以前画上去的
    @Override
    protected void onDraw(Canvas canvas) {
        //动画的
        switch (currentState) {
            case STATE_DRAW_IDLE:
                break;
            case STATE_DRAW_ARC:
                canvas.drawArc(outRectF, 0, angle, true, paint);
                canvas.drawArc(outRectF, 0, angle, false, circlePaint);
                break;
            case STATE_DRAW_ARROW:
                isDrawTriangle = true;
                canvas.drawArc(outRectF, 0, 360, true, paint);
                drawArc(canvas);
                break;
            case STATE_DRAW_PROGRESS:
                isDrawTriangle = false;
                canvas.drawArc(outRectF, 0, 360, true, paint);
                drawArc(canvas);
                break;
            case STATE_DRAW_SUCCESS:
                drawHook(canvas);
                break;
            case STATE_DRAW_ERROR:
                drawError(canvas);
                break;
            case STATE_DRAW_PATH:
                path.reset();
                path.moveTo((float) (outRectF.centerX() - Math.cos(180 / Math.PI * 30) * (outRectF.centerX() - outRectF.left)), (float) (outRectF.centerY() - Math.sin(180 / Math.PI * 30) * (outRectF.centerY() - outRectF.top)));
                path.quadTo(outRectF.centerX(), outRectF.centerY() + paths, (float) (outRectF.centerX() + Math.cos(180 / Math.PI * 30) * (outRectF.centerX() - outRectF.left)), (float) (outRectF.centerY() - Math.sin(180 / Math.PI * 30) * (outRectF.centerY() - outRectF.top)));
                canvas.drawPath(path, paint);
                canvas.drawArc(outRectF, 0, 360, true, paint);
                drawArc(canvas);
                break;
            case STATE_DRAW_CIRCLE:
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
        if (isDrawTriangle) {
            drawTriangle(canvas, startAngle, sweepAngle);
        }
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

    /**
     * 画勾
     *
     * @param canvas
     */
    private void drawHook(Canvas canvas) {
        mHook.reset();
        mHook.moveTo(innerRectf.centerX() - innerRectf.width() * 0.25f * fraction, innerRectf.centerY());
        mHook.lineTo(innerRectf.centerX() - innerRectf.width() * 0.1f * fraction, innerRectf.centerY() + innerRectf.height() * 0.18f * fraction);
        mHook.lineTo(innerRectf.centerX() + innerRectf.width() * 0.25f * fraction, innerRectf.centerY() - innerRectf.height() * 0.20f * fraction);
        canvas.drawArc(outRectF, 0, 360, false, paint);
        canvas.drawPath(mHook, mHookPaint);

    }

    /**
     * 画×
     *
     * @param canvas
     */
    private void drawError(Canvas canvas) {
        mError.reset();
        mError.moveTo(innerRectf.centerX() + innerRectf.width() * 0.2f * fraction, innerRectf.centerY() - innerRectf.height() * 0.2f * fraction);
        mError.lineTo(innerRectf.centerX() - innerRectf.width() * 0.2f * fraction, innerRectf.centerY() + innerRectf.height() * 0.2f * fraction);
        mError.moveTo(innerRectf.centerX() - innerRectf.width() * 0.2f * fraction, innerRectf.centerY() - innerRectf.height() * 0.2f * fraction);
        mError.lineTo(innerRectf.centerX() + innerRectf.width() * 0.2f * fraction, innerRectf.centerY() + innerRectf.height() * 0.2f * fraction);
        canvas.drawArc(outRectF, 0, 360, false, paint);
        canvas.drawPath(mError, mHookPaint);
    }


    private static int gradient(int color1, int color2, float p) {
        int r1 = (color1 & 0xff0000) >> 16;
        int g1 = (color1 & 0xff00) >> 8;
        int b1 = color1 & 0xff;
        int r2 = (color2 & 0xff0000) >> 16;
        int g2 = (color2 & 0xff00) >> 8;
        int b2 = color2 & 0xff;
        int newr = (int) (r2 * p + r1 * (1 - p));
        int newg = (int) (g2 * p + g1 * (1 - p));
        int newb = (int) (b2 * p + b1 * (1 - p));
        return Color.argb(255, newr, newg, newb);
    }

    private void start() {
        if (mRunning) {
            return;
        }
        mRunning = true;
        mObjectAnimatorAngle.setFloatValues(mCurrentGlobalAngle, 360f);
        mObjectAnimatorSweep.setFloatValues(mCurrentSweepAngle, 360f - MIN_SWEEP_ANGLE * 2);
//        mCurrentState = STATE_LOADING;
        mObjectAnimatorAngle.start();
        mObjectAnimatorSweep.start();
        postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, DELAY_TIME);
        invalidate();
    }

    public void finish() {
        stop();
        resetAngle();
        currentState = ((int) (Math.random() * 10)) % 2 == 1 ? STATE_DRAW_ERROR : STATE_DRAW_SUCCESS;
        if (!fractionAnimator.isRunning()) {
            fractionAnimator.start();
        }
    }

    private void resetAngle() {
        mCurrentSweepAngle = 0;
        mCurrentGlobalAngle = 0;
        mCurrentGlobalAngleOffset = 0;
    }

    private void stop() {
        if (!mRunning) {
            return;
        }
        mRunning = false;
        mObjectAnimatorAngle.cancel();
        mObjectAnimatorSweep.cancel();
        invalidate();
    }

    private void toggleAppearingMode() {
        mModeAppearing = !mModeAppearing;
        if (mModeAppearing) {
//            mCurrentColorIndex = ++mCurrentColorIndex % 4;
//            mNextColorIndex = ++mNextColorIndex % 4;
            mCurrentGlobalAngleOffset = (mCurrentGlobalAngleOffset + MIN_SWEEP_ANGLE * 2) % 360;
        }
    }
    // ////////////////////////////////////////////////////////////////////////////
    // ////////////// Animation

    private Property<TouchCircleView, Float> mAngleProperty = new Property<TouchCircleView, Float>(Float.class, "angle") {
        @Override
        public Float get(TouchCircleView object) {
            return object.getCurrentGlobalAngle();
        }

        @Override
        public void set(TouchCircleView object, Float value) {
            object.setCurrentGlobalAngle(value);
        }
    };

    private Property<TouchCircleView, Float> mSweepProperty = new Property<TouchCircleView, Float>(Float.class, "arc") {
        @Override
        public Float get(TouchCircleView object) {
            return object.getCurrentSweepAngle();
        }

        @Override
        public void set(TouchCircleView object, Float value) {
            object.setCurrentSweepAngle(value);
        }
    };

    private void setupAnimations() {
        mObjectAnimatorAngle = ObjectAnimator.ofFloat(this, mAngleProperty, mCurrentGlobalAngle, 360f);
        mObjectAnimatorAngle.setInterpolator(ANGLE_INTERPOLATOR);
        mObjectAnimatorAngle.setDuration(ANGLE_ANIMATOR_DURATION);
        mObjectAnimatorAngle.setRepeatMode(ValueAnimator.RESTART);
        mObjectAnimatorAngle.setRepeatCount(ValueAnimator.INFINITE);
        mObjectAnimatorAngle.addListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationRepeat(Animator animation) {
                mObjectAnimatorAngle.setFloatValues(360f);
            }
        });

        mObjectAnimatorSweep = ObjectAnimator.ofFloat(this, mSweepProperty, mCurrentSweepAngle, 360f - MIN_SWEEP_ANGLE * 2);
        mObjectAnimatorSweep.setInterpolator(SWEEP_INTERPOLATOR);
        mObjectAnimatorSweep.setDuration(SWEEP_ANIMATOR_DURATION);
        mObjectAnimatorSweep.setRepeatMode(ValueAnimator.RESTART);
        mObjectAnimatorSweep.setRepeatCount(ValueAnimator.INFINITE);
        mObjectAnimatorSweep.addListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationRepeat(Animator animation) {
                mObjectAnimatorSweep.setFloatValues(360f - MIN_SWEEP_ANGLE * 2);
                toggleAppearingMode();
            }
        });

        fractionAnimator = ValueAnimator.ofInt(0, 255);
        fractionAnimator.setInterpolator(ANGLE_INTERPOLATOR);
        fractionAnimator.setDuration(100);
        fractionAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                fraction = animation.getAnimatedFraction();
                mHookPaint.setAlpha((Integer) animation.getAnimatedValue());
                invalidate();
            }
        });
    }

    public void setCurrentGlobalAngle(float currentGlobalAngle) {
        mCurrentGlobalAngle = currentGlobalAngle;
        invalidate();
    }

    public float getCurrentGlobalAngle() {
        return mCurrentGlobalAngle;
    }

    public void setCurrentSweepAngle(float currentSweepAngle) {
        mCurrentSweepAngle = currentSweepAngle;
        invalidate();
    }

    public float getCurrentSweepAngle() {
        return mCurrentSweepAngle;
    }

}
