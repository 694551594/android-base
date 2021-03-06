package cn.yhq.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.yhq.dialog.core.DialogManager;
import cn.yhq.dialog.core.IDialog;
import cn.yhq.dialog.core.IDialogCreator;
import cn.yhq.fragment.FragmentHelper;

/**
 * Created by Yanghuiqiang on 2016/10/25.
 */

public abstract class BaseFragment extends Fragment implements
        IDialogCreator,
        FragmentHelper.OnFragmentChangeListener {
    private DialogManager mDialogManager;
    private FragmentHelper mFragmentHelper;
    private Config mConfig = new Config();
    private Unbinder unbinder;

    public static class Config {
        private boolean isEventBusEnable = false;

        public Config setEventBusEnable(boolean eventBusEnable) {
            isEventBusEnable = eventBusEnable;
            return this;
        }

    }

    protected void onConfig(Config config) {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        onConfig(mConfig);

        this.mDialogManager = new DialogManager(this);

        if (savedInstanceState != null) {
            if (mFragmentHelper != null) {
                mFragmentHelper.restoreInstanceState(savedInstanceState);
            }
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleMainThreadMessage(MessageEvent event) {
        event.threadMode = ThreadMode.MAIN;
        handleMessage(event);
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void handlePostingMessage(MessageEvent event) {
        event.threadMode = ThreadMode.POSTING;
        handleMessage(event);
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void handleAsyncMessage(MessageEvent event) {
        event.threadMode = ThreadMode.ASYNC;
        handleMessage(event);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void handleBackgroundMessage(MessageEvent event) {
        event.threadMode = ThreadMode.BACKGROUND;
        handleMessage(event);
    }

    public void handleMessage(MessageEvent event) {

    }

    public void sendMessageEvent(MessageEvent event) {
        EventBus.getDefault().post(event);
    }

    public void sendMessageEvent(int what, Object obj) {
        MessageEvent event = new MessageEvent();
        event.what = what;
        event.obj = obj;
        EventBus.getDefault().post(event);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mConfig.isEventBusEnable) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mConfig.isEventBusEnable) {
            EventBus.getDefault().unregister(this);
        }
    }

    public <T extends View> T getView(View view, int id) {
        return (T) view.findViewById(id);
    }

    public void setFragmentContainer(int fragmentContainerId) {
        mFragmentHelper = FragmentHelper.setup(this, fragmentContainerId);
        mFragmentHelper.setOnFragmentChangeListener(this);
    }

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
        if (mFragmentHelper.getLastFragmentInfo() == null) {
            return null;
        }
        return (T) mFragmentHelper.getLastFragmentInfo().getFragment();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mFragmentHelper != null) {
            mFragmentHelper.saveInstanceState(outState);
        }
    }

    public FragmentHelper getFragmentHelper() {
        return mFragmentHelper;
    }

    @Override
    public void onFragmentChanged(FragmentHelper.FragmentInfo fragmentInfo) {

    }

    public boolean onBackPressedFragment() {
        if (this.mFragmentHelper != null) {
            FragmentHelper.FragmentInfo fragmentInfo = this.mFragmentHelper.getLastFragmentInfo();
            if (fragmentInfo != null && fragmentInfo.getFragment() instanceof BaseFragment) {
                BaseFragment baseFragment = (BaseFragment) fragmentInfo.getFragment();
                if (!baseFragment.onBackPressedFragment()) {
                    return false;
                }
            }
        }
        return true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getContentViewLayoutId(), null);
        return view;
    }

    protected abstract int getContentViewLayoutId();

    public void showDialogFragment(int id) {
        mDialogManager.showDialog(id);
    }

    public void showDialogFragment(int id, Bundle bundle) {
        mDialogManager.showDialog(id, bundle);
    }

    @Override
    public IDialog createDialog(int id, Bundle args) {
        return null;
    }

    public DialogManager getDialogManager() {
        return mDialogManager;
    }

    /**
     * 显示消息
     *
     * @param resId
     */
    public void showToast(int resId) {
        showToast(this.getString(resId));
    }

    /**
     * 显示消息
     *
     * @param message
     */
    public void showToast(final String message) {
        this.getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            }

        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    public void startActivityForResult(Class<?> activity, Bundle bundle, int requestCode) {
        Intent intent = createIntent(activity);
        startActivityForResult(intent, requestCode, bundle);
    }

    public void startActivityForResult(Class<?> activity, int requestCode) {
        startActivityForResult(activity, null, requestCode);
    }

    public void startActivity(Class<?> activity, Bundle bundle) {
        Intent intent = createIntent(activity);
        startActivity(intent, bundle);
    }

    public void startActivity(Class<?> activity) {
        startActivity(activity, null);
    }

    public Intent createIntent(Class<?> activity) {
        Intent intent = new Intent(getActivity(), activity);
        return intent;
    }

    public <T extends View> T inflateView(int resId) {
        return (T) View.inflate(getContext(), resId, null);
    }
}
