package com.byzx.myhotel.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * ������Ϣ�����
 * */
public class RoomInfoDb {
   /**
    * ��������
    * */
	public static long add(Context context,String roomtype,int roomImg1,int roomImg2,
			String roomPrice,String roomDetail,int roomNumId){
		//����һ�����ݿ����
		HotelSqliteOpenHelper helper=new HotelSqliteOpenHelper(context);
		SQLiteDatabase database=helper.getWritableDatabase();
		//���Ҫ�����ֵ
		ContentValues values=new ContentValues();
		values.put("room_type", roomtype);
		values.put("room_preview_img1", roomImg1);
		values.put("room_preview_img2", roomImg2);
		values.put("room_price", roomPrice);
		values.put("room_detail", roomDetail);
		values.put("room_num_id", roomNumId);
		//��values���뵽����
		long count=database.insert("room_info_table", null, values);
		return count;
	}
	/**
	 * ���ݷ������Ͳ�ѯ������Ϣ
	 * */
	public static Cursor getRoomInfoByType(Context context,String roomType){
		//����SQLiteDatabase����
		HotelSqliteOpenHelper helper=new HotelSqliteOpenHelper(context);
		SQLiteDatabase database=helper.getReadableDatabase();
		//���ݷ������Ͳ�ѯ������Ϣ
		String sql="select * from room_info_table where room_type=?";
		Cursor cursor=database.rawQuery(sql, new String[]{roomType});
		return cursor;
	}
	/**
	 * ��ѯ�����Ƿ�������,����ֵ������false����û��ֵ������true
	 * */
	public static boolean getCount(Context context){
		//����SQLiteDatabase����
		HotelSqliteOpenHelper helper=new HotelSqliteOpenHelper(context);
		SQLiteDatabase database=helper.getReadableDatabase();
		//��ѯ�Ƿ���ֵ
		String sql="select * from room_info_table";
		Cursor cursor=database.rawQuery(sql, null);
		if(cursor.getCount()>0){
			cursor.close();
			database.close();
			return false;
		}else{
			cursor.close();
			database.close();
			return true;
		}
	}
}
