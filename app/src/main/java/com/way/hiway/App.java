package com.way.hiway;

import android.app.Application;
import android.content.Context;
import com.tencent.bugly.crashreport.CrashReport;

public class App extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        CrashReport.initCrashReport(this, BuildConfig.BUGLYID, false);
    }
}
