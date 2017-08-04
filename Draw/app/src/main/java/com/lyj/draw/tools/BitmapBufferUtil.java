package com.lyj.draw.tools;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by yu on 2017/7/14.
 */

public class BitmapBufferUtil {

    private Bitmap bitmap;
    private Canvas canvas;
    private static BitmapBufferUtil sBitmapBufferUtil;

    private BitmapBufferUtil(int width,int height){
        init(width,height);
    }

    private void init(int width, int height) {
        bitmap = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
    }

    /**
     * todo 此方法调用时机
     *
     * @return
     */
    public static BitmapBufferUtil getInstance(){
        if(sBitmapBufferUtil == null){
            sBitmapBufferUtil = new BitmapBufferUtil(SystemParams.sAreaWidth,SystemParams.sAreaHeight);
        }
        return sBitmapBufferUtil;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }
}
