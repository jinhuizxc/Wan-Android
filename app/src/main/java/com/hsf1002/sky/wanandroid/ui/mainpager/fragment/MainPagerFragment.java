package com.hsf1002.sky.wanandroid.ui.mainpager.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.hsf1002.sky.wanandroid.R;
import com.hsf1002.sky.wanandroid.app.Constants;
import com.hsf1002.sky.wanandroid.base.fragment.AbstractRootFragment;
import com.hsf1002.sky.wanandroid.component.RxBus;
import com.hsf1002.sky.wanandroid.contract.mainpager.MainPagerContract;
import com.hsf1002.sky.wanandroid.core.DataManager;
import com.hsf1002.sky.wanandroid.core.bean.BaseResponse;
import com.hsf1002.sky.wanandroid.core.bean.main.banner.BannerData;
import com.hsf1002.sky.wanandroid.core.bean.main.collect.FeedArticleData;
import com.hsf1002.sky.wanandroid.core.bean.main.collect.FeedArticleListData;
import com.hsf1002.sky.wanandroid.core.event.AutoLoginEvent;
import com.hsf1002.sky.wanandroid.core.event.DismissErrorView;
import com.hsf1002.sky.wanandroid.core.event.LoginEvent;
import com.hsf1002.sky.wanandroid.core.event.ShowErrorView;
import com.hsf1002.sky.wanandroid.core.event.SwitchNavigationEvent;
import com.hsf1002.sky.wanandroid.core.event.SwitchProjectEvent;
import com.hsf1002.sky.wanandroid.presenter.mainpager.MainPagerPresenter;
import com.hsf1002.sky.wanandroid.ui.mainpager.adapter.ArticleListAdapter;
import com.hsf1002.sky.wanandroid.utils.CommonUtils;
import com.hsf1002.sky.wanandroid.utils.GlideImageLoader;
import com.hsf1002.sky.wanandroid.utils.StartActivityUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by hefeng on 18-4-10.
 */

public class MainPagerFragment extends AbstractRootFragment<MainPagerPresenter> implements MainPagerContract.View{

    @BindView(R.id.normal_view)
    SmartRefreshLayout refreshLayout;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<FeedArticleData> feedArticleDataList;
    private ArticleListAdapter adapter;

    @Inject
    DataManager dataManager;
    private int articlePosition;
    private List<String> bannerTitleList;
    private List<String> bannerUrlList;
    private Banner banner;

