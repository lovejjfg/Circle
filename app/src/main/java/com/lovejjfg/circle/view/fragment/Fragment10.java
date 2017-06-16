package com.lovejjfg.circle.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lovejjfg.circle.R;
import com.lovejjfg.circle.widget.GradienTextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Joe on 2016-06-09
 * Email: lovejjfg@gmail.com
 */
public class Fragment10 extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public Fragment10() {
    }


    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Fragment10 newInstance(int sectionNumber) {
        Fragment10 fragment = new Fragment10();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

//    Button mBtAgain;
//    @Bind(R.id.bt_pause)
//    Button mBtPause;
//    @Bind(R.id.bt_finish)
//    Button mBtFinish;

    @Bind(R.id.bt_start)
    Button mStart;
    @Bind(R.id.gt_two)
    GradienTextView mTwo;
    @Bind(R.id.gt_one)
    GradienTextView mOne;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_text_animation, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOne.start(GradienTextView.Orientation.LEFT_TO_RIGHT_FORME_NONE, 1000);
                mTwo.start(GradienTextView.Orientation.LEFT_TO_RIGHT_FORME_NONE, 1000);
            }
        });
    }
}
