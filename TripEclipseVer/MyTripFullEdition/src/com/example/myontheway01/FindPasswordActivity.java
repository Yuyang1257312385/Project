package com.example.myontheway01;

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
import android.widget.TextView;
import android.widget.Toast;
/**
 * 发送验证码，并作非空验证，若不为空，跳转至设置密码界面,并将验证码传过去
 * */
public class FindPasswordActivity extends Activity implements OnClickListener{
	private EditText mNumberEt;//电话号码输入框
	private EditText mCodeEt;//验证码输入框
	private Button mGetCode,mNextStepBtn;//获取验证码按钮，下一步按钮
	private String number,code;//电话号码，验证码
	private Intent intent;//跳转至注册界面的意图
	private TextView mTitleTv;//设置标题
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		//初始化
		mNumberEt=(EditText) findViewById(R.id.et_number);
		mCodeEt=(EditText) findViewById(R.id.et_code);
		mGetCode=(Button) findViewById(R.id.btn_get_code);
		mNextStepBtn=(Button) findViewById(R.id.btn_next);
		mTitleTv=(TextView) findViewById(R.id.tv_title_register);
		mTitleTv.setText("重置密码");
		mGetCode.setOnClickListener(this);
		mNextStepBtn.setOnClickListener(this);
	}
	/**
	 * 根据电话号码发送验证码
	 * */
	private void sendMsgCode(String phoneNumber){
		//1、调用请求验证码接口
		BmobSMS.requestSMSCode(this, phoneNumber, "模板名称",new RequestSMSCodeListener() {
			@Override
			public void done(Integer smsId,BmobException ex) {
				// TODO Auto-generated method stub
				if(ex!=null){//验证码发送成功
					toast("发送失败，请重试");			
				}else{
					toast("发送成功");
				}
			}
		});
	}

	/** 验证短信验证码非空 ,则跳转
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
			toast("手机号或验证码为空");
		}
	}
    //toast方法，弹出消息
	public void toast(String str){
		Toast.makeText(FindPasswordActivity.this, str, Toast.LENGTH_LONG).show();
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
			checkNullSmsCode();
			break;
		default:
			break;
		}
		
	}
}
