package com.hsf1002.sky.wanandroid.contract.main;

import com.hsf1002.sky.wanandroid.base.view.AbstractPresenter;
import com.hsf1002.sky.wanandroid.base.view.BaseView;
import com.hsf1002.sky.wanandroid.core.bean.BaseResponse;
import com.hsf1002.sky.wanandroid.core.bean.main.collect.FeedArticleListData;
import com.tbruyelle.rxpermissions2.RxPermissions;

/**
 * Created by hefeng on 18-4-13.
 */

public interface ArticleDetailContract {
    interface View extends BaseView{
        /**
         * Show collect article data
         *
         * @param feedArticleListResponse BaseResponse<FeedArticleListData>
         */
        void showCollectArticleData(BaseResponse<FeedArticleListData> feedArticleListResponse);

        /**
         * Show cancel collect article data
         *
         * @param feedArticleListResponse BaseResponse<FeedArticleListData>
         */
        void showCancelCollectArticleData(BaseResponse<FeedArticleListData> feedArticleListResponse);

        /**
         * Share event
         */
        void shareEvent();

        /**
         * Share error
         */
        void shareError();
    }

    interface Presenter extends AbstractPresenter<View>
    {
        /**
         * Add collect article
         *
         * @param id article id
         */
        void addCollectArticle(int id);

        /**
         * Cancel collect article
         *
         * @param id article id
         */
        void cancelCollectArticle(int id);

        /**
         * Cancel collect article
         *
         * @param id article id
         */
        void cancelCollectPageArticle(int id);

        /**
         * verify share permission
         *
         * @param rxPermissions RxPermissions
         */
        void shareEventPermissionVerify(RxPermissions rxPermissions);
    }
}
