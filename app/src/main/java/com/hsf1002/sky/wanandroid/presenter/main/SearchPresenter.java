package com.hsf1002.sky.wanandroid.presenter.main;

import com.hsf1002.sky.wanandroid.base.presenter.BasePresenter;
import com.hsf1002.sky.wanandroid.contract.main.SearchContract;
import com.hsf1002.sky.wanandroid.core.DataManager;
import com.hsf1002.sky.wanandroid.core.bean.BaseResponse;
import com.hsf1002.sky.wanandroid.core.bean.main.search.TopSearchData;
import com.hsf1002.sky.wanandroid.core.bean.main.search.UsefulSiteData;
import com.hsf1002.sky.wanandroid.core.dao.HistoryData;
import com.hsf1002.sky.wanandroid.utils.RxUtils;
import com.hsf1002.sky.wanandroid.widget.BaseObserver;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by hefeng on 18-4-11.
 */

public class SearchPresenter extends BasePresenter<SearchContract.View> implements SearchContract.Presenter {

    private DataManager dataManager;

    @Inject
    public SearchPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(SearchContract.View view) {
        super.attachView(view);
    }

    @Override
    public void addHistoryData(String data) {
        addSubscribe(Observable.create((ObservableOnSubscribe<List<HistoryData>>) e ->
        {
            List<HistoryData> historyDataList = dataManager.addHistoryData(data);
            e.onNext(historyDataList);
        }).compose(RxUtils.rxSchedulerHelper()).subscribe( historyDataList ->
            view.judgeToTheSearchListActivity()));
    }

    @Override
    public void getTopSearchData() {
        addSubscribe(dataManager.getTopSearchData()
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new BaseObserver<BaseResponse<List<TopSearchData>>>(view)
                {
                    @Override
                    public void onNext(BaseResponse<List<TopSearchData>> listBaseResponse) {
                        if (listBaseResponse.getErrorCode() == BaseResponse.SUCCESS)
                        {
                            view.showTopSearchData(listBaseResponse);
                        }
                        else
                        {
                            view.showTopSearchDataFail();
                        }
                    }
                }));
    }

    @Override
    public void getUsefulSites() {
        addSubscribe(dataManager.getUsefulSites()
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new BaseObserver<BaseResponse<List<UsefulSiteData>>>(view)
                {
                    @Override
                    public void onNext(BaseResponse<List<UsefulSiteData>> listBaseResponse) {
                        if (listBaseResponse.getErrorCode() == BaseResponse.SUCCESS)
                        {
                            view.showUsefulSites(listBaseResponse);
                        }
                        else
                        {
                            view.showUsefulSitesDataFail();
                        }
                    }
                }));
    }

    @Override
    public void clearHistoryData() {
        dataManager.clearHistoryData();
    }
}
