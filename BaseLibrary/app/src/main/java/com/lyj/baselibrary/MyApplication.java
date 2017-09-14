package com.lyj.baselibrary;

import android.app.Application;

import com.lyj.baselib.net.HttpUtils;
import com.lyj.projectlib.net.OkHttpEngine;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;


/**
 * Created by yu on 2017/8/14.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        setupLeakCanary();
        HttpUtils.init(new OkHttpEngine());
    }

    protected RefWatcher setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return null;
        }
        return LeakCanary.install(this);
    }
}
