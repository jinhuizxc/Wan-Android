package com.hsf1002.sky.wanandroid.ui.main.activity;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

import com.hsf1002.sky.wanandroid.R;
import com.hsf1002.sky.wanandroid.app.Constants;
import com.hsf1002.sky.wanandroid.base.activity.BaseActivity;
import com.hsf1002.sky.wanandroid.base.fragment.BaseFragment;
import com.hsf1002.sky.wanandroid.component.RxBus;
import com.hsf1002.sky.wanandroid.contract.main.MainContract;
import com.hsf1002.sky.wanandroid.core.DataManager;
import com.hsf1002.sky.wanandroid.core.event.LoginEvent;
import com.hsf1002.sky.wanandroid.core.http.cookies.CookiesManager;
import com.hsf1002.sky.wanandroid.presenter.main.MainPresenter;
import com.hsf1002.sky.wanandroid.ui.hierarchy.fragment.KnowledgeHierarchyFragment;
import com.hsf1002.sky.wanandroid.ui.mainpager.fragment.MainPagerFragment;
import com.hsf1002.sky.wanandroid.ui.navigation.fragment.NavigationFragment;
import com.hsf1002.sky.wanandroid.ui.project.fragment.ProjectFragment;
import com.hsf1002.sky.wanandroid.utils.BottomNavigationViewHelper;
import com.hsf1002.sky.wanandroid.utils.CommonAlertDialog;
import com.hsf1002.sky.wanandroid.utils.CommonUtils;
import com.hsf1002.sky.wanandroid.utils.StartActivityUtils;
import com.hsf1002.sky.wanandroid.utils.StatusBarUtil;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.common_toolbar)
    Toolbar toolbar;

    @BindView(R.id.common_toolbar_title_tv)
    TextView title;

    @BindView(R.id.main_floating_action_btn)
    FloatingActionButton floatingActionButton;

    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView bottomNavigationView;

    @BindView(R.id.main_view_stub)
    ViewStub errorView;

    @BindView(R.id.nav_view)
    NavigationView navigationView;


    @Inject
    DataManager dataManager;

    private ArrayList<BaseFragment> fragments;
    private TextView ustv;

    private MainPagerFragment mainPagerFragment;
    private KnowledgeHierarchyFragment knowledgeHierarchyFragment;
    private NavigationFragment navigationFragment;
    private ProjectFragment projectFragment;

    private int lastIndex;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CommonAlertDialog.newInstance().cancelDialog(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();

        initToolbar();
        initData();
        initNavigationView();

        // split the bottom item space fairly
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        dataManager.setCurrentPage(Constants.FIRST);
        bottomNavigationView.setOnNavigationItemSelectedListener(item ->
        {
            switch (item.getItemId())
            {
                case R.id.tab_main_pager:
                    title.setText(getString(R.string.home_pager));
                    switchFragment(0);
                    mainPagerFragment.reLoad();
                    dataManager.setCurrentPage(Constants.FIRST);
                    break;
                case R.id.tab_knowledge_hierarchy:
                    title.setText(getString(R.string.knowledge_hierarchy));
                    switchFragment(1);
                    knowledgeHierarchyFragment.reLoad();
                    dataManager.setCurrentPage(Constants.SECOND);
                    break;
                case R.id.tab_navigation:
                    title.setText(getString(R.string.navigation));
                    switchFragment(2);
                    navigationFragment.reLoad();
                    dataManager.setCurrentPage(Constants.THIRD);
                    break;
                case R.id.tab_project:
                    title.setText(getString(R.string.project));
                    switchFragment(3);
                    projectFragment.reLoad();
                    dataManager.setCurrentPage(Constants.FOURTH);
                    break;
                default:
                    break;
            }

            return true;
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                View content = drawerLayout.getChildAt(0);

                float scale = 1 - slideOffset;
                float endScale = 0.8f + scale * 0.2f;
                float startScale = 1 - 0.3f * scale;

                drawerView.setScaleX(startScale);
                drawerView.setScaleY(startScale);
                drawerView.setAlpha(0.6f + 0.4f * (1 - scale));

                content.setTranslationX(drawerView.getMeasuredWidth() * (1 - scale));
                content.invalidate();

                content.setScaleX(endScale);
                content.setScaleY(endScale);
            }
        };
        toggle.syncState();
        drawerLayout.addDrawerListener(toggle);
    }

    @Override
    public void showLoginView() {
        if (navigationView == null)
        {
            return;
        }
        ustv = navigationView.getHeaderView(0).findViewById(R.id.nav_header_login_tv);
        ustv.setText(dataManager.getLoginAccount());
        ustv.setOnClickListener(null);
        navigationView.getMenu().findItem(R.id.nav_item_logout).setVisible(true);
    }

    @Override
    public void showLogoutView() {
        super.showLogoutView();

        ustv.setText(R.string.login_in);
        ustv.setOnClickListener(v -> StartActivityUtils.startLoginActivity(this));
        if (navigationView == null)
        {
            return;
        }
        navigationView.getMenu().findItem(R.id.nav_item_logout).setVisible(false);
    }

    @Override
    public void showDismissErrorView() {

    }

    @Override
    public void showErrorView() {

    }

    @Override
    public void showSwitchProject() {

    }

    @Override
    public void showSwitchNavigation() {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    private void initToolbar()
    {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        title.setText(getString(R.string.home_pager));
        StatusBarUtil.immersive(this, ContextCompat.getColor(this, R.color.transparent), 0.5f);
        toolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
    }

    private void initData()
    {
        fragments = new ArrayList<>();
        mainPagerFragment = MainPagerFragment.getInstance(null, null);
        knowledgeHierarchyFragment = KnowledgeHierarchyFragment.getInstance(null, null);
        navigationFragment = NavigationFragment.getInstance(null, null);
        projectFragment = ProjectFragment.getInstance(null, null);

        fragments.add(mainPagerFragment);
        fragments.add(knowledgeHierarchyFragment);
        fragments.add(navigationFragment);
        fragments.add(projectFragment);

        switchFragment(0);
    }

    private void switchFragment(int position)
    {
        if (position >= fragments.size())
        {
            return;
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment target = fragments.get(position);
        Fragment old = fragments.get(lastIndex);
        lastIndex = position;

        ft.hide(old);

        if (!target.isAdded())
        {
            ft.add(R.id.fragment_group, target);
        }

        ft.show(target);
        ft.commitAllowingStateLoss();
    }

    private void initNavigationView()
    {
        ustv = navigationView.getHeaderView(0).findViewById(R.id.nav_header_login_tv);

        if (dataManager.getLoginStatus())
        {
            showLoginView();
        }
        else
        {
            showLogoutView();
        }

        navigationView.getMenu().findItem(R.id.nav_item_my_collect)
                .setOnMenuItemClickListener( item ->
                {
                    if (dataManager.getLoginStatus())
                    {
                        StartActivityUtils.startCollectActivity(this);
                        return true;
                    }
                    else
                    {
                        StartActivityUtils.startLoginActivity(this);
                        CommonUtils.showToastMessage(this, getString(R.string.login_tint));
                        return true;
                    }

                });
        navigationView.getMenu().findItem(R.id.nav_item_about_us)
                .setOnMenuItemClickListener( item ->
                {
                    StartActivityUtils.startAboutUsActivity(this);
                    return true;
                });
        navigationView.getMenu().findItem(R.id.nav_item_logout)
                .setOnMenuItemClickListener( item ->
                {
                    logout();
                    return true;
                });
    }

    private void logout()
    {
        CommonAlertDialog.newInstance().showDialog(this, getString(R.string.login_tint), getString(R.string.ok), getString(R.string.no),
                v -> {
                        CommonAlertDialog.newInstance().cancelDialog(true);
                        navigationView.getMenu().findItem(R.id.nav_item_logout).setVisible(false);
                        dataManager.setLoginStatus(false);
                        CookiesManager.clearAllCookies();
                        RxBus.getDefault().post(new LoginEvent(false));
                        StartActivityUtils.startLoginActivity(this);
                },
                v -> {
                        CommonAlertDialog.newInstance().cancelDialog(true);
                });
    }

    @Override
    public void onBackPressedSupport() {
        //super.onBackPressedSupport();
        if (getSupportFragmentManager().getBackStackEntryCount() > 1)
        {
            pop();
        }
        else
        {
            ActivityCompat.finishAfterTransition(this);
        }
    }
}
