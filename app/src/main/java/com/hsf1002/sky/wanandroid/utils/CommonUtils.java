package com.hsf1002.sky.wanandroid.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.hsf1002.sky.wanandroid.R;
import com.hsf1002.sky.wanandroid.app.Constants;
import com.hsf1002.sky.wanandroid.app.GeeksApp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

/**
 * Created by hefeng on 18-4-9.
 */

public class CommonUtils {

    public static int dp2px(float dvValue)
    {
        final  float scale = GeeksApp.getInstance().getResources().getDisplayMetrics().density;
        return (int)(dvValue * scale + 0.5f);
    }

    public static void showToastMessage(Activity activity, String msg)
    {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showSnackMessage(Activity activity, String msg)
    {
        Snackbar snackbar = Snackbar.make(activity.getWindow().getDecorView(), msg, Snackbar.LENGTH_SHORT);
        View view = snackbar.getView();
        ((TextView)view.findViewById(R.id.snackbar_text)).setTextColor(ContextCompat.getColor(activity, R.color.white));
        snackbar.show();
    }

    public static boolean isNetworkConnected()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) GeeksApp.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        return connectivityManager.getActiveNetworkInfo() != null;
    }

    public static String getProcessName(int pid)
    {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processsName = reader.readLine();

            if (!TextUtils.isEmpty(processsName))
            {
                processsName = processsName.trim();
            }
            return processsName;
        }
        catch (Throwable throwable)
        {
            throwable.printStackTrace();
        }
        finally {
            try
            {
                if (reader != null)
                {
                    reader.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static int randomColor()
    {
        Random random = new Random();

        int red = random.nextInt(150);
        int green = random.nextInt(150);
        int blue = random.nextInt(150);

        return Color.rgb(red, green, blue);
    }

    public static int randomTagColor()
    {
        int randomNum = new Random().nextInt();
        int position = randomNum % Constants.TAB_COLORS.length;

        if (position < 0)
        {
            position  = -position;
        }

        return Constants.TAB_COLORS[position];
    }

    public static <T> T cast(Object object)
    {
        return (T)object;
    }
}
