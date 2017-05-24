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
	private com.example.myontheway01.view.SpringProgressView bar;//������
    private WebView loadWebWv;//������ҳ
    private String mUrl ="http://1111.alitrip.com/";//��ʾ����ַ
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exercise);
		bar = (com.example.myontheway01.view.SpringProgressView) findViewById(R.id.progressBar1);
		bar.setMaxCount(100.0f);
		loadWebWv = (WebView) findViewById(R.id.huodong_wv);
		//��������ļ�
		loadWebWv.getSettings().setAllowFileAccess(true);
		//������ʾ�ű�����
		loadWebWv.getSettings().setJavaScriptEnabled(true);
		//��ͼƬ�������ʺ� webview �Ĵ�С
		loadWebWv.getSettings().setUseWideViewPort(true); 
		//����һ����ַ
		loadWebWv.loadUrl(mUrl);
		//����ҳʱ������ϵͳ������� �����ڱ�WebView����ʾ,��Ƕ��ҳ��
		loadWebWv.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
		//����webview�ͻ���
		loadWebWv.setWebViewClient(new WebViewClient(){
			//������ҳ�Ƿ�������
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				bar.setVisibility(View.GONE);
				if(loadWebWv.getContentHeight()!=0){
					System.out.println("������");
				}
			}
			//�����ҳû�м��ص��������¼���
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				bar.setVisibility(View.VISIBLE);
				view.loadUrl(url);//���¼���
				return true;
			}
		});
		//�������ص�����ҳ
		loadWebWv.setWebChromeClient(new WebChromeClient(){
			//�����Ľ��ȵĸı�
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
				//bar.setProgress(newProgress);
				//bar.postInvalidate();//ˢ�½���
				bar.setCurrentCount(newProgress);
			}
		});
	}

}
