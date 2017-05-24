package com.example.myontheway01.adapter;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class WelcomeViewPagerAdapter extends PagerAdapter{
   private List<View> mData;
   /**
    * 写set方法，在外部设置mData;
    * */
	public void setmData(List<View> mData) {
	this.mData = mData;
}
	/**
	 * 构造方法
	 * */
	public WelcomeViewPagerAdapter(Context context){
		
	}
   /**
    * 获取集合的长度
    * */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		//################debug
		return mData!=null?mData.size():0;
	}
    /**
     * 固定写法，记住
     * */
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0==arg1;
	}
    /**
     * 添加的方法，返回值为为索引为position的集合的View
     * */
	@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			//mData中存放的是View,根据索引取出来放到ViewGroup中
		   container.addView(mData.get(position));
			return mData.get(position);
		}
	/**
	 * 移除的方法
	 * */
	@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			container.removeView(mData.get(position));
		}
}
