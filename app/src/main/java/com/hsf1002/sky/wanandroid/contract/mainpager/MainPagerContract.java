package com.hsf1002.sky.wanandroid.contract.mainpager;

import com.hsf1002.sky.wanandroid.base.view.AbstractPresenter;
import com.hsf1002.sky.wanandroid.base.view.BaseView;

/**
 * Created by hefeng on 18-4-10.
 */

public interface MainPagerContract {
    interface View extends BaseView
    {
        void showAutoLoginSuccess();
        void showAutoLoginFail();
    }

    interface Presenter extends AbstractPresenter<View>
    {
        void loadMainPagerData();
    }
}
