package com.hsf1002.sky.wanandroid.utils;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.hsf1002.sky.wanandroid.R;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by hefeng on 18-4-9.
 */

public class CommonUtils {

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

}
