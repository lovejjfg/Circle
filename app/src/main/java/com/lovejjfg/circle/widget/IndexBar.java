package com.lovejjfg.circle.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.lovejjfg.circle.R;

public class IndexBar extends View {

    private Paint mPaint;
    private static final String[] LETTERS = new String[]{"A", "G", "H",
            "I", "J", "K" };
    private static final int[] STATE_FOCUSED = new int[]{android.R.attr.state_focused};
    private int mCellWidth;
    private float mCellHeight;
    private Rect mRect;
    private boolean pressed;
    private int normalColor;
    private int selecColor;
    private float dimension;


    public IndexBar(Context context) {
        this(context, null);
    }

    public IndexBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndexBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.IndexBar, defStyleAttr, 0);
        normalColor = a.getColor(R.styleable.IndexBar_normalColor, Color.WHITE);
        selecColor = a.getColor(R.styleable.IndexBar_selecColor, Color.BLUE);
        dimension = a.getDimensionPixelSize(R.styleable.IndexBar_indexSize, sp2px(14));
        a.recycle();
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(normalColor);
        mPaint.setTextSize(dimension);
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mRect = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < LETTERS.length; i++) {
            String text = LETTERS[i];
            float textWidth = mPaint.measureText(text);
            mPaint.getTextBounds(text, 0, text.length(), mRect);
            float textHeight = mRect.height();
            float x = mCellWidth * 0.5f - textWidth * 0.5f;
            float y = mCellHeight * 0.5f + textHeight * 0.5f + mCellHeight * i;
            mPaint.setColor(mIndex == i ? selecColor : normalColor);
            canvas.drawText(text, x, y, mPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int mHeight = getMeasuredHeight();
        mCellWidth = getMeasuredWidth();
        mCellHeight = mHeight * 1.0f / 26;
    }

    private int mIndex = -1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float y;
        int currentIndex;
        invalidate();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                refreshState(true);
                y = event.getY();
                // 根据触摸到的位置, 获取索引
                currentIndex = (int) (y / mCellHeight);
                if (currentIndex != mIndex) {
                    if (mOnLetterChangeListener != null) {
                        if (currentIndex < LETTERS.length) {
                            mOnLetterChangeListener.onLetterChange(LETTERS[currentIndex]);
                        }
                    }
                    mIndex = currentIndex;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                y = event.getY();
                currentIndex = (int) (y / mCellHeight);
                if (currentIndex != mIndex) {
                    if (mOnLetterChangeListener != null) {
                        if (currentIndex < LETTERS.length) {
                            mOnLetterChangeListener.onLetterChange(LETTERS[currentIndex]);
                        }
                    }
                    mIndex = currentIndex;
                }
                break;
            case MotionEvent.ACTION_UP:
                refreshState(false);
                mIndex = -1;
                break;

            default:
                break;
        }
        return true;
    }

    private void refreshState(boolean state) {
        if (pressed != state) {
            pressed = state;
            refreshDrawableState();
        }
    }


    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        int[] states = super.onCreateDrawableState(extraSpace + 1);
        if (pressed) {
            mergeDrawableStates(states, STATE_FOCUSED);
        }
        return states;
    }

    public int dp2px(int dip) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    public int sp2px(float sp) {
        float scaledDensity = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * scaledDensity + 0.5f);
    }

    public interface OnLetterChangeListener {
        void onLetterChange(String letter);
    }

    private OnLetterChangeListener mOnLetterChangeListener;

    @SuppressWarnings("unused")
    public OnLetterChangeListener getOnLetterChangeListener() {
        return mOnLetterChangeListener;
    }

    public void setOnLetterChangeListener(OnLetterChangeListener onLetterChangeListener) {
        mOnLetterChangeListener = onLetterChangeListener;
    }

}
