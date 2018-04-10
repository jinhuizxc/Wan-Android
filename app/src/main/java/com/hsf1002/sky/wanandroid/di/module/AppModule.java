package com.hsf1002.sky.wanandroid.di.module;

import com.hsf1002.sky.wanandroid.app.GeeksApp;

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
}
