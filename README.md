# Wan-Android
Rxjava+RxAndroid+Retrofit+OkHttp+Gson+Glide+Dagger2+GreenDao+Lottie-android+SmartRefreshLayout


### com.airbnb.lottie
Lottie是一个支持Android、iOS、React Native，并由 Adobe After Effects制作aep格式的动画，然后经由bodymovin插件转化渲染为json格式可被移动端本地识别解析的Airbnb开源库。实时呈现After Effects动画效果，让应用程序可以像使用静态图片一样轻松地使用动画。Lottie支持API 14及以上
```
implementation "com.airbnb.android:lottie:2.3.0"
```
```
<com.airbnb.lottie.LottieAnimationView
    android:id="@+id/one_animation"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_marginStart="@dimen/dp_36"
    android:layout_marginTop="@dimen/dp_88"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintTop_toTopOf="parent" />
```
```
@BindView(R.id.one_animation)
LottieAnimationView oneAnimation;
```

```
oneAnimation.setAnimation("W.json");
oneAnimation.playAnimation();
....
if (oneAnimation != null)
{
    oneAnimation.cancelAnimation();
}
```

### com.scwang.smartrefresh:SmartRefreshLayout
```
implementation 'com.scwang.smartrefresh:SmartRefreshLayout:1.0.5.1'
implementation 'com.scwang.smartrefresh:SmartRefreshHeader:1.0.5.1'
```

```
<com.scwang.smartrefresh.layout.SmartRefreshLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/normal_view"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:visibility="invisible">
<!-- com.scwang.smartrefresh.layout.header.ClassicsHeader -->
    <com.scwang.smartrefresh.header.PhoenixHeader
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />

    <android.support.v7.widget.RecyclerView
        android:id="@+id/recyclerView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="@color/colorBackground" />
<!-- com.scwang.smartrefresh.layout.footer.ClassicsFooter -->
    <com.scwang.smartrefresh.header.TaurusHeader
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />
</com.scwang.smartrefresh.layout.SmartRefreshLayout>
```
```
@BindView(R.id.normal_view)
SmartRefreshLayout refreshLayout;
```
```
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
```
### com.youth.banner:banner
```
implementation "com.youth.banner:banner:1.4.10"
```
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <com.youth.banner.Banner
        android:id="@+id/head_banner"
        android:layout_width="match_parent"
        android:layout_height="@dimen/dp_200"
        android:padding="@dimen/dp_10" />
</LinearLayout>
```
```
private Banner banner;

LinearLayout headerGroup = ((LinearLayout) LayoutInflater.from(_mActivity).inflate(R.layout.head_banner, null));
banner = headerGroup.findViewById(R.id.head_banner);
headerGroup.removeView(banner);
// public class ArticleListAdapter extends BaseQuickAdapter<FeedArticleData, KnowledgeHierarchyListViewHolder> {
adapter.addHeaderView(banner);

...............
banner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
banner.setImageLoader(new GlideImageLoader());
banner.setImages(bannerImageList);
banner.setBannerAnimation(Transformer.DepthPage);
banner.setBannerTitles(bannerTitleList);
banner.isAutoPlay(true);
banner.setDelayTime(bannerDataList.size() * 400);
banner.setIndicatorGravity(BannerConfig.CENTER);
mBanner.setOnBannerListener(i -> JudgeUtils.startArticleDetailActivity(_mActivity,
                0, mBannerTitleList.get(i), mBannerUrlList.get(i),
                false, false, true));

banner.start();

```
```
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
```

### com.flyco.tablayout.SlidingTabLayout
```
implementation "com.flyco.tablayout:FlycoTabLayout_Lib:2.1.2@aar"
```
```
<android.support.design.widget.AppBarLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:fitsSystemWindows="false"
    android:theme="@style/AppTheme.AppBarOverlay"
    app:elevation="@dimen/dp_0">

    <include layout="@layout/common_toolbar_no_scroll" />

    <com.flyco.tablayout.SlidingTabLayout
        android:id="@+id/knowledge_hierarchy_detail_tab_layout"
        android:layout_width="match_parent"
        android:layout_height="@dimen/dp_48"
        android:background="@color/colorPrimary"
        app:tl_textAllCaps="false"
        app:tl_textBold="BOTH"
        app:tl_textsize="@dimen/sp_14" />
</android.support.design.widget.AppBarLayout>

<android.support.v4.view.ViewPager
    android:id="@+id/knowledge_hierarchy_detail_viewpager"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/white"
    app:layout_behavior="@string/appbar_scrolling_view_behavior" />
