package com.example.myontheway01.view;

import java.util.zip.Inflater;

import com.example.myontheway01.R;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 设置导航，第二个参数为要设置的背景图片
 * */
public class View1{
	public View v;
	private LinearLayout mBackGroundLl;
	private LayoutInflater inflater;//注意此处为LayoutInflater
	
   public View1(Context context,int imgId){
	 inflater=LayoutInflater.from(context);
	 //绑定布局
	 v=inflater.inflate(R.layout.wel_viewpager_a, null);
	 //如何在代码中设置布局（先给布局一个id,然后初始化，就可以使用了）
	 mBackGroundLl=(LinearLayout) v.findViewById(R.id.ll_bg);
	 mBackGroundLl.setBackgroundResource(imgId);
   }
}
