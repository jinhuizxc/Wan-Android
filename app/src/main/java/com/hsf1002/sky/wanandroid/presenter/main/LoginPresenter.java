package com.hsf1002.sky.wanandroid.presenter.main;

import android.text.TextUtils;

import com.hsf1002.sky.wanandroid.base.presenter.BasePresenter;
import com.hsf1002.sky.wanandroid.contract.main.LoginContract;
import com.hsf1002.sky.wanandroid.core.DataManager;
import com.hsf1002.sky.wanandroid.core.bean.BaseResponse;
import com.hsf1002.sky.wanandroid.core.bean.main.login.LoginData;
import com.hsf1002.sky.wanandroid.utils.RxUtils;
import com.hsf1002.sky.wanandroid.widget.BaseObserver;

import javax.inject.Inject;

/**
 * Created by hefeng on 18-4-17.
 */

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {

    private DataManager dataManager;

    @Inject
    public LoginPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void getLoginData(String username, String password) {
        addSubscribe(dataManager.getLoginData(username, password)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new BaseObserver<BaseResponse<LoginData>>(view)
                {
                    @Override
                    public void onNext(BaseResponse<LoginData> loginDataBaseResponse) {
                        if (loginDataBaseResponse.getErrorCode() == BaseResponse.SUCCESS)
                        {
                            view.showLoginData(loginDataBaseResponse);
                        }
                        else
                        {
                            view.showLoginFail();
                        }
                    }
                }));
    }

    @Override
    public void getRegisterData(String username, String password, String rePassword) {
        addSubscribe(dataManager.getRegisterData(username, password, rePassword)
                .compose(RxUtils.rxSchedulerHelper())
                .filter(loginDataBaseResponse ->
                        !TextUtils.isEmpty(username) &&
                        !TextUtils.isEmpty(password) &&
                        !TextUtils.isEmpty(rePassword))
                .subscribeWith(new BaseObserver<BaseResponse<LoginData>>(view)
                {
                    @Override
                    public void onNext(BaseResponse<LoginData> loginDataBaseResponse) {
                        if (loginDataBaseResponse.getErrorCode() == BaseResponse.SUCCESS)
                        {
                            view.showRegisterData(loginDataBaseResponse);
                        }
                        else
                        {
                            view.showRegisterFail();
                        }
                    }
                }));
    }
}
