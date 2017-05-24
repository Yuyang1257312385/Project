package com.example.myontheway01.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.myontheway01.MyApp;


public final class ActivityUtil {

   
    public static int[] getScreenSize() {
        int[] screens;
        // if (Constants.screenWidth > 0) {
        // return screens;
        // }
        DisplayMetrics dm=new DisplayMetrics();
        dm=MyApp.getInstance().getResources().getDisplayMetrics();
        screens=new int[]{dm.widthPixels, dm.heightPixels};
        return screens;
    }

    public static float[] getBitmapConfiguration(Bitmap bitmap, ImageView imageView, float screenRadio) {
        int screenWidth=ActivityUtil.getScreenSize()[0];
        float rawWidth=0;
        float rawHeight=0;
        float width=0;
        float height=0;
        if(bitmap == null) {
            // rawWidth = sourceWidth;
            // rawHeight = sourceHeigth;
            width=(float)(screenWidth / screenRadio);
            height=(float)width;
            imageView.setScaleType(ScaleType.FIT_XY);
        } else {
            rawWidth=bitmap.getWidth();
            rawHeight=bitmap.getHeight();
            if(rawHeight > 10 * rawWidth) {
                imageView.setScaleType(ScaleType.CENTER);
            } else {
                imageView.setScaleType(ScaleType.FIT_XY);
            }
            float radio=rawHeight / rawWidth;

            width=(float)(screenWidth / screenRadio);
            height=(float)(radio * width);
        }
        return new float[]{width, height};
    }

 
    public static String getVersionName(Context context) {
        PackageManager pm=context.getPackageManager();
        try {
            PackageInfo info=pm.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch(NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }

 

  
    public static void openBrowser(Context context, String urlText) {
        Intent intent=new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri url=Uri.parse(urlText);
        intent.setData(url);
        context.startActivity(intent);
    }

    /**
     * 切换全屏状�?�?
     * @param activity Activity
     * @param isFull 设置为true则全屏，否则非全�?
     */
    public static void toggleFullScreen(Activity activity, boolean isFull) {
        hideTitleBar(activity);
        Window window=activity.getWindow();
        WindowManager.LayoutParams params=window.getAttributes();
        if(isFull) {
            params.flags|=WindowManager.LayoutParams.FLAG_FULLSCREEN;
            window.setAttributes(params);
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            params.flags&=(~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            window.setAttributes(params);
            window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    /**
     * 设置为全�?
     * @param activity Activity
     */
    public static void setFullScreen(Activity activity) {
        toggleFullScreen(activity, true);
    }

    /**
     * 获取系统状�?栏高�?
     * @param activity Activity
     * @return 状�?栏高�?
     */
    public static int getStatusBarHeight(Activity activity) {
        try {
            Class<?> clazz=Class.forName("com.android.internal.R$dimen");
            Object object=clazz.newInstance();
            Field field=clazz.getField("status_bar_height");
            int dpHeight=Integer.parseInt(field.get(object).toString());
            return activity.getResources().getDimensionPixelSize(dpHeight);
        } catch(Exception e1) {
            e1.printStackTrace();
            return 0;
        }
    }

    /**
     * 隐藏Activity的系统默认标题栏
     * @param activity Activity
     */
    public static void hideTitleBar(Activity activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    /**
     * 强制设置Actiity的显示方向为垂直方向�?
     * @param activity Activity
     */
    public static void setScreenVertical(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * 强制设置Activity的显示方向为横向�?
     * @param activity Activity
     */
    public static void setScreenHorizontal(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    /**
     * 隐藏软件输入�?
     * @param activity Activity
     */
    public static void hideSoftInput(Activity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    /**
     * 关闭已经显示的输入法窗口�?
     * @param context 上下文对象，�?��为Activity
     * @param focusingView 输入法所在焦点的View
     */
    public static void closeSoftInput(Context context, View focusingView) {
        InputMethodManager imm=(InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(focusingView.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    /**
     * 使UI适配输入�?
     * @param activity Activity
     */
    public static void adjustSoftInput(Activity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    public interface MessageFilter {

        String filter(String msg);
    }

    public static MessageFilter msgFilter;

   
   
 
  
   
  

    /**
     * 根据手机的分辨率�?dp 的单�?转成�?px(像素)
     * @param context 上下文，�?��为Activity
     * @param dpValue dp数据�?
     * @return px像素�?
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale=context.getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5f);
    }


  
   
    
    /**
     * 得到view高度
     * @param w
     * @param bmw
     * @param bmh
     * @return
     */
    public static int getViewHeight(int w, int bmw, int bmh) {
        return w * bmh / bmw;
    }

    /**
     * 获取屏幕宽高
     * @param activity
     * @return
     */
    public static int[] getScreenSize(Activity activity) {
        int[] screens;
        // if (Constants.screenWidth > 0) {
        // return screens;
        // }
        DisplayMetrics dm=new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        screens=new int[]{dm.widthPixels, dm.heightPixels};
        return screens;
    }

    // MD5変換
    public static String Md5(String str) {
        if(str != null && !str.equals("")) {
            try {
                MessageDigest md5=MessageDigest.getInstance("MD5");
                char[] HEX={'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
                byte[] md5Byte=md5.digest(str.getBytes("UTF8"));
                StringBuffer sb=new StringBuffer();
                for(int i=0; i < md5Byte.length; i++) {
                    sb.append(HEX[(int)(md5Byte[i] & 0xff) / 16]);
                    sb.append(HEX[(int)(md5Byte[i] & 0xff) % 16]);
                }
                str=sb.toString();
            } catch(NoSuchAlgorithmException e) {

            } catch(Exception e) {
            }
        }
        return str;
    }


    /**
     * 判断网络连接是否可用
     * @param context
     * @return
     */
    public static boolean hasNetwork(Context context) {
        // 获取手机�?��连接管理对象（包括wifi，net等连接的管理�?
        ConnectivityManager connectivity=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivity != null) {
            // 获取网络连接管理的对�?
            NetworkInfo info=connectivity.getActiveNetworkInfo();
            if(info != null && info.isConnected()) {
                // 判断当前网络是否已经连接
                if(info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;

    }

    /**
     * 安装�?��APK文件
     * @param file
     */
    public static void installApk(Context context, File file) {
        Intent intent=new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /**
     * 将指定byte数组转换�?6进制大写字符�?
     * @param b
     * @return
     */
    public static String byteToHexString(byte[] b) {
        StringBuffer hexString=new StringBuffer();
        for(int i=0; i < b.length; i++) {
            String hex=Integer.toHexString(b[i] & 0xFF);
            if(hex.length() == 1) {
                hex='0' + hex;
            }
            hexString.append(hex.toUpperCase());
        }
        return hexString.toString();
    }

   

   
    public static byte[] load(InputStream is) throws IOException {
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        byte[] buffer=new byte[1024];
        int len;
        while((len=is.read(buffer)) != -1)
            baos.write(buffer, 0, len);
        baos.close();
        is.close();
        return baos.toByteArray();
    }

   
    public static byte[] getFileByte(File file) {
        if(!file.exists()) {
            return null;
        }
        try {
            FileInputStream fis=new FileInputStream(file);
            int len=fis.available();
            byte[] bytes=new byte[len];
            fis.read(bytes);
            fis.close();
            return bytes;
        } catch(Exception e) {

        }

        return null;
    }

    /**
     * 根据图片大小得到合�?的缩放率
     * @param value
     * @return
     */
    public static int getSimpleNumber(int value) {
        if(value > 30) {
            return 1 + getSimpleNumber(value / 4);
        } else {
            return 1;
        }
    }

    /**
     * 大数组（String）获取相同元�?大致思路�?1.首先将两个数组A、B排序(递增)<br>
     * 2.分别从A和B中各取出�?��素a,b，对a和b进行�?较：<br>
     * 1) 如果a与b相等，则将a或b存入�?��定集合中<br>
     * 2)如果a小于b，则继续取A的下�?��素，再与b�?�?br>
     * 3) 如果a大于b，则取B的下�?��元素，与a进行比较<br>
     * 3.反复进行步骤2，知道A或B的元素都比较�?br>
     * 4.返回集合(存了相同的元�?<br>
     * @param strArr1
     * @param strArr2
     * @return
     */
    public static List<String> getAllSameElement2(String[] strArr1, String[] strArr2) {
        if(strArr1 == null || strArr2 == null) {
            return null;
        }
        Arrays.sort(strArr1);
        Arrays.sort(strArr2);
        List<String> list=new ArrayList<String>();
        int k=0;
        int j=0;
        while(k < strArr1.length && j < strArr2.length) {
            if(strArr1[k].compareTo(strArr2[j]) == 0) {
                if(strArr1[k].equals(strArr2[j])) {
                    list.add(strArr1[k]);
                    k++;
                    j++;
                }
                continue;
            } else if(strArr1[k].compareTo(strArr2[j]) < 0) {
                k++;
            } else {
                j++;
            }
        }
        return list;
    }

    /**
     * 获取当前的年、月、日 对应的时�?
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static long getTime() {
        Date d=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String dateNowStr=sdf.format(d);
        // System.out.println("格式化后的日期：" + dateNowStr);
        Date d2=null;
        try {
            d2=sdf.parse(dateNowStr);
            // System.out.println(d2);
            // System.out.println(d2.getTime());
            return d2.getTime();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    // I added a generic return type to reduce the casting noise in client code
    @SuppressWarnings("unchecked")
    public static <T extends View> T get(View view, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }

}
