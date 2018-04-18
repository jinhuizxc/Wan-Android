package com.hsf1002.sky.wanandroid.presenter.main;

import com.hsf1002.sky.wanandroid.base.presenter.BasePresenter;
import com.hsf1002.sky.wanandroid.component.RxBus;
import com.hsf1002.sky.wanandroid.contract.main.CollectContract;
import com.hsf1002.sky.wanandroid.core.DataManager;
import com.hsf1002.sky.wanandroid.core.bean.BaseResponse;
import com.hsf1002.sky.wanandroid.core.bean.main.collect.FeedArticleData;
import com.hsf1002.sky.wanandroid.core.bean.main.collect.FeedArticleListData;
import com.hsf1002.sky.wanandroid.core.event.CollectEvent;
import com.hsf1002.sky.wanandroid.utils.RxUtils;
import com.hsf1002.sky.wanandroid.widget.BaseObserver;

import javax.inject.Inject;

/**
 * Created by hefeng on 18-4-18.
 */

public class CollectPresenter extends BasePresenter<CollectContract.View> implements CollectContract.Presenter{

    private DataManager dataManager;

    @Inject
    public CollectPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(CollectContract.View view) {
        super.attachView(view);
        registerEvent();
    }

    private void registerEvent()
    {
        addSubscribe(RxBus.getDefault().toFlowable(CollectEvent.class)
            .subscribe(collectEvent -> view.showRefreshEvent()));
    }

    @Override
    public void getCollectList(int page) {
        addSubscribe(dataManager.getCollectList(page)
            .compose(RxUtils.rxSchedulerHelper())
            .subscribeWith(new BaseObserver<BaseResponse<FeedArticleListData>>(view)
            {
                @Override
                public void onNext(BaseResponse<FeedArticleListData> feedArticleListDataBaseResponse) {
                    if (feedArticleListDataBaseResponse.getErrorCode() == BaseResponse.SUCCESS)
                    {
                        view.showCollectList(feedArticleListDataBaseResponse);
                    }
                    else
                    {
                        view.showCollectListFail();
                    }
                }
            }));
    }

    @Override
    public void cancelCollectPageArticle(int position, FeedArticleData feedArticleData) {
        addSubscribe(dataManager.cancelCollectPageArticle(feedArticleData.getId())
            .compose(RxUtils.rxSchedulerHelper())
            .subscribeWith(new BaseObserver<BaseResponse<FeedArticleListData>>(view)
            {
                @Override
                public void onNext(BaseResponse<FeedArticleListData> feedArticleListDataBaseResponse) {
                    if (feedArticleListDataBaseResponse.getErrorCode() == BaseResponse.SUCCESS)
                    {
                        feedArticleData.setCollect(false);
                        view.showCancelCollectPageArticleData(position, feedArticleData, feedArticleListDataBaseResponse);
                    }
                    else
                    {
                        view.showCancelCollectFail();
                    }
                }
            }));
    }
}
