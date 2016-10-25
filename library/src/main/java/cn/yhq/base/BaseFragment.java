package cn.yhq.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public abstract class BaseFragment extends Fragment implements
        IDialogCreator,
        FragmentHelper.OnFragmentChangeListener {
    private ValidateManager mValidateManager;
    private DialogManager mDialogManager;
    private FragmentHelper mFragmentHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mValidateManager = new ValidateManager();
        this.mDialogManager = new DialogManager(this);

        if (savedInstanceState != null) {
            restoreInstanceState(savedInstanceState);
        }
    }

    private void restoreInstanceState(Bundle savedInstanceState) {
        mFragmentHelper.restoreInstanceState(savedInstanceState);
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getContentViewLayoutId(), null);
    }

    protected abstract int getContentViewLayoutId();

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
}
