package com.hsf1002.sky.wanandroid.ui.project.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.flyco.tablayout.SlidingTabLayout;
import com.hsf1002.sky.wanandroid.R;
import com.hsf1002.sky.wanandroid.app.Constants;
import com.hsf1002.sky.wanandroid.base.fragment.AbstractRootFragment;
import com.hsf1002.sky.wanandroid.base.fragment.BaseFragment;
import com.hsf1002.sky.wanandroid.component.RxBus;
import com.hsf1002.sky.wanandroid.contract.project.ProjectContract;
import com.hsf1002.sky.wanandroid.core.DataManager;
import com.hsf1002.sky.wanandroid.core.bean.BaseResponse;
import com.hsf1002.sky.wanandroid.core.bean.project.ProjectClassifyData;
import com.hsf1002.sky.wanandroid.core.event.DismissErrorView;
import com.hsf1002.sky.wanandroid.core.event.JumpToTheTopEvent;
import com.hsf1002.sky.wanandroid.core.event.ShowErrorView;
import com.hsf1002.sky.wanandroid.presenter.project.ProjectPresenter;
import com.hsf1002.sky.wanandroid.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by hefeng on 18-4-10.
 */

public class ProjectFragment extends AbstractRootFragment<ProjectPresenter> implements ProjectContract.View{

    @BindView(R.id.project_tab_layout)
    SlidingTabLayout slidingTabLayout;

    @BindView(R.id.project_divider)
    View divider;

    @BindView(R.id.project_viewpager)
    ViewPager viewPager;

    @Inject
    DataManager dataManager;

    private List<ProjectClassifyData> dataList;
    private ArrayList<BaseFragment> fragments;
    private int currentPage;

    @Override
    public void onDestroy() {
        super.onDestroy();
        dataManager.setProjectCurrentPage(currentPage);
    }

    public static ProjectFragment getInstance(String param1, String param2) {
        ProjectFragment fragment = new ProjectFragment();
        Bundle args = new Bundle();
        args.putString(Constants.ARG_PARAM1, param1);
        args.putString(Constants.ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_project;
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();

        fragments = new ArrayList<>();
        presenter.getProjectClassifyData();
        currentPage = dataManager.getProjectCurrentPage();
        showLoading();
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    public void showProjectClassifyData(BaseResponse<List<ProjectClassifyData>> projectClassifyResponse) {
        if (projectClassifyResponse == null || projectClassifyResponse.getData() == null)
        {
            showProjectClassifyDataFail();
            return;
        }
        else
        {
            RxBus.getDefault().post(new DismissErrorView());

            if (dataManager.getCurrentPage() == Constants.FOURTH)
            {
                slidingTabLayout.setVisibility(View.VISIBLE);
                divider.setVisibility(View.VISIBLE);
                viewPager.setVisibility(View.VISIBLE);
            }
            else
            {
                slidingTabLayout.setVisibility(View.INVISIBLE);
                divider.setVisibility(View.INVISIBLE);
                viewPager.setVisibility(View.INVISIBLE);
            }

            dataList = projectClassifyResponse.getData();

            for (ProjectClassifyData data : dataList)
            {
                ProjectListFragment projectListFragment = ProjectListFragment.getInstance(data.getId(), null);
                fragments.add(projectListFragment);
            }

            viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
                @Override
                public Fragment getItem(int position) {
                    return fragments.get(position);
                }

                @Override
                public int getCount() {
                    return dataList == null ? 0 : dataList.size();
                }

                @Override
                public CharSequence getPageTitle(int position) {
                    return dataList.get(position).getName();
                }
            });

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    currentPage = position;
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            slidingTabLayout.setViewPager(viewPager);
            viewPager.setCurrentItem(Constants.TAB_ONE);
            showNormal();
        }
    }

    @Override
    public void showProjectClassifyDataFail() {
        CommonUtils.showSnackMessage(_mActivity, getString(R.string.failed_to_obtain_project_classify_data));
    }

    @Override
    public void showError() {
        //super.showError();
        slidingTabLayout.setVisibility(View.INVISIBLE);
        divider.setVisibility(View.INVISIBLE);
        viewPager.setVisibility(View.INVISIBLE);

        RxBus.getDefault().post(new ShowErrorView());
    }

    public void reLoad()
    {
        if (presenter != null && slidingTabLayout.getVisibility() == View.INVISIBLE)
        {
            presenter.getProjectClassifyData();
        }
    }

    public void jumpToTop()
    {
        if (fragments != null)
        {
            RxBus.getDefault().post(new JumpToTheTopEvent());
        }
    }
}
