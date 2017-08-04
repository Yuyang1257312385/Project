package com.lyj.draw.tools;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by yu on 2017/7/14.
 */

public class RectShapeDrawer extends ShapeDrawer {

    private int mPreX,mPreY;

    private Path mPath;

    Canvas canvas = BitmapBufferUtil.getInstance().getCanvas();
    Paint paint = PaintTools.getInstance().getPaint();


    public RectShapeDrawer(View view) {
        super(view);
        mPath = new Path();
        canvas = BitmapBufferUtil.getInstance().getCanvas();
        paint = PaintTools.getInstance().getPaint();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawPath(mPath,PaintTools.getInstance().getPaint());
        canvas.drawBitmap(BitmapBufferUtil.getInstance().getBitmap(),0,0,null);
    }

    @Override
    public void dealTouchEvent(MotionEvent motionEvent) {
        int currentX = (int) motionEvent.getX();
        int currentY = (int) motionEvent.getY();
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                mPreX = currentX;
                mPreY = currentY;
                break;
            case MotionEvent.ACTION_MOVE:
                mPath.reset();

                int startX = Math.min(mPreX,currentX);
                int startY = Math.min(mPreY,currentY);
                int endX = Math.max(mPreX,currentX);
                int endY = Math.max(mPreY,currentY);
                mPath.addRect(new RectF(startX,startY,endX,endY), Path.Direction.CCW);

                view.invalidate();
                break;
            case MotionEvent.ACTION_UP:
                canvas.drawPath(mPath,paint);
                view.invalidate();
                break;
        }
    }
}
