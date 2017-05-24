package com.example.mytrip.tools;


import com.example.mytrip.base.EnviromentConfig;
import com.orhanobut.logger.Logger;

/**
 * Created by yu on 2017/5/24.
 */

public class LogUtil {
    public static void d(String tag, String msg) {
        if (EnviromentConfig.IS_SHOW_LOG) {
            Logger.t(tag).d(msg);
        }
    }

    public static void json(String tag,String json){
        if(EnviromentConfig.IS_SHOW_LOG){
            Logger.t(tag).json(json);
        }
    }

    public static void i(String tag, String msg) {
        if (EnviromentConfig.IS_SHOW_LOG) {
            Logger.t(tag).i(msg);
        }
    }

    public static void w(String tag, String msg) {
        if (EnviromentConfig.IS_SHOW_LOG) {
            Logger.t(tag).w(msg);
        }
    }

    public static void e(String tag, String msg) {
        if (EnviromentConfig.IS_SHOW_LOG) {
            Logger.t(tag).e(msg);
        }
    }
}
