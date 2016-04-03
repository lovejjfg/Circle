package com.lovejjfg.circle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    @Bind(R.id.circle)
    RippleView rippleView;
    @Bind(R.id.pb_acceleration)
    SeekBar pb_acceleration;
    @Bind(R.id.pb_cirRadius)
    SeekBar pb_cirRadius;
    @Bind(R.id.pb_multipleRadius)
    SeekBar pb_multipleRadius;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        pb_acceleration.setOnSeekBarChangeListener(this);
        pb_cirRadius.setOnSeekBarChangeListener(this);
        pb_multipleRadius.setOnSeekBarChangeListener(this);
        pb_acceleration.setProgress(rippleView.getAcceleration());
        pb_cirRadius.setProgress(rippleView.getCirRadius());
        pb_multipleRadius.setProgress(rippleView.getMultipleRadius());

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
