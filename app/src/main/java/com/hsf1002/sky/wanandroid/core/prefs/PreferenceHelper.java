package com.hsf1002.sky.wanandroid.core.prefs;

/**
 * Created by hefeng on 18-4-10.
 */

public interface PreferenceHelper {
    void setLoginAccount(String account);
    void setLoginPassword(String password);
    void setLoginStatus(boolean isLogin);
    String getLoginAccount();
    String getLoginPassword();
    boolean getLoginStatus();

    void setCurrentPage(int page);
    int getCurrentPage();
    void setProjectCurrentPage(int page);
    int getProjectCurrentPage();
}
