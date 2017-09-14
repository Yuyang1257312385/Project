package com.lyj.projectlib.net;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.lyj.baselib.net.IEngineCallback;
import com.lyj.baselib.net.IHttpEngine;
import com.lyj.projectlib.net.cookie.CookiesManager;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yu on 2017/8/16.
 */

public class OkHttpEngine implements IHttpEngine {

    private final String TAG = "NetRequest";

    private OkHttpClient mClient ;

    private Handler mHandler ;

    public OkHttpEngine(){
        mClient = new OkHttpClient();

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        if (BuildConfig.DEBUG) {
//            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//            builder.addInterceptor(logging);
//        }
        mClient = builder.connectTimeout(60, TimeUnit.SECONDS)
                .cookieJar(new CookiesManager())
                .retryOnConnectionFailure(false)
                .hostnameVerifier(new Https.UnSafeHostnameVerifier())//不校验域名是否和https证书的域名一致
//                .addInterceptor(new RetryIntercepter(1))
                .build();
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void get(String url, Map<String, Object> params, final IEngineCallback callBack) {
        Request request = new Request.Builder().url(url).get().build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                //这里是子线程
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onFail(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if(response != null && response.isSuccessful()){
                    //这里是子线程

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String result = response.body().string();
                                callBack.onSuccess(result);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                }
            }
        });
    }

    @Override
    public void post(String url, Map<String, Object> params, final IEngineCallback callBack) {
        Log.d(TAG, "requestUrl = " + url);
        JSONObject jsonObject = new JSONObject(params);
        String requestJson = jsonObject.toString();
        Log.d(TAG,"URL = " + url);
        Log.d(TAG,"RequestJson = " + requestJson);
        FormBody formBody = new FormBody.Builder()
                .add("jsonStr", requestJson)
                .add("macStr", StringUtil.getMd5SaltStr(requestJson))
                .build();
        final Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                //这里是子线程
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onFail(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                if(response != null && response.isSuccessful()){
                    //这里是子线程

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String result = response.body().string();
                                callBack.onSuccess(result);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                }

            }
        });

    }



}
