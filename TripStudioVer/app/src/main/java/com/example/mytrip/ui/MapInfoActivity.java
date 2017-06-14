package com.example.mytrip.ui;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.mytrip.R;

public class MapInfoActivity extends Activity implements OnClickListener {

	private TextView mPlace;
	private TextView mDistance;
	private Button mNavigation;
	private ImageView mVista;
	private String name;
	private String address;
	private String shopHours;
	private String tag;
	private String telephone;
	private String type;
	private String detailUrl;
	private TextView mShopHours;
	private TextView mTag;
	private TextView mTelephone;
	private TextView mType;
	private double environmentRating;
	private RatingBar mEnvironmentRating;
	private double mStLat = 0;// 我的经度
	private double mStLon = 0;// 我的纬度
	private double mEtLat = 0;// 终点的经度
	private double mEtLon = 0;// 终点的纬度
	private String locaaddr;
	//
	private Button mPhoeNumRbtn;// 拨打电话
	private Button mNavigationRbtn;// 导航
	private Button mRoutRbtn;// 路线

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_details);
		initView();
		initData();

	}

	private void initView() {
		mPlace = (TextView) findViewById(R.id.sd_tv_place);// 地点名；
		mDistance = (TextView) findViewById(R.id.sd_tv_address);// 地点位置；
		mNavigation = (Button) findViewById(R.id.sd_btn_navigation);// 导航按钮；
		mVista = (ImageView) findViewById(R.id.sd_iv_vista);// 街景图片显示；
		mShopHours = (TextView) findViewById(R.id.sd_tv_shopHours);// 营业时间；
		mTag = (TextView) findViewById(R.id.sd_tv_tag);// 标签；
		mTelephone = (TextView) findViewById(R.id.sd_tv_telephone);// 电话；
		mType = (TextView) findViewById(R.id.sd_tv_type);// 类型；
		mEnvironmentRating = (RatingBar) findViewById(R.id.sd_rb_environmentRating);// 综合评价星星控件；

		mPhoeNumRbtn = (Button) findViewById(R.id.phone_btn);// 拨打电话
		mPhoeNumRbtn.setOnClickListener(this);
		mNavigationRbtn = (Button) findViewById(R.id.sd_btn_navigation);// 导航
		mNavigationRbtn.setOnClickListener(this);
		mRoutRbtn = (Button) findViewById(R.id.rout_btn);// 路线规划
		mRoutRbtn.setOnClickListener(this);
	}

	private void initData() {
		Intent intent = getIntent();// 声明意图得到对象；
		mStLat = intent.getDoubleExtra("stLat", 0);
		mStLon = intent.getDoubleExtra("stLon", 0);
		mEtLat = intent.getDoubleExtra("etLat", 0);
		mEtLon = intent.getDoubleExtra("etLon", 0);

		locaaddr = intent.getStringExtra("addr");// 得到地理位置信息；
		name = intent.getStringExtra("name");// 得到传过来的名字；
		address = intent.getStringExtra("address");// 得到传过来的地址；
		shopHours = intent.getStringExtra("shopHours");// poi 营业时间;
		tag = intent.getStringExtra("tag");// poi 标签
		telephone = intent.getStringExtra("telephone");// poi 电话
		type = intent.getStringExtra("type");// poi 类型
		detailUrl = intent.getStringExtra("address");// POI详情URL；
		environmentRating = intent.getDoubleExtra("environmentRating", 0);// POI环境评价；
		mPlace.setText(name);// 名字
		mDistance.setText(address);// 位置；
		mShopHours.setText("营业时间:" + shopHours);// 营业时间；
		mTag.setText("标签:" + tag);// 标签；
		mTelephone.setText("电话:" + telephone);// 电话；
		mType.setText("类型:" + type);// 类型；
		mEnvironmentRating.setRating((float) environmentRating);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.phone_btn:// 拨打电话
				telephone = mTelephone.getText().toString();
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + telephone));// 必须加tel
				if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
					// TODO: Consider calling
					//    ActivityCompat#requestPermissions
					// here to request the missing permissions, and then overriding
					//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
					//                                          int[] grantResults)
					// to handle the case where the user grants the permission. See the documentation
					// for ActivityCompat#requestPermissions for more details.
					return;
				}
				MapInfoActivity.this.startActivity(intent);
				break;
			case R.id.sd_btn_navigation:// 导航
				Intent intent2 = new Intent(this, MapNavigationActivity.class);
				intent2.putExtra("stLat", mStLat);// 我的经度
				intent2.putExtra("stlon", mStLon);// 我的维度
				intent2.putExtra("etlat", mEtLat);// 终点经度
				intent2.putExtra("etlon", mEtLon);// 终点纬度
				startActivity(intent2);
				break;
//		case R.id.rout_btn:// 路线规划
//			Intent intent3 = new Intent(this, MapNavigationActivity.class);
//			// intent3.putExtra("stLat", mStLat);//我的经度
//			// intent3.putExtra("stlon", mStLon);//我的维度
//			// intent3.putExtra("etlat", mEtLat);//终点经度
//			// intent3.putExtra("etlon", mEtLon);//终点纬度
//			startActivity(intent3);
			default:
				break;
		}
		finish();
	}

}
