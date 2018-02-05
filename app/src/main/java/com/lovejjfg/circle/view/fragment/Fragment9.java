package com.lovejjfg.circle.view.fragment;

import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.lovejjfg.circle.R;
import com.lovejjfg.circle.widget.BorderDrawable;


import butterknife.BindView;
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
//    @BindView(R.id.bt_pause)
//    Button mBtPause;
//    @BindView(R.id.bt_finish)
//    Button mBtFinish;

    //    @BindView(R.id.iv_loading)
//    ImageView mIvLoading;
    int i = 0;
    @BindView(R.id.text)
    TextView mText;
    @BindView(R.id.text2)
    Button mText2;
    @BindView(R.id.text3)
    CheckedTextView mText3;
    @BindView(R.id.text4)
    CheckedTextView mText4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_time_indicator, container, false);
        ButterKnife.bind(this, rootView);

        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_checked}, new BorderDrawable(BorderDrawable.STATE_CHECKED, Color.GREEN));
        stateListDrawable.addState(new int[]{-android.R.attr.state_enabled}, new BorderDrawable(BorderDrawable.STATE_UNABLE, Color.BLUE));
        stateListDrawable.addState(new int[]{}, new BorderDrawable(BorderDrawable.STATE_NORMAL, Color.BLACK));
        StateListDrawable stateListDrawable2 = new StateListDrawable();
        stateListDrawable2.addState(new int[]{android.R.attr.state_checked}, new BorderDrawable(BorderDrawable.STATE_CHECKED, Color.GREEN));
        stateListDrawable2.addState(new int[]{-android.R.attr.state_enabled}, new BorderDrawable(BorderDrawable.STATE_UNABLE, Color.BLUE));
        stateListDrawable2.addState(new int[]{}, new BorderDrawable(BorderDrawable.STATE_NORMAL, Color.BLACK));


        mText3.setBackgroundDrawable(stateListDrawable);
        mText4.setBackgroundDrawable(stateListDrawable2);
        mText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG", "onClick: ..mText1");
                mText3.setEnabled(!mText3.isEnabled());
            }
        });
        mText3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG", "onClick: ..mText3");
                mText3.setChecked(!mText3.isChecked());
            }
        });

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
