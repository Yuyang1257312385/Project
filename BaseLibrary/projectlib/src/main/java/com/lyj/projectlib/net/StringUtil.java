package com.lyj.projectlib.net;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yu on 2017/8/15.
 */

public class StringUtil {

    public static String getMd5SaltStr(String content){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        String date = sdf.format(new Date());
        String result = getMd5(content + "D01C751CDA791A092E0337C731F473D0" + date);
        //		String result = getMd5(value + Constant.MD5SALTT);
        return result.substring(0, 16);
    }

    /**
     * Md5加密
     *
     * @param str
     * @return
     */
    public static String getMd5(String str) {

        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }

        byte[] byteArray = messageDigest.digest();

        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }

        return md5StrBuff.toString();

    }
}
