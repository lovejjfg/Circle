package com.lovejjfg.circle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Test2Activity extends AppCompatActivity {
    @Bind(R.id.circle)
    CircleView circleView;

//    @Bind(R.id.bt)
//    Button bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.bt)
    void onClick() {
        circleView.start();
    }
}
