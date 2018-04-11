package com.hsf1002.sky.wanandroid.presenter.navigation;

import com.hsf1002.sky.wanandroid.base.presenter.BasePresenter;
import com.hsf1002.sky.wanandroid.contract.navigation.NavigationContract;
import com.hsf1002.sky.wanandroid.core.DataManager;

import javax.inject.Inject;

/**
 * Created by hefeng on 18-4-10.
 */

public class NavigationPresenter extends BasePresenter<NavigationContract.View> implements NavigationContract.Presenter {
    private DataManager dataManager;

    @Inject
    public NavigationPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(NavigationContract.View view) {
        super.attachView(view);
    }

    @Override
    public void getNavigationListData() {
    }
}
