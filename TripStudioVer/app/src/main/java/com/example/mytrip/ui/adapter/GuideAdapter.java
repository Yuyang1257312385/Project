package com.example.mytrip.ui.adapter;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class GuideAdapter extends PagerAdapter{
   private List<View> mData;

	public void setmData(List<View> mData) {
	this.mData = mData;
}

	public GuideAdapter(Context context){
		
	}
   /**
    * */
	@Override
	public int getCount() {
		return mData!=null?mData.size():0;
	}


	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0==arg1;
	}

	@Override
		public Object instantiateItem(ViewGroup container, int position) {
		   container.addView(mData.get(position));
			return mData.get(position);
		}

	@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(mData.get(position));
		}
}
