package com.hsf1002.sky.wanandroid.contract.main;

import com.hsf1002.sky.wanandroid.base.presenter.AbstractPresenter;
import com.hsf1002.sky.wanandroid.base.view.BaseView;
import com.hsf1002.sky.wanandroid.core.bean.BaseResponse;
import com.hsf1002.sky.wanandroid.core.bean.main.login.LoginData;

/**
 * Created by hefeng on 18-4-17.
 */

public interface LoginContract {
    interface View extends BaseView {

        /**
         * Show login data
         *
         * @param loginResponse BaseResponse<LoginData>
         */
        void showLoginData(BaseResponse<LoginData> loginResponse);

        /**
         * Show register data
         *
         * @param loginResponse BaseResponse<LoginData>
         */
        void showRegisterData(BaseResponse<LoginData> loginResponse);

        /**
         * Show login fail
         */
        void showLoginFail();

        /**
         * Show register fail
         */
        void showRegisterFail();

    }

    interface Presenter extends AbstractPresenter<View> {

        /**
         * Get Login data
         *
         * @param username user name
         * @param password password
         */
        void getLoginData(String username, String password);

        /**
         * 注册
         * http://www.wanandroid.com/user/register
         *
         * @param username user name
         * @param password password
         * @param rePassword re password
         */
        void getRegisterData(String username, String password, String rePassword);
    }
}
