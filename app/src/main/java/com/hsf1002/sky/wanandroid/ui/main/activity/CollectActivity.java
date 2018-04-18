package com.hsf1002.sky.wanandroid.ui.main.activity;


import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.hsf1002.sky.wanandroid.R;
import com.hsf1002.sky.wanandroid.base.activity.AbstractRootActivity;
import com.hsf1002.sky.wanandroid.contract.main.CollectContract;
import com.hsf1002.sky.wanandroid.core.bean.BaseResponse;
import com.hsf1002.sky.wanandroid.core.bean.main.collect.FeedArticleData;
import com.hsf1002.sky.wanandroid.core.bean.main.collect.FeedArticleListData;
import com.hsf1002.sky.wanandroid.presenter.main.CollectPresenter;
import com.hsf1002.sky.wanandroid.ui.mainpager.adapter.ArticleListAdapter;
import com.hsf1002.sky.wanandroid.utils.CommonUtils;
import com.hsf1002.sky.wanandroid.utils.StartActivityUtils;
import com.hsf1002.sky.wanandroid.utils.StatusBarUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hefeng on 18-4-17.
 */

public class CollectActivity extends AbstractRootActivity<CollectPresenter> implements CollectContract.View{

    @BindView(R.id.common_toolbar)
    Toolbar toolbar;

    @BindView(R.id.common_toolbar_title_tv)
    TextView title;

    @BindView(R.id.normal_view)
    SmartRefreshLayout smartRefreshLayout;

    @BindView(R.id.collect_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.collect_floating_action_btn)
    FloatingActionButton floatingActionButton;

    private boolean isRefresh = true;
    private int currentPage;
    private List<FeedArticleData> articleDataList;
    private ArticleListAdapter adapter;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_collect;
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();

        initToolbar();
        initView();
        setRefresh();
    }

    @Override
    public void showCollectList(BaseResponse<FeedArticleListData> feedArticleListResponse) {
        if (feedArticleListResponse == null || feedArticleListResponse.getData() == null || feedArticleListResponse.getData().getDatas() == null)
        {
            showCollectListFail();
            return;
        }
        else
        {
            if (isRefresh)
            {
                articleDataList = feedArticleListResponse.getData().getDatas();
                adapter.replaceData(articleDataList);
            }
            else
            {
                articleDataList.addAll(feedArticleListResponse.getData().getDatas());
                adapter.addData(feedArticleListResponse.getData().getDatas());
            }

            if (adapter.getData().size() == 0)
            {
                CommonUtils.showSnackMessage(this, getString(R.string.no_collect));
            }
        }
    }

    @Override
    public void showCancelCollectPageArticleData(int position, FeedArticleData feedArticleData, BaseResponse<FeedArticleListData> feedArticleListResponse) {
        adapter.remove(position);
        CommonUtils.showSnackMessage(this, getString(R.string.cancel_collect_success));
    }

    @Override
    public void showCollectListFail() {
        CommonUtils.showSnackMessage(this, getString(R.string.failed_to_obtain_collection_data));
    }

    @Override
    public void showRefreshEvent() {
        currentPage = 0;
        isRefresh = true;
        presenter.getCollectList(currentPage);
    }

    @OnClick({R.id.collect_floating_action_btn})
    void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.collect_floating_action_btn:
                recyclerView.smoothScrollToPosition(0);
                break;
            default:
                break;
        }
    }

    private void initToolbar()
    {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        title.setText(getString(R.string.collect));
        StatusBarUtil.immersive(this);
        StatusBarUtil.setPaddingSmart(this, toolbar);
        toolbar.setNavigationOnClickListener( v -> onBackPressedSupport());
    }

    private void initView()
    {
        articleDataList = new ArrayList<>();
        adapter = new ArticleListAdapter(R.layout.item_search_pager, articleDataList);
        adapter.setCollectPage();
        adapter.setOnItemClickListener((adapter1, view, position) ->
        {
            StartActivityUtils.startArticleDetailActivity(this,
                    adapter.getData().get(position).getId(),
                    adapter.getData().get(position).getTitle(),
                    adapter.getData().get(position).getLink(),
                    true, true, false);
        });

        adapter.setOnItemChildClickListener((adapter1, view, position) ->
        {
            switch (view.getId())
            {
                case R.id.item_search_pager_chapterName:
                    StartActivityUtils.startKnowledgeHierarchyDetailActivity(this, true,
                            adapter.getData().get(position).getChapterName(),
                            adapter.getData().get(position).getChapterName(),
                            adapter.getData().get(position).getChapterId());
                    break;
                case R.id.item_search_pager_like_iv:
                    presenter.cancelCollectPageArticle(position, adapter.getData().get(position));
                    break;
                default:
                    break;
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        presenter.getCollectList(currentPage);
    }

    private void setRefresh()
    {
        smartRefreshLayout.setOnRefreshListener( refreshLayout ->
        {
           showRefreshEvent();
           refreshLayout.finishRefresh(1000);
        });

        smartRefreshLayout.setOnLoadMoreListener( refreshLayout ->
        {
           currentPage++;
           isRefresh = false;
           presenter.getCollectList(currentPage);
           refreshLayout.finishRefresh(1000);
        });
    }
}
