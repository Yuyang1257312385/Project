package com.lyj.mybrowner;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by yu on 2017/8/3.
 */

public class ActBrowser extends Activity {

    private static final String TAG = "LYJDE";

    private float TOTAL = 200f;
    private float mPressY;
    private float mPrePercent = 0;
    private float mLastPercent = 0;//上次最终的alpha percent

    private LinearLayout mTopLl;
    private WebView mWebView;
    private ProgressBar mProgressBar;
    private EditText mInputEt;
    private ImageButton mSearchIb,mDangerIb;


    private String mEngine;
    private SharedPreferences mSp;
    private SharedPreferences.Editor mEditor;
    private List<String> mJunkUrlList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_browser);
        mSp = getSharedPreferences(Constant.ENGINE,MODE_PRIVATE);
        mEditor = mSp.edit();
        mEngine = mSp.getString(Constant.SP_ENGINE,Constant.BAIDU_ENGINE);
        initView();
        initParams();
        initListener();

        Intent intent = getIntent();
        String content = intent.getStringExtra("Content");
        if(!TextUtils.isEmpty(content)){
            dealContent(content);
        }

    }

    private void initView() {
        mTopLl = (LinearLayout) findViewById(R.id.ll_top);
        mWebView = (WebView) findViewById(R.id.web_view);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_large);
        mInputEt = (EditText) findViewById(R.id.et_input);
        mSearchIb = (ImageButton) findViewById(R.id.ib_next);
        mDangerIb = (ImageButton) findViewById(R.id.ib_danger);
    }

    private void initParams() {
        int junkUrlCount = mSp.getInt(Constant.SP_JUNK_COUNT,0);
        for(int i=0;i<junkUrlCount;i++){
            String junkUrl = mSp.getString(Constant.SP_JUNK+(i+1),"");
            if(!TextUtils.isEmpty(junkUrl)){
                mJunkUrlList.add(junkUrl);
            }
        }

        WebSettings webSet = mWebView.getSettings();
        webSet.setJavaScriptEnabled(true); //支持js
        webSet.setDomStorageEnabled(true);//支持html5 dom storage
        //打开页面自适应屏幕
        webSet.setUseWideViewPort(true);//设置此属性，可任意比例缩
        webSet.setLoadWithOverviewMode(true);
        //支持缩放
        //webSet.setBuiltInZoomControls(true);
        //webSet.setSupportZoom(true);

        //多窗口
        webSet.supportMultipleWindows();
        //关闭webview中缓存
        webSet.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if(mJunkUrlList.contains(url)){
                    Toast.makeText(ActBrowser.this,"你自己设的垃圾站点",Toast.LENGTH_LONG).show();
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mProgressBar.setVisibility(View.INVISIBLE);
            }

            //此方法在非UI线程执行，用于拦截敏感信息
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                url = url.toLowerCase();
                if(ADFilterTool.hasPorn(ActBrowser.this,url)){
                    return new WebResourceResponse(null,null,null);
                }
                if (!ADFilterTool.hasAd(ActBrowser.this, url)) {
                    return super.shouldInterceptRequest(view, url);//正常加载
                }else{
                    return new WebResourceResponse(null,null,null);//含有广告资源屏蔽请求
                }
            }

            //
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                //handler.cancel(); 默认的处理方式，WebView变成空白页
                handler.proceed();//接受证书
                //handleMessage(Message msg); 其他处理
                Log.d(TAG,"onReceivedSslError"+error.toString());
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                Log.d(TAG,"onReceivedError"+error.toString());
                super.onReceivedError(view, request, error);
            }
        });


    }

    private void initListener() {
        mWebView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mPressY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float currentY = event.getY();
                        float distance = currentY - mPressY;
                        float currentPer;
                        if (distance > 0) {
                            //向下滑动,逐渐显示
                            currentPer = mLastPercent + distance / TOTAL;
                            if (currentPer > 1) {
                                mLastPercent = 1;
                                mTopLl.setAlpha(1);
                                return false;
                            }
                        } else {
                            //向上滑动，逐渐消失
                            distance = -distance;
                            currentPer = mLastPercent - distance / TOTAL;
                            if (currentPer < 0) {
                                mLastPercent = 0;
                                mTopLl.setAlpha(0);
                                return false;
                            }
                        }
                        Log.d("LYJDE", "CurrentY = " + currentY);
                        Log.d("LYJDE", "PressY = " + mPressY);
                        Log.d("LYJDE", "mPrePercent = " + mPrePercent);
                        Log.d("LYJDE", "currentPer = " + currentPer);
                        Log.d("LYJDE", "==============================");
                        startAnim(mTopLl, mPrePercent,currentPer);
                        mPrePercent = currentPer;
                        break;
                    case MotionEvent.ACTION_UP:
                        mLastPercent = mPrePercent;
                        break;
                }
                return  false;
            }
        });

        mSearchIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = mInputEt.getText().toString().trim();
                mInputEt.setText("");
                hideKeyBoard();
                if(!TextUtils.isEmpty(content)){
                    dealContent(content);
                }
            }
        });

        mDangerIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(ActBrowser.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
                        .setTitle("该网站将被列为不可访问网址")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                int junkCount = mSp.getInt(Constant.SP_JUNK_COUNT,0);
                                int nowJunkCount = junkCount +1;
                                if(!TextUtils.isEmpty(mWebView.getUrl())){
                                    mEditor.putString(Constant.SP_JUNK + nowJunkCount,mWebView.getUrl());
                                    mEditor.putInt(Constant.SP_JUNK_COUNT,nowJunkCount);
                                    mEditor.commit();
                                }
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        })
                        .create();
                dialog.show();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPressY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float currentY = event.getY();
                float distance = currentY - mPressY;
                float currentPer;
                if (distance > 0) {
                    //向下滑动,逐渐显示
                    currentPer = mLastPercent + distance / TOTAL;
                    if (currentPer > 1) {
                        mLastPercent = 1;
                        mTopLl.setAlpha(1);
                        return false;
                    }
                } else {
                    //向上滑动，逐渐消失
                    distance = -distance;
                    currentPer = mLastPercent - distance / TOTAL;
                    if (currentPer < 0) {
                        mLastPercent = 0;
                        mTopLl.setAlpha(0);
                        return false;
                    }
                }
                Log.d("LYJDE", "CurrentY = " + currentY);
                Log.d("LYJDE", "PressY = " + mPressY);
                Log.d("LYJDE", "mPrePercent = " + mPrePercent);
                Log.d("LYJDE", "currentPer = " + currentPer);
                Log.d("LYJDE", "==============================");
                startAnim(mTopLl, mPrePercent,currentPer);
                mPrePercent = currentPer;
                break;
            case MotionEvent.ACTION_UP:
                mLastPercent = mPrePercent;
                break;
        }
        return  false;
    }

    private void startAnim(View view, float currentPercent, float prePercent) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", prePercent, currentPercent);
        animator.setDuration(10);
        animator.start();
    }


    private void dealContent(String content){
        String result = "";
        if(ADFilterTool.hasPorn(this,content)){
            Toast.makeText(this,"含有敏感信息",Toast.LENGTH_LONG).show();
            return;
        }

        if(content.contains(".")){
            if((!content.startsWith("http")) && (!content.startsWith("https"))){
                result = "http://" + content;
            }else {
                result = content;
            }

            for(int i=0;i<mJunkUrlList.size();i++){
                String url = mJunkUrlList.get(i);
                if(url.contains(result.substring(5))){
                    Toast.makeText(ActBrowser.this,"你自己设的垃圾站点",Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }else {
                result = content;
        }



        Pattern pattern = Pattern
                .compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+$");
        boolean isUrlFormat = pattern.matcher(result).matches();

        if(isUrlFormat){
            mWebView.loadUrl(result);
        }else {
            String url = mEngine + result;
            mWebView.loadUrl(url);
        }
    }

    public static void actionStart(Context context,String  content){
        Intent intent = new Intent(context,ActBrowser.class);
        intent.putExtra("Content",content);
        context.startActivity(intent);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack())
        {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void hideKeyBoard() {
        ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(ActBrowser.this.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
    }

}
