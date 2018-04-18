package com.hsf1002.sky.wanandroid.ui.main.activity;

import android.animation.ValueAnimator;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;


import com.hsf1002.sky.wanandroid.R;
import com.hsf1002.sky.wanandroid.base.activity.AbstractSimpleActivity;
import com.hsf1002.sky.wanandroid.utils.StatusBarUtil;
import com.hsf1002.sky.wanandroid.widget.interpolator.ElasticOutInterpolator;
import com.scwang.smartrefresh.header.FlyRefreshHeader;
import com.scwang.smartrefresh.header.flyrefresh.FlyView;
import com.scwang.smartrefresh.header.flyrefresh.MountainSceneView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hefeng on 18-4-17.
 */

public class AboutUsActivity extends AbstractSimpleActivity {

    @BindView(R.id.about_us_mountain)
    MountainSceneView mountainSceneView;

    @BindView(R.id.about_us_toolbar)
    Toolbar toolbar;

    @BindView(R.id.about_us_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.about_us_app_bar)
    AppBarLayout appBarLayout;

    @BindView(R.id.about_us_fly_refresh)
    FlyRefreshHeader flyRefreshHeader;

    @BindView(R.id.about_us_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;

    @BindView(R.id.about_us_fab)
    FloatingActionButton floatingActionButton;

    @BindView(R.id.about_us_fly_view)
    FlyView flyView;

    @BindView(R.id.about_us_content)
    NestedScrollView nestedScrollView;

    @BindView(R.id.aboutContent)
    TextView content;

    @BindView(R.id.aboutVersion)
    TextView version;


    private View.OnClickListener listener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void initEventAndData() {
        setSupportActionBar(toolbar);
        StatusBarUtil.immersive(this);
        StatusBarUtil.setPaddingSmart(this, toolbar);
        toolbar.setNavigationOnClickListener( v-> onBackPressedSupport());

        content.setText(Html.fromHtml(getString(R.string.about_content)));
        content.setMovementMethod(LinkMovementMethod.getInstance());

        try
        {
            String versionStr = getString(R.string.app_name) + " V" + getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            version.setText(versionStr);
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }

        flyRefreshHeader.setUp(mountainSceneView, flyView);
        smartRefreshLayout.setReboundInterpolator(new ElasticOutInterpolator());
        smartRefreshLayout.setReboundDuration(800);
        smartRefreshLayout.setOnRefreshListener(refreshLayout ->
        {
            updateTheme();
            refreshLayout.finishRefresh(1000);
        });

        smartRefreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener()
        {
            @Override
            public void onHeaderPulling(RefreshHeader header, float percent, int offset, int headerHeight, int extendHeight) {
                super.onHeaderPulling(header, percent, offset, headerHeight, extendHeight);

                if (appBarLayout == null || toolbar == null)
                {
                    return;
                }

                appBarLayout.setTranslationY(offset);
                toolbar.setTranslationY(-offset);
            }

            @Override
            public void onHeaderReleasing(RefreshHeader header, float percent, int offset, int footerHeight, int extendHeight) {
                super.onHeaderReleasing(header, percent, offset, footerHeight, extendHeight);

                if (appBarLayout == null || toolbar == null)
                {
                    return;
                }

                appBarLayout.setTranslationY(offset);
                toolbar.setTranslationY(-offset);
            }
        });

        smartRefreshLayout.autoRefresh();

        floatingActionButton.setOnClickListener( v-> smartRefreshLayout.autoRefresh());

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isAppbarExpand = true;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int scrollRange = appBarLayout.getTotalScrollRange();
                float fraction = 1f * (scrollRange + verticalOffset) / scrollRange;
                double minFraction = 0.1;
                double maxFraction = 0.8;

                if (fraction < minFraction && isAppbarExpand)
                {
                    isAppbarExpand = false;
                    floatingActionButton.animate().scaleX(0).scaleY(0);
                    flyView.animate().scaleX(0).scaleY(0);

                    ValueAnimator animator = ValueAnimator.ofInt(nestedScrollView.getPaddingTop(), 0);
                    animator.setDuration(300);
                    animator.addUpdateListener( animation -> {
                        nestedScrollView.setPadding(0, (int)animation.getAnimatedValue(), 0, 0);
                    });
                    animator.start();
                }

                if (fraction > maxFraction && !isAppbarExpand)
                {
                    isAppbarExpand = true;
                    floatingActionButton.animate().scaleX(1).scaleY(1);
                    flyView.animate().scaleX(1).scaleY(1);

                    ValueAnimator animator = ValueAnimator.ofInt(nestedScrollView.getPaddingTop(), DensityUtil.dp2px(25));
                    animator.setDuration(300);
                    animator.addUpdateListener( animation -> {
                        nestedScrollView.setPadding(0, (int)animation.getAnimatedValue(), 0, 0);
                    });
                    animator.start();
                }
            }
        });
    }

    private void updateTheme()
    {
        if (listener == null)
        {
            listener = new View.OnClickListener(){

                int index = 0;
                int[] idx = new int[]
                        {
                                android.R.color.holo_green_light,
                                android.R.color.holo_red_light,
                                android.R.color.holo_orange_light,
                                android.R.color.holo_blue_bright,
                        };

                @Override
                public void onClick(View v) {
                    int color = ContextCompat.getColor(getApplicationContext(), idx[index % idx.length]);
                    smartRefreshLayout.setPrimaryColors(color);
                    floatingActionButton.setBackgroundColor(color);
                    floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(color));
                    collapsingToolbarLayout.setContentScrimColor(color);
                    index++;
                }
            };
        }

        listener.onClick(null);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
