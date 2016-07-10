package com.lovejjfg.circle.view.fragment;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.lovejjfg.circle.R;
import com.lovejjfg.circle.anim.drawable.StrokeGradientDrawable;
import com.lovejjfg.circle.view.TabActivity;
import com.lovejjfg.circle.widget.PathTextView;
import com.lovejjfg.circle.widget.StickyNestedScrollView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Joe on 2016-06-09
 * Email: zhangjun166@pingan.com.cn
 */
public class Fragment6 extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
//    @Bind(R.id.ptv)
//    PathTextView mPtv;
    private static final String ARG_SECTION_NUMBER = "section_number";
    private StrokeGradientDrawable drawable;
    private GradientDrawable gradientDrawable;
    private float density;
    private boolean flag;
    private int currentDy;

    @Bind(R.id.scroll_view)
    StickyNestedScrollView scrollView;

    public Fragment6() {

    }

//    @Bind(R.id.ptv)
//    PathTextView mPtv;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Fragment6 newInstance(int sectionNumber) {
        Fragment6 fragment = new Fragment6();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        String join;
        String[] strs = {"xxxx", "xhahaha", "huohuo"};
        String tem = "";
        for (int i = 0; i < strs.length; i++) {
            tem += strs[i];
            if (i < strs.length - 1) {
                tem += ",";
            }
        }
        join = tem;
        //恩，知道字符串直接拼接效率低下之后，那么又手动改为StringBuilder

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strs.length; i++) {
            sb.append(strs[i]);
            if (i < strs.length - 1) {
                sb.append(";");
            }
        }
        join = sb.toString();
        //最后，你才知道TextUtils里面已经定义好了这种需求,哭瞎
        join = TextUtils.join(";", strs);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_main, container, false);
        ButterKnife.bind(this, rootView);
        setHasOptionsMenu(true);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        currentDy = ((TabActivity) getActivity()).getCurrentDy();
        Log.i("TAG", "onResume: " + currentDy);
        if (scrollView.getScrollY() < -currentDy) {
            scrollView.smoothScrollTo(0, -currentDy);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getActivity() != null) {
            currentDy = ((TabActivity) getActivity()).getCurrentDy();
            Log.i("TAG", "onResume: " + currentDy);
            if (scrollView != null && scrollView.getScrollY() < -currentDy) {
                scrollView.smoothScrollTo(0, -currentDy);
            }
        }

//        currentDy = ((TabActivity) getActivity()).getCurrentDy();
//        Log.i("TAG", "onResume: " + currentDy);
//        scrollView.smoothScrollTo(0, -currentDy);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_tab, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }
}
