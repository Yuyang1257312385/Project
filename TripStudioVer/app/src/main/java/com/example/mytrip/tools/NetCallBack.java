package com.example.mytrip.tools;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by yu on 2017/6/2.
 */

public abstract class NetCallBack {
    public abstract void onSuccess(String s, Call call, Response response);
    public abstract void onError(Call call, Response response, Exception e);
}
