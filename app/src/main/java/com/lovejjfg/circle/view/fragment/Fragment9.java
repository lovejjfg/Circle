package com.lovejjfg.circle.view.fragment;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lovejjfg.circle.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Joe on 2016-06-09
 * Email: lovejjfg@gmail.com
 */
public class Fragment9 extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public Fragment9() {
    }


    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Fragment9 newInstance(int sectionNumber) {
        Fragment9 fragment = new Fragment9();
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

//    @Bind(R.id.iv_loading)
//    ImageView mIvLoading;
    int i = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_time_indicator, container, false);
        ButterKnife.bind(this, rootView);

        final AnimationDrawable frameAnimation = (AnimationDrawable) getContext().getResources().getDrawable(R.drawable.iv_background);
        final int numberOfFrames = frameAnimation.getNumberOfFrames();
//        mIvLoading.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (i < numberOfFrames) {
//                    mIvLoading.setImageDrawable(frameAnimation.getFrame(i));
//                    i++;
//                    mIvLoading.postDelayed(this, 1000);
//                }
//            }
//        }, 1000);
//        frameAnimation.start();
//        bottomSeek.setOnSeekBarChangeListener(this);
//        topSeek.setOnSeekBarChangeListener(this);
//        leftSeek.setOnSeekBarChangeListener(this);
//        rightSeek.setOnSeekBarChangeListener(this);
//        mBtAgain.setOnClickListener(this);
        return rootView;
    }


}
