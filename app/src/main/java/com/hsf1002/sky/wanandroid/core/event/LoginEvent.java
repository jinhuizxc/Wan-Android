package com.hsf1002.sky.wanandroid.core.event;

/**
 * Created by hefeng on 18-4-10.
 */

public class LoginEvent {
    private boolean isLogin;

    public LoginEvent(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }
}
