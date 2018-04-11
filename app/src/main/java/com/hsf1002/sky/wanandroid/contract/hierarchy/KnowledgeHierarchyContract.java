package com.hsf1002.sky.wanandroid.contract.hierarchy;

import com.hsf1002.sky.wanandroid.base.view.AbstractPresenter;
import com.hsf1002.sky.wanandroid.base.view.BaseView;

/**
 * Created by hefeng on 18-4-10.
 */

public interface KnowledgeHierarchyContract {
    interface View extends BaseView
    {

    }

    interface Presenter extends AbstractPresenter<View>
    {
        void getKnowledgeHierarchyData();
    }
}
