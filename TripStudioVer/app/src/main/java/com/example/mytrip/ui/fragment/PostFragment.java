package com.example.mytrip.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.example.mytrip.R;
import com.example.mytrip.ui.adapter.PostAdapter;
import com.example.mytrip.ui.bean.CommentBean;
import com.example.mytrip.ui.bean.LikesBean;
import com.example.mytrip.ui.bean.PostBean;
import com.example.mytrip.ui.bmobdb.Comments;
import com.example.mytrip.ui.bmobdb.Likes;
import com.example.mytrip.ui.bmobdb.Post;

public class PostFragment extends Fragment {
	private RelativeLayout mLoadRl;// Ԥ���صĽ���
	private PostAdapter postAdapter;//��̬������
	private ListView postLv;//��ʾ��̬��ListView
	private MyBroadcastReceiver mBroadcastReceiver;//�㲥������
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//fragment�󶨲���
		View v = inflater.inflate(R.layout.fragment_post, null);
		//ע��㲥
		registerBroadcast();
		//��ʼ��
		inti(v);
		//��ö�̬������ݲ�ˢ��������
		getPostData();
		return v;
	}
	/**
	 * ��ʼ��
	 * */
	public void inti(View v) {
		//Ԥ���ز���
		//mLoadRl = (RelativeLayout) v.findViewById(R.id.lodingRelativeLayout);
		//��̬ListView
		postLv = (ListView) v.findViewById(R.id.lv_post);
		mLoadRl.setVisibility(View.VISIBLE);// Ԥ������ʾ
		postLv.setVisibility(View.GONE);// ��������
		//LoadingAinm.ininLodingView(v);// Ԥ���صĶ���Ч��
		postAdapter = new PostAdapter(getActivity());
		postLv.setAdapter(postAdapter);
	}
	/**
	 * ע��㲥
	 * */
	public void registerBroadcast() {
		mBroadcastReceiver = new MyBroadcastReceiver();
		//ע��㲥
		IntentFilter filter = new IntentFilter();//��ͼ������
		filter.addAction("com.example.myontheway01");
		getActivity().registerReceiver(mBroadcastReceiver, filter);
	}
	/**
	 * ��bmob�л�õ���̬�����ݣ���ˢ��������
	 * */
	private void getPostData() {
		//BmobUser user = new BmobUser;
		BmobQuery<Post> query = new BmobQuery<Post>();
		//query.addWhereEqualTo("author", user);    // ��ѯ��ǰ�û�����������,����������������������û���������
		query.order("-createdAt");
		query.include("author");// ϣ���ڲ�ѯ������Ϣ��ͬʱҲ�ѷ����˵���Ϣ��ѯ����
		query.findObjects(getActivity(), new FindListener<Post>() {
		    @Override
		    public void onSuccess(List<Post> object) {
		       //��object��ŵ�List<postBean>
		    	List<PostBean> postBeanList=new ArrayList<PostBean>();
		    	for(Post post:object){
		    		PostBean postBean=new PostBean();
		    		postBean.setPostId(post.getObjectId());
		    		postBean.setPostAuthor(post.getAuthor().getUsername());
		    		postBean.setPostContent(post.getPostContent());
		    		postBean.setPostTime(post.getCreatedAt());
		    		postBean.setPostImageUrl(post.getPostImage());
		    		postBeanList.add(postBean);
		    	}
		    	//���û���������Ӻ����۵ĵķ���
		    	getAllDataList(postBeanList);
		    }
			@Override
		    public void onError(int code, String msg) {
		       
		    }
		});
	}
	/**
	 * ����Ϊ���ӵļ��ϣ��Ȳ�ѯ���������ۣ�Ȼ��������ӵ�id�����ӵļ������Աȣ������һ�����۵ļ��ϣ��ü��ϰ���
	 * �������ߣ��������ݣ�����ʱ��,���Ӷ�Ӧ�����ۼ��ϣ�ÿ�������ְ��������ߺ��������ݣ�,Ȼ��ˢ��������
	 * */
	private void getAllDataList(final List<PostBean> postBeanList) {
				BmobQuery<Comments> query = new BmobQuery<Comments>();
				//query.addWhereEqualTo("author", user);    // ��ѯ��ǰ�û�����������,����������������������û���������
				query.order("-createdAt");
				query.setLimit(100);
				query.include("commentAuthor");// ϣ���ڲ�ѯ������Ϣ��ͬʱҲ�ѷ����˵���Ϣ��ѯ����
				query.findObjects(getActivity(), new FindListener<Comments>() {
				    @Override
				    public void onSuccess(List<Comments> object) {	
			      	for(PostBean postBean:postBeanList){
			      		List<CommentBean> commentBeanList=new ArrayList<CommentBean>();
			      		for(int i=0;i<object.size();i++){
			      			
			      			if(object.get(i).getPost().getObjectId().equals(postBean.getPostId())){
			      				CommentBean commentBean=new CommentBean();
				      			commentBean.setCommentAuthor(object.get(i).getCommentAuthor().getUsername());
				      			commentBean.setCommentContent(object.get(i).getCommentContent());
			      				commentBean.setReplyTo(object.get(i).getReplyTo());
				      			commentBeanList.add(commentBean);
			      			}
			      		}postBean.setCommentList(commentBeanList);
			      	}
			      	addLikeList(postBeanList);      	
		        }
					@Override
				    public void onError(int code, String msg) {
				       
				    }
				});
	}
	public void addLikeList(final List<PostBean> postBeanList){
		BmobQuery<Likes> query = new BmobQuery<Likes>();
		query.order("-createdAt");
		query.setLimit(100);
		query.include("likeAuthor,post");// ϣ�������޵�ʱ��ͬʱ�ѵ��޵��˺����Ӳ����
		query.findObjects(getActivity(), new FindListener<Likes>() {
		    @Override
		    public void onSuccess(List<Likes> object) {	
	      	for(PostBean postBean:postBeanList){
	      		List<LikesBean> likesBeanList=new ArrayList<LikesBean>();
	      		for(int i=0;i<object.size();i++){
	      			
	      			if(object.get(i).getPost().getObjectId().equals(postBean.getPostId())){
	      				LikesBean likesBean=new LikesBean();
	      				//�õ����޵��˵��û�id
	      			    likesBean.setLikeAuthorId(object.get(i).getLikeAuthor().getObjectId());
	      			    //�õ����޵��˵��û�����
		      			likesBean.setLikeAuthorName(object.get(i).getLikeAuthor().getUsername());
		      			//�õ����޵��Ǹ����ӵ�����id
		      			likesBean.setPostId(object.get(i).getPost().getObjectId());
		      			likesBeanList.add(likesBean);
	      			}
	      		}postBean.setPostLikes(likesBeanList);
	      	}
			if(postBeanList.size()>0){
	         postAdapter.setPostList(postBeanList);	
	         postAdapter.notifyDataSetChanged();
	      // ����UI
				mLoadRl.setVisibility(View.GONE);// Ԥ��������
				postLv.setVisibility(View.VISIBLE);// ������ʾ
	      	}
	      	     	
        }
			@Override
		    public void onError(int code, String msg) {
		       
		    }
		});

		
	}
	
	/**
	 * �㲥������
	 * @author Administrator
	 *
	 */
	class MyBroadcastReceiver extends BroadcastReceiver{
        /**
         * �������շ����Ĺ㲥
         */
		@Override
		public void onReceive(Context arg0, Intent intent) {			
			//����Action�ж���˭���͵�
			String action = intent.getAction();
			Toast.makeText(getActivity(), "�㲥���ܳɹ�", Toast.LENGTH_LONG).show();
			if(action.equals("com.example.myontheway01")){
				//��ȡ���Ӽ��������ݣ���ˢ��������
				getPostData();
			}
		}
		
	}

}
