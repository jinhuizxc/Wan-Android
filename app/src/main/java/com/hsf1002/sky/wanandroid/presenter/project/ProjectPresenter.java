package com.hsf1002.sky.wanandroid.presenter.project;

import com.hsf1002.sky.wanandroid.base.presenter.BasePresenter;
import com.hsf1002.sky.wanandroid.contract.project.ProjectContract;
import com.hsf1002.sky.wanandroid.core.DataManager;

import javax.inject.Inject;

/**
 * Created by hefeng on 18-4-10.
 */

public class ProjectPresenter extends BasePresenter<ProjectContract.View> implements ProjectContract.Presenter {

    private DataManager dataManager;

    @Inject
    public ProjectPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(ProjectContract.View view) {
        super.attachView(view);
    }

    @Override
    public void getProjectClassifyData() {

    }
}
