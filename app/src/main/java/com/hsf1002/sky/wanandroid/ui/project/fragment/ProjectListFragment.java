package com.hsf1002.sky.wanandroid.ui.project.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

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
import com.hsf1002.sky.wanandroid.ui.project.adapter.ProjectListAdapter;
import com.hsf1002.sky.wanandroid.utils.CommonUtils;
import com.hsf1002.sky.wanandroid.utils.StartActivityUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by hefeng on 18-4-11.
 */

public class ProjectListFragment extends BaseFragment<ProjectListPresenter> implements ProjectListContract.View {

    @BindView(R.id.project_list_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;

    @BindView(R.id.project_list_recycler_view)
    RecyclerView recyclerView;

    private List<FeedArticleData> dataList;
    private ProjectListAdapter adapter;
    private boolean isRefresh = true;
    private int currentPage;
    private int id;


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

        setRefresh();

        Bundle bundle = getArguments();
        id = bundle.getInt(Constants.ARG_PARAM1);

        dataList = new ArrayList<>();

        adapter = new ProjectListAdapter(R.layout.item_project_list, dataList);
        adapter.setOnItemClickListener(((adapter1, view, position) ->
        {
            StartActivityUtils.startArticleDetailActivity(_mActivity,
                    adapter.getData().get(position).getId(),
                    adapter.getData().get(position).getTitle().trim(),
                    adapter.getData().get(position).getLink().trim(),
                    adapter.getData().get(position).isCollect(),
                    false, true);
        }));
        adapter.setOnItemChildClickListener(((adapter1, view, position) ->
        {
            switch (view.getId())
            {
                case R.id.item_project_list_install_tv:
                    if (TextUtils.isEmpty(adapter.getData().get(position).getApkLink()))
                    {
                        return;
                    }
                    else
                    {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(adapter.getData().get(position).getApkLink())));
                    }
                    break;
                default:
                    break;
            }
        }));

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        presenter.getProjectListData(currentPage, id);
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
        if (projectListResponse == null || projectListResponse.getData() == null || projectListResponse.getData().getDatas() == null)
        {
            showProjectListFail();
            return;
        }
        else
        {
            dataList = projectListResponse.getData().getDatas();

            if (isRefresh)
            {
                adapter.replaceData(dataList);
            }
            else
            {
                adapter.addData(dataList);
            }
        }
    }

    @Override
    public void showCollectOutsideArticle(int position, FeedArticleData feedArticleData, BaseResponse<FeedArticleListData> feedArticleListResponse) {
        adapter.setData(position, feedArticleData);
        CommonUtils.showSnackMessage(_mActivity, getString(R.string.collect_success));
    }

    @Override
    public void showCancelCollectArticleData(int position, FeedArticleData feedArticleData, BaseResponse<FeedArticleListData> feedArticleListResponse) {
        adapter.setData(position, feedArticleData);
        CommonUtils.showSnackMessage(_mActivity, getString(R.string.cancel_collect_fail));
    }

    @Override
    public void showProjectListFail() {
        CommonUtils.showSnackMessage(_mActivity, getString(R.string.failed_to_obtain_project_list));
    }

    @Override
    public void showJumpToTheTop() {
        if (recyclerView != null)
        {
            recyclerView.smoothScrollToPosition(0);
        }
    }

    private void setRefresh()
    {
        currentPage = 1;

        smartRefreshLayout.setOnRefreshListener(refreshLayout ->
        {
            currentPage = 1;
            isRefresh = true;
            presenter.getProjectListData(currentPage, id);
            refreshLayout.finishRefresh(1000);
        }
        );

        smartRefreshLayout.setOnLoadMoreListener(refreshLayout ->
        {
            currentPage = 1;
            isRefresh = false;
            presenter.getProjectListData(currentPage, id);
            refreshLayout.finishRefresh(1000);
        });
    }
}
