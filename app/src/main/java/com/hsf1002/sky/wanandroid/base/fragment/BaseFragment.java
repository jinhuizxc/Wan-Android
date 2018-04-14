package com.hsf1002.sky.wanandroid.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.hsf1002.sky.wanandroid.R;
import com.hsf1002.sky.wanandroid.app.GeeksApp;
import com.hsf1002.sky.wanandroid.base.presenter.AbstractPresenter;
import com.hsf1002.sky.wanandroid.base.view.BaseView;
import com.hsf1002.sky.wanandroid.di.component.DaggerFragmentComponent;
import com.hsf1002.sky.wanandroid.di.component.FragmentComponent;
import com.hsf1002.sky.wanandroid.di.module.FragmentModule;
import com.hsf1002.sky.wanandroid.utils.CommonUtils;

import javax.inject.Inject;

/**
 * Created by hefeng on 18-4-10.
 */

public abstract class BaseFragment<T extends AbstractPresenter> extends AbstractSimpleFragment implements BaseView {

    @Inject
    protected T presenter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initInject();
        if (presenter != null)
        {
            presenter.attachView(this);
        }

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        if (presenter != null)
        {
            presenter.detachView();
        }

        super.onDestroyView();
    }

    public FragmentComponent getFragmentComponent()
    {
        return DaggerFragmentComponent.builder()
                .appComponent(GeeksApp.getAppComponent())
                .fragmentModule(new FragmentModule(this))
                .build();
    }

    @Override
    protected int getLayout() {
        return 0;
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    public void showErrorMsg(String errorMsg) {
        if (isAdded())
        {
            CommonUtils.showSnackMessage(_mActivity, errorMsg);
        }
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
    public void showLoginView() {

    }

    @Override
    public void showLogoutView() {

    }

    @Override
    public void showCollectFail() {
        CommonUtils.showSnackMessage(_mActivity, getString(R.string.collect_fail));

    }

    @Override
    public void showCancelCollectFail() {
        CommonUtils.showSnackMessage(_mActivity, getString(R.string.cancel_collect_fail));
    }

    @Override
    public void showCollectSuccess() {

    }

    @Override
    public void showCancelCollectSuccess() {

    }

    protected abstract void initInject();
}
