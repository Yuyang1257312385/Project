package com.byzx.myhotel.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ������
 * */
public class Tools {
   /**
    * ��ȡ��ǰʱ��
    * */
	public static String getDate(){
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//��ȡ��ǰʱ��
		Date date=new Date(System.currentTimeMillis());//�˴�������Util��
		String time=format.format(date);
		return time;
	}
}
