package com.lyj.framelib.http;

/**
 * Created by yu on 2017/8/14.
 */

public abstract class HttpCallBack {

    public abstract void onSuccess(String result);

    public abstract void onFail(Exception e);

    public void onProgress(long total, long current){};

}
