package com.byzx.myhotel.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * 创建数据库
 * @author LYJ
 * */
public class HotelSqliteOpenHelper extends SQLiteOpenHelper{

	public HotelSqliteOpenHelper(Context context) {
		super(context, "usermessage.db", null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		/**
		 * 建注册表register_table
		 * */
		String Register_sql="create table register_table(id integer primary key autoincrement,username text,password text)";
		db.execSQL(Register_sql);
		/**
		 * 建房间信息表room_info_table
		 * */
		String Roon_info_sql="create table room_info_table(id integer primary key autoincrement,room_type text," +
				"room_preview_img1 integer,room_preview_img2 integer,room_price text,room_detail text,room_num_id integer)";
		db.execSQL(Roon_info_sql);
		/**
		 * 建房号表room_num_table
		 * */
		String room_num_sql="create table room_num_table(id integer primary key autoincrement," +
				"room_num text,room_num_id integer,state integer)";
		db.execSQL(room_num_sql);
		/**
		 * 建登记信息表check_in_info_table
		 * */
		String check_in_info_sql="create table check_in_info_table(id integer primary key autoincrement," +
				"room_num text,room_price text,client_name text,client_id text,client_sex text,check_in_time text,state integer)";
		db.execSQL(check_in_info_sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
