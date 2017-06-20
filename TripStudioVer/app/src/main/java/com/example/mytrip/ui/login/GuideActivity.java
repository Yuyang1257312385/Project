package com.example.mytrip.ui.login;

import java.util.ArrayList;
import java.util.List;

import com.example.mytrip.R;
import com.example.mytrip.base.BaseActivity;
import com.example.mytrip.ui.MainActivity;
import com.example.mytrip.view.View1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;

/**
 * 首次启动引导页
 *
 */
public class GuideActivity extends BaseActivity implements OnPageChangeListener,OnClickListener{

	private ViewPager mGuideVp;
	private List<View> mData;
	private Button mStartBtn;
	private GuideAdapter adapter;
	private View[] mPoints;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);
		initView();
		initData();
		initPoint();
	}

	private void initView() {
		mGuideVp = (ViewPager) findViewById(R.id.vp_welcome);
		mStartBtn = (Button) findViewById(R.id.btn_start);
		mStartBtn.setOnClickListener(this);
		mGuideVp.setOnPageChangeListener(this);
		mData = new ArrayList<View>();
		adapter = new GuideAdapter(this);
		mGuideVp.setAdapter(adapter);
	}

	private void initData() {
		View1 v1 = new View1(this, R.drawable.w1);
		View1 v3 = new View1(this, R.drawable.w3);
		View1 v4 = new View1(this, R.drawable.w4);
		mData.add(v1.v);
		mData.add(v3.v);
		mData.add(v4.v);
		adapter.setmData(mData);
		adapter.notifyDataSetChanged();
	}

	private void initPoint() {
		ViewGroup viewGroup = (ViewGroup) findViewById(R.id.ll_point);
		mPoints = new View[mData.size()];
		for (int i = 0; i < mPoints.length; i++) {
			View view = new View(this);
			view.setLayoutParams(new LayoutParams(25, 25));
			mPoints[i] = view;
			if (i == 0) {
				view.setBackgroundResource(R.drawable.red_heart);
			} else {
				view.setBackgroundResource(R.drawable.white_heart);
			}
			viewGroup.addView(view);
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}


	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}


	@Override
	public void onPageSelected(int position) {
		if (mPoints != null && mPoints.length > 0) {
			for (int i = 0; i < mPoints.length; i++) {
				if (position == i) {
					mPoints[i].setBackgroundResource(R.drawable.red_heart);
					if (position == 2) {
						mStartBtn.setVisibility(View.VISIBLE);
						mStartBtn.setBackgroundResource(R.drawable.wel_btn_bg_off);
					}
				} else {
					mPoints[i].setBackgroundResource(R.drawable.white_heart);
					mStartBtn.setVisibility(View.GONE);
				}
			}
		}
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_start:
				mStartBtn.setBackgroundResource(R.drawable.wel_btn_bg_on);
				Intent intent = new Intent(GuideActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
				break;

			default:
				break;
		}
	}
	
}
