package cn.yhq.base.sample;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cn.yhq.base.BaseActivity;
import cn.yhq.base.MessageEvent;


public class MainActivity extends BaseActivity {
    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;

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
        this.mButton3 = this.getView(R.id.button3);
        this.mButton4 = this.getView(R.id.button4);
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
        this.mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SampleActivity4.class);
            }
        });
        this.mButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SampleActivity5.class);
            }
        });
    }

    @Override
    public void handleMainThreadMessage(MessageEvent event) {
        super.handleMainThreadMessage(event);
        this.showToast("上一个界面" + event.obj.toString());
    }
}
