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
        if (cookies.size() > 0)
        {
            for (Cookie cookie:cookies) {
                COOKIE_STORE.add(url, cookie);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        return COOKIE_STORE.get(url);
    }

    public static void clearAllCookies()
    {
        COOKIE_STORE.removeAll();
    }
}
