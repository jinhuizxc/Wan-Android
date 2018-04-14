package com.hsf1002.sky.wanandroid.core.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.hsf1002.sky.wanandroid.app.Constants;
import com.hsf1002.sky.wanandroid.app.GeeksApp;

import javax.inject.Inject;

/**
 * Created by hefeng on 18-4-11.
 */

public class PreferenceHelperImpl implements PreferenceHelper {

    private static final String MY_SHARED_PREFERENCE = "my_shared_preference";
    private final SharedPreferences sharedPreferences;

    @Inject
    public PreferenceHelperImpl() {
        this.sharedPreferences = GeeksApp.getInstance().getSharedPreferences(MY_SHARED_PREFERENCE, Context.MODE_PRIVATE);
    }

    @Override
    public void setLoginAccount(String account) {
        sharedPreferences.edit().putString(Constants.ACCOUNT, account).apply();
    }

    @Override
    public void setLoginPassword(String password) {
        sharedPreferences.edit().putString(Constants.PASSWORD, password).apply();
    }

    @Override
    public void setLoginStatus(boolean isLogin) {
        sharedPreferences.edit().putBoolean(Constants.LOGIN_STATUS, isLogin).apply();
    }

    @Override
    public String getLoginAccount() {
        return sharedPreferences.getString(Constants.ACCOUNT, "hello");
    }

    @Override
    public String getLoginPassword() {
        return sharedPreferences.getString(Constants.PASSWORD, "world");
    }

    @Override
    public boolean getLoginStatus() {
        return sharedPreferences.getBoolean(Constants.LOGIN_STATUS, false);
    }

    @Override
    public void setCurrentPage(int page) {
        sharedPreferences.edit().putInt(Constants.CURRENT_PAGE, page).apply();
    }

    @Override
    public int getCurrentPage() {
        return sharedPreferences.getInt(Constants.CURRENT_PAGE, 0);
    }

    @Override
    public void setProjectCurrentPage(int page) {
        sharedPreferences.edit().putInt(Constants.PROJECT_CURRENT_PAGE, page).apply();
    }

    @Override
    public int getProjectCurrentPage() {
        return sharedPreferences.getInt(Constants.PROJECT_CURRENT_PAGE, 0);
    }
}
