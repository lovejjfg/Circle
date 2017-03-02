package com.lovejjfg.circle.widget;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.lovejjfg.circle.R;

import java.util.ArrayList;

/**
 * Created by Joe on 2016/4/3.
 * Email lovejjfg@gmail.com
 */
public class DragBubbleView extends View {

    private static final String TAG = DragBubbleView.class.getSimpleName();
    private int startX;
    private int startY;
    private static int DEFAULT_RADIO = 30;
    private int ORIGIN_RADIO = DEFAULT_RADIO;
    private int dragRadio = ORIGIN_RADIO;
    private int CIRCLEX;
    private int CIRCLEY;
    private int MIN_RADIO = (int) (ORIGIN_RADIO * 0.4);//最小半径
    private int MAXDISTANCE = (int) (MIN_RADIO * 13);
    private Path path;
    private double angle;
    private boolean flag;
    private ValueAnimator valueX;
    private ValueAnimator valueY;
    private AnimatorSet animSetXY;
    private boolean fillDraw = true;
    private ArrayList<Bitmap> bubbles;
    private Paint bitmapPaint;

    public boolean isShowCircle() {
        return showCircle;
    }

    public void setShowCircle(boolean showCircle) {
        this.showCircle = showCircle;
        invalidate();
    }

    private boolean showCircle = true;
//    private boolean isUp;

    private final static int STATE_IDLE = 1;//静止的状态
    private final static int STATE_DRAG_NORMAL = 2;//正在拖拽的状态
    private final static int STATE_DRAG_BREAK = 3;//断裂后的拖拽状态
    private final static int STATE_UP_BREAK = 4;//放手后的爆裂的状态
    private final static int STATE_UP_BACK = 5;//放手后的没有断裂的返回的状态
    private final static int STATE_UP_DRAG_BREAK_BACK = 6;//拖拽断裂又返回的状态
    private int CurrentState = STATE_IDLE;
    private int currentPos = 0;

    private Paint paint;

    public DragBubbleView(Context context) {
        super(context);
        initView();
    }

    public DragBubbleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public DragBubbleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();

    }

    public void reset() {
        CurrentState = STATE_IDLE;
        currentPos = 0;
        invalidate();
    }


    private void initView() {
        path = new Path();
        animSetXY = new AnimatorSet();
        valueX = ValueAnimator.ofInt(startX, CIRCLEX);
        valueY = ValueAnimator.ofInt(startY, CIRCLEY);
        animSetXY.playTogether(valueX, valueY);
        valueX.setDuration(500);
        valueY.setDuration(500);
        valueX.setInterpolator(new OvershootInterpolator());
        valueY.setInterpolator(new OvershootInterpolator());
        valueX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                startX = (int) animation.getAnimatedValue();
                Log.e(TAG, "onAnimationUpdate-startX: " + startX);
                invalidate();
            }

        });
        valueY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                startY = (int) animation.getAnimatedValue();
                Log.e(TAG, "onAnimationUpdate-startY: " + startY);
                invalidate();

            }
        });


        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        paint.setStyle(fillDraw ? Paint.Style.FILL : Paint.Style.STROKE);
//        paint.setAntiAlias(true);
//        paint.setStrokeWidth(10);
        paint.setColor(Color.RED);
//        paint.setAlpha(50);
//        paint.setStrokeCap(Paint.Cap.SQUARE);

        Paint circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        circlePaint.setStyle(Paint.Style.STROKE);
//        paint.setAntiAlias(true);
        circlePaint.setStrokeWidth(10);
        //noinspection deprecation
        circlePaint.setColor(getResources().getColor(R.color.transWhite));
