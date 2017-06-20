package com.example.mytrip.ui.post.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import com.example.mytrip.R;
import com.example.mytrip.tools.LogUtil;
import com.example.mytrip.tools.ToastUtils;
import com.example.mytrip.ui.post.bean.Comments;
import com.example.mytrip.ui.post.bean.Likes;
import com.example.mytrip.ui.post.bean.LikesBean;
import com.example.mytrip.ui.post.bean.Post;
import com.example.mytrip.ui.post.bean.PostBean;
import com.example.mytrip.view.MyListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class PostAdapter extends BaseAdapter {
	private LayoutInflater inflater;// 筛选器
	private Activity context;// 上下文，即相应的activity
	private List<PostBean> postList;// 动态数据的集合
	private int expandPosition = -1;// 用于发表评论框和按钮的显示和隐藏

	private CommentAdapter commentAdapter;// 由于嵌套了一个评论的listView,故需要一个评论的适配器
	private PopupWindow mPopupWindow;
	// 屏幕的width
	private int mScreenWidth;
	// 屏幕的height
	private int mScreenHeight;
	// PopupWindow的width
	private int mPopupWindowWidth;
	// PopupWindow的height
	private int mPopupWindowHeight;
	String replyTo = "";// 回复那个人评论的昵称
	String replyContent = "";// 回复的内容
	Post post1 = new Post();
	private EditText commentReplyEt;// 回复评论的输入框
	private Button commentReplyBtn;// 提交回复的按钮
	//==============
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private DisplayImageOptions options;//设置图片访问参数
	//===========================
	// 通过构造函数为声明的变量赋值
	public PostAdapter(Activity context) {
		super();
		this.context = context;
		inflater = LayoutInflater.from(context);
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_stub)
				.showImageForEmptyUri(R.drawable.ic_empty)
				.showImageOnFail(R.drawable.ic_error)
				.cacheInMemory(true)
				.cacheOnDisc(true)
				.considerExifParams(true)
				//.displayer(new RoundedBitmapDisplayer(20))
				//.displayer(new CircleBitmapDisplayer())//不加这个，为矩形图，加这个为圆形图
				.build();
	}

	// 设置动态列表的数据
	public void setPostList(List<PostBean> postList) {
		this.postList = postList;
	}

	@Override
	public int getCount() {
		return postList != null ? postList.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		return postList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		commentAdapter = new CommentAdapter(context);
		if (convertView == null) {

			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_post, null);

			// viewHolder.userHeadIv=(ImageView)
			// convertView.findViewById(R.id.iv_post_user_headimg);
			viewHolder.userNameTv = (TextView) convertView
					.findViewById(R.id.tv_post_username);
			viewHolder.postTimeTv = (TextView) convertView
					.findViewById(R.id.tv_post_time);
			viewHolder.postContentTv = (TextView) convertView
					.findViewById(R.id.tv_post_content);
			// viewHolder.postIv=(ImageView)
			// convertView.findViewById(R.id.iv_post_img);
			viewHolder.postLikesTv = (TextView) convertView
					.findViewById(R.id.tv_post_likes);
			viewHolder.postLikeClickTv =(TextView) convertView
					.findViewById(R.id.tv_like_or_not);
			viewHolder.postCommentIv = (ImageView) convertView
					.findViewById(R.id.iv_post_comment);
			viewHolder.postCommentEt = (EditText) convertView
					.findViewById(R.id.et_post_comment_content);
			viewHolder.postCommentSubmitBtn = (Button) convertView
					.findViewById(R.id.btn_comment_submit);
			viewHolder.postCommentsLv = (MyListView) convertView
					.findViewById(R.id.lv_user_comment_replys);
			// viewHolder.postCommentIv.setTag(position);
			viewHolder.CommentView = LayoutInflater.from(context).inflate(
					R.layout.item_comment, null);
			viewHolder.postImage=(ImageView) convertView.findViewById(R.id.iv_post_img);
			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		// viewHolder.userHeadIv.setBackgroundResource(R.drawable.img_1);
		String userName = postList.get(position).getPostAuthor();
		if (userName != null) {
			viewHolder.userNameTv.setText(userName);
		}
		String postTime = postList.get(position).getPostTime();
		if (postTime != null) {
			viewHolder.postTimeTv.setText(postTime);
		}

		String postContent = postList.get(position).getPostContent();
		if (postContent != null) {
			viewHolder.postContentTv.setText(postContent);
		}
		//================================================================
		if (null == postList.get(position).getPostImageUrl()) {
			viewHolder.postImage.setVisibility(View.GONE);
		} else {
			viewHolder.postImage.setVisibility(View.VISIBLE);
			imageLoader.displayImage(postList.get(position).getPostImageUrl().getFileUrl() == null ? ""
					: postList.get(position).getPostImageUrl().getFileUrl(
					), viewHolder.postImage, options, animateFirstListener);
			/*ImageLoader
					.getInstance()
					.displayImage(
							postList.get(position).getPostImageUrl().getFileUrl(context) == null ? ""
									: postList.get(position).getPostImageUrl().getFileUrl(
											context),
							viewHolder.postImage,
							MyApp.getInstance().getOptions(
									R.drawable.bg_pic_loading),
									animateFirstListener);*/
		}
		//=================================================================
		// viewHolder.postIv.setBackgroundResource(R.drawable.img_1);
		// viewHolder.postLikesTv.setText(postList.get(position).getLikes().toString());
		viewHolder.postLikeClickTv.setOnClickListener(new ClickOrder(position,viewHolder));
		String postLikeStr="";

		if(postList.get(position).getPostLikes()!=null){
//			viewHolder.postLikeBtn.setTag(postList.get(position).getPostLikes()!=null);
//
//				for(LikesBean likesBean:postList.get(position).getPostLikes()){
//					BmobUser bUser=BmobUser.getCurrentUser(context);
//					//if(postList.get(position).getPostLikes().equals(viewHolder.postLikeBtn.getTag())){
//					if(likesBean.getLikeAuthorId().equals(bUser.getObjectId())){
//		                viewHolder.postLikeBtn.setText("取消");
//						//break;
//					}else{
//						viewHolder.postLikeBtn.setText("点赞");
//					}
//					//}
//				}
//


			for(LikesBean likesBean:postList.get(position).getPostLikes()){
				postLikeStr+=likesBean.getLikeAuthorName()+",";
				BmobUser bUser=BmobUser.getCurrentUser();
				//if(postList.get(position).getPostLikes().equals(viewHolder.postLikeBtn.getTag())){
				if(likesBean.getLikeAuthorId().equals(bUser.getObjectId())){
					viewHolder.postLikeClickTv.setText("0");
					//System.out.println(likesBean.getLikeAuthorId()+"======"+likesBean.getLikeAuthorName());
					//break;
				}else{
					viewHolder.postLikeClickTv.setText("1");
				}
			}

		}
		if(!postLikeStr.equals("")&&postLikeStr!=null){
			viewHolder.postLikesTv.setText(postLikeStr+"觉得很赞");

		}else{
			viewHolder.postLikesTv.setText("");

		}

		viewHolder.postCommentIv.setOnClickListener(new ClickOrder(position,viewHolder));
		viewHolder.postCommentSubmitBtn.setOnClickListener(new ClickOrder(
				position,viewHolder));
		// 设置评论框的显示和隐藏
		if (expandPosition == position) {
			viewHolder.postCommentEt.setVisibility(View.VISIBLE);
			viewHolder.postCommentEt.requestFocus();
			viewHolder.postCommentSubmitBtn.setVisibility(View.VISIBLE);
		} else {
			viewHolder.postCommentEt.setText("");
			viewHolder.postCommentEt.setVisibility(View.GONE);
			viewHolder.postCommentSubmitBtn.setVisibility(View.GONE);
		}

		viewHolder.postCommentsLv.setAdapter(commentAdapter);
		commentAdapter.setList(postList.get(position).getCommentList());
		viewHolder.postCommentsLv
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView arg0, View v, int arg2,
											long arg3) {

						replyTo = postList.get(position).getCommentList()
								.get(arg2).getCommentAuthor();
						post1.setObjectId(postList.get(position).getPostId());
						getPopupWindowInstance(position,viewHolder);

						mPopupWindow.showAtLocation(v, Gravity.TOP, 0, 200);

//						int[] location = new int[2];
//				        v.getLocationOnScreen(location);
//				        mPopupWindow.showAtLocation(v,
//				        		Gravity.NO_GRAVITY, location[0],
//				        		location[1]-mPopupWindow.getHeight());
						commentReplyEt.setHint("回复 " + replyTo);
						commentReplyBtn.setText("取消");
						commentAdapter.notifyDataSetChanged();
					}
				});
		commentAdapter.notifyDataSetChanged();

		return convertView;

	}

	/**
	 * 存放item中各个控件的类
	 * */
	public class ViewHolder {
		// private ImageView userHeadIv;//用户头像
		private TextView userNameTv;// 用户名
		private TextView postTimeTv;// 发表动态时间
		private TextView postContentTv;// 动态的内容
		// private ImageView postIv;//动态的图片
		private TextView postLikeClickTv;// 点赞（作用为按钮）
		private TextView postLikesTv;// 点赞的人
		private ImageView postCommentIv;// 评论（作用为按钮）
		private EditText postCommentEt;// 评论内容输入框
		private Button postCommentSubmitBtn;// 提交评论内容按钮
		private MyListView postCommentsLv;// 评论的listview
		private View CommentView;// 评论的item
		private ImageView postImage;//动态中的图片
	}
	private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}
	class ClickOrder implements OnClickListener {
		public int position;
		public ViewHolder viewHolder;
		public ClickOrder(int position,ViewHolder viewHolder) {
			this.position = position;
			this.viewHolder=viewHolder;
		}

		@Override
		public void onClick(View v) {

			switch (v.getId()) {
				// 点击评论图标，将评论框和评论按钮设置为可见
				case R.id.iv_post_comment:
					if (expandPosition == position) {
						expandPosition = -1;
					} else {
						expandPosition = position;
					}
					notifyDataSetChanged();
					break;
				// 点击提交评论按钮,将评论框和评论按钮隐藏，将数据存入到bmob
				case R.id.btn_comment_submit:
					BmobUser commentAuthor = BmobUser.getCurrentUser();
					String commentContent = viewHolder.postCommentEt.getText()
							.toString().trim();
					Post post = new Post();
					post.setObjectId(postList.get(position).getPostId());
					Comments comments = new Comments();
					comments.setCommentAuthor(commentAuthor);
					comments.setPost(post);
					comments.setCommentContent(commentContent);
					comments.save(new SaveListener<String>() {
						@Override
						public void done(String s, BmobException e) {
							if(e == null){
								Toast.makeText(context, "评论成功", Toast.LENGTH_LONG)
										.show();
								// 用Activity可以调用发送广播的方法
								Intent mIntent = new Intent("com.example.myontheway01");// 通过指定发送的频道
								context.sendBroadcast(mIntent);
							}else {
								Toast.makeText(context, "评论失败", Toast.LENGTH_LONG)
										.show();
								Intent mIntent = new Intent("com.example.myontheway01");// 通过指定发送的频道
								context.sendBroadcast(mIntent);
								LogUtil.d("LYJ",e.toString());
							}
						}
					});
					if (expandPosition == position) {
						expandPosition = -1;
					} else {
						expandPosition = position;
					}
					notifyDataSetChanged();
					break;
				case R.id.btn_reply_comment:
					if (commentReplyBtn.getText().equals("提交")) {
						BmobUser replyAuthor = BmobUser.getCurrentUser();
						replyContent = commentReplyEt.getText().toString().trim();
						Comments replyComment = new Comments();
						replyComment.setCommentAuthor(replyAuthor);
						replyComment.setPost(post1);
						replyComment.setCommentContent(replyContent);
						replyComment.setReplyTo(replyTo);
						replyComment.save(new SaveListener<String>() {
							@Override
							public void done(String s, BmobException e) {
								if(e == null){
									Toast.makeText(context, "评论成功", Toast.LENGTH_LONG)
											.show();
									commentReplyEt.setText("");
									// 用Activity可以调用发送广播的方法
									Intent mIntent = new Intent(
											"com.example.myontheway01");// 通过指定发送的频道
									context.sendBroadcast(mIntent);
								}else {
									Toast.makeText(context, "评论失败", Toast.LENGTH_LONG)
											.show();
									commentReplyEt.setText("");
									Intent mIntent = new Intent(
											"com.example.myontheway01");// 通过指定发送的频道
									context.sendBroadcast(mIntent);
									LogUtil.d("LYJ",e.toString());
								}
							}
						});
						mPopupWindow.dismiss();
					} else if (commentReplyBtn.getText().equals("取消")) {
						Toast.makeText(context, "取消回复", Toast.LENGTH_LONG).show();
						mPopupWindow.dismiss();
					}
					break;
				case R.id.tv_like_or_not:
					if(viewHolder.postLikeClickTv.getText().equals("1")){
						BmobUser user = BmobUser.getCurrentUser();
						Post post1 = new Post();
						post1.setObjectId(postList.get(position).getPostId());
						final Likes likes = new Likes();
						likes.setLikeAuthor(user);
						likes.setPost(post1);
						likes.save(new SaveListener<String>() {
							@Override
							public void done(String s, BmobException e) {
								if(e == null){
									Toast.makeText(context, "点赞成功", Toast.LENGTH_LONG).show();
									// viewHolder.postLikeBtn.setText("取消");
									Intent mIntent = new Intent(
											"com.example.myontheway01");// 通过指定发送的频道
									context.sendBroadcast(mIntent);
								}else {
									Toast.makeText(context, "点赞失败", Toast.LENGTH_LONG).show();
									Intent mIntent = new Intent(
											"com.example.myontheway01");// 通过指定发送的频道
									context.sendBroadcast(mIntent);
									LogUtil.d("LYJ",e.toString());
								}
							}
						});

					}else if(viewHolder.postLikeClickTv.getText().equals("0")){
						//根据当前用户和帖子id查出点赞的id并删除
						BmobUser bUser=BmobUser.getCurrentUser();
						String postId=postList.get(position).getPostId();
						getLikesId(bUser,postId);
					}
					break;
				default:
					break;
			}
		}

	}
	/**
	 * 通过当前用户和帖子ID获取点赞id
	 * */
	private void getLikesId(BmobUser bUser,String postId){

		//--and条件1
		BmobQuery<Likes> eq1 = new BmobQuery<Likes>();
		BmobUser bmobUser=new BmobUser();
		bmobUser.setObjectId(bUser.getObjectId());
		eq1.addWhereEqualTo("likeAuthor", bmobUser);
		//--and条件2
		BmobQuery<Likes> eq2 = new BmobQuery<Likes>();
		Post post=new Post();
		post.setObjectId(postId);
		eq2.addWhereEqualTo("post", post);
		//最后组装完整的and条件
		List<BmobQuery<Likes>> andQuerys = new ArrayList<BmobQuery<Likes>>();
		andQuerys.add(eq1);
		andQuerys.add(eq2);
		//查询符合整个and条件的人
		BmobQuery<Likes> query = new BmobQuery<Likes>();
		query.and(andQuerys);
		query.findObjects(new FindListener<Likes>() {
			@Override
			public void done(List<Likes> list, BmobException e) {
				if(e == null){
					String likesId="";
					for(Likes likes:list){
						likesId=likes.getObjectId();
					}
					deleteLikesById(likesId);
				}else {
					ToastUtils.showShortToast("查询失败");
				}
			}
		});
	}
	/**
	 * 根据点赞的id删除点赞数据
	 * */
	public void deleteLikesById(String likesId){
		Likes likes = new Likes();
		likes.setObjectId(likesId);
		likes.delete(likesId, new UpdateListener() {
			@Override
			public void done(BmobException e) {
				if(e == null){
					Toast.makeText(context, "取消成功", Toast.LENGTH_LONG).show();
					Intent mIntent = new Intent(
							"com.example.myontheway01");// 通过指定发送的频道
					context.sendBroadcast(mIntent);
				}else {
					Toast.makeText(context, "取消失败", Toast.LENGTH_LONG).show();
					Intent mIntent = new Intent(
							"com.example.myontheway01");// 通过指定发送的频道
					context.sendBroadcast(mIntent);
					LogUtil.d("LYJ","E ==" + e.getErrorCode() + e.toString());
				}
			}
		});
	}
	private void getPopupWindowInstance(int position,ViewHolder viewHolder) {
		if (null != mPopupWindow) {
			mPopupWindow.dismiss();
			return;
		} else {
			initPopuptWindow(position,viewHolder);
			mPopupWindow.setFocusable(true);
			commentReplyEt.setFocusableInTouchMode(true);
			commentReplyEt.requestFocus();
			commentReplyEt.findFocus();
			openKeyboard(new Handler(), 500);
		}
	}

	private void initPopuptWindow(int position,ViewHolder viewHolder) {
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		View popupWindow = layoutInflater.inflate(R.layout.popup_reply_comment,
				null);
		commentReplyBtn = (Button) popupWindow
				.findViewById(R.id.btn_reply_comment);
		commentReplyEt = (EditText) popupWindow
				.findViewById(R.id.et_reply_comment);
		//commentReplyEt.setFocusable(false);// 默认无焦点
		commentReplyBtn.setOnClickListener(new ClickOrder(position,viewHolder));
		commentReplyEt.addTextChangedListener(new EditChangedListener());
		// 绑定软键盘上的完成按键，输入框必须加上，android:imeOptions="actionDone"
		commentReplyEt.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
										  KeyEvent event) {
				Toast.makeText(context, "完成键监听成功", Toast.LENGTH_LONG).show();
				// 隐藏输入法界面
				((InputMethodManager) context
						.getSystemService(Context.INPUT_METHOD_SERVICE))
						.hideSoftInputFromWindow(
								commentReplyEt.getWindowToken(), 0);
				return true;
				// do sth when pressed the enter key in the Soft InputMethod

			}
		});

		// 创建一个PopupWindow
		// 参数1：contentView 指定PopupWindow的内容
		// 参数2：width 指定PopupWindow的width
		// 参数3：height 指定PopupWindow的height
		mPopupWindow = new PopupWindow(popupWindow, LayoutParams.MATCH_PARENT,
				150);
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		// 获取屏幕和PopupWindow的width和height
		mScreenWidth = wm.getDefaultDisplay().getWidth();// 手机屏幕宽度
		mScreenWidth = wm.getDefaultDisplay().getHeight();// 手机屏幕的高度
		mPopupWindowWidth = mPopupWindow.getWidth();
		mPopupWindowHeight = mPopupWindow.getHeight();
		// 显示键盘
		openKeyboard(new Handler(), 500);

	}

	/**
	 * 打开软键盘
	 */
	private void openKeyboard(Handler mHandler, int s) {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}, s);
	}

	/**
	 * 回复评论框的监听器
	 * */
	class EditChangedListener implements TextWatcher {
		private CharSequence temp;// 监听前的文本
		private int editStart;// 光标开始位置
		private int editEnd;// 光标结束位置

		// private final int charMaxNum = 10;

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
									  int after) {
			temp = s;
			context.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					// commentReplyBtn.setText("取消");
					// commentReplyBtn.invalidate();
				}
			});

			Log.v("lyj", "取消");
		}

		@Override
		public void onTextChanged(final CharSequence s, int start, int before,
								  int count) {
			if (s != null && !s.equals("")) {
				context.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						commentReplyBtn.setText("提交");
						commentReplyBtn.invalidate();
						Log.v("lyj", "提交");
					}
				});

			} else {
				commentReplyBtn.setText("取消");
				commentReplyBtn.invalidate();
			}
			Log.v("lyj", "提交.......");

		}

		@Override
		public void afterTextChanged(Editable s) {
			editStart = commentReplyEt.getSelectionStart();
			editEnd = commentReplyEt.getSelectionEnd();
			if (temp.length() == 0) {
				commentReplyBtn.setText("取消");
			}
		}
	};
}
