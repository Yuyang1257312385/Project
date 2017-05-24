package com.example.myontheway01.fragment;

import com.example.myontheway01.MapActivity;
import com.example.myontheway01.R;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class NewsFragment extends Fragment {
	private WebView mNetWorkWv;
	private String url = "http://m.tuniu.com/";// 途牛

	// 进度条
	private ProgressBar progressBar;

	@SuppressLint("SetJavaScriptEnabled")
	// private String url="http://www.biadu.com/";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_news, null);
		mNetWorkWv = (WebView) v.findViewById(R.id.wv_news);
		progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
		// 访问的网址
		mNetWorkWv.loadUrl(url);

		WebSettings settings = mNetWorkWv.getSettings();
		settings.setSupportZoom(true); // 支持缩放
		settings.setBuiltInZoomControls(true); // 启用内置缩放装置
	    settings.setJavaScriptEnabled(true); // 启用JS脚本

		mNetWorkWv.setWebViewClient(new WebViewClient() {

			// 网页加载开始时调用，显示加载提示旋转进度条
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				super.onPageStarted(view, url, favicon);
				progressBar.setVisibility(android.view.View.VISIBLE);
			}

			// 网页加载完成时调用，隐藏加载提示旋转进度条
			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
				progressBar.setVisibility(android.view.View.GONE);
			}

			// 网页加载失败时调用，隐藏加载提示旋转进度条
			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				// TODO Auto-generated method stub
				super.onReceivedError(view, errorCode, description, failingUrl);
				progressBar.setVisibility(android.view.View.GONE);
			}

		});
		return v;
	}

//	// 按返回键让他返回上一级界面 而不是finish掉自己 退出软件
//	private boolean onKeyDown(int keyCode,KeyEvent event) {
//		if ((keyCode == KeyEvent.KEYCODE_BACK) && mNetWorkWv.canGoBack()) {
//			mNetWorkWv.goBack(); // goBack()表示返回webView的上一页面
//			return true;
//		} else if (keyCode == KeyEvent.KEYCODE_BACK) {
//			//ConfirmExit();// 按了返回键，但已经不能返回，则执行退出确认
//			//return true;
//			return super.onKeyDown(keyCode, event);
//		}
//		
//	}

//	private void ConfirmExit() {
//			AlertDialog.Builder ad=new AlertDialog.Builder(Main.this);
//			ad.setTitle("退出");
//	    	ad.setMessage("是否退出软件?");
//	    	ad.setPositiveButton("是", new DialogInterface.OnClickListener(){
//
//				@Override
//				public void onClick(DialogInterface arg0, int arg1) {
//					// TODO Auto-generated method stub
//					main.this.finish();
//				}
//	    	
//	    	});
//		}
}
