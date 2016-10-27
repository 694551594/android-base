package cn.yhq.base.sample;

import android.os.Bundle;
import android.view.View;

import cn.yhq.base.BaseActivity;


public class SampleActivity2 extends BaseActivity {
    private View mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_sample2;
    }

    @Override
    protected void onViewCreated(Bundle savedInstanceState) {
        super.onViewCreated(savedInstanceState);
        this.setTitle("第二个界面");
        this.mButton = this.getView(R.id.button);
        this.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSharedElementActivity(SampleActivity3.class, mButton, "share_button");
            }
        });
    }


}
