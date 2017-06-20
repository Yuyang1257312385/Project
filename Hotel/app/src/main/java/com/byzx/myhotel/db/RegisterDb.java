package com.byzx.myhotel.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

/**
 * 对注册用户表的操作
 * */
public class RegisterDb {
	/**
	 * 注册
	 * */
	public static boolean register(Context context,String username,String password){
		if(isUserExist(context, username)==0){HotelSqliteOpenHelper helper=new HotelSqliteOpenHelper(context);
			SQLiteDatabase db=helper.getWritableDatabase();
			ContentValues values=new ContentValues();
			values.put("username", username);
			values.put("password", password);
			long col=db.insert("register_table", null, values);
			if(col>0){
				db.close();//######
				return true;
			}else{
				db.close();
				return false;

			}
		}else{
			return false;
		}
	}
	/**
	 * 判断用户是否存在
	 * */
	public static int isUserExist(Context context,String username){
		HotelSqliteOpenHelper helper=new HotelSqliteOpenHelper(context);
		SQLiteDatabase database=helper.getReadableDatabase();
		String sql="select * from register_table where username=?";
		Cursor c=database.rawQuery(sql, new String[]{username});
		return c !=null?c.getCount():0;
	}
	/**
	 * 登录
	 * */
	public static boolean login(Context context,String username,String password){
		HotelSqliteOpenHelper helper=new HotelSqliteOpenHelper(context);
		SQLiteDatabase db=helper.getReadableDatabase();
		String sql="select * from register_table where username=? and password=?";
		Cursor c=db.rawQuery(sql, new String[]{username,password});
		if(c.getCount()>0){
			db.close();
			return true;

		}else{
			db.close();
			return false;
		}

	}
}
