package com.example.mytrip.ui;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.ResetPasswordByCodeListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mytrip.R;

/**
 * ��������
 * */
public class FindResetPasswordActivity extends Activity implements OnClickListener{
	private Button mFinishBtn;//��ɰ�ť
    private EditText mUserNameEt,mPasswordEt,mRePasswordEt;//�û��������룬�ٴ��������������
    private TextView mTitleTv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_info);
         intiView();
	}
	/**
	 * ��ʼ���ؼ�
	 * */
	private void intiView() {
		// TODO Auto-generated method stub
		mUserNameEt=(EditText) findViewById(R.id.et_username);
		mPasswordEt=(EditText) findViewById(R.id.et_password);
		mRePasswordEt=(EditText) findViewById(R.id.et_repassword);
		mFinishBtn=(Button) findViewById(R.id.btn_finish);
		mTitleTv=(TextView) findViewById(R.id.tv_title_register_data);
		mTitleTv.setText("��������");
		mFinishBtn.setOnClickListener(this);
	}
	
	/** ͨ��������֤���������û����� 
	 * @method requestSmsCode    
	 * @return void  
	 * ע�������������ȵ���������֤��Ľӿڻ�ȡ������֤�룬�����ö�����֤����������ӿ������ø��ֻ��Ŷ�Ӧ���û�������
	 */
	private void resetPasswordBySMS(){
        String code=getIntent().getStringExtra("code");
        String password=mPasswordEt.getText().toString().trim();
        String rePassword=mRePasswordEt.getText().toString().trim();
        if(password!=null&&!password.equals("")){
        	if(!password.equals(rePassword)){
        		toast("�����������벻һ��");
        		return;
        	}
        }else{
        	toast("���벻��Ϊ��");
        	return;
        }
		//2�����õ��ǰ��˸��ֻ��ŵ��˻�������
		BmobUser.resetPasswordBySMSCode(this, code,password, new ResetPasswordByCodeListener() {
			
			@Override
			public void done(BmobException e) {
				// TODO Auto-generated method stub
				if(e==null){
					toast("�������óɹ�");
					Intent intent=new Intent(FindResetPasswordActivity.this,LoginActivity.class);
					startActivity(intent);
				}else{
					toast("�����룺"+e.getErrorCode()+",����ԭ��"+e.getLocalizedMessage());
					
				}
			}
		});
	}
	
	
	 //toast������������Ϣ
		public void toast(String str){
			Toast.makeText(FindResetPasswordActivity.this, str, Toast.LENGTH_LONG).show();
		}
	//��ת����¼����
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		resetPasswordBySMS();
		
	}
}
