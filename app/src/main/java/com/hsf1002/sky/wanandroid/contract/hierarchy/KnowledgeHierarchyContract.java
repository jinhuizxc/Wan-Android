package com.hsf1002.sky.wanandroid.contract.hierarchy;

import com.hsf1002.sky.wanandroid.base.presenter.AbstractPresenter;
import com.hsf1002.sky.wanandroid.base.view.BaseView;
import com.hsf1002.sky.wanandroid.core.bean.BaseResponse;
import com.hsf1002.sky.wanandroid.core.bean.hierarchy.KnowledgeHierarchyData;

import java.util.List;

/**
 * Created by hefeng on 18-4-10.
 */

public interface KnowledgeHierarchyContract {
    interface View extends BaseView
    {
        /**
         * Show Knowledge Hierarchy Data
         *
         * @param knowledgeHierarchyResponse BaseResponse<List<KnowledgeHierarchyData>>
         */
        void showKnowledgeHierarchyData(BaseResponse<List<KnowledgeHierarchyData>> knowledgeHierarchyResponse);

        /**
         * Show knowledge hierarchy detail data fail
         */
        void showKnowledgeHierarchyDetailDataFail();
    }

    interface Presenter extends AbstractPresenter<View>
    {
        void getKnowledgeHierarchyData();
    }
}
