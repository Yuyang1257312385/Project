package com.example.yu.baselibrary.IOC;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2018/4/30.
 */

public class ViewUtils {

    public static void inject(Activity activity){
        inject(new ViewFinder(activity),activity);
    }

    public static void inject(View view){
        inject(new ViewFinder(view),view);
    }

    public static void inject(View view,Object object){
        inject(new ViewFinder(view),object);
    }

    private static void inject(ViewFinder finder,Object object){
        injectField(finder,object);
        injectEvent(finder,object);
    }

    /**
     * 字段注入
     * @param finder  该参数用于获取View
     * @param object  被反射的类
     */
    private static void injectField(ViewFinder finder, Object object) {
        //1. 获取到字段
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        //2. 获取到字段对应的value;
        for(Field field:fields){
            ViewById viewById = field.getAnnotation(ViewById.class);
            if(viewById != null){
                int viewId = viewById.value();
                //3. 获取到view
                View view = finder.findViewById(viewId);
                //4. 动态的注入view
                if(view != null){
                    //private的字段也可以设置
                    field.setAccessible(true);
                    try {
                        field.set(object,view);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

    /**
     * 注入事件
     * @param finder
     * @param object
     */
    private static void injectEvent(ViewFinder finder, Object object) {
        //1. 获取方法
        Class<?> clazz = object.getClass();
        Method[]  methods = clazz.getDeclaredMethods();
        //2. 获取添加Onclick注解方法的值
        for(Method method : methods){
            OnClick onClick = method.getAnnotation(OnClick.class);
            if(onClick != null){
                int[] viewIds = onClick.value();
                for(int viewId : viewIds){
                    //3. 获取View
                    View view = finder.findViewById(viewId);
                    if(view != null){
                        //====扩展 检查网络===
                        String tip = "";
                        ChectNet chectNet = method.getAnnotation(ChectNet.class);
                        if(chectNet != null){
                            tip = chectNet.value();
                        }
                        //====扩展 检查网络 end===


                        //4. 设置点击事件
                        view.setOnClickListener(new DeclaredOnClickListener(method,object,tip));
                    }
                }
            }
        }

    }



    private static class DeclaredOnClickListener implements View.OnClickListener {
        private Method mMethod;
        private Object mObject;
        private String mNetTip;

        public DeclaredOnClickListener(Method method, Object object,String tip) {
            mMethod = method;
            mObject = object;
            mNetTip = tip;
        }

        @Override
        public void onClick(View v) {
            if(!TextUtils.isEmpty(mNetTip)){
                if(!isNetWorkAvailable(v.getContext())){
                    Toast.makeText(v.getContext(),mNetTip,Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            mMethod.setAccessible(true);
            try {
                //5. 动态执行方法
                mMethod.invoke(mObject,v);
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    mMethod.invoke(mObject,null);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    private static boolean isNetWorkAvailable(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isAvailable()){
            return true;
        }
        return false;
    }
}
