package com.hsf1002.sky.wanandroid.presenter.mainpager;

import com.hsf1002.sky.wanandroid.R;
import com.hsf1002.sky.wanandroid.base.presenter.BasePresenter;
import com.hsf1002.sky.wanandroid.component.RxBus;
import com.hsf1002.sky.wanandroid.contract.mainpager.MainPagerContract;
import com.hsf1002.sky.wanandroid.core.DataManager;
import com.hsf1002.sky.wanandroid.core.event.CollectEvent;
import com.hsf1002.sky.wanandroid.core.event.LoginEvent;

import javax.inject.Inject;

/**
 * Created by hefeng on 18-4-10.
 */

public class MainPagerPresenter extends BasePresenter<MainPagerContract.View> implements MainPagerContract.Presenter {

    private DataManager dataManager;
    private boolean isRefresh = true;
    private int currentPage;

    @Inject
    public MainPagerPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(MainPagerContract.View view) {
        super.attachView(view);
        registerEvent();
    }

    private void registerEvent()
    {
        addSubscribe(RxBus.getDefault().toFlowable(CollectEvent.class)
            .filter(collectEvent -> !collectEvent.isCancelCollectSuccess())
            .subscribe(collectEvent -> view.showCollectSuccess()));

        addSubscribe(RxBus.getDefault().toFlowable(CollectEvent.class)
                .filter(CollectEvent::isCancelCollectSuccess)
                .subscribe(collectEvent -> view.showCancelCollectSuccess()));

        addSubscribe(RxBus.getDefault().toFlowable(LoginEvent.class)
            .filter(LoginEvent::isLogin)
            .subscribe(loginEvent -> view.showLoginView()));

        addSubscribe(RxBus.getDefault().toFlowable(LoginEvent.class)
            .filter(loginEvent -> !loginEvent.isLogin())
            .subscribe(loginEvent -> view.showLogoutView()));
    }

    @Override
    public void loadMainPagerData() {

    }
}
