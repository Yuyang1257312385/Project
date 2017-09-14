package com.lyj.projectlib.net;

import android.content.Context;

import com.lyj.baselib.net.IEngineCallback;

import java.util.Map;

/**
 * Created by yu on 2017/8/16.
 */

public abstract class OkHttpCallBack implements IEngineCallback{

    @Override
    public void onPreExecute(Context context, Map<String, Object> params) {
        //参数处理

    }



    @Override
    public void onProgress(long total, long current) {

    }
}
