package com.lyj.draw.tools;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by yu on 2017/7/14.
 */

public abstract class ShapeDrawer {
    public View view;


    public ShapeDrawer(View view) {
        this.view = view;
    }

    public abstract void draw(Canvas canvas);
    public abstract void dealTouchEvent(MotionEvent motionEvent);
}
