package com.example.myontheway01.fragment;

import java.util.ArrayList;
import java.util.List;

import com.example.loadrefresh.LoadingAinm;
import com.example.myontheway01.R;
import com.example.myontheway01.adapter.StrategyAdapter;
import com.example.myontheway01.bean.StrategyBean;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class StrategyFragment extends ListFragment{
	private RelativeLayout mLoadRl;           // 预加载的界面
	private LinearLayout mListLl;             // 数据的界面
//	private PullToRefreshLayout pullToManager;// 可以滑动的相对布局
	private PullToRefreshListView mPullToRefreshListView;
	// 初始化数据
	private static final String INIT_URL = "http://app.117go.com/demo27/php/plaza.php?submit=getPlaza4&startId=0&fetchNewer=1&length=20&type=0&isWaterfall=0&token=&v=a5.0.4&vc=anzhi&vd=f2e4ee47505f6fba";
	// 刷新时调用的数据
	private static final String REFRESH_URL = "http://app.117go.com/demo27/php/plaza.php?submit=getPlaza4&startId=0&fetchNewer=1&length=20&type=0&isWaterfall=0&token=&v=a5.0.4&vc=anzhi&vd=f2e4ee47505f6fba";
	private static final String LOAD_URL = "http://app.117go.com/demo27/php/plaza.php?submit=getPlaza4&startId="
			+ HttpGetData.getStartId()
			+ "&fetchNewer=0&length=20&type=0&isWaterfall=0&token=&v=a5.0.4&vc=anzhi&vd=f2e4ee47505f6fba";
//	private PullableListView mListView;
//	private ListView mListView;
	private StrategyAdapter mAdapter;
	private List<StrategyBean> data = new ArrayList<StrategyBean>();
	private static final int INIT_DATE = 0;     // 初始化数据
	private static final int REFRESH_DATE = 1;  // 刷新数据
	private static final int LOAD_DATE = 2;     // 加载数据
	private Handler mHandler = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(final android.os.Message msg) {
			switch (msg.what) {
			case INIT_DATE:// 更新 UI,初始化数据

				data.clear();
				data = (List<StrategyBean>) msg.obj;
				mAdapter.setData(data);
				mAdapter.notifyDataSetChanged();// 刷新适配器
				// 更新UI
				mLoadRl.setVisibility(View.GONE);// 预加载隐藏
				mPullToRefreshListView.setVisibility(View.VISIBLE);// 数据显示

				break;
			case REFRESH_DATE:// 更新 UI，刷新数据

				data.clear();
				data = (List<StrategyBean>) msg.obj;
				mAdapter.setData(data);
				mAdapter.notifyDataSetChanged();// 刷新适配器
				// 界面更新成功后调用，用来关掉下拉刷新的那个布局
				mPullToRefreshListView.onRefreshComplete();
				break;
			case LOAD_DATE:// 更新 UI,加载数据

				data.addAll((List<StrategyBean>) msg.obj);
				// data = (List<StrategyBean>) msg.obj;
				mAdapter.setData(data);
				mAdapter.notifyDataSetChanged();// 刷新适配器
				// 界面更新成功后调用，用来关掉上拉加载的那个布局
				mPullToRefreshListView.onRefreshComplete();
				break;
			default:
				break;
			}
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// 解决4.0以后主线程不能访问http的问题
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
				.penaltyLog().penaltyDeath().build());
		View v = inflater.inflate(R.layout.fragment_strategy, null);
		initView(v);
		mAdapter = new StrategyAdapter(getActivity());
		mPullToRefreshListView.setAdapter(mAdapter);
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message m = mHandler.obtainMessage();
				m.what = INIT_DATE;
				m.obj = HttpGetData.getStrategyBean(INIT_URL);
				mHandler.sendMessage(m);

			}
		}).start();

		return v;
	}

	/**
	 * 初始化View
	 * 
	 * @param v
	 */
	private void initView(View v) {
		// 自定义的可以上下滑动的相对布局
//		pullToManager = (PullToRefreshLayout) v.findViewById(R.id.refresh_view);
		mPullToRefreshListView=(PullToRefreshListView) v.findViewById(R.id.pull_refresh_list);	
		// 相对布局滑动事件监听
//		pullToManager.setOnRefreshListener(this);
		mPullToRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				if(refreshView.isHeaderShown()){//下拉刷新
					/**
					 * 显示刷新时间
					 */
					String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
							DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

					// Update the LastUpdatedLabel
					refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				
					/**
					 * 下拉所执行的操作
					 */
					new Thread(new Runnable(){

						@Override
						public void run() {
							Message m = mHandler.obtainMessage();
							m.what = REFRESH_DATE;
							m.obj = HttpGetData.getStrategyBean(REFRESH_URL);
							mHandler.sendMessage(m);			
						}
					}).start();
					
				}else{//上拉加載
					new Thread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							Message m = mHandler.obtainMessage();
							m.what = LOAD_DATE;
							m.obj = HttpGetData.getStrategyBean(LOAD_URL);
							mHandler.sendMessage(m);
						}
					}).start();
				}
			}
		});
		mLoadRl = (RelativeLayout) v.findViewById(R.id.lodingRelativeLayout);
		mLoadRl.setVisibility(View.VISIBLE);// 预加载显示
		mPullToRefreshListView.setVisibility(View.GONE);// 数据隐藏
		LoadingAinm.ininLodingView(v);// 预加载的动画效果
		//设置pull-to-refresh模式为 Mode.Both
		 mPullToRefreshListView.setMode(Mode.BOTH);
	}
}

