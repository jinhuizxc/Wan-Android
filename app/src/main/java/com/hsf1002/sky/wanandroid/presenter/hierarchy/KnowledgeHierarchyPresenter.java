package com.hsf1002.sky.wanandroid.presenter.hierarchy;

import com.hsf1002.sky.wanandroid.base.presenter.BasePresenter;
import com.hsf1002.sky.wanandroid.contract.hierarchy.KnowledgeHierarchyContract;
import com.hsf1002.sky.wanandroid.core.DataManager;

import javax.inject.Inject;

/**
 * Created by hefeng on 18-4-10.
 */

public class KnowledgeHierarchyPresenter extends BasePresenter<KnowledgeHierarchyContract.View> implements KnowledgeHierarchyContract.Presenter {

    private DataManager dataManager;

    @Inject
    public KnowledgeHierarchyPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void getKnowledgeHierarchyData() {

    }
}
