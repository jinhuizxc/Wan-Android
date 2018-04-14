package com.hsf1002.sky.wanandroid.presenter.hierarchy;

import com.hsf1002.sky.wanandroid.base.presenter.BasePresenter;
import com.hsf1002.sky.wanandroid.component.RxBus;
import com.hsf1002.sky.wanandroid.contract.hierarchy.KnowledgeHierarchyListContract;
import com.hsf1002.sky.wanandroid.core.DataManager;
import com.hsf1002.sky.wanandroid.core.bean.BaseResponse;
import com.hsf1002.sky.wanandroid.core.bean.main.collect.FeedArticleData;
import com.hsf1002.sky.wanandroid.core.bean.main.collect.FeedArticleListData;
import com.hsf1002.sky.wanandroid.core.event.CollectEvent;
import com.hsf1002.sky.wanandroid.core.event.KnowledgeJumpTopEvent;
import com.hsf1002.sky.wanandroid.core.event.ReloadDetailEvent;
import com.hsf1002.sky.wanandroid.utils.RxUtils;
import com.hsf1002.sky.wanandroid.widget.BaseObserver;

import javax.inject.Inject;

/**
 * Created by hefeng on 18-4-14.
 */

public class KnowledgeHierarchyListPresenter extends BasePresenter<KnowledgeHierarchyListContract.View> implements KnowledgeHierarchyListContract.Presenter {

    private DataManager dataManager;

    @Inject
    public KnowledgeHierarchyListPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(KnowledgeHierarchyListContract.View view) {
        super.attachView(view);
        registerEvent();
    }

    private void registerEvent()
    {
        addSubscribe(RxBus.getDefault().toFlowable(CollectEvent.class)
            .filter(collectEvent -> !collectEvent.isCancelCollectSuccess())
            .subscribe(collectEvent ->  view.showCollectSuccess()));

        addSubscribe(RxBus.getDefault().toFlowable(CollectEvent.class)
            .filter(CollectEvent::isCancelCollectSuccess)
            .subscribe(collectEvent -> view.showCancelCollectSuccess()));

        addSubscribe(RxBus.getDefault().toFlowable(KnowledgeJumpTopEvent.class)
            .subscribe(knowledgeJumpTopEvent -> view.showJumpTheTop()));

        addSubscribe(RxBus.getDefault().toFlowable(ReloadDetailEvent.class)
            .subscribe(reloadDetailEvent -> view.showReloadDetailEvent()));
    }

    @Override
    public void getKnowledgeHierarchyDetailData(int page, int cid) {
        addSubscribe(dataManager.getKnowledgeHierarchyDetailData(page, cid)
            .compose(RxUtils.rxSchedulerHelper())
            .subscribeWith(new BaseObserver<BaseResponse<FeedArticleListData>>(view)
            {
                @Override
                public void onNext(BaseResponse<FeedArticleListData> feedArticleListDataBaseResponse) {
                    if (feedArticleListDataBaseResponse.getErrorCode() == BaseResponse.SUCCESS)
                    {
                        view.showKnowledgeHierarchyDetailData(feedArticleListDataBaseResponse);
                    }
                    else
                    {
                        view.showKnowledgeHierarchyDetailDataFail();
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
                    else {
                        view.showCollectFail();
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
