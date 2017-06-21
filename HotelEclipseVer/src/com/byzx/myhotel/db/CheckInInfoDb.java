package com.byzx.myhotel.db;

import java.util.ArrayList;
import java.util.List;

import com.byzx.myhotel.bean.CheckInInfoBean;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * �Ǽ���Ϣ����
 * */
public class CheckInInfoDb {
   /**
    * ��ӵǼ���Ϣ
    * */
	
	public static long add(Context context,String roomNum,String roomPrice,
			String clientName,String clientId,String clientSex,String checkInTime,int state){
		HotelSqliteOpenHelper helper=new HotelSqliteOpenHelper(context);
		SQLiteDatabase database=helper.getWritableDatabase();
		ContentValues values=new ContentValues();
		values.put("room_num", roomNum);
		values.put("room_price", roomPrice);
		values.put("client_name", clientName);
		values.put("client_id", clientId);
		values.put("client_sex", clientSex);
		values.put("check_in_time", checkInTime);
		values.put("state", state);
		long count = database.insert("check_in_info_table", null, values);
		database.close();
		return count;
	}
	/**
	 * ��ѯ���еǼ���Ϣ
	 * */
	public static Cursor findAll(Context context,int state){
		HotelSqliteOpenHelper helper=new HotelSqliteOpenHelper(context);
		SQLiteDatabase database=helper.getReadableDatabase();
		String sql="select * from check_in_info_table where state=? order by check_in_time";
		Cursor c=database.rawQuery(sql,new String[]{state+""});
		return c;
	}
	/**
	 * ���ݷ���������ѯ���еĵǼ���Ϣ,֧��ģ����ѯ
	 * */
	public static Cursor findAllByClientName(Context context,String search,int state){
		HotelSqliteOpenHelper helper=new HotelSqliteOpenHelper(context);
		SQLiteDatabase database=helper.getReadableDatabase();
		String sql="select * from check_in_info_table where client_name like ? and state=? order by check_in_time";
		Cursor c=database.rawQuery(sql, new String[]{"%"+search+"%",state+""});
		return c;
	}
	/**
	 * ���ݷ���Ų�ѯ�Ǽ���Ϣ
	 * */
	public static Cursor findAllByRoomNum(Context context,String search,int state){
		HotelSqliteOpenHelper helper=new HotelSqliteOpenHelper(context);
		SQLiteDatabase database=helper.getReadableDatabase();
		String sql="select * from check_in_info_table where room_num=? and state=?";
		Cursor c=database.rawQuery(sql, new String[]{search,state+""});
		return c;
	}
	
	/**
	 * ���ݷ���������ѯ���еǼ���Ϣ��������洢��List<CheckInInfoBean>��
	 * */
	public static List<CheckInInfoBean> findAllChecked(Context context,String search,int state,int clickBtn){
		Cursor c=null;
		if(search!=null && !search.equals("")){
			if(clickBtn==1){
				c=findAllByRoomNum(context, search, state);
			} else {
				c=findAllByClientName(context,search,state);
			}
			
		}else{
			c=findAll(context,state);
		}
		
		List<CheckInInfoBean> beans=new ArrayList<CheckInInfoBean>();
		if(c!=null && c.getCount()>0){
			while(c.moveToNext()){
				
				CheckInInfoBean bean=new CheckInInfoBean();
				bean.setmClientName(c.getString(c.getColumnIndex("client_name")));
				bean.setmClientSex(c.getString(c.getColumnIndex("client_sex")));
				bean.setmClientId(c.getString(c.getColumnIndex("client_id")));
				bean.setmRoomNum(c.getString(c.getColumnIndex("room_num")));
				bean.setmRoomPrice(c.getString(c.getColumnIndex("room_price")));
				bean.setmCheckInTime(c.getString(c.getColumnIndex("check_in_time")));
				beans.add(bean);
			}
		}
		c.close();
		return beans;
		
	}
	/**
	 * �޸���ס��Ϣ��״̬��0Ϊδ��ס��1Ϊ��ס��
	 * */
	public static long update(Context context,String roomNum,int state){
		HotelSqliteOpenHelper helper=new HotelSqliteOpenHelper(context);
		SQLiteDatabase database=helper.getReadableDatabase();
		ContentValues values=new ContentValues();
		values.put("state", state);
		long count=database.update("check_in_info_table", values, "room_Num=?", new String[]{roomNum});
		return count;
	}
}
