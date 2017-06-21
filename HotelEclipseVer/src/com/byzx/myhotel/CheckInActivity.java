package com.byzx.myhotel;

import com.byzx.myhotel.db.CheckInInfoDb;
import com.byzx.myhotel.db.RoomNumDb;
import com.byzx.myhotel.utils.Tools;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class CheckInActivity extends Activity implements OnClickListener,OnCheckedChangeListener{
	//声明变量
	private TextView mRoomNumTv;//房间号
	
	private TextView mRoomPriceTv;//房间价格
	
	private EditText mClientNameEt;//客户姓名
	private String mClientName;
	private EditText mClientIdEt;//客户身份证
	private String mClientId;
	private RadioButton mMaleRb,mFemaleRb;//性别（男 女）
	private String mClientSex="男";
	private RadioGroup mClientSexRg;//性别
	private EditText mCheckInTimeEt;//入住时间
	private String mCheckInTime;
	private Button mUpdateTimeBtn;//刷新时间按钮
	private Button mConfirmBtn;//确认提交按钮
	private String mRoomNum=null;//接受通过intent传过来的房间号
	private String mRoomPrice=null;//接受通过intent传过来的房间价格
	
   @Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.activity_check_in);
	//初始化控件
	initView();
	//初始化数据
	initData();
	//注册
}
/**
 * 初始化控件
 * */
private void initView() {
	// TODO Auto-generated method stub
	mRoomNumTv=(TextView) findViewById(R.id.tv_room_num);
	mRoomPriceTv=(TextView) findViewById(R.id.tv_room_price);
	mClientNameEt=(EditText) findViewById(R.id.et_name);
	mClientIdEt=(EditText) findViewById(R.id.et_id_num);
	mMaleRb=(RadioButton) findViewById(R.id.rb_male);
	mFemaleRb=(RadioButton) findViewById(R.id.rb_female);
	mClientSexRg=(RadioGroup) findViewById(R.id.rg_sex);
	//注册监听器
	mClientSexRg.setOnCheckedChangeListener(this);
	mCheckInTimeEt=(EditText) findViewById(R.id.et_check_in_time);
	//时间不可编辑
	mCheckInTimeEt.setEnabled(false);
	mUpdateTimeBtn=(Button) findViewById(R.id.btn_fresh_time);
	//注册监听器
	mUpdateTimeBtn.setOnClickListener(this);
	mConfirmBtn=(Button) findViewById(R.id.btn_custom_info);
	mConfirmBtn.setOnClickListener(this);
}
/**
 * 初始化数据
 * */
private void initData() {
	// TODO Auto-generated method stub
	mRoomNum=getIntent().getStringExtra("roomNum").toString().trim();
	mRoomPrice=getIntent().getStringExtra("roomPrice").toString().trim();
	if(mRoomNum!=null && !mRoomNum.equals("") && mRoomPrice!=null && !mRoomPrice.equals("")){
		mRoomNumTv.setText(mRoomNum);
		mRoomPriceTv.setText(mRoomPrice);
	}
}
@Override
public void onCheckedChanged(RadioGroup arg0, int id) {
	// TODO Auto-generated method stub
	switch (id) {
	case R.id.rb_female:
		mClientSex="女";
		break;
	case R.id.rb_male:
		mClientSex="男";
		break;
	default:
		break;
	}
}
@Override
public void onClick(View v) {
	// TODO Auto-generated method stub
	switch (v.getId()) {
	case R.id.btn_fresh_time:
	    mCheckInTime=Tools.getDate();
		mCheckInTimeEt.setText(mCheckInTime);
		break;
	case R.id.btn_custom_info:
		if(notEmptyCheck()){
		//将登记信息保存到数据库
		long count=CheckInInfoDb.add(this, mRoomNum, mRoomPrice, mClientName,mClientId, mClientSex, mCheckInTime,1);
		if(count>0){
			RoomNumDb.updateState(this, 1, mRoomNum);
			Toast.makeText(this, "登记成功", Toast.LENGTH_LONG).show();
			finish();
		}else{
			Toast.makeText(this, "登记失败", Toast.LENGTH_LONG).show();
		}
		}
		
		break;
	default:
		break;
	}
}
/**
 * 非空验证
 * */
private boolean notEmptyCheck() {
	// TODO Auto-generated method stub
	boolean b=false;
	mClientName=mClientNameEt.getText().toString().trim();
	mClientId=mClientIdEt.getText().toString().trim();
	if(mClientName==null || mClientName.equals("")){
		mClientNameEt.requestFocus();
		mClientNameEt.setError("请输入房客姓名");
		return false;
	}else if(mClientId==null || mClientId.equals("")){
		mClientIdEt.requestFocus();
		mClientIdEt.setError("请输入房客身份证");
		return false;
	}else if(mCheckInTime==null || mCheckInTime.equals("")){
		mCheckInTimeEt.requestFocus();
		mCheckInTimeEt.setError("请更新时间");
		return false;
	}else{
		return true;
	}
}
}
