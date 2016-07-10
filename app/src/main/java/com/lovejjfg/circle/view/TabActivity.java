package com.lovejjfg.circle.view;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.lovejjfg.circle.R;
import com.lovejjfg.circle.view.fragment.Fragment1;
import com.lovejjfg.circle.view.fragment.Fragment2;
import com.lovejjfg.circle.view.fragment.Fragment3;
import com.lovejjfg.circle.view.fragment.Fragment4;
import com.lovejjfg.circle.view.fragment.Fragment5;
import com.lovejjfg.circle.view.fragment.Fragment6;
import com.lovejjfg.circle.widget.FixedViewPager;
import com.lovejjfg.circle.widget.HeadViewFrameLayout;
import com.lovejjfg.circle.widget.StickyNestScrollView;
import com.lovejjfg.circle.widget.StickyNestedScrollView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TabActivity extends AppCompatActivity implements View.OnClickListener, HeadViewFrameLayout.NestedScrollListener {

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    @Bind(R.id.tab)
    TabLayout mTab;
    @Bind(R.id.header_container)
    HeadViewFrameLayout mHeaderLayout;
    @Bind(R.id.container)
    FixedViewPager mViewPager;
    @Bind(R.id.iv_header)
    ImageView mIvHeader;
    private ArrayList<Fragment> fragments;

    public int getCurrentDy() {
        return currentDy;
    }

    private int currentDy;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_new);
        ButterKnife.bind(this);
        mHeaderLayout.setTarget(mIvHeader);
        mHeaderLayout.addNestedScrollListener(this);

//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // 生成一个状态栏大小的矩形
        // 设置根布局的参数
//        mParent.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fragments = new ArrayList<>();
//        fragments.add(Fragment3.newInstance(1));
//        fragments.add(Fragment5.newInstance(1));
//        fragments.add(Fragment1.newInstance(1));
//        fragments.add(Fragment2.newInstance(1));
//        fragments.add(Fragment4.newInstance(1));
        fragments.add(Fragment6.newInstance(1));
        fragments.add(Fragment6.newInstance(1));
        fragments.add(Fragment6.newInstance(1));
        fragments.add(Fragment6.newInstance(1));
        fragments.add(Fragment6.newInstance(1));
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        /*
      The {@link android.support.v4.view.PagerAdapter} that will provide
      fragments for each of the sections. We use a
      {@link FragmentPagerAdapter} derivative, which will keep every
      loaded fragment in memory. If this becomes too memory intensive, it
      may be best to switch to a
      {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(5);
        mTab.setupWithViewPager(mViewPager);



    }

    @Override
    public void onClick(View v) {
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

    @Override
    public void onNestedScroll(int dy) {
        currentDy = dy;
        mTab.setTranslationY(dy);

    }



    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            // TODO: 2016-06-09 完善剩余逻辑

            return fragments.get(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
                case 3:
                    return "SECTION 4";
                case 4:
                    return "SECTION 5";
            }
            return null;
        }
    }
}
