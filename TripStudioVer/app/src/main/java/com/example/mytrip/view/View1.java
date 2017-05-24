package com.example.mytrip.view;

import com.example.mytrip.R;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class View1{
	public View v;
	private LinearLayout mBackGroundLl;
	private LayoutInflater inflater;

	public View1(Context context, int imgId) {
		inflater = LayoutInflater.from(context);
		v = inflater.inflate(R.layout.wel_viewpager_a, null);
		mBackGroundLl = (LinearLayout) v.findViewById(R.id.ll_bg);
		mBackGroundLl.setBackgroundResource(imgId);
   }
}
