package com.hsf1002.sky.wanandroid.core;

import com.hsf1002.sky.wanandroid.core.db.DbHelper;
import com.hsf1002.sky.wanandroid.core.http.HttpHelper;
import com.hsf1002.sky.wanandroid.core.prefs.PreferenceHelper;

import javax.inject.Inject;

/**
 * Created by hefeng on 18-4-10.
 */

public class DataManager  implements HttpHelper, DbHelper, PreferenceHelper {

    @Inject
    public DataManager() {
    }
}
