package com.hsf1002.sky.wanandroid.ui.mainpager.viewholder;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.hsf1002.sky.wanandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hefeng on 18-4-11.
 */

public class KnowledgeHierarchyListViewHolder extends BaseViewHolder {

    @BindView(R.id.item_search_pager_group)
    CardView cardView;

    @BindView(R.id.item_search_pager_like_iv)
    ImageView likeImage;

    @BindView(R.id.item_search_pager_title)
    TextView titleTv;

    @BindView(R.id.item_search_pager_author)
    TextView authorTv;

    @BindView(R.id.item_search_pager_tag_tv)
    TextView tagTv;

    @BindView(R.id.item_search_pager_chapterName)
    TextView chapterTv;

    @BindView(R.id.item_search_pager_niceDate)
    TextView niceDataTv;

    public KnowledgeHierarchyListViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
