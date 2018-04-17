package com.hsf1002.sky.wanandroid.presenter.project;

import com.hsf1002.sky.wanandroid.base.presenter.BasePresenter;
import com.hsf1002.sky.wanandroid.component.RxBus;
import com.hsf1002.sky.wanandroid.contract.project.ProjectListContract;
import com.hsf1002.sky.wanandroid.core.DataManager;
import com.hsf1002.sky.wanandroid.core.bean.BaseResponse;
import com.hsf1002.sky.wanandroid.core.bean.main.collect.FeedArticleData;
import com.hsf1002.sky.wanandroid.core.bean.main.collect.FeedArticleListData;
import com.hsf1002.sky.wanandroid.core.bean.project.ProjectListData;
import com.hsf1002.sky.wanandroid.core.event.JumpToTheTopEvent;
import com.hsf1002.sky.wanandroid.utils.RxUtils;
import com.hsf1002.sky.wanandroid.widget.BaseObserver;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by hefeng on 18-4-16.
 */

public class ProjectListPresenter extends BasePresenter<ProjectListContract.View> implements ProjectListContract.Presenter {

    private DataManager dataManager;

    @Inject
    public ProjectListPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(ProjectListContract.View view) {
        super.attachView(view);
        registerEvent();
    }

    private void registerEvent()
    {
        addSubscribe(RxBus.getDefault().toFlowable(JumpToTheTopEvent.class)
            .subscribe(jumpToTheTopEvent -> view.showJumpToTheTop()));
    }

    @Override
    public void getProjectListData(int page, int cid)
    {
        addSubscribe(dataManager.getProjectListData(page, cid)
            .compose(RxUtils.rxSchedulerHelper())
            .subscribeWith(new BaseObserver<BaseResponse<ProjectListData>>(view)
            {
                @Override
                public void onNext(BaseResponse<ProjectListData> listBaseResponse) {
                    if (listBaseResponse.getErrorCode() == BaseResponse.SUCCESS)
                    {
                        view.showProjectListData(listBaseResponse);
                    }
                    else
                    {
                        view.showProjectListFail();
                    }
                }
            }
            ));
    }

    @Override
    public void addCollectOutsideArticle(int position, FeedArticleData feedArticleData) {
        addSubscribe(dataManager.addCollectOutsideArticle(feedArticleData.getTitle(), feedArticleData.getAuthor(), feedArticleData.getLink())
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new BaseObserver<BaseResponse<FeedArticleListData>>(view)
                {
                    @Override
                    public void onNext(BaseResponse<FeedArticleListData> feedArticleListDataBaseResponse) {
                        if (feedArticleListDataBaseResponse.getErrorCode() == BaseResponse.SUCCESS)
                        {
                            feedArticleData.setCollect(true);
                            view.showCollectOutsideArticle(position, feedArticleData, feedArticleListDataBaseResponse);
                        }
                        else
                        {
                            view.showCollectFail();
                        }
                    }
                }));
    }

    @Override
    public void cancelCollectArticle(int position, FeedArticleData feedArticleData) {
        addSubscribe(dataManager.cancelCollectArticle(feedArticleData.getId())
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new BaseObserver<BaseResponse<FeedArticleListData>>(view) {
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
               }
               ));
    }
}
