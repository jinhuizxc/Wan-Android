package com.hsf1002.sky.wanandroid.ui.main.viewholder;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.hsf1002.sky.wanandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hefeng on 18-4-19.
 */

public class SearchHistoryViewHolder extends BaseViewHolder{

    @BindView(R.id.item_search_history_tv)
    TextView searchHistoryTv;

    public SearchHistoryViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
