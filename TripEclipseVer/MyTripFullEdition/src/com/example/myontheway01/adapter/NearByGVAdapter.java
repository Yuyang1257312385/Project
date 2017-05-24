package com.example.myontheway01.adapter;

import java.util.List;
import java.util.zip.Inflater;

import com.example.myontheway01.R;
import com.example.myontheway01.bean.NearByBean;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 附近信息网格布局的适配器
 * 1.继承BaseAdapter,添加未完成方法
 * 2.写构造方法获得上下文（布局），写set方法获得数据
 * 3.创建一个holder类，存放item中的控件
 * */
public class NearByGVAdapter extends BaseAdapter{
    private Context mContext;
    private List<NearByBean> list;
	private LayoutInflater inflater;


	public void setList(List<NearByBean> list) {
		this.list = list;
	}
    public NearByGVAdapter(Context context){
    	//筛选器
    	this.mContext=context;
    	inflater=LayoutInflater.from(context);
    }
    /**
     * 获取集合中的元素个数
     * */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list != null ? list.size() : 0;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
    /**
     * 得到视图，第一个参数代表是哪个item,第二个为item的视图
     * */
	@Override
	public View getView(int position, View contentView, ViewGroup viewgroup) {
		// TODO Auto-generated method stub
		Holder holder=null;
		//若没有视图，则创建保存
		if(contentView==null){
			//绑定布局
			contentView=inflater.inflate(R.layout.item_nearby, null);
			//初始化控件
			holder=new Holder();
			holder.photo=(ImageView) contentView.findViewById(R.id.iv_picture);
			holder.content=(TextView) contentView.findViewById(R.id.tv_content);
			//保存
			contentView.setTag(holder);
		//若存在视图，则从保存的中取出控件
		}else{
			holder=(Holder) contentView.getTag();
		}
		//为控件赋值
		holder.photo.setImageResource(list.get(position).getImgId());
		holder.content.setText(list.get(position).getContent());
		return contentView;
	}
   class Holder{
	   ImageView photo;
	   TextView content;
   }
}
