package com.lyj.scansearch;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * Created by yu on 2016/8/12.
 *
 * obj  javabean对应的对象
 *json   jsonStr
 * jsonObj  JsonObject
 * list  java集合
 */
public class JsonUtil {
    /**
     * object-->jsonStr
     * */
    public static String obj2Json(Object object){
        return JSON.toJSONString(object,true);
    }
    /**
     * jsonStr-->object
     * */
    public static Object json2Obj(String jsonStr, Class clazz){
        return JSON.parseObject(jsonStr,clazz);
    }

    /**
     * jsonStr-->jsonObj
     * */
    public static JSONObject json2JsonObj(String jsonStr){
        return JSON.parseObject(jsonStr);
    }

    /**
     * jsonObj-->jsonStr
     * */
    public static String jsonObj2Json(JSONObject jsonObject){
        //底层还是调用toJSONString
        return jsonObject.toString();
    }

    /**
     * object-->jsonObject
     * */
    public static JSONObject obj2JsonObj(Object object){
        return (JSONObject) JSON.toJSON(object);
    }
    /**
     * jsonObject-->object
     * */
    public static Object jsonObj2Obj(JSONObject jsonObject, Class clazz){
        return JSON.parseObject(jsonObject.toString(),clazz);
    }

    /**
     *json-->list(json必须为array)
     * */
    public static List json2List(String jsonStr, Class clazz){
        return JSON.parseArray(jsonStr,clazz.getClasses());
    }

}
