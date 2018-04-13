package com.hsf1002.sky.wanandroid.presenter.mainpager;

import android.util.Log;

import com.hsf1002.sky.wanandroid.app.Constants;
import com.hsf1002.sky.wanandroid.base.presenter.BasePresenter;
import com.hsf1002.sky.wanandroid.component.RxBus;
import com.hsf1002.sky.wanandroid.contract.mainpager.MainPagerContract;
import com.hsf1002.sky.wanandroid.core.DataManager;
import com.hsf1002.sky.wanandroid.core.bean.BaseResponse;
import com.hsf1002.sky.wanandroid.core.bean.main.banner.BannerData;
import com.hsf1002.sky.wanandroid.core.bean.main.collect.FeedArticleData;
import com.hsf1002.sky.wanandroid.core.bean.main.collect.FeedArticleListData;
import com.hsf1002.sky.wanandroid.core.bean.main.login.LoginData;
import com.hsf1002.sky.wanandroid.core.event.CollectEvent;
import com.hsf1002.sky.wanandroid.core.event.LoginEvent;
import com.hsf1002.sky.wanandroid.utils.CommonUtils;
import com.hsf1002.sky.wanandroid.utils.RxUtils;
import com.hsf1002.sky.wanandroid.widget.BaseObserver;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by hefeng on 18-4-10.
 */

public class MainPagerPresenter extends BasePresenter<MainPagerContract.View> implements MainPagerContract.Presenter {

    private DataManager dataManager;
    private boolean isRefresh = true;
    private int currentPage;

    @Inject
    public MainPagerPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(MainPagerContract.View view) {
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

        addSubscribe(RxBus.getDefault().toFlowable(LoginEvent.class)
            .filter(LoginEvent::isLogin)
            .subscribe(loginEvent -> view.showLoginView()));

        addSubscribe(RxBus.getDefault().toFlowable(LoginEvent.class)
            .filter(loginEvent -> !loginEvent.isLogin())
            .subscribe(loginEvent -> view.showLogoutView()));
    }

    @Override
    public void loadMainPagerData() {
        String account = dataManager.getLoginAccount();
        String password = dataManager.getLoginPassword();

        Observable<BaseResponse<LoginData>> loginObservable = dataManager.getLoginData(account, password);
        Observable<BaseResponse<List<BannerData>>> bannerObservable = dataManager.getBannerData();
        Observable<BaseResponse<FeedArticleListData>> articleObservable = dataManager.getFeedArticleList(Constants.FIRST);

        addSubscribe(Observable.zip(loginObservable, bannerObservable, articleObservable,
                (loginResponse, bannerResponse, articleResponse) ->
                {
                    HashMap<String, Object> map = new HashMap<>(3);
                    map.put(Constants.LOGIN_DATA, loginResponse);
                    map.put(Constants.BANNER_DATA, bannerResponse);
                    map.put(Constants.ARTICLE_DATA, articleResponse);

                    return  map;
                }
        ).compose(RxUtils.rxSchedulerHelper())
        .subscribeWith(new BaseObserver<HashMap<String, Object>>(view)
        {
            @Override
            public void onNext(HashMap<String, Object> map) {
                BaseResponse<LoginData> loginDataBaseResponse = CommonUtils.cast(map.get(Constants.LOGIN_DATA));

                if (loginDataBaseResponse.getErrorCode() == BaseResponse.SUCCESS)
                {
                    LoginData loginData = loginDataBaseResponse.getData();

                    dataManager.setLoginAccount(loginData.getUsername());
                    dataManager.setLoginPassword(loginData.getPassword());
                    dataManager.setLoginStatus(true);

                    view.showAutoLoginSuccess();
                }
                else
                {
                    view.showAutoLoginFail();
                }

                view.showBannerData(CommonUtils.cast(map.get(Constants.BANNER_DATA)));
                view.showArticleList(CommonUtils.cast(map.get(Constants.ARTICLE_DATA)), isRefresh);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);

                view.showAutoLoginFail();
            }
        }));
    }


    @Override
    public void getFeedArticleList() {
        addSubscribe(dataManager.getFeedArticleList(currentPage)
            .compose(RxUtils.rxSchedulerHelper())
            .filter(feedArticleListDataBaseResponse -> view != null)
            .subscribeWith(new BaseObserver<BaseResponse<FeedArticleListData>>(view)
            {
                @Override
                public void onNext(BaseResponse<FeedArticleListData> feedArticleListDataBaseResponse) {
                    if (feedArticleListDataBaseResponse.getErrorCode() == BaseResponse.SUCCESS)
                    {
                        view.showArticleList(feedArticleListDataBaseResponse, isRefresh);
                    }
                    else
                    {
                        view.showArticleListFail();
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

    @Override
    public void getBannerData() {
        addSubscribe(dataManager.getBannerData()
            .compose(RxUtils.rxSchedulerHelper())
            .subscribeWith(new BaseObserver<BaseResponse<List<BannerData>>>(view)
            {
                @Override
                public void onNext(BaseResponse<List<BannerData>> bannerResponse) {
                    if (bannerResponse.getErrorCode() == BaseResponse.SUCCESS)
                    {
                        view.showBannerData(bannerResponse);
                    }
                    else
                    {
                        view.showBannerDataFail();
                    }
                }
            }));
    }

    @Override
    public void autoRefresh() {
        isRefresh = true;
        currentPage = 0;
        getBannerData();
        getFeedArticleList();
    }

    @Override
    public void loadMore() {
        isRefresh = false;
        currentPage++;
        getFeedArticleList();
    }
}
