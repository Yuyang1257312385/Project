package com.lyj.draw.customwidget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.lyj.draw.tools.PaintTools;
import com.lyj.draw.tools.ShapeDrawer;
import com.lyj.draw.tools.SystemParams;

/**
 * Created by yu on 2017/7/14.
 */

public class DrawView extends View {

    private ShapeDrawer shapeDrawer;

    public DrawView(Context context) {
        super(context);
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setShapeDrawer(ShapeDrawer shapeDrawer) {
        this.shapeDrawer = shapeDrawer;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        SystemParams.sAreaWidth = this.getMeasuredWidth();
        SystemParams.sAreaHeight = this.getMeasuredHeight();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
       shapeDrawer.dealTouchEvent(event);
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        shapeDrawer.draw(canvas);
    }
}
