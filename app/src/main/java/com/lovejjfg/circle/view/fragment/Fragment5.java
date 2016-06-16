package com.lovejjfg.circle.view.fragment;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.lovejjfg.circle.R;
import com.lovejjfg.circle.anim.drawable.StrokeGradientDrawable;
import com.lovejjfg.circle.widget.PathTextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Joe on 2016-06-09
 * Email: zhangjun166@pingan.com.cn
 */
public class Fragment5 extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    @Bind(R.id.ptv)
    PathTextView mPtv;
    private static final String ARG_SECTION_NUMBER = "section_number";
    private StrokeGradientDrawable drawable;
    private GradientDrawable gradientDrawable;
    private float density;
    private boolean flag;

    public Fragment5() {
    }

//    @Bind(R.id.ptv)
//    PathTextView mPtv;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Fragment5 newInstance(int sectionNumber) {
        Fragment5 fragment = new Fragment5();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab5, container, false);
        ButterKnife.bind(this, rootView);
        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_tab, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_default:
                mPtv.setMode(PathTextView.Default);
                break;
            case R.id.action_bounce:
                mPtv.setMode(PathTextView.Bounce);
                break;
            case R.id.action_oblique:
                mPtv.setMode(PathTextView.Oblique);
                break;
        }
        return true;
    }
}
