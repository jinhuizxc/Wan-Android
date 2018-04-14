package com.hsf1002.sky.wanandroid.ui.hierarchy.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hsf1002.sky.wanandroid.R;
import com.hsf1002.sky.wanandroid.app.Constants;
import com.hsf1002.sky.wanandroid.base.fragment.AbstractRootFragment;
import com.hsf1002.sky.wanandroid.component.RxBus;
import com.hsf1002.sky.wanandroid.contract.hierarchy.KnowledgeHierarchyContract;
import com.hsf1002.sky.wanandroid.contract.hierarchy.KnowledgeHierarchyListContract;
import com.hsf1002.sky.wanandroid.core.DataManager;
import com.hsf1002.sky.wanandroid.core.bean.BaseResponse;
import com.hsf1002.sky.wanandroid.core.bean.hierarchy.KnowledgeHierarchyData;
import com.hsf1002.sky.wanandroid.core.bean.main.collect.FeedArticleData;
import com.hsf1002.sky.wanandroid.core.bean.main.collect.FeedArticleListData;
import com.hsf1002.sky.wanandroid.core.event.CollectEvent;
import com.hsf1002.sky.wanandroid.core.event.DismissDetailErrorView;
import com.hsf1002.sky.wanandroid.core.event.DismissErrorView;
import com.hsf1002.sky.wanandroid.core.event.ShowDetailErrorView;
import com.hsf1002.sky.wanandroid.core.event.SwitchNavigationEvent;
import com.hsf1002.sky.wanandroid.core.event.SwitchProjectEvent;
import com.hsf1002.sky.wanandroid.presenter.hierarchy.KnowledgeHierarchyListPresenter;
import com.hsf1002.sky.wanandroid.presenter.hierarchy.KnowledgeHierarchyPresenter;
import com.hsf1002.sky.wanandroid.ui.mainpager.adapter.ArticleListAdapter;
import com.hsf1002.sky.wanandroid.utils.CommonUtils;
import com.hsf1002.sky.wanandroid.utils.StartActivityUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by hefeng on 18-4-11.
 */

public class KnowledgeHierarchyListFragment extends AbstractRootFragment<KnowledgeHierarchyListPresenter> implements KnowledgeHierarchyListContract.View {

