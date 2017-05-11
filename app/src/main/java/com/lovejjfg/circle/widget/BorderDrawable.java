package com.lovejjfg.circle.widget;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Joe on 2017/5/9.
 * Email lovejjfg@gmail.com
 */

public class BorderDrawable extends Drawable {

    private Paint mPaint;
    private Paint pathPaint;
    private Path mArrow;
    private Rect mRect;
    private Rect mInnerRect;
    public static final int STATE_CHECKED = 1;
    public static final int STATE_NORMAL = 0;
    public static final int STATE_UNABLE = 2;
    private boolean showInner = true;

    private int borderColor = 0xffff0000;//未选中描边的颜色和INNER中的描边颜色
    private int mainColor = 0xff00ff00;//选中的描边以及里面的方块的颜色。
    private int currentState;
    private Paint mInnerPaint;
    private Paint mBorderPaint;

    public BorderDrawable(int state, int mainColor) {
        this(state, mainColor, false);
    }

    public BorderDrawable(int state, int mainColor, boolean showInner) {
        currentState = state;
        update(mainColor, showInner);
    }

    public void update(int mainColor, boolean showInner) {
        this.showInner = showInner;
        this.mainColor = mainColor;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mainColor);
        mPaint.setStrokeWidth(3);
        //设置画虚线，如果之后不再使用虚线，调用paint.setPathEffect(null);
        pathPaint = new Paint(mPaint);
        pathPaint.setStyle(Paint.Style.FILL);
        mArrow = new Path();

        mInnerPaint = new Paint();
        mBorderPaint = new Paint();
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(3);
        mBorderPaint.setColor(borderColor);
        mInnerPaint.setColor(mainColor);
    }

    public void updateBorderColor() {

    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        mRect = bounds;
        int value = (int) (bounds.height() * 0.1f);
        mArrow.reset();
        mArrow.moveTo(0, 0);
        mArrow.lineTo(value, 0);
        mArrow.lineTo(0, value);
        mArrow.close();
        mInnerRect = new Rect(mRect.left + value, mRect.top + value, mRect.right - value, mRect.bottom - value);
        invalidateSelf();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        switch (currentState) {
            case STATE_CHECKED://只画边框就好
                mPaint.setColor(mainColor);
                canvas.drawRect(mRect, mPaint);
                canvas.drawPath(mArrow, pathPaint);
                break;
            case STATE_UNABLE://只画边框就好
                mPaint.setColor(borderColor);
                PathEffect effects = new DashPathEffect(new float[]{10, 10, 10, 10}, 1);
                mPaint.setPathEffect(effects);
                canvas.drawRect(mRect, mPaint);
                break;
            case STATE_NORMAL:
                mPaint.setColor(borderColor);
                canvas.drawRect(mRect, mPaint);
                break;
        }
        if (showInner) {
            canvas.drawRect(mInnerRect, mInnerPaint);
            canvas.drawRect(mInnerRect, mBorderPaint);
        }


    }

    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }
}