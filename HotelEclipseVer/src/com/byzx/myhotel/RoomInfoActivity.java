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
                              ,OnClickListener{//ע���
    //��������
	private TextView mRoomTypeTv,mRoomPriceTv,mRoomDetailTv;
	private ImageView mRoomImg1,mRoomImg2;
	private String mRoomType;
	private RadioButton mRb01,mRb02,mRb03,mRb04,mRb05;
	private RadioGroup mRg;
	private Button mCommitBtn;
	//��Ų鵽�ķ����
	private List<Integer> mRoomNums=new ArrayList<Integer>();
	//��ŵ�ѡ��ť�ؼ�
	private List<RadioButton> mRadioButtons=new ArrayList<RadioButton>();
	//��Ű�������źͷ���״̬��Ϣ��RoomNumBean����
	private List<RoomNumBean> mListRoomNums=new ArrayList<RoomNumBean>();
	//ѡ�еķ����
	private String selectRoomNum=null;
	//ѡ�з���ļ۸�
	private String selectRoomPrice=null;
	//����Id
	private int mRoomNumId=0;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	//ȥ�����⣨������setContentView���棩
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	setContentView(R.layout.activity_room_info);
    	//��ʼ���ؼ�
    	initView();
    	//��ʼ������
    	initData();
    	//���ݷ���Ÿ�����ʾ��ѡ��ť
    	//showRadioButton();
    	//���ݷ���Ÿ�����ʾ��ѡ��ť��ͬʱ���Ѿ���ס�ķ�����Ϊ���ɱ༭
    	showRadioButton2();
    	//ע��
    }
	


	//��ʼ���ؼ�
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
		//����ѡ��ť�ŵ�������ȥ
		mRadioButtons.add(mRb01);
		mRadioButtons.add(mRb02);
		mRadioButtons.add(mRb03);
		mRadioButtons.add(mRb04);
		mRadioButtons.add(mRb05);
		
		mRg=(RadioGroup) findViewById(R.id.rg_select_room_num);
		//ע�������
		mRg.setOnCheckedChangeListener(this);
		mCommitBtn=(Button) findViewById(R.id.btn_select_info);
		mCommitBtn.setOnClickListener(this);
	}
	//��ʼ������
	private void initData() {
		// TODO Auto-generated method stub
		//��intent��ͨ����room_type��ȡ���������
		mRoomType=getIntent().getStringExtra("room_type").toString().trim();
		//�жϻ�ȡ��ֵ�Ƿ�Ϊ��
		if(mRoomType!=null && !mRoomType.equals("")){
			//��ֵ���õ�����ҳ
			mRoomTypeTv.setText(mRoomType);
			//ͨ���������ʹ����ݿ���ȡ�������͵���Ϣ
			Cursor cursor=RoomInfoDb.getRoomInfoByType(this, mRoomType);
			if (cursor!=null && cursor.getCount()>0) {//##########
				while (cursor.moveToNext()) {
					int RoomImg1=cursor.getInt(cursor.getColumnIndex("room_preview_img1"));
					mRoomImg1.setImageResource(RoomImg1);
					int RoomImg2=cursor.getInt(cursor.getColumnIndex("room_preview_img2"));
					mRoomImg1.setImageResource(RoomImg2);
				    selectRoomPrice=cursor.getString(cursor.getColumnIndex("room_price"));
					mRoomPriceTv.setText("��"+selectRoomPrice);
					String RoomDetail=cursor.getString(cursor.getColumnIndex("room_detail"));
					mRoomDetailTv.setText(RoomDetail);
					//��ȡ����Ź���Id
					mRoomNumId=cursor.getInt(cursor.getColumnIndex("room_num_id"));
					//ͨ������Id�õ������
					getRoomNums(mRoomNumId);
				}
			}
		}
	}
    /**
     * ͨ������Id��÷����
     * */
	private void getRoomNums(int roomNumId) {
		// TODO Auto-generated method stub
		//��ռ����е�����
		mRoomNums.clear();
		//ͨ������ID�ӷ���ű��л�÷����
		Cursor c=RoomNumDb.getRoomNum(this, roomNumId);
		if(c!=null && c.getCount()>0){
			while(c.moveToNext()){
				mRoomNums.add(c.getInt(c.getColumnIndex("room_num")));
			}
			c.close();
		}
	}

	   /**
	    * ���ݷ���Ÿ�����ʾ��ѡ��ť��ͬʱ���Ѿ���ס�ķ�����Ϊ���ɱ༭
	    * */
		private void showRadioButton2() {
			// TODO Auto-generated method stub
			if(mRadioButtons!=null && mRadioButtons.size()>0){
				//�����а�ť������
				for(RadioButton rb:mRadioButtons){
					rb.setVisibility(View.INVISIBLE);
				}
				//�����ݿ��ж�ȡ����źͷ���״̬������Щ���ݷ�����󣬽�������뼯��
				mListRoomNums.clear();
				Cursor c=RoomNumDb.getRoomNum(this, mRoomNumId);
				if(c!=null && c.getCount()>0){
					while(c.moveToNext()){
						RoomNumBean bean=new RoomNumBean(c.getString(c.getColumnIndex("room_num")),
								c.getInt(c.getColumnIndex("state")));
						mListRoomNums.add(bean);
					}
				}
				//�������ϣ����ð�ť״̬
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
	 * ���ݷ���ŵĸ�����ʾ��ѡ��ť
	 * */
	private void showRadioButton() {
		// TODO Auto-generated method stub
		//��mRadioButtons��������ֵʱ���ڽ��в���
		if(mRadioButtons!=null && mRadioButtons.size()>0){
			//�Ƚ����еĵ�ѡ��ť������
			for(RadioButton rb:mRadioButtons){
				rb.setVisibility(View.INVISIBLE);//ע��˴�ΪView.
			}
			//���ݷ���ŵļ���mRoomNums�Ե�ѡ��ť���в���
			if(mRoomNums!=null && mRoomNums.size()>0){
				for(int i=0;i<mRoomNums.size();i++){
					//�ɼ�
					mRadioButtons.get(i).setVisibility(View.VISIBLE);
					//��ֵ
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
		Toast.makeText(this, selectRoomNum+"�ŷ���ѡ��", Toast.LENGTH_LONG).show();
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
