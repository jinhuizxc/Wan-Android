package com.hsf1002.sky.wanandroid.di.component;

import com.hsf1002.sky.wanandroid.app.GeeksApp;
import com.hsf1002.sky.wanandroid.core.DataManager;
import com.hsf1002.sky.wanandroid.core.db.GreenDaoHelper;
import com.hsf1002.sky.wanandroid.core.http.RetrofitHelper;
import com.hsf1002.sky.wanandroid.core.prefs.PreferenceHelper;
import com.hsf1002.sky.wanandroid.core.prefs.PreferenceHelperImpl;
import com.hsf1002.sky.wanandroid.di.module.AppModule;
import com.hsf1002.sky.wanandroid.di.module.HttpModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by hefeng on 18-4-8.
 */

@Singleton
@Component(modules = {AppModule.class, HttpModule.class})
public interface AppComponent {
    GeeksApp getContext();

    DataManager getDataManager();

    RetrofitHelper getRetrofitHelper();

    GreenDaoHelper getGreenDaoHelper();

    PreferenceHelperImpl getPreferenceHelper();
}
