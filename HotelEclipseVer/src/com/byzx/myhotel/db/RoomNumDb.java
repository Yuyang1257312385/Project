package com.byzx.myhotel.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 *房间号表的一些操作 
 * */
public class RoomNumDb {
   /**
    * 插入数据
    * */
	public static long add(Context context,String roomNum,int roomNumId,int state){
		//创建SQliteDatabase对象
		HotelSqliteOpenHelper helper=new HotelSqliteOpenHelper(context);
		SQLiteDatabase database=helper.getWritableDatabase();
		//插入数据
		ContentValues values=new ContentValues();
		values.put("room_num", roomNum);
		values.put("room_num_id", roomNumId);
		values.put("state", state);
		long count=database.insert("room_num_table", null, values);
		return count;
	}
	/**
	 * 通过关联ID获取信息
	 * */
	public static Cursor getRoomNum(Context context,int roomNumId){
		HotelSqliteOpenHelper helper=new HotelSqliteOpenHelper(context);
		SQLiteDatabase database=helper.getWritableDatabase();
		String sql="select * from room_num_table where room_num_id=?";
		Cursor c=database.rawQuery(sql, new String[]{roomNumId+""});
		return c;
	}
	/**
	 * 更改房间状态
	 * 【0表示未入住，1表示入住】
	 * */
	public static long updateState(Context context,int state,String roomNum){
		HotelSqliteOpenHelper helper=new HotelSqliteOpenHelper(context);
		SQLiteDatabase database=helper.getWritableDatabase();
		ContentValues values=new ContentValues();
		values.put("state", state);
		//#######debug注意此处的写法"room_num=?"
		long count=database.update("room_num_table", values, "room_num=?", new String[]{roomNum});
	    return count;
	}
}
