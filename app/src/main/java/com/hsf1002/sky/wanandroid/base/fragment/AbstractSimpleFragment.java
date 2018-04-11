package com.hsf1002.sky.wanandroid.base.fragment;



import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

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



}
