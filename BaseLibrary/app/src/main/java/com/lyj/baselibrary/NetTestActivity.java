package com.lyj.baselibrary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lyj.baselib.net.HttpUtils;
import com.lyj.projectlib.net.OkHttpCallBack;
import com.lyj.projectlib.net.StringUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yu on 2017/8/15.
 */

public class NetTestActivity extends Activity implements View.OnClickListener{

    private final String TAG = "NetRequest";

    private Button mLoginBtn,mFuncInfoBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_net_test);
        mLoginBtn = (Button) findViewById(R.id.btn_login);
        mFuncInfoBtn = (Button) findViewById(R.id.btn_func_info);
        initAction();

    }

    private void initAction() {
        mLoginBtn.setOnClickListener(this);
        mFuncInfoBtn.setOnClickListener(this);
    }


    public static void actionStart(Context context ){
        Intent intent = new Intent(context,NetTestActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_func_info:

//                Map<String,Object> requestMap = new HashMap<>();
//                requestMap.put("CUSTID","6000052234");
//
//                Map forwardMap  = new HashMap();
//                forwardMap.put("CUSTID","6000052234");
//                forwardMap.put("SYSNO", "HZG_B");
//
//                requestMap.put("FORWARDREQUEST", forwardMap);
//                String url = "http://tech.qianhai.cn/ctp/market/downFuncAuth";
//                NetUtils.post(url, requestMap, new HttpCallBack() {
//                    @Override
//                    public void onSuccess(String result) {
//                        Log.d(TAG,"response succ= " + result);
//                    }
//
//                    @Override
//                    public void onFail(Exception e) {
//
//                    }
//                });

                break;
        }
    }

    private void login() {

        Map<String,Object> loginMap = new HashMap<>();
        loginMap.put("BUSRID","gst1502134");
        loginMap.put("OPERID","admin");
        loginMap.put("LOGINPWD","fcd618a987d97b34947fd3a4cd18d33c");
        loginMap.put("LOGINTYPE","Y");
        loginMap.put("SYSNO", "HZG_B");
        String url = "http://tech.qianhai.cn/ctp/login/login.do";

        HttpUtils.with(this).url(url).addParams(loginMap).post().execute(new OkHttpCallBack() {
            @Override
            public void onSuccess(String result) {
                Log.d(TAG,"response succ= " + result);
                Toast.makeText(NetTestActivity.this,"succ",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFail(Exception e) {
                Log.d(TAG,"response fail = " + e.toString());
            }


        });
    }
}
