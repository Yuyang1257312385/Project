package com.example.yu.baselibrary.utils;

import android.util.Log;

/**
 * Created by Administrator on 2018/5/1.
 */

public class LogUtil {

    private static int LOG_LEVEL = 0;

    private static final int LEVEL_V = 1;
    private static final int LEVEL_D = 2;
    private static final int LEVEL_I = 3;
    private static final int LEVEL_W = 4;
    private static final int LEVEL_E = 5;
    private static final int LEVEL_NOTHING = 6;

    public static void v(String TAG,String msg){
        if(LOG_LEVEL < LEVEL_V){
            Log.v(TAG,msg);
        }
    }

    public static void d(String TAG,String msg){
        if(LOG_LEVEL < LEVEL_D){
            Log.d(TAG,msg);
        }
    }

    public static void i(String TAG,String msg){
        if(LOG_LEVEL < LEVEL_I){
            Log.i(TAG,msg);
        }
    }

    public static void w(String TAG,String msg){
        if(LOG_LEVEL < LEVEL_W){
            Log.w(TAG,msg);
        }
    }

    public static void e(String TAG,String msg){
        if(LOG_LEVEL < LEVEL_E){
            Log.e(TAG,msg);
        }
    }
}
