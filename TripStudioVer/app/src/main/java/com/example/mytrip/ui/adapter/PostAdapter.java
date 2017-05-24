package com.example.mytrip.ui.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

import com.example.mytrip.R;
import com.example.mytrip.ui.bean.LikesBean;
import com.example.mytrip.ui.bean.PostBean;
import com.example.mytrip.ui.bmobdb.Comments;
import com.example.mytrip.ui.bmobdb.Likes;
import com.example.mytrip.ui.bmobdb.Post;
import com.example.mytrip.view.MyListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

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
	private LayoutInflater inflater;
	private Activity context;
	private List<PostBean> postList;
	private int expandPosition = -1;

	private CommentAdapter commentAdapter;
	private PopupWindow mPopupWindow;
	private int mScreenWidth;
	private int mScreenHeight;
	private int mPopupWindowWidth;
	private int mPopupWindowHeight;
	String replyTo = "";
	String replyContent = "";
	Post post1 = new Post();
	private EditText commentReplyEt;
	private Button commentReplyBtn;
	//==============
	//private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private ImageLoadingListener animateFirstListener = new ImageLoadingListener() {
		@Override
		public void onLoadingStarted(String s, View view) {

		}

		@Override
		public void onLoadingFailed(String s, View view, FailReason failReason) {

		}

		@Override
		public void onLoadingComplete(String s, View view, Bitmap bitmap) {

		}

		@Override
		public void onLoadingCancelled(String s, View view) {

		}
	};
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private DisplayImageOptions options;

	//===========================
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
				//.displayer(new CircleBitmapDisplayer())
				.build();
	}

	public void setPostList(List<PostBean> postList) {
		this.postList = postList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return postList != null ? postList.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return postList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
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
			viewHolder.postLikeClickTv = (TextView) convertView
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
			viewHolder.postImage = (ImageView) convertView.findViewById(R.id.iv_post_img);
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
			imageLoader.displayImage(postList.get(position).getPostImageUrl().getFileUrl(context) == null ? ""
					: postList.get(position).getPostImageUrl().getFileUrl(
					context), viewHolder.postImage, options, animateFirstListener);
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
		//// TODO: 2017/5/24
		//viewHolder.postLikeClickTv.setOnClickListener(new ClickOrder(position, viewHolder));
		viewHolder.postLikeClickTv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

		String postLikeStr = "";

		if (postList.get(position).getPostLikes() != null) {
//			viewHolder.postLikeBtn.setTag(postList.get(position).getPostLikes()!=null);
//			
//				for(LikesBean likesBean:postList.get(position).getPostLikes()){
//					BmobUser bUser=BmobUser.getCurrentUser(context);
//					//if(postList.get(position).getPostLikes().equals(viewHolder.postLikeBtn.getTag())){
//					if(likesBean.getLikeAuthorId().equals(bUser.getObjectId())){
//		                viewHolder.postLikeBtn.setText("ȡ��");    
//						//break;
//					}else{
//						viewHolder.postLikeBtn.setText("����");
//					}
//					//}
//				}
//			


			for (LikesBean likesBean : postList.get(position).getPostLikes()) {
				postLikeStr += likesBean.getLikeAuthorName() + ",";
				BmobUser bUser = BmobUser.getCurrentUser(context);
				//if(postList.get(position).getPostLikes().equals(viewHolder.postLikeBtn.getTag())){
				if (likesBean.getLikeAuthorId().equals(bUser.getObjectId())) {
					viewHolder.postLikeClickTv.setText("0");
					//System.out.println(likesBean.getLikeAuthorId()+"======"+likesBean.getLikeAuthorName());
					//break;
				} else {
					viewHolder.postLikeClickTv.setText("1");
				}
			}

		}
		if (!postLikeStr.equals("") && postLikeStr != null) {
			viewHolder.postLikesTv.setText(postLikeStr + "sss");

		} else {
			viewHolder.postLikesTv.setText("");

		}

		viewHolder.postCommentIv.setOnClickListener(new ClickOrder(position, viewHolder));
		viewHolder.postCommentSubmitBtn.setOnClickListener(new ClickOrder(
				position, viewHolder));
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
						//getPopupWindowInstance(position, viewHolder);

						mPopupWindow.showAtLocation(v, Gravity.TOP, 0, 200);

//						int[] location = new int[2];  
//				        v.getLocationOnScreen(location);  			          
//				        mPopupWindow.showAtLocation(v,
//				        		Gravity.NO_GRAVITY, location[0], 
//				        		location[1]-mPopupWindow.getHeight());
						commentReplyEt.setHint("�ظ� " + replyTo);
						commentReplyBtn.setText("ȡ��");
						commentAdapter.notifyDataSetChanged();
					}
				});
		commentAdapter.notifyDataSetChanged();

		return convertView;

	}


	class ClickOrder implements OnClickListener {
		public int position;
		public ViewHolder viewHolder;

		public ClickOrder(int position, ViewHolder viewHolder) {
			this.position = position;
			this.viewHolder = viewHolder;
		}

		@Override
		public void onClick(View v) {

			switch (v.getId()) {
				case R.id.iv_post_comment:
					if (expandPosition == position) {
						expandPosition = -1;
					} else {
						expandPosition = position;
					}
					notifyDataSetChanged();
					break;
				case R.id.btn_comment_submit:
					BmobUser commentAuthor = BmobUser.getCurrentUser(context);
					String commentContent = viewHolder.postCommentEt.getText()
							.toString().trim();
					Post post = new Post();
					post.setObjectId(postList.get(position).getPostId());
					Comments comments = new Comments();
					comments.setCommentAuthor(commentAuthor);
					comments.setPost(post);
					comments.setCommentContent(commentContent);
					comments.save(context, new SaveListener() {
						@Override
						public void onSuccess() {
							Toast.makeText(context, "���۳ɹ�", Toast.LENGTH_LONG)
									.show();
							Intent mIntent = new Intent("com.example.myontheway01");
							context.sendBroadcast(mIntent);
						}

						@Override
						public void onFailure(int code, String msg) {
							Toast.makeText(context, "����ʧ��", Toast.LENGTH_LONG)
									.show();
							Intent mIntent = new Intent("com.example.myontheway01");
							context.sendBroadcast(mIntent);
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
					if (commentReplyBtn.getText().equals("�ύ")) {
						BmobUser replyAuthor = BmobUser.getCurrentUser(context);
						replyContent = commentReplyEt.getText().toString().trim();
						Comments replyComment = new Comments();
						replyComment.setCommentAuthor(replyAuthor);
						replyComment.setPost(post1);
						replyComment.setCommentContent(replyContent);
						replyComment.setReplyTo(replyTo);
						replyComment.save(context, new SaveListener() {
							@Override
							public void onSuccess() {
								Toast.makeText(context, "���۳ɹ�", Toast.LENGTH_LONG)
										.show();
								commentReplyEt.setText("");
								Intent mIntent = new Intent(
										"com.example.myontheway01");
								context.sendBroadcast(mIntent);
							}

							@Override
							public void onFailure(int code, String msg) {
								Toast.makeText(context, "����ʧ��", Toast.LENGTH_LONG)
										.show();
								commentReplyEt.setText("");
								Intent mIntent = new Intent(
										"com.example.myontheway01");
								context.sendBroadcast(mIntent);
							}
						});

						mPopupWindow.dismiss();
					} else if (commentReplyBtn.getText().equals("ȡ��")) {
						Toast.makeText(context, "ȡ���ظ�", Toast.LENGTH_LONG).show();
						mPopupWindow.dismiss();
					}
					break;
				case R.id.tv_like_or_not:
					if (viewHolder.postLikeClickTv.getText().equals("1")) {
						BmobUser user = BmobUser.getCurrentUser(context);
						Post post1 = new Post();
						post1.setObjectId(postList.get(position).getPostId());
						final Likes likes = new Likes();
						likes.setLikeAuthor(user);
						likes.setPost(post1);
						likes.save(context, new SaveListener() {

							@Override
							public void onSuccess() {
								Toast.makeText(context, "���޳ɹ�", Toast.LENGTH_LONG).show();
								// viewHolder.postLikeBtn.setText("ȡ��");
								Intent mIntent = new Intent(
										"com.example.myontheway01");// ͨ��ָ�����͵�Ƶ��
								context.sendBroadcast(mIntent);
							}

							@Override
							public void onFailure(int code, String msg) {
								Toast.makeText(context, "����ʧ��", Toast.LENGTH_LONG).show();
								Intent mIntent = new Intent(
										"com.example.myontheway01");// ͨ��ָ�����͵�Ƶ��
								context.sendBroadcast(mIntent);
							}
						});

					} else if (viewHolder.postLikeClickTv.getText().equals("0")) {

						BmobUser bUser = BmobUser.getCurrentUser(context);
						String postId = postList.get(position).getPostId();
						getLikesId(bUser, postId);
					}
					break;
				default:
					break;
			}
		}

		/**
		 * */
		private void getLikesId(BmobUser bUser, String postId) {


			BmobQuery<Likes> eq1 = new BmobQuery<Likes>();
			BmobUser bmobUser = new BmobUser();
			bmobUser.setObjectId(bUser.getObjectId());
			eq1.addWhereEqualTo("likeAuthor", bmobUser);
			BmobQuery<Likes> eq2 = new BmobQuery<Likes>();
			Post post = new Post();
			post.setObjectId(postId);
			eq2.addWhereEqualTo("post", post);
			//�����װ������and����
			List<BmobQuery<Likes>> andQuerys = new ArrayList<BmobQuery<Likes>>();
			andQuerys.add(eq1);
			andQuerys.add(eq2);
			BmobQuery<Likes> query = new BmobQuery<Likes>();
			query.and(andQuerys);
			query.findObjects(context, new FindListener<Likes>() {
				@Override
				public void onSuccess(List<Likes> object) {
					// TODO Auto-generated method stub
					String likesId = "";
					for (Likes likes : object) {
						likesId = likes.getObjectId();
					}
					deleteLikesById(likesId);
				}

				@Override
				public void onError(int code, String msg) {
					Toast.makeText(context, "��ѯʧ��", Toast.LENGTH_LONG).show();

				}
			});
		}

		/**
		 * */
		public void deleteLikesById(String likesId) {
			Likes likes = new Likes();
			likes.setObjectId(likesId);
			likes.delete(context, new DeleteListener() {

				@Override
				public void onSuccess() {
					// TODO Auto-generated method stub
					Toast.makeText(context, "ȡ���ɹ�", Toast.LENGTH_LONG).show();
					Intent mIntent = new Intent(
							"com.example.myontheway01");
					context.sendBroadcast(mIntent);

				}

				@Override
				public void onFailure(int code, String msg) {
					// TODO Auto-generated method stub
					Toast.makeText(context, "ȡ��ʧ��", Toast.LENGTH_LONG).show();
					Intent mIntent = new Intent(
							"com.example.myontheway01");
					context.sendBroadcast(mIntent);
				}
			});
		}
	}
	/**
	 * */
	public class ViewHolder {
		// private ImageView userHeadIv;
		private TextView userNameTv;
		private TextView postTimeTv;
		private TextView postContentTv;
		// private ImageView postIv;
		private TextView postLikeClickTv;
		private TextView postLikesTv;
		private ImageView postCommentIv;
		private EditText postCommentEt;
		private Button postCommentSubmitBtn;
		private MyListView postCommentsLv;
		private View CommentView;
		private ImageView postImage;

		private  class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

			 final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

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







		private void getPopupWindowInstance(int position, ViewHolder viewHolder) {
			if (null != mPopupWindow) {
				mPopupWindow.dismiss();
				return;
			} else {
				initPopuptWindow(position, viewHolder);
				mPopupWindow.setFocusable(true);
				commentReplyEt.setFocusableInTouchMode(true);
				commentReplyEt.requestFocus();
				commentReplyEt.findFocus();
				openKeyboard(new Handler(), 500);
			}
		}

		private void initPopuptWindow(int position, ViewHolder viewHolder) {
			LayoutInflater layoutInflater = LayoutInflater.from(context);
			View popupWindow = layoutInflater.inflate(R.layout.popup_reply_comment,
					null);
			commentReplyBtn = (Button) popupWindow
					.findViewById(R.id.btn_reply_comment);
			commentReplyEt = (EditText) popupWindow
					.findViewById(R.id.et_reply_comment);
			//commentReplyEt.setFocusable(false);// Ĭ���޽���
			commentReplyBtn.setOnClickListener(new ClickOrder(position, viewHolder));
			commentReplyEt.addTextChangedListener(new EditChangedListener());
			commentReplyEt.setOnEditorActionListener(new OnEditorActionListener() {
				@Override
				public boolean onEditorAction(TextView v, int actionId,
											  KeyEvent event) {
					Toast.makeText(context, "��ɼ������ɹ�", Toast.LENGTH_LONG).show();
					((InputMethodManager) context
							.getSystemService(Context.INPUT_METHOD_SERVICE))
							.hideSoftInputFromWindow(
									commentReplyEt.getWindowToken(), 0);
					return true;
					// do sth when pressed the enter key in the Soft InputMethod

				}
			});


			mPopupWindow = new PopupWindow(popupWindow, LayoutParams.MATCH_PARENT,
					150);
			WindowManager wm = (WindowManager) context
					.getSystemService(Context.WINDOW_SERVICE);
			mScreenWidth = wm.getDefaultDisplay().getWidth();
			mScreenWidth = wm.getDefaultDisplay().getHeight();
			mPopupWindowWidth = mPopupWindow.getWidth();
			mPopupWindowHeight = mPopupWindow.getHeight();

			openKeyboard(new Handler(), 500);

		}

		/**
		 *
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
		 *
		 * */
		class EditChangedListener implements TextWatcher {
			private CharSequence temp;
			private int editStart;
			private int editEnd;

			// private final int charMaxNum = 10;

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
										  int after) {
				temp = s;
				context.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						// commentReplyBtn.setText("ȡ��");
						// commentReplyBtn.invalidate();
					}
				});

				Log.v("lyj", "ȡ��");
			}

			@Override
			public void onTextChanged(final CharSequence s, int start, int before,
									  int count) {
				if (s != null && !s.equals("")) {
					context.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							commentReplyBtn.setText("�ύ");
							commentReplyBtn.invalidate();
							Log.v("lyj", "�ύ");
						}
					});

				} else {
					commentReplyBtn.setText("ȡ��");
					commentReplyBtn.invalidate();
				}
				Log.v("lyj", "�ύ.......");

			}

			@Override
			public void afterTextChanged(Editable s) {
				editStart = commentReplyEt.getSelectionStart();
				editEnd = commentReplyEt.getSelectionEnd();
				if (temp.length() == 0) {
					commentReplyBtn.setText("ȡ��");
				}
			}
		}

		;
	}
}