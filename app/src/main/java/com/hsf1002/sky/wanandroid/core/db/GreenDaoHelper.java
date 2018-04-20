package com.hsf1002.sky.wanandroid.core.db;

import com.hsf1002.sky.wanandroid.app.GeeksApp;
import com.hsf1002.sky.wanandroid.core.dao.DaoSession;
import com.hsf1002.sky.wanandroid.core.dao.HistoryData;
import com.hsf1002.sky.wanandroid.core.dao.HistoryDataDao;

import java.util.Iterator;
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
        HistoryDataDao historyDataDao = daoSession.getHistoryDataDao();

        return historyDataDao.loadAll();
    }

    @Override
    public List<HistoryData> addHistoryData(String data) {
        HistoryDataDao historyDataDao = daoSession.getHistoryDataDao();
        List<HistoryData> historyDataList = historyDataDao.loadAll();
        HistoryData historyData = new HistoryData();
        historyData.setDate(System.currentTimeMillis());
        historyData.setData(data);

        Iterator<HistoryData> iterator = historyDataList.iterator();

        while(iterator.hasNext())
        {
            HistoryData historyData1 =iterator.next();

            if (historyData1.getData().equals(data))
            {
                historyDataList.remove(historyData1);
                historyDataList.add(historyData);
                historyDataDao.deleteAll();
                historyDataDao.insertInTx(historyDataList);

                return historyDataList;
            }
        }

        if (historyDataList.size() < 10)
        {
            historyDataDao.insert(historyData);
        }
        else
        {
            historyDataList.remove(0);
            historyDataList.add(historyData);
            historyDataDao.deleteAll();
            historyDataDao.insertInTx(historyDataList);
        }

        return historyDataList;
    }

    @Override
    public void clearHistoryData() {
        HistoryDataDao historyDataDao = daoSession.getHistoryDataDao();
        historyDataDao.deleteAll();
    }
}
