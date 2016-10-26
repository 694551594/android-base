package cn.yhq.base.sample;

import cn.yhq.base.BaseActivity;

/**
 * Created by Yanghuiqiang on 2016/10/25.
 */

public class SampleActivity1 extends BaseActivity {

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
