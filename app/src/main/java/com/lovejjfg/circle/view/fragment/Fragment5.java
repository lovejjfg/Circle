package com.lovejjfg.circle.view.fragment;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.lovejjfg.circle.R;
import com.lovejjfg.circle.anim.drawable.StrokeGradientDrawable;
import com.lovejjfg.circle.widget.IndexBar;
import com.lovejjfg.circle.widget.PathTextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Joe on 2016-06-09
 * Email: lovejjfg@gmail.com.cn
 */
public class Fragment5 extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    @Bind(R.id.ptv)
    PathTextView mPtv;
    @Bind(R.id.index)
    IndexBar mIndex;
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
//        Toast toast = new Toast(getActivity());
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
        ArrayList<String> letter =new ArrayList<>();
        letter.add("A");
        letter.add("B");
        letter.add("C");
        letter.add("D");
        letter.add("E");
        letter.add("F");
        letter.add("G");
        letter.add("H");
        letter.add("I");
        letter.add("J");
        letter.add("K");
        letter.add("L");
        letter.add("M");
        letter.add("N");
        letter.add("O");
        letter.add("P");
        letter.add("Q");
        letter.add("R");
        letter.add("S");
        letter.add("T");
        letter.add("U");
        letter.add("V");
        letter.add("W");
        letter.add("X");
        letter.add("Y");
        letter.add("Z");
        mIndex.setLetters(letter);
        mIndex.setOnLetterChangeListener(new IndexBar.OnLetterChangeListener() {
            @Override
            public void onLetterChange(String letter) {
                Log.e("TAG", "onLetterChange: " + letter);

            }
        });
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
