package com.hsf1002.sky.wanandroid.presenter.project;

import com.hsf1002.sky.wanandroid.base.presenter.BasePresenter;
import com.hsf1002.sky.wanandroid.contract.project.ProjectListContract;
import com.hsf1002.sky.wanandroid.core.DataManager;
import com.hsf1002.sky.wanandroid.core.bean.main.collect.FeedArticleData;

import javax.inject.Inject;

/**
 * Created by hefeng on 18-4-16.
 */

public class ProjectListPresenter extends BasePresenter<ProjectListContract.View> implements ProjectListContract.Presenter {

    private DataManager dataManager;

    @Inject
    public ProjectListPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void getProjectListData(int page, int cid) {

    }

    @Override
    public void addCollectOutsideArticle(int position, FeedArticleData feedArticleData) {

    }

    @Override
    public void cancelCollectArticle(int position, FeedArticleData feedArticleData) {

    }
}
