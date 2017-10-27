package com.lyj.baselibrary;

import android.app.Application;

import com.lyj.baselib.net.HttpUtils;
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
    }

    protected RefWatcher setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return null;
        }
        return LeakCanary.install(this);
    }
}
