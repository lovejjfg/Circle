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

import com.lovejjfg.circle.listener.SimpleAnimatorListener;
import com.lovejjfg.circle.R;

import java.util.ArrayList;

/**
 * Created by Joe on 2016-06-14
 * Email: lovejjfg@gmail.com
 */
public class PathTextView extends View {
    private static final String TAG = PathTextView.class.getSimpleName();

    private static String TEST = "这就是一个测试 哎哟不错哦";
    //    private static int[] COLOR = {Color.BLUE, Color.RED, Color.GRAY, Color.GREEN, Color.BLUE};
    private DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator();//减速插补器
    private LinearInterpolator linearInterpolator = new LinearInterpolator();//加速插补器
    private LinearOutSlowInInterpolator linearOutSlowInInterpolator = new LinearOutSlowInInterpolator();
    private FastOutSlowInInterpolator fastOutSlowInInterpolator = new FastOutSlowInInterpolator();
    private BounceInterpolator bounceInterpolator = new BounceInterpolator();//反弹插补器
    private Path path;
    private float textWidth;

    public static final int Default = 11;//默认
    public static final int Bounce = 12;//反弹
    public static final int Oblique = 13;//倾斜飞出

    private int Mode = Bounce;
    private float defaultRadio = 20;
    private float defaultX = 0;//文字的默认x
    private float defaultY = 0;//文字的默认y
    private Paint textPaint;
    private float currentOffset = -1;//文字偏移量
    private Paint paint;
    private float textHeight;
    private Paint bitmapPaint;
    private float radioCenterX;
    private float radioCenterY;
    private int currentHeight;
    private float dXXX;//x方向的偏移量


    private float amplitude = 100.0f;//振幅
    private Bitmap currentBitmap;
    private int currentIndex;
    private float fraction;
    private boolean isUp;
    private boolean left;
    private ObjectAnimator distanceDownAnimator;//图片下降的动画
    private ObjectAnimator distanceUpAnimator;//图片上升的动画
    private ObjectAnimator offsetAnimator;//偏移动画
    private float density;

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
        density = getContext().getResources().getDisplayMetrics().density;
        final ArrayList<Bitmap> bitmaps = new ArrayList<>();
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.fruit1));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.fruit2));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.fruit3));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.fruit4));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.fruit5));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.fruit6));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.fruit7));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.fruit8));
        currentBitmap = bitmaps.get(0);
        radioCenterY = currentBitmap.getHeight() / 2.0f;
        path = new Path();
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(sp2px(getContext(), 20));
        textPaint.setColor(Color.RED);
        textPaint.setStrokeCap(Paint.Cap.ROUND);
        textPaint.setTextAlign(Paint.Align.LEFT);

        textWidth = textPaint.measureText(TEST);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setColor(Color.GREEN);

        bitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bitmapPaint.setStyle(Paint.Style.FILL);
        bitmapPaint.setStrokeWidth(5);
        bitmapPaint.setColor(Color.GREEN);

        offsetAnimator = ObjectAnimator.ofFloat(this, mOffsetProperty, 0);
        offsetAnimator.setDuration(300);
        offsetAnimator.setInterpolator(bounceInterpolator);

        distanceDownAnimator = ObjectAnimator.ofFloat(this, mDistanceProperty, 0);
        distanceDownAnimator.setInterpolator(linearInterpolator);
        distanceDownAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                fraction = animation.getAnimatedFraction();
            }
        });
        distanceDownAnimator.addListener(new SimpleAnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                isUp = false;
                dXXX = 0;
                if (++currentIndex >= bitmaps.size()) {
                    currentIndex = 0;
                }
                currentBitmap = bitmaps.get(currentIndex);
                radioCenterY = currentBitmap.getHeight() / 2.0f;
//
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                offsetAnimator.cancel();
                offsetAnimator.setDuration(200);
                offsetAnimator.setFloatValues(defaultY, defaultY + amplitude, defaultY);
                offsetAnimator.start();

                distanceUpAnimator.start();

            }
        });
        distanceDownAnimator.start();

        distanceUpAnimator = ObjectAnimator.ofFloat(this, mDistanceProperty, 0);
