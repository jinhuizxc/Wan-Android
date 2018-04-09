package com.hsf1002.sky.wanandroid.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.hsf1002.sky.wanandroid.component.ActivityCollector;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;


/**
 * Created by hefeng on 18-4-8.
 */

public abstract class AbstractSimpleActivity extends SupportActivity {
    private Unbinder unbinder;
    protected AbstractSimpleActivity mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(this);
        mActivity = this;
        onViewCreated();
        ActivityCollector.getInstance().addActivity(this);
        initEventAndData();
    }

    @Override
    protected void onDestroy() {
        ActivityCollector.getInstance().removeActivity(this);
        unbinder.unbind();

        super.onDestroy();
    }

    protected void setToolBar(Toolbar toolBar, CharSequence title)
    {
        toolBar.setTitle(title);
        setSupportActionBar(toolBar);
        assert getSupportActionBar() != null;
    }

    protected void onViewCreated()
    {
    }

    protected abstract int getLayoutId();

    protected abstract void initEventAndData();
}
