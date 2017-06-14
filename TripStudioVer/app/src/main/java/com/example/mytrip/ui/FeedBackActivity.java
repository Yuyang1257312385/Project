package com.example.mytrip.ui;

import android.app.ListActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.mytrip.R;
import com.example.mytrip.base.BaseActivity;
import com.example.mytrip.tools.ToastUtils;
import com.example.mytrip.ui.bmobdb.FeedBack;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class FeedBackActivity extends BaseActivity implements OnClickListener{
	private EditText mFeedBackContentEt;
	private Button mSubmitBtn;
	BmobUser user;
	String content;
	FeedBack fb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_feedback);
		initView();
	}


	private void initView() {
		mFeedBackContentEt=(EditText) findViewById(R.id.et_feedback_content);
		mSubmitBtn=(Button) findViewById(R.id.btn_submit);
		mSubmitBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.btn_submit:
				save();
				break;
			default:
				break;
		}

	}

	//保存反馈人,反馈内容到Bmob
	private void save() {
		user=BmobUser.getCurrentUser(BmobUser.class);       //得到当前用户
		content=mFeedBackContentEt.getText().toString().trim();  // 得到反馈内容
		fb=new FeedBack();
		fb.setFeedBackUser(user);
		if(!TextUtils.isEmpty(content)){
			fb.setFeedBackContent(content);
			fb.save(new SaveListener<String>() {
				@Override
				public void done(String s, BmobException e) {
					if(e == null){
						mFeedBackContentEt.setText("");
						ToastUtils.showShortToast("提交成功");
					}else {
						ToastUtils.showShortToast("未提交成功--"+ e.getMessage() + "("+ e.getErrorCode()+")");
					}
				}
			});
		} else{
			ToastUtils.showShortToast("您还未留下宝贵的意见");
		}
	}
}




