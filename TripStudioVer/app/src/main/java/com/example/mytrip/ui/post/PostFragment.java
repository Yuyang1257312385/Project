package com.example.mytrip.ui.post;

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

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import com.example.mytrip.R;
import com.example.mytrip.tools.ToastUtils;
import com.example.mytrip.ui.post.adapter.PostAdapter;
import com.example.mytrip.ui.post.bean.CommentBean;
import com.example.mytrip.ui.post.bean.Comments;
import com.example.mytrip.ui.post.bean.Likes;
import com.example.mytrip.ui.post.bean.LikesBean;
import com.example.mytrip.ui.post.bean.Post;
import com.example.mytrip.ui.post.bean.PostBean;

public class PostFragment extends Fragment {
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
		//动态ListView
		postLv = (ListView) v.findViewById(R.id.lv_post);
		postLv.setVisibility(View.GONE);// 数据隐藏
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
		query.findObjects(new FindListener<Post>() {
			@Override
			public void done(List<Post> list, BmobException e) {
				if(e == null){
					//将object存放到List<postBean>
					List<PostBean> postBeanList=new ArrayList<PostBean>();
					for(Post post:list){
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
				}else {
					ToastUtils.showShortToast(e.getMessage() + "("+ e.getErrorCode()+")");
				}
			}


		});
	}
	/**
	 * 参数为帖子的集合，先查询出所有评论，
	 * 然后根据帖子的id与帖子的集合做对比，
	 * 最后获得一个评论的集合，该集合包括
	 * 帖子作者，帖子内容，帖子时间,帖子对应的评论集合（每个评论又包括评论者和评论内容）,
	 * 然后刷新适配器
	 * */
	private void getAllDataList(final List<PostBean> postBeanList) {
		BmobQuery<Comments> query = new BmobQuery<Comments>();
		//query.addWhereEqualTo("author", user);    // 查询当前用户的所有帖子,若不加这句查出来的是所有用户发的帖子
		query.order("-createdAt");
		query.setLimit(100);
		query.include("commentAuthor");// 希望在查询帖子信息的同时也把发布人的信息查询出来
		query.findObjects(new FindListener<Comments>() {
			@Override
			public void done(List<Comments> list, BmobException e) {
				if(e == null){
					for(PostBean postBean:postBeanList){
						List<CommentBean> commentBeanList=new ArrayList<CommentBean>();
						for(int i=0;i<list.size();i++){

							if(list.get(i).getPost().getObjectId().equals(postBean.getPostId())){
								CommentBean commentBean=new CommentBean();
								commentBean.setCommentAuthor(list.get(i).getCommentAuthor().getUsername());
								commentBean.setCommentContent(list.get(i).getCommentContent());
								commentBean.setReplyTo(list.get(i).getReplyTo());
								commentBeanList.add(commentBean);
							}
						}
						postBean.setCommentList(commentBeanList);
					}

					if(postBeanList.size()>0){
						postAdapter.setPostList(postBeanList);
						postAdapter.notifyDataSetChanged();
						// 更新UI
						postLv.setVisibility(View.VISIBLE);// 数据显示
					}
					//addLikeList(postBeanList);
				}else{
					ToastUtils.showShortToast(e.getMessage() + "("+ e.getErrorCode()+")");
				}
			}
		});
	}
	public void addLikeList(final List<PostBean> postBeanList){
		BmobQuery<Likes> query = new BmobQuery<Likes>();
		query.order("-createdAt");
		query.setLimit(100);
		query.include("likeAuthor,post");// 希望该条赞的时候同时把点赞的人和帖子查出来
		query.findObjects(new FindListener<Likes>() {
			@Override
			public void done(List<Likes> list, BmobException e) {
				if(e == null){
					for(PostBean postBean:postBeanList){
						List<LikesBean> likesBeanList=new ArrayList<LikesBean>();
						for(int i=0;i<list.size();i++){

							if(list.get(i).getPost().getObjectId().equals(postBean.getPostId())){
								LikesBean likesBean=new LikesBean();
								//得到点赞的人的用户id
								likesBean.setLikeAuthorId(list.get(i).getLikeAuthor().getObjectId());
								//得到点赞的人的用户名字
								likesBean.setLikeAuthorName(list.get(i).getLikeAuthor().getUsername());
								//得到点赞的那个帖子的帖子id
								likesBean.setPostId(list.get(i).getPost().getObjectId());
								likesBeanList.add(likesBean);
							}
						}
						postBean.setPostLikes(likesBeanList);
					}
					if(postBeanList.size()>0){
						postAdapter.setPostList(postBeanList);
						postAdapter.notifyDataSetChanged();
						// 更新UI
						postLv.setVisibility(View.VISIBLE);// 数据显示
					}
				}else {
					ToastUtils.showShortToast(e.getMessage() + "("+ e.getErrorCode()+")");
				}
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
			ToastUtils.showShortToast("广播接受成功");
			if(action.equals("com.example.myontheway01")){
				//获取帖子及评论数据，并刷新适配器
				getPostData();
			}
		}

	}

}
