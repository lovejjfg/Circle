package com.lovejjfg.circle.view.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.lovejjfg.circle.anim.MorphingAnimator;
import com.lovejjfg.circle.listener.OnAnimationEndListener;
import com.lovejjfg.circle.R;
import com.lovejjfg.circle.widget.CircleView;
import com.lovejjfg.circle.anim.drawable.StrokeGradientDrawable;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Joe on 2016-06-09
 * Email: lovejjfg@gmail.com
 */
public class Fragment4 extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private StrokeGradientDrawable drawable;
    private float density;
    private boolean flag;
    private Display display;
    private int topMargin;
    private LinearOutSlowInInterpolator linearOutSlowInInterpolator;
    private boolean isStart;


    public Fragment4() {
    }

    @BindView(R.id.bt)
    TextView mBt;
    @BindView(R.id.circle)
    CircleView circleView;
    @BindView(R.id.scrim)
    View scrim;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Fragment4 newInstance(int sectionNumber) {
        Fragment4 fragment = new Fragment4();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab4, container, false);
        ButterKnife.bind(this, rootView);
        linearOutSlowInInterpolator = new LinearOutSlowInInterpolator();
        drawable = createDrawable(Color.RED);
        GradientDrawable gradientDrawable = drawable.getGradientDrawable();
        density = getResources().getDisplayMetrics().density;

        mBt.setBackgroundDrawable(gradientDrawable);
        mBt.setText(R.string.animator_over);
        scrim.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int y = (int) event.getY();
                int x = (int) event.getX();
                startBackGround(x, y);
                return false;
            }
        });

        WindowManager wm = (WindowManager)getActivity().getSystemService(Context.WINDOW_SERVICE);
        display = wm.getDefaultDisplay();
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) mBt.getLayoutParams();
        topMargin = layoutParams.topMargin;
        return rootView;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void startBackGround(int centerX,int centerY) {
        AnimatorSet showScrim = new AnimatorSet();
        showScrim.playTogether(
                ViewAnimationUtils.createCircularReveal(
                        scrim,
                        centerX,
                        centerY,
                        0,
                        (float) Math.hypot(display.getWidth(), display.getHeight())),
                ObjectAnimator.ofArgb(
                        scrim, "backgroundColor",
                        Color.GRAY,
                        getActivity().getResources().getColor(R.color.gray_1),
                        getActivity().getResources().getColor(R.color.transWhite)));
        showScrim.setDuration(1000L);
        showScrim.setInterpolator(linearOutSlowInInterpolator);
        showScrim.start();
    }

    private StrokeGradientDrawable createDrawable(int color) {
        GradientDrawable drawable = (GradientDrawable) this.getResources().getDrawable(R.drawable.shape_button).mutate();
        drawable.setColor(color);
        drawable.setCornerRadius(54);
        StrokeGradientDrawable strokeGradientDrawable = new StrokeGradientDrawable(drawable);
        strokeGradientDrawable.setStrokeColor(color);
        strokeGradientDrawable.setStrokeWidth(15);
        return strokeGradientDrawable;
    }

    @OnClick(R.id.bt)
    void onClick() {
        // TODO: 2016-06-12 完善Drawable的相关逻辑！
//        mBt.setText(null);
        startBackGround(display.getWidth() / 2, mBt.getMeasuredHeight() / 2+topMargin);
        Rect bounds = mBt.getBackground().getBounds();
        Log.i("TAG", "onAnimationUpdate:left " + bounds.left + ";;;Right:" + bounds.right);

        circleView.start();
        if (!flag) {
            flag = true;
            //减小
//            animator.setRight(mBt.getMeasuredWidth());
//            animator.setLeft(0);
//            animator.setoffset(-2 * 36);
            MorphingAnimator animator = new MorphingAnimator(mBt, drawable);
            animator.setDuration(2000);
            animator.setFromColor(Color.RED);
            animator.setToColor(Color.BLUE);
            mBt.setText("开始");
//        animator.setPadding(25);
            animator.setListener(new OnAnimationEndListener() {
                @Override
                public void onAnimationEnd() {
//                mBt.setVisibility(View.VISIBLE);
                    Log.i("TAG", "onAnimationEnd: 动画结束！！");
                    isStart = false;
//                    mBt.setText(flag ? "开始" : getResources().getString(R.string.animator_over));

                }
            });
            animator.setFromWidth(mBt.getWidth());
            animator.setToWidth(mBt.getHeight());
            animator.setFromCornerRadius(5 * density);
            animator.setToCornerRadius(40 * density);
            animator.start();
//            mBt.setText("始");
        } else {
            //增大
            flag = false;
//            animator.setRight(mBt.getMeasuredWidth());
//            animator.setLeft(0);
//            animator.setoffset(2 * 36);
            MorphingAnimator animator = new MorphingAnimator(mBt, drawable);
            animator.setDuration(2000);
            animator.setFromColor(Color.RED);
            animator.setToColor(Color.BLUE);
//        animator.setPadding(25);
            animator.setListener(new OnAnimationEndListener() {
                @Override
                public void onAnimationEnd() {
//                mBt.setVisibility(View.VISIBLE);
                    Log.i("TAG", "onAnimationEnd: 动画结束！！");
//                    mBt.setText(flag ? "开始" : getResources().getString(R.string.animator_over));

                }
            });
            mBt.setText("动画结束");
            animator.setFromCornerRadius(40 * density);
            animator.setToCornerRadius(5 * density);
            animator.setFromWidth(mBt.getHeight());
            animator.setToWidth(mBt.getWidth());
//            mBt.setText("动画结束动画结束");
            animator.start();
        }
//        if (dWidth != 0) {
//        }
//        circleView.start();


    }

}
