package com.example.yu.jokyeassy.base;

import android.app.Application;

/**
 * Created by Administrator on 2018/5/1.
 */

public class JokeApplication extends Application {

    private static JokeApplication mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }

    public synchronized static JokeApplication getInstance() {
        return mApplication;
    }
}
