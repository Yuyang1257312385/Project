package com.example.mytrip.ui;


import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.VerifySMSCodeListener;




import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mytrip.R;

public class RegisterActivity extends Activity implements OnClickListener{
	private EditText mNumberEt;//�绰���������
	private EditText mCodeEt;//��֤�������
	private Button mGetCode,mNextStepBtn;//��ȡ��֤�밴ť����һ����ť
	private String number,code;//�绰���룬��֤��
	private Intent intent;//��ת��ע��������ͼ
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		//��ʼ��
		mNumberEt=(EditText) findViewById(R.id.et_number);
		mCodeEt=(EditText) findViewById(R.id.et_code);
		mGetCode=(Button) findViewById(R.id.btn_get_code);
		mNextStepBtn=(Button) findViewById(R.id.btn_next);
		mGetCode.setOnClickListener(this);
		mNextStepBtn.setOnClickListener(this);
	}
	/**
	 * ���ݵ绰���뷢����֤��
	 * */
	private void sendMsgCode(String phoneNumber){
		//1������������֤��ӿ�
		BmobSMS.requestSMSCode(this, phoneNumber, "ģ������",new RequestSMSCodeListener() {
			@Override
			public void done(Integer smsId,BmobException ex) {
				// TODO Auto-generated method stub
				if(ex!=null){//��֤�뷢�ͳɹ�
					toast("����ʧ�ܣ�������");			
				}
			}
		});
	}

	/** ��֤������֤�� 
	 * @method requestSmsCode    
	 * @return void  
	 * @exception   
	 */
	private void verifySmsCode(){
		number = mNumberEt.getText().toString();
		String code = mCodeEt.getText().toString();
		if(!TextUtils.isEmpty(number)&&!TextUtils.isEmpty(code)){
			BmobSMS.verifySmsCode(RegisterActivity.this,number,code, new VerifySMSCodeListener() {
				
				@Override
				public void done(BmobException ex) {
					// TODO Auto-generated method stub
					if(ex==null){//������֤������֤�ɹ�
						toast("��֤ͨ��");
						intent=new Intent(RegisterActivity.this,RegisterDataActivity.class);
			           	intent.putExtra("number", number);
			           	startActivity(intent);
			           	finish();
					}else{
						toast("��֤ʧ�ܣ�code ="+ex.getErrorCode()+",msg = "+ex.getLocalizedMessage());
					}
				}
			});
		}else{
			toast("�������ֻ��ź���֤��");
		}
	}
    //toast������������Ϣ
	public void toast(String str){
		Toast.makeText(RegisterActivity.this, str, Toast.LENGTH_LONG).show();
	}
	
    //���ݵ����ͬ�İ�ť���ò�ͬ�ķ���
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_get_code:
			number = mNumberEt.getText().toString().trim();
			sendMsgCode(number);
			break;
		case R.id.btn_next:
			verifySmsCode();
			break;
		default:
			break;
		}
		
	}
}
