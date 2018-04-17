package com.hsf1002.sky.wanandroid.contract.project;

import com.hsf1002.sky.wanandroid.base.presenter.AbstractPresenter;
import com.hsf1002.sky.wanandroid.base.view.BaseView;
import com.hsf1002.sky.wanandroid.core.bean.BaseResponse;
import com.hsf1002.sky.wanandroid.core.bean.project.ProjectClassifyData;

import java.util.List;

/**
 * Created by hefeng on 18-4-10.
 */

public interface ProjectContract {
    interface View extends BaseView
    {
        /**
         * Show project classify data
         *
         * @param projectClassifyResponse List<ProjectClassifyData>
         */
        void showProjectClassifyData(BaseResponse<List<ProjectClassifyData>> projectClassifyResponse);

        /**
         * Show project calssify data fail
         */
        void showProjectClassifyDataFail();
    }

    interface Presenter extends AbstractPresenter<View>
    {
        void getProjectClassifyData();
    }
}
