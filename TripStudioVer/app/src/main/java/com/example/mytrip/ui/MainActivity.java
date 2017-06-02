package com.example.mytrip.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;


import com.example.mytrip.R;
import com.example.mytrip.ui.bmobdb.MyUser;
import com.example.mytrip.ui.footprint.FootPrintActivity;
import com.example.mytrip.ui.fragment.NearByFragment;
import com.example.mytrip.ui.fragment.NewsFragment;
import com.example.mytrip.ui.fragment.PostFragment;
import com.example.mytrip.ui.fragment.SightListFragment;
import com.example.mytrip.ui.fragment.StrategyFragment;
import com.example.mytrip.ui.slideactivity.ExerciseActivity;
import com.example.mytrip.view.XCRoundImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.slidingmenu.lib.SlidingMenu;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
//import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends FragmentActivity implements OnCheckedChangeListener,OnClickListener{//ע��˴����ð�

	private List<Fragment> fragments;
	private SightListFragment mMainFragment;
	private NewsFragment mNewsFragment;
	private PostFragment mPostFragment;
	private NearByFragment mNearByFragment;
	private RadioGroup mRG;
	private TextView mTitleTv;
	private ImageView mUserHeadIv;
	private View mUserHeadRl, mFootPrintRl, mCollectRl, mDownloadMap,
	    mExerciseRl, mSetRl, mFeedBackRl;
	
	
	private XCRoundImageView mLeftHeadIv;
	private Button mLoginBtn;
	private TextView mUserNameTv;
	private SlidingMenu mLeftMenu ;
	private Button mPostBtn;
	private long exitTime;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private DisplayImageOptions options;
	@Override
		protected void onCreate(Bundle arg0) {
			super.onCreate(arg0);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.activity_main);

			initView();
			
			slideOut();
			getUserInfo();
		}
        
		private void slideOut() {
			mLeftMenu = new SlidingMenu(this);

			mLeftMenu.setMode(SlidingMenu.LEFT);
			mLeftMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
			// menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
			mLeftMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
			mLeftMenu.setFadeDegree(1.0f);
			mLeftMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
			View view = LayoutInflater.from(this).inflate(R.layout.left_menu,null);
			mLeftMenu.setMenu(view);
//			mFeedBackRl=findViewById(R.id.rl_feed_back);
//			mFeedBackRl.setOnClickListener(this);
			mUserHeadRl=findViewById(R.id.rl_user_head);
		    mFootPrintRl=findViewById(R.id.rl_foot_print);
		    mCollectRl=findViewById(R.id.rl_collect);
		    mDownloadMap=findViewById(R.id.rl_download_map);
		    mExerciseRl=findViewById(R.id.rl_exercise);
		    mSetRl=findViewById(R.id.rl_set);
		    mFeedBackRl=findViewById(R.id.rl_feed_back);
		    mUserHeadRl.setOnClickListener(this);
		    mFootPrintRl.setOnClickListener(this);
		    mCollectRl.setOnClickListener(this);
		    mDownloadMap.setOnClickListener(this);
		    mExerciseRl.setOnClickListener(this);
		    mSetRl.setOnClickListener(this);
		    mFeedBackRl.setOnClickListener(this);
		    mLeftHeadIv=(XCRoundImageView) findViewById(R.id.iv_head_img);
		    mLeftHeadIv.setOnClickListener(this);
		    mUserHeadIv=(ImageView) findViewById(R.id.iv_user_head);
			mUserHeadIv.setOnClickListener(this);
			mLoginBtn=(Button) findViewById(R.id.btn_login_rightnow);
			mLoginBtn.setOnClickListener(this);
			mUserNameTv=(TextView) findViewById(R.id.tv_username);
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

	private void initView() {
		// TODO Auto-generated method stub
		
		fragments=new ArrayList<Fragment>();
		//mMainFragment=new StrategyFragment();
		mMainFragment=new SightListFragment();
		mNewsFragment=new NewsFragment();
		mPostFragment=new PostFragment();
		mNearByFragment=new NearByFragment();
		fragments.add(mMainFragment);
		fragments.add(mNewsFragment);
		fragments.add(mPostFragment);
		fragments.add(mNearByFragment);
		showFragment(R.id.frameLayout1,fragments.get(0));
		mRG=(RadioGroup) findViewById(R.id.rg_select);
		mRG.setOnCheckedChangeListener(this);
		mTitleTv=(TextView) findViewById(R.id.tv_title);
		mTitleTv.setText("");
//	         	mLeftMenu = (SlidingMenu) findViewById(R.id.id_menu);
		mPostBtn=(Button) findViewById(R.id.btn_post);
		mPostBtn.setOnClickListener(this);
		
	}

	public void getUserInfo(){
		MyUser mUser=BmobUser.getCurrentUser(MainActivity.this,MyUser.class);
		if(mUser!=null){
			mUserNameTv.setVisibility(View.VISIBLE);
			mLoginBtn.setVisibility(View.GONE);
			mUserNameTv.setText(mUser.getUsername());
			BmobFile avatarFile = mUser.getHeadImage();
			if (null != avatarFile) {
				imageLoader.displayImage(avatarFile.getFileUrl(this),mLeftHeadIv , options, animateFirstListener);
			}
		}else{
			mUserNameTv.setVisibility(View.GONE);
			mLoginBtn.setVisibility(View.VISIBLE);
		}
	}

	public void showFragment(int id,Fragment fragment) {
		FragmentManager manager=getSupportFragmentManager();
		FragmentTransaction transaction=manager.beginTransaction();
		transaction.replace(id,fragment);
		transaction.commit();
	}
	@Override
	public void onCheckedChanged(RadioGroup arg0, int id) {
		switch (id) {
		case R.id.rb_main:
			showFragment(R.id.frameLayout1, fragments.get(0));
			mUserHeadIv.setVisibility(View.VISIBLE);
			mTitleTv.setVisibility(View.VISIBLE);
			mTitleTv.setText("");
			mPostBtn.setVisibility(View.INVISIBLE);
			break;
		case R.id.rb_news:
			showFragment(R.id.frameLayout1, fragments.get(1));
			mUserHeadIv.setVisibility(View.GONE);
			mTitleTv.setVisibility(View.GONE);
			mPostBtn.setVisibility(View.GONE);
			break;
		case R.id.rb_search:
			BmobUser user=BmobUser.getCurrentUser(this);
			if(user!=null){
				showFragment(R.id.frameLayout1, fragments.get(2));
				mUserHeadIv.setVisibility(View.VISIBLE);
				mTitleTv.setVisibility(View.VISIBLE);
				mTitleTv.setText("");
				mPostBtn.setVisibility(View.VISIBLE);
			}else{
				Toast.makeText(this, "", Toast.LENGTH_LONG).show();
			}
			
			break;
		case R.id.rb_more:
			showFragment(R.id.frameLayout1, fragments.get(3));
			mUserHeadIv.setVisibility(View.GONE);
			mTitleTv.setVisibility(View.GONE);
			mPostBtn.setVisibility(View.GONE);
			break;
		
		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_user_head:
			mLeftMenu.toggle();
			break;
		case R.id.iv_head_img:
			Intent intentSet=new Intent(MainActivity.this,SetActivity.class);
			startActivity(intentSet);
			break;
		case R.id.rl_download_map:
			Intent intentMap=new Intent(MainActivity.this,MapOfflineActivity.class);
			startActivity(intentMap);
			break;
		case R.id.rl_exercise:
			Intent intent6=new Intent(MainActivity.this,ExerciseActivity.class);
			startActivity(intent6);
			break;
		case R.id.rl_set:
			Intent intent7=new Intent(MainActivity.this,SetActivity.class);
			startActivity(intent7);
			break;
		case R.id.rl_feed_back:
			BmobUser user=new BmobUser();
			if (user!=null) {
				//todo 报错 暂时注掉
//				Intent intent8=new Intent(MainActivity.this,FeedBackActivity.class);
//				startActivity(intent8);
			}else{
				Toast.makeText(this, "", Toast.LENGTH_LONG).show();
			}
			
			break;
		case R.id.btn_post:
			Intent intent1=new Intent(MainActivity.this,PublishPostActivity.class);
			startActivity(intent1);	
		    break;
		case R.id.rl_foot_print:
			Intent intent2=new Intent(MainActivity.this,FootPrintActivity.class);
			startActivity(intent2);
			break;
		case R.id.btn_login_rightnow:
			Intent intentLogin=new Intent(MainActivity.this,LoginActivity.class);
			startActivity(intentLogin);
			break;
		default:
			break;
		}
		
	}

	public void toggleMenu(View view)
	{
		mLeftMenu.toggle();
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
	
}
