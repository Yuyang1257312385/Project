package com.byzx.myhotel;

import java.util.ArrayList;
import java.util.List;

import com.byzx.myhotel.bean.RoomNumBean;
import com.byzx.myhotel.db.RoomInfoDb;
import com.byzx.myhotel.db.RoomNumDb;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class RoomInfoActivity extends Activity implements OnCheckedChangeListener
		,OnClickListener{//注意包
	//声明变量
	private TextView mRoomTypeTv,mRoomPriceTv,mRoomDetailTv;
	private ImageView mRoomImg1,mRoomImg2;
	private String mRoomType;
	private RadioButton mRb01,mRb02,mRb03,mRb04,mRb05;
	private RadioGroup mRg;
	private Button mCommitBtn;
	//存放查到的房间号
	private List<Integer> mRoomNums=new ArrayList<Integer>();
	//存放单选按钮控件
	private List<RadioButton> mRadioButtons=new ArrayList<RadioButton>();
	//存放包含房间号和房间状态信息的RoomNumBean对象
	private List<RoomNumBean> mListRoomNums=new ArrayList<RoomNumBean>();
	//选中的房间号
	private String selectRoomNum=null;
	//选中房间的价格
	private String selectRoomPrice=null;
	//关联Id
	private int mRoomNumId=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//去掉标题（必须在setContentView上面）
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_room_info);
		//初始化控件
		initView();
		//初始化数据
		initData();
		//根据房间号个数显示单选按钮
		//showRadioButton();
		//根据房间号个数显示单选按钮，同时将已经入住的房间设为不可编辑
		showRadioButton2();
		//注册
	}



	//初始化控件
	private void initView() {
		// TODO Auto-generated method stub
		mRoomTypeTv=(TextView) findViewById(R.id.tv_room_type);
		mRoomPriceTv=(TextView) findViewById(R.id.tv_room_price);
		mRoomDetailTv=(TextView) findViewById(R.id.tv_room_detail);
		mRoomImg1=(ImageView) findViewById(R.id.iv_img_01);
		mRoomImg2=(ImageView) findViewById(R.id.iv_img_02);
		mRb01=(RadioButton) findViewById(R.id.rb_room_num01);
		mRb02=(RadioButton) findViewById(R.id.rb_room_num02);
		mRb03=(RadioButton) findViewById(R.id.rb_room_num03);
		mRb04=(RadioButton) findViewById(R.id.rb_room_num04);
		mRb05=(RadioButton) findViewById(R.id.rb_room_num05);
		//将单选按钮放到集合中去
		mRadioButtons.add(mRb01);
		mRadioButtons.add(mRb02);
		mRadioButtons.add(mRb03);
		mRadioButtons.add(mRb04);
		mRadioButtons.add(mRb05);

		mRg=(RadioGroup) findViewById(R.id.rg_select_room_num);
		//注册监听器
		mRg.setOnCheckedChangeListener(this);
		mCommitBtn=(Button) findViewById(R.id.btn_select_info);
		mCommitBtn.setOnClickListener(this);
	}
	//初始化数据
	private void initData() {
		// TODO Auto-generated method stub
		//从intent中通过键room_type获取房间的类型
		mRoomType=getIntent().getStringExtra("room_type").toString().trim();
		//判断获取的值是否为空
		if(mRoomType!=null && !mRoomType.equals("")){
			//将值设置到详情页
			mRoomTypeTv.setText(mRoomType);
			//通过房间类型从数据库中取出该类型的信息
			Cursor cursor=RoomInfoDb.getRoomInfoByType(this, mRoomType);
			if (cursor!=null && cursor.getCount()>0) {//##########
				while (cursor.moveToNext()) {
					int RoomImg1=cursor.getInt(cursor.getColumnIndex("room_preview_img1"));
					mRoomImg1.setImageResource(RoomImg1);
					int RoomImg2=cursor.getInt(cursor.getColumnIndex("room_preview_img2"));
					mRoomImg1.setImageResource(RoomImg2);
					selectRoomPrice=cursor.getString(cursor.getColumnIndex("room_price"));
					mRoomPriceTv.setText("￥"+selectRoomPrice);
					String RoomDetail=cursor.getString(cursor.getColumnIndex("room_detail"));
					mRoomDetailTv.setText(RoomDetail);
					//获取房间号关联Id
					mRoomNumId=cursor.getInt(cursor.getColumnIndex("room_num_id"));
					//通过关联Id得到房间号
					getRoomNums(mRoomNumId);
				}
			}
		}
	}
	/**
	 * 通过关联Id获得房间号
	 * */
	private void getRoomNums(int roomNumId) {
		// TODO Auto-generated method stub
		//清空集合中的内容
		mRoomNums.clear();
		//通过关联ID从房间号表中获得房间号
		Cursor c=RoomNumDb.getRoomNum(this, roomNumId);
		if(c!=null && c.getCount()>0){
			while(c.moveToNext()){
				mRoomNums.add(c.getInt(c.getColumnIndex("room_num")));
			}
			c.close();
		}
	}

	/**
	 * 根据房间号个数显示单选按钮，同时将已经入住的房间设为不可编辑
	 * */
	private void showRadioButton2() {
		// TODO Auto-generated method stub
		if(mRadioButtons!=null && mRadioButtons.size()>0){
			//将所有按钮都隐藏
			for(RadioButton rb:mRadioButtons){
				rb.setVisibility(View.INVISIBLE);
			}
			//从数据库中读取房间号和房间状态，将这些内容放入对象，将对象放入集合
			mListRoomNums.clear();
			Cursor c=RoomNumDb.getRoomNum(this, mRoomNumId);
			if(c!=null && c.getCount()>0){
				while(c.moveToNext()){
					RoomNumBean bean=new RoomNumBean(c.getString(c.getColumnIndex("room_num")),
							c.getInt(c.getColumnIndex("state")));
					mListRoomNums.add(bean);
				}
			}
			//遍历集合，设置按钮状态
			for(int i=0;i<mListRoomNums.size();i++){
				mRadioButtons.get(i).setText(mListRoomNums.get(i).getRoomNum());
				mRadioButtons.get(i).setVisibility(View.VISIBLE);
				if(mListRoomNums.get(i).getState()==1){
					mRadioButtons.get(i).setBackgroundResource(R.drawable.roominfo_roomnum_checked);
					mRadioButtons.get(i).setEnabled(false);

				}
			}
		}
	}
	/**
	 * 根据房间号的个数显示单选按钮
	 * */
	private void showRadioButton() {
		// TODO Auto-generated method stub
		//当mRadioButtons集合中有值时，在进行操作
		if(mRadioButtons!=null && mRadioButtons.size()>0){
			//先将所有的单选按钮都隐藏
			for(RadioButton rb:mRadioButtons){
				rb.setVisibility(View.INVISIBLE);//注意此处为View.
			}
			//根据房间号的集合mRoomNums对单选按钮进行操作
			if(mRoomNums!=null && mRoomNums.size()>0){
				for(int i=0;i<mRoomNums.size();i++){
					//可见
					mRadioButtons.get(i).setVisibility(View.VISIBLE);
					//赋值
					mRadioButtons.get(i).setText(mRoomNums.get(i)+"");
				}
			}
		}
	}


	@Override
	public void onCheckedChanged(RadioGroup arg0, int id) {
		// TODO Auto-generated method stub
		switch (id) {
			case R.id.rb_room_num01:
				selectRoomNum=mRb01.getText().toString().trim();
				break;
			case R.id.rb_room_num02:
				selectRoomNum=mRb02.getText().toString().trim();
				break;
			case R.id.rb_room_num03:
				selectRoomNum=mRb03.getText().toString().trim();
				break;
			case R.id.rb_room_num04:
				selectRoomNum=mRb04.getText().toString().trim();
				break;
			case R.id.rb_room_num05:
				selectRoomNum=mRb05.getText().toString().trim();
				break;
			default:
				break;
		}
		Toast.makeText(this, selectRoomNum+"号房被选中", Toast.LENGTH_LONG).show();
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.btn_select_info:
				if (selectRoomNum!=null && selectRoomPrice!=null) {
					Intent intent = new Intent(this, CheckInActivity.class);
					intent.putExtra("roomNum", selectRoomNum);
					intent.putExtra("roomPrice", selectRoomPrice);
					startActivity(intent);
					finish();
				}
				break;

			default:
				break;
		}
	}
}
