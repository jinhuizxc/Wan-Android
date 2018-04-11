package com.hsf1002.sky.wanandroid.core.http.cookies;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Created by hefeng on 18-4-11.
 */

public class CookiesManager implements CookieJar {
    private static final PersistentCookieStore COOKIE_STORE = new PersistentCookieStore();

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {

    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        return null;
    }
}
