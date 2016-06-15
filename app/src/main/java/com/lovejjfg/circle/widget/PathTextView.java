package com.lovejjfg.circle.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Property;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.lovejjfg.circle.R;

import java.util.ArrayList;

/**
 * Created by Joe on 2016-06-14
 * Email: zhangjun166@pingan.com.cn
 */
public class PathTextView extends View {
    private static String TEST = "这就是一个测试哎哟不错哦哦测试！！哎哟";
    private static int[] COLOR = {Color.BLUE, Color.RED, Color.GRAY, Color.GREEN, Color.BLUE};
    DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator();
    LinearInterpolator linearInterpolator = new LinearInterpolator();
    LinearOutSlowInInterpolator linearOutSlowInInterpolator = new LinearOutSlowInInterpolator();
    FastOutSlowInInterpolator fastOutSlowInInterpolator = new FastOutSlowInInterpolator ();
    private Path path;
    private float textWidth;

    private static final int Default = 11;
    private static final int Oblique = 12;
    private int Mode = Default;
    private float defaultRadio = 20;
    private float defaultX = 0;
    private float defaultY = 0;
    private Paint textPaint;
    private float currentOffset = -1;
    private Paint paint;
    private ObjectAnimator offsetAnimator;
    private float textHeight;
    private Paint cilclePaint;
    private float radioCenterX;
    private float radioCenterY = defaultRadio;
    private ObjectAnimator distanceDownAnimator;

    public float getAmplitude() {
        return amplitude;
    }

    public void setAmplitude(float amplitude) {
        this.amplitude = amplitude;
    }

    private float amplitude = 30.0f;//振幅
    private BounceInterpolator bounceInterpolator;
    private Bitmap currentBitmap;
    private int currentIndex;
    private float fraction;
    private boolean isUp;
    private boolean left;
    private ObjectAnimator distanceUpAnimator;

    public PathTextView(Context context) {
        this(context, null);
    }

    public PathTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        final ArrayList<Bitmap> bitmaps = new ArrayList<>(4);
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.fruit1));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.fruit2));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.fruit3));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.fruit4));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.fruit5));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.fruit6));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.fruit7));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.fruit8));
        currentBitmap = bitmaps.get(0);
        path = new Path();
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(30);
        textPaint.setColor(Color.RED);
        textPaint.setStrokeCap(Paint.Cap.ROUND);
        textPaint.setTextAlign(Paint.Align.LEFT);

        textWidth = textPaint.measureText(TEST);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(5);
        paint.setColor(Color.GREEN);

        cilclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        cilclePaint.setStyle(Paint.Style.FILL);
        cilclePaint.setStrokeWidth(5);
        cilclePaint.setColor(Color.GREEN);

        textWidth = textPaint.measureText(TEST);
        offsetAnimator = ObjectAnimator.ofFloat(this, mOffsetProperty, 0);
        offsetAnimator.setDuration(300);
        bounceInterpolator = new BounceInterpolator();
        offsetAnimator.setInterpolator(bounceInterpolator);

        distanceDownAnimator = ObjectAnimator.ofFloat(this, mDistanceProperty, 0);
        distanceDownAnimator.setDuration(1000);
        distanceDownAnimator.setInterpolator(linearInterpolator);
