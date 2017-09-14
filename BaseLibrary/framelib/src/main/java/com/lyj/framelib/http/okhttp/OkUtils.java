package com.lyj.framelib.http.okhttp;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.lyj.framelib.http.HttpCallBack;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by yu on 2017/8/15.
 */

public class OkUtils {
    public static final String TAG = "OKHTTP";
    private static final int SUCCESS = 0x01;
    private static final int FAIL = 0x02;
    private static final int PROGRESS = 0x03;

    private HttpCallBack mHttpCallBack;

    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case SUCCESS:
                    mHttpCallBack.onSuccess((String) msg.obj);
                    break;
                case FAIL:
                    mHttpCallBack.onFail((Exception) msg.obj);
                    break;
                case PROGRESS:
                    break;
            }
        }
    };

    static OkHttpClient client;

    static class OkImpl {
        static OkUtils instance = new OkUtils();
    }

    public static OkUtils instance() {
        return OkImpl.instance;
    }


    private OkUtils() {
    }

    /**
     * 初始化网络组件
     *
     * @param outTime 超时时间
     */
    public void init(int outTime,Context context) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        client = builder.connectTimeout(outTime, TimeUnit.SECONDS)
                .cookieJar(new CookieManager(context))
                .retryOnConnectionFailure(false)
                //.hostnameVerifier(new Https.UnSafeHostnameVerifier())//不校验域名是否和https证书的域名一致
//                .addInterceptor(new RetryIntercepter(1))
                .build();

    }

    public void get(String url, final HttpCallBack httpCallBack){


        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                httpCallBack.onFail(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                httpCallBack.onSuccess(response.body().toString());
            }
        });
    }

    public void post(String url, final Map<String, Object> params, final HttpCallBack httpCallBack){
        if (params == null) {
            return;
        }
        mHttpCallBack = httpCallBack;
        JSONObject jsonObject = new JSONObject(params);
        String requestJson = jsonObject.toString();
        Log.d(TAG,"URL = " + url);
        Log.d(TAG,"RequestJson = " + requestJson);
        RequestBody requestBody = new FormBody.Builder()
                .add("jsonStr", requestJson)
                .add("macStr", StringUtil.getMd5SaltStr(requestJson))
                .build();


        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                Message message = new Message();
                message.what = FAIL;
                message.obj = e;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message message = new Message();
                message.what = SUCCESS;
                message.obj = response.body().string();
                handler.sendMessage(message);
            }
        });
    }

    public void downloadFile(String fileUrl, String filePath, String fileName, final HttpCallBack callBack) {
        File dir = new File(filePath);
        if(!dir.exists()){
            dir.mkdirs();
        }

        final File file = new File(filePath, fileName);
        final Request request = new Request.Builder()
                .url(fileUrl)
                .get()
                .build();
        final Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, e.toString());
                callBack.onFail(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    long total = response.body().contentLength();
                    Log.d(TAG, "total------>" + total);
                    long current = 0;
                    is = response.body().byteStream();
                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf)) != -1) {
                        current += len;
                        fos.write(buf, 0, len);
                        Log.d(TAG, "current------>" + current);
                        callBack.onProgress(total, current);
                    }
                    fos.flush();
                    callBack.onSuccess(response.body().toString());

                } catch (IOException e) {
                    Log.e(TAG, e.toString());

                } finally {
                    try {
                        if (is != null) {
                            is.close();
                        }
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, e.toString());
                    }
                }
            }
        });
    }

}