//        paint.setAlpha(50);
//        paint.setStrokeCap(Paint.Cap.SQUARE);

        bitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bitmapPaint.setStyle(Paint.Style.FILL);

        bubbles = new ArrayList<>(5);
        bubbles.add(BitmapFactory.decodeResource(getResources(), R.mipmap.bubble_one));
        bubbles.add(BitmapFactory.decodeResource(getResources(), R.mipmap.bubble_two));
        bubbles.add(BitmapFactory.decodeResource(getResources(), R.mipmap.bubble_three));
        bubbles.add(BitmapFactory.decodeResource(getResources(), R.mipmap.bubble_four));
        bubbles.add(BitmapFactory.decodeResource(getResources(), R.mipmap.bubble_five));

    }

    private void updatePath() {
        int dy = Math.abs(CIRCLEY - startY);
        int dx = Math.abs(CIRCLEX - startX);

        double dis = Math.sqrt(dy * dy + dx * dx);
        if (dis <= MAXDISTANCE) {//增加的情况，原始半径减小
            if (CurrentState == STATE_DRAG_BREAK || CurrentState == STATE_UP_DRAG_BREAK_BACK) {
                CurrentState = STATE_UP_DRAG_BREAK_BACK;
            } else {
                CurrentState = STATE_DRAG_NORMAL;
            }
            ORIGIN_RADIO = (int) (DEFAULT_RADIO - (dis / MAXDISTANCE) * (DEFAULT_RADIO - MIN_RADIO));
            Log.e(TAG, "distance: " + (int) ((1 - dis / MAXDISTANCE) * MIN_RADIO));
            Log.i(TAG, "distance: " + ORIGIN_RADIO);
        } else {
            CurrentState = STATE_DRAG_BREAK;
        }
//        distance = dis;
        flag = (startY - CIRCLEY) * (startX - CIRCLEX) <= 0;
        Log.i("TAG", "updatePath: " + flag);
        angle = Math.atan(dy * 1.0 / dx);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        switch (CurrentState) {
            case STATE_IDLE://空闲状态，就画默认的圆
                if (showCircle) {
                    canvas.drawCircle(CIRCLEX, CIRCLEY, dragRadio, paint);//默认的
                }
                break;
            case STATE_UP_BACK://执行返回的动画
            case STATE_DRAG_NORMAL://拖拽状态 画贝塞尔曲线和两个圆
                path.reset();
                if (flag) {
                    //第一个点
                    path.moveTo((float) (CIRCLEX - Math.sin(angle) * ORIGIN_RADIO), (float) (CIRCLEY - Math.cos(angle) * ORIGIN_RADIO));

                    path.quadTo((float) ((startX + CIRCLEX) * 0.5), (float) ((startY + CIRCLEY) * 0.5), (float) (startX - Math.sin(angle) * dragRadio), (float) (startY - Math.cos(angle) * dragRadio));
                    path.lineTo((float) (startX + Math.sin(angle) * dragRadio), (float) (startY + Math.cos(angle) * dragRadio));

                    path.quadTo((float) ((startX + CIRCLEX) * 0.5), (float) ((startY + CIRCLEY) * 0.5), (float) (CIRCLEX + Math.sin(angle) * ORIGIN_RADIO), (float) (CIRCLEY + Math.cos(angle) * ORIGIN_RADIO));
                    path.close();
                    canvas.drawPath(path, paint);
                } else {
                    //第一个点
                    path.moveTo((float) (CIRCLEX - Math.sin(angle) * ORIGIN_RADIO), (float) (CIRCLEY + Math.cos(angle) * ORIGIN_RADIO));

                    path.quadTo((float) ((startX + CIRCLEX) * 0.5), (float) ((startY + CIRCLEY) * 0.5), (float) (startX - Math.sin(angle) * dragRadio), (float) (startY + Math.cos(angle) * dragRadio));
                    path.lineTo((float) (startX + Math.sin(angle) * dragRadio), (float) (startY - Math.cos(angle) * dragRadio));

                    path.quadTo((float) ((startX + CIRCLEX) * 0.5), (float) ((startY + CIRCLEY) * 0.5), (float) (CIRCLEX + Math.sin(angle) * ORIGIN_RADIO), (float) (CIRCLEY - Math.cos(angle) * ORIGIN_RADIO));
                    path.close();
                    canvas.drawPath(path, paint);
                }
                if (showCircle) {
                    canvas.drawCircle(CIRCLEX, CIRCLEY, ORIGIN_RADIO, paint);//默认的
                    canvas.drawCircle(startX == 0 ? CIRCLEX : startX, startY == 0 ? CIRCLEY : startY, dragRadio, paint);//拖拽的
                }
                break;

            case STATE_DRAG_BREAK://拖拽到了上限，画拖拽的圆:
            case STATE_UP_DRAG_BREAK_BACK:
                if (showCircle) {
                    canvas.drawCircle(startX == 0 ? CIRCLEX : startX, startY == 0 ? CIRCLEY : startY, dragRadio, paint);//拖拽的
                }
                break;

            case STATE_UP_BREAK://画出爆裂的效果
                if (currentPos < 0 || currentPos >= bubbles.size()) {
                    currentPos = 0;
                    CurrentState = STATE_IDLE;
                    return;
                }
                Bitmap currentBitmap = bubbles.get(currentPos);
                canvas.drawBitmap(currentBitmap, startX - currentBitmap.getWidth() / 2.0f, startY - currentBitmap.getHeight() / 2.0f, bitmapPaint);
                currentPos++;
                postInvalidateDelayed(50);
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!isEnabled()) {
            return true;
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN://有事件先拦截再说！！
                getParent().requestDisallowInterceptTouchEvent(true);
                CurrentState = STATE_IDLE;
                animSetXY.cancel();
                startX = (int) ev.getX();
                startY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE://移动的时候
                startX = (int) ev.getX();
                startY = (int) ev.getY();

                updatePath();
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (CurrentState == STATE_DRAG_NORMAL) {
                    CurrentState = STATE_UP_BACK;
                    valueX.setIntValues(startX, CIRCLEX);
                    valueY.setIntValues(startY, CIRCLEY);
                    animSetXY.start();
                } else if (CurrentState == STATE_DRAG_BREAK) {
                    CurrentState = STATE_UP_BREAK;
                    invalidate();
                } else {
                    CurrentState = STATE_UP_DRAG_BREAK_BACK;
                    valueX.setIntValues(startX, CIRCLEX);
                    valueY.setIntValues(startY, CIRCLEY);
                    animSetXY.start();
                }
                break;

        }

        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(DEFAULT_RADIO * 2, MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(DEFAULT_RADIO * 2, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

        super.onLayout(changed, left, top, right, bottom);

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        CIRCLEX = (int) ((w) * 0.5 + 0.5);
        CIRCLEY = (int) ((h) * 0.5 + 0.5);
        CurrentState = STATE_IDLE;
    }

    public void setFillDraw(boolean fillDraw) {
        this.fillDraw = fillDraw;
        paint.setStyle(fillDraw ? Paint.Style.FILL : Paint.Style.STROKE);
        invalidate();
    }

    public boolean getFillDraw() {
        return this.fillDraw;
    }

    public void setOriginR(int progress) {
        ORIGIN_RADIO = progress;
        DEFAULT_RADIO = progress;
        dragRadio = progress;
        MIN_RADIO = (int) (ORIGIN_RADIO * 0.4);
        MAXDISTANCE = MIN_RADIO * 13;
        requestLayout();
        invalidate();
    }

}

