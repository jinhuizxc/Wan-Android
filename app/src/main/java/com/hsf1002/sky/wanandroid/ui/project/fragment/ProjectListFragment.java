package com.hsf1002.sky.wanandroid.ui.project.fragment;

import android.os.Bundle;

import com.hsf1002.sky.wanandroid.R;
import com.hsf1002.sky.wanandroid.app.Constants;
import com.hsf1002.sky.wanandroid.base.fragment.BaseFragment;
import com.hsf1002.sky.wanandroid.contract.project.ProjectListContract;
import com.hsf1002.sky.wanandroid.core.bean.BaseResponse;
import com.hsf1002.sky.wanandroid.core.bean.main.collect.FeedArticleData;
import com.hsf1002.sky.wanandroid.core.bean.main.collect.FeedArticleListData;
import com.hsf1002.sky.wanandroid.core.bean.project.ProjectListData;
import com.hsf1002.sky.wanandroid.presenter.project.ProjectListPresenter;
import com.hsf1002.sky.wanandroid.presenter.project.ProjectPresenter;

/**
 * Created by hefeng on 18-4-11.
 */

public class ProjectListFragment extends BaseFragment<ProjectListPresenter> implements ProjectListContract.View {
    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_project_list;
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
    }

    public static ProjectListFragment getInstance(int param1, String param2) {
        ProjectListFragment fragment = new ProjectListFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.ARG_PARAM1, param1);
        args.putString(Constants.ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void showProjectListData(BaseResponse<ProjectListData> projectListResponse) {

    }

    @Override
    public void showCollectOutsideArticle(int position, FeedArticleData feedArticleData, BaseResponse<FeedArticleListData> feedArticleListResponse) {

    }

    @Override
    public void showCancelCollectArticleData(int position, FeedArticleData feedArticleData, BaseResponse<FeedArticleListData> feedArticleListResponse) {

    }

    @Override
    public void showProjectListFail() {

    }

    @Override
    public void showJumpToTheTop() {

    }
}
