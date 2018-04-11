package com.hsf1002.sky.wanandroid.presenter.main;

import com.hsf1002.sky.wanandroid.base.presenter.BasePresenter;
import com.hsf1002.sky.wanandroid.component.RxBus;
import com.hsf1002.sky.wanandroid.contract.main.MainContract;
import com.hsf1002.sky.wanandroid.core.DataManager;
import com.hsf1002.sky.wanandroid.core.event.AutoLoginEvent;
import com.hsf1002.sky.wanandroid.core.event.DismissErrorView;
import com.hsf1002.sky.wanandroid.core.event.LoginEvent;
import com.hsf1002.sky.wanandroid.core.event.ShowErrorView;
import com.hsf1002.sky.wanandroid.core.event.SwitchNavigationEvent;
import com.hsf1002.sky.wanandroid.core.event.SwitchProjectEvent;

import javax.inject.Inject;

/**
 * Created by hefeng on 18-4-10.
 */

public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter{
    private DataManager dataManager;

    @Inject
    public MainPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(MainContract.View view) {
        super.attachView(view);
        registerEvent();
    }

    private void registerEvent()
    {
        addSubscribe(RxBus.getDefault().toFlowable(DismissErrorView.class)
            .subscribe(dismissErrorView -> view.showDismissErrorView()));

        addSubscribe(RxBus.getDefault().toFlowable(LoginEvent.class)
            .filter(LoginEvent::isLogin)
            .subscribe(loginEvent -> view.showLoginView()));

        addSubscribe(RxBus.getDefault().toFlowable(LoginEvent.class)
            .filter(loginEvent -> !loginEvent.isLogin())
            .subscribe(loginEvent -> view.showLogoutView()));

        addSubscribe(RxBus.getDefault().toFlowable(AutoLoginEvent.class)
            .subscribe(autoLoginEvent -> view.showLoginView()));

        addSubscribe(RxBus.getDefault().toFlowable(ShowErrorView.class)
            .subscribe(showErrorView -> view.showErrorView()));

        addSubscribe(RxBus.getDefault().toFlowable(SwitchProjectEvent.class)
            .subscribe(switchProjectEvent -> view.showSwitchProject()));

        addSubscribe(RxBus.getDefault().toFlowable(SwitchNavigationEvent.class)
            .subscribe(switchNavigationEvent -> view.showSwitchNavigation()));
    }
}
