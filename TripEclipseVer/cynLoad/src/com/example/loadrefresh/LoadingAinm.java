package com.example.loadrefresh;
import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class LoadingAinm {
	/**
	 * 实现一个数据加载前的一个预加载的动画效果
	 * @param view
	 */
	public static void ininLodingView(View view) {
		
		ImageView loadingImageView = (ImageView) view
				.findViewById(R.id.lodding);
		TextView loadingTextView = (TextView) view
				.findViewById(R.id.lodiing_text);
		loadingImageView.setBackgroundResource(R.anim.lodding);
		final AnimationDrawable animationDrawable = (AnimationDrawable) loadingImageView
				.getBackground();
		loadingImageView.post(new Runnable() {
			@Override
			public void run() {
				animationDrawable.start();
			}
		});
	}

	public static void ininLoding(Activity activity) {
		ImageView loadingImageView = (ImageView) activity
				.findViewById(R.id.lodding);
		TextView loadingTextView = (TextView) activity
				.findViewById(R.id.lodiing_text);
		loadingImageView.setBackgroundResource(R.anim.lodding);
		final AnimationDrawable animationDrawable = (AnimationDrawable) loadingImageView
				.getBackground();
		loadingImageView.post(new Runnable() {
			@Override
			public void run() {
				animationDrawable.start();
			}
		});
	}
}
