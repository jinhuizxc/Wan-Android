package com.hsf1002.sky.wanandroid.base.presenter;

import com.hsf1002.sky.wanandroid.base.view.AbstractPresenter;
import com.hsf1002.sky.wanandroid.base.view.BaseView;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by hefeng on 18-4-9.
 */

public class BasePresenter<T extends BaseView> implements AbstractPresenter<T>{

    protected T view;
    private CompositeDisposable compositeDisposable;

    protected void addSubscribe(Disposable disposable)
    {
        if (compositeDisposable == null)
        {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }


    @Override
    public void attachView(T view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;

        if (compositeDisposable != null)
        {
            compositeDisposable.clear();
        }
    }
}
