package com.hsf1002.sky.wanandroid.di.component;

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
}
