package com.lyj.draw.tools;

import android.graphics.Bitmap;

import java.util.Stack;

/**
 * Created by yu on 2017/7/14.
 *
 * 撤销
 */

public class BitmapHistoryUtil {

    private static Stack<Bitmap> sBitmapStack;

    private static BitmapHistoryUtil sBitmapHistoryUtil;

    private BitmapHistoryUtil(){
        if(sBitmapStack == null){
            sBitmapStack = new Stack<>();
        }
    }

    public static BitmapHistoryUtil getInstance(){
        if(sBitmapHistoryUtil == null){
            sBitmapHistoryUtil = new BitmapHistoryUtil();
        }
        return sBitmapHistoryUtil;
    }

    public void push(Bitmap bitmap){
        if(bitmap != null){
            if(sBitmapStack.size()>5){
                Bitmap bitmapRemove = sBitmapStack.remove(0);
                if(!bitmapRemove.isRecycled()){
                    bitmapRemove.recycle();
                    System.gc();
                    bitmapRemove = null;
                }
            }
            sBitmapStack.add(bitmap);
        }
    }

    public void repo(){
        
    }

}
