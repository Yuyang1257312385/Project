package com.example.mytrip.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import com.example.mytrip.ui.MapActivity;
import com.example.mytrip.R;
import com.example.mytrip.ui.adapter.NearByGVAdapter;
import com.example.mytrip.ui.bean.NearByBean;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class NearByFragment extends Fragment implements OnClickListener,OnItemClickListener{
   //����
	private Context mContext;
	private EditText mSearchEt;//������
	private ImageView mSearchIv;//����ͼ��
	private GridView mServiceGv;//���񲼾�
	private List<NearByBean> list = new ArrayList<NearByBean>();
	private NearByGVAdapter adapter;//������
	//private NearByGvAdapter adapter;
	@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	// TODO Auto-generated method stub	
	View v=inflater.inflate(R.layout.fragment_nearby, null);
	inti(v);
	   return v;
}
	//��ʼ��
	public void inti(View v) {
		mContext=getActivity();//������fragment,��Ϊ����д��
		mSearchEt=(EditText) v.findViewById(R.id.et_search);
		mSearchIv=(ImageView) v.findViewById(R.id.iv_search);
		mServiceGv=(GridView) v.findViewById(R.id.gv_near_by);
		setList();
		adapter=new NearByGVAdapter(mContext);
		adapter.setList(list);
		mServiceGv.setAdapter(adapter);
		mSearchIv.setOnClickListener(this);
		mServiceGv.setOnItemClickListener(this);
	}
	/**
	 * Ϊdata��ֵ
	 * */
	private void setList() {
		// TODO Auto-generated method stub
		list.clear();
		list.add(new NearByBean(R.drawable.dinner, "��ʳ"));
		list.add(new NearByBean(R.drawable.mall, "����"));
		list.add(new NearByBean(R.drawable.hotel, "����"));
		list.add(new NearByBean(R.drawable.bar, "�ư�"));
		list.add(new NearByBean(R.drawable.ktv, "KTV"));
		list.add(new NearByBean(R.drawable.cinema, "ӰԺ"));
		list.add(new NearByBean(R.drawable.bank, "����"));
		list.add(new NearByBean(R.drawable.atm, "ATM"));
		list.add(new NearByBean(R.drawable.scenic, "����"));
	}
    /**
     * ��һ��������parent�൱�ڶ�Ӧ��������һ��ָ�룬����ͨ�������������װ�ŵ�һ�ж�����
     * ��ͨ�׵����˵�����㣬��������һ��listview��grigview;

	   �ڶ��������� view�����b item��view�ľ������������������view�������b��Ŀؼ���id������ؼ�

	������������int position,    // position������item�ڶ�Ӧlistview(gridview)���������λ��
	  ������listviewʱ��������һ��һ������item��Ȼ������ǰ�˳���źöӣ��ڷŵ�listview�
	  ��˼�������b�ǵ�position�����õģ�

	���ĸ������� long id     // id������item��listview��ĵڼ��е�λ�ã�
	 �󲿷�ʱ��position��id��ֵ��һ����
	
     * */
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		Intent intent=new Intent(mContext,MapActivity.class);
		//�������item������ͨ��intent���ݵ���ͼ����
		intent.putExtra("whichItem", list.get(position).getContent().toString().trim());
		startActivity(intent);
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		String content=mSearchEt.getText().toString().trim();
		if(content!=null && !content.equals("")){
			Intent intent=new Intent(mContext,MapActivity.class);
			intent.putExtra("whichItem", content);
			startActivity(intent);
		}else{
			Toast.makeText(getActivity(), "�������ݲ���Ϊ��",Toast.LENGTH_LONG).show();
		}
	}
}
