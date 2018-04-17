package com.hsf1002.sky.wanandroid.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by hefeng on 18-4-17.
 */

public class ImageLoader {

    public static void load(Context context, String url, ImageView iv)
    {
        Glide.with(context).load(url).into(iv);
    }
}
