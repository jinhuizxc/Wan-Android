package com.hsf1002.sky.wanandroid.core;

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
import com.hsf1002.sky.wanandroid.core.dao.HistoryData;
import com.hsf1002.sky.wanandroid.core.db.DbHelper;
import com.hsf1002.sky.wanandroid.core.http.HttpHelper;
import com.hsf1002.sky.wanandroid.core.prefs.PreferenceHelper;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by hefeng on 18-4-10.
 */

public class DataManager implements HttpHelper, DbHelper, PreferenceHelper {

    private HttpHelper httpHelper;
    private DbHelper dbHelper;
    private PreferenceHelper preferenceHelper;

    public DataManager(HttpHelper httpHelper, DbHelper dbHelper, PreferenceHelper preferenceHelper) {
        this.httpHelper = httpHelper;
        this.dbHelper = dbHelper;
        this.preferenceHelper = preferenceHelper;
    }

    @Override
    public void setLoginAccount(String account) {
        preferenceHelper.setLoginAccount(account);
    }

    @Override
    public void setLoginPassword(String password) {
        preferenceHelper.setLoginPassword(password);
    }

    @Override
    public void setLoginStatus(boolean isLogin) {
        preferenceHelper.setLoginStatus(isLogin);
    }

    @Override
    public String getLoginAccount() {
        return preferenceHelper.getLoginAccount();
    }

    @Override
    public String getLoginPassword() {
        return preferenceHelper.getLoginPassword();
    }

    @Override
    public boolean getLoginStatus() {
        return preferenceHelper.getLoginStatus();
    }

    @Override
    public void setCurrentPage(int page) {
        preferenceHelper.setCurrentPage(page);
    }

    @Override
    public int getCurrentPage() {
        return preferenceHelper.getCurrentPage();
    }

    @Override
    public void setProjectCurrentPage(int page) {
        preferenceHelper.setProjectCurrentPage(page);
    }

    @Override
    public int getProjectCurrentPage() {
        return preferenceHelper.getProjectCurrentPage();
    }

    @Override
    public Observable<BaseResponse<FeedArticleListData>> getFeedArticleList(int pageNum) {
        return httpHelper.getFeedArticleList(pageNum);
    }

    @Override
    public Observable<BaseResponse<FeedArticleListData>> getSearchList(int pageNum, String k) {
        return httpHelper.getSearchList(pageNum, k);
    }

    @Override
    public Observable<BaseResponse<List<TopSearchData>>> getTopSearchData() {
        return httpHelper.getTopSearchData();
    }

    @Override
    public Observable<BaseResponse<List<UsefulSiteData>>> getUsefulSites() {
        return httpHelper.getUsefulSites();
    }

    @Override
    public Observable<BaseResponse<List<KnowledgeHierarchyData>>> getKnowledgeHierarchyData() {
        return httpHelper.getKnowledgeHierarchyData();
    }

    @Override
    public Observable<BaseResponse<FeedArticleListData>> getKnowledgeHierarchyDetailData(int page, int cid) {
        return httpHelper.getKnowledgeHierarchyDetailData(page, cid);
    }

    @Override
    public Observable<BaseResponse<List<NavigationListData>>> getNavigationListData() {
        return httpHelper.getNavigationListData();
    }

    @Override
    public Observable<BaseResponse<List<ProjectClassifyData>>> getProjectClassifyData() {
        return httpHelper.getProjectClassifyData();
    }

    @Override
    public Observable<BaseResponse<ProjectListData>> getProjectListData(int page, int cid) {
        return httpHelper.getProjectListData(page, cid);
    }

    @Override
    public Observable<BaseResponse<LoginData>> getLoginData(String username, String password) {
        return httpHelper.getLoginData(username, password);
    }

    @Override
    public Observable<BaseResponse<LoginData>> getRegisterData(String username, String password, String rePassword) {
        return httpHelper.getRegisterData(username, password, rePassword);
    }

    @Override
    public Observable<BaseResponse<FeedArticleListData>> addCollectArticle(int id) {
        return httpHelper.addCollectArticle(id);
    }

    @Override
    public Observable<BaseResponse<FeedArticleListData>> addCollectOutsideArticle(String title, String author, String link) {
        return httpHelper.addCollectOutsideArticle(title, author, link);
    }

    @Override
    public Observable<BaseResponse<FeedArticleListData>> getCollectList(int page) {
        return httpHelper.getCollectList(page);
    }

    @Override
    public Observable<BaseResponse<FeedArticleListData>> cancelCollectPageArticle(int id) {
        return httpHelper.cancelCollectPageArticle(id);
    }

    @Override
    public Observable<BaseResponse<FeedArticleListData>> cancelCollectArticle(int id) {
        return httpHelper.cancelCollectArticle(id);
    }

    @Override
    public Observable<BaseResponse<List<BannerData>>> getBannerData() {
        return httpHelper.getBannerData();
    }

    @Override
    public List<HistoryData> loadAllHistoryData() {
        return dbHelper.loadAllHistoryData();
    }

    @Override
    public List<HistoryData> addHistoryData(String data) {
        return dbHelper.addHistoryData(data);
    }

    @Override
    public void clearHistoryData() {
        dbHelper.clearHistoryData();
    }
}
