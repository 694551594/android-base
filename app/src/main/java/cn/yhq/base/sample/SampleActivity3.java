package cn.yhq.base.sample;

import android.os.Bundle;
import android.view.View;

import cn.yhq.base.BaseActivity;


public class SampleActivity3 extends BaseActivity {
    private View mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_sample3;
    }

    @Override
    protected void onViewCreated(Bundle savedInstanceState) {
        super.onViewCreated(savedInstanceState);
        this.setTitle("第三个界面");
        //this.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        this.mButton = this.getView(R.id.button);
        this.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishSharedElementActivity();

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finishSharedElementActivity();
    }
}
