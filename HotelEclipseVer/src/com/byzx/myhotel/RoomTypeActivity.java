package com.byzx.myhotel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.style.SuperscriptSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class RoomTypeActivity extends Activity implements OnClickListener {
 //声明变量
 private Button mSingleRoomBtn;
 private Button mDoubleRoomBtn;
 private Button mStandardSingleBtn;
 private Button mBusinessDoubleBtn;
	@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.activity_roomtype);
	//初始化控件
	ininView();
	//注册（注册时的theme中设置theme为弹窗的style）########################
	
}
	private void ininView() {
		// TODO Auto-generated method stub
		mSingleRoomBtn=(Button) findViewById(R.id.btn_single_room);
		mDoubleRoomBtn=(Button) findViewById(R.id.btn_double_room);
		mStandardSingleBtn=(Button) findViewById(R.id.btn_standard_single);
		mBusinessDoubleBtn=(Button) findViewById(R.id.btn_business_double);
		mSingleRoomBtn.setOnClickListener(this);
		mDoubleRoomBtn.setOnClickListener(this);
		mStandardSingleBtn.setOnClickListener(this);
		mBusinessDoubleBtn.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		Intent intent=new Intent(RoomTypeActivity.this,RoomInfoActivity.class);
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_single_room:
			//通过intent传递房间类型
			intent.putExtra("room_type", "单人间");
			startActivity(intent);
			break;
		case R.id.btn_double_room:
			intent.putExtra("room_type", "双人间");
			startActivity(intent);
			break;
		case R.id.btn_standard_single:
			intent.putExtra("room_type", "标准间");
			startActivity(intent);
			break;
		case R.id.btn_business_double:
			intent.putExtra("room_type", "商务间");
			startActivity(intent);
			break;
		default:
			break;
		}
	}
}
