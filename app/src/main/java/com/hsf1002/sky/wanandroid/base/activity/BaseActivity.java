package com.hsf1002.sky.wanandroid.base.activity;

import com.hsf1002.sky.wanandroid.R;
import com.hsf1002.sky.wanandroid.app.GeeksApp;
import com.hsf1002.sky.wanandroid.base.view.AbstractPresenter;
import com.hsf1002.sky.wanandroid.base.view.BaseView;
import com.hsf1002.sky.wanandroid.di.component.ActivityComponent;
import com.hsf1002.sky.wanandroid.di.component.DaggerActivityComponent;
import com.hsf1002.sky.wanandroid.di.module.ActivityModule;
import com.hsf1002.sky.wanandroid.utils.CommonUtils;

import javax.inject.Inject;

/**
 * Created by hefeng on 18-4-8.
 */

public abstract class BaseActivity<T extends AbstractPresenter> extends AbstractSimpleActivity implements BaseView {

    @Inject
    protected T presenter;

    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter.detachView();
        }
        super.onDestroy();
    }

    protected ActivityComponent getActivityComponent()
    {
        return DaggerActivityComponent.builder()
                .appComponent(GeeksApp.getAppComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    @Override
    protected void onViewCreated() {
        super.onViewCreated();

        initInject();

        if (presenter != null)
        {
            presenter.attachView(this);
        }
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    public void showErrorMsg(String errorMsg) {
        CommonUtils.showSnackMessage(this, errorMsg);
    }

    @Override
    public void showNormal() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showLogoutView() {

    }

    @Override
    public void showCollectFail() {
        CommonUtils.showSnackMessage(this, getString(R.string.collect_fail));
    }

    @Override
    public void showCancelCollectFail() {
        CommonUtils.showSnackMessage(this, getString(R.string.cancel_collect_fail));
    }

    @Override
    public void showCollectSuccess() {

    }

    @Override
    public void showCancelCollectSuccess() {

    }

    protected abstract void initInject();
}
