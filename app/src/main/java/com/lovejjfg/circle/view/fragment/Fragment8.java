package com.lovejjfg.circle.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;

import com.lovejjfg.circle.R;
import com.lovejjfg.circle.widget.JumpBall;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.ContentValues.TAG;

/**
 * Created by Joe on 2016-06-09
 * Email: lovejjfg@gmail.com
 */
public class Fragment8 extends Fragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public Fragment8() {
    }


    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Fragment8 newInstance(int sectionNumber) {
        Fragment8 fragment = new Fragment8();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.jump_ball)
    JumpBall mJumpBall;
//    @BindView(R.id.bt_again)
//    Button mBtAgain;
//    @BindView(R.id.bt_pause)
//    Button mBtPause;
//    @BindView(R.id.bt_finish)
//    Button mBtFinish;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_jump_ball, container, false);
        ButterKnife.bind(this, rootView);

//        bottomSeek.setOnSeekBarChangeListener(this);
//        topSeek.setOnSeekBarChangeListener(this);
//        leftSeek.setOnSeekBarChangeListener(this);
//        rightSeek.setOnSeekBarChangeListener(this);
//        mBtAgain.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        Log.e(TAG, "onClick: " + v.getId());
        mJumpBall.setVisibility(View.VISIBLE);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//        switch (seekBar.getId()) {
//            case R.id.sb_bottom:
//                mJumpBall.setBottomChange(progress);
//                break;
//            case R.id.sb_left:
//                mJumpBall.setLeftChange(progress);
//                break;
//            case R.id.sb_right:
//                mJumpBall.setRightChange(progress);
//                break;
//            case R.id.sb_top:
//                mJumpBall.setTopChange(progress);
//                break;
//        }
//        mJumpBall.invalidate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @OnClick({R.id.bt_finish, R.id.bt_pause, R.id.bt_again})
    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_again:
                mJumpBall.start();
                break;

            case R.id.bt_finish:
                mJumpBall.finish();
                break;

            case R.id.bt_pause:
                mJumpBall.pause();
                break;

        }
    }
}
