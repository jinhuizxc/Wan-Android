package com.hsf1002.sky.wanandroid.ui.hierarchy.fragment;

import com.hsf1002.sky.wanandroid.base.fragment.AbstractRootFragment;
import com.hsf1002.sky.wanandroid.contract.hierarchy.KnowledgeHierarchyContract;
import com.hsf1002.sky.wanandroid.core.bean.BaseResponse;
import com.hsf1002.sky.wanandroid.core.bean.hierarchy.KnowledgeHierarchyData;
import com.hsf1002.sky.wanandroid.presenter.hierarchy.KnowledgeHierarchyPresenter;

import java.util.List;

/**
 * Created by hefeng on 18-4-11.
 */

public class KnowledgeHierarchyListFragment extends AbstractRootFragment<KnowledgeHierarchyPresenter> implements KnowledgeHierarchyContract.View {
    @Override
    protected void initInject() {

    }

    @Override
    public void showKnowledgeHierarchyData(BaseResponse<List<KnowledgeHierarchyData>> knowledgeHierarchyResponse) {

    }

    @Override
    public void showKnowledgeHierarchyDetailDataFail() {

    }
}
