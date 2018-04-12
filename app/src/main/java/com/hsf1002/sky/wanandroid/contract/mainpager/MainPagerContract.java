package com.hsf1002.sky.wanandroid.contract.mainpager;

import com.hsf1002.sky.wanandroid.base.view.AbstractPresenter;
import com.hsf1002.sky.wanandroid.base.view.BaseView;
import com.hsf1002.sky.wanandroid.core.bean.BaseResponse;
import com.hsf1002.sky.wanandroid.core.bean.main.banner.BannerData;
import com.hsf1002.sky.wanandroid.core.bean.main.collect.FeedArticleData;
import com.hsf1002.sky.wanandroid.core.bean.main.collect.FeedArticleListData;

import java.util.List;

/**
 * Created by hefeng on 18-4-10.
 */

public interface MainPagerContract {
    interface View extends BaseView
    {
        void showAutoLoginSuccess();
        void showAutoLoginFail();

        void showArticleList(BaseResponse<FeedArticleListData> feedArticleListResponse, boolean isRefresh);
        void showArticleListFail();
        void showCollectArticleData(int position, FeedArticleData feedArticleData, BaseResponse<FeedArticleListData> feedArticleListResponse);
        void showCancelCollectArticleData(int position, FeedArticleData feedArticleData, BaseResponse<FeedArticleListData> feedArticleListResponse);

        void showBannerData(BaseResponse<List<BannerData>> bannerResponse);
        void showBannerDataFail();
    }

    interface Presenter extends AbstractPresenter<View>
    {
        void loadMainPagerData();

        void getFeedArticleList();
        void addCollectArticle(int position, FeedArticleData feedArticleData);
        void cancelCollectArticle(int position, FeedArticleData feedArticleData);

        void getBannerData();

        void autoRefresh();
        void loadMore();
    }
}
