package com.hsf1002.sky.wanandroid.core.db;

import com.hsf1002.sky.wanandroid.core.dao.HistoryData;

import java.util.List;

/**
 * Created by hefeng on 18-4-10.
 */

public interface DbHelper {
    List<HistoryData> loadAllHistoryData();
    List<HistoryData> addHistoryData(String data);
    void clearHistoryData();
}
