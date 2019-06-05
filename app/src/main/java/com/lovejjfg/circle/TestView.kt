package com.lovejjfg.circle

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver

/**
 * Created by joe on 2019/3/30.
 * Email: lovejjfg@gmail.com
 */
class TestView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = -1) :
    View(
        context,
        attrs,
        defStyleAttr
    ) {
    var testCrash = false

    init {

        viewTreeObserver.addOnPreDrawListener { false }

//        post {  }
//        setLayerType(LAYER_TYPE_SOFTWARE, null)
//        Looper.myQueue().addIdleHandler(MessageQueue.IdleHandler {
//            println("Idle....")
//            false
//        })
//        alpha = 0f
//        animate().scaleX(0.5f).scaleY(0.5f)
//            .alpha(1f)
//            .setDuration(3000).start()

//        invalidate(false)
//        animation = TranslateAnimation(0f,100f,0f,100f).apply {
//            duration = 5000
//            fillAfter = true
//        }
//        handler.looper.queue
    }

//    override fun onSetAlpha(alpha: Int): Boolean {
//        return true
//    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val mode = MeasureSpec.getMode(widthMeasureSpec)
        when (mode) {
            MeasureSpec.EXACTLY -> println("TestView mode:EXACTLY")
            MeasureSpec.AT_MOST -> println("TestView mode:AT_MOST")
            MeasureSpec.UNSPECIFIED -> println("TestView mode:UNSPECIFIED")
            else -> println("mode:unknown")
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
//        throw RuntimeException("xxxxxx")
    }

    override fun onDraw(canvas: Canvas?) {
        println("TestView onDraw canvas:$canvas")
        super.onDraw(canvas)
        if (testCrash) {
            throw RuntimeException("xxxxxx")
        }
//        if (canvas is DisplayListCanvas) {
//            println("hahaahahahhah")
//        }
    }

    override fun draw(canvas: Canvas?) {
        println("TestView draw canvas:$canvas")
        super.draw(canvas)
    }

    override fun dispatchDraw(canvas: Canvas?) {
        println("TestView dispatchDraw dispatchDraw:$canvas")
        super.dispatchDraw(canvas)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        println("width:$$width;")
    }
}
