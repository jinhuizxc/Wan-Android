package com.hsf1002.sky.wanandroid.ui.hierarchy.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hsf1002.sky.wanandroid.R;
import com.hsf1002.sky.wanandroid.app.Constants;
import com.hsf1002.sky.wanandroid.base.fragment.AbstractRootFragment;
import com.hsf1002.sky.wanandroid.component.RxBus;
import com.hsf1002.sky.wanandroid.contract.hierarchy.KnowledgeHierarchyContract;
import com.hsf1002.sky.wanandroid.core.DataManager;
import com.hsf1002.sky.wanandroid.core.bean.BaseResponse;
import com.hsf1002.sky.wanandroid.core.bean.hierarchy.KnowledgeHierarchyData;
import com.hsf1002.sky.wanandroid.core.event.DismissErrorView;
import com.hsf1002.sky.wanandroid.core.event.ShowErrorView;
import com.hsf1002.sky.wanandroid.presenter.hierarchy.KnowledgeHierarchyPresenter;
import com.hsf1002.sky.wanandroid.ui.hierarchy.activity.KnowledgeHierarchyDetailActivity;
import com.hsf1002.sky.wanandroid.ui.hierarchy.adapter.KnowledgeHierarchyAdapter;
import com.hsf1002.sky.wanandroid.utils.CommonUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by hefeng on 18-4-10.
 */

public class KnowledgeHierarchyFragment  extends AbstractRootFragment<KnowledgeHierarchyPresenter> implements KnowledgeHierarchyContract.View{

    @BindView(R.id.normal_view)
    SmartRefreshLayout smartRefreshLayout;

    @BindView(R.id.knowledge_hierarchy_recycler_view)
    RecyclerView recyclerView;


    @Inject
    DataManager dataManager;

    private List<KnowledgeHierarchyData> knowledgeHierarchyDataList;
    private KnowledgeHierarchyAdapter adapter;

    public static KnowledgeHierarchyFragment getInstance(String param1, String param2) {
        KnowledgeHierarchyFragment fragment = new KnowledgeHierarchyFragment();
        Bundle args = new Bundle();
        args.putString(Constants.ARG_PARAM1, param1);
        args.putString(Constants.ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_knowledge_hierarchy;
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();

        setRefresh();

        adapter = new KnowledgeHierarchyAdapter(R.layout.item_knowledge_hierarchy, knowledgeHierarchyDataList);
        adapter.setOnItemClickListener((adapter1, view, position) ->
        {
            Intent intent = new Intent(_mActivity, KnowledgeHierarchyDetailActivity.class);
            intent.putExtra(Constants.ARG_PARAM1, adapter.getData().get(position));
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));

        presenter.getKnowledgeHierarchyData();
        showLoading();
    }

    @Override
    protected void initInject() {
        /*
        * init the presenter, of vital importance
        * */
        getFragmentComponent().inject(this);
    }

    @Override
    public void showKnowledgeHierarchyData(BaseResponse<List<KnowledgeHierarchyData>> knowledgeHierarchyResponse) {
        if (knowledgeHierarchyResponse == null || knowledgeHierarchyResponse.getData() == null)
        {
            showKnowledgeHierarchyDetailDataFail();
            return;
        }
        else
        {
            RxBus.getDefault().post(new DismissErrorView());

            if (dataManager.getCurrentPage() == Constants.SECOND)
            {
                smartRefreshLayout.setVisibility(View.VISIBLE);
            }
            else
            {
                smartRefreshLayout.setVisibility(View.INVISIBLE);
            }

            knowledgeHierarchyDataList = knowledgeHierarchyResponse.getData();
            adapter.replaceData(knowledgeHierarchyDataList);
            showNormal();
        }
    }

    @Override
    public void showKnowledgeHierarchyDetailDataFail() {
        CommonUtils.showSnackMessage(_mActivity, getString(R.string.failed_to_obtain_knowledge_data));
    }

    @Override
    public void showError() {
        super.showError();
        smartRefreshLayout.setVisibility(View.INVISIBLE);
        RxBus.getDefault().post(new ShowErrorView());
    }

    public void reLoad()
    {
        if (presenter != null && smartRefreshLayout.getVisibility() == View.INVISIBLE)
        {
            presenter.getKnowledgeHierarchyData();
        }
    }

    public void jumpToTop()
    {
        if (recyclerView != null)
        {
            recyclerView.smoothScrollToPosition(0);
        }
    }

    private void setRefresh()
    {
        smartRefreshLayout.setPrimaryColorsId(Constants.BLUE_THEME, R.color.white);

        smartRefreshLayout.setOnRefreshListener( refreshLayout ->
        {
           presenter.getKnowledgeHierarchyData();
           refreshLayout.finishRefresh(1000);
        });

        smartRefreshLayout.setOnLoadMoreListener(refreshLayout ->
        {
           presenter.getKnowledgeHierarchyData();
           refreshLayout.finishLoadMore(1000);
        });
    }
}
