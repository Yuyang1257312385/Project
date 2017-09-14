package com.lyj.baselib.net;

import android.content.Context;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yu on 2017/8/16.
 */

public class HttpUtils{


    private String mUrl;
    private Context mContext;
    private int REQUEST_TYPE = POST_TYPE;
    private static final int GET_TYPE = 0x01;
    private static final int POST_TYPE = 0x02;

    private Map<String,Object> mParams;

    private static IHttpEngine mHttpEngine;

    private HttpUtils (Context context){
        mContext = context;
        mParams = new HashMap<>();
    }
    public static HttpUtils with(Context context){
        return new HttpUtils(context);
    }

    public HttpUtils get(){
        REQUEST_TYPE = GET_TYPE;
        return this;
    }

    public HttpUtils post(){
        REQUEST_TYPE = POST_TYPE;
        return this;
    }

    public HttpUtils url(String url){
        mUrl = url;
        return this;
    }

    public HttpUtils addParams(String key,Object value){
        mParams.put(key,value);
        return this;
    }

    public HttpUtils addParams(Map<String,Object> params){
        mParams.putAll(params);
        return this;
    }

    //添加回调 执行
    public void execute(IEngineCallback callback){

        if(callback == null){
            callback = IEngineCallback.DEFAULT_CALLBACK;
        }

        callback.onPreExecute(mContext,mParams);

        if(REQUEST_TYPE == GET_TYPE){
            get(mUrl,mParams,callback);
        }

        if(REQUEST_TYPE == POST_TYPE){
            post(mUrl,mParams,callback);
        }
    }

    public void execute(){
        execute(null);
    }

    //在application中初始化引擎
    public static void init(IHttpEngine httpEngine){
        mHttpEngine = httpEngine;
    }



    private void get(String url, Map<String, Object> params, IEngineCallback callback) {
        mHttpEngine.get(url,params,callback);
    }


    private void post(String url, Map<String, Object> params, IEngineCallback callback) {
        mHttpEngine.post(url,params,callback);
    }

    /**
     * 解析一个类上面的class信息
     * @param callback
     * @return
     */
    public static Class<?> analysClazzInfo(IEngineCallback callback){
        Type genType = callback.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType)genType).getActualTypeArguments();
        return (Class<?>) params[0];
    }

}
