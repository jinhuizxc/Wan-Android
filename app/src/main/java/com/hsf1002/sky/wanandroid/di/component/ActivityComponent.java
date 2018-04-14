package com.hsf1002.sky.wanandroid.di.component;

import android.app.Activity;

import com.hsf1002.sky.wanandroid.ui.hierarchy.activity.KnowledgeHierarchyDetailActivity;
import com.hsf1002.sky.wanandroid.ui.main.activity.ArticleDetailActivity;
import com.hsf1002.sky.wanandroid.ui.main.activity.MainActivity;
import com.hsf1002.sky.wanandroid.di.module.ActivityModule;
import com.hsf1002.sky.wanandroid.di.scope.ActivityScope;
import com.hsf1002.sky.wanandroid.ui.main.activity.SplashActivity;

import dagger.Component;

/**
 * Created by hefeng on 18-4-8.
 */

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    Activity getActivity();

    void inject(MainActivity mainActivity);

    void inject(SplashActivity splashActivity);

    void inject(ArticleDetailActivity articleDetailActivity);

    void inject(KnowledgeHierarchyDetailActivity knowledgeHierarchyDetailActivity);
}
