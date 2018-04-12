package com.hsf1002.sky.wanandroid.base.fragment;

import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.hsf1002.sky.wanandroid.R;
import com.hsf1002.sky.wanandroid.base.presenter.BasePresenter;

/**
 * Created by hefeng on 18-4-10.
 */

public abstract class AbstractRootFragment<T extends BasePresenter> extends BaseFragment<T> {
    private static final int NORMAL_STATE = 0;
    private static final int LOADING_STATE = 1;

    private LottieAnimationView lottieAnimationView;
    private View loadingView;
    private ViewGroup normalViewGroup;
    private ViewGroup parentViewGroup;

    private int currentState = NORMAL_STATE;

    @Override
    protected void initEventAndData() {
        //super.initEventAndData();
        if (getView() == null)
        {
            return;
        }

        normalViewGroup = getView().findViewById(R.id.normal_view);
        if (normalViewGroup == null)
        {
            throw new IllegalStateException("the subclass of RootActivity must constain a view named 'normalview'. ");
        }
        if (!(normalViewGroup.getParent() instanceof ViewGroup))
        {
            throw new IllegalStateException("normalview's parent should be a ViewGroup.");
        }

        parentViewGroup = (ViewGroup)normalViewGroup.getParent();
        View.inflate(_mActivity, R.layout.loading_view, parentViewGroup);

        loadingView = parentViewGroup.findViewById(R.id.loading_group);
        lottieAnimationView = loadingView.findViewById(R.id.loading_animation);

        loadingView.setVisibility(View.GONE);
        normalViewGroup.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoading() {
        //super.showLoading();
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
        //super.showNormal();
        if (currentState == NORMAL_STATE)
        {
            return;
        }

        hideCurrentView();

        currentState = NORMAL_STATE;
        normalViewGroup.setVisibility(View.VISIBLE);
    }

    private void hideCurrentView()
    {
        switch (currentState)
        {
            case NORMAL_STATE:
                normalViewGroup.setVisibility(View.GONE);
                break;
            case LOADING_STATE:
                lottieAnimationView.cancelAnimation();
                loadingView.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }
}
