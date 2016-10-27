package cn.yhq.base.sample;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cn.yhq.base.BaseActivity;


public class MainActivity extends BaseActivity {
    private Button mButton1;
    private Button mButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setSwipeBackWrapper(false);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onViewCreated(Bundle savedInstanceState) {
        super.onViewCreated(savedInstanceState);
        this.setTitle("首页");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        this.mButton1 = this.getView(R.id.button1);
        this.mButton2 = this.getView(R.id.button2);
        this.mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SampleActivity1.class);

            }
        });
        this.mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SampleActivity2.class);

            }
        });
    }


}
