package com.hsf1002.sky.wanandroid.ui.mainpager.fragment;

import android.os.Bundle;

import com.hsf1002.sky.wanandroid.app.Constants;
import com.hsf1002.sky.wanandroid.base.fragment.AbstractRootFragment;
import com.hsf1002.sky.wanandroid.contract.mainpager.MainPagerContract;
import com.hsf1002.sky.wanandroid.presenter.main.MainPresenter;
import com.hsf1002.sky.wanandroid.presenter.mainpager.MainPagerPresenter;

/**
 * Created by hefeng on 18-4-10.
 */

public class MainPagerFragment extends AbstractRootFragment<MainPagerPresenter> implements MainPagerContract.View{

    public static MainPagerFragment getInstance(String param1, String param2)
    {
        MainPagerFragment fragment = new MainPagerFragment();
        Bundle args = new Bundle();
        args.putString(Constants.ARG_PARAM1, param1);
        args.putString(Constants.ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void showAutoLoginSuccess() {

    }

    @Override
    public void showAutoLoginFail() {

    }
}
