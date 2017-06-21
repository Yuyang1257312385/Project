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
	//��������
	private TextView mRoomNumTv;//�����
	
	private TextView mRoomPriceTv;//����۸�
	
	private EditText mClientNameEt;//�ͻ�����
	private String mClientName;
	private EditText mClientIdEt;//�ͻ����֤
	private String mClientId;
	private RadioButton mMaleRb,mFemaleRb;//�Ա��� Ů��
	private String mClientSex="��";
	private RadioGroup mClientSexRg;//�Ա�
	private EditText mCheckInTimeEt;//��סʱ��
	private String mCheckInTime;
	private Button mUpdateTimeBtn;//ˢ��ʱ�䰴ť
	private Button mConfirmBtn;//ȷ���ύ��ť
	private String mRoomNum=null;//����ͨ��intent�������ķ����
	private String mRoomPrice=null;//����ͨ��intent�������ķ���۸�
	
   @Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.activity_check_in);
	//��ʼ���ؼ�
	initView();
	//��ʼ������
	initData();
	//ע��
}
/**
 * ��ʼ���ؼ�
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
	//ע�������
	mClientSexRg.setOnCheckedChangeListener(this);
	mCheckInTimeEt=(EditText) findViewById(R.id.et_check_in_time);
	//ʱ�䲻�ɱ༭
	mCheckInTimeEt.setEnabled(false);
	mUpdateTimeBtn=(Button) findViewById(R.id.btn_fresh_time);
	//ע�������
	mUpdateTimeBtn.setOnClickListener(this);
	mConfirmBtn=(Button) findViewById(R.id.btn_custom_info);
	mConfirmBtn.setOnClickListener(this);
}
/**
 * ��ʼ������
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
		mClientSex="Ů";
		break;
	case R.id.rb_male:
		mClientSex="��";
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
		//���Ǽ���Ϣ���浽���ݿ�
		long count=CheckInInfoDb.add(this, mRoomNum, mRoomPrice, mClientName,mClientId, mClientSex, mCheckInTime,1);
		if(count>0){
			RoomNumDb.updateState(this, 1, mRoomNum);
			Toast.makeText(this, "�Ǽǳɹ�", Toast.LENGTH_LONG).show();
			finish();
		}else{
			Toast.makeText(this, "�Ǽ�ʧ��", Toast.LENGTH_LONG).show();
		}
		}
		
		break;
	default:
		break;
	}
}
/**
 * �ǿ���֤
 * */
private boolean notEmptyCheck() {
	// TODO Auto-generated method stub
	boolean b=false;
	mClientName=mClientNameEt.getText().toString().trim();
	mClientId=mClientIdEt.getText().toString().trim();
	if(mClientName==null || mClientName.equals("")){
		mClientNameEt.requestFocus();
		mClientNameEt.setError("�����뷿������");
		return false;
	}else if(mClientId==null || mClientId.equals("")){
		mClientIdEt.requestFocus();
		mClientIdEt.setError("�����뷿�����֤");
		return false;
	}else if(mCheckInTime==null || mCheckInTime.equals("")){
		mCheckInTimeEt.requestFocus();
		mCheckInTimeEt.setError("�����ʱ��");
		return false;
	}else{
		return true;
	}
}
}
