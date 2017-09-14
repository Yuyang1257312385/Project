package com.lyj.framelib.http;

import android.content.Context;

import java.util.Map;

/**
 * Created by yu on 2017/8/14.
 */

public interface IHttpEngine {

    // post 提交
    public void post(Context context, String url, Map<String, Object> params, HttpCallBack httpCallBack, boolean cache);

    // get 提交
    public void get(Context context, String url, Map<String, Object> params, HttpCallBack httpCallBack, boolean cache);

    // 取消请求
    // 下载文件
    // 上传文件
    // https添加安全证书
}
