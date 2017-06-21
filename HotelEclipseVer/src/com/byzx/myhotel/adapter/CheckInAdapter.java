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
    //��������
	private Activity mContext;
	private List<CheckInInfoBean> mdata;//���ݼ�
	private LayoutInflater inflater;//ɸѡ��
	private CheckInInfoInterface checkInInfoInterface;//�ӿڣ��ʵ����Activity����������������ˢ���������ķ�����
	//��ʼ������
	/*public CheckInAdapter(Context context,List<CheckInInfoBean> data){
		mdata=data;
		inflater=LayoutInflater.from(context);
	}*/
	//����ÿ�β�ѯ��ͬ�����ݶ���Ҫ�������������ʿ�������д
	public CheckInAdapter(Activity context){
		mContext=context;
		inflater=LayoutInflater.from(context);
		checkInInfoInterface=(CheckInInfoInterface) context;//���contextΪCheckInActivity
		
	}
	//����data
	public void setMdata(List<CheckInInfoBean> mdata){
		this.mdata=mdata;
	}
	/**
	 * ���ݼ��Ĵ�С
	 * */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mdata!=null?mdata.size():0;
	}
   /**
    * ��ÿһ��item��õ�����
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
		 
		//����dialogҲ��һ��onclicklistener,�����Ҫ���ϰ�����������
		 holder.btn_check_out.setOnClickListener(new android.view.View.OnClickListener() {
		 	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//��ʾһ����������������
				showDialog(mdata.get(position).getmRoomNum());
			}

			
		});
		 return contentView;
		 
	}
	/**
	 * �����������ſؼ�
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
	 * ������˷�ʱ������һ���������ڴ����Ƿ��˷����߼�����
	 * */
	  public void showDialog(final String roomNum){
		  //����һ������
		AlertDialog.Builder builder=new Builder(mContext, R.style.Theme_FloatActivity);
		builder.setTitle("��ܰ��ʾ��");
		builder.setMessage("��ȷ���˵�"+roomNum+"�ŷ���");
		builder.setPositiveButton("��", new OnClickListener(){

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				//������ס��Ϣ���е�״̬
				long count=CheckInInfoDb.update(mContext, roomNum, 0);
				if(count>0){
					Toast.makeText(mContext, "�˷��ɹ�", Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(mContext, "�˷�ʧ��", Toast.LENGTH_LONG).show();
				}
				//���·���ű����ס״̬
				RoomNumDb.updateState(mContext, 0, roomNum);
				//ˢ��������
				checkInInfoInterface.onUpdateUIListener();
			}});
		builder.setNegativeButton("��",new OnClickListener(){

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}});
		//��ʾ������
		builder.show();
	  }
}
