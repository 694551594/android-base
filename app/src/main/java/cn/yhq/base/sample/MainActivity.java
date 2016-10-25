package cn.yhq.base.sample;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;

import cn.yhq.base.DrawerToggleActivity;


public class MainActivity extends DrawerToggleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setSwipeBackEnable(false);
    }

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_drawer;
    }

    @Override
    protected void onViewCreated() {
        super.onViewCreated();
        this.getSupportActionBar().setTitle("首页");
        //this.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    protected DrawerLayout getDrawerLayout() {
        return (DrawerLayout) this.findViewById(R.id.main_drawerlayout);
    }
}
