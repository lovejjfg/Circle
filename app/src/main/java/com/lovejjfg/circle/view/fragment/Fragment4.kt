package com.lovejjfg.circle.view.fragment

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.TargetApi
import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.support.v4.view.animation.LinearOutSlowInInterpolator
import android.support.v7.widget.ViewUtils
import android.util.Log
import android.view.Display
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView

import com.lovejjfg.circle.anim.MorphingAnimator
import com.lovejjfg.circle.listener.OnAnimationEndListener
import com.lovejjfg.circle.R
import com.lovejjfg.circle.widget.CircleView
import com.lovejjfg.circle.anim.drawable.StrokeGradientDrawable

import butterknife.Bind
import butterknife.ButterKnife
import butterknife.OnClick

/**
 * Created by Joe on 2016-06-09
 * Email: lovejjfg@gmail.com
 */
class Fragment4 : Fragment() {
    private var drawable: StrokeGradientDrawable? = null
    private var gradientDrawable: GradientDrawable? = null
    private var density: Float = 0.toFloat()
    private var flag: Boolean = false
    private var display: Display? = null
    private var topMargin: Int = 0
    private var linearOutSlowInInterpolator: LinearOutSlowInInterpolator? = null
    private var isStart: Boolean = false

    @Bind(R.id.bt)
    internal var mBt: TextView? = null
    @Bind(R.id.circle)
    internal var circleView: CircleView? = null
    @Bind(R.id.scrim)
    internal var scrim: View? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.fragment_tab4, container, false)
        ButterKnife.bind(this, rootView)
        linearOutSlowInInterpolator = LinearOutSlowInInterpolator()
        drawable = createDrawable(Color.RED)
        gradientDrawable = drawable!!.gradientDrawable
        density = resources.displayMetrics.density

        mBt!!.setBackgroundDrawable(gradientDrawable)
        mBt!!.setText(R.string.animator_over)
        scrim!!.setOnTouchListener { v, event ->
            val y = event.y.toInt()
            val x = event.x.toInt()
            startBackGround(x, y)
            false
        }

        val wm = activity.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        display = wm.defaultDisplay
        val layoutParams = mBt!!.layoutParams as ViewGroup.MarginLayoutParams
        topMargin = layoutParams.topMargin
        return rootView
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun startBackGround(centerX: Int, centerY: Int) {
        val showScrim = AnimatorSet()
        showScrim.playTogether(
                ViewAnimationUtils.createCircularReveal(
                        scrim,
                        centerX,
                        centerY,
                        0f,
                        Math.hypot(display!!.width.toDouble(), display!!.height.toDouble()).toFloat()),
                ObjectAnimator.ofArgb(
                        scrim, "backgroundColor",
                        Color.GRAY,
                        activity.resources.getColor(R.color.gray_1),
                        activity.resources.getColor(R.color.transWhite)))
        showScrim.duration = 1000L
        showScrim.interpolator = linearOutSlowInInterpolator
        showScrim.start()
    }

    private fun createDrawable(color: Int): StrokeGradientDrawable {
        val drawable = this.resources.getDrawable(R.drawable.shape_button).mutate() as GradientDrawable
        drawable.setColor(color)
        drawable.setCornerRadius(54f)
        val strokeGradientDrawable = StrokeGradientDrawable(drawable)
        strokeGradientDrawable.strokeColor = color
        strokeGradientDrawable.strokeWidth = 15
        return strokeGradientDrawable
    }

    @OnClick(R.id.bt)
    internal fun onClick() {
        // TODO: 2016-06-12 完善Drawable的相关逻辑！
        //        mBt.setText(null);
        startBackGround(display!!.width / 2, mBt!!.measuredHeight / 2 + topMargin)
        val bounds = mBt!!.background.bounds
        Log.i("TAG", "onAnimationUpdate:left " + bounds.left + ";;;Right:" + bounds.right)

        circleView!!.start()
        if (!flag) {
            flag = true
            //减小
            //            animator.setRight(mBt.getMeasuredWidth());
            //            animator.setLeft(0);
            //            animator.setoffset(-2 * 36);
            val animator = MorphingAnimator(mBt, drawable)
            animator.setDuration(2000)
            animator.setFromColor(Color.RED)
            animator.setToColor(Color.BLUE)
            mBt!!.text = "开始"
            //        animator.setPadding(25);
            animator.setListener {
                //                mBt.setVisibility(View.VISIBLE);
                Log.i("TAG", "onAnimationEnd: 动画结束！！")
                isStart = false
                //                    mBt.setText(flag ? "开始" : getResources().getString(R.string.animator_over));
            }
            animator.setFromWidth(mBt!!.width)
            animator.setToWidth(mBt!!.height)
            animator.setFromCornerRadius(5 * density)
            animator.setToCornerRadius(40 * density)
            animator.start()
            //            mBt.setText("始");
        } else {
            //增大
            flag = false
            //            animator.setRight(mBt.getMeasuredWidth());
            //            animator.setLeft(0);
            //            animator.setoffset(2 * 36);
            val animator = MorphingAnimator(mBt, drawable)
            animator.setDuration(2000)
            animator.setFromColor(Color.RED)
            animator.setToColor(Color.BLUE)
            //        animator.setPadding(25);
            animator.setListener {
                //                mBt.setVisibility(View.VISIBLE);
                Log.i("TAG", "onAnimationEnd: 动画结束！！")
                //                    mBt.setText(flag ? "开始" : getResources().getString(R.string.animator_over));
            }
            mBt!!.text = "动画结束"
            animator.setFromCornerRadius(40 * density)
            animator.setToCornerRadius(5 * density)
            animator.setFromWidth(mBt!!.height)
            animator.setToWidth(mBt!!.width)
            //            mBt.setText("动画结束动画结束");
            animator.start()
        }
        //        if (dWidth != 0) {
        //        }
        //        circleView.start();


    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        fun newInstance(sectionNumber: Int): Fragment4 {
            val fragment = Fragment4()
            val args = Bundle()
            args.putInt(ARG_SECTION_NUMBER, sectionNumber)
            fragment.arguments = args
            return fragment
        }
    }

}
