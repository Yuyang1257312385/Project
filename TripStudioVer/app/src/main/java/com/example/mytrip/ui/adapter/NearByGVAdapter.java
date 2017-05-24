package com.example.mytrip.ui.adapter;

import java.util.List;

import com.example.mytrip.R;
import com.example.mytrip.ui.bean.NearByBean;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
/**
 *
 * */
public class NearByGVAdapter extends BaseAdapter{
    private Context mContext;
    private List<NearByBean> list;
	private LayoutInflater inflater;


	public void setList(List<NearByBean> list) {
		this.list = list;
	}
    public NearByGVAdapter(Context context){
    	//ɸѡ��
    	this.mContext=context;
    	inflater=LayoutInflater.from(context);
    }
    /**
     * ��ȡ�����е�Ԫ�ظ���
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
     * �õ���ͼ����һ�������������ĸ�item,�ڶ���Ϊitem����ͼ
     * */
	@Override
	public View getView(int position, View contentView, ViewGroup viewgroup) {
		// TODO Auto-generated method stub
		Holder holder=null;
		//��û����ͼ���򴴽�����
		if(contentView==null){
			//�󶨲���
			contentView=inflater.inflate(R.layout.item_nearby, null);
			//��ʼ���ؼ�
			holder=new Holder();
			holder.photo=(ImageView) contentView.findViewById(R.id.iv_picture);
			holder.content=(TextView) contentView.findViewById(R.id.tv_content);
			//����
			contentView.setTag(holder);
		//��������ͼ����ӱ������ȡ���ؼ�
		}else{
			holder=(Holder) contentView.getTag();
		}
		//Ϊ�ؼ���ֵ
		holder.photo.setImageResource(list.get(position).getImgId());
		holder.content.setText(list.get(position).getContent());
		return contentView;
	}
   class Holder{
	   ImageView photo;
	   TextView content;
   }
}
