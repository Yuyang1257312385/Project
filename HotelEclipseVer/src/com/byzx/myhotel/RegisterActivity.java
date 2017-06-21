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
	//声明变量
	private EditText mUserNameEt;//用户名
	private EditText mPassWordEt;//密码
	private EditText mRePassWordEt;//再次输入密码
	private Button mRegisterBtn;//注册按钮
   @Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.activity_register);
	//初始化控件
	initView();
	//在MainFest中注册
}
   
   /**
    * 初始化控件
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
 * 点击注册处理
 * */
@Override
public void onClick(View v) {
	// TODO Auto-generated method stub
	switch (v.getId()) {
	case R.id.btn_register:
		//检查注册
		checkRegister();
		break;

	default:
		break;
	}
}
/**
 * 检查注册
 * */
private void checkRegister() {
	// TODO Auto-generated method stub
	String username=mUserNameEt.getText().toString().trim();
	String password=mPassWordEt.getText().toString().trim();
	String repassword=mRePassWordEt.getText().toString().trim();
	if(username==null||username.equals("")){
		Toast.makeText(this, "用户名不能为空", Toast.LENGTH_LONG).show();
		return;
	}else if(password==null||password.equals("")){
		Toast.makeText(this, "密码不能为空", Toast.LENGTH_LONG).show();
		return;
	}else if(password.length()<6){
		Toast.makeText(this, "密码不能小于6位", Toast.LENGTH_LONG).show();
		return;
	}
	else if(!repassword.equals(password)){
		Toast.makeText(this, "两次密码不一致", Toast.LENGTH_LONG).show();
	    return;
	}
	
	boolean b=RegisterDb.register(RegisterActivity.this, username, repassword);
	if(b){
		Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_LONG).show();
		finish();
	}else{
		Toast.makeText(RegisterActivity.this, "该用户名已存在", Toast.LENGTH_LONG).show();
	}
	
}
}
