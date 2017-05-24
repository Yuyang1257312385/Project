package com.example.mytrip.ui;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.mytrip.R;
import com.example.mytrip.base.Constant;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.BmobUser.BmobThirdUserAuth;
import cn.bmob.v3.listener.OtherLoginListener;
import cn.bmob.v3.listener.SaveListener;



import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener {
   private TextView mForgerPwdTv,mOtherWayLoginTv;//����,��������
//   private TextView mTitleTv;
   private LinearLayout mLoginLl,mOtherWayLoginLl;//�˺������
   private ImageView mLoginLogoIv;
   private CheckBox mRemPwdCb;//��ס����
   private Button mLoginBtn,mRegisterBtn;//��¼��ť,ע�ᰴť
   private EditText mUsernameEt,mPasswordEt;//�û��������������
   private String mUsername,mPassword;//�û��� ����
   private ImageView mThirdQqIv,mThirdWeiboIv,mThirdWeixinIv;//����qq ΢�� ΢�ŵĵ�������¼�ؼ�
   Handler handler=new Handler(){
	   public void handleMessage(Message msg){
		   switch(msg.what){
			case 1:
				Animation animation2 = AnimationUtils.
    			loadAnimation(LoginActivity.this, R.anim.pre_welcome_tween);
    	//�������䶯��
    	  mLoginLl.startAnimation(animation2);				
		  mLoginBtn.startAnimation(animation2);
//		  mTitleTv.startAnimation(animation2);
		   mForgerPwdTv.startAnimation(animation2);
		   mOtherWayLoginTv.startAnimation(animation2);	   
		   mOtherWayLoginLl.startAnimation(animation2);		   
		   mRemPwdCb.startAnimation(animation2);		  
		   mRegisterBtn.startAnimation(animation2);		
		   mLoginLl.setVisibility(View.VISIBLE);				
			  mLoginBtn.setVisibility(View.VISIBLE);
//			  mTitleTv.setVisibility(View.VISIBLE);
			   mForgerPwdTv.setVisibility(View.VISIBLE);
			   mOtherWayLoginTv.setVisibility(View.VISIBLE);   
			   mOtherWayLoginLl.setVisibility(View.VISIBLE);		   
			   mRemPwdCb.setVisibility(View.VISIBLE);		  
			   mRegisterBtn.setVisibility(View.VISIBLE);				
				break;
			default:
				break;
			}
	   }
   };
	@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.activity_login);
	//ע��
	intiView();
	initTweenAnim();
	//����Ƿ��ס����
	checkRememberPwd();
}
	/**
	 * ��ʼ���ؼ�
	 * */
	private void intiView() {
		// TODO Auto-generated method stub
		mLoginLogoIv=(ImageView) findViewById(R.id.iv_login_logo);
		
//		   mTitleTv=(TextView) findViewById(R.id.tv_title);
		   mForgerPwdTv=(TextView) findViewById(R.id.tv_forget_pwd);
		   //Ϊ��������ע�������
		   mForgerPwdTv.setOnClickListener(this);
		   mOtherWayLoginTv=(TextView) findViewById(R.id.tv_otherway_login);
		   mLoginLl=(LinearLayout) findViewById(R.id.ll_login);
		   mOtherWayLoginLl=(LinearLayout) findViewById(R.id.ll_otherway_login);		   
		   mRemPwdCb=(CheckBox) findViewById(R.id.cb_remberpwd);
		   mLoginBtn=(Button) findViewById(R.id.btn_login);
		   mLoginBtn.setOnClickListener(this);
		   mRegisterBtn=(Button) findViewById(R.id.btn_register);
		   mRegisterBtn.setOnClickListener(this);
		   mUsernameEt=(EditText) findViewById(R.id.et_username);
		   mPasswordEt=(EditText) findViewById(R.id.et_password);
		   //��������¼�Ŀؼ���ע�������
		   mThirdQqIv=(ImageView) findViewById(R.id.iv_qq);
		   mThirdWeiboIv=(ImageView) findViewById(R.id.iv_weibo);
		   mThirdWeixinIv=(ImageView) findViewById(R.id.iv_weixin);
		   mThirdQqIv.setOnClickListener(this);
		   mThirdWeiboIv.setOnClickListener(this);
		   mThirdWeixinIv.setOnClickListener(this);
	}
	/**
     * ���䶯��
     */
    public void initTweenAnim(){
    	Animation animation = AnimationUtils.
    			loadAnimation(LoginActivity.this, R.anim.login_logo_tween);
    	//�������䶯��
    	mLoginLogoIv.startAnimation(animation);	
    	new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Message msg=Message.obtain();
				msg.what=1;
				msg.arg1=1;
				handler.sendMessage(msg);
				
			}
    		
    	}).start();
    }
	/**
	 * ��½�û�
	 */
	private void testLogin() {
		mUsername=mUsernameEt.getText().toString().trim();
		mPassword=mPasswordEt.getText().toString().trim();
		final BmobUser bu2 = new BmobUser();
		bu2.setUsername(mUsername);
		bu2.setPassword(mPassword);
		bu2.login(this, new SaveListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				toast(bu2.getUsername() + "��½�ɹ�");
				
				//�Ƿ��ס����
				SharedPreferences preferences2=getSharedPreferences("RememberPwd", 0);
				boolean isRemember=mRemPwdCb.isChecked();
				Editor editor=preferences2.edit();
				if(isRemember){
					editor.putString("username", mUsername);
					editor.putString("password", mPassword);
					
				}
				editor.putBoolean("rememberpwd", isRemember);
				editor.commit();
				
				Intent intent=new Intent(LoginActivity.this,MainActivity.class);
				startActivity(intent);
				finish();
			}

			@Override
			public void onFailure(int code, String msg) {
				// TODO Auto-generated method stub
				toast("��½ʧ��:" + msg);
			}
		});
	}

    	
  //toast������������Ϣ
	public void toast(String str){
		Toast.makeText(LoginActivity.this, str, Toast.LENGTH_LONG).show();
	}
    //
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		//�����¼��ť�Ĳ���
		case R.id.btn_login:
			testLogin();
			
			break;
			//���ע�ᰴť�Ĳ���
		case R.id.btn_register:
			Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
			startActivity(intent);
			break;
			//�����������Ĳ���
		case R.id.tv_forget_pwd:
			Intent intent1=new Intent(LoginActivity.this,FindPasswordActivity.class);
			startActivity(intent1);
			break;
		case R.id.iv_qq:
			qqAuthorize();
			break;
		default:
			break;
		}
		
	}
	/**
	 * ����Ƿ��ס����,����ס�������û����������д����sharePreference��RememberPwd��ֵ��������ס��������
	 * */	private void checkRememberPwd() {
		// TODO Auto-generated method stub
		SharedPreferences preferences3=getSharedPreferences("RememberPwd", 0);
		boolean rememberpwd=preferences3.getBoolean("rememberpwd",false);
		if(rememberpwd){
			String username=preferences3.getString("username", "");
			String password=preferences3.getString("password", "");
			mUsernameEt.setText(username);
			mPasswordEt.setText(password);
			mRemPwdCb.setChecked(true);
		}
	} 
	 /**
	  * qq��Ȩ
	  * */
		public static Tencent mTencent;
		
		private void qqAuthorize(){
			if(mTencent==null){
				mTencent = Tencent.createInstance(Constant.QQ_APP_ID, getApplicationContext());
			}
			mTencent.logout(this);
			mTencent.login(this, "all", new IUiListener() {
				
				@Override
				public void onComplete(Object arg0) {
					// TODO Auto-generated method stub
					if(arg0!=null){
						JSONObject jsonObject = (JSONObject) arg0;
						try {
							String token = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_ACCESS_TOKEN);
							String expires = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_EXPIRES_IN);
							String openId = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_OPEN_ID);
							BmobThirdUserAuth authInfo = new BmobThirdUserAuth(BmobThirdUserAuth.SNS_TYPE_QQ,token, expires,openId);
							loginWithAuth(authInfo);
						} catch (JSONException e) {
						}
					}
				}
				
				@Override
				public void onError(UiError arg0) {
					// TODO Auto-generated method stub
					toast("QQ��Ȩ����"+arg0.errorCode+"--"+arg0.errorDetail);
				}
				
				@Override
				public void onCancel() {
					// TODO Auto-generated method stub
					toast("ȡ��qq��Ȩ");
				}
			});
		}
		/**  
		 * ��Ȩ�ɹ���¼
		 * @method loginWithAuth  
		 * @param context
		 * @param authInfo   
		 * @return void  
		 * @exception   
		 */
		public void loginWithAuth(final BmobThirdUserAuth authInfo){
	    	BmobUser.loginWithAuthData(LoginActivity.this, authInfo, new OtherLoginListener() {
				
				@Override
				public void onSuccess(JSONObject userAuth) {
					// TODO Auto-generated method stub
					Log.i("smile",authInfo.getSnsType()+"��½�ɹ�����:"+userAuth);
					Intent intent = new Intent(LoginActivity.this, MainActivity.class);
					intent.putExtra("json", userAuth.toString());
					intent.putExtra("from", authInfo.getSnsType());
					startActivity(intent);
				}
				
				@Override
				public void onFailure(int code, String msg) {
					// TODO Auto-generated method stub
					toast("��������½ʧ�ܣ�"+msg);
				}
				
			});
		}
}
