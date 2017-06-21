package com.example.mytrip.ui.personal;


import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;




import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.example.mytrip.R;
import com.example.mytrip.tools.ToastUtils;

public class RegisterActivity extends Activity implements OnClickListener{
	private static final int COUNT_TIME = 60 * 1000;// 倒计时总时间
	private static final int COUNT_INTERVAL = 1000;// 倒计时时间间隔
	private MyCounter mCounter;//倒计时

	private EditText mNumberEt;//电话号码输入框
	private EditText mCodeEt;//验证码输入框
	private Button mGetCodeBtn,mNextStepBtn;//获取验证码按钮，下一步按钮
	private String number,code;//电话号码，验证码
	private Intent intent;//跳转至注册界面的意图
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		initView();

	}

	private void initView() {
		mNumberEt=(EditText) findViewById(R.id.et_number);
		mCodeEt=(EditText) findViewById(R.id.et_code);
		mGetCodeBtn=(Button) findViewById(R.id.btn_get_code);
		mNextStepBtn=(Button) findViewById(R.id.btn_next);
		mGetCodeBtn.setOnClickListener(this);
		mNextStepBtn.setOnClickListener(this);

	}

	/**
	 * 根据电话号码发送验证码
	 * */
	private void sendMsgCode(String phoneNumber){
		//1、调用请求验证码接口
		BmobSMS.requestSMSCode(phoneNumber, "模板名称", new QueryListener<Integer>() {
			@Override
			public void done(Integer integer, BmobException e) {
				if(e != null){//验证码发送成功
					ToastUtils.showShortToast("发送失败，请重试");
				}else {
					mCounter = new MyCounter(COUNT_TIME,COUNT_INTERVAL);
					mCounter.start();
					mGetCodeBtn.setEnabled(false);
				}
			}
		});
	}

	/** 验证短信验证码
	 * @method requestSmsCode
	 * @return void
	 * @exception
	 */
	private void verifySmsCode(){
		number = mNumberEt.getText().toString();
		String code = mCodeEt.getText().toString();
		if(!TextUtils.isEmpty(number)&&!TextUtils.isEmpty(code)){
			BmobSMS.verifySmsCode(number,code, new UpdateListener() {

				@Override
				public void done(BmobException ex) {
					// TODO Auto-generated method stub
					if(ex==null){//短信验证码已验证成功
						ToastUtils.showShortToast("验证通过");
						intent=new Intent(RegisterActivity.this,RegisterDataActivity.class);
						intent.putExtra("number", number);
						startActivity(intent);
						finish();
					}else{
						ToastUtils.showShortToast("验证失败：code ="+ex.getErrorCode()+",msg = "+ex.getLocalizedMessage());
					}
				}
			});
		}else{
			ToastUtils.showShortToast("请输入手机号和验证码");
		}
	}


	//根据点击不同的按钮调用不同的方法
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


	/**
	 * 倒计时
	 */
	private class MyCounter extends CountDownTimer {

		public MyCounter(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {
			if (mCounter != null) {
				mCounter.cancel();
				mCounter = null;
			}

			mGetCodeBtn.setText("发送验证码");//发送验证码
			mGetCodeBtn.setEnabled(true);
		}

		@Override
		public void onTick(long millisUntilFinished) {
			mGetCodeBtn.setText(millisUntilFinished / COUNT_INTERVAL + " 秒");
		}
	}
}
