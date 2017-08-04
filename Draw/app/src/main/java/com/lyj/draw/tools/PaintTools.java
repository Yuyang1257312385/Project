package com.lyj.draw.tools;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by yu on 2017/7/14.
 *
 * 定义绘图的 颜色 线条宽度 空心&实心
 *
 * 对外提供paint单例对象,
 */

public class PaintTools {

    private int color;//颜色
    private int borderWidth;//线条宽度
    private boolean fill ;//空心还是实心

    private static PaintTools sPaintTools;
    private static Paint paint;

    /**
     * 构造方法私有，防止外部创建
     */
    private PaintTools(){
        reset();
    }

    private void reset() {
        this.color = Color.BLUE;
        this.borderWidth = 10;
        this.fill = false;
    }

    public static PaintTools getInstance(){
        if(sPaintTools == null){
            sPaintTools = new PaintTools();
        }
        return sPaintTools;
    }

    public Paint getPaint() {
        if(paint == null){
            paint = new Paint();
        }
        paint.setAntiAlias(true);
        paint.setColor(this.color);
        paint.setStrokeWidth(borderWidth);
        paint.setStyle(fill ? Paint.Style.FILL : Paint.Style.STROKE);
        return paint;
    }


    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
    }

    public boolean isFill() {
        return fill;
    }

    public void setFill(boolean fill) {
        this.fill = fill;
    }




}
