package com.hsf1002.sky.wanandroid.ui.main.activity;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.hsf1002.sky.wanandroid.R;
import com.hsf1002.sky.wanandroid.app.Constants;
import com.hsf1002.sky.wanandroid.base.activity.BaseActivity;
import com.hsf1002.sky.wanandroid.component.RxBus;
import com.hsf1002.sky.wanandroid.contract.main.LoginContract;
import com.hsf1002.sky.wanandroid.core.DataManager;
import com.hsf1002.sky.wanandroid.core.bean.BaseResponse;
import com.hsf1002.sky.wanandroid.core.bean.main.login.LoginData;
import com.hsf1002.sky.wanandroid.core.event.LoginEvent;
import com.hsf1002.sky.wanandroid.presenter.main.LoginPresenter;
import com.hsf1002.sky.wanandroid.utils.CommonUtils;
import com.hsf1002.sky.wanandroid.utils.StatusBarUtil;
import com.hsf1002.sky.wanandroid.widget.RegisterPopupWindow;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by hefeng on 18-4-13.
 */

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View, View.OnClickListener{

    @BindView(R.id.login_group)
    RelativeLayout loginGroup;

    @BindView(R.id.login_toolbar)
    Toolbar toolbar;

    @BindView(R.id.login_account_edit)
    EditText account;

    @BindView(R.id.login_password_edit)
    EditText password;

    @BindView(R.id.login_btn)
    Button loginBtn;

    @BindView(R.id.login_register_btn)
    Button registerBtn;

    @Inject
    DataManager dataManager;
    private RegisterPopupWindow popupWindow;


    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();

        registerBtn.setOnClickListener(this);
        popupWindow = new RegisterPopupWindow(this, this);
        popupWindow.setAnimationStyle(R.style.popup_window_animation);
        popupWindow.setOnDismissListener(() ->{
            setBackgroundAlpha(1.0f);
            registerBtn.setOnClickListener(this);
        });
        StatusBarUtil.immersive(this);
        StatusBarUtil.setPaddingSmart(this, toolbar);
        toolbar.setNavigationOnClickListener( v-> onBackPressedSupport());

        RxView.clicks(loginBtn)
                // 防抖动,1秒内第一次点击有效
                .throttleFirst(Constants.CLICK_TIME_AREA, TimeUnit.MILLISECONDS)
                .filter( o -> presenter != null)
                .subscribe( o ->
                {
                   String accountStr =  account.getText().toString().trim();
                   String passwordStr = password.getText().toString().trim();

                   if (TextUtils.isEmpty(accountStr) || TextUtils.isEmpty(passwordStr))
                   {
                       CommonUtils.showSnackMessage(this, getString(R.string.account_password_null_tint));
                       return;
                   }
                   else
                   {
                       presenter.getLoginData(accountStr, passwordStr);
                   }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_register_btn:
                popupWindow.showAtLocation(loginGroup, Gravity.CENTER, 0, 0);
                registerBtn.setOnClickListener(null);
                break;
            case R.id.register_btn:
                register();
                break;
            default:
                break;
        }
    }

    private void register()
    {
        if (presenter == null || popupWindow == null)
        {
            return;
        }
        else
        {
            String accountStr = popupWindow.usernameEdit.getText().toString().trim();
            String passwordStr = popupWindow.passwordEdit.getText().toString().trim();
            String repasswordStr = popupWindow.repasswordEdit.getText().toString().trim();

            if (TextUtils.isEmpty(accountStr) || TextUtils.isEmpty(passwordStr) || TextUtils.isEmpty(repasswordStr))
            {
                CommonUtils.showSnackMessage(this, getString(R.string.account_password_null_tint));
                return;
            }

            if (!passwordStr.equals(repasswordStr))
            {
                CommonUtils.showSnackMessage(this, getString(R.string.password_not_same));
                return;
            }

            presenter.getRegisterData(accountStr, passwordStr, repasswordStr);
        }
    }

    @Override
    public void showLoginView() {

    }

    @Override
    public void showLoginData(BaseResponse<LoginData> loginResponse) {
        if (loginResponse == null || loginResponse.getData() == null)
        {
            showLoginFail();
            return;
        }
        else
        {
            LoginData loginData = loginResponse.getData();
            dataManager.setLoginAccount(loginData.getUsername());
            dataManager.setLoginPassword(loginData.getPassword());
            dataManager.setLoginStatus(true);

            RxBus.getDefault().post(new LoginEvent(true));
            CommonUtils.showSnackMessage(this, getString(R.string.login_success));
            onBackPressedSupport();
        }
    }

    @Override
    public void showRegisterData(BaseResponse<LoginData> loginResponse) {
        if (loginResponse == null || loginResponse.getData() == null)
        {
            showRegisterFail();
            return;
        }
        else
        {
            presenter.getLoginData(loginResponse.getData().getUsername(), loginResponse.getData().getPassword());
        }
    }

    @Override
    public void showLoginFail() {
        CommonUtils.showSnackMessage(this, getString(R.string.login_fail));
    }

    @Override
    public void showRegisterFail() {
        CommonUtils.showSnackMessage(this, getString(R.string.register_fail));
    }

    public void setBackgroundAlpha( float alpha)
    {
        WindowManager.LayoutParams lp = getWindow().getAttributes();

        lp.alpha = alpha;
        getWindow().setAttributes(lp);
    }
}
