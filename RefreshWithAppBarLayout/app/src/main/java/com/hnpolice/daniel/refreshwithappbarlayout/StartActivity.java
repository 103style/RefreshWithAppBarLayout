package com.hnpolice.daniel.refreshwithappbarlayout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * create by luoxiaoke 2017/3/30 11:02
 * eamil:wtimexiaoke@gmail.com
 * github:https://github.com/103style
 * csdn:http://blog.csdn.net/lxk_1993
 * 简书：http://www.jianshu.com/u/109656c2d96f
 */
public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv1, R.id.tv2})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv1:
                startActivity(new Intent(StartActivity.this, NotBottomTabActivity.class));
                break;
            case R.id.tv2:
                startActivity(new Intent(StartActivity.this, WithBottomTabActivity.class));
                break;
        }
    }
}
