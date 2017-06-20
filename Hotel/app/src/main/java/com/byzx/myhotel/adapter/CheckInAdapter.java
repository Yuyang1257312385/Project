package com.byzx.myhotel.adapter;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.byzx.edu4001.interfac.CheckInInfoInterface;
import com.byzx.myhotel.R;
import com.byzx.myhotel.bean.CheckInInfoBean;
import com.byzx.myhotel.db.CheckInInfoDb;
import com.byzx.myhotel.db.RoomNumDb;




public class CheckInAdapter extends BaseAdapter{
	//声明变量
	private Activity mContext;
	private List<CheckInInfoBean> mdata;//数据集
	private LayoutInflater inflater;//筛选器
	private CheckInInfoInterface checkInInfoInterface;//接口（搭建实例化Activity的桥梁，用来调用刷新适配器的方法）
	//初始化变量
	/*public CheckInAdapter(Context context,List<CheckInInfoBean> data){
		mdata=data;
		inflater=LayoutInflater.from(context);
	}*/
	//由于每次查询不同的数据都需要更新适配器，故可以这样写
	public CheckInAdapter(Activity context){
		mContext=context;
		inflater=LayoutInflater.from(context);
		checkInInfoInterface=(CheckInInfoInterface) context;//这个context为CheckInActivity

	}
	//设置data
	public void setMdata(List<CheckInInfoBean> mdata){
		this.mdata=mdata;
	}
	/**
	 * 数据集的大小
	 * */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mdata!=null?mdata.size():0;
	}
	/**
	 * 从每一个item获得的内容
	 * */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mdata.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View contentView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		Holder holder=null;
		if(contentView==null){
			holder=new Holder();
			contentView=inflater.inflate(R.layout.activity_checked_client_info_item, null);
			holder.tv_client_name=(TextView) contentView.findViewById(R.id.tv_client_name);
			holder.tv_client_sex=(TextView) contentView.findViewById(R.id.tv_client_sex);
			holder.tv_client_id=(TextView) contentView.findViewById(R.id.tv_client_id);
			holder.tv_room_num=(TextView) contentView.findViewById(R.id.tv_room_num1);
			holder.tv_room_price=(TextView) contentView.findViewById(R.id.tv_room_price1);
			holder.tv_check_in_time=(TextView) contentView.findViewById(R.id.tv_check_in_time1);
			holder.btn_check_out=(Button) contentView.findViewById(R.id.btn_check_out);
			contentView.setTag(holder);
		}else{
			holder=(Holder) contentView.getTag();
		}
		holder.tv_client_name.setText(mdata.get(position).getmClientName());
		holder.tv_client_sex.setText(mdata.get(position).getmClientSex());
		holder.tv_client_id.setText(mdata.get(position).getmClientId());
		holder.tv_room_num.setText(mdata.get(position).getmRoomNum());
		holder.tv_room_price.setText(mdata.get(position).getmRoomPrice());

		holder.tv_check_in_time.setText(mdata.get(position).getmCheckInTime());
		if(checkInInfoInterface.getI()==2){
			holder.btn_check_out.setVisibility(View.GONE);
		}else{
			holder.btn_check_out.setVisibility(View.VISIBLE);
		}

		//由于dialog也有一个onclicklistener,故这个要加上包名以作区分
		holder.btn_check_out.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//显示一个弹窗，并做处理
				showDialog(mdata.get(position).getmRoomNum());
			}


		});
		return contentView;

	}
	/**
	 * 这个类用来存放控件
	 * */
	class Holder{
		public TextView tv_client_name;
		public TextView tv_client_sex;
		public TextView tv_client_id;
		public TextView tv_room_num;
		public TextView tv_room_price;
		public TextView tv_check_in_time;
		public Button btn_check_out;
	}
	/**
	 * 当点击退房时，弹出一个弹窗，在此做是否退房的逻辑处理
	 * */
	public void showDialog(final String roomNum){
		//创建一个弹窗
		AlertDialog.Builder builder=new Builder(mContext, R.style.Theme_FloatActivity);
		builder.setTitle("温馨提示：");
		builder.setMessage("您确定退掉"+roomNum+"号房吗？");
		builder.setPositiveButton("是", new OnClickListener(){

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				//更新入住信息表中的状态
				long count=CheckInInfoDb.update(mContext, roomNum, 0);
				if(count>0){
					Toast.makeText(mContext, "退房成功", Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(mContext, "退房失败", Toast.LENGTH_LONG).show();
				}
				//更新房间号表的入住状态
				RoomNumDb.updateState(mContext, 0, roomNum);
				//刷新适配器
				checkInInfoInterface.onUpdateUIListener();
			}});
		builder.setNegativeButton("否",new OnClickListener(){

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub

			}});
		//显示弹出框
		builder.show();
	}
}
