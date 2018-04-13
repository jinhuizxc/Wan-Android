package com.hsf1002.sky.wanandroid.presenter.main;

import android.Manifest;

import com.hsf1002.sky.wanandroid.base.presenter.BasePresenter;
import com.hsf1002.sky.wanandroid.contract.main.ArticleDetailContract;
import com.hsf1002.sky.wanandroid.core.DataManager;
import com.hsf1002.sky.wanandroid.core.bean.BaseResponse;
import com.hsf1002.sky.wanandroid.core.bean.main.collect.FeedArticleListData;
import com.hsf1002.sky.wanandroid.utils.RxUtils;
import com.hsf1002.sky.wanandroid.widget.BaseObserver;
import com.tbruyelle.rxpermissions2.RxPermissions;

import javax.inject.Inject;

/**
 * Created by hefeng on 18-4-13.
 */

public class ArticleDetailPresenter extends BasePresenter<ArticleDetailContract.View> implements ArticleDetailContract.Presenter {
    DataManager dataManager;

    @Inject
    public ArticleDetailPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void addCollectArticle(int id) {
        addSubscribe(dataManager.addCollectArticle(id)
            .compose(RxUtils.rxSchedulerHelper())
            .subscribeWith(new BaseObserver<BaseResponse<FeedArticleListData>>(view)
            {
                @Override
                public void onNext(BaseResponse<FeedArticleListData> feedArticleListDataBaseResponse) {
                    if (feedArticleListDataBaseResponse.getErrorCode() == BaseResponse.SUCCESS)
                    {
                        view.showCollectArticleData(feedArticleListDataBaseResponse);
                    }
                    else
                    {
                        view.showCollectFail();
                    }
                }
            }));
    }

    @Override
    public void cancelCollectArticle(int id) {
        addSubscribe(dataManager.cancelCollectArticle(id)
            .compose(RxUtils.rxSchedulerHelper())
            .subscribeWith(new BaseObserver<BaseResponse<FeedArticleListData>>(view)
            {
                @Override
                public void onNext(BaseResponse<FeedArticleListData> feedArticleListDataBaseResponse) {
                    if (feedArticleListDataBaseResponse.getErrorCode() == BaseResponse.SUCCESS)
                    {
                        view.showCancelCollectArticleData(feedArticleListDataBaseResponse);
                    }
                    else
                    {
                        view.showCancelCollectFail();
                    }
                }
            }));
    }

    @Override
    public void cancelCollectPageArticle(int id) {
        addSubscribe(dataManager.cancelCollectPageArticle(id)
            .compose(RxUtils.rxSchedulerHelper())
            .subscribeWith(new BaseObserver<BaseResponse<FeedArticleListData>>(view)
            {
                @Override
                public void onNext(BaseResponse<FeedArticleListData> feedArticleListDataBaseResponse) {
                    if (feedArticleListDataBaseResponse.getErrorCode() == BaseResponse.SUCCESS)
                    {
                        view.showCancelCollectArticleData(feedArticleListDataBaseResponse);
                    }
                    else
                    {
                        view.showCancelCollectFail();
                    }
                }
            }));
    }

    @Override
    public void shareEventPermissionVerify(RxPermissions rxPermissions) {
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted ->
                {
                   if (granted)
                   {
                       view.shareEvent();
                   }
                   else
                   {
                       view.shareError();
                   }
                });
    }
}
