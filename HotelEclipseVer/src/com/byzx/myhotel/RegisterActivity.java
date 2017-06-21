package com.byzx.myhotel;

import com.byzx.myhotel.db.RegisterDb;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity implements OnClickListener{
	//��������
	private EditText mUserNameEt;//�û���
	private EditText mPassWordEt;//����
	private EditText mRePassWordEt;//�ٴ���������
	private Button mRegisterBtn;//ע�ᰴť
   @Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.activity_register);
	//��ʼ���ؼ�
	initView();
	//��MainFest��ע��
}
   
   /**
    * ��ʼ���ؼ�
    * */
private void initView() {
	// TODO Auto-generated method stub
	mUserNameEt=(EditText) findViewById(R.id.et_username);
	mPassWordEt=(EditText) findViewById(R.id.et_password);
	mRePassWordEt=(EditText) findViewById(R.id.et_repassword);
	mRegisterBtn=(Button) findViewById(R.id.btn_register);
	mRegisterBtn.setOnClickListener(this);
}
/**
 * ���ע�ᴦ��
 * */
@Override
public void onClick(View v) {
	// TODO Auto-generated method stub
	switch (v.getId()) {
	case R.id.btn_register:
		//���ע��
		checkRegister();
		break;

	default:
		break;
	}
}
/**
 * ���ע��
 * */
private void checkRegister() {
	// TODO Auto-generated method stub
	String username=mUserNameEt.getText().toString().trim();
	String password=mPassWordEt.getText().toString().trim();
	String repassword=mRePassWordEt.getText().toString().trim();
	if(username==null||username.equals("")){
		Toast.makeText(this, "�û�������Ϊ��", Toast.LENGTH_LONG).show();
		return;
	}else if(password==null||password.equals("")){
		Toast.makeText(this, "���벻��Ϊ��", Toast.LENGTH_LONG).show();
		return;
	}else if(password.length()<6){
		Toast.makeText(this, "���벻��С��6λ", Toast.LENGTH_LONG).show();
		return;
	}
	else if(!repassword.equals(password)){
		Toast.makeText(this, "�������벻һ��", Toast.LENGTH_LONG).show();
	    return;
	}
	
	boolean b=RegisterDb.register(RegisterActivity.this, username, repassword);
	if(b){
		Toast.makeText(RegisterActivity.this, "ע��ɹ���", Toast.LENGTH_LONG).show();
		finish();
	}else{
		Toast.makeText(RegisterActivity.this, "���û����Ѵ���", Toast.LENGTH_LONG).show();
	}
	
}
}
