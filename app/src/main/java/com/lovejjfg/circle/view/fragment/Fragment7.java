package com.lovejjfg.circle.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lovejjfg.circle.R;
import com.lovejjfg.circle.widget.CurveLayout;
import com.lovejjfg.circle.widget.CurveView;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;

/**
 * Created by Joe on 2016-06-09
 * Email: lovejjfg@gmail.com
 */
public class Fragment7 extends Fragment implements View.OnClickListener {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public Fragment7() {
    }


    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Fragment7 newInstance(int sectionNumber) {
        Fragment7 fragment = new Fragment7();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Bind(R.id.ts)
    CurveView mCurveView;

    @Bind(R.id.view)
    View mView;

    @Bind(R.id.curve_container)
    CurveLayout mContainer;

    @Bind(R.id.tv1)
    TextView mTv1;
    @Bind(R.id.tv2)
    TextView mTv2;
    @Bind(R.id.tv3)
    TextView mTv3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_tab7, container, false);
        ButterKnife.bind(this, rootView);
        mCurveView.setTarget(mView);
        mContainer.addmDispatcher(mCurveView);
        mTv1.setOnClickListener(this);
        mTv2.setOnClickListener(this);
        mTv3.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        Log.e(TAG, "onClick: " + v.getId());
    }
}
