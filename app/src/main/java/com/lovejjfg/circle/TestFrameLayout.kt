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
class TestFrameLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = -1) :
    FrameLayout(
        context,
        attrs,
        defStyleAttr
    ) {
    init {
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        arrayListOf("xx1", "xx2", "xxxx3").forEach {
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val mode = MeasureSpec.getMode(widthMeasureSpec)
        when (mode) {
            MeasureSpec.EXACTLY -> println("TestFrameLayout mode:EXACTLY")
            MeasureSpec.AT_MOST -> println("TestFrameLayout mode:AT_MOST")
            MeasureSpec.UNSPECIFIED -> println("TestFrameLayout mode:UNSPECIFIED")
            else -> println("mode:unknown")
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
    }

    override fun dispatchDraw(canvas: Canvas?) {
        println("TestFrameLayout dispatchDraw canvas:$canvas")
        super.dispatchDraw(canvas)
    }

    override fun draw(canvas: Canvas?) {
        println("TestFrameLayout draw canvas:$canvas")
        super.draw(canvas)
    }

    override fun onDraw(canvas: Canvas?) {
        println("TestFrameLayout onDraw canvas:$canvas")
        super.onDraw(canvas)
    }

    override fun addView(child: View?, index: Int, params: android.view.ViewGroup.LayoutParams?) {
        println("TestFrameLayout addView:$child")
        super.addView(child, index, params)
    }

    override fun addView(child: View?) {
        println("TestFrameLayout addView222:$child")
        super.addView(child)
    }

    override fun addView(child: View?, index: Int) {
        println("TestFrameLayout addView22:$child")
        super.addView(child, index)
    }

    override fun addView(child: View?, params: android.view.ViewGroup.LayoutParams?) {
        println("TestFrameLayout addView2:$child")
        super.addView(child, params)
    }

    override fun onAttachedToWindow() {
        println("TestFrameLayout onAttachedToWindow")
        super.onAttachedToWindow()
    }
}
