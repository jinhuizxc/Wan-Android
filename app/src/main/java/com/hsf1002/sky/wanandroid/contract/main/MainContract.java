package com.hsf1002.sky.wanandroid.contract.main;

import com.hsf1002.sky.wanandroid.base.view.AbstractPresenter;
import com.hsf1002.sky.wanandroid.base.view.BaseView;

/**
 * Created by hefeng on 18-4-10.
 */

public interface MainContract {

    interface View extends BaseView
    {
        void showDismissErrorView();
        void showErrorView();
        void showSwitchProject();
        void showSwitchNavigation();
    }

    interface Presenter extends AbstractPresenter<View>
    {

    }
}
