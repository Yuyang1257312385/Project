package com.example.mytrip.ui;
import java.util.Timer;
import java.util.TimerTask;
import cn.bmob.v3.Bmob;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.example.mytrip.R;
import com.example.mytrip.ui.login.ActGuide;

/**
 * ��ӭ����
 *1.ʵ����������ת
 *2.���״δ򿪣����뵼���������״Σ�ֱ�ӽ���������
 * */
public class PreWelcomeActivity extends Activity{
  private RelativeLayout mPreWelcome;
	@Override
   protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	//ȥ������
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	//�󶨲���
	setContentView(R.layout.pre_welcome);
	mPreWelcome=(RelativeLayout) findViewById(R.id.rl_pre_welcome);
	initTweenAnim();
	intiIntentActivity();
	// ��ʼ�� Bmob SDK
    // ʹ��ʱ�뽫�ڶ�������Application ID�滻������Bmob�������˴�����Application ID
    Bmob.initialize(this, "eb780bd854e27fe6c598d56064c27f84");
	//ע��#####
}
	/**
     * ���䶯��
     */
    public void initTweenAnim(){
    	Animation animation = AnimationUtils.
    			loadAnimation(PreWelcomeActivity.this, R.anim.pre_welcome_tween);
    	//�������䶯��
    	mPreWelcome.startAnimation(animation);
    }
    /**
     * 3����ת��ͨ��Timer��TimerTaskʵ��
     * */
    private void intiIntentActivity() {
		// TODO Auto-generated method stub
		Timer timer=new Timer();
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
		//�����ִ��task;
		timer.schedule(task, 3000);
    }
    /**
     * ͨ��preferences1�еġ�isFirstRun����TRUE����false��ѡ����ת����������������
     * */
    
	public void skip() {
		
		SharedPreferences preference1=this.getSharedPreferences("FirstRun", 0);
		 boolean isFirstRun=preference1.getBoolean ("isFirstRun", true);
		Editor editor=preference1.edit();
		if(isFirstRun){
			editor.putBoolean("isFirstRun", false);
			editor.commit();
			Intent intent=new Intent(PreWelcomeActivity.this, ActGuide.class);
			startActivity(intent);	
		}else{
			Intent intent=new Intent(PreWelcomeActivity.this, MainActivity.class);
			startActivity(intent);
		}
		
	}
}
