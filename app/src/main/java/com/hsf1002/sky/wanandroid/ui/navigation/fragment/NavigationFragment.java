package com.hsf1002.sky.wanandroid.ui.navigation.fragment;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.hsf1002.sky.wanandroid.R;
import com.hsf1002.sky.wanandroid.app.Constants;
import com.hsf1002.sky.wanandroid.base.fragment.AbstractRootFragment;
import com.hsf1002.sky.wanandroid.component.RxBus;
import com.hsf1002.sky.wanandroid.contract.navigation.NavigationContract;
import com.hsf1002.sky.wanandroid.core.DataManager;
import com.hsf1002.sky.wanandroid.core.bean.BaseResponse;
import com.hsf1002.sky.wanandroid.core.bean.navigation.NavigationListData;
import com.hsf1002.sky.wanandroid.core.event.DismissErrorView;
import com.hsf1002.sky.wanandroid.core.event.ShowErrorView;
import com.hsf1002.sky.wanandroid.presenter.navigation.NavigationPresenter;
import com.hsf1002.sky.wanandroid.ui.navigation.adapter.NavigationAdapter;
import com.hsf1002.sky.wanandroid.utils.CommonUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.ITabView;
import q.rorbin.verticaltablayout.widget.TabView;

/**
 * Created by hefeng on 18-4-10.
 */

public class NavigationFragment extends AbstractRootFragment<NavigationPresenter> implements NavigationContract.View {

    @BindView(R.id.navigation_tab_layout)
    VerticalTabLayout verticalTabLayout;

    @BindView(R.id.normal_view)
    LinearLayout navigationNormalView;

    @BindView(R.id.navigation_divider)
    View divider;

    @BindView(R.id.navigation_RecyclerView)
    RecyclerView recyclerView;


    @Inject
    DataManager dataManager;

    private LinearLayoutManager manager;
    private boolean needScroll;
    private int index;
    private boolean isClickTab;


    public static NavigationFragment getInstance(String param1, String param2) {
        NavigationFragment fragment = new NavigationFragment();
        Bundle args = new Bundle();
        args.putString(Constants.ARG_PARAM1, param1);
        args.putString(Constants.ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_navigation;
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();

        presenter.getNavigationListData();
        showLoading();
    }

    @Override
    public void showNavigationListData(BaseResponse<List<NavigationListData>> navigationListResponse) {
        if (navigationListResponse == null || navigationListResponse.getData() == null) {
            showNavigationListFail();
            return;
        } else {
            RxBus.getDefault().post(new DismissErrorView());

            List<NavigationListData> navigationListDataList = navigationListResponse.getData();

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

            if (dataManager.getCurrentPage() == Constants.THIRD) {
                navigationNormalView.setVisibility(View.VISIBLE);
                divider.setVisibility(View.VISIBLE);
            } else {
                navigationNormalView.setVisibility(View.INVISIBLE);
                divider.setVisibility(View.INVISIBLE);
            }

            NavigationAdapter adapter = new NavigationAdapter(R.layout.item_navigation, navigationListDataList);
            recyclerView.setAdapter(adapter);
            manager = new LinearLayoutManager(_mActivity);
            recyclerView.setLayoutManager(manager);

            leftRightLinkage();
            showNormal();
        }
    }

    @Override
    public void showNavigationListFail() {
        CommonUtils.showSnackMessage(_mActivity, getString(R.string.failed_to_obtain_navigation_list));
    }

    @Override
    public void showError() {
        //super.showError();
        verticalTabLayout.setBackgroundColor(ContextCompat.getColor(_mActivity, R.color.transparent));
        navigationNormalView.setVisibility(View.INVISIBLE);
        divider.setVisibility(View.INVISIBLE);
        RxBus.getDefault().post(new ShowErrorView());
    }

    private void leftRightLinkage() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (needScroll && (newState == RecyclerView.SCROLL_STATE_IDLE)) {
                    needScroll = false;

                    int offset = index - manager.findFirstVisibleItemPosition();
                    if (offset >= 0 && offset < recyclerView.getChildCount()) {
                        int top = recyclerView.getChildAt(offset).getTop();
                        recyclerView.smoothScrollBy(0, top);
                    }
                }

                rightLinkageLeft(newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (needScroll) {
                    needScroll = false;

                    int offset = index - manager.findFirstVisibleItemPosition();

                    if (offset >= 0 && offset < recyclerView.getChildCount()) {
                        int top = recyclerView.getChildAt(offset).getTop();
                        recyclerView.smoothScrollBy(0, top);
                    }
                }
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
    }

    private void rightLinkageLeft(int newState)
    {
        if (newState == RecyclerView.SCROLL_STATE_IDLE)
        {
            if (isClickTab)
            {
                isClickTab = false;
                return;
            }

            int first = manager.findFirstVisibleItemPosition();

            if (index != first)
            {
                index = first;
                setChecked(index);
            }
        }
    }


    private void selectTag(int i)
    {
        index = i;
        recyclerView.stopScroll();
        smoothScrollToPosition(i);
    }

    private void setChecked(int i)
    {
        if (isClickTab)
        {
            isClickTab = false;
        }
        else
        {
            verticalTabLayout.setTabSelected(i);
        }
        index = i;
    }

    private void smoothScrollToPosition(int i)
    {
        int first = manager.findFirstVisibleItemPosition();
        int last = manager.findLastVisibleItemPosition();

        if (i <= first)
        {
            recyclerView.smoothScrollToPosition(i);
        }
        else if (i <= last)
        {
            int top = recyclerView.getChildAt(i - first).getTop();
            recyclerView.smoothScrollBy(0, top);
        }
        else
        {
            recyclerView.smoothScrollToPosition(i);
            needScroll = true;
        }
    }

    public void reLoad()
    {
        if (presenter != null && navigationNormalView.getVisibility() == View.INVISIBLE)
        {
            presenter.getNavigationListData();
        }
    }

    public void jumpToTop()
    {
        if (verticalTabLayout != null)
        {
            verticalTabLayout.setTabSelected(0);
        }
    }
}
