package com.example.mytrip.ui;

import com.example.mytrip.R;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class NewsFragment extends Fragment {
	private WebView mNetWorkWv;
	private String url = "http://m.tuniu.com/";

	private ProgressBar progressBar;

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_news, null);
		mNetWorkWv = (WebView) v.findViewById(R.id.wv_news);
		progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
		mNetWorkWv.loadUrl(url);

		WebSettings settings = mNetWorkWv.getSettings();
		settings.setSupportZoom(true);
		settings.setBuiltInZoomControls(true);
	    settings.setJavaScriptEnabled(true);

		mNetWorkWv.setWebViewClient(new WebViewClient() {

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				super.onPageStarted(view, url, favicon);
				progressBar.setVisibility(View.VISIBLE);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
				progressBar.setVisibility(View.GONE);
			}

			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				// TODO Auto-generated method stub
				super.onReceivedError(view, errorCode, description, failingUrl);
				progressBar.setVisibility(View.GONE);
			}

		});
		return v;
	}
}
