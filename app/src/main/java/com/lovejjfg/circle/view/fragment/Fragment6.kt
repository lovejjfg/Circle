package com.lovejjfg.circle.view.fragment

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup

import com.lovejjfg.circle.R
import com.lovejjfg.circle.anim.drawable.StrokeGradientDrawable
import com.lovejjfg.circle.widget.PathTextView

import butterknife.Bind
import butterknife.ButterKnife

/**
 * Created by Joe on 2016-06-09
 * Email: lovejjfg@gmail.com
 */
class Fragment6 : Fragment() {
    private val drawable: StrokeGradientDrawable? = null
    private val gradientDrawable: GradientDrawable? = null
    private val density: Float = 0.toFloat()
    private val flag: Boolean = false

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.fragment_tab6, container, false)
        ButterKnife.bind(this, rootView)
        return rootView
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
        fun newInstance(sectionNumber: Int): Fragment6 {
            val fragment = Fragment6()
            val args = Bundle()
            args.putInt(ARG_SECTION_NUMBER, sectionNumber)
            fragment.arguments = args
            return fragment
        }
    }

}
