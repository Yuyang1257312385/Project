package com.example.mytrip.ui;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mytrip.R;

/**
 * ������֤�룬�����ǿ���֤������Ϊ�գ���ת�������������,������֤�봫��ȥ
 * */
public class FindPasswordActivity extends Activity implements OnClickListener{
	private EditText mNumberEt;//�绰���������
	private EditText mCodeEt;//��֤�������
	private Button mGetCode,mNextStepBtn;//��ȡ��֤�밴ť����һ����ť
	private String number,code;//�绰���룬��֤��
	private Intent intent;//��ת��ע��������ͼ
	private TextView mTitleTv;//���ñ���
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
		mTitleTv=(TextView) findViewById(R.id.tv_title_register);
		mTitleTv.setText("��������");
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
				}else{
					toast("���ͳɹ�");
				}
			}
		});
	}

	/** ��֤������֤��ǿ� ,����ת
	 * @method requestSmsCode    
	 * @return void  
	 * @exception   
	 */
	private void checkNullSmsCode(){
		number = mNumberEt.getText().toString();
		String code = mCodeEt.getText().toString();
		if(code!=null&&!code.equals("")&&number!=null&&!number.equals("")){
						intent=new Intent(FindPasswordActivity.this,FindResetPasswordActivity.class);
			           	intent.putExtra("code", code);
			           	startActivity(intent);
			           	finish();
		}else{
			toast("�ֻ��Ż���֤��Ϊ��");
		}
	}
    //toast������������Ϣ
	public void toast(String str){
		Toast.makeText(FindPasswordActivity.this, str, Toast.LENGTH_LONG).show();
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
			checkNullSmsCode();
			break;
		default:
			break;
		}
		
	}
}
