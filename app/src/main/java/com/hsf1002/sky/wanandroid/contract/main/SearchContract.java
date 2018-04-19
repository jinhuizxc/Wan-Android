package com.hsf1002.sky.wanandroid.contract.main;

import com.hsf1002.sky.wanandroid.base.presenter.AbstractPresenter;
import com.hsf1002.sky.wanandroid.base.view.BaseView;
import com.hsf1002.sky.wanandroid.core.bean.BaseResponse;
import com.hsf1002.sky.wanandroid.core.bean.main.search.TopSearchData;
import com.hsf1002.sky.wanandroid.core.bean.main.search.UsefulSiteData;
import com.hsf1002.sky.wanandroid.core.dao.HistoryData;

import java.util.List;

/**
 * Created by hefeng on 18-4-11.
 */

public interface SearchContract {
    interface View extends BaseView {

        /**
         * Show history data
         *
         * @param historyDataList List<HistoryData>
         */
        void showHistoryData(List<HistoryData> historyDataList);

        /**
         * Show top search data
         *
         * @param topSearchDataResponse BaseResponse<List<TopSearchData>>
         */
        void showTopSearchData(BaseResponse<List<TopSearchData>> topSearchDataResponse);

        /**
         * Show useful sites
         *
         * @param usefulSitesResponse BaseResponse<List<UsefulSiteData>>
         */
        void showUsefulSites(BaseResponse<List<UsefulSiteData>> usefulSitesResponse);

        /**
         * Show top search data fail
         */
        void showTopSearchDataFail();

        /**
         * Show useful sites data fail
         */
        void showUsefulSitesDataFail();

        /**
         * Judge to the search list activity
         */
        void judgeToTheSearchListActivity();

    }

    interface Presenter extends AbstractPresenter<View> {

        /**
         * Add history data
         *
         * @param data history data
         */
        void addHistoryData(String data);

        /**
         * 热搜
         */
        void getTopSearchData();

        /**
         * 常用网站
         */
        void getUsefulSites();

        /**
         * Clear history data
         */
        void clearHistoryData();
    }
}
