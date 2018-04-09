package com.hsf1002.sky.wanandroid.di.module;

import android.app.Activity;

import com.hsf1002.sky.wanandroid.di.component.ActivityComponent;
import com.hsf1002.sky.wanandroid.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hefeng on 18-4-8.
 */

@Module
public class ActivityModule {
    private Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    Activity provideActivity()
    {
        return activity;
    }
}
