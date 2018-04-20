package com.hsf1002.sky.wanandroid.ui.main.activity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;


import com.hsf1002.sky.wanandroid.R;
import com.hsf1002.sky.wanandroid.app.Constants;
import com.hsf1002.sky.wanandroid.base.activity.AbstractRootActivity;
import com.hsf1002.sky.wanandroid.component.RxBus;
import com.hsf1002.sky.wanandroid.contract.main.SearchListContract;
import com.hsf1002.sky.wanandroid.core.DataManager;
import com.hsf1002.sky.wanandroid.core.bean.BaseResponse;
import com.hsf1002.sky.wanandroid.core.bean.main.collect.FeedArticleData;
import com.hsf1002.sky.wanandroid.core.bean.main.collect.FeedArticleListData;
import com.hsf1002.sky.wanandroid.core.event.SwitchNavigationEvent;
import com.hsf1002.sky.wanandroid.core.event.SwitchProjectEvent;
import com.hsf1002.sky.wanandroid.presenter.main.SearchListPresenter;
import com.hsf1002.sky.wanandroid.ui.mainpager.adapter.ArticleListAdapter;
import com.hsf1002.sky.wanandroid.utils.CommonUtils;
import com.hsf1002.sky.wanandroid.utils.StartActivityUtils;
import com.hsf1002.sky.wanandroid.utils.StatusBarUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hefeng on 18-4-13.
 */

public class SearchListActivity extends AbstractRootActivity<SearchListPresenter> implements SearchListContract.View{

    @BindView(R.id.common_toolbar)
    Toolbar toolbar;

    @BindView(R.id.common_toolbar_title_tv)
    TextView title;

    @BindView(R.id.search_list_refresh_layout)
    RefreshLayout refreshLayout;

    @BindView(R.id.normal_view)
    RecyclerView recyclerView;

    @BindView(R.id.search_list_floating_action_btn)
    FloatingActionButton floatingActionButton;


    @Inject
    DataManager dataManager;

    private int articlePosition;
    private int currentPage;
    private List<FeedArticleData> articleDataList;
    private ArticleListAdapter adapter;
    private boolean isAddData;
    private String searchStr;



    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_list;
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();

        initToolbar();
        presenter.getSearchList(currentPage, searchStr);
        articleDataList = new ArrayList<>();
        adapter = new ArticleListAdapter(R.layout.item_search_pager, articleDataList);
        adapter.setSearchPage();
        adapter.setOnItemClickListener((adapter1, view, position) ->
        {
            articlePosition = position;
            StartActivityUtils.startArticleDetailActivity(this,
                    adapter.getData().get(position).getId(),
                    adapter.getData().get(position).getTitle(),
                    adapter.getData().get(position).getLink(),
                    adapter.getData().get(position).isCollect(),
                    false, false);
        });
        adapter.setOnItemChildClickListener((adapter1, view, position) ->
        {
            switch (view.getId())
            {
                case R.id.item_search_pager_chapterName:
                    StartActivityUtils.startKnowledgeHierarchyDetailActivity(this, true,
                            adapter.getData().get(position).getSuperChapterName(),
                            adapter.getData().get(position).getChapterName(),
                            adapter.getData().get(position).getChapterId());
                    break;
                case R.id.item_search_pager_like_iv:
                    likeEvnet(position);
                    break;
                case R.id.item_search_pager_tag_tv:
                    String superChapterName = adapter.getData().get(position).getSuperChapterName();
                    if (superChapterName.contains(getString(R.string.open_project)))
                    {
                        onBackPressedSupport();
                        RxBus.getDefault().post(new SwitchProjectEvent());
                    }
                    else if (superChapterName.contains(getString(R.string.navigation)))
                    {
                        onBackPressedSupport();
                        RxBus.getDefault().post(new SwitchNavigationEvent());
                    }
                    break;
                default:
                    break;

            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setRefresh();
        showLoading();
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void showSearchList(BaseResponse<FeedArticleListData> feedArticleListResponse) {
        if (feedArticleListResponse == null ||
                feedArticleListResponse.getData() == null ||
                feedArticleListResponse.getData().getDatas() == null)
        {
            showSearchListFail();
            return;
        }

        FeedArticleListData articleData = feedArticleListResponse.getData();
        articleDataList = articleData.getDatas();

        if (isAddData)
        {
            adapter.addData(articleDataList);
        }
        else
        {
            adapter.replaceData(articleDataList);
        }

        showNormal();
    }

    @Override
    public void showCollectArticleData(int position, FeedArticleData feedArticleData, BaseResponse<FeedArticleListData> feedArticleListResponse) {
        adapter.setData(position, feedArticleData);
        CommonUtils.showSnackMessage(this, getString(R.string.collect_success));
    }

    @Override
    public void showCancelCollectArticleData(int position, FeedArticleData feedArticleData, BaseResponse<FeedArticleListData> feedArticleListResponse) {
        adapter.setData(position, feedArticleData);
        CommonUtils.showSnackMessage(this, getString(R.string.cancel_collect));
    }

    @Override
    public void showSearchListFail() {
        CommonUtils.showSnackMessage(this, getString(R.string.failed_to_obtain_search_data_list));
    }

    @Override
    public void showCollectSuccess() {

    }

    @Override
    public void showCancelCollectSuccess() {

    }

    @OnClick({R.id.search_list_floating_action_btn})
    void onClick(View view)
    {
        switch (view.getId()) {
            case R.id.search_list_floating_action_btn:
                recyclerView.smoothScrollToPosition(0);
                break;
            default:
                break;
        }
    }

    private void initToolbar()
    {
        Bundle bundle = getIntent().getExtras();

        if (bundle == null)
        {
            return;
        }

        searchStr = (String)bundle.get(Constants.SEARCH_TEXT);
        if (!TextUtils.isEmpty(searchStr))
        {
            title.setText(searchStr);
            title.setTextColor(ContextCompat.getColor(this, R.color.title_black));
        }

        StatusBarUtil.immersive(this, ContextCompat.getColor(this, R.color.transparent), 0.5f);
        StatusBarUtil.setPaddingSmart(this, toolbar);
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_grey_24dp));
        toolbar.setNavigationOnClickListener( v-> onBackPressedSupport());
    }

    private void likeEvnet(int position)
    {
        if (!dataManager.getLoginStatus())
        {
            StartActivityUtils.startLoginActivity(this);
            CommonUtils.showSnackMessage(this, getString(R.string.login_tint));
            return;
        }

        if (adapter.getData().get(position).isCollect())
        {
            presenter.cancelCollectArticle(position, adapter.getData().get(position));
        }
        else
        {
            presenter.addCollectArticle(position, adapter.getData().get(position));
        }
    }

    private void setRefresh()
    {
        refreshLayout.setOnRefreshListener( refreshLayout1 ->
        {
           currentPage = 0;
           isAddData = false;
           presenter.getSearchList(currentPage, searchStr);
           refreshLayout1.finishRefresh(1000);
        });

        refreshLayout.setOnLoadMoreListener( refreshLayout1 ->
        {
            currentPage++;
            isAddData = true;
            presenter.getSearchList(currentPage, searchStr);
            refreshLayout1.finishRefresh(1000);
        });
    }
}
