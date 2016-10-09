package com.lovejjfg.circle.view.fragment

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup

import com.lovejjfg.circle.R
import com.lovejjfg.circle.anim.drawable.StrokeGradientDrawable
import com.lovejjfg.circle.widget.IndexBar
import com.lovejjfg.circle.widget.PathTextView

import java.util.ArrayList

import butterknife.Bind
import butterknife.ButterKnife

/**
 * Created by Joe on 2016-06-09
 * Email: lovejjfg@gmail.com.cn
 */
class Fragment5 : Fragment() {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    @Bind(R.id.ptv)
    internal var mPtv: PathTextView? = null
    @Bind(R.id.index)
    internal var mIndex: IndexBar? = null
    private val drawable: StrokeGradientDrawable? = null
    private val gradientDrawable: GradientDrawable? = null
    private val density: Float = 0.toFloat()
    private val flag: Boolean = false

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.fragment_tab5, container, false)
        ButterKnife.bind(this, rootView)
        setHasOptionsMenu(true)
        val letter = ArrayList<String>()
        letter.add("A")
        letter.add("B")
        letter.add("C")
        letter.add("D")
        letter.add("E")
        letter.add("F")
        letter.add("G")
        letter.add("H")
        letter.add("I")
        letter.add("J")
        letter.add("K")
        letter.add("L")
        letter.add("M")
        letter.add("N")
        letter.add("O")
        letter.add("P")
        letter.add("Q")
        letter.add("R")
        letter.add("S")
        letter.add("T")
        letter.add("U")
        letter.add("V")
        letter.add("W")
        letter.add("X")
        letter.add("Y")
        letter.add("Z")
        mIndex!!.letters = letter
        mIndex!!.onLetterChangeListener = IndexBar.OnLetterChangeListener { pos, letter -> Log.e("TAG", "onLetterChange: " + letter) }
        return rootView
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.menu_tab, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.action_default -> mPtv!!.setMode(PathTextView.Default)
            R.id.action_bounce -> mPtv!!.setMode(PathTextView.Bounce)
            R.id.action_oblique -> mPtv!!.setMode(PathTextView.Oblique)
        }
        return true
    }

    companion object {
        private val ARG_SECTION_NUMBER = "section_number"

        //    @Bind(R.id.ptv)
        //    PathTextView mPtv;

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        fun newInstance(sectionNumber: Int): Fragment5 {
            val fragment = Fragment5()
            val args = Bundle()
            //        Toast toast = new Toast(getActivity());
            args.putInt(ARG_SECTION_NUMBER, sectionNumber)
            fragment.arguments = args
            return fragment
        }
    }
}
