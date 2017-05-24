package com.example.mytrip.ui.fragment;

import com.example.mytrip.R;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class NewsFragment extends Fragment {
	private WebView mNetWorkWv;
	private String url = "http://m.tuniu.com/";// ;ţ

	// ������
	private ProgressBar progressBar;

	@SuppressLint("SetJavaScriptEnabled")
	// private String url="http://www.biadu.com/";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_news, null);
		mNetWorkWv = (WebView) v.findViewById(R.id.wv_news);
		progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
		// ���ʵ���ַ
		mNetWorkWv.loadUrl(url);

		WebSettings settings = mNetWorkWv.getSettings();
		settings.setSupportZoom(true); // ֧������
		settings.setBuiltInZoomControls(true); // ������������װ��
	    settings.setJavaScriptEnabled(true); // ����JS�ű�

		mNetWorkWv.setWebViewClient(new WebViewClient() {

			// ��ҳ���ؿ�ʼʱ���ã���ʾ������ʾ��ת������
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				super.onPageStarted(view, url, favicon);
				progressBar.setVisibility(View.VISIBLE);
			}

			// ��ҳ�������ʱ���ã����ؼ�����ʾ��ת������
			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
				progressBar.setVisibility(View.GONE);
			}

			// ��ҳ����ʧ��ʱ���ã����ؼ�����ʾ��ת������
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

//	// �����ؼ�����������һ������ ������finish���Լ� �˳����
//	private boolean onKeyDown(int keyCode,KeyEvent event) {
//		if ((keyCode == KeyEvent.KEYCODE_BACK) && mNetWorkWv.canGoBack()) {
//			mNetWorkWv.goBack(); // goBack()��ʾ����webView����һҳ��
//			return true;
//		} else if (keyCode == KeyEvent.KEYCODE_BACK) {
//			//ConfirmExit();// ���˷��ؼ������Ѿ����ܷ��أ���ִ���˳�ȷ��
//			//return true;
//			return super.onKeyDown(keyCode, event);
//		}
//		
//	}

//	private void ConfirmExit() {
//			AlertDialog.Builder ad=new AlertDialog.Builder(Main.this);
//			ad.setTitle("�˳�");
//	    	ad.setMessage("�Ƿ��˳����?");
//	    	ad.setPositiveButton("��", new DialogInterface.OnClickListener(){
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
