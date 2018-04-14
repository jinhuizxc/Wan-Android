package com.hsf1002.sky.wanandroid.ui.hierarchy.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hsf1002.sky.wanandroid.R;
import com.hsf1002.sky.wanandroid.core.bean.hierarchy.KnowledgeHierarchyData;
import com.hsf1002.sky.wanandroid.ui.hierarchy.viewholder.KnowledgeHierarchyViewHolder;
import com.hsf1002.sky.wanandroid.utils.CommonUtils;

import java.util.List;

/**
 * Created by hefeng on 18-4-13.
 */

public class KnowledgeHierarchyAdapter extends BaseQuickAdapter<KnowledgeHierarchyData, KnowledgeHierarchyViewHolder> {

    public KnowledgeHierarchyAdapter(int layoutResId, @Nullable List<KnowledgeHierarchyData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(KnowledgeHierarchyViewHolder helper, KnowledgeHierarchyData item) {
        if (item.getName() == null) {
            return;
        }

        helper.setText(R.id.item_knowledge_hierarchy_title, item.getName());
        helper.setTextColor(R.id.item_knowledge_hierarchy_title, CommonUtils.randomColor());

        if (item.getChildren() == null)
        {
            return;
        }
        else
        {
            StringBuilder content = new StringBuilder();

            for (KnowledgeHierarchyData data:item.getChildren())
            {
                content.append(data.getName()).append("  ");
            }

            helper.setText(R.id.item_knowledge_hierarchy_content, content.toString());
        }
    }
}
