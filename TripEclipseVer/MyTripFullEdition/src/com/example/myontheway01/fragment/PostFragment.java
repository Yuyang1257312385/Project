package com.example.myontheway01.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;

import com.example.loadrefresh.LoadingAinm;
import com.example.myontheway01.R;
import com.example.myontheway01.adapter.PostAdapter;
import com.example.myontheway01.bean.CommentBean;
import com.example.myontheway01.bean.LikesBean;
import com.example.myontheway01.bean.PostBean;
import com.example.myontheway01.bmobdb.Comments;
import com.example.myontheway01.bmobdb.Likes;
import com.example.myontheway01.bmobdb.Post;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class PostFragment extends Fragment {
	private RelativeLayout mLoadRl;// 预加载的界面
	private PostAdapter postAdapter;//动态适配器
	private ListView postLv;//显示动态的ListView
	private MyBroadcastReceiver mBroadcastReceiver;//广播接收器
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//fragment绑定布局
		View v = inflater.inflate(R.layout.fragment_post, null);
		//注册广播
		registerBroadcast();
		//初始化
		inti(v);
		//获得动态表的数据并刷新适配器
		getPostData();
		return v;
	}
	/**
	 * 初始化
	 * */
	public void inti(View v) {
		//预加载布局
		mLoadRl = (RelativeLayout) v.findViewById(R.id.lodingRelativeLayout);
		//动态ListView
		postLv = (ListView) v.findViewById(R.id.lv_post);
		mLoadRl.setVisibility(View.VISIBLE);// 预加载显示
		postLv.setVisibility(View.GONE);// 数据隐藏
		LoadingAinm.ininLodingView(v);// 预加载的动画效果
		postAdapter = new PostAdapter(getActivity());
		postLv.setAdapter(postAdapter);
	}
	/**
	 * 注册广播
	 * */
	public void registerBroadcast() {
		mBroadcastReceiver = new MyBroadcastReceiver();
		//注册广播
		IntentFilter filter = new IntentFilter();//意图过滤器
		filter.addAction("com.example.myontheway01");
		getActivity().registerReceiver(mBroadcastReceiver, filter);
	}
	/**
	 * 从bmob中获得到动态的数据，并刷新适配器
	 * */
	private void getPostData() {
		//BmobUser user = new BmobUser;
		BmobQuery<Post> query = new BmobQuery<Post>();
		//query.addWhereEqualTo("author", user);    // 查询当前用户的所有帖子,若不加这句查出来的是所有用户发的帖子
		query.order("-createdAt");
		query.include("author");// 希望在查询帖子信息的同时也把发布人的信息查询出来
		query.findObjects(getActivity(), new FindListener<Post>() {
		    @Override
		    public void onSuccess(List<Post> object) {
		       //将object存放到List<postBean>
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
		    	//调用获得所有帖子和评论的的方法
		    	getAllDataList(postBeanList);
		    }
			@Override
		    public void onError(int code, String msg) {
		       
		    }
		});
	}
	/**
	 * 参数为帖子的集合，先查询出所有评论，然后根据帖子的id与帖子的集合做对比，最后获得一个评论的集合，该集合包括
	 * 帖子作者，帖子内容，帖子时间,帖子对应的评论集合（每个评论又包括评论者和评论内容）,然后刷新适配器
	 * */
	private void getAllDataList(final List<PostBean> postBeanList) {
				BmobQuery<Comments> query = new BmobQuery<Comments>();
				//query.addWhereEqualTo("author", user);    // 查询当前用户的所有帖子,若不加这句查出来的是所有用户发的帖子
				query.order("-createdAt");
				query.setLimit(100);
				query.include("commentAuthor");// 希望在查询帖子信息的同时也把发布人的信息查询出来
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
		query.include("likeAuthor,post");// 希望该条赞的时候同时把点赞的人和帖子查出来
		query.findObjects(getActivity(), new FindListener<Likes>() {
		    @Override
		    public void onSuccess(List<Likes> object) {	
	      	for(PostBean postBean:postBeanList){
	      		List<LikesBean> likesBeanList=new ArrayList<LikesBean>();
	      		for(int i=0;i<object.size();i++){
	      			
	      			if(object.get(i).getPost().getObjectId().equals(postBean.getPostId())){
	      				LikesBean likesBean=new LikesBean();
	      				//得到点赞的人的用户id
	      			    likesBean.setLikeAuthorId(object.get(i).getLikeAuthor().getObjectId());
	      			    //得到点赞的人的用户名字
		      			likesBean.setLikeAuthorName(object.get(i).getLikeAuthor().getUsername());
		      			//得到点赞的那个帖子的帖子id
		      			likesBean.setPostId(object.get(i).getPost().getObjectId());
		      			likesBeanList.add(likesBean);
	      			}
	      		}postBean.setPostLikes(likesBeanList);
	      	}
			if(postBeanList.size()>0){
	         postAdapter.setPostList(postBeanList);	
	         postAdapter.notifyDataSetChanged();
	      // 更新UI
				mLoadRl.setVisibility(View.GONE);// 预加载隐藏
				postLv.setVisibility(View.VISIBLE);// 数据显示
	      	}
	      	     	
        }
			@Override
		    public void onError(int code, String msg) {
		       
		    }
		});

		
	}
	
	/**
	 * 广播接受器
	 * @author Administrator
	 *
	 */
	class MyBroadcastReceiver extends BroadcastReceiver{
        /**
         * 用来接收发来的广播
         */
		@Override
		public void onReceive(Context arg0, Intent intent) {			
			//根据Action判断是谁发送的
			String action = intent.getAction();
			Toast.makeText(getActivity(), "广播接受成功", Toast.LENGTH_LONG).show();
			if(action.equals("com.example.myontheway01")){
				//获取帖子及评论数据，并刷新适配器
				getPostData();
			}
		}
		
	}

}
