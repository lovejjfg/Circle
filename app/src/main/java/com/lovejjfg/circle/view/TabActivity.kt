package com.lovejjfg.circle.view

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.util.Log
import android.view.View

import com.lovejjfg.circle.R
import com.lovejjfg.circle.view.fragment.Fragment1
import com.lovejjfg.circle.view.fragment.Fragment2
import com.lovejjfg.circle.view.fragment.Fragment3
import com.lovejjfg.circle.view.fragment.Fragment4
import com.lovejjfg.circle.view.fragment.Fragment5
import com.lovejjfg.circle.view.fragment.Fragment6
import com.lovejjfg.circle.view.fragment.Fragment7

import java.util.ArrayList

import butterknife.Bind
import butterknife.ButterKnife

class TabActivity : AppCompatActivity(), View.OnClickListener {

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * [FragmentPagerAdapter] derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    /**
     * The [ViewPager] that will host the section contents.
     */
    @Bind(R.id.tab)
    internal var mTab: TabLayout? = null
    @Bind(R.id.container)
    internal var mViewPager: ViewPager? = null
    @Bind(R.id.fab)
    internal var mFab: FloatingActionButton? = null
    private var fragments: ArrayList<Fragment>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab)
        ButterKnife.bind(this)

        val toolbar = findViewById(R.id.toolbar) as Toolbar?
        setSupportActionBar(toolbar)
        fragments = ArrayList<Fragment>()
        fragments!!.add(Fragment6.newInstance(1))
        fragments!!.add(Fragment3.newInstance(1))
        fragments!!.add(Fragment5.newInstance(1))
        fragments!!.add(Fragment1.newInstance(1))
        fragments!!.add(Fragment2.newInstance(1))
        fragments!!.add(Fragment4.newInstance(1))
        fragments!!.add(Fragment7.newInstance(1))
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
        // Set up the ViewPager with the sections adapter.
        mViewPager!!.adapter = mSectionsPagerAdapter
        mTab!!.setupWithViewPager(mViewPager)

        mFab!!.setOnClickListener(this)


    }

    override fun onClick(v: View) {
        //        ListPopupWindow popupWindow = new ListPopupWindow(this);
        //        popupWindow.setHeight(300);
        //        popupWindow.setWidth(1000);
        //        ArrayList<String> list = new ArrayList<>();
        //        list.add("TEST1");
        //        list.add("TEST12");
        //        list.add("TEST13");
        //        list.add("TEST14");
        //        popupWindow.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.item_layout, R.id.ctv, list));
        //        popupWindow.show();
    }


    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            // TODO: 2016-06-09 完善剩余逻辑

            return fragments!![position]
        }

        override fun getCount(): Int {
            // Show 3 total pages.
            return fragments!!.size
        }

        override fun getPageTitle(position: Int): String? {
            when (position) {
                0 -> return "1"
                1 -> return "2"
                2 -> return "3"
                3 -> return "4"
                4 -> return "5"
                5 -> return "6"
                6 -> return "7"
            }
            return null
        }
    }
}
