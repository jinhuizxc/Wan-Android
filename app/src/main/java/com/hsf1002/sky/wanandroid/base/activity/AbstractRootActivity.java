package com.hsf1002.sky.wanandroid.base.activity;

import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.hsf1002.sky.wanandroid.R;
import com.hsf1002.sky.wanandroid.base.presenter.BasePresenter;

/**
 * Created by hefeng on 18-4-18.
 */

public class AbstractRootActivity <T extends BasePresenter> extends BaseActivity<T> {

    private static final int NORMAL_STATE = 0;
    private static final int LOADING_STATE = 1;

    private LottieAnimationView lottieAnimationView;
    private View loadingView;
    private ViewGroup normalGroup;
    private ViewGroup parentGroup;

    private int currentState = NORMAL_STATE;

    @Override
    protected void initEventAndData() {
        super.initEventAndData();

        normalGroup = findViewById(R.id.normal_view);

        if (normalGroup == null) {
            throw new IllegalStateException("The subclass of RootActivity must contain a View named 'mNormalView'.");
        }

        if (!(normalGroup.getParent() instanceof ViewGroup))
        {
            throw new IllegalStateException("mNormalView's ParentView should be a ViewGroup.");
        }

        parentGroup = (ViewGroup)normalGroup.getParent();
        View.inflate(this, R.layout.loading_view, parentGroup);

        loadingView = parentGroup.findViewById(R.id.loading_group);
        lottieAnimationView = loadingView.findViewById(R.id.loading_animation);
        loadingView.setVisibility(View.GONE);
        normalGroup.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoading() {
        super.showLoading();

        if (currentState == LOADING_STATE)
        {
            return;
        }

        hideCurrentView();

        currentState = LOADING_STATE;
        loadingView.setVisibility(View.VISIBLE);
        lottieAnimationView.setAnimation("loading_bus.json");
        lottieAnimationView.playAnimation();
    }

    @Override
    public void showNormal() {
        super.showNormal();

        if (currentState == NORMAL_STATE)
        {
            return;
        }

        hideCurrentView();

        currentState = NORMAL_STATE;
        normalGroup.setVisibility(View.VISIBLE);
    }

    private void hideCurrentView()
    {
        switch (currentState)
        {
            case NORMAL_STATE:
                normalGroup.setVisibility(View.GONE);
                break;
            case LOADING_STATE:
                loadingView.setVisibility(View.GONE);
                lottieAnimationView.cancelAnimation();
                break;
            default:
                break;
        }
    }

    @Override
    public void showLoginView() {

    }

    @Override
    protected void initInject() {

    }
}
