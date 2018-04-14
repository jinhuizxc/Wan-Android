package com.hsf1002.sky.wanandroid.contract.navigation;

import com.hsf1002.sky.wanandroid.base.presenter.AbstractPresenter;
import com.hsf1002.sky.wanandroid.base.view.BaseView;

/**
 * Created by hefeng on 18-4-10.
 */

public interface NavigationContract {
    interface View extends BaseView
    {

    }

    interface Presenter extends AbstractPresenter<View>
    {
        void getNavigationListData();
    }
}
