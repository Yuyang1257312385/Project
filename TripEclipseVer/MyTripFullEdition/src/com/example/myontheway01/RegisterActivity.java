package com.example.myontheway01;


import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.VerifySMSCodeListener;




import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity implements OnClickListener{
	private EditText mNumberEt;//电话号码输入框
	private EditText mCodeEt;//验证码输入框
	private Button mGetCode,mNextStepBtn;//获取验证码按钮，下一步按钮
	private String number,code;//电话号码，验证码
	private Intent intent;//跳转至注册界面的意图
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
			BmobSMS.verifySmsCode(RegisterActivity.this,number,code, new VerifySMSCodeListener() {
				
				@Override
				public void done(BmobException ex) {
					// TODO Auto-generated method stub
					if(ex==null){//短信验证码已验证成功
						toast("验证通过");
						intent=new Intent(RegisterActivity.this,RegisterDataActivity.class);
			           	intent.putExtra("number", number);
			           	startActivity(intent);
			           	finish();
					}else{
						toast("验证失败：code ="+ex.getErrorCode()+",msg = "+ex.getLocalizedMessage());
					}
				}
			});
		}else{
			toast("请输入手机号和验证码");
		}
	}
    //toast方法，弹出消息
	public void toast(String str){
		Toast.makeText(RegisterActivity.this, str, Toast.LENGTH_LONG).show();
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
}
