package com.hsf1002.sky.wanandroid.ui.main.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.hsf1002.sky.wanandroid.R;
import com.hsf1002.sky.wanandroid.app.Constants;
import com.hsf1002.sky.wanandroid.base.activity.BaseActivity;
import com.hsf1002.sky.wanandroid.component.RxBus;
import com.hsf1002.sky.wanandroid.contract.main.ArticleDetailContract;
import com.hsf1002.sky.wanandroid.core.DataManager;
import com.hsf1002.sky.wanandroid.core.bean.BaseResponse;
import com.hsf1002.sky.wanandroid.core.bean.main.collect.FeedArticleListData;
import com.hsf1002.sky.wanandroid.core.event.CollectEvent;
import com.hsf1002.sky.wanandroid.presenter.main.ArticleDetailPresenter;
import com.hsf1002.sky.wanandroid.utils.CommonUtils;
import com.hsf1002.sky.wanandroid.utils.StartActivityUtils;
import com.hsf1002.sky.wanandroid.utils.StatusBarUtil;
import com.just.agentweb.AgentWeb;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.lang.reflect.Method;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by hefeng on 18-4-13.
 */

public class ArticleDetailActivity extends BaseActivity<ArticleDetailPresenter> implements ArticleDetailContract.View {

    @BindView(R.id.article_detail_toolbar)
    Toolbar toolbar;

    @BindView(R.id.article_detail_web_view)
    FrameLayout webview;

    private Bundle bundle;
    private MenuItem collectItem;
    private int id;
    private String link;
    private String title;

    @Inject
    DataManager dataManager;
    private boolean isCollect;
    private boolean isCommonSite;
    private boolean isCollectPage;
    private AgentWeb agentWeb;

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

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_article_detail;
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();

        initToolBar();

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
    public boolean onCreateOptionsMenu(Menu menu) {
        bundle = getIntent().getExtras();
        assert bundle != null;
        isCommonSite = (Boolean)bundle.get(Constants.IS_COMMON_SITE);

        if (!isCommonSite)
        {
            getMenuInflater().inflate(R.menu.menu_acticle, menu);
            collectItem = menu.findItem(R.id.item_collect);

            if (isCollect)
            {
                collectItem.setTitle(getString(R.string.cancel_collect));
            }
            else
            {
                collectItem.setTitle(getString(R.string.collect));
            }
        }
        else
        {
            getMenuInflater().inflate(R.menu.menu_article_common, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.item_share:
                presenter.shareEventPermissionVerify(new RxPermissions(this));
                break;
            case R.id.item_collect:
                collectEvent();
                break;
            case R.id.item_system_browser:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // called when the flow menu visible, set the menu items having with icons
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null)
        {
            if (Constants.MENU_BUILDER.equalsIgnoreCase(menu.getClass().getSimpleName()))
            {
                try {
                    @SuppressLint("PrivateApi")
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public void shareEvent() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_type_url, getString(R.string.app_name), title, link));
        intent.setType("text/plain");
        startActivity(intent);
    }

    @Override
    public void shareError() {
        CommonUtils.showSnackMessage(this, getString(R.string.write_permission_not_allowed));
    }

    private void collectEvent()
    {
        if (!dataManager.getLoginStatus())
        {
            CommonUtils.showToastMessage(this, getString(R.string.login_tint));
            StartActivityUtils.startLoginActivity(this);
        }
        else
        {
            if (collectItem.getTitle().equals(getString(R.string.collect)))
            {
                presenter.addCollectArticle(id);
            }
            else
            {
                if (isCollectPage)
                {
                    presenter.cancelCollectPageArticle(id);
                }
                else
                {
                    presenter.cancelCollectArticle(id);
                }
            }
        }
    }

    @Override
    public void showLoginView() {

    }

    private void initToolBar()
    {
        bundle = getIntent().getExtras();
        assert  bundle != null;
        title = (String)bundle.get(Constants.ARTICLE_TITLE);
        setToolBar(toolbar, Html.fromHtml(title));
        StatusBarUtil.immersive(this);
        StatusBarUtil.setPaddingSmart(this, toolbar);

        toolbar.setNavigationOnClickListener( v->
        {
            if (isCollect)
            {
                RxBus.getDefault().post(new CollectEvent(false));
            }
            else
            {
                RxBus.getDefault().post(new CollectEvent(true));
            }
            onBackPressedSupport();
        });
        link = (String) bundle.get(Constants.ARTICLE_LINK);
        id = ((int) bundle.get(Constants.ARTICLE_ID));
        isCommonSite = ((boolean) bundle.get(Constants.IS_COMMON_SITE));
        isCollect = ((boolean) bundle.get(Constants.IS_COLLECT));
        isCollectPage = ((boolean) bundle.get(Constants.IS_COLLECT_PAGE));
    }

    @Override
    public void showCollectArticleData(BaseResponse<FeedArticleListData> feedArticleListResponse) {
        isCollect = true;
        collectItem.setTitle(getString(R.string.cancel_collect));
        CommonUtils.showSnackMessage(this, getString(R.string.collect_success));
    }

    @Override
    public void showCancelCollectArticleData(BaseResponse<FeedArticleListData> feedArticleListResponse) {
        isCollect = false;
        if (!isCollectPage)
        {
            collectItem.setTitle(getString(R.string.collect));
        }
        CommonUtils.showSnackMessage(this, getString(R.string.cancel_collect_success));
    }
}
