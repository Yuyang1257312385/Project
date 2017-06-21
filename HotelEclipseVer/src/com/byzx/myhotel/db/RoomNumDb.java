package com.byzx.myhotel.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 *����ű��һЩ���� 
 * */
public class RoomNumDb {
   /**
    * ��������
    * */
	public static long add(Context context,String roomNum,int roomNumId,int state){
		//����SQliteDatabase����
		HotelSqliteOpenHelper helper=new HotelSqliteOpenHelper(context);
		SQLiteDatabase database=helper.getWritableDatabase();
		//��������
		ContentValues values=new ContentValues();
		values.put("room_num", roomNum);
		values.put("room_num_id", roomNumId);
		values.put("state", state);
		long count=database.insert("room_num_table", null, values);
		return count;
	}
	/**
	 * ͨ������ID��ȡ��Ϣ
	 * */
	public static Cursor getRoomNum(Context context,int roomNumId){
		HotelSqliteOpenHelper helper=new HotelSqliteOpenHelper(context);
		SQLiteDatabase database=helper.getWritableDatabase();
		String sql="select * from room_num_table where room_num_id=?";
		Cursor c=database.rawQuery(sql, new String[]{roomNumId+""});
		return c;
	}
	/**
	 * ���ķ���״̬
	 * ��0��ʾδ��ס��1��ʾ��ס��
	 * */
	public static long updateState(Context context,int state,String roomNum){
		HotelSqliteOpenHelper helper=new HotelSqliteOpenHelper(context);
		SQLiteDatabase database=helper.getWritableDatabase();
		ContentValues values=new ContentValues();
		values.put("state", state);
		//#######debugע��˴���д��"room_num=?"
		long count=database.update("room_num_table", values, "room_num=?", new String[]{roomNum});
	    return count;
	}
}
