package com.example.myontheway01.footprint;

import java.util.List;

import cn.bmob.v3.listener.SaveListener;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.example.myontheway01.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FootPrintAdapter extends BaseAdapter {

	private Context context;
	private List<FootPrint> data;
	private LayoutInflater inflater;


	public FootPrintAdapter(Context context) {
	super();
	this.context = context;
	inflater=LayoutInflater.from(context);
}

	public void setData(List<FootPrint> data) {
		this.data = data;
	}



	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data != null ? data.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		Holder holder = null;
		if (convertView == null) {
			holder = new Holder();
			convertView = inflater.inflate(R.layout.item_time_shaft, null);
			holder.mCreatTime = (TextView) convertView
					.findViewById(R.id.tv_creat_time);
			holder.mContent = (TextView) convertView
					.findViewById(R.id.tv_content_msg);
			holder.mLocation = (TextView) convertView
					.findViewById(R.id.tv_location);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		// 使用控件显示
		holder.mLocation.setText(data.get(position).getmAdds());
		holder.mContent.setText(data.get(position).getmContent());
		holder.mCreatTime.setText(data.get(position).getCreatedAt());
		return convertView;
	}

	class Holder {
		public TextView mCreatTime;// 发表时间
		public TextView mContent;// 显示消息的具体内容
		public TextView mLocation;// 显示消息的具体内容

	}

	
}
