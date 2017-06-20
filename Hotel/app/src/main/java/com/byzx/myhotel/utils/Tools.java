package com.byzx.myhotel.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 工具类
 * */
public class Tools {
	/**
	 * 获取当前时间
	 * */
	public static String getDate(){
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//获取当前时间
		Date date=new Date(System.currentTimeMillis());//此处导的是Util包
		String time=format.format(date);
		return time;
	}
}
