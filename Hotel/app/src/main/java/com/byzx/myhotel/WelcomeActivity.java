package com.byzx.myhotel;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
/**
 * 欢迎界面
 * */
public class WelcomeActivity extends Activity implements OnClickListener{

	private RelativeLayout mBackGroundRl;//背景布局
	private ImageView mAnimIv;//要动态播放的图片
	/**
	 * activity的入口方法
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去掉标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//将布局跟对应的activity进行一个关联
		setContentView(R.layout.activity_welcome);
		//初始化控件
		initView();
		//补间动画
		initTweenAnim();
		//帧动画
		//initFrameAnim();
		//3秒跳转或点击跳转
		intiIntentActivity();
	}
	/**
	 * 初始化控件
	 * */
	private void initView() {
		// TODO Auto-generated method stub
		mBackGroundRl = (RelativeLayout) findViewById(R.id.rl_bg);
		mAnimIv = (ImageView) findViewById(R.id.iv_message);
		//用于点击图片跳转
		mAnimIv.setOnClickListener(this);
	}

	/**
	 * 帧动画
	 * */
	private void initFrameAnim() {
		// TODO Auto-generated method stub
		//将一组图片设置为背景
		mAnimIv.setBackgroundResource(R.anim.welcome);
		//将背景转为动画
		final AnimationDrawable animationDrawable=(AnimationDrawable) mAnimIv.getBackground();
		//比较耗时的要开一个新线程
		mAnimIv.post(
				new Runnable(){
					public void run(){
						//在新线程中开启动画
						animationDrawable.start();
					}
				}
		);
	}
	/**
	 * 补间动画
	 */
	public void initTweenAnim(){
		Animation animation = AnimationUtils.
				loadAnimation(WelcomeActivity.this, R.anim.welcome_tween);
		//开启补间动画
		mBackGroundRl.startAnimation(animation);
	}
	/**
	 * 3秒跳转
	 * */
	Timer timer;
	private void intiIntentActivity() {
		// TODO Auto-generated method stub
		timer=new Timer();
		TimerTask task=new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//activity跳转的实现
				skip();
				//跳转之后关闭自身activity
				finish();
			}

		};
		//task over
		timer.schedule(task, 3000);
	}
	/**
	 * 点击跳转，同时关闭timer
	 * */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

			case R.id.iv_message:
				//关闭计时器
				if(timer!=null){
					timer.cancel();
				}
				skip();
				//跳转之后关闭自身activity
				finish();
				break;
			default:
				break;
		}
	}
	/**
	 * 跳转
	 * */
	public void skip() {
		Intent intent=new Intent();
		intent.setClass(WelcomeActivity.this, LoginActivity.class);
		startActivity(intent);
	}


}