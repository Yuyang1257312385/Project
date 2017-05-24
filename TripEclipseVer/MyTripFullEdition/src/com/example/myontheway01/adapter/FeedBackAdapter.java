package com.example.myontheway01.adapter;

import java.util.List;
import java.util.zip.Inflater;

import cn.bmob.v3.BmobUser;

import com.example.myontheway01.R;
import com.example.myontheway01.bmobdb.FeedBack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FeedBackAdapter extends BaseAdapter{

	private Context context;              // 1�������ģ���֮�󶨵�Activity��
	private LayoutInflater mInflater;     // 2:������
	private List<FeedBack> mFeedBackList;  // 3:��������
	
// 4:���캯��	
	public FeedBackAdapter(Context context) {
		super();
		this.context = context;
		mInflater=LayoutInflater.from(context);  //��context(��Activity)��ɸѡListView�õ�
	}
	
// 5:Activity���ô˷���ʱ��������(��������)������������
	public void setFeedBackList(List<FeedBack> feedBackList) {
		this.mFeedBackList = feedBackList;
	}

	@Override
	public int getCount() {
		return mFeedBackList!=null?mFeedBackList.size():0;   //6:���ؼ��ϵ�����
	}
 
	@Override
	public Object getItem(int position) {
		return mFeedBackList.get(position);      //7:
	}

	@Override
	public long getItemId(int position) {        //8:
		return position;
	}

	@Override
	public View getView(int position, View contentView, ViewGroup arg2) {
		Holder holder=null;
		
		if(contentView==null){
			holder=new Holder();
			contentView=mInflater.inflate(R.layout.activity_feedback_item, null);
			holder.mNickNameTv=(TextView) contentView.findViewById(R.id.tv_nick_name);
			holder.mFeedbackContentTv=(TextView) contentView.findViewById(R.id.tv_feedback_content);
		    holder.mFeedBackTimeTv=(TextView) contentView.findViewById(R.id.tv_date);
			contentView.setTag(holder);
		}
		else{
			holder=(Holder) contentView.getTag();
		}
		
		holder.mNickNameTv.setText(mFeedBackList.get(position).getFeedBackUser().getUsername());
        holder.mFeedbackContentTv.setText(mFeedBackList.get(position).getFeedBackContent());
		holder.mFeedBackTimeTv.setText(mFeedBackList.get(position).getCreatedAt());
        return contentView;
	}
	class Holder{
		private TextView mNickNameTv;
		private TextView mFeedbackContentTv;
		private TextView mFeedBackTimeTv;
	}
}