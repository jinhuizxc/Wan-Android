package com.hsf1002.sky.wanandroid.contract.navigation;

import com.hsf1002.sky.wanandroid.base.presenter.AbstractPresenter;
import com.hsf1002.sky.wanandroid.base.view.BaseView;
import com.hsf1002.sky.wanandroid.core.bean.BaseResponse;
import com.hsf1002.sky.wanandroid.core.bean.navigation.NavigationListData;

import java.util.List;

/**
 * Created by hefeng on 18-4-10.
 */

public interface NavigationContract {
    interface View extends BaseView
    {
        /**
         * Show navigation list data
         *
         * @param navigationListResponse BaseResponse<List<NavigationListData>>
         */
        void showNavigationListData(BaseResponse<List<NavigationListData>> navigationListResponse);

        /**
         * Show navigation list fail
         */
        void showNavigationListFail();
    }

    interface Presenter extends AbstractPresenter<View>
    {
        void getNavigationListData();
    }
}
