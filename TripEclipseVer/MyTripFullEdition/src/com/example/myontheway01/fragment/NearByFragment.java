package com.example.myontheway01.fragment;

import java.util.ArrayList;
import java.util.List;

import com.example.myontheway01.MapActivity;
import com.example.myontheway01.R;
import com.example.myontheway01.adapter.NearByGVAdapter;
import com.example.myontheway01.bean.NearByBean;
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
   //声明
	private Context mContext;
	private EditText mSearchEt;//搜索框
	private ImageView mSearchIv;//搜索图标
	private GridView mServiceGv;//网格布局
	private List<NearByBean> list = new ArrayList<NearByBean>();
	private NearByGVAdapter adapter;//适配器
	//private NearByGvAdapter adapter;
	@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	// TODO Auto-generated method stub	
	View v=inflater.inflate(R.layout.fragment_nearby, null);
	inti(v);
	   return v;
}
	//初始化
	public void inti(View v) {
		mContext=getActivity();//由于是fragment,故为此种写法
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
	 * 为data赋值
	 * */
	private void setList() {
		// TODO Auto-generated method stub
		list.clear();
		list.add(new NearByBean(R.drawable.dinner, "美食"));
		list.add(new NearByBean(R.drawable.mall, "超市"));
		list.add(new NearByBean(R.drawable.hotel, "宾馆"));
		list.add(new NearByBean(R.drawable.bar, "酒吧"));
		list.add(new NearByBean(R.drawable.ktv, "KTV"));
		list.add(new NearByBean(R.drawable.cinema, "影院"));
		list.add(new NearByBean(R.drawable.bank, "银行"));
		list.add(new NearByBean(R.drawable.atm, "ATM"));
		list.add(new NearByBean(R.drawable.scenic, "景点"));
	}
    /**
     * 第一个参数：parent相当于对应适配器的一个指针，可以通过它来获得里面装着的一切东西，
     * 再通俗点就是说告诉你，你点的是哪一个listview或grigview;

	   第二个参数： view是你点b item的view的句柄，就是你可以用这个view，来获得b里的控件的id后操作控件

	第三个参数：int position,    // position是你点的item在对应listview(gridview)适配器里的位置
	  （生成listview时，适配器一个一个的做item，然后把他们按顺序排好队，在放到listview里，
	  意思就是这个b是第position号做好的）

	第四个参数： long id     // id是所点item在listview里的第几行的位置，
	 大部分时候position和id的值是一样的
	
     * */
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		Intent intent=new Intent(mContext,MapActivity.class);
		//将点击的item的内容通过intent传递到地图界面
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
			Toast.makeText(getActivity(), "搜索内容不能为空",Toast.LENGTH_LONG).show();
		}
	}
}