//        distanceUpAnimator.setRepeatCount(Integer.MAX_VALUE);
//        distanceUpAnimator.setRepeatMode(ValueAnimator.INFINITE);
        distanceUpAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                fraction = animation.getAnimatedFraction();
                // TODO: 2016-06-15 这里有点儿取巧，需要优化。！！
                    float f = (Float) animation.getAnimatedValue();
                if (Mode == Bounce && (int) (defaultY - textHeight + density) == (int) (f) && !offsetAnimator.isRunning()) {
//                    offsetAnimator
                    dXXX = (left ? radioCenterX * fraction : radioCenterX * fraction * -1.0f);
                    offsetAnimator.cancel();
                    offsetAnimator.setDuration(100);
                    offsetAnimator.setFloatValues(defaultY, defaultY + 50, defaultY);
                    offsetAnimator.start();
                    Log.i(TAG, "onAnimationUpdate: YY" + (int) f);
                    Log.i(TAG, "onAnimationUpdate: XX" + (left ? radioCenterX * fraction : radioCenterX * fraction * -1.0f));
                }
            }
        });
        distanceUpAnimator.addListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isUp = true;
                left = !left;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                distanceDownAnimator.start();
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.i(TAG, "onSizeChanged: size改变了！！！！");
        super.onSizeChanged(w, h, oldw, oldh);
        currentHeight = h;
        initAnim(h);
    }

    private void initAnim(int currentHeight) {
//        distanceUpAnimator.cancel();
//        distanceDownAnimator.cancel();
        if (textHeight == 0) {
            textHeight = textPaint.getFontMetrics().bottom - textPaint.getFontMetrics().top;
        }
        defaultY = currentHeight - textHeight; //(h+textHeight*0.5f) / 2.0f;
        offsetAnimator.setFloatValues(defaultY, defaultY + amplitude, defaultY);
        radioCenterY = currentBitmap.getHeight() / 2.0f;//初始化默认高度
        distanceDownAnimator.setFloatValues(radioCenterY, defaultY - textHeight);//到文字的顶部就好
        Log.i(TAG, "initAnim: radioCenterY:" + radioCenterY + ";;TO:" + (defaultY));

//        distanceDownAnimator.start();
        switch (Mode) {
            case Default:
                distanceDownAnimator.setDuration(1000);
                distanceUpAnimator.setDuration(1000);
                distanceUpAnimator.setInterpolator(decelerateInterpolator);
                distanceUpAnimator.setFloatValues(defaultY - textHeight, radioCenterY);
                break;
            case Oblique:
                distanceDownAnimator.setDuration(500);

                distanceUpAnimator.setDuration(1000);
                distanceUpAnimator.setInterpolator(decelerateInterpolator);
                distanceUpAnimator.setFloatValues(defaultY - textHeight, radioCenterY + currentBitmap.getHeight());//到达不了最高处
                break;
            case Bounce:
                // TODO: 2016-06-14 完成第二次的振幅效果
                distanceDownAnimator.setDuration(1000);

                distanceUpAnimator.setDuration(2000);
                distanceUpAnimator.setInterpolator(linearOutSlowInInterpolator);
                // TODO: 2016-06-15 这里要---1
                distanceUpAnimator.setFloatValues(defaultY - textHeight , defaultY - 4 * textHeight, (int) (defaultY - textHeight + density * 2f), defaultY - 2 * textHeight);
                break;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        textHeight = textPaint.getFontMetrics().bottom - textPaint.getFontMetrics().top;
        widthMeasureSpec = MeasureSpec.makeMeasureSpec((int) textPaint.measureText(TEST), MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float dX = (left ? radioCenterX * fraction : radioCenterX * fraction * -1.0f);//相对于中心点 0 的水平偏移量
        path.reset();
        path.moveTo(defaultX, defaultY);
        radioCenterX = (defaultX + textWidth) / 2.0f;
//        radioCenterX = dXXX == 0 ? (defaultX + textWidth) / 2.0f : dXXX;
        if (currentOffset != -1) {
            path.quadTo(dXXX == 0 ? radioCenterX : radioCenterX + dXXX, currentOffset, textWidth, defaultY);
        } else {
            path.lineTo(textWidth, defaultY);
        }
//        canvas.drawPoint(dXXX == 0 ? radioCenterX : -dXXX, currentOffset, paint);//测试使用！

        canvas.drawTextOnPath(TEST, path, 0, 0, textPaint);
//        canvas.drawPath(path, paint);//测试使用
        if (currentBitmap != null) {
            if (!isUp) {
                canvas.rotate(360 * fraction, radioCenterX, radioCenterY);
                canvas.drawBitmap(currentBitmap, radioCenterX - currentBitmap.getWidth() / 2.0f, radioCenterY - currentBitmap.getHeight() / 2.0f, null);
                return;
            }
            switch (Mode) {
                case Default:
                    canvas.rotate(360 * fraction, radioCenterX, radioCenterY);
                    canvas.drawBitmap(currentBitmap, radioCenterX - currentBitmap.getWidth() / 2.0f, radioCenterY - currentBitmap.getHeight() / 2.0f, null);
                    break;
                case Bounce:
                case Oblique:
                    canvas.rotate(360 * fraction, radioCenterX + dX, radioCenterY);
                    canvas.translate(dX, 0);
                    int i1 = blendColors(Color.WHITE, Color.TRANSPARENT, fraction);
                    bitmapPaint.setColor(i1);
                    canvas.drawBitmap(currentBitmap, radioCenterX - currentBitmap.getWidth() / 2.0f, radioCenterY - currentBitmap.getHeight() / 2.0f, bitmapPaint);
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

    public static float sp2px(Context context, float sp) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return sp * scaledDensity;
    }

    public void setMode(int mode) {
        if (Mode != mode) {
            this.Mode = mode;
            initAnim(currentHeight);
        }
    }

    @SuppressWarnings("unused")
    public float getAmplitude() {
        return amplitude;
    }

    @SuppressWarnings("unused")
    public void setAmplitude(float amplitude) {
        this.amplitude = amplitude;
    }

    public static String getText() {
        return TEST;
    }

    public static void setText(String TEST) {
        PathTextView.TEST = TEST;
    }

}
