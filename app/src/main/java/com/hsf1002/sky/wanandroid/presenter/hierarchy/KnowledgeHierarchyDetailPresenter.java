package com.hsf1002.sky.wanandroid.presenter.hierarchy;

import com.hsf1002.sky.wanandroid.base.presenter.BasePresenter;
import com.hsf1002.sky.wanandroid.component.RxBus;
import com.hsf1002.sky.wanandroid.contract.hierarchy.KnowledgeHierarchyDetailContract;
import com.hsf1002.sky.wanandroid.core.DataManager;
import com.hsf1002.sky.wanandroid.core.event.DismissDetailErrorView;
import com.hsf1002.sky.wanandroid.core.event.ShowDetailErrorView;
import com.hsf1002.sky.wanandroid.core.event.SwitchNavigationEvent;
import com.hsf1002.sky.wanandroid.core.event.SwitchProjectEvent;

import javax.inject.Inject;

/**
 * Created by hefeng on 18-4-14.
 */

public class KnowledgeHierarchyDetailPresenter extends BasePresenter<KnowledgeHierarchyDetailContract.View>  implements KnowledgeHierarchyDetailContract.Presenter{

    private DataManager dataManager;

    @Inject
    public KnowledgeHierarchyDetailPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(KnowledgeHierarchyDetailContract.View view) {
        super.attachView(view);

        registerEvent();
    }

    private void registerEvent()
    {
        addSubscribe(RxBus.getDefault().toFlowable(DismissDetailErrorView.class)
            .subscribe(dismissDetailErrorView -> view.showDismissDetailErrorView()));

        addSubscribe(RxBus.getDefault().toFlowable(ShowDetailErrorView.class)
            .subscribe(showDetailErrorView -> view.showDetailErrorView()));

        addSubscribe(RxBus.getDefault().toFlowable(SwitchProjectEvent.class)
            .subscribe(switchProjectEvent -> view.showSwitchProject()));

        addSubscribe(RxBus.getDefault().toFlowable(SwitchNavigationEvent.class)
            .subscribe(switchNavigationEvent -> view.showSwitchNavigation()));
    }

}
