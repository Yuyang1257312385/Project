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
 * ��¼����
 * @author LYJ
 * */
public class LoginActivity extends Activity implements OnClickListener{
    //��������
	private EditText mUserNameEt;//�û�����
	private EditText mPassWordEt;//�����
	private CheckBox mRememberCb;//��ס����
	private TextView mRegisterTv;//ע��
	private Button mLoginBtn;//��¼��ť
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	//ȥ������
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	//��activity����Ӧ�Ĳ��ֽ��й���
    	setContentView(R.layout.activity_login);
    	//Ϊ ע�� ����»���
    	  //���ַ�ʽ I����  II��ע����ȡ��values��string�У���������»��߱�ǩ
    	//TextView tv=(TextView) findViewById(R.id.tv_register);
    	//tv.setText(Html.fromHtml("<u>"+"ע��"+"</u>"));
    	//��ʼ���ؼ�
    	initView();
    	//����Ƿ��ס����
    	checkRememberPwd();
    	//���֮����AndroidManifest.xml��ע��
    }
	
	private void initView() {
		// TODO Auto-generated method stub
	mUserNameEt=(EditText) findViewById(R.id.et_username);
	mPassWordEt=(EditText) findViewById(R.id.et_password);
	mRememberCb=(CheckBox) findViewById(R.id.cb_remberpwd);
	mRegisterTv=(TextView) findViewById(R.id.tv_register);
	mLoginBtn=(Button) findViewById(R.id.btn_login);
	
	//�󶨵���¼�
	  //��һ�ַ�ʽ
//	 mLoginBtn.setOnClickListener(new OnClickListener() {
//		
//		@Override
//		public void onClick(View arg0) {
//			// TODO Auto-generated method stub
//			
//		}
//	});
	  //�ڶ��ַ�ʽ
	//Ϊ��¼��ע�� ע�������
	mLoginBtn.setOnClickListener(this);
	mRegisterTv.setOnClickListener(this);
	}
	
	/**
	 * �������
	 * */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		
		case R.id.btn_login://��¼��ť�¼�
			//��ȡ�û�����������е�����
			String username=mUserNameEt.getText().toString().trim();
			String password=mPassWordEt.getText().toString().trim();
			if(username==null||username.equals("")){
				//������Ϣ��ʾ����һ��ʱ���ر�
				Toast.makeText(this, "���ĵ�¼������Ϊ��", Toast.LENGTH_LONG).show();
				return;
			}else if(password==null||password.equals("")){
				Toast.makeText(this, "�������벻��Ϊ��", Toast.LENGTH_LONG).show();
				return;
			}
			
			boolean b=RegisterDb.login(LoginActivity.this, username, password);
			if(b){
				//�Ƿ��ס����
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
				 * ��ת��������
				 * */
				Intent intent = new Intent();
				intent.setClass(LoginActivity.this,MainActivity.class);
				startActivity(intent);
				
				Toast.makeText(this, "��¼�ɹ�", Toast.LENGTH_LONG).show();
			}else {
				Toast.makeText(this, "�û��������벻��ȷ", Toast.LENGTH_LONG).show();			
			}
			break;
		case R.id.tv_register:
			//��ת��ע�����
			initIntentActivity();
			break;
		default:
			break;
		}
	}
	 /**
	  * �ӵ�¼������ת��ע�����
	  * */
	 public void initIntentActivity(){
	    	//Android�м�ʱ����д��
	    	Timer timer = new Timer();
	    	TimerTask task = new TimerTask() {
				
				@Override
				public void run() {
					//activity ����תʵ��
					Intent intent = new Intent();
					intent.setClass(LoginActivity.this,RegisterActivity.class);
					startActivity(intent);
					
				}
			};
			timer.schedule(task,50);
	    }
	 /**
		 * ����Ƿ��ס����
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
