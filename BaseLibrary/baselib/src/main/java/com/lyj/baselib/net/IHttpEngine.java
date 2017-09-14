package com.lyj.baselib.net;

import android.content.Context;

import java.util.Map;

/**
 * Created by yu on 2017/8/16.
 */

public interface IHttpEngine {

    //get
    void get(String url, Map<String,Object> params, IEngineCallback callback);

    //post
    void post(String url,Map<String,Object> params,IEngineCallback callback);

    //上传文件

    //下载文件

    //https


}
