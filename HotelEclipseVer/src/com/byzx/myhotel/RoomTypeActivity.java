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
 //��������
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
	//��ʼ���ؼ�
	ininView();
	//ע�ᣨע��ʱ��theme������themeΪ������style��########################
	
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
			//ͨ��intent���ݷ�������
			intent.putExtra("room_type", "���˼�");
			startActivity(intent);
			break;
		case R.id.btn_double_room:
			intent.putExtra("room_type", "˫�˼�");
			startActivity(intent);
			break;
		case R.id.btn_standard_single:
			intent.putExtra("room_type", "��׼��");
			startActivity(intent);
			break;
		case R.id.btn_business_double:
			intent.putExtra("room_type", "�����");
			startActivity(intent);
			break;
		default:
			break;
		}
	}
}
