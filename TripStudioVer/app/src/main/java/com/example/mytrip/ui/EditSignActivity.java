package com.example.mytrip.ui;

import com.example.mytrip.R;
import com.example.mytrip.ui.bmobdb.MyUser;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditSignActivity extends Activity implements OnClickListener{
	private Button updateSignBtn;
	private EditText updateSignEt;
   @Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_edit_sign);
	updateSignBtn=(Button) findViewById(R.id.btn_submit);
	updateSignEt=(EditText) findViewById(R.id.et_update);
	updateSignBtn.setOnClickListener(this);
}
@Override
public void onClick(View arg0) {
	// TODO Auto-generated method stub
	String sign=updateSignEt.getText().toString().trim();
	MyUser mUser=new MyUser();
	mUser.setSign(sign);
	MyUser bmobUser = BmobUser.getCurrentUser(this,MyUser.class);
	mUser.update(this,bmobUser.getObjectId(),new UpdateListener() {
	    @Override
	    public void onSuccess() {
	     Intent intent=new Intent(EditSignActivity.this,SetActivity.class);
	     startActivity(intent);
	    }
	    @Override
	    public void onFailure(int code, String msg) {
	       Toast.makeText(EditSignActivity.this, "�޸�ʧ��", Toast.LENGTH_LONG).show();
	    }
	});
}
}
