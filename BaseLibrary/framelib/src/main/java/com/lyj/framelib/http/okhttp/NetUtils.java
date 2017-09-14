package com.lyj.framelib.http.okhttp;

import com.lyj.framelib.http.HttpCallBack;

import java.io.File;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yu on 2017/8/15.
 *
 * 网络请求的统一入口，若需要更换框架，只需要更换本类中的方法即可
 *
 * 根据项目的特定需求重新封装一次或修改
 */

public class NetUtils {

    /**
     * get请求
     *
     * @param url
     * @param httpCallBack
     */
    public static void get(String url, HttpCallBack httpCallBack){
        OkUtils.instance().get(url,httpCallBack);
    }

    /**
     * post请求
     *
     * @param url
     * @param params
     * @param httpCallBack
     */
    public static void post(String url,Map<String,Object> params,HttpCallBack httpCallBack){
        OkUtils.instance().post(url,params,httpCallBack);
    }

    /**
     *上传文件
     *
     * @param url
     * @param params
     * @param httpCallBack
     */
    public static void uploadFile(String url, Map<String,File> params,HttpCallBack httpCallBack){

    }

    public static void downloadFile(String url,String filePath,String fileName,HttpCallBack httpCallBack){
        OkUtils.instance().downloadFile(url,filePath,fileName,httpCallBack);
    }




}
