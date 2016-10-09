package com.lovejjfg.circle.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.lovejjfg.circle.R
import com.lovejjfg.circle.widget.CurveLayout
import com.lovejjfg.circle.widget.CurveView

import butterknife.Bind
import butterknife.ButterKnife

import android.content.ContentValues.TAG

/**
 * Created by Joe on 2016-06-09
 * Email: lovejjfg@gmail.com
 */
class Fragment7 : Fragment(), View.OnClickListener {

    @Bind(R.id.ts)
    internal var mCurveView: CurveView? = null

    @Bind(R.id.view)
    internal var mView: View? = null

    @Bind(R.id.curve_container)
    internal var mContainer: CurveLayout? = null

    @Bind(R.id.tv1)
    internal var mTv1: TextView? = null
    @Bind(R.id.tv2)
    internal var mTv2: TextView? = null
    @Bind(R.id.tv3)
    internal var mTv3: TextView? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val rootView = inflater!!.inflate(R.layout.fragment_tab7, container, false)
        ButterKnife.bind(this, rootView)
        mCurveView!!.setTarget(mView)
        mContainer!!.addmDispatcher(mCurveView)
        mTv1!!.setOnClickListener(this)
        mTv2!!.setOnClickListener(this)
        mTv3!!.setOnClickListener(this)
        return rootView
    }

    override fun onClick(v: View) {
        Log.e(TAG, "onClick: " + v.id)
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
        fun newInstance(sectionNumber: Int): Fragment7 {
            val fragment = Fragment7()
            val args = Bundle()
            args.putInt(ARG_SECTION_NUMBER, sectionNumber)
            fragment.arguments = args
            return fragment
        }
    }
}
