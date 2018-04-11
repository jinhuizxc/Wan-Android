package com.hsf1002.sky.wanandroid.core;

import com.hsf1002.sky.wanandroid.core.db.DbHelper;
import com.hsf1002.sky.wanandroid.core.http.HttpHelper;
import com.hsf1002.sky.wanandroid.core.prefs.PreferenceHelper;

import javax.inject.Inject;

/**
 * Created by hefeng on 18-4-10.
 */

public class DataManager  implements HttpHelper, DbHelper, PreferenceHelper {

    private HttpHelper httpHelper;
    private DbHelper dbHelper;
    private PreferenceHelper preferenceHelper;

    @Inject
    public DataManager() {
    }

    public DataManager(HttpHelper httpHelper, DbHelper dbHelper, PreferenceHelper preferenceHelper) {
        this.httpHelper = httpHelper;
        this.dbHelper = dbHelper;
        this.preferenceHelper = preferenceHelper;
    }
}
