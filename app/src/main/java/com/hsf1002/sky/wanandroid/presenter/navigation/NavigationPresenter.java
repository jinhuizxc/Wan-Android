package com.hsf1002.sky.wanandroid.presenter.navigation;

import com.hsf1002.sky.wanandroid.base.presenter.BasePresenter;
import com.hsf1002.sky.wanandroid.contract.navigation.NavigationContract;
import com.hsf1002.sky.wanandroid.core.DataManager;
import com.hsf1002.sky.wanandroid.core.bean.BaseResponse;
import com.hsf1002.sky.wanandroid.core.bean.navigation.NavigationListData;
import com.hsf1002.sky.wanandroid.utils.RxUtils;
import com.hsf1002.sky.wanandroid.widget.BaseObserver;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by hefeng on 18-4-10.
 */

public class NavigationPresenter extends BasePresenter<NavigationContract.View> implements NavigationContract.Presenter {
    private DataManager dataManager;

    @Inject
    public NavigationPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(NavigationContract.View view) {
        super.attachView(view);
    }

    @Override
    public void getNavigationListData() {
        addSubscribe(dataManager.getNavigationListData()
            .compose(RxUtils.rxSchedulerHelper())
            .subscribeWith(new BaseObserver<BaseResponse<List<NavigationListData>>>(view)
            {
                @Override
                public void onNext(BaseResponse<List<NavigationListData>> listBaseResponse) {
                    if (listBaseResponse.getErrorCode() == BaseResponse.SUCCESS)
                    {
                        view.showNavigationListData(listBaseResponse);
                    }
                    else
                    {
                        view.showNavigationListFail();
                    }
                }
            }));
    }
}
