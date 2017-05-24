package com.example.mytrip.ui.footprint;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.example.mytrip.R;
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
	// ʱ����
	private ListView listView;
	private EditText mMsgEt;
	private Button mSendBtn;
	private Button mAddsBtn;// ���λ��
	private FootPrintAdapter footPrintAdapter;

	BmobUser mUserName;
	private String mContent;

	MapView mMapView;
	BaiduMap mBaiduMap = null;
	private String mAdds;
	private String mAdds2 = "";
	FootPrint footPrint;

	// private PullToRefreshListView mPullToRefreshListView;

	// ��λ���
	LocationClient mLocClient = null;
	public MyLocationListenner myListener = new MyLocationListenner();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time_shaft_listview);
		listView = (ListView) findViewById(R.id.lv_foo_print);
		// lsitview ��ÿһ��֮����Ҫ����һ��ͼƬ��Ϊ��� ���� Item ֮���޼�϶
		listView.setDividerHeight(0);
		// footPrintAdapter=new FootPrintAdapter(this, null);
		footPrintAdapter = new FootPrintAdapter(this);
		listView.setAdapter(footPrintAdapter);
		inData();
		getQuery();
		// ˢ�½���
		refresh();
	}

	private void inData() {

		// ��ͼ��λ��ʼ��
		mLocClient = new LocationClient(this); // ����LocationClient��
		mLocClient.registerLocationListener(myListener); // ע���������
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// ��GPS
		option.setCoorType("bd09ll"); // ������������
		option.setScanSpan(1000);
		option.setIsNeedAddress(true);
		option.setNeedDeviceDirect(true);
		mLocClient.setLocOption(option);
		mLocClient.start();

		mSendBtn = (Button) findViewById(R.id.sed_msg_btn1);
		mSendBtn.setOnClickListener(this);
		mAddsBtn = (Button) findViewById(R.id.location_btn);
		mAddsBtn.setOnClickListener(this);
		mMsgEt = (EditText) findViewById(R.id.et_sed_msg); // ��ʼ�����ݿ�

	}

	// ��ѯ
	private void getQuery() {
		BmobUser mUserName = BmobUser.getCurrentUser(this);
		if (mUserName != null) {
			BmobQuery<FootPrint> query = new BmobQuery<FootPrint>();
			BmobUser bmobUser = new BmobUser();
			bmobUser.setObjectId(mUserName.getObjectId());
			query.addWhereEqualTo("mUserName", bmobUser);
			query.include("mUserName");
			query.order("-createdAt");
			// ִ�в�ѯ����
			query.findObjects(this, new FindListener<FootPrint>() {

				@Override
				public void onSuccess(List<FootPrint> object) {

					getBean(object);
				}

				@Override
				public void onError(int arg0, String arg1) {
					// TODO Auto-generated method stub

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
	 * ��λSDK��������
	 */
	public class MyLocationListenner implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view ���ٺ��ڴ����½��յ�λ��
			if (location == null) {
				return;
			}
			// location.getCity() + ��ʾ���������
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
			mUserName = BmobUser.getCurrentUser(this); // ��ȡ����ǰ�û�
			mContent = mMsgEt.getText().toString().trim(); // �����Ϣ����
			footPrint.setmAdds(mAdds2);// ����λ����Ϣ
			footPrint.setmUserName(mUserName);
			// footPrint = new FootPrint();
			// ע�⣺���ܵ���gameScore.setObjectId("")����
			if (mContent == null || mContent.equals("")) {

				Toast.makeText(FootPrintActivity.this, "�㻹δ��������",
						Toast.LENGTH_LONG).show();

			} else {
				footPrint.setmContent(mContent);

				footPrint.save(this, new SaveListener() {

					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						Toast.makeText(FootPrintActivity.this, "����ɹ�",
								Toast.LENGTH_LONG).show();
						mAdds2 = "";
						getQuery();
						mMsgEt.setText("");
					}

					@Override
					public void onFailure(int code, String arg0) {
						// TODO Auto-generated method stub
						Toast.makeText(FootPrintActivity.this, "����ʧ��",
								Toast.LENGTH_LONG).show();
						mAdds2 = "";
					}
				});
				break;
			}
			
		case R.id.location_btn:// ���λ����Ϣ
			new AlertDialog.Builder(this)
					.setTitle("��ܰ��ʾ��")
					.setIcon(R.drawable.groupchat_send_failed)
					.setMessage("�Ƿ�������ǰλ����Ϣ?")
					.setPositiveButton("��",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface arg0,
										int arg1) {
									// TODO Auto-generated method stub
									mAdds2 = "";
								}
							})
					.setNegativeButton("��",
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
