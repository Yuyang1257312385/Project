package com.example.mytrip.tools;

import android.content.Context;
import android.graphics.Bitmap;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by yu on 2017/6/2.
 */

public class NetUtil {
    public static void doGet(Context context, String url, final NetCallBack callBack){
        OkGo.get(url)     // 请求方式和请求url
                .tag(context)                       // 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        callBack.onSuccess(s,call,response);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        callBack.onError(call, response, e);
                    }
                });
    }
}
