package com.hsf1002.sky.wanandroid.ui.hierarchy.fragment;

import android.os.Bundle;

import com.hsf1002.sky.wanandroid.app.Constants;
import com.hsf1002.sky.wanandroid.base.fragment.AbstractRootFragment;
import com.hsf1002.sky.wanandroid.contract.hierarchy.KnowledgeHierarchyContract;
import com.hsf1002.sky.wanandroid.presenter.hierarchy.KnowledgeHierarchyPresenter;

/**
 * Created by hefeng on 18-4-10.
 */

public class KnowledgeHierarchyFragment  extends AbstractRootFragment<KnowledgeHierarchyPresenter> implements KnowledgeHierarchyContract.View{

    public static KnowledgeHierarchyFragment getInstance(String param1, String param2) {
        KnowledgeHierarchyFragment fragment = new KnowledgeHierarchyFragment();
        Bundle args = new Bundle();
        args.putString(Constants.ARG_PARAM1, param1);
        args.putString(Constants.ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }
}
