package com.hsf1002.sky.wanandroid.ui.navigation.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hsf1002.sky.wanandroid.R;
import com.hsf1002.sky.wanandroid.core.bean.main.collect.FeedArticleData;
import com.hsf1002.sky.wanandroid.core.bean.navigation.NavigationListData;
import com.hsf1002.sky.wanandroid.ui.navigation.viewholder.NavigationViewHolder;
import com.hsf1002.sky.wanandroid.utils.CommonUtils;
import com.hsf1002.sky.wanandroid.utils.StartActivityUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

/**
 * Created by hefeng on 18-4-16.
 */

public class NavigationAdapter extends BaseQuickAdapter<NavigationListData, NavigationViewHolder> {

    public NavigationAdapter(int layoutResId, @Nullable List<NavigationListData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(NavigationViewHolder helper, NavigationListData item) {
        if (!TextUtils.isEmpty(item.getName()))
        {
            helper.setText(R.id.item_navigation_tv, item.getName());
        }

        TagFlowLayout tagFlowLayout = helper.getView(R.id.item_navigation_flow_layout);
        List<FeedArticleData> articleDataList = item.getArticles();

        tagFlowLayout.setAdapter(new TagAdapter<FeedArticleData>(articleDataList) {
            @Override
            public View getView(FlowLayout parent, int position, FeedArticleData feedArticleData) {
                TextView tv = (TextView)LayoutInflater.from(parent.getContext()).inflate(R.layout.flow_layout_tv, tagFlowLayout, false);

                if (feedArticleData == null)
                {
                    return null;
                }

                String name = feedArticleData.getTitle();

                tv.setPadding(CommonUtils.dp2px(10), CommonUtils.dp2px(10), CommonUtils.dp2px(10), CommonUtils.dp2px(10));
                tv.setText(name);
                tv.setTextColor(CommonUtils.randomColor());

                tagFlowLayout.setOnTagClickListener(((view, position1, parent1) ->
                {
                    StartActivityUtils.startArticleDetailActivity(parent.getContext(),
                            articleDataList.get(position1).getId(),
                            articleDataList.get(position1).getTitle(),
                            articleDataList.get(position1).getLink(),
                            articleDataList.get(position1).isCollect(),
                            false, false
                            );
                    return true;
                }));

                return tv;
            }
        });
    }
}
