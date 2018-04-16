package com.hsf1002.sky.wanandroid.ui.navigation.viewholder;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.hsf1002.sky.wanandroid.R;
import com.zhy.view.flowlayout.FlowLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hefeng on 18-4-16.
 */

public class NavigationViewHolder extends BaseViewHolder {

    @BindView(R.id.item_navigation_tv)
    TextView title;

    @BindView(R.id.item_navigation_flow_layout)
    FlowLayout flowLayout;

    public NavigationViewHolder(View view) {
        super(view);

        ButterKnife.bind(this, view);
    }
}
