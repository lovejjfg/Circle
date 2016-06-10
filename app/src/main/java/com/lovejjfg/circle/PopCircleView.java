package com.lovejjfg.circle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Joe on 2016/4/3.
 * Email lovejjfg@gmail.com
 */
public class PopCircleView extends View {

    private static final String TAG = PopCircleView.class.getSimpleName();
    private int startX;
    private int startY;
    private int movedX;
    private int movedY;
    private int DefaultRADIO = 50;
    private int ORIGINRADIO = DefaultRADIO;
    private int DRAGRADIO = ORIGINRADIO;
    private int MINRADIO = (int) (ORIGINRADIO * 0.6);//最小半径
    private int circleX = 400;
    private int circleY = 400;

    private float percent;
    private int MAXDISTANCE = (int) (MINRADIO * 10);

    private Path path;
    private double angle;
    private boolean flag;
    private int currentDy;
    private double distance;


    //设置默认半径
    public void setCirRadius(int cirRadius) {
        this.ORIGINRADIO = cirRadius;
        Log.i("默认半径：", cirRadius + "");

    }

    public int getCirRadius() {
        return ORIGINRADIO;
    }

    private int cirRadius;
    private Paint circlePaint;
    private Paint paint;

    public PopCircleView(Context context) {
        super(context);
        initView();
    }

    public PopCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public PopCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();

    }

    private float firstCount;
    private boolean isAdd = true;
    private boolean isReduce;

    private void initView() {
        path = new Path();
        setOnTouchListener(new OnTouchListener() {

                               private int endX;
                               private int endY;
                               private float mMoveCircleY;

                               @Override
                               public boolean onTouch(View v, MotionEvent ev) {
                                   switch (ev.getAction()) {
                                       case MotionEvent.ACTION_DOWN://有事件先拦截再说！！
                                           startX = (int) ev.getX();
                                           startY = (int) ev.getY();
                                           movedX = (int) ev.getX();
                                           movedY = (int) ev.getY();
                                           break;
                                       case MotionEvent.ACTION_MOVE://移动的时候
//                                           int endY = (int) ev.getRawY();
//                                           angle = endY - startY;
                                           startX = (int) ev.getX();
                                           startY = (int) ev.getY();

                                           endY = (int) ev.getY();
                                           endX = (int) ev.getX();
                                           int dy = endY - movedY;
                                           int dx = endX - movedX;
                                           movedY = endY;
                                           movedX = endX;
//                                           currentDy+=dy;

                                           updatePath();
//                                           double DISTANCE = Math.sqrt(dy * dy + dx * dx);
//                                           if (ORIGINRADIO - DISTANCE >= MINRADIO && ORIGINRADIO - DISTANCE  <= DefaultRADIO) {
////                                               Log.e("TAG", "currentDy: " + currentDy);
//                                               ORIGINRADIO -= DISTANCE;
//
//                                           }

                                           invalidate();
                                           break;
                                   }

                                   return true;
                               }
                           }

        );
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        paint.setStyle(Paint.Style.FILL);
//        paint.setAntiAlias(true);
//        paint.setStrokeWidth(10);
        paint.setColor(Color.RED);
//        paint.setAlpha(50);
//        paint.setStrokeCap(Paint.Cap.SQUARE);

        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        circlePaint.setStyle(Paint.Style.FILL);
//        paint.setAntiAlias(true);
        circlePaint.setStrokeWidth(ORIGINRADIO / 2);
        circlePaint.setColor(Color.GREEN);
//        paint.setAlpha(50);
//        paint.setStrokeCap(Paint.Cap.SQUARE);


    }

    private void updatePath() {
        int dy = Math.abs(circleY - startY);
        int dx = Math.abs(circleX - startX);

        double dis = Math.sqrt(dy * dy + dx * dx);
        if (dis <= MAXDISTANCE) {//增加的情况，原始半径减小
            ORIGINRADIO = (int) (DefaultRADIO - (dis / MAXDISTANCE) * (DefaultRADIO - MINRADIO));
            Log.e(TAG, "distance: " + (int) ((1 - dis / MAXDISTANCE) * MINRADIO));
            Log.i(TAG, "distance: " + ORIGINRADIO);
        }
//        distance = dis;
        flag = (circleY - startY) * (circleX - startX) <= 0;
        Log.i("TAG", "updatePath: " + flag);
        angle = Math.atan(dy * 1.0 / dx);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (startY != 0 || startX != 0) {
            path.reset();
            if (flag) {
                //第一个点
                path.moveTo((float) (circleX - Math.sin(angle) * ORIGINRADIO), (float) (circleY - Math.cos(angle) * ORIGINRADIO));

                path.quadTo((float) ((startX + circleX) * 0.5), (float) ((startY + circleY) * 0.5), (float) (startX - Math.sin(angle) * DRAGRADIO), (float) (startY - Math.cos(angle) * DRAGRADIO));
                path.lineTo((float) (startX + Math.sin(angle) * DRAGRADIO), (float) (startY + Math.cos(angle) * DRAGRADIO));

                path.quadTo((float) ((startX + circleX) * 0.5), (float) ((startY + circleY) * 0.5), (float) (circleX + Math.sin(angle) * ORIGINRADIO), (float) (circleY + Math.cos(angle) * ORIGINRADIO));
                path.close();
                canvas.drawPath(path, paint);
            } else {
                //第一个点
                path.moveTo((float) (circleX - Math.sin(angle) * ORIGINRADIO), (float) (circleY + Math.cos(angle) * ORIGINRADIO));

                path.quadTo((float) ((startX + circleX) * 0.5), (float) ((startY + circleY) * 0.5), (float) (startX - Math.sin(angle) * DRAGRADIO), (float) (startY + Math.cos(angle) * DRAGRADIO));
                path.lineTo((float) (startX + Math.sin(angle) * DRAGRADIO), (float) (startY - Math.cos(angle) * DRAGRADIO));

                path.quadTo((float) ((startX + circleX) * 0.5), (float) ((startY + circleY) * 0.5), (float) (circleX + Math.sin(angle) * ORIGINRADIO), (float) (circleY - Math.cos(angle) * ORIGINRADIO));
                path.close();
                canvas.drawPath(path, paint);
            }

        }

        canvas.drawCircle(circleX, circleY, ORIGINRADIO, paint);//默认的
        canvas.drawPoint((float) ((startX + circleX) * 0.5), (float) ((startY + circleY) * 0.5), circlePaint);//默认的
        canvas.drawCircle(startX == 0 ? circleX : startX, startY == 0 ? circleY : startY, DRAGRADIO, paint);//拖拽的

    }

}
