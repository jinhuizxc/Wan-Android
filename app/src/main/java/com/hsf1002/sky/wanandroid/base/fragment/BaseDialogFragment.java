package com.hsf1002.sky.wanandroid.base.fragment;

import android.os.Bundle;

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
 * Created by hefeng on 18-4-11.
 */

public abstract class BaseDialogFragment<T extends AbstractPresenter> extends AbstractSimpleDialogFragment implements BaseView {

    @Inject
    protected T presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initInject();

        if (presenter != null)
        {
            presenter.attachView(this);
        }
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
    public void showErrorMsg(String errorMsg) {
        if (getActivity() != null)
        {
            CommonUtils.showSnackMessage(getActivity(), errorMsg);
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
        if (getActivity() != null)
        {
            CommonUtils.showSnackMessage(getActivity(), getString(R.string.collect_fail));
        }
    }

    @Override
    public void showCancelCollectFail() {
        if (getActivity() != null)
        {
            CommonUtils.showSnackMessage(getActivity(), getString(R.string.cancel_collect_fail));
        }
    }

    @Override
    public void showCollectSuccess() {

    }

    @Override
    public void showCancelCollectSuccess() {

    }

    protected abstract void initInject();
}
