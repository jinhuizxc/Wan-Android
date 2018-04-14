package com.hsf1002.sky.wanandroid.contract.hierarchy;

import com.hsf1002.sky.wanandroid.base.presenter.AbstractPresenter;
import com.hsf1002.sky.wanandroid.base.view.BaseView;

/**
 * Created by hefeng on 18-4-14.
 */

public interface KnowledgeHierarchyDetailContract {

    interface View extends BaseView
    {
        /**
         * Show dismiss detail error view
         */
        void showDismissDetailErrorView();

        /**
         * Show detail error view
         */
        void showDetailErrorView();

        /**
         * Show switch project
         */
        void showSwitchProject();

        /**
         * Show switch navigation
         */
        void showSwitchNavigation();
    }

    interface Presenter extends AbstractPresenter<View> {

    }
}
