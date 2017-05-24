package com.example.myontheway01;
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
/**
 * 欢迎界面
 *1.实现了三秒跳转
 *2.若首次打开，进入导航，若非首次，直接进入主界面
 * */
public class PreWelcomeActivity extends Activity{
  private RelativeLayout mPreWelcome;
	@Override
   protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	//去掉标题
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	//绑定布局
	setContentView(R.layout.pre_welcome);
	mPreWelcome=(RelativeLayout) findViewById(R.id.rl_pre_welcome);
	initTweenAnim();
	intiIntentActivity();
	// 初始化 Bmob SDK
    // 使用时请将第二个参数Application ID替换成你在Bmob服务器端创建的Application ID
    Bmob.initialize(this, "eb780bd854e27fe6c598d56064c27f84");
	//注册#####
}
	/**
     * 补间动画
     */
    public void initTweenAnim(){
    	Animation animation = AnimationUtils.
    			loadAnimation(PreWelcomeActivity.this, R.anim.pre_welcome_tween);
    	//开启补间动画
    	mPreWelcome.startAnimation(animation);
    }
    /**
     * 3秒跳转，通过Timer和TimerTask实现
     * */
    private void intiIntentActivity() {
		// TODO Auto-generated method stub
		Timer timer=new Timer();
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
		//三秒后执行task;
		timer.schedule(task, 3000);
    }
    /**
     * 通过preferences1中的“isFirstRun”的TRUE或者false来选择跳转到导航或者主界面
     * */
    
	public void skip() {
		
		SharedPreferences preference1=this.getSharedPreferences("FirstRun", 0);
		 boolean isFirstRun=preference1.getBoolean ("isFirstRun", true);
		Editor editor=preference1.edit();
		if(isFirstRun){
			editor.putBoolean("isFirstRun", false);
			editor.commit();
			Intent intent=new Intent(PreWelcomeActivity.this, WelcomeActivity.class);
			startActivity(intent);	
		}else{
			Intent intent=new Intent(PreWelcomeActivity.this, MainActivity.class);
			startActivity(intent);
		}
		
	}
}
