package com.example.myontheway01;

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
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MapActivity extends Activity implements
		OnGetPoiSearchResultListener {

	private InfoWindow mInfoWindow;
	private PopupWindow mPopupWindow;

	// 定位后获取的经纬度
	private double mStLat = 0;// 我的经度
	private double mStLon = 0;// 我的纬度
	private double mEtLat = 0;// 终点的经度
	private double mEtLon = 0;// 终点的纬度

	// 获得名称
	TextView mName;
	String name;
	// 电话号码
	TextView mPhone;
	String number;
	// 获得地址
	TextView mAdds;
	String adds;
	View v;
	String detailUrl;
	String shopHours;
	String tag;
	String type;
	double environmentRating;
	double facilityRating;
	private LayoutInflater mInflater;// 过滤器
	// 定位相关
	LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();
	private LocationMode mCurrentMode;
	BitmapDescriptor mCurrentMarker;
	private PoiSearch mPoiSearch = null;
	MapView mMapView;
	BaiduMap mBaiduMap = null;
	private String locaaddr;
	// UI相关
	OnCheckedChangeListener radioButtonListener;
	Button requestLocButton;
	boolean isFirstLoc = true;// 是否首次定位

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 在使用SDK各组件之前初始化context信息，传入ApplicationContext
		// 注意该方法要再setContentView方法之前实现
		// SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_map);
		mInflater = LayoutInflater.from(this);

		// 获取地图控件引用
		mMapView = (MapView) findViewById(R.id.bmapView);
		requestLocButton = (Button) findViewById(R.id.button1);
		mCurrentMode = LocationMode.NORMAL;
		requestLocButton.setText("普通");
		OnClickListener btnClickListener = new OnClickListener() {
			public void onClick(View v) {
				switch (mCurrentMode) {
				case NORMAL:
					requestLocButton.setText("跟随");
					mCurrentMode = LocationMode.FOLLOWING;
					mBaiduMap
							.setMyLocationConfigeration(new MyLocationConfiguration(
									mCurrentMode, true, mCurrentMarker));
					break;
				case COMPASS:
					requestLocButton.setText("普通");
					mCurrentMode = LocationMode.NORMAL;
					mBaiduMap
							.setMyLocationConfigeration(new MyLocationConfiguration(
									mCurrentMode, true, mCurrentMarker));
					break;
				case FOLLOWING:
					requestLocButton.setText("罗盘");
					mCurrentMode = LocationMode.COMPASS;
					mBaiduMap
							.setMyLocationConfigeration(new MyLocationConfiguration(
									mCurrentMode, true, mCurrentMarker));
					break;
				}
			}
		};
		requestLocButton.setOnClickListener(btnClickListener);
		// 地图初始化
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);
		// 定位初始化
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		mLocClient.setLocOption(option);
		mLocClient.start();
		// 周边搜索注册监听器
		// 初始化搜索模块，注册搜索事件监听
		mPoiSearch = PoiSearch.newInstance();
		mPoiSearch.setOnGetPoiSearchResultListener(this);
	}

	/**
	 * 影响搜索按钮点击事件
	 * 
	 * @param v
	 */
	public void searchButtonProcess(LatLng lng) {
		Intent intent = getIntent();
		String content = intent.getStringExtra("whichItem");
		// 搜附近，或者周边
		// mPoiSearch.searchNearby((new PoiNearbySearchOption())
		// .keyword("银行")//分类
		// .location(null)//当前坐标
		// .pageCapacity(20)//每页显示的数量
		// .pageNum(1)//支取一页数据
		// .radius(1000));//半径
		// 搜全城
		// mPoiSearch.searchInCity((new PoiCitySearchOption())
		// .city("西安")
		// .keyword("电影院")
		// .pageNum(1));
		// 搜周边
		// 目标医院纬度
		// float Latitude = 34.224647f;
		// // 目标医院经度
		// float Longitude = 108.943240f;
		// LatLng latLng = new LatLng(Latitude, Longitude);
		mPoiSearch.searchNearby((new PoiNearbySearchOption().keyword(content)
				.radius(2000).pageNum(1).location(lng)));

	}

	/*
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null || mMapView == null)
				return;
			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(100).latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();

			// 自己当前的坐标【位置】
			LatLng ll = new LatLng(location.getLatitude(),
					location.getLongitude());
			if (isFirstLoc) {
				isFirstLoc = false;
				mBaiduMap.setMyLocationData(locData);
				// 自己当前的坐标【位置】
				LatLng ll2 = new LatLng(location.getLatitude(),
						location.getLongitude());
				/*
				 * 两种方式获取自己的经纬度
				 */
				// ll2.latitude;
				// 获得定位的起点经纬度
				mStLat = location.getLatitude();
				mStLon = location.getLongitude();
				// 搜索周边
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
		// 退出时销毁定位
		mLocClient.stop();
		mPoiSearch.destroy();
		// 关闭定位图层
		mBaiduMap.setMyLocationEnabled(false);
		mMapView.onDestroy();
		mMapView = null;

		super.onDestroy();
	}

	// 当点击小红泡泡时回调此方法

	@Override
	public void onGetPoiDetailResult(PoiDetailResult result) {

		// 具体信息

		shopHours = result.getShopHours();// poi 营业时间;
		tag = result.getTag();// poi 标签
		type = result.getType();// poi 类型
		environmentRating = result.getEnvironmentRating();// POI环境评价；
		facilityRating = result.getFacilityRating();// POI设备评价；

		if (result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
			// Html.fromHtml("<html></html>");
		} else {
			//Toast.makeText(this, result.getName() + ": " + result.getAddress(),
				//	Toast.LENGTH_SHORT).show();
			// 获得终点的经纬度
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
			Toast.makeText(this, "未找到结果", Toast.LENGTH_LONG).show();
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

			// 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
			String strInfo = "在";
			for (CityInfo cityInfo : result.getSuggestCityList()) {
				strInfo += cityInfo.city;
				strInfo += ",";
			}
			strInfo += "找到结果";
			Toast.makeText(this, strInfo, Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * 小气泡的点击监听
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
			// 获得所有的值
			name = poi.name;// POI名称
			number = poi.phoneNum;// 获取电话号码
			adds = poi.address;// 获取地址*/
			// 过滤器 绑定布局
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
					// 创建一个意图
					Intent intent = new Intent(MapActivity.this,
							MapInfoActivity.class);
					// 获取经纬度
					intent.putExtra("stLat", mStLat);
					intent.putExtra("stLon", mStLon);
					intent.putExtra("etLat", mEtLat);
					intent.putExtra("etLon", mEtLon);
					// 具体信息
					intent.putExtra("name", name);
					intent.putExtra("address", adds);
					intent.putExtra("telephone", number);

					intent.putExtra("shopHours", shopHours);
					intent.putExtra("tag", tag);
					intent.putExtra("type", type);
					intent.putExtra("addr", locaaddr);// 地理位置信息；

					intent.putExtra("environmentRating", environmentRating);
					intent.putExtra("facilityRating", facilityRating);
					// 跳转
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
