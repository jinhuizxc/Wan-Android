package com.hsf1002.sky.wanandroid.base.activity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.hsf1002.sky.wanandroid.app.GeeksApp;
import com.hsf1002.sky.wanandroid.base.view.AbstractPresenter;
import com.hsf1002.sky.wanandroid.base.view.BaseView;
import com.hsf1002.sky.wanandroid.di.component.ActivityComponent;
import com.hsf1002.sky.wanandroid.di.component.DaggerActivityComponent;
import com.hsf1002.sky.wanandroid.di.module.ActivityModule;

import javax.inject.Inject;

/**
 * Created by hefeng on 18-4-8.
 */

public class BaseActivity<T extends AbstractPresenter> extends AbstractSimpleActivity implements BaseView {

    @Inject
    protected T presenter;

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

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
                .appComponent(GeeksApp.getmAppComponent())
                .activityModule(new ActivityModule(this))
                .build();
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

    }

    @Override
    public void showCancelCollectFail() {

    }

    @Override
    public void showCollectSuccess() {

    }

    @Override
    public void showCancelCollectSuccess() {

    }
}