//        distanceDownAnimator.setRepeatCount(Integer.MAX_VALUE);
//        distanceDownAnimator.setRepeatMode(ValueAnimator.INFINITE);
        distanceDownAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                fraction = animation.getAnimatedFraction();
            }
        });
        distanceDownAnimator.addListener(new Animator.AnimatorListener() {
            private int count;

            @Override
            public void onAnimationStart(Animator animation) {
                Log.i("TAG", "onAnimationEnd: 开始了！！");
                isUp = false;
                if (++currentIndex >= bitmaps.size()) {
                    currentIndex = 0;
                }
                currentBitmap = bitmaps.get(currentIndex);
//                offsetAnimator.cancel();
//                distanceUpAnimator.cancel();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.i("TAG", "onAnimationEnd: 结束了！！");
                distanceUpAnimator.start();
                offsetAnimator.start();

            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.i("TAG", "onAnimationEnd: 取消了！！");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        distanceDownAnimator.start();

        distanceUpAnimator = ObjectAnimator.ofFloat(this, mDistanceProperty, 0);
        distanceUpAnimator.setDuration(Mode == Default ? 1000 : 3000);
        // TODO: 2016-06-14 完成第二次的振幅效果
        distanceUpAnimator.setInterpolator(Mode == Default ? decelerateInterpolator : linearOutSlowInInterpolator);
//        distanceUpAnimator.setRepeatCount(Integer.MAX_VALUE);
//        distanceUpAnimator.setRepeatMode(ValueAnimator.INFINITE);
        distanceUpAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                fraction = animation.getAnimatedFraction();
            }
        });
        distanceUpAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isUp = true;
                left = !left;
                Log.i("TAG", "onAnimationEnd: 开始了！！");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                distanceDownAnimator.start();
                Log.i("TAG", "onAnimationEnd: 结束了！！");
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.i("TAG", "onAnimationEnd: 取消了！！");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
//        distanceUpAnimator.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        textHeight = textPaint.getFontMetrics().bottom - textPaint.getFontMetrics().top;
        defaultY = h - textHeight; //(h+textHeight*0.5f) / 2.0f;
        offsetAnimator.setFloatValues(defaultY, defaultY + amplitude, defaultY);
        distanceDownAnimator.setFloatValues(radioCenterY, defaultY - textHeight);
        if (Mode == Default) {
            distanceUpAnimator.setFloatValues(defaultY - textHeight, radioCenterY);
        } else {
            distanceUpAnimator.setFloatValues(defaultY - textHeight, defaultY - 4 * textHeight, defaultY - textHeight, defaultY - 2 * textHeight);

        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        textHeight = textPaint.getFontMetrics().bottom - textPaint.getFontMetrics().top;
        widthMeasureSpec = MeasureSpec.makeMeasureSpec((int) textPaint.measureText(TEST), MeasureSpec.EXACTLY);
//        MeasureSpec.getMode(heightMeasureSpec)==MeasureSpec.EXACTLY
//        heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) (textHeight*2), MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        path.reset();
        path.moveTo(defaultX, defaultY);
        radioCenterX = (defaultX + textWidth) / 2.0f;
        if (currentOffset != -1) {
            path.quadTo(radioCenterX, currentOffset, textWidth, defaultY);
        } else {
            path.lineTo(textWidth, defaultY);
        }

//        canvas.drawPoint(radioCenterX, currentOffset, paint);
//        canvas.drawPath(path, paint);
        canvas.drawTextOnPath(TEST, path, 0, 0, textPaint);

//        canvas.drawCircle(radioCenterX, radioCenterY, defaultRadio, cilclePaint);

        if (currentBitmap != null) {
            if (!isUp) {
                canvas.rotate(isUp ? 360 * fraction : 360 + 360 * fraction, radioCenterX, radioCenterY);
                canvas.drawBitmap(currentBitmap, radioCenterX - currentBitmap.getWidth() / 2.0f, radioCenterY - currentBitmap.getHeight() / 2.0f, null);
                return;
            }
            switch (Mode) {
                case Default:
                    canvas.rotate(isUp ? 360 * fraction : 360 + 360 * fraction, radioCenterX, radioCenterY);
                    canvas.drawBitmap(currentBitmap, radioCenterX - currentBitmap.getWidth() / 2.0f, radioCenterY - currentBitmap.getHeight() / 2.0f, null);
                    break;
                case Oblique:
                    float x = left ? radioCenterX * fraction : radioCenterX * fraction * -1.0f;
                    canvas.translate(x, 0);
                    // TODO: 2016-06-14 又是旋转又是平移的中心点坐标
//                    canvas.rotate(360 * fraction, radioCenterX+x*0.5f, radioCenterY);
                    int i1 = blendColors(Color.WHITE, Color.TRANSPARENT, fraction);
                    cilclePaint.setColor(i1);

                    canvas.drawBitmap(currentBitmap, radioCenterX - currentBitmap.getWidth() / 2.0f, radioCenterY - currentBitmap.getHeight() / 2.0f, cilclePaint);

                    break;

            }

        }

    }

    private Property<PathTextView, Float> mOffsetProperty = new Property<PathTextView, Float>(Float.class, "offset") {
        @Override
        public Float get(PathTextView object) {
            return object.getCurrentOffset();
        }

        @Override
        public void set(PathTextView object, Float value) {

            object.setCurrentOffset(value);
        }
    };
    private Property<PathTextView, Float> mDistanceProperty = new Property<PathTextView, Float>(Float.class, "distance") {
        @Override
        public Float get(PathTextView object) {
            return object.getCurrentDistance();
        }

        @Override
        public void set(PathTextView object, Float value) {
            object.setCurrentDistance(value);

        }
    };

    public Float getCurrentOffset() {
        return currentOffset;
    }

    public void setCurrentOffset(Float currentOffset) {
        this.currentOffset = currentOffset;
        invalidate();
    }

    public void setCurrentDistance(Float currentDistance) {
        this.radioCenterY = currentDistance;
        invalidate();
    }

    public Float getCurrentDistance() {
        return radioCenterY;
    }


    public static
    @ColorInt
    int blendColors(@ColorInt int color1,
                    @ColorInt int color2,
                    @FloatRange(from = 0f, to = 1f) float ratio) {
        final float inverseRatio = 1f - ratio;
        float a = (Color.alpha(color1) * inverseRatio) + (Color.alpha(color2) * ratio);
        float r = (Color.red(color1) * inverseRatio) + (Color.red(color2) * ratio);
        float g = (Color.green(color1) * inverseRatio) + (Color.green(color2) * ratio);
        float b = (Color.blue(color1) * inverseRatio) + (Color.blue(color2) * ratio);
        return Color.argb((int) a, (int) r, (int) g, (int) b);
    }
}
