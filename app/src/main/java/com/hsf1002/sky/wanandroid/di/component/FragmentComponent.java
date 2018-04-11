package com.hsf1002.sky.wanandroid.di.component;

import android.app.Activity;

import com.hsf1002.sky.wanandroid.di.module.FragmentModule;
import com.hsf1002.sky.wanandroid.di.scope.FragmentScope;
import com.hsf1002.sky.wanandroid.ui.hierarchy.fragment.KnowledgeHierarchyFragment;
import com.hsf1002.sky.wanandroid.ui.hierarchy.fragment.KnowledgeHierarchyListFragment;
import com.hsf1002.sky.wanandroid.ui.main.fragment.SearchDialogFragment;
import com.hsf1002.sky.wanandroid.ui.mainpager.fragment.MainPagerFragment;
import com.hsf1002.sky.wanandroid.ui.navigation.fragment.NavigationFragment;
import com.hsf1002.sky.wanandroid.ui.project.fragment.ProjectFragment;
import com.hsf1002.sky.wanandroid.ui.project.fragment.ProjectListFragment;

import dagger.Component;

/**
 * Created by hefeng on 18-4-11.
 */

@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    Activity getActivity();
    void inject(MainPagerFragment mainPagerFragment);
    void inject(KnowledgeHierarchyFragment knowledgeHierarchyFragment);
    void inject(KnowledgeHierarchyListFragment knowledgeHierarchyListFragment);
    void inject(ProjectFragment projectFragment);
    void inject(NavigationFragment navigationFragment);
    void inject(ProjectListFragment projectListFragment);
    void inject(SearchDialogFragment searchDialogFragment);
}
