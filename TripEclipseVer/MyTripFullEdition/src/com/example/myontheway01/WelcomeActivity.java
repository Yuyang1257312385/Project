package com.example.myontheway01;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;

import com.example.myontheway01.adapter.WelcomeViewPagerAdapter;
import com.example.myontheway01.view.View1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;

public class WelcomeActivity extends Activity implements OnPageChangeListener,OnClickListener{
	
   //�ؼ�
	private ViewPager mWelcomeVp;
   //���ϣ�����ΪView
	private List<View> mData;
	private Button mStartBtn;
	//������
	private WelcomeViewPagerAdapter adapter;
	//�����������СԲ���View
	private View[] mPoints; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_welcome);
		//��ʼ���ؼ�
		initView();
		//��ʼ������
		initData();
		//��ʼ��СԲ��
		initPoint();
	}
	/**
	 * ��ʼ���ؼ�
	 * */
	private void initView() {
		// TODO Auto-generated method stub
		mWelcomeVp=(ViewPager) findViewById(R.id.vp_welcome);
		mStartBtn=(Button) findViewById(R.id.btn_start);
		mStartBtn.setOnClickListener(this);
		mWelcomeVp.setOnPageChangeListener(this);
		mData=new ArrayList<View>();
		adapter=new WelcomeViewPagerAdapter(this);
		//��������
		mWelcomeVp.setAdapter(adapter);
		}
		/**
		 * ��ʼ������
		 * */
	private void initData() {
			// TODO Auto-generated method stub
			View1 v1=new View1(this, R.drawable.w1);
			View1 v2=new View1(this, R.drawable.w2);
			View1 v3=new View1(this, R.drawable.w3);
			View1 v4=new View1(this, R.drawable.w4);
			//###############Ϊv1.v????????
			mData.add(v1.v);
			mData.add(v2.v);
			mData.add(v3.v);
			mData.add(v4.v);
			//��ֵ������������ˢ��������
			adapter.setmData(mData);
			adapter.notifyDataSetChanged();
		}
   /**
    * ��ʼ��СԲ��
    * */
	private void initPoint() {
		// TODO Auto-generated method stub
		//���СԲ��Ĳ���
		ViewGroup viewGroup=(ViewGroup) findViewById(R.id.ll_point);
		//��ʼ�����飬���СΪ��ViewPager����ʾ�Ĳ��ֵĸ���
		mPoints=new View[mData.size()];
		for(int i=0;i<mPoints.length;i++){
			View view=new View(this);
			if(i==0){
				view.setLayoutParams(new LayoutParams(25, 25));
				view.setBackgroundResource(R.drawable.icon_like_red_32);
				//���뵽����
				mPoints[i]=view;
				//���õ�������
				viewGroup.addView(view);
			}else{
				view.setLayoutParams(new LayoutParams(25,25));
				view.setBackgroundResource(R.drawable.icon_like_white_34);
				mPoints[i]=view;
				viewGroup.addView(view);
			}
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub	
	}


	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

    /**
     * ѡ��
     * */
	@Override
	public void onPageSelected(int position) {
		// TODO Auto-generated method stub
		//�ǿ��жϣ�����Ҫ��
		if(mPoints!=null && mPoints.length>0){
			for(int i=0;i<mPoints.length;i++){
				if(position==i){
					mPoints[i].setBackgroundResource(R.drawable.icon_like_red_32);
					if(position==3){
						
						mStartBtn.setVisibility(View.VISIBLE);
						mStartBtn.setBackgroundResource(R.drawable.wel_btn_bg_off);
					}
				}else{
					mPoints[i].setBackgroundResource(R.drawable.icon_like_white_34);
					mStartBtn.setVisibility(View.GONE);
					
				}
			}
		}
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_start:
			mStartBtn.setBackgroundResource(R.drawable.wel_btn_bg_on);
			Intent intent=new Intent(WelcomeActivity.this,MainActivity.class);
			startActivity(intent);
			finish();
			break;

		default:
			break;
		}
	}
	
}