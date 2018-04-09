package com.hsf1002.sky.wanandroid.base.view;

/**
 * Created by hefeng on 18-4-8.
 */

public interface BaseView {
    void showErrorMsg(String errorMsg);
    void showNormal();
    void showError();

    void showLoading();
    void showLogoutView();

    void showCollectFail();
    void showCancelCollectFail();
    void showCollectSuccess();
    void showCancelCollectSuccess();
}
