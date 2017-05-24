package com.example.mytrip.ui.adapter;

import java.util.List;

import com.example.mytrip.R;
import com.example.mytrip.ui.bmobdb.FeedBack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FeedBackAdapter extends BaseAdapter{

	private Context context;
	private LayoutInflater mInflater;
	private List<FeedBack> mFeedBackList;
	
	public FeedBackAdapter(Context context) {
		super();
		this.context = context;
		mInflater=LayoutInflater.from(context);
	}
	
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