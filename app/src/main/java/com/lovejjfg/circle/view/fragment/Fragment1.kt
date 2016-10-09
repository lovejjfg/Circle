package com.lovejjfg.circle.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar

import com.lovejjfg.circle.R
import com.lovejjfg.circle.widget.RipplesView

import butterknife.Bind
import butterknife.ButterKnife

/**
 * Created by Joe on 2016-06-09
 * Email: lovejjfg@gmail.com
 */
class Fragment1 : Fragment(), SeekBar.OnSeekBarChangeListener {

    @Bind(R.id.circle)
    internal var rippleView: RipplesView? = null
    @Bind(R.id.pb_acceleration)
    internal var pb_acceleration: SeekBar? = null
    @Bind(R.id.pb_cirRadius)
    internal var pb_cirRadius: SeekBar? = null
    @Bind(R.id.pb_multipleRadius)
    internal var pb_multipleRadius: SeekBar? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.fragment_tab1, container, false)
        ButterKnife.bind(this, rootView)
        pb_acceleration!!.setOnSeekBarChangeListener(this)
        pb_cirRadius!!.setOnSeekBarChangeListener(this)
        pb_multipleRadius!!.setOnSeekBarChangeListener(this)
        pb_acceleration!!.progress = rippleView!!.acceleration
        pb_cirRadius!!.progress = rippleView!!.cirRadius
        pb_multipleRadius!!.progress = rippleView!!.multipleRadius

        return rootView
    }

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        when (seekBar.id) {
            R.id.pb_acceleration -> rippleView!!.acceleration = progress
            R.id.pb_cirRadius -> rippleView!!.cirRadius = progress
            R.id.pb_multipleRadius -> rippleView!!.multipleRadius = progress
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {

    }

    override fun onStopTrackingTouch(seekBar: SeekBar) {

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
        fun newInstance(sectionNumber: Int): Fragment1 {
            val fragment = Fragment1()
            val args = Bundle()
            args.putInt(ARG_SECTION_NUMBER, sectionNumber)
            fragment.arguments = args
            return fragment
        }
    }
}
