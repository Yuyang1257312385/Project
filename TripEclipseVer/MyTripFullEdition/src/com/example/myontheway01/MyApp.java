package com.example.myontheway01;

import cn.bmob.v3.Bmob;

import com.baidu.mapapi.SDKInitializer;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
/**
 * 环境的配置和初始化，其中放只需要执行一次且在四大组件
 * 执行之前执行的操作
 * */
public class MyApp extends Application {
	 private static MyApp myApp = null;
	@Override
    public void onCreate() {
    	SDKInitializer.initialize(getApplicationContext());
    	Bmob.initialize(this,"eb780bd854e27fe6c598d56064c27f84");
    	myApp=this;
    	if (false && Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyDialog().build());
			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyDeath().build());
		}
		
		initImageLoader(getApplicationContext());
    	super.onCreate();
    	//注册
    }
	 public static MyApp getInstance(){
			return myApp;
		}
	    /**
		 * 初始化IamgeLoader框架
		 * @param context
		 */
		public static void initImageLoader(Context context) {
			// This configuration tuning is custom. You can tune every option, you may tune some of them,
			// or you can create default configuration by
			//  ImageLoaderConfiguration.createDefault(this);
			// method.
			ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
					.threadPriority(Thread.NORM_PRIORITY - 2)
					.denyCacheImageMultipleSizesInMemory()
					.discCacheFileNameGenerator(new Md5FileNameGenerator())
					.tasksProcessingOrder(QueueProcessingType.LIFO)
					.writeDebugLogs() // Remove for release app
					.build();
			// Initialize ImageLoader with configuration.
			ImageLoader.getInstance().init(config);
		}
}
