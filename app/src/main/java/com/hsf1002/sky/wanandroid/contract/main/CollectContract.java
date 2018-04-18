package com.hsf1002.sky.wanandroid.contract.main;

import com.hsf1002.sky.wanandroid.base.presenter.AbstractPresenter;
import com.hsf1002.sky.wanandroid.base.view.BaseView;
import com.hsf1002.sky.wanandroid.core.bean.BaseResponse;
import com.hsf1002.sky.wanandroid.core.bean.main.collect.FeedArticleData;
import com.hsf1002.sky.wanandroid.core.bean.main.collect.FeedArticleListData;

/**
 * Created by hefeng on 18-4-18.
 */

public interface CollectContract {
    interface View extends BaseView {

        /**
         * Show collect list
         *
         * @param feedArticleListResponse BaseResponse<FeedArticleListData>
         */
        void showCollectList(BaseResponse<FeedArticleListData> feedArticleListResponse);

        /**
         * Show cancel collect article data
         *
         * @param position Position
         * @param feedArticleData FeedArticleData
         * @param feedArticleListResponse BaseResponse<FeedArticleListData>
         */
        void showCancelCollectPageArticleData(int position, FeedArticleData feedArticleData, BaseResponse<FeedArticleListData> feedArticleListResponse);

        /**
         * Show collect list fail
         */
        void showCollectListFail();

        /**
         * Show Refresh event
         */
        void showRefreshEvent();

    }

    interface Presenter extends AbstractPresenter<View> {

        /**
         * Get collect list
         *
         * @param page page number
         */
        void getCollectList(int page);

        /**
         * Cancel collect article
         *
         * @param position Position
         * @param feedArticleData FeedArticleData
         */
        void cancelCollectPageArticle(int position, FeedArticleData feedArticleData);


    }
}
