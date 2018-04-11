package com.hsf1002.sky.wanandroid.ui.main.fragment;

import android.view.ViewTreeObserver;

import com.hsf1002.sky.wanandroid.base.fragment.BaseDialogFragment;
import com.hsf1002.sky.wanandroid.contract.main.SearchContract;
import com.hsf1002.sky.wanandroid.presenter.main.SearchPresenter;
import com.hsf1002.sky.wanandroid.widget.CircularRevealAnim;

/**
 * Created by hefeng on 18-4-11.
 */

public class SearchDialogFragment extends BaseDialogFragment<SearchPresenter> implements
        SearchContract.View,
        CircularRevealAnim.AnimListener,
        ViewTreeObserver.OnPreDrawListener
{

    @Override
    public boolean onPreDraw() {
        return false;
    }
}
