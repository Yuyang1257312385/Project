package com.example.mytrip.ui;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.example.mytrip.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MapActivity extends Activity implements
		OnGetPoiSearchResultListener {

	private InfoWindow mInfoWindow;
	private PopupWindow mPopupWindow;

	// ��λ���ȡ�ľ�γ��
	private double mStLat = 0;// �ҵľ���
	private double mStLon = 0;// �ҵ�γ��
	private double mEtLat = 0;// �յ�ľ���
	private double mEtLon = 0;// �յ��γ��

	// �������
	TextView mName;
	String name;
	// �绰����
	TextView mPhone;
	String number;
	// ��õ�ַ
	TextView mAdds;
	String adds;
	View v;
	String detailUrl;
	String shopHours;
	String tag;
	String type;
	double environmentRating;
	double facilityRating;
	private LayoutInflater mInflater;// ������
	// ��λ���
	LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();
	private LocationMode mCurrentMode;
	BitmapDescriptor mCurrentMarker;
	private PoiSearch mPoiSearch = null;
	MapView mMapView;
	BaiduMap mBaiduMap = null;
	private String locaaddr;
	// UI���
	OnCheckedChangeListener radioButtonListener;
	Button requestLocButton;
	boolean isFirstLoc = true;// �Ƿ��״ζ�λ

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ��ʹ��SDK�����֮ǰ��ʼ��context��Ϣ������ApplicationContext
		// ע��÷���Ҫ��setContentView����֮ǰʵ��
		// SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_map);
		mInflater = LayoutInflater.from(this);

		// ��ȡ��ͼ�ؼ�����
		mMapView = (MapView) findViewById(R.id.bmapView);
		requestLocButton = (Button) findViewById(R.id.button1);
		mCurrentMode = LocationMode.NORMAL;
		requestLocButton.setText("��ͨ");
		OnClickListener btnClickListener = new OnClickListener() {
			public void onClick(View v) {
				switch (mCurrentMode) {
				case NORMAL:
					requestLocButton.setText("����");
					mCurrentMode = LocationMode.FOLLOWING;
					mBaiduMap
							.setMyLocationConfigeration(new MyLocationConfiguration(
									mCurrentMode, true, mCurrentMarker));
					break;
				case COMPASS:
					requestLocButton.setText("��ͨ");
					mCurrentMode = LocationMode.NORMAL;
					mBaiduMap
							.setMyLocationConfigeration(new MyLocationConfiguration(
									mCurrentMode, true, mCurrentMarker));
					break;
				case FOLLOWING:
					requestLocButton.setText("����");
					mCurrentMode = LocationMode.COMPASS;
					mBaiduMap
							.setMyLocationConfigeration(new MyLocationConfiguration(
									mCurrentMode, true, mCurrentMarker));
					break;
				}
			}
		};
		requestLocButton.setOnClickListener(btnClickListener);
		// ��ͼ��ʼ��
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		// ������λͼ��
		mBaiduMap.setMyLocationEnabled(true);
		// ��λ��ʼ��
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// ��gps
		option.setCoorType("bd09ll"); // ������������
		option.setScanSpan(1000);
		mLocClient.setLocOption(option);
		mLocClient.start();
		// �ܱ�����ע�������
		// ��ʼ������ģ�飬ע�������¼�����
		mPoiSearch = PoiSearch.newInstance();
		mPoiSearch.setOnGetPoiSearchResultListener(this);
	}

	/**
	 * Ӱ��������ť����¼�
	 * 
	 * @param v
	 */
	public void searchButtonProcess(LatLng lng) {
		Intent intent = getIntent();
		String content = intent.getStringExtra("whichItem");
		// �Ѹ����������ܱ�
		// mPoiSearch.searchNearby((new PoiNearbySearchOption())
		// .keyword("����")//����
		// .location(null)//��ǰ����
		// .pageCapacity(20)//ÿҳ��ʾ������
		// .pageNum(1)//֧ȡһҳ����
		// .radius(1000));//�뾶
		// ��ȫ��
		// mPoiSearch.searchInCity((new PoiCitySearchOption())
		// .city("����")
		// .keyword("��ӰԺ")
		// .pageNum(1));
		// ���ܱ�
		// Ŀ��ҽԺγ��
		// float Latitude = 34.224647f;
		// // Ŀ��ҽԺ����
		// float Longitude = 108.943240f;
		// LatLng latLng = new LatLng(Latitude, Longitude);
		mPoiSearch.searchNearby((new PoiNearbySearchOption().keyword(content)
				.radius(2000).pageNum(1).location(lng)));

	}

	/*
	 * ��λSDK��������
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view ���ٺ��ڴ����½��յ�λ��
			if (location == null || mMapView == null)
				return;
			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// �˴����ÿ����߻�ȡ���ķ�����Ϣ��˳ʱ��0-360
					.direction(100).latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();

			// �Լ���ǰ�����꡾λ�á�
			LatLng ll = new LatLng(location.getLatitude(),
					location.getLongitude());
			if (isFirstLoc) {
				isFirstLoc = false;
				mBaiduMap.setMyLocationData(locData);
				// �Լ���ǰ�����꡾λ�á�
				LatLng ll2 = new LatLng(location.getLatitude(),
						location.getLongitude());
				/*
				 * ���ַ�ʽ��ȡ�Լ��ľ�γ��
				 */
				// ll2.latitude;
				// ��ö�λ����㾭γ��
				mStLat = location.getLatitude();
				mStLon = location.getLongitude();
				// �����ܱ�
				searchButtonProcess(ll2);
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll2);
				mBaiduMap.animateMapStatus(u);

			}

		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}

	@Override
	protected void onPause() {
		mMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		super.onResume();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	protected void onDestroy() {
		// �˳�ʱ���ٶ�λ
		mLocClient.stop();
		mPoiSearch.destroy();
		// �رն�λͼ��
		mBaiduMap.setMyLocationEnabled(false);
		mMapView.onDestroy();
		mMapView = null;

		super.onDestroy();
	}

	// �����С������ʱ�ص��˷���

	@Override
	public void onGetPoiDetailResult(PoiDetailResult result) {

		// ������Ϣ

		shopHours = result.getShopHours();// poi Ӫҵʱ��;
		tag = result.getTag();// poi ��ǩ
		type = result.getType();// poi ����
		environmentRating = result.getEnvironmentRating();// POI�������ۣ�
		facilityRating = result.getFacilityRating();// POI�豸���ۣ�

		if (result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(this, "��Ǹ��δ�ҵ����", Toast.LENGTH_SHORT).show();
			// Html.fromHtml("<html></html>");
		} else {
			//Toast.makeText(this, result.getName() + ": " + result.getAddress(),
				//	Toast.LENGTH_SHORT).show();
			// ����յ�ľ�γ��
			LatLng latLng = result.getLocation();
			mEtLat = latLng.latitude;
			mEtLon = latLng.longitude;
			if (mStLat != 0 && mStLon != 0 && mEtLat != 0 && mEtLon != 0) {
				Intent intent = new Intent(MapActivity.this,
						MapInfoActivity.class);

			}
		}

	}

	@Override
	public void onGetPoiResult(PoiResult result) {
		if (result == null
				|| result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
			Toast.makeText(this, "δ�ҵ����", Toast.LENGTH_LONG).show();
			return;
		}
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {
			mBaiduMap.clear();
			PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
			mBaiduMap.setOnMarkerClickListener(overlay);
			overlay.setData(result);
			overlay.addToMap();
			overlay.zoomToSpan();
			return;
		}
		if (result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {

			// ������ؼ����ڱ���û���ҵ����������������ҵ�ʱ�����ذ����ùؼ�����Ϣ�ĳ����б�
			String strInfo = "��";
			for (CityInfo cityInfo : result.getSuggestCityList()) {
				strInfo += cityInfo.city;
				strInfo += ",";
			}
			strInfo += "�ҵ����";
			Toast.makeText(this, strInfo, Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * С���ݵĵ������
	 * 
	 * @author Administrator
	 * 
	 */
	private class MyPoiOverlay extends PoiOverlay {

		public MyPoiOverlay(BaiduMap baiduMap) {
			super(baiduMap);
		}

		@Override
		public boolean onPoiClick(final int index) {
			super.onPoiClick(index);

			PoiInfo poi = getPoiResult().getAllPoi().get(index);
			mPoiSearch.searchPoiDetail(new PoiDetailSearchOption()
					.poiUid(poi.uid));
			// ������е�ֵ
			name = poi.name;// POI����
			number = poi.phoneNum;// ��ȡ�绰����
			adds = poi.address;// ��ȡ��ַ*/
			// ������ �󶨲���
			View v = mInflater.inflate(R.layout.map_po, null);
			mName = (TextView) v.findViewById(R.id.tv_name);
			mName.setText(name);
			/*mName = (TextView) v.findViewById(R.id.tv_name);
			mPhone = (TextView) v.findViewById(R.id.tv_phone);
			mAdds = (TextView) v.findViewById(R.id.tv_address);
			
			mName.setText(name);
			mPhone.setText(number);
			mAdds.setText(adds);*/
			OnInfoWindowClickListener listener = null;
			listener = new OnInfoWindowClickListener() {
				public void onInfoWindowClick() {
					// ����һ����ͼ
					Intent intent = new Intent(MapActivity.this,
							MapInfoActivity.class);
					// ��ȡ��γ��
					intent.putExtra("stLat", mStLat);
					intent.putExtra("stLon", mStLon);
					intent.putExtra("etLat", mEtLat);
					intent.putExtra("etLon", mEtLon);
					// ������Ϣ
					intent.putExtra("name", name);
					intent.putExtra("address", adds);
					intent.putExtra("telephone", number);

					intent.putExtra("shopHours", shopHours);
					intent.putExtra("tag", tag);
					intent.putExtra("type", type);
					intent.putExtra("addr", locaaddr);// ����λ����Ϣ��

					intent.putExtra("environmentRating", environmentRating);
					intent.putExtra("facilityRating", facilityRating);
					// ��ת
					startActivity(intent);
					finish();
				}
			};
						
			if(v!=null){
			InfoWindow mInfoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(v),
					poi.location, -47, listener);
			mBaiduMap.showInfoWindow(mInfoWindow);
			}
			return true;
		}
	}

}
