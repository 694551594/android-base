package cn.yhq.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class TabPageIndicatorAdapter extends FragmentPagerAdapter {
    private List<TabInfo> mTabs = new ArrayList<>();
    private Activity mActivity;
    private final FragmentManager mFragmentManager;

    public static class TabInfo {
        public Class<? extends BaseFragment> clss;
        public BaseFragment fragment;
        public String title;
        public Bundle bundle;

        public TabInfo(String title, Class<? extends BaseFragment> clss) {
            super();
            this.clss = clss;
            this.title = title;
        }

        public TabInfo(String title, Class<? extends BaseFragment> clss, Bundle bundle) {
            super();
            this.clss = clss;
            this.title = title;
            this.bundle = bundle;
        }

    }

    public TabPageIndicatorAdapter(Activity activity, FragmentManager fm, List<TabInfo> fragments) {
        super(fm);
        this.mTabs = fragments;
        this.mActivity = activity;
        this.mFragmentManager = fm;
    }

    @SuppressWarnings("unchecked")
    public <T extends BaseFragment> T getItem(Class<T> clazz) {
        for (TabInfo tabInfo : mTabs) {
            if (clazz.getName().equals(tabInfo.clss.getName())) {
                return (T) tabInfo.fragment;
            }
        }
        return null;
    }

    @Override
    public Fragment getItem(int location) {
        BaseFragment fragment = mTabs.get(location).fragment;
        if (fragment == null) {
            Bundle args = mTabs.get(location).bundle;
            if (args == null) {
                args = new Bundle();
            }
            args.putInt("index", location);
            mTabs.get(location).fragment =
                    fragment =
                            (BaseFragment) Fragment.instantiate(mActivity, mTabs.get(location).clss.getName(),
                                    args);
        }
        return fragment;
    }

    public boolean onBackPressedFragment(int location) {
        return this.mTabs.get(location).fragment != null
                && this.mTabs.get(location).fragment.onBackPressedFragment();
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        super.finishUpdate(container);
    }

    @Override
    public int getCount() {
        return mTabs.size();
    }

    @Override
    public CharSequence getPageTitle(int location) {
        return mTabs.get(location).title;
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
        super.restoreState(state, loader);
        List<Fragment> list = this.mFragmentManager.getFragments();
        if (list == null) {
            return;
        }
        for (int location = 0; location < mTabs.size(); location++) {
            for (int i = 0; i < list.size(); i++) {
                if (mTabs.get(location).clss == list.get(i).getClass()) {
                    mTabs.get(location).fragment = (BaseFragment) list.get(i);
                    break;
                }
            }

        }
    }


}
