package com.hsf1002.sky.wanandroid.app;

import java.io.File;

/**
 * Created by hefeng on 18-4-9.
 */

public class Constants {
    static final String BUGLY_ID = "a29fb52485";
    static final String DB_NAME = "aws_wan_android.db";

    private static final String PATH_DATA = GeeksApp.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";
    public static final String PATH_CACHE = PATH_DATA + "/NetCache";
    public static final int CACHE_SIZE = 50;

    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOURTH = 3;


    public static final int TAB_ONE = 0;

    public static final String ARG_PARAM1 = "param1";
    public static final String ARG_PARAM2 = "param2";


    public static final String SEARCH_TEXT = "search_text";
    public static final String MENU_BUILDER = "MenuBuilder";
    public static final String LOGIN_DATA = "login_data";
    public static final String BANNER_DATA = "banner_data";
    public static final String ARTICLE_DATA = "article_data";



}
