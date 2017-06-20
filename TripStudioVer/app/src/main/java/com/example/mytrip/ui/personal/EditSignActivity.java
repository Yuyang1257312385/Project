package com.example.mytrip.ui.personal;

import com.example.mytrip.R;
import com.example.mytrip.tools.LogUtil;
import com.example.mytrip.tools.ToastUtils;
import com.example.mytrip.ui.personal.bean.MyUser;
import com.example.mytrip.ui.slideitem.SetActivity;

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
	MyUser bmobUser = BmobUser.getCurrentUser(MyUser.class);

	mUser.update(bmobUser.getObjectId(),new UpdateListener() {
		@Override
		public void done(BmobException e) {
			if(e == null){
				Intent intent=new Intent(EditSignActivity.this,SetActivity.class);
				startActivity(intent);
			}else {
				ToastUtils.showShortToast("修改失败");
				LogUtil.d("LYJ",e.toString());
			}
		}


	});
}
}
