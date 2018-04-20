package com.hsf1002.sky.wanandroid.presenter.main;


import com.hsf1002.sky.wanandroid.base.presenter.BasePresenter;
import com.hsf1002.sky.wanandroid.component.RxBus;
import com.hsf1002.sky.wanandroid.contract.main.SearchListContract;
import com.hsf1002.sky.wanandroid.core.DataManager;
import com.hsf1002.sky.wanandroid.core.bean.BaseResponse;
import com.hsf1002.sky.wanandroid.core.bean.main.collect.FeedArticleData;
import com.hsf1002.sky.wanandroid.core.bean.main.collect.FeedArticleListData;
import com.hsf1002.sky.wanandroid.core.event.CollectEvent;
import com.hsf1002.sky.wanandroid.utils.RxUtils;
import com.hsf1002.sky.wanandroid.widget.BaseObserver;

import javax.inject.Inject;


/**
 * Created by hefeng on 18-4-19.
 */

public class SearchListPresenter extends BasePresenter<SearchListContract.View> implements SearchListContract.Presenter {

    private DataManager dataManager;

    @Inject
    public SearchListPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(SearchListContract.View view) {
        super.attachView(view);

        registerEvent();
    }

    private void registerEvent()
    {
        addSubscribe(RxBus.getDefault().toFlowable(CollectEvent.class)
            .filter(collectEvent -> !collectEvent.isCancelCollectSuccess())
            .subscribe(collectEvent -> view.showCollectSuccess()));

        addSubscribe(RxBus.getDefault().toFlowable(CollectEvent.class)
            .filter(CollectEvent::isCancelCollectSuccess)
            .subscribe(collectEvent -> view.showCancelCollectSuccess()));
    }

    @Override
    public void getSearchList(int page, String k) {
        addSubscribe(dataManager.getSearchList(page, k)
            .compose(RxUtils.rxSchedulerHelper())
            .subscribeWith(new BaseObserver<BaseResponse<FeedArticleListData>>(view)
            {
                @Override
                public void onNext(BaseResponse<FeedArticleListData> feedArticleListDataBaseResponse) {
                    if (feedArticleListDataBaseResponse.getErrorCode() == BaseResponse.SUCCESS)
                    {
                        view.showSearchList(feedArticleListDataBaseResponse);
                    }
                    else
                    {
                        view.showSearchListFail();
                    }
                }
            }));
    }

    @Override
    public void addCollectArticle(int position, FeedArticleData feedArticleData) {
        addSubscribe(dataManager.addCollectArticle(feedArticleData.getId())
            .compose(RxUtils.rxSchedulerHelper())
            .subscribeWith(new BaseObserver<BaseResponse<FeedArticleListData>>(view)
            {
                @Override
                public void onNext(BaseResponse<FeedArticleListData> feedArticleListDataBaseResponse) {
                    if (feedArticleListDataBaseResponse.getErrorCode() == BaseResponse.SUCCESS)
                    {
                        feedArticleData.setCollect(true);
                        view.showCollectArticleData(position, feedArticleData, feedArticleListDataBaseResponse);
                    }
                    else
                    {
                        view.showCancelCollectFail();
                    }
                }
            }));
    }

    @Override
    public void cancelCollectArticle(int position, FeedArticleData feedArticleData) {
        addSubscribe(dataManager.cancelCollectArticle(feedArticleData.getId())
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new BaseObserver<BaseResponse<FeedArticleListData>>(view)
                {
                    @Override
                    public void onNext(BaseResponse<FeedArticleListData> feedArticleListDataBaseResponse) {
                        if (feedArticleListDataBaseResponse.getErrorCode() == BaseResponse.SUCCESS)
                        {
                            feedArticleData.setCollect(false);
                            view.showCancelCollectArticleData(position, feedArticleData, feedArticleListDataBaseResponse);
                        }
                        else
                        {
                            view.showCancelCollectFail();
                        }
                    }
                }));
    }
}
