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
 * ͨ��btn��ֵ��
 * ��ʾ��ס��Ϣ,�˷�����1�˷���
 * */
public class CheckInListActivity extends Activity implements CheckInInfoInterface{
    //��������
	private TextView mTitleTv;//������
	private ListView mListView;//listview
	private TextView mListTv;//��listview�ڲ����е�λ����ȫ��ͬ��Ĭ��Ϊgone,���鲻��ĳ����ס��Ϣʱ������ҳ����Ϊ��ʾ
	private List<CheckInInfoBean> data=new ArrayList<CheckInInfoBean>();//���ݼ�
	private EditText mSearchEt;
	private CheckInAdapter adapter;
	private int btn;
	private Button mCheckOutBtn;
	//�б���ʾ�ؼ�
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_checked_client_info_list);
		//��ʼ���ؼ�
		initView();
		//��������������
		adapter=new CheckInAdapter(this);
		mListView.setAdapter(adapter);
		//����������
		updateAdapter(null);
	}
	/**
	 * ��ʼ���ؼ�
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
			mSearchEt.setHint("�����뷿���");
			mTitleTv.setText("�����˷�");
		}else if(btn==2){
			mSearchEt.setHint("������Ҫ��������������Ϊ��ѯ����");
			mTitleTv.setText("��ס��ʷ");
			//mCheckOutBtn.setVisibility(View.GONE);
		}
	}
	/**
	 * ���������ťʱ����������
	 * */
	public void onClickSearch(View v){//���뺬����
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
			adapter.notifyDataSetChanged();//ˢ��������
		}
	}
	@Override
	public void onUpdateUIListener() {
		// TODO Auto-generated method stub
		updateAdapter(null);
	}
	/**
	 * ��ȡ���Ǹ���ť����ģ���CheckInAdapter�еõ���ֵ��������Ӧ����
	 * */
	@Override
	public int getI() {
		// TODO Auto-generated method stub
		return btn;
	}
}
