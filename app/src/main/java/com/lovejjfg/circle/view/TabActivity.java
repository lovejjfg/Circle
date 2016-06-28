package com.lovejjfg.circle.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.lovejjfg.circle.R;
import com.lovejjfg.circle.view.fragment.Fragment1;
import com.lovejjfg.circle.view.fragment.Fragment2;
import com.lovejjfg.circle.view.fragment.Fragment3;
import com.lovejjfg.circle.view.fragment.Fragment4;
import com.lovejjfg.circle.view.fragment.Fragment5;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TabActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    @Bind(R.id.tab)
    TabLayout mTab;
    @Bind(R.id.container)
    ViewPager mViewPager;
    @Bind(R.id.fab)
    FloatingActionButton mFab;
    private ArrayList<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fragments = new ArrayList<>();
        fragments.add(Fragment3.newInstance(1));
        fragments.add(Fragment5.newInstance(1));
        fragments.add(Fragment1.newInstance(1));
        fragments.add(Fragment2.newInstance(1));
        fragments.add(Fragment4.newInstance(1));
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mTab.setupWithViewPager(mViewPager);

        mFab.setOnClickListener(this);


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
        String extendInfoStr = "roomid:1,space:,price:;roomid:2,space:12,price:;roomid:3,space:11,price:3000";
        //roomid:2,type:1,space:20,price:2200,roomtag:1|11|21"
        StringBuilder sbInfo = new StringBuilder();
        StringBuilder sbPrice = new StringBuilder();
        if (extendInfoStr.contains(";")) {
            String extendInfo[] = extendInfoStr.split(";");
            int extendSize = extendInfo.length;
            //roomid:1,space:,price:;roomid:2,space:12,price:2000;roomid:3,space:,price:3000
            String mRentRoomIDValue = "";
            String mRentRoomSpaceValue = "";
            String mRentRoomPriceValue = "";
            for (String itemTemp : extendInfo) {
                if (itemTemp.contains(",")) {
                    String itemInfo[] = itemTemp.split(",");
                    for (int j = 0; j < itemInfo.length; j++) {
                        if (itemInfo[j].contains("roomid")) {
                            mRentRoomIDValue = itemInfo[j].substring(7, itemInfo[j].length());
                        }
                        if (itemInfo[j].contains("space")) {
                            mRentRoomSpaceValue = itemInfo[j].substring(6, itemInfo[j].length());
                        }
                        if (itemInfo[j].contains("price")) {
                            mRentRoomPriceValue = itemInfo[j].substring(6, itemInfo[j].length());
                        }
                        if (!TextUtils.isEmpty(mRentRoomSpaceValue) && !TextUtils.isEmpty(mRentRoomPriceValue)) {
                            sbInfo.append("roomid:").append(mRentRoomIDValue).append(",space:").append(mRentRoomSpaceValue).append(",price:")
                                    .append(mRentRoomPriceValue).append(";");
                            sbPrice.append(mRentRoomPriceValue).append(",");
                            mRentRoomIDValue = "";
                            mRentRoomPriceValue = "";
                            mRentRoomSpaceValue = "";
                        }
                    }
                }
            }
        }
        Log.e("TAG", "onClick: " + sbInfo.toString().substring(0, sbInfo.length() > 0 ? sbInfo.length() - 1 : sbInfo.length()));
        Log.e("TAG", "onClick: " + sbPrice.toString().substring(0, sbPrice.length() > 0 ? sbPrice.length() - 1 : sbInfo.length()));
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
