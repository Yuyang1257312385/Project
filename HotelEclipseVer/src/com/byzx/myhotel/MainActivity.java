package com.byzx.myhotel;

import com.byzx.myhotel.db.RoomInfoDb;
import com.byzx.myhotel.db.RoomNumDb;

import android.app.Activity;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener{
   //声明控件
	private Button mCheckInBtn;
	private Button mCheckInInfoBtn;
	private Button mCheckOutBtn;
	private Button mCheckOutInfoBtn;
   private TextView mTitleTv;
	@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.activity_main);
	//初始化控件
	mCheckInBtn=(Button) findViewById(R.id.btn_checkin);
	mCheckInInfoBtn=(Button) findViewById(R.id.btn_check_in_info);
	mTitleTv=(TextView) findViewById(R.id.tv_title);
	mCheckOutBtn=(Button) findViewById(R.id.btn_exit);
	mCheckOutInfoBtn=(Button) findViewById(R.id.btn_exitmsg);
	mCheckInBtn.setOnClickListener(this);
	mCheckInInfoBtn.setOnClickListener(this);
	mCheckOutBtn.setOnClickListener(this);
	mCheckOutInfoBtn.setOnClickListener(this);
	//将从res中的string 获取的内容设置为标题内容
	mTitleTv.setText(getResources().getString(R.string.main_title_tv));
	//插入房间信息
	insertRoomInfo();
	//注册
}
	/**
	 * 插入房间信息
	 * */
	private void insertRoomInfo() {
		if (RoomInfoDb.getCount(this)) {
			// TODO Auto-generated method stub
			RoomInfoDb.add(this, "单人间", R.drawable.room_img03,
					R.drawable.room_img04, "80", "各种玩，各种萌，还有wifi", 1);
			RoomInfoDb.add(this, "双人间", R.drawable.room_img02,
					R.drawable.room_img06, "100", "各种玩，各种萌，有wifi，可洗澡", 2);
			RoomInfoDb.add(this, "标准间", R.drawable.room_img03,
					R.drawable.room_img05, "120", "各种玩，各种萌，还有wifi,带卫生间", 3);
			RoomInfoDb.add(this, "商务间", R.drawable.room_img01,
					R.drawable.room_img02, "150", "各种玩，各种萌，还有wifi，电视", 4);
			RoomNumDb.add(this, "101", 1,0);
			RoomNumDb.add(this, "102", 1,0);
			RoomNumDb.add(this, "103", 1,0);
			RoomNumDb.add(this, "104", 1,0);
			RoomNumDb.add(this, "105", 1,0);
			RoomNumDb.add(this, "201", 2,0);
			RoomNumDb.add(this, "202", 2,0);
			RoomNumDb.add(this, "203", 2,0);
			RoomNumDb.add(this, "204", 2,0);
			RoomNumDb.add(this, "301", 3,0);
			RoomNumDb.add(this, "302", 3,0);
			RoomNumDb.add(this, "303", 3,0);
			RoomNumDb.add(this, "401", 4,0);
			RoomNumDb.add(this, "402", 4,0);
			RoomNumDb.add(this, "403", 4,0);
		}
	
	}
	/**
	 * 恢复页面时将标题内容设置为原来内容
	 * */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mTitleTv.setText(getResources().getString(R.string.main_title_tv));
	}
	@Override
		protected void onPause() {
			// TODO Auto-generated method stub
			super.onPause();
			mTitleTv.setText("宾馆类型");
		}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_checkin:
			//跳转到房间类型
			Intent intent=new Intent(MainActivity.this,RoomTypeActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_check_in_info:
			//跳转到入住信息
			Intent intent1 =new Intent(MainActivity.this,CheckInListActivity.class);
			startActivity(intent1);
			break;
		case R.id.btn_exit:
			//跳转到退房界面
			Intent intent2=new Intent(MainActivity.this,CheckInListActivity.class);
			//若在CheckInListActivity中接受到到为1，则表示点击的是退出按钮
			intent2.putExtra("button",1);
			startActivity(intent2);
			break;
		case R.id.btn_exitmsg:
			Intent intent3=new Intent(MainActivity.this,CheckInListActivity.class);
			//若在CheckInListActivity中接受到到为2，则表示点击的是退出按钮
			intent3.putExtra("button", 2);
			startActivity(intent3);
			break;
		default:
			break;
		}
	}
	
}
