package com.example.myontheway01;

import java.util.ArrayList;
import java.util.List;

import com.example.myontheway01.adapter.FeedBackAdapter;
import com.example.myontheway01.bean.StrategyBean;
import com.example.myontheway01.bmobdb.FeedBack;
import com.example.myontheway01.fragment.HttpGetData;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FeedBackActivity extends ListActivity implements OnClickListener{
	private EditText mFeedBackContentEt;
	private Button mSubmitBtn;
	BmobUser user;
	String content;
	FeedBack fb;
	private FeedBackAdapter mFbAdapter;   //声明适配器
//	private ListView mFeedBackLv;       //声明反馈的集合控件
	//~~1~~刷新中的上拉下刷:
	private PullToRefreshListView mPullToRefreshListView; 
    Handler handler=new Handler(){
    	public void handleMessage(Message msg) {
    		switch (msg.what) {
			case 0:
				List<FeedBack> data=new ArrayList<FeedBack>();
				data=(List<FeedBack>) msg.obj;
				if(data!=null){
					mFbAdapter.setFeedBackList(data);
					mFbAdapter.notifyDataSetChanged();
				}
				
				break;
			case 1:
				List<FeedBack> data1=new ArrayList<FeedBack>();
				data1=(List<FeedBack>) msg.obj;
				if(data1!=null){
					mFbAdapter.setFeedBackList(data1);
					mFbAdapter.notifyDataSetChanged();
				}
				break;
			case 2:
				List<FeedBack> data2=new ArrayList<FeedBack>();
				data2=(List<FeedBack>) msg.obj;
				if(data2!=null){
					mFbAdapter.setFeedBackList(data2);
					mFbAdapter.notifyDataSetChanged();
				}
				break;
			default:
				break;
			}
    	}
    };

	//	private TextView mNickNameTv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_feedback);
		initView();
		query(0);
		refresh();
	}

	//查询出所有的反馈
	public void query(final int what) {
		// TODO Auto-generated method stub
		BmobQuery<FeedBack> query=new BmobQuery<FeedBack>();
		query.order("-createdAt");
		query.include("feedBackUser");
		query.findObjects(this, new FindListener<FeedBack>(){

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(List<FeedBack> arg0) {
				// TODO Auto-generated method stub
				Message message = Message.obtain();
				message.what = what;
				message.obj = arg0;
				handler.sendMessage(message);
				mFbAdapter.setFeedBackList(arg0);
				mFbAdapter.notifyDataSetChanged();
//				Toast.makeText(FeedBackActivity.this, "OK", Toast.LENGTH_LONG).show();


			}});
	}
	//初始化控件
	private void initView() {
		// TODO Auto-generated method stub
		mFeedBackContentEt=(EditText) findViewById(R.id.et_feedback_content);
		mSubmitBtn=(Button) findViewById(R.id.btn_submit);
		mSubmitBtn.setOnClickListener(this);
		//初始化上拉下刷的ListView~~5~~
		mPullToRefreshListView=(PullToRefreshListView) findViewById(R.id.pull_refresh_list);
		//适配器相关	
	//	mFeedBackLv=(ListView) findViewById(R.id.lv_feedBack); //给反馈集合的控件进行是初始化
    	mFbAdapter=new FeedBackAdapter(this);    //实例化适配器，调用构造函数
//		mFeedBackLv.setAdapter(mFbAdapter);
		mPullToRefreshListView.setAdapter(mFbAdapter);
		//绑定适配器
		 
		
	}
	
	private void refresh() {
		// TODO Auto-generated method stub
		mPullToRefreshListView.setMode(Mode.BOTH);	
		mPullToRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				if(refreshView.isHeaderShown()){
					/**
					 * 显示刷新时间
					 */
					//String label = DateUtils.formatDateTime(FeedBackActivity.this, System.currentTimeMillis(),
					//		DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

					// Update the LastUpdatedLabel
					//refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
					query(1);
				}else{
					//Toast.makeText(FeedBackActivity.this, "上拉加载更多", Toast.LENGTH_LONG).show();
					query(2);
				}
			}});
	
		
	}

	//提交监听的方法
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		user=BmobUser.getCurrentUser(this,BmobUser.class);       //得到当前用户
		content=mFeedBackContentEt.getText().toString().trim();  // 得到反馈内容
		save();
	}

	//保存反馈人,反馈内容到Bmob
	private void save() {
		// TODO Auto-generated method stub
		fb=new FeedBack();
		fb.setFeedBackUser(user);
		if(content!=null && !content.equals("")){
		fb.setFeedBackContent(content);
		fb.save(this, new SaveListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				Toast.makeText(FeedBackActivity.this, "提交成功！", Toast.LENGTH_LONG).show();
				mFeedBackContentEt.setText("");
				query(0);
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Toast.makeText(FeedBackActivity.this, "未提交成功！", Toast.LENGTH_LONG).show();
			}
		});
	}
		else{
			Toast.makeText(this, "您还未留下宝贵的意见", Toast.LENGTH_LONG).show();
		}
	}
}




