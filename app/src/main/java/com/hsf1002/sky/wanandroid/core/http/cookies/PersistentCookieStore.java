package com.hsf1002.sky.wanandroid.core.http.cookies;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.hsf1002.sky.wanandroid.app.GeeksApp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * Created by hefeng on 18-4-11.
 */

public class PersistentCookieStore {

    private static final String COOKIE_PREFS = "Cookies_Prefs";
    private final Map<String, ConcurrentHashMap<String, Cookie>> cookies;
    private final SharedPreferences cookiePrefs;


    public PersistentCookieStore() {

        cookiePrefs = GeeksApp.getInstance().getSharedPreferences(COOKIE_PREFS, 0);
        cookies = new HashMap<>();

        Map<String, ?> map = cookiePrefs.getAll();

        for (Map.Entry<String, ?> entry : map.entrySet())
        {
            String[] cookieNames = TextUtils.split((String)entry.getValue(), ",");

            for (String name : cookieNames)
            {
                String encodedCookie = cookiePrefs.getString(name, null);

                if (encodedCookie != null)
                {
                    Cookie decodedCookie = decodeCookie(encodedCookie);

                    if (decodedCookie != null)
                    {
                        if (!cookies.containsKey(entry.getKey()))
                        {
                            cookies.put(entry.getKey(), new ConcurrentHashMap<>());
                        }
                        cookies.get(entry.getKey()).put(name, decodedCookie);
                    }
                }
            }
        }
    }

    private String getCookieToken(Cookie cookie)
    {
        return  cookie.name() + "@" + cookie.domain();
    }

    public void add(HttpUrl url, Cookie cookie)
    {
        String name = getCookieToken(cookie);

        if (!cookie.persistent())
        {
            if (!cookies.containsKey(url.host()))
            {
                cookies.put(url.host(), new ConcurrentHashMap<>(10));
            }
            cookies.get(url.host()).put(name, cookie);
        }
        else
        {
            if (cookies.containsKey(url.host()))
            {
                cookies.get(url.host()).remove(name);
            }
        }

        SharedPreferences.Editor writer = cookiePrefs.edit();
        writer.putString(url.host(), TextUtils.join(",", cookies.get(url.host()).entrySet()));
        writer.putString(name, encodeCookie(new OkHttpCookies(cookie)));
        writer.apply();
    }

    public List<Cookie> get(HttpUrl url)
    {
        ArrayList<Cookie> ret = new ArrayList<>();

        if (cookies.containsKey(url.host()))
        {
            ret.addAll(cookies.get(url.host()).values());
        }

        return ret;
    }

    public void removeAll()
    {
        SharedPreferences.Editor writer = cookiePrefs.edit();
        writer.clear();
        writer.apply();
        cookies.clear();
    }

    public boolean remove(HttpUrl url, Cookie cookie)
    {
        String name = getCookieToken(cookie);

        if (cookies.containsKey(url.host()) && cookies.get(url.host()).contains(name))
        {
            cookies.get(url.host()).remove(name);

            SharedPreferences.Editor writer = cookiePrefs.edit();

            if (cookiePrefs.contains(name))
            {
                writer.remove(name);
            }

            writer.putString(url.host(), TextUtils.join(",", cookies.get(url.host()).keySet()));
            writer.apply();

            return true;
        }
        else
        {
            return false;
        }
    }


    public List<Cookie> getCookies()
    {
        ArrayList<Cookie> ret = new ArrayList<>();

        for (String key : cookies.keySet())
        {
            ret.addAll(cookies.get(key).values());
        }

        return ret;
    }

    private String encodeCookie(OkHttpCookies cookie)
    {
        if (cookie == null)
        {
            return null;
        }

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        try
        {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(os);
            objectOutputStream.writeObject(cookie);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return  null;
        }

        return byteArrayToHexString(os.toByteArray());
    }

    private Cookie decodeCookie(String cookieString)
    {
        byte[] bytes = hexStringToByteArray(cookieString);

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

        Cookie cookie = null;

        try
        {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            cookie =((OkHttpCookies) objectInputStream.readObject()).getCookies();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        return cookie;
    }


    /* bytes array converted to hex string*/
    private String byteArrayToHexString(byte[] bytes)
    {
        StringBuilder sb = new StringBuilder(bytes.length * 2);

        for (byte element : bytes)
        {
            int v = element & 0xff;
            if (v < 0xf)
            {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }

        return sb.toString().toUpperCase(Locale.US);
    }
    /* hex string converted to bytes array */
    private byte[] hexStringToByteArray(String hexString)
    {
        int len = hexString.length();
        byte[] data = new byte[len /2];

        for (int i=0; i<len; i += 2)
        {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character.digit(hexString.charAt(i + 1), 16));
        }

        return data;

    }
}
