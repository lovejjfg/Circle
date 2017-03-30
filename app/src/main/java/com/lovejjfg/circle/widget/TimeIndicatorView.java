package com.lovejjfg.circle.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.lovejjfg.circle.R;


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

    public TimeIndicatorView(Context context) {
        this(context, null);
    }

    public TimeIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public TimeIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mRectF = new RectF();
        paint = new Paint();

        //画蓝色矩形区域
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        paint.setStyle(Paint.Style.STROKE); //空心
        paint.setStrokeWidth(3);

        fillPaint = new Paint(paint);


        //设置画虚线，如果之后不再使用虚线，调用paint.setPathEffect(null);
        PathEffect effects = new DashPathEffect(new float[]{5, 5, 5, 5}, 1);
        paint.setPathEffect(effects);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.artboard);
        halfWidth = bitmap.getWidth() * 0.5f;
        halfHeight = bitmap.getHeight() * 0.5f;
    }

    private int angle = 270;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        centerX = (int) (getMeasuredWidth() * 0.5f);
        centerY = getMeasuredHeight();
        mRectF.set(0, getPaddingTop(), getMeasuredWidth(), getMeasuredWidth() + getPaddingTop());
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
        canvas.drawArc(mRectF, 210, 60, false, paint);
        canvas.drawArc(mRectF, 270, 60, false, fillPaint);
        canvas.drawBitmap(bitmap, cx, cy, paint);
        super.onDraw(canvas);
    }
}