    @BindView(R.id.knowledge_hierarchy_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;

    @BindView(R.id.normal_view)
    RecyclerView recyclerView;

    private int id;
    private int currentPage;
    private List<FeedArticleData> articleDataList;
    private ArticleListAdapter adapter;
    private boolean isRefresh = true;
    private int position;

    @Inject
    DataManager dataManager;


    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_knowledge_hierarchy_list;
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();

        isInnerFragment = true;
        setRefresh();

        Bundle bundle = getArguments();
        id = bundle.getInt(Constants.ARG_PARAM1, 0);
        if (id == 0)
        {
            return;
        }

        currentPage = 0;
        presenter.getKnowledgeHierarchyDetailData(currentPage, id);

        adapter = new ArticleListAdapter(R.layout.item_search_pager, articleDataList);
        adapter.setOnItemClickListener((adapter1, view, position1) ->
        {
            position = position1;

            StartActivityUtils.startArticleDetailActivity(_mActivity,
                    adapter.getData().get(position).getId(),
                    adapter.getData().get(position).getTitle().trim(),
                    adapter.getData().get(position).getLink(),
                    adapter.getData().get(position).isCollect(),
                    false, false);
        });

        adapter.setOnItemChildClickListener((adapter1, view, position1) ->
        {
            switch (view.getId())
            {
                case R.id.item_search_pager_chapterName:
                    break;
                case R.id.item_search_pager_like_iv:
                    likeEvent(position);
                    break;
                case R.id.item_search_pager_tag_tv:
                    String superChaperName = adapter.getData().get(position).getSuperChapterName();

                    if (superChaperName.contains(getString(R.string.open_project)))
                    {
                        RxBus.getDefault().post(new SwitchProjectEvent());
                    }
                    else if (superChaperName.contains(getString(R.string.navigation)))
                    {
                        RxBus.getDefault().post(new SwitchNavigationEvent());
                    }
                    break;
                default:
                    break;
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        showLoading();
    }

    public static KnowledgeHierarchyListFragment getInstance(int id, String param2)
    {
        KnowledgeHierarchyListFragment fragment = new KnowledgeHierarchyListFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.ARG_PARAM1, id);
        args.putString(Constants.ARG_PARAM2, param2);
        fragment.setArguments(args);

        return  fragment;
    }

    @Override
    public void showKnowledgeHierarchyDetailData(BaseResponse<FeedArticleListData> feedArticleListResponse) {
        if (feedArticleListResponse == null || feedArticleListResponse.getData() == null || feedArticleListResponse.getData().getDatas() == null)
        {
            showKnowledgeHierarchyDetailDataFail();
            return;
        }

        RxBus.getDefault().post(new DismissDetailErrorView());

        smartRefreshLayout.setVisibility(View.VISIBLE);
        articleDataList = feedArticleListResponse.getData().getDatas();

        if (isRefresh)
        {
            adapter.replaceData(articleDataList);
        }
        else
        {
            adapter.addData(articleDataList);
        }

        showNormal();
    }

    @Override
    public void showError() {
        super.showError();
        RxBus.getDefault().post(new ShowDetailErrorView());
        smartRefreshLayout.setVisibility(View.GONE);
    }

    @Override
    public void showCollectArticleData(int position, FeedArticleData feedArticleData, BaseResponse<FeedArticleListData> feedArticleListResponse) {
        adapter.setData(position, feedArticleData);
        RxBus.getDefault().post(new CollectEvent(false));
        CommonUtils.showSnackMessage(_mActivity, getString(R.string.collect_success));
    }

    @Override
    public void showCancelCollectArticleData(int position, FeedArticleData feedArticleData, BaseResponse<FeedArticleListData> feedArticleListResponse) {
        adapter.setData(position, feedArticleData);
        RxBus.getDefault().post(new CollectEvent(true));
        CommonUtils.showSnackMessage(_mActivity, getString(R.string.cancel_collect_success));
    }

    @Override
    public void showKnowledgeHierarchyDetailDataFail() {
        CommonUtils.showSnackMessage(_mActivity, getString(R.string.failed_to_obtain_knowledge_data));
    }

    @Override
    public void showJumpTheTop() {
        if (recyclerView != null)
        {
            recyclerView.smoothScrollToPosition(0);
        }
    }

    @Override
    public void showReloadDetailEvent() {
        if (smartRefreshLayout != null)
        {
            smartRefreshLayout.autoRefresh();
        }
    }

    @Override
    public void showCollectSuccess() {
        if (adapter != null && adapter.getData().size() > position)
        {
            adapter.getData().get(position).setCollect(true);
            adapter.setData(position, adapter.getData().get(position));
        }
    }

    @Override
    public void showCancelCollectSuccess() {
        if (adapter != null && adapter.getData().size() > position)
        {
            adapter.getData().get(position).setCollect(false);
            adapter.setData(position, adapter.getData().get(position));
        }
    }

    private void likeEvent(int position)
    {
        if (!dataManager.getLoginStatus())
        {
            StartActivityUtils.startLoginActivity(_mActivity);
            CommonUtils.showToastMessage(_mActivity, getString(R.string.login_tint));
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
        smartRefreshLayout.setPrimaryColorsId(Constants.BLUE_THEME, R.color.white);

        smartRefreshLayout.setOnRefreshListener( refreshLayout ->
        {
           currentPage = 0;

           if (id != 0)
           {
               isRefresh = true;
               presenter.getKnowledgeHierarchyDetailData(0, id);
           }

           refreshLayout.finishRefresh(1000);
        });

        smartRefreshLayout.setOnLoadMoreListener(refreshLayout ->
        {
           currentPage++;

           if (id != 0)
           {
               isRefresh = false;
               presenter.getKnowledgeHierarchyDetailData(currentPage, id);
           }
        });
    }
}
