package cn.yhq.base.sample;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.Button;

import cn.yhq.base.DrawerToggleActivity;


public class MainActivity extends DrawerToggleActivity {
    private Button mButton;
    private DrawerLayout mDrawerLayout;

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
        this.mButton = this.getView(R.id.button);
        this.mDrawerLayout = this.getView(R.id.main_drawerlayout);
        this.setDrawerLayout(mDrawerLayout);
        this.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SampleActivity1.class);
            }
        });
    }
}
