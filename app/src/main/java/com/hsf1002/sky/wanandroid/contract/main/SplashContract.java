package com.hsf1002.sky.wanandroid.contract.main;

import com.hsf1002.sky.wanandroid.base.view.AbstractPresenter;
import com.hsf1002.sky.wanandroid.base.view.BaseView;

/**
 * Created by hefeng on 18-4-9.
 */

public interface SplashContract {
    interface View extends BaseView
    {
        void jumpToMain();
    }

    interface Presenter extends AbstractPresenter<View>
    {
    }
}
