package com.example.mytrip.ui.slideactivity;

import com.example.mytrip.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ExerciseActivity extends Activity {
	private com.example.mytrip.view.SpringProgressView bar;
    private WebView loadWebWv;
    private String mUrl ="http://1111.alitrip.com/";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exercise);
		bar = (com.example.mytrip.view.SpringProgressView) findViewById(R.id.progressBar1);
		bar.setMaxCount(100.0f);
		loadWebWv = (WebView) findViewById(R.id.huodong_wv);
		loadWebWv.getSettings().setAllowFileAccess(true);
		loadWebWv.getSettings().setJavaScriptEnabled(true);
		loadWebWv.getSettings().setUseWideViewPort(true);
		loadWebWv.loadUrl(mUrl);
		loadWebWv.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
		loadWebWv.setWebViewClient(new WebViewClient(){
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				bar.setVisibility(View.GONE);
				if(loadWebWv.getContentHeight()!=0){
					System.out.println("������");
				}
			}
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				bar.setVisibility(View.VISIBLE);
				view.loadUrl(url);
				return true;
			}
		});
		loadWebWv.setWebChromeClient(new WebChromeClient(){
			//�����Ľ��ȵĸı�
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
				//bar.setProgress(newProgress);
				//bar.postInvalidate();
				bar.setCurrentCount(newProgress);
			}
		});
	}

}
