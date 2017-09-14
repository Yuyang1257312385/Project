package com.lyj.baselib.net;

import android.content.Context;

import java.util.Map;

/**
 * Created by yu on 2017/8/16.
 */

public interface IEngineCallback {

    //在执行之前会做的方法,可以对应不同的项目需求做一些特殊化的操作
    void onPreExecute(Context context, Map<String,Object> params);

    void onSuccess(String result);

    void onFail(Exception e);

    void onProgress(long total, long current);

    //void onFinish();

    public static IEngineCallback DEFAULT_CALLBACK = new IEngineCallback() {
        @Override
        public void onPreExecute(Context context, Map<String,Object> params) {

        }

        @Override
        public void onSuccess(String result) {

        }

        @Override
        public void onFail(Exception e) {

        }

        @Override
        public void onProgress(long total, long current) {

        }
    };


}
