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
 * ��ӭ����
 * */
public class WelcomeActivity extends Activity implements OnClickListener{

	private RelativeLayout mBackGroundRl;//��������
	private ImageView mAnimIv;//Ҫ��̬���ŵ�ͼƬ	
    /**
     * activity����ڷ���
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ȥ��������
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //�����ָ���Ӧ��activity����һ������
        setContentView(R.layout.activity_welcome);
        //��ʼ���ؼ�
        initView();
        //���䶯��
        initTweenAnim();
        //֡����
        //initFrameAnim();
        //3����ת������ת
        intiIntentActivity();
}   
    /**
     * ��ʼ���ؼ�
     * */
    private void initView() {
		// TODO Auto-generated method stub
    	mBackGroundRl = (RelativeLayout) findViewById(R.id.rl_bg);
        mAnimIv = (ImageView) findViewById(R.id.iv_message);
        //���ڵ��ͼƬ��ת
        mAnimIv.setOnClickListener(this);
	}
	
	/**
     * ֡����
     * */
    private void initFrameAnim() {
		// TODO Auto-generated method stub
		//��һ��ͼƬ����Ϊ����
    	mAnimIv.setBackgroundResource(R.anim.welcome);
    	//������תΪ����
    	final AnimationDrawable animationDrawable=(AnimationDrawable) mAnimIv.getBackground();
	    //�ȽϺ�ʱ��Ҫ��һ�����߳�
    	mAnimIv.post(
    			new Runnable(){
	    	public void run(){
	    		//�����߳��п�������
	    		animationDrawable.start();
	    	}
	    }
    			    );
    }
	/**
     * ���䶯��
     */
    public void initTweenAnim(){
    	Animation animation = AnimationUtils.
    			loadAnimation(WelcomeActivity.this, R.anim.welcome_tween);
    	//�������䶯��
    	mBackGroundRl.startAnimation(animation);
    }
    /**
     * 3����ת
     * */
    Timer timer;
    private void intiIntentActivity() {
		// TODO Auto-generated method stub
		timer=new Timer();
		TimerTask task=new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//activity��ת��ʵ��
				skip();
				//��ת֮��ر�����activity
				finish();
			}
           
		};
		//task over
		timer.schedule(task, 3000);
	}
    /**
     * �����ת��ͬʱ�ر�timer
     * */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		
		case R.id.iv_message:
			//�رռ�ʱ��
			if(timer!=null){
				timer.cancel();
			}
			skip();
			//��ת֮��ر�����activity
			finish();
			break;
		default:
			break;
		}
	}
	 /**
     * ��ת
     * */
	public void skip() {
		Intent intent=new Intent();
		intent.setClass(WelcomeActivity.this, LoginActivity.class);
		startActivity(intent);
	}
	
	
}