```
```
@BindView(R.id.knowledge_hierarchy_detail_tab_layout)
SlidingTabLayout slidingTabLayout;
```
```
viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (getIntent().getBooleanExtra(Constants.IS_SINGLE_CHAPTER, false))
        {
            return  chapterName;
        }
        else {
            return knowledgeHierarchyDataList.get(position).getName().trim();
        }
    }
});
slidingTabLayout.setViewPager(viewPager);
```

### com.just.agentweb:agentweb
```
implementation 'com.just.agentweb:agentweb:3.1.0'
```
```
<FrameLayout
        android:id="@+id/article_detail_web_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:clipToPadding="false" />
```
```
@BindView(R.id.article_detail_web_view)
FrameLayout webview;

private AgentWeb agentWeb;
```
```
@Override
protected void initEventAndData() {
    agentWeb = AgentWeb.with(this)
            .setAgentWebParent(webview, new LinearLayout.LayoutParams(-1, -1))
            .useDefaultIndicator()
            .defaultProgressBarColor()
            .setMainFrameErrorView(R.layout.webview_error_view, -1)
            .createAgentWeb()
            .ready()
            .go(link);
}

@Override
protected void onPause() {
    super.onPause();
    agentWeb.getWebLifeCycle().onPause();
}

@Override
protected void onResume() {
    super.onResume();
    agentWeb.getWebLifeCycle().onResume();
}

@Override
protected void onDestroy() {
    agentWeb.getWebLifeCycle().onDestroy();
    super.onDestroy();
}
```
### q.rorbin:VerticalTabLayout
```
implementation "q.rorbin:VerticalTabLayout:1.2.5"
```
```
<q.rorbin.verticaltablayout.VerticalTabLayout
    android:id="@+id/navigation_tab_layout"
    android:layout_width="@dimen/dp_100"
    android:layout_height="match_parent"
    android:background="@color/grey_bg"
    app:tab_height="@dimen/dp_50"
    app:indicator_color="@color/white"
    app:indicator_gravity="fill"
    app:tab_margin="@dimen/dp_15"
    app:tab_mode="scrollable" />
```
```
@BindView(R.id.navigation_tab_layout)
VerticalTabLayout verticalTabLayout;
```
```
verticalTabLayout.setTabAdapter(new TabAdapter() {
    @Override
    public int getCount() {
        return navigationListDataList == null ? 0 : navigationListDataList.size();
    }

    @Override
    public ITabView.TabBadge getBadge(int position) {
        return null;
    }

    @Override
    public ITabView.TabIcon getIcon(int position) {
        return null;
    }

    @Override
    public ITabView.TabTitle getTitle(int position) {
        return new TabView.TabTitle.Builder()
                .setContent(navigationListDataList.get(position).getName())
                .setTextColor(R.color.blue, R.color.black)
                .build();
    }

    @Override
    public int getBackground(int position) {
        return -1;
    }
});

verticalTabLayout.addOnTabSelectedListener(new VerticalTabLayout.OnTabSelectedListener() {
    @Override
    public void onTabSelected(TabView tab, int position) {
        isClickTab = true;
        selectTag(position);
    }

    @Override
    public void onTabReselected(TabView tab, int position) {

    }
});

private void selectTag(int i)
{
    index = i;
    recyclerView.stopScroll();
    smoothScrollToPosition(i);
}
```
### com.github.CymChad:BaseRecyclerViewAdapterHelper
```
implementation 'com.github.CymChad:BaseRecyclerViewAdapterHelper:2.9.34'
```
```
private ProjectListAdapter adapter;

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
```
```
public class ProjectListAdapter extends BaseQuickAdapter<FeedArticleData, ProjectListViewHolder>{

    public ProjectListAdapter(int layoutResId, @Nullable List<FeedArticleData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(ProjectListViewHolder helper, FeedArticleData item) {
      if (!TextUtils.isEmpty(item.getApkLink()))
        {
            helper.getView(R.id.item_project_list_install_tv).setVisibility(View.VISIBLE);
        }
        else
        {
            helper.getView(R.id.item_project_list_install_tv).setVisibility(View.GONE);
        }

        helper.addOnClickListener(R.id.item_project_list_install_tv);
    }
```
```
public class ProjectListViewHolder extends BaseViewHolder{
  @BindView(R.id.item_project_list_install_tv)
  TextView install;

public ProjectListViewHolder(View view) {
    super(view);
    ButterKnife.bind(this, view);
  }
}
```
