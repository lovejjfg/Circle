package com.lovejjfg.circle

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.widget.FrameLayout

/**
 * Created by joe on 2019/3/30.
 * Email: lovejjfg@gmail.com
 */
class TestLinearLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = -1) :
    FrameLayout(
        context,
        attrs,
        defStyleAttr
    ) {
    init {
//        setLayerType(LAYER_TYPE_SOFTWARE, null)
        arrayListOf("xx1", "xx2", "xxxx3").forEach {
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val mode = MeasureSpec.getMode(widthMeasureSpec)
        when (mode) {
            MeasureSpec.EXACTLY -> println("TestLinearLayout mode:EXACTLY")
            MeasureSpec.AT_MOST -> println("TestLinearLayout mode:AT_MOST")
            MeasureSpec.UNSPECIFIED -> println("TestLinearLayout mode:UNSPECIFIED")
            else -> println("mode:unknown")
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
    }

    override fun dispatchDraw(canvas: Canvas?) {
        println("TestLinearLayout dispatchDraw canvas:$canvas")
        super.dispatchDraw(canvas)
    }

    override fun draw(canvas: Canvas?) {
        println("TestLinearLayout draw canvas:$canvas")
        super.draw(canvas)
    }

    override fun onDraw(canvas: Canvas?) {
        println("TestLinearLayout onDraw canvas:$canvas")
        super.onDraw(canvas)
    }

    override fun addView(child: View?, index: Int, params: android.view.ViewGroup.LayoutParams?) {
        println("TestLinearLayout addView:$child")
        super.addView(child, index, params)
    }

    override fun addView(child: View?) {
        println("TestLinearLayout addView222:$child")
        super.addView(child)
    }

    override fun addView(child: View?, index: Int) {
        println("TestLinearLayout addView22:$child")
        super.addView(child, index)
    }

    override fun addView(child: View?, params: android.view.ViewGroup.LayoutParams?) {
        println("TestLinearLayout addView2:$child")
        super.addView(child, params)
    }

    override fun onAttachedToWindow() {
        println("TestLinearLayout onAttachedToWindow")
        super.onAttachedToWindow()
    }
}
