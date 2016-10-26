package cn.yhq.base.sample;

import android.os.Bundle;

import cn.yhq.base.BaseActivity;

/**
 * Created by Yanghuiqiang on 2016/10/25.
 */

public class SampleActivity1 extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_fragment;
    }

    @Override
    protected void onViewCreated() {
        super.onViewCreated();
        this.setTitle("第二个界面");
        this.setFragmentContainer(R.id.main_fragment_container);
        this.addFragment(SampleFragment.class);
        this.changeFragment(0);
    }

}
