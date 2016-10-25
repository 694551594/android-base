package cn.yhq.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import cn.yhq.dialog.core.DialogManager;
import cn.yhq.dialog.core.IDialog;
import cn.yhq.dialog.core.IDialogCreator;
import cn.yhq.fragment.FragmentHelper;
import cn.yhq.validate.ValidateManager;

/**
 * Created by Yanghuiqiang on 2016/10/25.
 */

public abstract class BaseActivity extends SwipeBackActivity implements
        IDialogCreator,
        FragmentHelper.OnFragmentChangeListener {
    private ValidateManager mValidateManager;
    private DialogManager mDialogManager;
    private ActivityManager mActivityManager;
    private Toolbar mToolbar;
    private FragmentHelper mFragmentHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(getContentViewLayoutId());
        this.mActivityManager = ActivityManager.getInstance();
        this.mActivityManager.addActivity(this);
        this.mValidateManager = new ValidateManager();
        this.mDialogManager = new DialogManager(this);
        this.onViewCreated();

        if (savedInstanceState != null) {
            restoreInstanceState(savedInstanceState);
        }

    }

    protected void restoreInstanceState(Bundle savedInstanceState) {
        mFragmentHelper.restoreInstanceState(savedInstanceState);
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
        if (mFragmentHelper.getLastTabInfo() == null) {
            return null;
        }
        return (T) mFragmentHelper.getLastTabInfo().getFragment();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mFragmentHelper != null) {
            mFragmentHelper.saveInstanceState(outState);
        }
    }

    public FragmentHelper getFragmentHelper() {
        return mFragmentHelper;
    }

    @Override
    public void onFragmentChanged(FragmentHelper.TabInfo tabInfo) {

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
            FragmentHelper.TabInfo tabInfo = this.mFragmentHelper.getLastTabInfo();
            if (tabInfo != null && tabInfo.getFragment() instanceof BaseFragment) {
                BaseFragment baseFragment = (BaseFragment) tabInfo.getFragment();
                if (!baseFragment.onBackPressedFragment()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 如果返回false，证明不需要toolbar
     *
     * @param toolbar
     * @return
     */
    protected boolean onCreateToolbar(Toolbar toolbar) {
        if (!TextUtils.isEmpty(this.getTitle())) {
            toolbar.setTitle(this.getTitle());
        }
        return true;
    }

    @Override
    public void setContentView(int layoutResId) {
        ToolBarHelper toolBarHelper = new ToolBarHelper(this, layoutResId, R.layout.toolbar);
        mToolbar = toolBarHelper.getToolBar();
        if (onCreateToolbar(mToolbar)) {
            setSupportActionBar(mToolbar);
            setContentView(toolBarHelper.getContentView());
        } else {
            super.setContentView(layoutResId);
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
        super.onDestroy();
    }

    protected abstract int getContentViewLayoutId();

    protected void onViewCreated() {

    }

    public boolean validate() {
        return mValidateManager.validate();
    }

    public boolean validate(EditText editText) {
        return mValidateManager.validate(editText);
    }

    public ValidateManager addValidateItem(EditText editText, ValidateManager.ValidateItem item) {
        mValidateManager.addValidateItem(editText, item);
        return mValidateManager;
    }

    public ValidateManager addValidateItems(EditText editText, List<ValidateManager.ValidateItem> items) {
        mValidateManager.addValidateItems(editText, items);
        return mValidateManager;
    }

    public ValidateManager addValidateRequiredItem(EditText editText, String validateMessage) {
        mValidateManager.addValidateRequiredItem(editText, validateMessage);
        return mValidateManager;
    }

    public ValidateManager addValidateEmailItem(EditText editText, String validateMessage) {
        mValidateManager.addValidateEmailItem(editText, validateMessage);
        return mValidateManager;
    }

    public ValidateManager addValidatePhoneItem(EditText editText, String validateMessage) {
        mValidateManager.addValidatePhoneItem(editText, validateMessage);
        return mValidateManager;
    }

    public ValidateManager addValidateMinLengthItem(EditText editText, String validateMessage,
                                                    int length) {
        mValidateManager.addValidateMinLengthItem(editText, validateMessage, length);
        return mValidateManager;
    }

    public ValidateManager addValidateMaxLengthItem(EditText editText, String validateMessage,
                                                    int value) {
        mValidateManager.addValidateMaxLengthItem(editText, validateMessage, value);
        return mValidateManager;
    }

    public ValidateManager addValidateMinValueItem(EditText editText, String validateMessage,
                                                   int value) {
        mValidateManager.addValidateMinValueItem(editText, validateMessage, value);
        return mValidateManager;
    }

    public ValidateManager addValidateUniqueItem(EditText editText, String validateMessage,
                                                 List<String> array) {
        mValidateManager.addValidateUniqueItem(editText, validateMessage, array);
        return mValidateManager;
    }

    public ValidateManager addValidateMaxValueItem(EditText editText, String validateMessage,
                                                   int value) {
        mValidateManager.addValidateMaxValueItem(editText, validateMessage, value);
        return mValidateManager;
    }

    public ValidateManager addValidateEqualsItem(EditText editText1, String validateMessage,
                                                 EditText editText2) {
        mValidateManager.addValidateEqualsItem(editText1, validateMessage, editText2);
        return mValidateManager;
    }

    public ValidateManager addValidateEqualsItem(EditText editText, String validateMessage,
                                                 String text) {
        mValidateManager.addValidateEqualsItem(editText, validateMessage, text);
        return mValidateManager;
    }

    public ValidateManager addValidateItem(EditText editText, ValidateManager.ValidateType type,
                                           String validateMessage) {
        mValidateManager.addValidateItem(editText, type, validateMessage);
        return mValidateManager;
    }

    public ValidateManager addValidateRegexItem(EditText editText, String validateRegex,
                                                String validateMessage) {
        mValidateManager.addValidateRegexItem(editText, validateRegex, validateMessage);
        return mValidateManager;
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

    public ValidateManager getValidateManager() {
        return mValidateManager;
    }

    public DialogManager getDialogManager() {
        return mDialogManager;
    }

    public void startActivityForResult(Class<?> activity, Bundle bundle, int requestCode) {
        Intent intent = getIntent(activity, bundle);
        this.startActivityForResult(intent, requestCode);
    }

    public void startActivityForResult(Class<?> activity, int requestCode) {
        Intent intent = getIntent(activity);
        this.startActivityForResult(intent, requestCode);
    }

    public void startActivity(Class<?> activity, Bundle bundle) {
        Intent intent = getIntent(activity, bundle);
        this.startActivity(intent);
    }

    public void startActivity(Class<?> activity) {
        Intent intent = getIntent(activity);
        this.startActivity(intent);
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

    public Intent getIntent(Class<?> activity, Bundle bundle) {
        Intent intent = new Intent(this, activity);
        intent.putExtras(bundle);
        return intent;
    }

    public Intent getIntent(Class<?> activity) {
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

    public void setSwipeBackEnable(boolean enable) {
        this.getSwipeBackLayout().setEnableGesture(enable);
    }

}
