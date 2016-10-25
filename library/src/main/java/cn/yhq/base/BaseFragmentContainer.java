package cn.yhq.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import cn.yhq.fragment.FragmentHelper;

/**
 * Created by Yanghuiqiang on 2016/10/25.
 */

public abstract class BaseFragmentContainer extends BaseFragment implements FragmentHelper.OnFragmentChangeListener {
    private FragmentHelper mFragmentHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentHelper = FragmentHelper.setup(this, getFragmentContainerId());
        mFragmentHelper.setOnFragmentChangeListener(this);
        if (savedInstanceState != null) {
            mFragmentHelper.restoreInstanceState(savedInstanceState);
        }
    }

    protected abstract int getFragmentContainerId();

    public <T extends Fragment> T addFragment(Class<T> fragment, Bundle args) {
        return mFragmentHelper.addFragment(fragment, args);
    }

    public <T extends Fragment> T addFragment(Class<T> fragment) {
        return mFragmentHelper.addFragment(fragment);
    }

    public void changeFragment(int position) {
        mFragmentHelper.changeFragment(position);
    }

    public <T extends Fragment> void changeFragment(Class<T> fragment) {
        mFragmentHelper.changeFragment(fragment);
    }

    public <T extends Fragment> T getLastFragment() {
        if (mFragmentHelper.getLastTabInfo() == null) {
            return null;
        }
        return (T) mFragmentHelper.getLastTabInfo().getFragment();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mFragmentHelper.saveInstanceState(outState);
    }

    public FragmentHelper getFragmentHelper() {
        return mFragmentHelper;
    }

    @Override
    public void onFragmentChanged(FragmentHelper.TabInfo tabInfo) {

    }

    @Override
    protected boolean onBackPressedFragment() {
        FragmentHelper.TabInfo tabInfo = this.mFragmentHelper.getLastTabInfo();
        if (tabInfo != null && tabInfo.getFragment() instanceof BaseFragment) {
            BaseFragment baseFragment = (BaseFragment) tabInfo.getFragment();
            if (!baseFragment.onBackPressedFragment()) {
                return false;
            }
        }
        return true;
    }

}
