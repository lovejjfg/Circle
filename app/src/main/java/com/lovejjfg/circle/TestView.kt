package com.lovejjfg.circle

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView

/**
 * Created by joe on 2019/3/30.
 * Email: lovejjfg@gmail.com
 */
class TestView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = -1) :
    TextView
        (
        context,
        attrs,
        defStyleAttr
    ) {

    init {

        val a = context.obtainStyledAttributes(
            attrs, R.styleable.AppCompatTextView, defStyleAttr, -1
        )
//        a.getColor(R.styleable.AppCompatTextView., -1)
//
//        a.recycle()
//
//        if (true) {
//            label297: {
//
//            }
        }
//    }
}
