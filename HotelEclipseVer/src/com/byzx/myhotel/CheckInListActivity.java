package com.byzx.myhotel;

import java.util.ArrayList;
import java.util.List;

import com.byzx.edu4001.interfac.CheckInInfoInterface;
import com.byzx.myhotel.adapter.CheckInAdapter;
import com.byzx.myhotel.bean.CheckInInfoBean;
import com.byzx.myhotel.db.CheckInInfoDb;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 通过btn的值来
 * 显示入住信息,退房管理（1退房）
 * */
public class CheckInListActivity extends Activity implements CheckInInfoInterface{
    //声明变量
	private TextView mTitleTv;//标题栏
	private ListView mListView;//listview
	private TextView mListTv;//与listview在布局中的位置完全相同，默认为gone,当查不到某人入住信息时，将此页面设为显示
	private List<CheckInInfoBean> data=new ArrayList<CheckInInfoBean>();//数据集
	private EditText mSearchEt;
	private CheckInAdapter adapter;
	private int btn;
	private Button mCheckOutBtn;
	//列表显示控件
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_checked_client_info_list);
		//初始化控件
		initView();
		//创建适配器对象
		adapter=new CheckInAdapter(this);
		mListView.setAdapter(adapter);
		//更新适配器
		updateAdapter(null);
	}
	/**
	 * 初始化控件
	 * */
	private void initView() {
		// TODO Auto-generated method stub
		mListView=(ListView) findViewById(R.id.lv_list);
		mListTv=(TextView) findViewById(R.id.tv_list);
		mSearchEt=(EditText) findViewById(R.id.et_search);
		mCheckOutBtn=(Button) findViewById(R.id.btn_check_out);
		mTitleTv=(TextView) findViewById(R.id.tv_title);
		btn=getIntent().getIntExtra("button", 0);
		if(btn==1){
			mSearchEt.setHint("请输入房间号");
			mTitleTv.setText("办理退房");
		}else if(btn==2){
			mSearchEt.setHint("请输入要房客姓名，不填为查询所有");
			mTitleTv.setText("入住历史");
			//mCheckOutBtn.setVisibility(View.GONE);
		}
	}
	/**
	 * 点击搜索按钮时，进行搜索
	 * */
	public void onClickSearch(View v){//必须含参数
		String search=mSearchEt.getText().toString().trim();
		updateAdapter(search);
	}
	private void updateAdapter(String search) {
		// TODO Auto-generated method stub
	     if(btn==2){
	    	 data=CheckInInfoDb.findAllChecked(this, search, 0, 2);
	     }else if(btn==1){
			data=CheckInInfoDb.findAllChecked(this, search, 1,1);
		}else{
			data=CheckInInfoDb.findAllChecked(this,search,1,0);
		}
		
		if(data==null ||data.size()==0){
			mListView.setVisibility(View.GONE);
			mListTv.setVisibility(View.VISIBLE);
		}else{
			mListView.setVisibility(View.VISIBLE);
			mListTv.setVisibility(View.GONE);
			adapter.setMdata(data);
			adapter.notifyDataSetChanged();//刷新适配器
		}
	}
	@Override
	public void onUpdateUIListener() {
		// TODO Auto-generated method stub
		updateAdapter(null);
	}
	/**
	 * 获取是那个按钮点击的，在CheckInAdapter中得到该值，并做相应处理
	 * */
	@Override
	public int getI() {
		// TODO Auto-generated method stub
		return btn;
	}
}
