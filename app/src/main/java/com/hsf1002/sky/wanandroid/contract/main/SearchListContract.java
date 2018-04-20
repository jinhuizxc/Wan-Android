package com.hsf1002.sky.wanandroid.contract.main;

import com.hsf1002.sky.wanandroid.base.presenter.AbstractPresenter;
import com.hsf1002.sky.wanandroid.base.view.BaseView;
import com.hsf1002.sky.wanandroid.core.bean.BaseResponse;
import com.hsf1002.sky.wanandroid.core.bean.main.collect.FeedArticleData;
import com.hsf1002.sky.wanandroid.core.bean.main.collect.FeedArticleListData;

/**
 * Created by hefeng on 18-4-19.
 */

public class SearchListContract {

    public interface View extends BaseView {

        /**
         * Show search list
         *
         * @param feedArticleListResponse BaseResponse<FeedArticleListData>
         */
        void showSearchList(BaseResponse<FeedArticleListData> feedArticleListResponse);

        /**
         * Show collect article data
         *
         * @param position Position
         * @param feedArticleData FeedArticleData
         * @param feedArticleListResponse BaseResponse<FeedArticleListData>
         */
        void showCollectArticleData(int position, FeedArticleData feedArticleData, BaseResponse<FeedArticleListData> feedArticleListResponse);

        /**
         * Show cancel collect article data
         *
         * @param position Position
         * @param feedArticleData FeedArticleData
         * @param feedArticleListResponse BaseResponse<FeedArticleListData>
         */
        void showCancelCollectArticleData(int position, FeedArticleData feedArticleData, BaseResponse<FeedArticleListData> feedArticleListResponse);

        /**
         * Show search list fail
         */
        void showSearchListFail();

    }

    public interface Presenter extends AbstractPresenter<View> {

        /**
         * 搜索
         * @param page page
         * @param k POST search key
         */
        void getSearchList(int page, String k);

        /**
         * Add collect article
         *
         * @param position Position
         * @param feedArticleData FeedArticleData
         */
        void addCollectArticle(int position, FeedArticleData feedArticleData);

        /**
         * Cancel collect article
         *
         * @param position Position
         * @param feedArticleData FeedArticleData
         */
        void cancelCollectArticle(int position, FeedArticleData feedArticleData);

    }
}
