package com.byzx.myhotel;



import java.util.Timer;
import java.util.TimerTask;

import com.byzx.myhotel.db.RegisterDb;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 登录界面
 * @author LYJ
 * */
public class LoginActivity extends Activity implements OnClickListener{
	//声明变量
	private EditText mUserNameEt;//用户名框
	private EditText mPassWordEt;//密码框
	private CheckBox mRememberCb;//记住密码
	private TextView mRegisterTv;//注册
	private Button mLoginBtn;//登录按钮
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//去掉标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//将activity与相应的布局进行关联
		setContentView(R.layout.activity_login);
		//为 注册 添加下划线
		//两种方式 I下面  II将注册提取到values的string中，在里面加下划线标签
		//TextView tv=(TextView) findViewById(R.id.tv_register);
		//tv.setText(Html.fromHtml("<u>"+"注册"+"</u>"));
		//初始化控件
		initView();
		//检查是否记住密码
		checkRememberPwd();
		//完成之后在AndroidManifest.xml中注册
	}

	private void initView() {
		// TODO Auto-generated method stub
		mUserNameEt=(EditText) findViewById(R.id.et_username);
		mPassWordEt=(EditText) findViewById(R.id.et_password);
		mRememberCb=(CheckBox) findViewById(R.id.cb_remberpwd);
		mRegisterTv=(TextView) findViewById(R.id.tv_register);
		mLoginBtn=(Button) findViewById(R.id.btn_login);

		//绑定点击事件
		//第一种方式
//	 mLoginBtn.setOnClickListener(new OnClickListener() {
//
//		@Override
//		public void onClick(View arg0) {
//			// TODO Auto-generated method stub
//
//		}
//	});
		//第二种方式
		//为登录和注册 注册监听器
		mLoginBtn.setOnClickListener(this);
		mRegisterTv.setOnClickListener(this);
	}

	/**
	 * 点击处理
	 * */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

			case R.id.btn_login://登录按钮事件
				//获取用户名和密码框中的内容
				String username=mUserNameEt.getText().toString().trim();
				String password=mPassWordEt.getText().toString().trim();
				if(username==null||username.equals("")){
					//弹出信息提示栏，一段时间后关闭
					Toast.makeText(this, "您的登录名不能为空", Toast.LENGTH_LONG).show();
					return;
				}else if(password==null||password.equals("")){
					Toast.makeText(this, "您的密码不能为空", Toast.LENGTH_LONG).show();
					return;
				}

				boolean b=RegisterDb.login(LoginActivity.this, username, password);
				if(b){
					//是否记住密码
					SharedPreferences preferences2=getSharedPreferences("RememberPwd", 0);
					boolean isRemember=mRememberCb.isChecked();


					Editor editor=preferences2.edit();
					if(isRemember){
						editor.putString("username", username);
						editor.putString("password", password);

					}
					editor.putBoolean("rememberpwd", isRemember);
					editor.commit();

					/**
					 * 跳转到主界面
					 * */
					Intent intent = new Intent();
					intent.setClass(LoginActivity.this,MainActivity.class);
					startActivity(intent);

					Toast.makeText(this, "登录成功", Toast.LENGTH_LONG).show();
				}else {
					Toast.makeText(this, "用户名或密码不正确", Toast.LENGTH_LONG).show();
				}
				break;
			case R.id.tv_register:
				//跳转到注册界面
				initIntentActivity();
				break;
			default:
				break;
		}
	}
	/**
	 * 从登录界面跳转到注册界面
	 * */
	public void initIntentActivity(){
		//Android中计时器的写法
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				//activity 的跳转实现
				Intent intent = new Intent();
				intent.setClass(LoginActivity.this,RegisterActivity.class);
				startActivity(intent);

			}
		};
		timer.schedule(task,50);
	}
	/**
	 * 检查是否记住密码
	 * */
	private void checkRememberPwd() {
		// TODO Auto-generated method stub
		SharedPreferences preferences3=getSharedPreferences("RememberPwd", 0);
		boolean rememberpwd=preferences3.getBoolean("rememberpwd",false);
		if(rememberpwd){
			String username=preferences3.getString("username", "");
			String password=preferences3.getString("password", "");
			mUserNameEt.setText(username);
			mPassWordEt.setText(password);
			mRememberCb.setChecked(true);
		}
	}
}
