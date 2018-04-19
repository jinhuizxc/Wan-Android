package com.hsf1002.sky.wanandroid.ui.main.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hsf1002.sky.wanandroid.R;
import com.hsf1002.sky.wanandroid.core.dao.HistoryData;
import com.hsf1002.sky.wanandroid.ui.main.viewholder.SearchHistoryViewHolder;
import com.hsf1002.sky.wanandroid.utils.CommonUtils;

import java.util.List;

/**
 * Created by hefeng on 18-4-19.
 */

public class HistorySearchAdapter extends BaseQuickAdapter<HistoryData, SearchHistoryViewHolder> {
    public HistorySearchAdapter(int layoutResId, @Nullable List<HistoryData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(SearchHistoryViewHolder helper, HistoryData item) {
        helper.setText(R.id.item_search_history_tv, item.getData());
        helper.setTextColor(R.id.item_search_history_tv, CommonUtils.randomColor());

        helper.addOnClickListener(R.id.item_search_history_tv);
    }
}
