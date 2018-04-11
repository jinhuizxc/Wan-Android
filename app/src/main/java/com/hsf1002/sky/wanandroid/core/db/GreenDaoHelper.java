package com.hsf1002.sky.wanandroid.core.db;

import com.hsf1002.sky.wanandroid.app.GeeksApp;
import com.hsf1002.sky.wanandroid.core.dao.DaoSession;
import com.hsf1002.sky.wanandroid.core.dao.HistoryData;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by hefeng on 18-4-11.
 */

public class GreenDaoHelper implements DbHelper {

    private DaoSession daoSession;

    @Inject
    public GreenDaoHelper() {
        this.daoSession = GeeksApp.getInstance().getDaoSession();
    }

    @Override
    public List<HistoryData> loadAllHistoryData() {
        return null;
    }

    @Override
    public List<HistoryData> addHistoryData(String data) {
        return null;
    }

    @Override
    public void clearHistoryData() {

    }
}
