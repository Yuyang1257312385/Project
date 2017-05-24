package com.example.mytrip.base;

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
 * */
public class MyApp extends Application {
	 private static MyApp myApp = null;
	@Override
    public void onCreate() {
    	SDKInitializer.initialize(getApplicationContext());
    	Bmob.initialize(this,EnviromentConfig.BMOB_KEY);
    	myApp=this;
    	if (false && Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyDialog().build());
			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyDeath().build());
		}
		
		initImageLoader(getApplicationContext());
    	super.onCreate();
    }
	 public static MyApp getInstance(){
			return myApp;
		}

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
