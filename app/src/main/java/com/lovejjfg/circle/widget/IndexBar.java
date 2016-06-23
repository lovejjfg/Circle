package com.lovejjfg.circle.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.lovejjfg.circle.R;

import java.util.List;

/**
 * Created by Joe on 2016-06-20
 * Email: lovejjfg@163.com
 */
public class IndexBar extends View {

    private Paint mPaint;
    private int mHeight;

    @SuppressWarnings("unused")
    @Nullable
    public List<String> getLetters() {
        return letters;
    }
    @SuppressWarnings("unused")
    public void setLetters(@Nullable List<String> letters) {
        if (letters == null) {
            setVisibility(GONE);
            return;
        }
        this.letters = letters;
        mHeight = getMeasuredHeight();
        mCellWidth = getMeasuredWidth();
        mCellHeight = mHeight * 1.0f / 26;
        beginY = (mHeight - mCellHeight * letters.size()) * 0.5f;
        invalidate();
    }
    @Nullable
    private List<String> letters;
    private static final int[] STATE_FOCUSED = new int[]{android.R.attr.state_focused};
    private int mCellWidth;
    private float mCellHeight;
    private Rect mRect;
    private boolean pressed;
    private int normalColor;
    private int selecColor;
    private float dimension;
    private float beginY;


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
        normalColor = a.getColor(R.styleable.IndexBar_normalColor, Color.GRAY);
        selecColor = a.getColor(R.styleable.IndexBar_selecColor, Color.BLUE);
        dimension = a.getDimensionPixelSize(R.styleable.IndexBar_indexSize, sp2px(14));
        a.recycle();
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(normalColor);
        mPaint.setTextSize(dimension);
        mPaint.setTypeface(Typeface.DEFAULT);
        mRect = new Rect();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (letters != null) {
            for (int i = 0; i < letters.size(); i++) {
                String text = letters.get(i);
                float textWidth = mPaint.measureText(text);
                mPaint.getTextBounds(text, 0, text.length(), mRect);
                float textHeight = mRect.height();
                float x = mCellWidth * 0.5f - textWidth * 0.5f;
                float y = mCellHeight * 0.5f + textHeight * 0.5f + mCellHeight * i + beginY;
                mPaint.setColor(mIndex == i ? selecColor : normalColor);
                canvas.drawText(text, x, y, mPaint);
//                mPaint.setColor(Color.RED);
//                mPaint.setStrokeWidth(20);
//                canvas.drawPoint(x, y, mPaint);
//                mPaint.setColor(Color.GREEN);
//                canvas.drawPoint(x, getMeasuredHeight() * 0.5f, mPaint);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = getMeasuredHeight();
        mCellWidth = getMeasuredWidth();
        mCellHeight = mHeight * 1.0f / 26;
        if (letters != null) {
            beginY = (mHeight - mCellHeight * letters.size()) * 0.5f;
        }

    }

    private int mIndex = -1;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float y;
        int currentIndex;
        invalidate();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("TAG", "onTouchEvent:Down ");
                getParent().requestDisallowInterceptTouchEvent(true);
                refreshState(true);
                y = event.getY();
                checkIndex(y);
                break;
            case MotionEvent.ACTION_MOVE:
                y = event.getY();
                checkIndex(y);
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

    private void checkIndex(float y) {
        int currentIndex;
        if (y < beginY) {
            return;
        }
        currentIndex = (int) ((y - beginY) / mCellHeight);
        if (currentIndex != mIndex) {
            if (mOnLetterChangeListener != null) {
                if (letters != null && currentIndex < letters.size()) {
                    mOnLetterChangeListener.onLetterChange(letters.get(currentIndex));
                }
            }
            mIndex = currentIndex;
        }
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
