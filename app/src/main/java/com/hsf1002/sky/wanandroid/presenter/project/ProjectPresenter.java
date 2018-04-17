package com.hsf1002.sky.wanandroid.presenter.project;

import com.hsf1002.sky.wanandroid.base.presenter.BasePresenter;
import com.hsf1002.sky.wanandroid.contract.project.ProjectContract;
import com.hsf1002.sky.wanandroid.core.DataManager;
import com.hsf1002.sky.wanandroid.core.bean.BaseResponse;
import com.hsf1002.sky.wanandroid.core.bean.project.ProjectClassifyData;
import com.hsf1002.sky.wanandroid.utils.RxUtils;
import com.hsf1002.sky.wanandroid.widget.BaseObserver;

import java.util.List;

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
        addSubscribe(dataManager.getProjectClassifyData()
            .compose(RxUtils.rxSchedulerHelper())
            .subscribeWith(new BaseObserver<BaseResponse<List<ProjectClassifyData>>>(view)
            {
                @Override
                public void onNext(BaseResponse<List<ProjectClassifyData>> listBaseResponse) {
                    if (listBaseResponse.getErrorCode() == BaseResponse.SUCCESS)
                    {
                        view.showProjectClassifyData(listBaseResponse);
                    }
                    else
                    {
                        view.showProjectClassifyDataFail();
                    }
                }
            }));
    }
}
