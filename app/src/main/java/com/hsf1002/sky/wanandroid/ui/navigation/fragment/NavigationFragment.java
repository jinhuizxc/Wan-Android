package com.hsf1002.sky.wanandroid.ui.navigation.fragment;

import android.os.Bundle;

import com.hsf1002.sky.wanandroid.app.Constants;
import com.hsf1002.sky.wanandroid.base.fragment.AbstractRootFragment;
import com.hsf1002.sky.wanandroid.contract.navigation.NavigationContract;
import com.hsf1002.sky.wanandroid.presenter.navigation.NavigationPresenter;

/**
 * Created by hefeng on 18-4-10.
 */

public class NavigationFragment extends AbstractRootFragment<NavigationPresenter> implements NavigationContract.View{

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

    }
}
