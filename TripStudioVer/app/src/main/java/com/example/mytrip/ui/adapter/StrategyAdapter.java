package com.example.mytrip.ui.adapter;

import java.util.List;

import com.byzx.httpimageviewdemo.imgdownload.ImageCache;
import com.example.mytrip.R;
import com.example.mytrip.ui.bean.StrategyBean;
import com.example.mytrip.ui.bean.UserInfoBean;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
/**
 *
 * */
public class StrategyAdapter extends BaseAdapter {
	private Context context;
	private List<StrategyBean> datas;
	private LruCache<String, Bitmap> lruCache;
	private final static String IMAGEHOME = "http://img.117go.com/timg/p308/";
	private final static String AVATAR = "http://img.117go.com/demo27/img/ava66/";

	public StrategyAdapter(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		lruCache = ImageCache.GetLruCache(context);
	}

	public void setData(List<StrategyBean> datas) {

		this.datas = datas;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		
		return datas!=null?datas.size():0;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return datas.get(arg0);

	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View view, ViewGroup arg2) {
		ViewHolder viewHolder = null;

		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(context).inflate(R.layout.item_strategy,
					null);
			viewHolder.disCitys = (TextView) view
					.findViewById(R.id.strategy_txt_address);
			viewHolder.favoriteCount = (TextView) view
					.findViewById(R.id.strategy_favoriteCount);
			viewHolder.image = (ImageView) view
					.findViewById(R.id.strategy_imageView);
			viewHolder.pictureCount = (TextView) view
					.findViewById(R.id.strategy_txt_picCount);
			viewHolder.title = (TextView) view
					.findViewById(R.id.strategy_txt_title);
			viewHolder.viewCount = (TextView) view
					.findViewById(R.id.strategy_txt_eye);
			viewHolder.userCircleImageView = (ImageView) view
					.findViewById(R.id.strategy_user_circleImageView);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		StrategyBean StrategyBean = datas.get(arg0);
		viewHolder.favoriteCount.setText(StrategyBean.getFavoriteCount());
		viewHolder.pictureCount.setText(StrategyBean.getPictureCount());
		viewHolder.title.setText(StrategyBean.getTitle());
		viewHolder.viewCount.setText(StrategyBean.getViewCount());
	    try {
			viewHolder.disCitys.setText(StrategyBean.getDispCities()[0]); // stringBuffer.toString()		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			viewHolder.disCitys.setText("δ֪");
		}
		viewHolder.image.setTag(IMAGEHOME + StrategyBean.getImage());
		//��ʾһ��Ĭ��ͼƬ
		viewHolder.image.setImageResource(R.drawable.defaultcovers);
		new ImageCache(context, lruCache, viewHolder.image, IMAGEHOME
				+ StrategyBean.getImage(), "OnTheWay", 800, 400);
		UserInfoBean userInfo = StrategyBean.getUserInfo();
		viewHolder.userCircleImageView.setTag(AVATAR + userInfo.getAvatar());
		new ImageCache(context, lruCache, viewHolder.userCircleImageView,
				AVATAR + userInfo.getAvatar(), "OnTheWay", 800, 400);
		return view;
	}

	private class ViewHolder {

		private ImageView userCircleImageView;
		public TextView pictureCount;
		public TextView favoriteCount;
		public TextView disCitys;
		public TextView viewCount;
		public ImageView image;
		public TextView title;

	}
}
