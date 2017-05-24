package com.example.mytrip.ui;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mytrip.R;

public class RegisterDataActivity extends Activity implements OnClickListener {
	private Button mFinishBtn;//��ɰ�ť
    private EditText mUserNameEt,mPassword,mRePassword;//�û��������룬�ٴ��������������
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_info);
         intiView();
		
	}
	/**
	 * ��ʼ���ؼ�
	 * */
	private void intiView() {
		// TODO Auto-generated method stub
		mUserNameEt=(EditText) findViewById(R.id.et_username);
		mPassword=(EditText) findViewById(R.id.et_password);
		mRePassword=(EditText) findViewById(R.id.et_repassword);
		mFinishBtn=(Button) findViewById(R.id.btn_finish);
		mFinishBtn.setOnClickListener(this);
	}
	/**
	 * ע���û�
	 */
	private void testSignUp(String name,String pwd) {
		String number=getIntent().getStringExtra("number");
		final BmobUser BmobUser = new BmobUser();
		BmobUser.setUsername(name);
		BmobUser.setPassword(pwd);
		BmobUser.setMobilePhoneNumber(number);
		BmobUser.signUp(this, new SaveListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				toast("ע��ɹ�:" + BmobUser.getUsername() + "-"
						+ BmobUser.getObjectId() + "-" + BmobUser.getCreatedAt()
						+ "-" + BmobUser.getSessionToken()+",�Ƿ���֤��"+BmobUser.getEmailVerified());
				Intent intent=new Intent(RegisterDataActivity.this,LoginActivity.class);
				startActivity(intent);
				finish();
			}

			@Override
			public void onFailure(int code, String msg) {
				// TODO Auto-generated method stub
				toast("ע��ʧ��:" + msg);
			}
		});
	}
	 //toast������������Ϣ
		public void toast(String str){
			Toast.makeText(RegisterDataActivity.this, str, Toast.LENGTH_LONG).show();
		}
	//��ת����¼����
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		String username=mUserNameEt.getText().toString().trim();
		String password=mPassword.getText().toString().trim();
		testSignUp(username,password);
		
	}
}
