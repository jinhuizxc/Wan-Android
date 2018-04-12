package com.hsf1002.sky.wanandroid.widget;

import android.text.TextUtils;

import com.hsf1002.sky.wanandroid.R;
import com.hsf1002.sky.wanandroid.app.GeeksApp;
import com.hsf1002.sky.wanandroid.base.view.BaseView;
import com.hsf1002.sky.wanandroid.core.http.exception.ServerException;

import io.reactivex.observers.ResourceObserver;
import retrofit2.HttpException;

/**
 * Created by hefeng on 18-4-11.
 */

public abstract class BaseObserver<T>  extends ResourceObserver<T>{

    private BaseView view;
    private String errorMsg;
    private boolean isShowError = true;

    public BaseObserver(BaseView view) {
        this.view = view;
    }

    public BaseObserver(BaseView view, String errorMsg) {
        this.view = view;
        this.errorMsg = errorMsg;
    }

    public BaseObserver(BaseView view, String errorMsg, boolean isShowError) {
        this.view = view;
        this.errorMsg = errorMsg;
        this.isShowError = isShowError;
    }

    @Override
    public void onError(Throwable e) {
        if (view == null)
        {
            return;
        }

        if (errorMsg != null && !TextUtils.isEmpty(errorMsg))
        {
            view.showErrorMsg(errorMsg);
        }
        else if (e instanceof ServerException)
        {
            view.showErrorMsg(e.toString());
        }
        else if (e instanceof HttpException)
        {
            view.showErrorMsg(GeeksApp.getInstance().getString(R.string.http_error));
        }
        else
        {
            view.showErrorMsg(GeeksApp.getInstance().getString(R.string.unKnown_error));
        }

        if (isShowError)
        {
            view.showError();
        }
    }

    @Override
    public void onComplete() {

    }
}
