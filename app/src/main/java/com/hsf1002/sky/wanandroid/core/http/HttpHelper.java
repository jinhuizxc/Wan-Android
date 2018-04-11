package com.hsf1002.sky.wanandroid.core.http;

import com.hsf1002.sky.wanandroid.core.bean.BaseResponse;
import com.hsf1002.sky.wanandroid.core.bean.hierarchy.KnowledgeHierarchyData;
import com.hsf1002.sky.wanandroid.core.bean.main.banner.BannerData;
import com.hsf1002.sky.wanandroid.core.bean.main.collect.FeedArticleListData;
import com.hsf1002.sky.wanandroid.core.bean.main.login.LoginData;
import com.hsf1002.sky.wanandroid.core.bean.main.search.TopSearchData;
import com.hsf1002.sky.wanandroid.core.bean.main.search.UsefulSiteData;
import com.hsf1002.sky.wanandroid.core.bean.navigation.NavigationListData;
import com.hsf1002.sky.wanandroid.core.bean.project.ProjectClassifyData;
import com.hsf1002.sky.wanandroid.core.bean.project.ProjectListData;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by hefeng on 18-4-10.
 */

public interface HttpHelper {

    Observable<BaseResponse<FeedArticleListData>> getFeedArticleList(int pageNum);
    Observable<BaseResponse<FeedArticleListData>> getSearchList(int pageNum, String k);

    Observable<BaseResponse<List<TopSearchData>>> getTopSearchData();
    Observable<BaseResponse<List<UsefulSiteData>>> getUsefulSites();

    Observable<BaseResponse<List<KnowledgeHierarchyData>>> getKnowledgeHierarchyData();
    Observable<BaseResponse<FeedArticleListData>> getKnowledgeHierarchyDetailData(int page, int cid);

    Observable<BaseResponse<List<NavigationListData>>> getNavigationListData();

    Observable<BaseResponse<List<ProjectClassifyData>>> getProjectClassifyData();
    Observable<BaseResponse<ProjectListData>> getProjectListData(int page, int cid);

    Observable<BaseResponse<LoginData>> getLoginData(String username, String password);
    Observable<BaseResponse<LoginData>> getRegisterData(String username, String password, String rePassword);

    Observable<BaseResponse<FeedArticleListData>> addCollectArticle(int id);
    Observable<BaseResponse<FeedArticleListData>> addCollectOutsideArticle(String title, String author, String link);
    Observable<BaseResponse<FeedArticleListData>> getCollectList(int page);
    Observable<BaseResponse<FeedArticleListData>> cancelCollectPageArticle(int id);
    Observable<BaseResponse<FeedArticleListData>> cancelCollectArticle(int id);

    Observable<BaseResponse<List<BannerData>>> getBannerData();
}
