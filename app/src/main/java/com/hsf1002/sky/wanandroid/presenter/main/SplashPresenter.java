package com.hsf1002.sky.wanandroid.presenter.main;

import com.hsf1002.sky.wanandroid.base.presenter.BasePresenter;
import com.hsf1002.sky.wanandroid.contract.main.SplashContract;
import com.hsf1002.sky.wanandroid.core.DataManager;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by hefeng on 18-4-9.
 */

public class SplashPresenter extends BasePresenter<SplashContract.View> implements SplashContract.Presenter{
    private DataManager dataManager;

    @Inject
    public SplashPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(SplashContract.View view) {
        super.attachView(view);

        long splashTime = 3000;
        addSubscribe(Observable.timer(splashTime, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(along -> view.jumpToMain())
        );
    }
}
