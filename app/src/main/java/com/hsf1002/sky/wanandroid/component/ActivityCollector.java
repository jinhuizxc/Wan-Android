package com.hsf1002.sky.wanandroid.component;

import android.app.Activity;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by hefeng on 18-4-8.
 */

public class ActivityCollector {

    private static ActivityCollector activityCollector;

    public synchronized static ActivityCollector getInstance()
    {
        if (activityCollector == null)
        {
            activityCollector = new ActivityCollector();
        }

        return  activityCollector;
    }

    private Set<Activity> activitySet;

    public void addActivity(Activity activity)
    {
        if (activitySet == null)
        {
            activitySet = new HashSet<>();
        }
        activitySet.add(activity);
    }

    public void removeActivity(Activity activity)
    {
        if (activitySet != null)
        {
            activitySet.remove(activity);
        }
    }

    public void exitApp()
    {
        if (activitySet != null)
        {
            synchronized (activitySet)
            {
                for (Activity activity:activitySet)
                {
                    activity.finish();
                }
            }
        }

        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
