package com.example.myontheway01.view;

import java.util.zip.Inflater;

import com.example.myontheway01.R;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

/**
 * ���õ������ڶ�������ΪҪ���õı���ͼƬ
 * */
public class View1{
	public View v;
	private LinearLayout mBackGroundLl;
	private LayoutInflater inflater;//ע��˴�ΪLayoutInflater
	
   public View1(Context context,int imgId){
	 inflater=LayoutInflater.from(context);
	 //�󶨲���
	 v=inflater.inflate(R.layout.wel_viewpager_a, null);
	 //����ڴ��������ò��֣��ȸ�����һ��id,Ȼ���ʼ�����Ϳ���ʹ���ˣ�
	 mBackGroundLl=(LinearLayout) v.findViewById(R.id.ll_bg);
	 mBackGroundLl.setBackgroundResource(imgId);
   }
}