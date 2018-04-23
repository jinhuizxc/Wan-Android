package com.hsf1002.sky.wanandroid.base.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hsf1002.sky.wanandroid.app.GeeksApp;
import com.squareup.leakcanary.RefWatcher;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by hefeng on 18-4-11.
 */

public abstract class AbstractSimpleDialogFragment extends DialogFragment {
    private Unbinder unbinder;
    protected View rootView;
    private CompositeDisposable compositeDisposable;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayout(), container, false);
        unbinder = ButterKnife.bind(this, rootView);
        compositeDisposable = new CompositeDisposable();
        initEventAndData();

        return rootView;// super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onDestroyView() {
        if (compositeDisposable != null)
        {
            compositeDisposable.clear();
        }
        unbinder.unbind();

        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        RefWatcher refWatcher = GeeksApp.getRefWatcher(getActivity());
        refWatcher.watch(this);
        super.onDestroy();
    }

    protected abstract int getLayout();
    protected abstract void initEventAndData();
}
