package com.hsf1002.sky.wanandroid.di.module;

import com.hsf1002.sky.wanandroid.app.GeeksApp;
import com.hsf1002.sky.wanandroid.contract.project.ProjectContract;
import com.hsf1002.sky.wanandroid.core.DataManager;
import com.hsf1002.sky.wanandroid.core.db.DbHelper;
import com.hsf1002.sky.wanandroid.core.db.GreenDaoHelper;
import com.hsf1002.sky.wanandroid.core.http.HttpHelper;
import com.hsf1002.sky.wanandroid.core.http.RetrofitHelper;
import com.hsf1002.sky.wanandroid.core.prefs.PreferenceHelper;
import com.hsf1002.sky.wanandroid.core.prefs.PreferenceHelperImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hefeng on 18-4-8.
 */

@Module
public class AppModule {
    private final GeeksApp application;

    public AppModule(GeeksApp application) {
        this.application = application;
    }

    @Provides
    @Singleton
    GeeksApp provideApplicationContext()
    {
        return application;
    }

    @Provides
    @Singleton
    HttpHelper provideHttpHelper(RetrofitHelper retrofitHelper)
    {
        return retrofitHelper;
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(GreenDaoHelper dbHelper)
    {
        return dbHelper;
    }

    @Provides
    @Singleton
    PreferenceHelper providePreferenceHelper(PreferenceHelperImpl preferenceHelper)
    {
        return preferenceHelper;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(HttpHelper httpHelper, DbHelper dbHelper, PreferenceHelper preferenceHelper)
    {
        return new DataManager(httpHelper, dbHelper, preferenceHelper);
    }
}
