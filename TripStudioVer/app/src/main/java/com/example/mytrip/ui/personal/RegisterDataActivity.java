package com.example.mytrip.ui.personal;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.example.mytrip.R;
import com.example.mytrip.tools.ToastUtils;
import com.example.mytrip.ui.login.LoginActivity;

public class RegisterDataActivity extends Activity implements OnClickListener {
	private Button mFinishBtn;//完成按钮
	private EditText mUserNameEt,mPassword,mRePassword;//用户名，密码，再次输入密码输入框
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_info);
		intiView();

	}
	/**
	 * 初始化控件
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
	 * 注册用户
	 */
	private void testSignUp(String name,String pwd) {
		String number=getIntent().getStringExtra("number");
		final BmobUser BmobUser = new BmobUser();
		BmobUser.setUsername(name);
		BmobUser.setPassword(pwd);
		BmobUser.setMobilePhoneNumber(number);
		BmobUser.signUp(new SaveListener<Object>() {
			@Override
			public void done(Object o, BmobException e) {
				if(e == null){
					ToastUtils.showShortToast("注册成功:" + BmobUser.getUsername() + "-"
							+ BmobUser.getObjectId() + "-" + BmobUser.getCreatedAt()
							+ "-" + BmobUser.getSessionToken()+",是否验证："+BmobUser.getEmailVerified());
					Intent intent=new Intent(RegisterDataActivity.this,LoginActivity.class);
					startActivity(intent);
					finish();
				}else {
					ToastUtils.showShortToast("注册失败:" + e.getErrorCode() + e.toString());
				}
			}
		});
	}

	//跳转到登录界面
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		String username=mUserNameEt.getText().toString().trim();
		String password=mPassword.getText().toString().trim();
		testSignUp(username,password);

	}

}
