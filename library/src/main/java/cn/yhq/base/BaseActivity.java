package cn.yhq.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.f2prateek.dart.Dart;
import com.youngfeng.snake.Snake;
import com.youngfeng.snake.annotations.EnableDragToClose;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.yhq.dialog.core.DialogManager;
import cn.yhq.dialog.core.IDialog;
import cn.yhq.dialog.core.IDialogCreator;
import cn.yhq.fragment.FragmentHelper;
import icepick.Icepick;

/**
 * Created by Yanghuiqiang on 2016/10/25.
 */

@EnableDragToClose()
public abstract class BaseActivity extends AppCompatActivity implements
        IDialogCreator,
        FragmentHelper.OnFragmentChangeListener {
    private DialogManager mDialogManager;
    private ActivityManager mActivityManager;
    private ToolbarManager mToolbarManager;
    private Toolbar mToolbar;
    private FragmentHelper mFragmentHelper;
    private Config mConfig = new Config();
    private Unbinder unbinder;

    public static class Config {
        private boolean isToolbarWrapper = true;
        private boolean isSwipeBackWrapper = true;
        private boolean isEventBusEnable = false;
        private boolean isFullScreen = false;

        public Config setToolbarWrapper(boolean toolbarWrapper) {
            isToolbarWrapper = toolbarWrapper;
            return this;
        }

        public Config setEventBusEnable(boolean eventBusEnable) {
            isEventBusEnable = eventBusEnable;
            return this;
        }

        public Config setSwipeBackWrapper(boolean swipeBackWrapper) {
            isSwipeBackWrapper = swipeBackWrapper;
            return this;
        }

        public Config setFullScreen(boolean fullScreen) {
            isFullScreen = fullScreen;
            return this;
        }

    }

    protected void onConfig(Config config) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.onConfig(mConfig);

        if (mConfig.isFullScreen) {
            this.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);// 去掉信息栏
        }

        super.onCreate(savedInstanceState);

        if (mConfig.isSwipeBackWrapper) {
            Snake.host(this);
        }
        this.setContentView(getContentViewLayoutId());

        this.mActivityManager = ActivityManager.getInstance();
        this.mActivityManager.addActivity(this);
        this.mDialogManager = new DialogManager(this);

        unbinder = ButterKnife.bind(this);
        Dart.inject(this);

        this.onViewCreated(savedInstanceState);

        if (savedInstanceState != null) {
            if (mFragmentHelper != null) {
                mFragmentHelper.restoreInstanceState(savedInstanceState);
            }
            Icepick.restoreInstanceState(this, savedInstanceState);
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
    protected void onStart() {
        super.onStart();
        if (mConfig.isEventBusEnable) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mConfig.isEventBusEnable) {
            EventBus.getDefault().unregister(this);
        }
    }

    public <T extends View> T getView(int id) {
        return (T) this.findViewById(id);
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mFragmentHelper != null) {
            mFragmentHelper.saveInstanceState(outState);
        }
        Icepick.saveInstanceState(this, outState);
    }

    public FragmentHelper getFragmentHelper() {
        return mFragmentHelper;
    }

    @Override
    public void onFragmentChanged(FragmentHelper.FragmentInfo fragmentInfo) {

    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        if (this.getSupportActionBar() != null) {
            this.getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public void setTitle(int titleId) {
        super.setTitle(titleId);
        if (this.getSupportActionBar() != null) {
            this.getSupportActionBar().setTitle(titleId);
        }
    }

    @Override
    public void onBackPressed() {
        if (!onBackPressedFragment()) {
            return;
        }
        super.onBackPressed();
    }

    protected boolean onBackPressedFragment() {
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

    protected void onCreateToolbar(Toolbar toolbar) {
        if (!TextUtils.isEmpty(this.getTitle())) {
            toolbar.setTitle(this.getTitle());
        }
    }

    private void wrapperToolbar(View view) {
        mToolbarManager = ToolbarManager.Builder.builder(this)
                .setLayoutView(view)
                .setToolbar(R.layout.toolbar)
                .build();
        mToolbar = mToolbarManager.getToolBar();
        onCreateToolbar(mToolbar);
        setSupportActionBar(mToolbar);
        super.setContentView(mToolbarManager.getContentView());
    }

    @Override
    public void setContentView(int layoutResId) {
        if (mConfig.isToolbarWrapper) {
            View view = LayoutInflater.from(this).inflate(layoutResId, null, false);
            wrapperToolbar(view);
        } else {
            super.setContentView(layoutResId);
        }
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        if (mConfig.isToolbarWrapper) {
            wrapperToolbar(view);
        } else {
            super.setContentView(view, params);
        }
    }

    @Override
    public void setContentView(View view) {
        if (mConfig.isToolbarWrapper) {
            wrapperToolbar(view);
        } else {
            super.setContentView(view);
        }
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        this.mActivityManager.removeActivity(this);
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroy();
    }

    protected abstract int getContentViewLayoutId();

    protected void onViewCreated(Bundle savedInstanceState) {

    }

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

    public void startActivityForResult(Class<?> activity, Bundle bundle, int requestCode) {
        Intent intent = createIntent(activity);
        ActivityCompat.startActivityForResult(this, intent, requestCode, bundle);
    }

    public void startActivityForResult(Class<?> activity, int requestCode) {
        startActivityForResult(activity, null, requestCode);
    }

    public void startActivity(Class<?> activity, Bundle bundle) {
        Intent intent = createIntent(activity);
        ActivityCompat.startActivity(this, intent, bundle);
    }

    public void startActivity(Class<?> activity) {
        startActivity(activity, null);
    }

    public void startSharedElementActivity(Class<?> activity, View sharedElement, String sharedElementName) {
        ActivityCompat.startActivity(
                this,
                createIntent(activity),
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                        this,
                        sharedElement,
                        sharedElementName
                ).toBundle()
        );
    }

    public void finishSharedElementActivity() {
        ActivityCompat.finishAfterTransition(this);
    }

    protected void callbackOnActivityResult(int resultCode, Intent data) {
        setResult(resultCode, data);
        finish();
    }

    protected void callbackOnActivityCanceledResult(Intent data) {
        callbackOnActivityResult(Activity.RESULT_CANCELED, data);
    }

    protected void callbackOnActivityOKResult(Intent data) {
        callbackOnActivityResult(Activity.RESULT_OK, data);
    }

    public Intent createIntent(Class<?> activity, Bundle bundle) {
        Intent intent = new Intent(this, activity);
        intent.putExtras(bundle);
        return intent;
    }

    public Intent createIntent(Class<?> activity) {
        Intent intent = new Intent(this, activity);
        return intent;
    }

    protected Context getContext() {
        return this;
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
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            }

        });

    }

    public <T extends View> T inflateView(int resId) {
        return (T) View.inflate(getContext(), resId, null);
    }

}
