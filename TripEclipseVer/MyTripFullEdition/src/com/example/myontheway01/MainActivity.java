package com.example.myontheway01;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;


import com.example.myontheway01.bmobdb.MyUser;
import com.example.myontheway01.footprint.FootPrintActivity;
import com.example.myontheway01.fragment.StrategyFragment;
import com.example.myontheway01.fragment.NearByFragment;
import com.example.myontheway01.fragment.NewsFragment;
import com.example.myontheway01.fragment.PostFragment;
import com.example.myontheway01.slideactivity.ExerciseActivity;
import com.example.myontheway01.view.XCRoundImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
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


/**
 * 主界面动态绑定fragment时，要继承FragmentActivity
 * */

public class MainActivity extends FragmentActivity implements OnCheckedChangeListener,OnClickListener{//注意此处倒得包
  //声明
	private List<Fragment> fragments;//该集合用来存放碎片
	private StrategyFragment mMainFragment;
	private NewsFragment mNewsFragment;
	private PostFragment mPostFragment;
	private NearByFragment mNearByFragment;
	private RadioGroup mRG;
	private TextView mTitleTv;
	private ImageView mUserHeadIv;//主界面的头像
	private View 
	    mUserHeadRl,//头像
	    mFootPrintRl,//脚印
	    mCollectRl,//收藏
	    mDownloadMap,//离线
	    mExerciseRl,//活动
	    mSetRl,//设置
	    mFeedBackRl;//反馈
	
	
	private XCRoundImageView mLeftHeadIv;//侧滑后的头像
	private Button mLoginBtn;//登录按钮
	private TextView mUserNameTv;//显示用户名
	private SlidingMenu mLeftMenu ;
	private Button mPostBtn;
	private long exitTime;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private DisplayImageOptions options;//设置图片访问参数
	@Override
		protected void onCreate(Bundle arg0) {
			// TODO Auto-generated method stub
			super.onCreate(arg0);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.activity_main);
			//初始化控件
			initView();
			
			slideOut();
			getUserInfo();
		}
        
		private void slideOut() {
			mLeftMenu = new SlidingMenu(this);
			// 菜单方向
			mLeftMenu.setMode(SlidingMenu.LEFT);
			// 设置滑动的屏幕范围
			mLeftMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);//靠近侧滑菜单的位置才可以滑动出侧滑菜单
			// menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);//滑动调出侧滑菜单的功能失效
			// 菜单距屏幕另一边的宽度
			mLeftMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
			// 滑动时的渐变程度
			mLeftMenu.setFadeDegree(1.0f);//设值为1.0f滑动开始时侧滑菜单完全隐藏，滑动过程中渐渐显示出来
			// 加在当前Activity
			mLeftMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
	                // 自定义布局，显示自己侧滑菜单要显示的部分
			View view = LayoutInflater.from(this).inflate(R.layout.left_menu,null);
			mLeftMenu.setMenu(view);
//			mFeedBackRl=findViewById(R.id.rl_feed_back);
//			mFeedBackRl.setOnClickListener(this);
			//侧滑布局
			mUserHeadRl=findViewById(R.id.rl_user_head);//头像
		    mFootPrintRl=findViewById(R.id.rl_foot_print);//脚印
		    mCollectRl=findViewById(R.id.rl_collect);//收藏
		    mDownloadMap=findViewById(R.id.rl_download_map);//离线
		    mExerciseRl=findViewById(R.id.rl_exercise);//活动
		    mSetRl=findViewById(R.id.rl_set);//设置
		    mFeedBackRl=findViewById(R.id.rl_feed_back);//反馈
		    mUserHeadRl.setOnClickListener(this);//头像
		    mFootPrintRl.setOnClickListener(this);//脚印
		    mCollectRl.setOnClickListener(this);//收藏
		    mDownloadMap.setOnClickListener(this);//离线
		    mExerciseRl.setOnClickListener(this);//活动
		    mSetRl.setOnClickListener(this);//设置
		    mFeedBackRl.setOnClickListener(this);//反馈
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
			//.displayer(new CircleBitmapDisplayer())//不加这个，为矩形图，加这个为圆形图
			.build();
	 }
	/**
	 * 初始化控件
	 * */
	private void initView() {
		// TODO Auto-generated method stub
		
		fragments=new ArrayList<Fragment>();
		//fragment可以直接new
		mMainFragment=new StrategyFragment();
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
		mTitleTv.setText("主页");
//	         	mLeftMenu = (SlidingMenu) findViewById(R.id.id_menu);
		mPostBtn=(Button) findViewById(R.id.btn_post);
		mPostBtn.setOnClickListener(this);
		
	}
	/**
	 * 获得用户信息
	 * */
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
	/**
	 * 显示fragment  第一个参数为显示到的组件的id,第二个参数为要显示的fragment
	 * */
	public void showFragment(int id,Fragment fragment) {
		FragmentManager manager=getSupportFragmentManager();//注意此处导入v4包
		FragmentTransaction transaction=manager.beginTransaction();
		transaction.replace(id,fragment);
		transaction.commit();
	}
	@Override
	public void onCheckedChanged(RadioGroup arg0, int id) {
		// TODO Auto-generated method stub
		switch (id) {
		case R.id.rb_main:
			showFragment(R.id.frameLayout1, fragments.get(0));
			mUserHeadIv.setVisibility(View.VISIBLE);
			mTitleTv.setVisibility(View.VISIBLE);
			mTitleTv.setText("主页");
			mPostBtn.setVisibility(View.INVISIBLE);
			break;
		case R.id.rb_news:
			showFragment(R.id.frameLayout1, fragments.get(1));
			//mTitleTv.setText("出行");
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
				mTitleTv.setText("动态");
				mPostBtn.setVisibility(View.VISIBLE);
			}else{
				Toast.makeText(this, "请先登录", Toast.LENGTH_LONG).show();
			}
			
			break;
		case R.id.rb_more:
			showFragment(R.id.frameLayout1, fragments.get(3));
			mUserHeadIv.setVisibility(View.GONE);
			mTitleTv.setVisibility(View.GONE);
			//mTitleTv.setText("附近");
			mPostBtn.setVisibility(View.GONE);
			break;
		
		default:
			break;
		}
	}
	/*
	 * 界面跳转
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_user_head://头像
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
		case R.id.rl_exercise://活动
			Intent intent6=new Intent(MainActivity.this,ExerciseActivity.class);
			startActivity(intent6);
			break;
		case R.id.rl_set://设置
			Intent intent7=new Intent(MainActivity.this,SetActivity.class);
			startActivity(intent7);
			break;
		case R.id.rl_feed_back://反馈
			BmobUser user=new BmobUser();
			if (user!=null) {
				Intent intent8=new Intent(MainActivity.this,FeedBackActivity.class);
				startActivity(intent8);
			}else{
				Toast.makeText(this, "请登录", Toast.LENGTH_LONG).show();
			}
			
			break;
		case R.id.btn_post://发表说说
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
	/**
	 * 侧滑
	 * */
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
