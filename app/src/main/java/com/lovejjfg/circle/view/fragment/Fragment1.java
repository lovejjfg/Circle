package com.lovejjfg.circle.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.lovejjfg.circle.R;
import com.lovejjfg.circle.widget.RipplesView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Joe on 2016-06-09
 * Email: lovejjfg@gmail.com
 */
public class Fragment1 extends Fragment implements SeekBar.OnSeekBarChangeListener {

        @Bind(R.id.circle)
        RipplesView rippleView;
    @Bind(R.id.pb_acceleration)
    SeekBar pb_acceleration;
    @Bind(R.id.pb_cirRadius)
    SeekBar pb_cirRadius;
    @Bind(R.id.pb_multipleRadius)
    SeekBar pb_multipleRadius;
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public Fragment1() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Fragment1 newInstance(int sectionNumber) {
        Fragment1 fragment = new Fragment1();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab1, container, false);
        ButterKnife.bind(this, rootView);
        pb_acceleration.setOnSeekBarChangeListener(this);
        pb_cirRadius.setOnSeekBarChangeListener(this);
        pb_multipleRadius.setOnSeekBarChangeListener(this);
        pb_acceleration.setProgress(rippleView.getAcceleration());
        pb_cirRadius.setProgress(rippleView.getCirRadius());
        pb_multipleRadius.setProgress(rippleView.getMultipleRadius());

        return rootView;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.pb_acceleration:
                rippleView.setAcceleration(progress);
                break;
            case R.id.pb_cirRadius:
                rippleView.setCirRadius(progress);
                break;
            case R.id.pb_multipleRadius:
                rippleView.setMultipleRadius(progress);
                break;

        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
