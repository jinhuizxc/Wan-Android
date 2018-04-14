package com.hsf1002.sky.wanandroid.presenter.hierarchy;

import com.hsf1002.sky.wanandroid.base.presenter.BasePresenter;
import com.hsf1002.sky.wanandroid.component.RxBus;
import com.hsf1002.sky.wanandroid.contract.hierarchy.KnowledgeHierarchyContract;
import com.hsf1002.sky.wanandroid.core.DataManager;
import com.hsf1002.sky.wanandroid.core.bean.BaseResponse;
import com.hsf1002.sky.wanandroid.core.bean.hierarchy.KnowledgeHierarchyData;
import com.hsf1002.sky.wanandroid.utils.RxUtils;
import com.hsf1002.sky.wanandroid.widget.BaseObserver;

import java.util.List;

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
        addSubscribe(dataManager.getKnowledgeHierarchyData()
            .compose(RxUtils.rxSchedulerHelper())
            .subscribeWith(new BaseObserver<BaseResponse<List<KnowledgeHierarchyData>>>(view)
            {
                @Override
                public void onNext(BaseResponse<List<KnowledgeHierarchyData>> listBaseResponse) {
                    if (listBaseResponse.getErrorCode() == BaseResponse.SUCCESS)
                    {
                        view.showKnowledgeHierarchyData(listBaseResponse);
                    }
                    else
                    {
                        view.showKnowledgeHierarchyDetailDataFail();
                    }
                }
            }));
    }
}
