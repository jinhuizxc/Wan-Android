package com.hsf1002.sky.wanandroid.app;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.ContextCompat;

import com.hsf1002.sky.wanandroid.R;
import com.hsf1002.sky.wanandroid.core.dao.DaoMaster;
import com.hsf1002.sky.wanandroid.core.dao.DaoSession;
import com.hsf1002.sky.wanandroid.di.component.AppComponent;
import com.hsf1002.sky.wanandroid.di.component.DaggerAppComponent;
import com.hsf1002.sky.wanandroid.di.module.AppModule;
import com.hsf1002.sky.wanandroid.di.module.HttpModule;
import com.hsf1002.sky.wanandroid.utils.CommonUtils;
import com.scwang.smartrefresh.header.DeliveryHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;

import dagger.internal.Beta;

/**
 * Created by hefeng on 18-4-8.
 */

public class GeeksApp extends Application {

    private static GeeksApp sInstance;
    private RefWatcher refWatcher;
    public static boolean sIsFirstRun;
    private static volatile AppComponent sAppComponent;


    static
    {
        SmartRefreshLayout.setDefaultRefreshHeaderCreater((context, refreshLayout) -> {
            //全局设置主题颜色
            refreshLayout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);
            //指定为Delivery Header，默认是贝塞尔雷达Header
            return new DeliveryHeader(context);
        });
        SmartRefreshLayout.setDefaultRefreshFooterCreater((context, layout) -> {
            //默认是 BallPulseFooter
            return new BallPulseFooter(context).setAnimatingColor(ContextCompat.getColor(context, R.color.colorPrimary));
        });

    }

    private DaoSession mDaoSession;

    public static synchronized GeeksApp getInstance()
    {
        return sInstance;
    }

    public static RefWatcher getRefWatcher(Context context)
    {
        GeeksApp application = (GeeksApp)context.getApplicationContext();
        return application.refWatcher;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;

        initGreenDao();

        refWatcher = LeakCanary.install(this);

        initBugly();
    }

    private void initGreenDao()
    {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, Constants.DB_NAME);
        SQLiteDatabase database = devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        mDaoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession()
    {
        return mDaoSession;
    }

    private void initBugly()
    {
        //String packageName = getApplicationContext().getPackageName();
        //String processName = CommonUtils.getProcessName(android.os.Process.myPid());

        //CrashReport.UserStrategy strategy = new CrashReport.UserStrategy((getApplicationContext()));
        //strategy.setUploadProcess((processName == null) || processName.equals(packageName));
        //CrashReport.initCrashReport(getApplicationContext(), Constants.BUGLY_ID, false, strategy);
        com.tencent.bugly.beta.Beta.autoCheckUpgrade = false;
        Bugly.init(getApplicationContext(), Constants.BUGLY_ID, false);
    }

    public static synchronized AppComponent getAppComponent()
    {
        if (sAppComponent == null)
        {
            sAppComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(sInstance))
                    .httpModule(new HttpModule())
                    .build();
        }

        return sAppComponent;
    }

}
