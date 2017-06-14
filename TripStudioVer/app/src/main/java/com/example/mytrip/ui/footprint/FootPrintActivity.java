package com.example.mytrip.ui.footprint;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.example.mytrip.R;
import com.example.mytrip.tools.LogUtil;
import com.example.mytrip.tools.ToastUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

/**
 * @author Administrator
 * 
 */
public class FootPrintActivity extends Activity implements OnClickListener,
		getQuery {
	private Activity mContext;
	// 时间轴
	private ListView listView;
	private EditText mMsgEt;
	private Button mSendBtn;
	private Button mAddsBtn;// 获得位置
	private FootPrintAdapter footPrintAdapter;

	BmobUser mUserName;
	private String mContent;

	MapView mMapView;
	BaiduMap mBaiduMap = null;
	private String mAdds;
	private String mAdds2 = "";
	FootPrint footPrint;

	// private PullToRefreshListView mPullToRefreshListView;

	// 定位相关
	LocationClient mLocClient = null;
	public MyLocationListenner myListener = new MyLocationListenner();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time_shaft_listview);
		listView = (ListView) findViewById(R.id.lv_foo_print);
		// lsitview 的每一项之间需要设置一个图片做为间隔 设置 Item 之间无间隙
		listView.setDividerHeight(0);
		// footPrintAdapter=new FootPrintAdapter(this, null);
		footPrintAdapter = new FootPrintAdapter(this);
		listView.setAdapter(footPrintAdapter);
		inData();
		getQuery();
		// 刷新界面
		refresh();
	}

	private void inData() {

		// 地图定位初始化
		mLocClient = new LocationClient(this); // 声明LocationClient类
		mLocClient.registerLocationListener(myListener); // 注册监听函数
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开GPS
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		option.setIsNeedAddress(true);
		option.setNeedDeviceDirect(true);
		mLocClient.setLocOption(option);
		mLocClient.start();

		mSendBtn = (Button) findViewById(R.id.sed_msg_btn1);
		mSendBtn.setOnClickListener(this);
		mAddsBtn = (Button) findViewById(R.id.location_btn);
		mAddsBtn.setOnClickListener(this);
		mMsgEt = (EditText) findViewById(R.id.et_sed_msg); // 初始化内容框

	}

	// 查询
	private void getQuery() {
		BmobUser mUserName = BmobUser.getCurrentUser();
		if (mUserName != null) {
			BmobQuery<FootPrint> query = new BmobQuery<FootPrint>();
			BmobUser bmobUser = new BmobUser();
			bmobUser.setObjectId(mUserName.getObjectId());
			query.addWhereEqualTo("mUserName", bmobUser);
			query.include("mUserName");
			query.order("-createdAt");
			// 执行查询方法
			query.findObjects(new FindListener<FootPrint>() {
				@Override
				public void done(List<FootPrint> list, BmobException e) {
					if(e == null){
						getBean(list);
					}else {
						ToastUtils.showShortToast("e == " + e.toString());
					}
				}
			});
		}
	}

	public void getBean(List<FootPrint> listBeans) {

		footPrintAdapter.setData(listBeans);

		footPrintAdapter.notifyDataSetChanged();
	}

	private void refresh() {

	}

	/*
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null) {
				return;
			}
			// location.getCity() + 显示具体的市区
			mAdds = location.getAddrStr();
			mAddsBtn.setText(mAdds);
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}

	@Override
	public void onClick(View v) {
		FootPrint footPrint = new FootPrint();
		switch (v.getId()) {
			case R.id.sed_msg_btn1:
				mUserName = BmobUser.getCurrentUser(); // 获取到当前用户
				mContent = mMsgEt.getText().toString().trim(); // 获得消息内容
				footPrint.setmAdds(mAdds2);// 保存位置信息
				footPrint.setmUserName(mUserName);
				// footPrint = new FootPrint();
				// 注意：不能调用gameScore.setObjectId("")方法
				if (mContent == null || mContent.equals("")) {

					Toast.makeText(FootPrintActivity.this, "你还未发表心情",
							Toast.LENGTH_LONG).show();

				} else {
					footPrint.setmContent(mContent);

					footPrint.save(new SaveListener<String>() {
						@Override
						public void done(String s, BmobException e) {
							if(e == null){
								ToastUtils.showShortToast( "发表成功");
								mAdds2 = "";
								getQuery();
								mMsgEt.setText("");
							}else {
								ToastUtils.showShortToast("发表失败");
								mAdds2 = "";
							}

						}
					});
					break;
				}

			case R.id.location_btn:// 获得位置信息
				new AlertDialog.Builder(this)
						.setTitle("温馨提示：")
						.setIcon(R.drawable.groupchat_send_failed)
						.setMessage("是否发送您当前位置信息?")
						.setPositiveButton("否",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
														int arg1) {
										// TODO Auto-generated method stub
										mAdds2 = "";
									}
								})
						.setNegativeButton("是",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
														int arg1) {
										// TODO Auto-generated method stub
										mAdds2 = mAdds;
									}
								}).show();

				break;
		}
	}
}
