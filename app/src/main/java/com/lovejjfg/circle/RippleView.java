package com.lovejjfg.circle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Joe on 2016/4/3.
 * Email lovejjfg@gmail.com
 */
public class RippleView extends View {

    private RectF rectF;

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
    private float result;
    private Paint containPaint;
    private long angle;
    private Paint paint;

    public RippleView(Context context) {
        super(context);
        initView();
    }

    public RippleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public RippleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private float firstCount;
    private boolean isAdd = true;
    private boolean isReduce;
    Handler handler = new Handler() {


        public void handleMessage(android.os.Message msg) {
            if (msg.what == 1) {
                invalidate();
                angle++;
                if (angle == 360) {
                    angle = 0;
                }
                handler.sendEmptyMessageDelayed(1, 1);
            } else {
                // 判断是否达到最大，达到最大时往回缩
                if (firstCount >= 0 && isAdd) {
                    result = getInterpolation(firstCount);
                    firstCount++;

                }
                if (firstCount >= 10 || isReduce) {
                    isAdd = false;
                    isReduce = true;
                    result = getInterpolation(firstCount);
                    firstCount--;
                    if (firstCount <= 1) {
                        isAdd = true;
                        isReduce = false;
                    }
                }
                handler.sendEmptyMessageDelayed(2, 160);
            }
        }
    };

    private void initView() {
        rectF = new RectF();
        cirRadius = 200;
        circlePaint = new Paint();
        circlePaint.setColor(Color.WHITE);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setAntiAlias(true);
        circlePaint.setAlpha(50);
        wavePaint = new Paint(circlePaint);
        wavePaint.setStyle(Paint.Style.FILL);
        containPaint = new Paint(circlePaint);
        containPaint.setStrokeWidth(10);
        containPaint.setAntiAlias(true);
        containPaint.setAlpha(1);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(20);
        paint.setColor(Color.WHITE);
        paint.setAlpha(50);

        handler.sendEmptyMessage(1);
        handler.sendEmptyMessage(2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        int height = getHeight();
        int width = getWidth();
        Log.e("result:", result + "");
        angle++;
        // 最小半径
        int minRadius = (multipleRadius * cirRadius / 10);
        // 最多能扩大多少
//        float f1 = maxMul * cirRadius / 10;
        // 计算百分比距离
        float f2 =  result ;

//        // 扩散光圈效果
//        canvas.drawCircle(width / 2, height / 2, radius + f2, circlePaint);
        paint.setStrokeWidth(result+f2);
        rectF.set(width / 2 - (minRadius + f2),height / 2 - (minRadius + f2), width
                / 2 + (minRadius + f2),height / 2 + (minRadius + f2));
        canvas.drawArc(rectF, 0, 360, false, paint);
        // 最小圆形
        canvas.drawCircle(width / 2, height / 2, minRadius, circlePaint);
        circlePaint.setAlpha(120);
        // 第二层圆形
        canvas.drawCircle(width / 2, height / 2, minRadius + 20, circlePaint);
        circlePaint.setAlpha(180);
        // 第三层圆形
        canvas.drawCircle(width / 2, height / 2, minRadius + 40, circlePaint);
        // 波浪的容器
        canvas.drawCircle(width / 2, height / 2, minRadius - 5, containPaint);
        // 绘制一个扇形
        RectF rectF = new RectF(width / 2 - minRadius,height / 2 - minRadius, width
                / 2 + minRadius,height / 2 + minRadius);
        canvas.drawArc(rectF, 0, 180, false, wavePaint);
        double lineX = 0;
        double lineY = 0;
        // 根据正弦绘制波浪
        for (int i = width / 2 - minRadius; i < width / 2 + minRadius; i++) {
            lineX = i;
            lineY = 10 * Math.sin((i + angle) * Math.PI / 180) + getHeight()
                    / 2 + 40;
            canvas.drawLine((int) lineX, (int) (lineY - 70), (int) lineX + 1,
                    height / 2, wavePaint);
        }
    }

    public float getInterpolation(float input) {
        return (float) (0.5 * acceleration * input * input);
    }


}
