package com.hsf1002.sky.wanandroid.ui.hierarchy.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.hsf1002.sky.wanandroid.R;
import com.hsf1002.sky.wanandroid.app.Constants;
import com.hsf1002.sky.wanandroid.base.activity.BaseActivity;
import com.hsf1002.sky.wanandroid.base.fragment.BaseFragment;
import com.hsf1002.sky.wanandroid.component.RxBus;
import com.hsf1002.sky.wanandroid.contract.hierarchy.KnowledgeHierarchyDetailContract;
import com.hsf1002.sky.wanandroid.core.bean.hierarchy.KnowledgeHierarchyData;
import com.hsf1002.sky.wanandroid.core.event.KnowledgeJumpTopEvent;
import com.hsf1002.sky.wanandroid.core.event.ReloadDetailEvent;
import com.hsf1002.sky.wanandroid.presenter.hierarchy.KnowledgeHierarchyDetailPresenter;
import com.hsf1002.sky.wanandroid.ui.hierarchy.fragment.KnowledgeHierarchyListFragment;
import com.hsf1002.sky.wanandroid.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hefeng on 18-4-13.
 */

public class KnowledgeHierarchyDetailActivity extends BaseActivity<KnowledgeHierarchyDetailPresenter> implements KnowledgeHierarchyDetailContract.View{

    @BindView(R.id.common_toolbar)
    Toolbar toolbar;

    @BindView(R.id.common_toolbar_title_tv)
    TextView title;

    @BindView(R.id.knowledge_hierarchy_detail_tab_layout)
    SlidingTabLayout slidingTabLayout;

    @BindView(R.id.knowledge_hierarchy_detail_viewpager)
    ViewPager viewPager;

    @BindView(R.id.knowledge_floating_action_btn)
    FloatingActionButton floatingActionButton;

    @BindView(R.id.detail_view_stub)
    ViewStub errorView;

    private List<KnowledgeHierarchyData> knowledgeHierarchyDataList;
    private ArrayList<BaseFragment> fragments;
    private String chapterName;


    /*
    * init the presenter, of vital importance
    * */
    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_knowledge_hierarchy_detail;
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        initToolbar();

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
    }

    @Override
    public void showLoginView() {

    }

    private void initToolbar()
    {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        StatusBarUtil.immersive(this, ContextCompat.getColor(this, R.color.transparent), 0.5f);
        StatusBarUtil.setPaddingSmart(this, toolbar);

        toolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
        fragments = new ArrayList<>();

        if (getIntent().getBooleanExtra(Constants.IS_SINGLE_CHAPTER, false))
        {
            String superChapterName = getIntent().getStringExtra(Constants.SUPER_CHAPTER_NAME);
            chapterName = getIntent().getStringExtra(Constants.CHAPTER_NAME);
            int chaperId = getIntent().getIntExtra(Constants.CHAPTER_ID, 0);

            title.setText(superChapterName);
            fragments.add(KnowledgeHierarchyListFragment.getInstance(chaperId, null));
        }
        else
        {
            KnowledgeHierarchyData knowledgeHierarchyData = (KnowledgeHierarchyData)getIntent().getSerializableExtra(Constants.ARG_PARAM1);
            title.setText(knowledgeHierarchyData.getName().trim());
            knowledgeHierarchyDataList = knowledgeHierarchyData.getChildren();

            if (knowledgeHierarchyDataList == null)
            {
                return;
            }
            else
            {
                for (KnowledgeHierarchyData data : knowledgeHierarchyDataList)
                {
                    fragments.add(KnowledgeHierarchyListFragment.getInstance(data.getId(), null));
                }
            }
        }
    }

    @Override
    public void showError() {
        super.showError();
        errorView.setVisibility(View.VISIBLE);

        TextView reload = findViewById(R.id.error_reload_tv);
        reload.setOnClickListener( v -> RxBus.getDefault().post(new ReloadDetailEvent()));
    }

    @OnClick({R.id.knowledge_floating_action_btn})
    void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.knowledge_floating_action_btn:
                RxBus.getDefault().post(new KnowledgeJumpTopEvent());
                break;
            default:
                break;
        }
    }

    @Override
    public void showDismissDetailErrorView() {
        if (errorView != null)
        {
            errorView.setVisibility(View.GONE);
        }
    }

    @Override
    public void showDetailErrorView() {
        if (errorView != null)
        {
            showError();
        }
    }

    @Override
    public void showSwitchProject() {
        onBackPressedSupport();
    }

    @Override
    public void showSwitchNavigation() {
        onBackPressedSupport();
    }
}
