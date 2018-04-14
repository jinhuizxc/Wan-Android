package com.hsf1002.sky.wanandroid.ui.hierarchy.viewholder;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.hsf1002.sky.wanandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hefeng on 18-4-13.
 */

public class KnowledgeHierarchyViewHolder extends BaseViewHolder {

    @BindView(R.id.item_knowledge_hierarchy_title)
    TextView title;

    @BindView(R.id.item_knowledge_hierarchy_content)
    TextView content;

    public KnowledgeHierarchyViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
