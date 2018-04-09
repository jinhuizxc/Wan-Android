package com.hsf1002.sky.wanandroid.base.view;

/**
 * Created by hefeng on 18-4-8.
 */

public interface AbstractPresenter<T extends BaseView> {
    void attachView(T view);
    void detachView();
}
