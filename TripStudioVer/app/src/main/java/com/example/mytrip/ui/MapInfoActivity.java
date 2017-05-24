package com.example.mytrip.ui;



import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
	private double mStLat = 0;// �ҵľ���
	private double mStLon = 0;// �ҵ�γ��
	private double mEtLat = 0;// �յ�ľ���
	private double mEtLon = 0;// �յ��γ��
	private String locaaddr;
	//
	private Button mPhoeNumRbtn;// ����绰
	private Button mNavigationRbtn;// ����
	private Button mRoutRbtn;// ·��

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_details);
		initView();
		initData();

	}

	private void initView() {
		mPlace = (TextView) findViewById(R.id.sd_tv_place);// �ص�����
		mDistance = (TextView) findViewById(R.id.sd_tv_address);// �ص�λ�ã�
		mNavigation = (Button) findViewById(R.id.sd_btn_navigation);// ������ť��
		mVista = (ImageView) findViewById(R.id.sd_iv_vista);// �־�ͼƬ��ʾ��
		mShopHours = (TextView) findViewById(R.id.sd_tv_shopHours);// Ӫҵʱ�䣻
		mTag = (TextView) findViewById(R.id.sd_tv_tag);// ��ǩ��
		mTelephone = (TextView) findViewById(R.id.sd_tv_telephone);// �绰��
		mType = (TextView) findViewById(R.id.sd_tv_type);// ���ͣ�
		mEnvironmentRating = (RatingBar) findViewById(R.id.sd_rb_environmentRating);// �ۺ��������ǿؼ���

		mPhoeNumRbtn = (Button) findViewById(R.id.phone_btn);// ����绰
		mPhoeNumRbtn.setOnClickListener(this);
		mNavigationRbtn = (Button) findViewById(R.id.sd_btn_navigation);// ����
		mNavigationRbtn.setOnClickListener(this);
		mRoutRbtn = (Button) findViewById(R.id.rout_btn);// ·�߹滮
		mRoutRbtn.setOnClickListener(this);
	}

	private void initData() {
		Intent intent = getIntent();// ������ͼ�õ�����
		mStLat = intent.getDoubleExtra("stLat", 0);
		mStLon = intent.getDoubleExtra("stLon", 0);
		mEtLat = intent.getDoubleExtra("etLat", 0);
		mEtLon = intent.getDoubleExtra("etLon", 0);

		locaaddr = intent.getStringExtra("addr");// �õ�����λ����Ϣ��
		name = intent.getStringExtra("name");// �õ������������֣�
		address = intent.getStringExtra("address");// �õ��������ĵ�ַ��
		shopHours = intent.getStringExtra("shopHours");// poi Ӫҵʱ��;
		tag = intent.getStringExtra("tag");// poi ��ǩ
		telephone = intent.getStringExtra("telephone");// poi �绰
		type = intent.getStringExtra("type");// poi ����
		detailUrl = intent.getStringExtra("address");// POI����URL��
		environmentRating = intent.getDoubleExtra("environmentRating", 0);// POI�������ۣ�
		mPlace.setText(name);// ����
		mDistance.setText(address);// λ�ã�
		mShopHours.setText("Ӫҵʱ��:" + shopHours);// Ӫҵʱ�䣻
		mTag.setText("��ǩ:" + tag);// ��ǩ��
		mTelephone.setText("�绰:" + telephone);// �绰��
		mType.setText("����:" + type);// ���ͣ�
		mEnvironmentRating.setRating((float) environmentRating);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.phone_btn:// ����绰
			telephone = mTelephone.getText().toString();
			Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+telephone));// �����tel
			MapInfoActivity.this.startActivity(intent);
			break;
		case R.id.sd_btn_navigation:// ����
			Intent intent2 = new Intent(this, MapNavigationActivity.class);
			intent2.putExtra("stLat", mStLat);// �ҵľ���
			intent2.putExtra("stlon", mStLon);// �ҵ�ά��
			intent2.putExtra("etlat", mEtLat);// �յ㾭��
			intent2.putExtra("etlon", mEtLon);// �յ�γ��
			startActivity(intent2);		
			break;
//		case R.id.rout_btn:// ·�߹滮
//			Intent intent3 = new Intent(this, MapNavigationActivity.class);
//			// intent3.putExtra("stLat", mStLat);//�ҵľ���
//			// intent3.putExtra("stlon", mStLon);//�ҵ�ά��
//			// intent3.putExtra("etlat", mEtLat);//�յ㾭��
//			// intent3.putExtra("etlon", mEtLon);//�յ�γ��
//			startActivity(intent3);
		default:
			break;
		}
		finish();
	}

}
