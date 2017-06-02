package com.example.mytrip.ui.login;
import java.util.Timer;
import java.util.TimerTask;

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
import com.example.mytrip.base.ActBase;
import com.example.mytrip.base.Constant;
import com.example.mytrip.ui.BaiduSearch;
import com.example.mytrip.ui.MainActivity;
import com.example.mytrip.ui.TestSearchAct;


/**
 * 启动页
 *
 *
 *
 */
public class ActLaunch extends ActBase{
  private RelativeLayout mPreWelcome;
	@Override
   protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.act_launch);
	mPreWelcome=(RelativeLayout) findViewById(R.id.rl_pre_welcome);
	initTweenAnim();
	intiIntentActivity();
}

    public void initTweenAnim(){
    	Animation animation = AnimationUtils.
    			loadAnimation(ActLaunch.this, R.anim.pre_welcome_tween);
    	mPreWelcome.startAnimation(animation);
    }

    private void intiIntentActivity() {
		Timer timer=new Timer();
		TimerTask task=new TimerTask() {
			@Override
			public void run() {
				skip();
				finish();
			}
           
		};
		timer.schedule(task, 3000);
    }

	public void skip() {

		boolean isFirstRun=spUtils.getBoolean (Constant.SP_FIRST_RUN, true);
		if(isFirstRun){
			spUtils.putBoolean(Constant.SP_FIRST_RUN,false);
			Intent intent=new Intent(ActLaunch.this, ActGuide.class);
			startActivity(intent);	
		}else{
			Intent intent=new Intent(ActLaunch.this, MainActivity.class);
			startActivity(intent);
		}
		
	}
}
