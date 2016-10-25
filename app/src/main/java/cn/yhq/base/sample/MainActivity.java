package cn.yhq.base.sample;

import android.os.Bundle;

import cn.yhq.base.BaseActivity;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onViewCreated() {
        this.getSupportActionBar().setTitle("标题");
        //this.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }
}
