package com.hsf1002.sky.wanandroid.ui.mainpager.adapter;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hsf1002.sky.wanandroid.R;
import com.hsf1002.sky.wanandroid.core.bean.main.collect.FeedArticleData;
import com.hsf1002.sky.wanandroid.ui.mainpager.viewholder.KnowledgeHierarchyListViewHolder;

import java.util.List;

/**
 * Created by hefeng on 18-4-11.
 */

public class ArticleListAdapter extends BaseQuickAdapter<FeedArticleData, KnowledgeHierarchyListViewHolder> {

    private boolean isCollectPage;
    private boolean isSearchPage;

    public ArticleListAdapter(int layoutResId, @Nullable List<FeedArticleData> data) {
        super(layoutResId, data);
    }

    public void setCollectPage()
    {
        isCollectPage = true;
        notifyDataSetChanged();
    }

    public void setSearchPage()
    {
        isSearchPage = true;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(KnowledgeHierarchyListViewHolder helper, FeedArticleData item) {

        if (!TextUtils.isEmpty(item.getTitle()))
        {
            helper.setText(R.id.item_search_pager_title, Html.fromHtml(item.getTitle()));
        }

        if (item.isCollect() || isCollectPage)
        {
            helper.setImageResource(R.id.item_search_pager_like_iv, R.drawable.icon_like_article_selected);
        }
        else
        {
            helper.setImageResource(R.id.item_search_pager_like_iv, R.drawable.icon_like_article_not_selected);
        }

        if (!TextUtils.isEmpty(item.getAuthor()))
        {
            helper.setText(R.id.item_search_pager_author, item.getAuthor());
        }

        setTag(helper, item);

        if (!TextUtils.isEmpty(item.getChapterName()))
        {
            String classifyName = item.getSuperChapterName() + " /" + item.getChapterName();

            if (isCollectPage)
            {
                helper.setText(R.id.item_search_pager_chapterName, item.getChapterName());
            }
            else
            {
                helper.setText(R.id.item_search_pager_chapterName, classifyName);
            }
        }

        if (!TextUtils.isEmpty(item.getNiceDate()))
        {
            helper.setText(R.id.item_search_pager_niceDate, item.getNiceDate());
        }

        if (isSearchPage)
        {
            CardView cardView = helper.getView(R.id.item_search_pager_group);
            cardView.setForeground(null);
            cardView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.selector_search_item_bac));
        }

        helper.addOnClickListener(R.id.item_search_pager_chapterName);
        helper.addOnClickListener(R.id.item_search_pager_like_iv);
        helper.addOnClickListener(R.id.item_search_pager_tag_tv);
    }

    private void setTag(KnowledgeHierarchyListViewHolder helper, FeedArticleData item)
    {
        helper.getView(R.id.item_search_pager_tag_tv).setVisibility(View.GONE);

        if (isCollectPage)
        {
            return;
        }

        if (item.getSuperChapterName().contains(mContext.getString(R.string.open_project)))
        {
            helper.getView(R.id.item_search_pager_tag_tv).setVisibility(View.VISIBLE);
            helper.setText(R.id.item_search_pager_tag_tv, R.string.project);
            helper.setTextColor(R.id.item_search_pager_tag_tv, ContextCompat.getColor(mContext, R.color.light_deep_red));
            helper.setBackgroundRes(R.id.item_search_pager_tag_tv, R.drawable.selector_tag_red_background);
        }

        if (item.getSuperChapterName().contains(mContext.getString(R.string.navigation)))
        {
            helper.getView(R.id.item_search_pager_tag_tv).setVisibility(View.VISIBLE);
            helper.setText(R.id.item_search_pager_tag_tv, R.string.navigation);
            helper.setTextColor(R.id.item_search_pager_tag_tv, ContextCompat.getColor(mContext, R.color.light_deep_red));
            helper.setBackgroundRes(R.id.item_search_pager_tag_tv, R.drawable.selector_tag_red_background);
        }

        if (item.getNiceDate().contains(mContext.getString(R.string.minute))
                || item.getNiceDate().contains(mContext.getString(R.string.hour))
                || item.getNiceDate().contains(mContext.getString(R.string.one_day))
                )
        {
            helper.getView(R.id.item_search_pager_tag_tv).setVisibility(View.VISIBLE);
            helper.setText(R.id.item_search_pager_tag_tv, R.string.text_new);
            helper.setTextColor(R.id.item_search_pager_tag_tv, ContextCompat.getColor(mContext, R.color.light_green));
            helper.setBackgroundRes(R.id.item_search_pager_tag_tv, R.drawable.shape_tag_green_background);
        }
    }
}
