package com.hsf1002.sky.wanandroid.utils;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by hefeng on 18-4-11.
 */

public class RxUtils {
    public static <T>ObservableTransformer<T, T> rxSchedulerHelper()
    {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
