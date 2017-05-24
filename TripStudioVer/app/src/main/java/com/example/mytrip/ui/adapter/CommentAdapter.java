package com.example.mytrip.ui.adapter;

import java.util.List;

import com.example.mytrip.R;
import com.example.mytrip.ui.bean.CommentBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CommentAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<CommentBean> commentList;
	
    public CommentAdapter(Context context){
    	inflater=LayoutInflater.from(context);
    }

	public void setList(List<CommentBean> list) {
		this.commentList = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return commentList!=null?commentList.size():0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return commentList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
    
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
	 ViewHolder viewHolder;    
	    if (convertView == null) {
	    	viewHolder=new ViewHolder();
			convertView = inflater.inflate(R.layout.item_comment, null);
			viewHolder.commentAuthorName = (TextView) convertView
					.findViewById(R.id.tv_comment_author);
			viewHolder.commentContent = (TextView) convertView
					.findViewById(R.id.tv_comment_text);
			viewHolder.replyTo=(TextView) convertView.findViewById(R.id.tv_reply_to);
			viewHolder.reply=(TextView) convertView.findViewById(R.id.tv_reply);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
			
		}
	    String commentAuthor=commentList.get(position).getCommentAuthor();
	   if(commentAuthor!=null){
		   viewHolder.commentAuthorName.setText(commentAuthor);
	   }
		String commentContent=commentList.get(position).getCommentContent();
		if(commentContent!=null){
			viewHolder.commentContent.setText(commentContent);
		}
		String replyTo=commentList.get(position).getReplyTo();
		if(replyTo!=null&& !replyTo.equals("")){
			viewHolder.replyTo.setText(replyTo);
			viewHolder.replyTo.setVisibility(View.VISIBLE);
			viewHolder.reply.setVisibility(View.VISIBLE);
			//viewHolder.reply.setTextColor(0xffffff00);
		}else{
			viewHolder.reply.setVisibility(View.GONE);
			viewHolder.replyTo.setVisibility(View.GONE);
		}
		return convertView;
	}
	public class ViewHolder {
		private TextView commentAuthorName; // �������ǳ�
		private TextView commentContent; // ���� ����
		private TextView replyTo;//�ظ�˭
		private TextView reply;//�ظ�������
	}
}
