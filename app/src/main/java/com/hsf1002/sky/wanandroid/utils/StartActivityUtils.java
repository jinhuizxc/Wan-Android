package com.hsf1002.sky.wanandroid.utils;

import android.content.Context;
import android.content.Intent;

import com.hsf1002.sky.wanandroid.app.Constants;
import com.hsf1002.sky.wanandroid.ui.hierarchy.activity.KnowledgeHierarchyDetailActivity;
import com.hsf1002.sky.wanandroid.ui.main.activity.ArticleDetailActivity;
import com.hsf1002.sky.wanandroid.ui.main.activity.LoginActivity;
import com.hsf1002.sky.wanandroid.ui.main.activity.SearchListActivity;

/**
 * Created by hefeng on 18-4-13.
 */

public class StartActivityUtils {

    public static void startArticleDetailActivity(Context context, int id, String articleTitle, String articleLink, boolean isCollect, boolean isCollectPage, boolean isCommonSite)
    {
        Intent intent = new Intent(context, ArticleDetailActivity.class);
        intent.putExtra(Constants.ARTICLE_ID, id);
        intent.putExtra(Constants.ARTICLE_TITLE, articleTitle);
        intent.putExtra(Constants.ARTICLE_LINK, articleLink);
        intent.putExtra(Constants.IS_COLLECT, isCollect);
        intent.putExtra(Constants.IS_COLLECT_PAGE, isCollectPage);
        intent.putExtra(Constants.IS_COMMON_SITE, isCommonSite);
        context.startActivity(intent);
    }

    public static void startKnowledgeHierarchyDetailActivity(Context context, boolean isSingleChapter,
                                                             String superChapterName, String chapterName, int chapterId) {
        Intent intent = new Intent(context, KnowledgeHierarchyDetailActivity.class);
        intent.putExtra(Constants.IS_SINGLE_CHAPTER, isSingleChapter);
        intent.putExtra(Constants.SUPER_CHAPTER_NAME, superChapterName);
        intent.putExtra(Constants.CHAPTER_NAME, chapterName);
        intent.putExtra(Constants.CHAPTER_ID, chapterId);
        context.startActivity(intent);
    }

    public static void startSearchListActivity(Context mActivity, String searchText) {
        Intent intent = new Intent(mActivity, SearchListActivity.class);
        intent.putExtra(Constants.SEARCH_TEXT, searchText);
        mActivity.startActivity(intent);
    }

    public static void startLoginActivity(Context mActivity) {
        Intent intent = new Intent(mActivity, LoginActivity.class);
        mActivity.startActivity(intent);
    }
}
