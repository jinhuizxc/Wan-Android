package com.hsf1002.sky.wanandroid.core.prefs;

import android.content.Context;
import android.content.SharedPreferences;

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

    }

    @Override
    public void setLoginPassword(String password) {

    }

    @Override
    public void setLoginStatus(boolean isLogin) {

    }

    @Override
    public String getLoginAccount() {
        return null;
    }

    @Override
    public String getLoginPassword() {
        return null;
    }

    @Override
    public boolean getLoginStatus() {
        return false;
    }

    @Override
    public void setCurrentPage(int page) {

    }

    @Override
    public int getCurrentPage() {
        return 0;
    }

    @Override
    public void setProjectCurrentPage(int page) {

    }

    @Override
    public int getProjectCurrentPage() {
        return 0;
    }
}
