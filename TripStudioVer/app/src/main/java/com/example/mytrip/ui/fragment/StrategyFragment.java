package com.example.mytrip.ui.fragment;


import android.support.v4.app.ListFragment;

public class StrategyFragment extends ListFragment{
//	private RelativeLayout mLoadRl;           // Ԥ���صĽ���
//	private LinearLayout mListLl;             // ���ݵĽ���
////	private PullToRefreshLayout pullToManager;// ���Ի�������Բ���
//	private PullToRefreshListView mPullToRefreshListView;
//	// ��ʼ������
//	private static final String INIT_URL = "http://app.117go.com/demo27/php/plaza.php?submit=getPlaza4&startId=0&fetchNewer=1&length=20&type=0&isWaterfall=0&token=&v=a5.0.4&vc=anzhi&vd=f2e4ee47505f6fba";
//	// ˢ��ʱ���õ�����
//	private static final String REFRESH_URL = "http://app.117go.com/demo27/php/plaza.php?submit=getPlaza4&startId=0&fetchNewer=1&length=20&type=0&isWaterfall=0&token=&v=a5.0.4&vc=anzhi&vd=f2e4ee47505f6fba";
//	private static final String LOAD_URL = "http://app.117go.com/demo27/php/plaza.php?submit=getPlaza4&startId="
//			+ HttpGetData.getStartId()
//			+ "&fetchNewer=0&length=20&type=0&isWaterfall=0&token=&v=a5.0.4&vc=anzhi&vd=f2e4ee47505f6fba";
////	private PullableListView mListView;
////	private ListView mListView;
//	private StrategyAdapter mAdapter;
//	private List<StrategyBean> data = new ArrayList<StrategyBean>();
//	private static final int INIT_DATE = 0;     // ��ʼ������
//	private static final int REFRESH_DATE = 1;  // ˢ������
//	private static final int LOAD_DATE = 2;     // ��������
//	private Handler mHandler = new Handler() {
//		@SuppressWarnings("unchecked")
//		public void handleMessage(final Message msg) {
//			switch (msg.what) {
//			case INIT_DATE:// ���� UI,��ʼ������
//
//				data.clear();
//				data = (List<StrategyBean>) msg.obj;
//				mAdapter.setData(data);
//				mAdapter.notifyDataSetChanged();// ˢ��������
//				// ����UI
//				mLoadRl.setVisibility(View.GONE);// Ԥ��������
//				mPullToRefreshListView.setVisibility(View.VISIBLE);// ������ʾ
//
//				break;
//			case REFRESH_DATE:// ���� UI��ˢ������
//
//				data.clear();
//				data = (List<StrategyBean>) msg.obj;
//				mAdapter.setData(data);
//				mAdapter.notifyDataSetChanged();// ˢ��������
//				// ������³ɹ�����ã������ص�����ˢ�µ��Ǹ�����
//				mPullToRefreshListView.onRefreshComplete();
//				break;
//			case LOAD_DATE:// ���� UI,��������
//
//				data.addAll((List<StrategyBean>) msg.obj);
//				// data = (List<StrategyBean>) msg.obj;
//				mAdapter.setData(data);
//				mAdapter.notifyDataSetChanged();// ˢ��������
//				// ������³ɹ�����ã������ص��������ص��Ǹ�����
//				mPullToRefreshListView.onRefreshComplete();
//				break;
//			default:
//				break;
//			}
//		};
//	};
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
//				.detectDiskReads().detectDiskWrites().detectNetwork()
//				.penaltyLog().build());
//		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
//				.detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
//				.penaltyLog().penaltyDeath().build());
//		View v = inflater.inflate(R.layout.fragment_strategy, null);
//		initView(v);
//		mAdapter = new StrategyAdapter(getActivity());
//		mPullToRefreshListView.setAdapter(mAdapter);
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				Message m = mHandler.obtainMessage();
//				m.what = INIT_DATE;
//				m.obj = HttpGetData.getStrategyBean(INIT_URL);
//				mHandler.sendMessage(m);
//
//			}
//		}).start();
//
//		return v;
//	}
//
//	/**
//	 * ��ʼ��View
//	 *
//	 * @param v
//	 */
//	private void initView(View v) {
//		// �Զ���Ŀ������»�������Բ���
////		pullToManager = (PullToRefreshLayout) v.findViewById(R.id.refresh_view);
//		mPullToRefreshListView=(PullToRefreshListView) v.findViewById(R.id.pull_refresh_list);
//		// ��Բ��ֻ����¼�����
////		pullToManager.setOnRefreshListener(this);
//		mPullToRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
//
//			@Override
//			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
//				// TODO Auto-generated method stub
//				if(refreshView.isHeaderShown()){//����ˢ��
//					/**
//					 * ��ʾˢ��ʱ��
//					 */
//					String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
//							DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
//
//					// Update the LastUpdatedLabel
//					refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
//
//					/**
//					 * ������ִ�еĲ���
//					 */
//					new Thread(new Runnable(){
//
//						@Override
//						public void run() {
//							Message m = mHandler.obtainMessage();
//							m.what = REFRESH_DATE;
//							m.obj = HttpGetData.getStrategyBean(REFRESH_URL);
//							mHandler.sendMessage(m);
//						}
//					}).start();
//
//				}else{//�������d
//					new Thread(new Runnable() {
//
//						@Override
//						public void run() {
//							// TODO Auto-generated method stub
//							Message m = mHandler.obtainMessage();
//							m.what = LOAD_DATE;
//							m.obj = HttpGetData.getStrategyBean(LOAD_URL);
//							mHandler.sendMessage(m);
//						}
//					}).start();
//				}
//			}
//		});
//		mLoadRl = (RelativeLayout) v.findViewById(R.id.lodingRelativeLayout);
//		mLoadRl.setVisibility(View.VISIBLE);// Ԥ������ʾ
//		mPullToRefreshListView.setVisibility(View.GONE);// ��������
//		LoadingAinm.ininLodingView(v);// Ԥ���صĶ���Ч��
//		//����pull-to-refreshģʽΪ Mode.Both
//		 mPullToRefreshListView.setMode(Mode.BOTH);
//	}
}

