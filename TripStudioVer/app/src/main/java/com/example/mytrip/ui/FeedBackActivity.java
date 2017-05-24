package com.example.mytrip.ui;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

import com.example.mytrip.R;

public class FeedBackActivity extends ListActivity implements OnClickListener{
//	private EditText mFeedBackContentEt;
//	private Button mSubmitBtn;
//	BmobUser user;
//	String content;
//	FeedBack fb;
//	private FeedBackAdapter mFbAdapter;   //����������
////	private ListView mFeedBackLv;       //���������ļ��Ͽؼ�
//	//~~1~~ˢ���е�������ˢ:
//	private PullToRefreshListView mPullToRefreshListView;
//    Handler handler=new Handler(){
//    	public void handleMessage(Message msg) {
//    		switch (msg.what) {
//			case 0:
//				List<FeedBack> data=new ArrayList<FeedBack>();
//				data=(List<FeedBack>) msg.obj;
//				if(data!=null){
//					mFbAdapter.setFeedBackList(data);
//					mFbAdapter.notifyDataSetChanged();
//				}
//
//				break;
//			case 1:
//				List<FeedBack> data1=new ArrayList<FeedBack>();
//				data1=(List<FeedBack>) msg.obj;
//				if(data1!=null){
//					mFbAdapter.setFeedBackList(data1);
//					mFbAdapter.notifyDataSetChanged();
//				}
//				break;
//			case 2:
//				List<FeedBack> data2=new ArrayList<FeedBack>();
//				data2=(List<FeedBack>) msg.obj;
//				if(data2!=null){
//					mFbAdapter.setFeedBackList(data2);
//					mFbAdapter.notifyDataSetChanged();
//				}
//				break;
//			default:
//				break;
//			}
//    	}
//    };

	//	private TextView mNickNameTv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_feedback);
//		initView();
//		query(0);
//		refresh();
	}

	@Override
	public void onClick(View v) {

	}


//	public void query(final int what) {
//		// TODO Auto-generated method stub
//		BmobQuery<FeedBack> query=new BmobQuery<FeedBack>();
//		query.order("-createdAt");
//		query.include("feedBackUser");
//		query.findObjects(this, new FindListener<FeedBack>(){
//
//			@Override
//			public void onError(int arg0, String arg1) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void onSuccess(List<FeedBack> arg0) {
//				// TODO Auto-generated method stub
//				Message message = Message.obtain();
//				message.what = what;
//				message.obj = arg0;
//				handler.sendMessage(message);
//				mFbAdapter.setFeedBackList(arg0);
//				mFbAdapter.notifyDataSetChanged();
////				Toast.makeText(FeedBackActivity.this, "OK", Toast.LENGTH_LONG).show();
//
//
//			}});
//	}
//	private void initView() {
//		// TODO Auto-generated method stub
//		mFeedBackContentEt=(EditText) findViewById(R.id.et_feedback_content);
//		mSubmitBtn=(Button) findViewById(R.id.btn_submit);
//		mSubmitBtn.setOnClickListener(this);
//		mPullToRefreshListView=(PullToRefreshListView) findViewById(R.id.pull_refresh_list);
//	//	mFeedBackLv=(ListView) findViewById(R.id.lv_feedBack); //���������ϵĿؼ������ǳ�ʼ��
//    	mFbAdapter=new FeedBackAdapter(this);    //ʵ���������������ù��캯��
////		mFeedBackLv.setAdapter(mFbAdapter);
//		mPullToRefreshListView.setAdapter(mFbAdapter);
//
//
//
//	}
//
//	private void refresh() {
//		// TODO Auto-generated method stub
//		mPullToRefreshListView.setMode(Mode.BOTH);
//		mPullToRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
//
//			@Override
//			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
//				// TODO Auto-generated method stub
//				if(refreshView.isHeaderShown()){
//					/**
//					 * ��ʾˢ��ʱ��
//					 */
//					//String label = DateUtils.formatDateTime(FeedBackActivity.this, System.currentTimeMillis(),
//					//		DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
//
//					// Update the LastUpdatedLabel
//					//refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
//					query(1);
//				}else{
//					//Toast.makeText(FeedBackActivity.this, "�������ظ���", Toast.LENGTH_LONG).show();
//					query(2);
//				}
//			}});
//
//
//	}
//
//	@Override
//	public void onClick(View arg0) {
//		// TODO Auto-generated method stub
//		user=BmobUser.getCurrentUser(this,BmobUser.class);       //�õ���ǰ�û�
//		content=mFeedBackContentEt.getText().toString().trim();  // �õ���������
//		save();
//	}
//
//
//	private void save() {
//		// TODO Auto-generated method stub
//		fb=new FeedBack();
//		fb.setFeedBackUser(user);
//		if(content!=null && !content.equals("")){
//		fb.setFeedBackContent(content);
//		fb.save(this, new SaveListener() {
//
//			@Override
//			public void onSuccess() {
//				// TODO Auto-generated method stub
//				Toast.makeText(FeedBackActivity.this, "�ύ�ɹ���", Toast.LENGTH_LONG).show();
//				mFeedBackContentEt.setText("");
//				query(0);
//			}
//
//			@Override
//			public void onFailure(int arg0, String arg1) {
//				// TODO Auto-generated method stub
//				Toast.makeText(FeedBackActivity.this, "δ�ύ�ɹ���", Toast.LENGTH_LONG).show();
//			}
//		});
//	}
//		else{
//			Toast.makeText(this, "����δ���±�������", Toast.LENGTH_LONG).show();
//		}
//	}
}




