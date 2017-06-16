package com.lovejjfg.circle.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.lovejjfg.circle.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * Created by Joe on 2017/3/30.
 * Email lovejjfg@gmail.com
 */

public class TimeIndicatorView extends View {

    private Paint paint;
    private int centerX;
    private int centerY;
    private RectF mRectF;
    private Bitmap bitmap;
    private Paint fillPaint;
    private int cx;
    private int cy;
    private float halfWidth;
    private float halfHeight;
    private Path mPath;
    private PathMeasure pathMeasure;
    private Point mPoint;
    private float[] mCoords;
    private int total = 6;
    private static final int START = 210;
    private static final int TOTAL_ANGLE = 120;
    private int firtAngle = 1;
    private Path mPath2;

    /**
     * Mon - Monday
     * 2.Tue - Tuesday
     * 3.Wed - Wednesday
     * 4.Thur- Thursday
     * 5.Fri- Friday
     * 6.Sat- Saturday
     * 日.Sun - Sunday
     */

    public TimeIndicatorView(Context context) {
        this(context, null);
    }

    public TimeIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public TimeIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mRectF = new RectF();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //画蓝色矩形区域
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        paint.setStyle(Paint.Style.STROKE); //空心
        paint.setStrokeWidth(3);

        mPath = new Path();
        mPath2 = new Path();
        pathMeasure = new PathMeasure();
        mCoords = new float[]{0f, 0f};

        fillPaint = new Paint(paint);


        //设置画虚线，如果之后不再使用虚线，调用paint.setPathEffect(null);
        PathEffect effects = new DashPathEffect(new float[]{10, 10, 10, 10}, 1);
        paint.setPathEffect(effects);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.artboard);
        halfWidth = bitmap.getWidth() * 0.5f;
        halfHeight = bitmap.getHeight() * 0.5f;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        centerX = (int) (getMeasuredWidth() * 0.5f);
        centerY = getMeasuredHeight();
        mRectF.set(-60, getPaddingTop(), getMeasuredWidth()+60, getMeasuredWidth() + getPaddingTop());
        cx = (int) Math.abs(Math.sin(Math.toRadians(210)) * (centerX - halfWidth));
        cy = (int) Math.cos(Math.toRadians(210) * (centerX - halfHeight) + getPaddingTop());
//                cy = (int) (((int) Math.abs()) * (centerX - halfHeight)) + getPaddingTop());
        // TODO: 2017/3/30 修正x轴的坐标体系 以及y周
        Log.e("TAG", "onMeasure YY: " + cy);
        Log.e("TAG", "onMeasure X: " + cx);

    }

    /*
     * X坐标=a + Math.sin(2*Math.PI / 360) * r ；Y坐标=b + Math.cos(2*Math.PI / 360) * r ；
     */
    @Override
    protected void onDraw(Canvas canvas) {
//        canvas.drawCircle(centerX, centerY, centerX, paint);
        mPath.reset();
        mPath2.reset();
        mPath.addArc(mRectF, START, firtAngle);
        mPath2.addArc(mRectF, START + firtAngle + 1, TOTAL_ANGLE - firtAngle);
        pathMeasure.setPath(mPath, false);
//        canvas.drawArc(mRectF, 210, 60, false, paint);
//        canvas.drawArc(mRectF, 270, 60, false, fillPaint);
        canvas.drawPath(mPath, fillPaint);
        canvas.drawPath(mPath2, paint);
        pathMeasure.getPosTan(pathMeasure.getLength(), mCoords, null);
        canvas.drawBitmap(bitmap, mCoords[0] - halfWidth, mCoords[1] - halfHeight, paint);
//        for (int i = 0; i <= total; i++) {
//            pathMeasure.getPosTan(i * pathMeasure.getLength() / total, mCoords, null);
//            canvas.drawBitmap(bitmap, mCoords[0] - halfWidth, mCoords[1] - halfHeight, paint);
//        }
        if (firtAngle < TOTAL_ANGLE) {
            firtAngle += 10;
            postInvalidateDelayed(2000);
        }
        super.onDraw(canvas);
    }
//1483221600000   06:00:00
//1483264799000   17:59:59
//result 43199 (0~43199) 十二小时


//1483264800000   18:00:00
//1483307999000   05:59:59
//1483304399000   04:59:59  -3600  //39600  //39599
//1483308000000   06:00:00
//    白天-6点的时间就是结果
//    晚上-6点的时间 大于零-43199  小于零+43199
//x/y>1?x/y-1:x/y<1?x/y+1

    /*
    白天模式直接获取早上的六点就好

     */
    private static void method() {
        try {
            // 获取系统年月日
            int hour = 6;
            int min = 0;
            int second = 0;
            SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date now = new Date();
            String time = myFmt.format(now);
            time = time + " " + hour + ":" + min + ":" + second;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            Date date;
            date = sdf.parse(time);
            // 获取指定时间的毫秒值
            long longDate = date.getTime();
            long speTime = System.currentTimeMillis();
            System.out.println("系统时间：" + speTime);
            System.out.println("指定时间：" + longDate);
            long dt = speTime - longDate;
            System.out.println("差值：" + dt);
            System.out.println("百分比：" + dt / 43199000.f);
            System.out.println();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
