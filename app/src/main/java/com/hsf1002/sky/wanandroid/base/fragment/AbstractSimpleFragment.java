package com.hsf1002.sky.wanandroid.base.fragment;



import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hsf1002.sky.wanandroid.R;
import com.hsf1002.sky.wanandroid.app.GeeksApp;
import com.hsf1002.sky.wanandroid.utils.CommonUtils;
import com.squareup.leakcanary.RefWatcher;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by hefeng on 18-4-10.
 */

public abstract class AbstractSimpleFragment extends SupportFragment {
    private Unbinder unbinder;
    private long clickTime;
    private CompositeDisposable compositeDisposable;
    public boolean isInnerFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        unbinder = ButterKnife.bind(this, view);
        compositeDisposable = new CompositeDisposable();

        return view;//super.onCreateView(inflater, container, savedInstanceState);
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
        RefWatcher refWatcher = GeeksApp.getRefWatcher(_mActivity/*getActivity()*/);
        refWatcher.watch(this);

        super.onDestroy();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        initEventAndData();
    }

    @Override
    public boolean onBackPressedSupport() {
        if (getChildFragmentManager().getBackStackEntryCount() > 1)
        {
            popChild();
        }
        else
        {
            if (isInnerFragment)
            {
                _mActivity.finish();
                return true;
            }

            long currentTime = System.currentTimeMillis();
            long time = 2000;

            if (currentTime - clickTime > time)
            {
                CommonUtils.showSnackMessage(_mActivity, getString(R.string.double_click_exit_tint));
                clickTime = System.currentTimeMillis();
            }
            else
            {
                _mActivity.finish();
            }
        }

        return true;//super.onBackPressedSupport();
    }

    protected abstract int getLayout();
    protected abstract void initEventAndData();
}
