package com.hsf1002.sky.wanandroid.utils;

import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by hefeng on 18-4-9.
 */

public class CommonUtils {

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
