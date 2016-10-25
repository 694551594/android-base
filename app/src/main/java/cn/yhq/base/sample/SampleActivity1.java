package cn.yhq.base.sample;

import java.util.ArrayList;
import java.util.List;

import cn.yhq.base.TabActivity;
import cn.yhq.base.TabPageIndicatorAdapter;

/**
 * Created by Yanghuiqiang on 2016/10/25.
 */

public class SampleActivity1 extends TabActivity {

    @Override
    protected void onViewCreated() {
        super.onViewCreated();
        this.getSupportActionBar().setTitle("第二个界面");

        List<TabPageIndicatorAdapter.TabInfo> tabItems = new ArrayList<>();
        tabItems.add(new TabPageIndicatorAdapter.TabInfo("fragment1", SampleFragment.class));
        tabItems.add(new TabPageIndicatorAdapter.TabInfo("fragment2", SampleFragment.class));
        this.setTabItems(tabItems);
    }

}
