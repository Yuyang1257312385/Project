package com.example.mytrip.ui.personal;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mytrip.R;
import com.example.mytrip.tools.ToastUtils;
import com.example.mytrip.ui.login.LoginActivity;

/**
 * 重置密码
 * */
public class FindResetPasswordActivity extends Activity implements OnClickListener{
	private Button mFinishBtn;//完成按钮
	private EditText mUserNameEt,mPasswordEt,mRePasswordEt;//用户名，密码，再次输入密码输入框
	private TextView mTitleTv;
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
		mPasswordEt=(EditText) findViewById(R.id.et_password);
		mRePasswordEt=(EditText) findViewById(R.id.et_repassword);
		mFinishBtn=(Button) findViewById(R.id.btn_finish);
		mTitleTv=(TextView) findViewById(R.id.tv_title_register_data);
		mTitleTv.setText("重置密码");
		mFinishBtn.setOnClickListener(this);
	}

	/** 通过短信验证码来重置用户密码
	 * @method requestSmsCode
	 * @return void
	 * 注：整体流程是先调用请求验证码的接口获取短信验证码，随后调用短信验证码重置密码接口来重置该手机号对应的用户的密码
	 */
	private void resetPasswordBySMS(){
		String code=getIntent().getStringExtra("code");
		String password=mPasswordEt.getText().toString().trim();
		String rePassword=mRePasswordEt.getText().toString().trim();
		if(password!=null&&!password.equals("")){
			if(!password.equals(rePassword)){
				ToastUtils.showShortToast("两次密码输入不一致");
				return;
			}
		}else{
			ToastUtils.showShortToast("密码不能为空");
			return;
		}
		//2、重置的是绑定了该手机号的账户的密码
		BmobUser.resetPasswordBySMSCode(code, password, new UpdateListener() {
			@Override
			public void done(BmobException e) {
				if(e==null){
					ToastUtils.showShortToast("密码重置成功");
					Intent intent=new Intent(FindResetPasswordActivity.this,LoginActivity.class);
					startActivity(intent);
				}else{
					ToastUtils.showShortToast("错误码："+e.getErrorCode()+",错误原因："+e.getLocalizedMessage());

				}
			}
		});
	}



	//跳转到登录界面
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		resetPasswordBySMS();

	}
}
