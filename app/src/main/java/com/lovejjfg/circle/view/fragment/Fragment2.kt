package com.lovejjfg.circle.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.SeekBar

import com.lovejjfg.circle.R
import com.lovejjfg.circle.widget.DragBubbleView

import butterknife.Bind
import butterknife.ButterKnife

/**
 * Created by Joe on 2016-06-09
 * Email: lovejjfg@gmail.com
 */
class Fragment2 : Fragment(), CompoundButton.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener {

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    @Bind(R.id.cb_fill)
    internal var mCbFill: CheckBox? = null
    @Bind(R.id.cb_circle)
    internal var mCbCircle: CheckBox? = null
    @Bind(R.id.pb_cirRadius)
    internal var mPbRadio: SeekBar? = null
    @Bind(R.id.dbv)
    internal var mBubble: DragBubbleView? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.fragment_tab2, container, false)
        ButterKnife.bind(this, rootView)
        mCbFill!!.isChecked = mBubble!!.fillDraw
        mCbCircle!!.isChecked = mBubble!!.isShowCircle
        //        mBubble.setEnabled(false);
        mCbFill!!.setOnCheckedChangeListener(this)
        mPbRadio!!.setOnSeekBarChangeListener(this)
        mCbCircle!!.setOnCheckedChangeListener(this)

        return rootView
    }

    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        when (buttonView.id) {
            R.id.cb_fill -> mBubble!!.fillDraw = isChecked
            R.id.cb_circle -> mBubble!!.isShowCircle = isChecked
        }

    }

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        mBubble!!.setOriginR(progress + 30)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {

    }

    override fun onStopTrackingTouch(seekBar: SeekBar) {

    }

    companion object {
        private val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        fun newInstance(sectionNumber: Int): Fragment2 {
            val fragment = Fragment2()
            val args = Bundle()
            args.putInt(ARG_SECTION_NUMBER, sectionNumber)
            fragment.arguments = args
            return fragment
        }
    }
}
