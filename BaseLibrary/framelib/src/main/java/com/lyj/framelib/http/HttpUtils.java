package com.lyj.framelib.http;

import android.content.Context;
import android.text.TextUtils;

import com.lyj.framelib.http.okhttp.OkHttpEngine;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yu on 2017/8/14.
 */

public class HttpUtils {
    // 上下文
    private Context mContext;
    // 网络访问引擎
    private static IHttpEngine mHttpEngine = new OkHttpEngine();
    // 接口地址
    private String mUrl;
    // 请求参数
    private Map<String, Object> mParams;
    // get请求标识
    private final int GET_REQUEST = 0x0011;
    // post请求标识
    private final int POST_REQUEST = 0x0022;
    // 请求的方式
    private int mRequestMethod = GET_REQUEST;

    // 是否缓存
    private boolean mCache = false;

    // 切换引擎
    public void exchangeEngine(IHttpEngine httpEngine){
        this.mHttpEngine = httpEngine;
    }

    // 可以在Application中配置HttpEngine
    public  void initEngine(IHttpEngine httpEngine){
        this.mHttpEngine = httpEngine;
    }

    private HttpUtils(Context context) {
        this.mContext = context;
        mParams = new HashMap<>();
    }

    public static HttpUtils with(Context context) {
        return new HttpUtils(context);
    }

    public HttpUtils url(String url) {
        mUrl = url;
        return this;
    }

    // 执行方法
    public void execute(HttpCallBack httpCallBack) {
        if (TextUtils.isEmpty(mUrl)) {
            throw new NullPointerException("访问路径不能为空");
        }

        if (mRequestMethod == GET_REQUEST) {
            mHttpEngine.get(mContext,mUrl, mParams, httpCallBack,false);
        }

        if (mRequestMethod == POST_REQUEST) {
            mHttpEngine.post(mContext,mUrl, mParams, httpCallBack,false);
        }
    }
}
