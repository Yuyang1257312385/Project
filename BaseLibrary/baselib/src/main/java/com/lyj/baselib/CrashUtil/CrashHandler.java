package com.lyj.baselib.CrashUtil;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.lyj.baselib.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.SimpleTimeZone;

/**
 * Created by yu on 2017/8/8.
 * <p>
 * 单例的设计模式的异常捕捉
 * <p>
 * 把崩溃的信息保存到内存卡中
 *
 * 使用：
 * 1.CrashHandler.getInstance().init(this.getApplicationContext());
 * 2.获取到异常文件，做相应的处理
 * File[] files = CrashHandler.getInstance().getCrashFiles();
 * 3.若上传的话，上传成功后删除
 *
 * 附： 修改的话看todo
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private final String FILE_NAME = "crash_file";

    private static CrashHandler mInstance;
    //获取应用的一些信息
    private static Context mContext;
    //获取系统默认的异常处理类
    private Thread.UncaughtExceptionHandler mDefaultExceptionHandler;

    public static CrashHandler getInstance() {
        if (mInstance == null) {
            synchronized (CrashHandler.class) {
                if (mInstance == null) {
                    mInstance = new CrashHandler();
                }
            }
        }
        return mInstance;
    }

    public void init(Context context) {
        this.mContext = context;
        //设置全局的异常类为本类
        Thread.currentThread().setUncaughtExceptionHandler(this);
        mDefaultExceptionHandler = Thread.currentThread().getDefaultUncaughtExceptionHandler();
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Log.d("TAG", "异常");

        saveInfoToSdcard(e);

        //TODO 1  是否使用默认的处理
        mDefaultExceptionHandler.uncaughtException(t, e);
    }


    private boolean saveInfoToSdcard(Throwable throwable) {
        String fileName = null;
        StringBuffer stringBuffer = new StringBuffer();
        //1.应用信息
        stringBuffer.append(mContext.getString(R.string.error));
        stringBuffer.append(getExceptionTime()+"\n");
        stringBuffer.append(mContext.getString(R.string.app_info));
        stringBuffer.append(getAppInfo());
        //2.崩溃的详细信息
        stringBuffer.append(mContext.getString(R.string.crash_info));

        stringBuffer.append(getExceptionInfo(throwable));

        //3.手机信息
        stringBuffer.append(mContext.getString(R.string.device_info));
        stringBuffer.append(getMobileInfo());
        stringBuffer.append(mContext.getString(R.string.error_end));

        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            //TODO 2 用什么路径
            //由于6.0需动态申请权限，故放在app目录下
            //File dir = new File(mContext.getFilesDir() + File.separator + "crash" + File.separator);
            File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/BaseLib");

            if(!dir.exists()){
                dir.mkdirs();
            }
            //为完整文件名
            fileName = dir.toString() + File.separator + FILE_NAME + ".txt";
            FileWriter fileWriter = null;
            try {
                //TODO 3 参数二表示是否建一个文件夹
                fileWriter = new FileWriter(fileName,true);
                fileWriter.write(stringBuffer.toString());
                fileWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(fileWriter != null){
                    try {
                        fileWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        return true;
    }

    public  File[] getCrashFiles(){
        //TODO 4 路径修改
        File dir = new File(mContext.getFilesDir() + File.separator + "crash" + File.separator);
        if(dir.isDirectory()){
            File[] files = dir.listFiles();
            if(files.length>0){
                return files;
            }
        }
        return null;
    };

    //TODO 5  若一个文件直接获取该文件即可

    /**
     * 删除crash目录下的文件
     *
     */
    public  boolean deleteCrashFiles() {
        File dir = new File(mContext.getFilesDir() + File.separator + "crash" + File.separator);
        if(dir.isDirectory()){
            File[] files = dir.listFiles();
            if(files.length>0){
                for(File file:files){
                    boolean succ = file.delete();
                    if(!succ){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * 获取应用版本信息
     *
     * @return
     */
    private String getAppInfo() {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            PackageManager pm = mContext.getPackageManager();
            String packageName = mContext.getPackageName();
            stringBuffer.append("包名 = ");
            stringBuffer.append(packageName+ "\n");
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            if(!TextUtils.isEmpty(pi.versionName)){
                stringBuffer.append("versionName = " + pi.versionName + "\n");
            }
            if(!TextUtils.isEmpty(pi.packageName)){
                stringBuffer.append("versionCode = " + pi.versionCode + "\n");
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return stringBuffer.toString();
    }

    /**
     * 异常发生时间
     * @return
     */
    private String getExceptionTime() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    /***
     * 获取异常的信息
     * @param throwable
     * @return
     */
    private String getExceptionInfo(Throwable throwable){
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        throwable.printStackTrace(printWriter);
        printWriter.close();
        return stringWriter.toString()+"\n";
    }

    /**
     * 通过反射获取手机信息
     */
    private String getMobileInfo() {
        StringBuffer sb = new StringBuffer();

        try {
            Field[] fields = Build.class.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String name = field.getName();
                String value = field.get(null).toString();
                sb.append(name + "==" + value);
                sb.append("\n");
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return sb.toString() + "\n";

    }
}
