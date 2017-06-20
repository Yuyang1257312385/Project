package com.byzx.myhotel.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 房间信息表操作
 * */
public class RoomInfoDb {
	/**
	 * 插入数据
	 * */
	public static long add(Context context,String roomtype,int roomImg1,int roomImg2,
						   String roomPrice,String roomDetail,int roomNumId){
		//产生一个数据库对象
		HotelSqliteOpenHelper helper=new HotelSqliteOpenHelper(context);
		SQLiteDatabase database=helper.getWritableDatabase();
		//存放要插入的值
		ContentValues values=new ContentValues();
		values.put("room_type", roomtype);
		values.put("room_preview_img1", roomImg1);
		values.put("room_preview_img2", roomImg2);
		values.put("room_price", roomPrice);
		values.put("room_detail", roomDetail);
		values.put("room_num_id", roomNumId);
		//将values插入到表中
		long count=database.insert("room_info_table", null, values);
		return count;
	}
	/**
	 * 根据房间类型查询房间信息
	 * */
	public static Cursor getRoomInfoByType(Context context,String roomType){
		//创建SQLiteDatabase对象
		HotelSqliteOpenHelper helper=new HotelSqliteOpenHelper(context);
		SQLiteDatabase database=helper.getReadableDatabase();
		//根据房间类型查询房间信息
		String sql="select * from room_info_table where room_type=?";
		Cursor cursor=database.rawQuery(sql, new String[]{roomType});
		return cursor;
	}
	/**
	 * 查询表里是否有数据,若有值，返回false，若没有值，返回true
	 * */
	public static boolean getCount(Context context){
		//创建SQLiteDatabase对象
		HotelSqliteOpenHelper helper=new HotelSqliteOpenHelper(context);
		SQLiteDatabase database=helper.getReadableDatabase();
		//查询是否有值
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
