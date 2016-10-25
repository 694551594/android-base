package cn.yhq.base;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import java.util.List;

/**
 * Created by Yanghuiqiang on 2016/10/25.
 */

public abstract class TabActivity extends DrawerToggleActivity {
    protected TabLayout mTabLayout;
    protected ViewPager mViewPager;
    protected TabPageIndicatorAdapter mPagerAdapter;
    protected int mCurrentPosition;

    private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrollStateChanged(int position) {
        }

        @Override
        public void onPageScrolled(int position, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int position) {
            mCurrentPosition = position;
            if (position == 0) {
                setSwipeBackEnable(true);
            } else {
                setSwipeBackEnable(false);
            }

        }
    };

    public void setTabItems(List<TabPageIndicatorAdapter.TabInfo> tabItems) {
        mPagerAdapter =
                new TabPageIndicatorAdapter(this, getSupportFragmentManager(), tabItems);
        mViewPager.setAdapter(mPagerAdapter);
        this.mViewPager.setCurrentItem(mCurrentPosition);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("tabPosition", this.mCurrentPosition);
    }

    @Override
    protected void restoreInstanceState(Bundle savedInstanceState) {
        super.restoreInstanceState(savedInstanceState);
        this.mCurrentPosition = savedInstanceState.getInt("tabPosition");
        this.mViewPager.setCurrentItem(mCurrentPosition);
    }

    @Override
    protected void onViewCreated() {
        super.onViewCreated();
        mTabLayout = this.getView(R.id.tablayout);
        mViewPager = this.getView(R.id.viewpager);
        mViewPager.addOnPageChangeListener(mPageChangeListener);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onBackPressed() {
        if (mPagerAdapter.onBackPressedFragment(mCurrentPosition)) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        mViewPager.removeOnPageChangeListener(mPageChangeListener);
        super.onDestroy();
    }

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.tablayout;
    }

    public int getCurrentTabIndex() {
        return mCurrentPosition;
    }
}
