package com.tairan.hzhyq.gradleplugindemo;

import android.app.Application;
import android.widget.Toast;

import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.bugly.crashreport.CrashReport.UserStrategy;

/**
 * Created by hzhyq on 2017/4/11 0011.
 */

public class APP extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        UserStrategy userStrategy = new UserStrategy(getApplicationContext());
        userStrategy.setAppChannel(InitUtil.getChannel(getApplicationContext()));
        userStrategy.setAppPackageName(getApplicationContext().getPackageName());
        userStrategy.setAppVersion(InitUtil.getPackageVersion(getApplicationContext()));
        Toast.makeText(this,InitUtil.getChannel(getApplicationContext()) , Toast.LENGTH_SHORT).show();
        CrashReport.initCrashReport(getApplicationContext(), "f0297a3510", false,userStrategy);
    }
}
