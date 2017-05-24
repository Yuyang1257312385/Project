package com.example.myontheway01.slideactivity;

import com.example.myontheway01.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ExerciseActivity extends Activity {
	private com.example.myontheway01.view.SpringProgressView bar;//进度条
    private WebView loadWebWv;//加载网页
    private String mUrl ="http://1111.alitrip.com/";//显示的网址
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exercise);
		bar = (com.example.myontheway01.view.SpringProgressView) findViewById(R.id.progressBar1);
		bar.setMaxCount(100.0f);
		loadWebWv = (WebView) findViewById(R.id.huodong_wv);
		//允许操作文件
		loadWebWv.getSettings().setAllowFileAccess(true);
		//可以显示脚本语言
		loadWebWv.getSettings().setJavaScriptEnabled(true);
		//将图片调整到适合 webview 的大小
		loadWebWv.getSettings().setUseWideViewPort(true); 
		//设置一个网址
		loadWebWv.loadUrl(mUrl);
		//打开网页时不调用系统浏览器， 而是在本WebView中显示,内嵌网页：
		loadWebWv.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
		//监听webview客户端
		loadWebWv.setWebViewClient(new WebViewClient(){
			//监听网页是否加载完成
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				bar.setVisibility(View.GONE);
				if(loadWebWv.getContentHeight()!=0){
					System.out.println("有数据");
				}
			}
			//如果网页没有加载到，则重新加载
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				bar.setVisibility(View.VISIBLE);
				view.loadUrl(url);//重新加载
				return true;
			}
		});
		//监听加载到的网页
		loadWebWv.setWebChromeClient(new WebChromeClient(){
			//监听的进度的改变
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
				//bar.setProgress(newProgress);
				//bar.postInvalidate();//刷新界面
				bar.setCurrentCount(newProgress);
			}
		});
	}

}
