package cn.yhq.base.sample;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import cn.yhq.base.BaseActivity;


public class MainActivity extends BaseActivity {
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setSwipeBackEnable(false);
    }

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean onCreateToolbar(Toolbar toolbar) {
        return super.onCreateToolbar(toolbar);
    }

    @Override
    protected void onViewCreated() {
        super.onViewCreated();
        this.setTitle("首页");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        this.mButton = this.getView(R.id.button);
        this.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SampleActivity1.class);
            }
        });
    }
}