    @Override
    public void onResume() {
        super.onResume();

        if (banner != null)
        {
            banner.startAutoPlay();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        if (banner != null)
        {
            banner.stopAutoPlay();
        }
    }

    public static MainPagerFragment getInstance(String param1, String param2)
    {
        MainPagerFragment fragment = new MainPagerFragment();
        Bundle args = new Bundle();
        args.putString(Constants.ARG_PARAM1, param1);
        args.putString(Constants.ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    protected void initInject() {
        /*
        * init the presenter, of vital importance
        * */
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_main_pager;
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();

        feedArticleDataList = new ArrayList<>();
        adapter = new ArticleListAdapter(R.layout.item_search_pager, feedArticleDataList);
        adapter.setOnItemClickListener((adapter1, view, position) ->
        {
            articlePosition = position;
            StartActivityUtils.startArticleDetailActivity(_mActivity,
                    adapter.getData().get(position).getId(),
                    adapter.getData().get(position).getTitle(),
                    adapter.getData().get(position).getLink(),
                    adapter.getData().get(position).isCollect(), false, false);
        });
        adapter.setOnItemChildClickListener((adapter1, view, position) ->
        {
            switch (view.getId())
            {
                case R.id.item_search_pager_chapterName:
                    StartActivityUtils.startKnowledgeHierarchyDetailActivity(_mActivity, true,
                            adapter.getData().get(position).getSuperChapterName(),
                            adapter.getData().get(position).getChapterName(),
                            adapter.getData().get(position).getChapterId());
                    break;
                case R.id.item_search_pager_like_iv:
                    likeEvent(position);
                    break;
                case R.id.item_search_pager_tag_tv:
                    String superChapterName = adapter.getData().get(position).getSuperChapterName();
                    if (superChapterName.contains(getString(R.string.open_project)))
                    {
                        RxBus.getDefault().post(new SwitchProjectEvent());
                    }
                    else if (superChapterName.contains(getString(R.string.navigation)))
                    {
                        RxBus.getDefault().post(new SwitchNavigationEvent());
                    }
                    break;

            }
        });

        LinearLayout headerGroup = ((LinearLayout) LayoutInflater.from(_mActivity).inflate(R.layout.head_banner, null));
        banner = headerGroup.findViewById(R.id.head_banner);
        headerGroup.removeView(banner);
        adapter.addHeaderView(banner);
        //adapter.addFooterView(banner);
        recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        recyclerView.setAdapter(adapter);

        setRefresh();

        if (!TextUtils.isEmpty(dataManager.getLoginAccount())
                && !TextUtils.isEmpty(dataManager.getLoginPassword()))
        {
            presenter.loadMainPagerData();
        }
        else
        {
            presenter.autoRefresh();
        }

        showLoading();
    }

    @Override
    public void showAutoLoginSuccess() {
        if (isAdded())
        {
            CommonUtils.showSnackMessage(_mActivity, getString(R.string.auto_login_success));
            RxBus.getDefault().post(new AutoLoginEvent());
        }
    }

    @Override
    public void showAutoLoginFail() {
        dataManager.setLoginStatus(false);
        //CookiesManager
        RxBus.getDefault().post(new LoginEvent(false));
    }

    @Override
    public void showArticleList(BaseResponse<FeedArticleListData> feedArticleListResponse, boolean isRefresh) {
        if (feedArticleListResponse == null || feedArticleListResponse.getData() == null
                || feedArticleListResponse.getData().getDatas() == null)
        {
            showArticleListFail();
            return;
        }

        RxBus.getDefault().post(new DismissErrorView());

        if (dataManager.getCurrentPage() == Constants.FIRST)
        {
            refreshLayout.setVisibility(View.VISIBLE);
        }
        else
        {
            refreshLayout.setVisibility(View.INVISIBLE);
        }

        if (isRefresh)
        {
            feedArticleDataList = feedArticleListResponse.getData().getDatas();
            adapter.replaceData(feedArticleListResponse.getData().getDatas());
        }
        else
        {
            feedArticleDataList.addAll(feedArticleListResponse.getData().getDatas());
            adapter.addData(feedArticleListResponse.getData().getDatas());
        }
        showNormal();
    }

    @Override
    public void showArticleListFail() {
        CommonUtils.showSnackMessage(_mActivity, getString(R.string.failed_to_obtain_article_list));
    }

    @Override
    public void showCollectArticleData(int position, FeedArticleData feedArticleData, BaseResponse<FeedArticleListData> feedArticleListResponse) {

        adapter.setData(position, feedArticleData);
        CommonUtils.showSnackMessage(_mActivity, getString(R.string.collect_success));
    }

    @Override
    public void showCancelCollectArticleData(int position, FeedArticleData feedArticleData, BaseResponse<FeedArticleListData> feedArticleListResponse) {
        adapter.setData(position, feedArticleData);
        CommonUtils.showSnackMessage(_mActivity, getString(R.string.cancel_collect_success));
    }

    @Override
    public void showBannerData(BaseResponse<List<BannerData>> bannerResponse) {
        if (bannerResponse == null || bannerResponse.getData() == null)
        {
            showBannerDataFail();
            return;
        }

        bannerTitleList = new ArrayList<>();
        List<String> bannerImageList = new ArrayList<>();
        bannerUrlList = new ArrayList<>();
        List<BannerData> bannerDataList = bannerResponse.getData();

        for (BannerData bannerData:bannerDataList)
        {
            bannerTitleList.add(bannerData.getTitle());
            bannerImageList.add(bannerData.getImagePath());
            bannerUrlList.add(bannerData.getUrl());
        }

        banner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(bannerImageList);
        banner.setBannerAnimation(Transformer.DepthPage);
        banner.setBannerTitles(bannerTitleList);
        banner.isAutoPlay(true);
        banner.setDelayTime(bannerDataList.size() * 400);
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setOnBannerListener(i -> StartActivityUtils.startArticleDetailActivity(_mActivity,
                0, bannerTitleList.get(i), bannerUrlList.get(i),
                false, false, true));
        banner.start();
    }

    @Override
    public void showBannerDataFail() {
        CommonUtils.showSnackMessage(_mActivity, getString(R.string.failed_to_obtain_banner_data));
    }

    @Override
    public void showCollectSuccess() {
        //super.showCollectSuccess();
        if (adapter != null && adapter.getData().size() > articlePosition)
        {
            adapter.getData().get(articlePosition).setCollect(true);
            adapter.setData(articlePosition, adapter.getData().get(articlePosition));
        }
    }

    @Override
    public void showCancelCollectSuccess() {
        //super.showCancelCollectSuccess();
        if (adapter != null && adapter.getData().size() > articlePosition)
        {
            adapter.getData().get(articlePosition).setCollect(false);
            adapter.setData(articlePosition, adapter.getData().get(articlePosition));
        }
    }

    @Override
    public void showError() {
//        super.showError();
        refreshLayout.setVisibility(View.INVISIBLE);
        RxBus.getDefault().post(new ShowErrorView());
    }

    private void likeEvent(int position)
    {
        if (!dataManager.getLoginStatus())
        {
            StartActivityUtils.startLoginActivity(_mActivity);
            CommonUtils.showToastMessage(_mActivity, getString(R.string.login_tint));
            return;
        }
        else
        {
            if (adapter.getData().get(position).isCollect())
            {
                presenter.cancelCollectArticle(position, adapter.getData().get(position));
            }
            else
            {
                presenter.addCollectArticle(position, adapter.getData().get(position));
            }
        }
    }

    public void reLoad()
    {
        if (refreshLayout != null && presenter != null && refreshLayout.getVisibility() == View.INVISIBLE && CommonUtils.isNetworkConnected())
        {
            refreshLayout.autoRefresh();
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
        refreshLayout.setOnRefreshListener( refreshLayout1 ->
        {
            presenter.autoRefresh();
            refreshLayout1.finishRefresh(1000);
        });

        refreshLayout.setOnLoadMoreListener(refreshLayout1 ->
        {
            presenter.loadMore();
            refreshLayout1.finishLoadMore(1000);
        });
    }
}
