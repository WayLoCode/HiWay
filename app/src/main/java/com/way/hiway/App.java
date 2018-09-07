package com.way.hiway;

import android.app.Application;
import com.tencent.bugly.crashreport.CrashReport;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        CrashReport.initCrashReport(this, BuildConfig.BUGLYID, false);
    }
}
