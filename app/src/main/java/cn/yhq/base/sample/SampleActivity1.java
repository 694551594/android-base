package cn.yhq.base.sample;

import cn.yhq.base.BaseActivity;

/**
 * Created by Yanghuiqiang on 2016/10/25.
 */

public class SampleActivity1 extends BaseActivity {

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onViewCreated() {
        super.onViewCreated();
        this.getSupportActionBar().setTitle("第二个界面");
    }

}
