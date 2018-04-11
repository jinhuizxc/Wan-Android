package com.hsf1002.sky.wanandroid.base.fragment;

import com.hsf1002.sky.wanandroid.base.view.AbstractPresenter;
import com.hsf1002.sky.wanandroid.base.view.BaseView;

/**
 * Created by hefeng on 18-4-11.
 */

public class BaseDialogFragment<T extends AbstractPresenter> extends AbstractSimpleDialogFragment implements BaseView {
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
    public void showLoginView() {

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
