package com.ispring.planeghost;

import android.app.Application;

import cn.waps.AppConnect;


/**
 * Created by yu on 2017/7/28.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppConnect.getInstance("461c5cb748b771d3f3b1e64e9f58657e","default",this);
    }
}
