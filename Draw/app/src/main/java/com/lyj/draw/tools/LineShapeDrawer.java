package com.lyj.draw.tools;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by yu on 2017/7/14.
 */

public class LineShapeDrawer extends ShapeDrawer {

    private int mPreX,mPreY;
    private Path mPath;
    private View mView;


    public LineShapeDrawer(View view) {
        super(view);
        mView = view;
        mPath = new Path();
    }

    @Override
    public void draw(Canvas canvas) {
        Bitmap bitmapBuf = BitmapBufferUtil.getInstance().getBitmap();
        if(bitmapBuf != null){
            canvas.drawBitmap(bitmapBuf,0,0,null);
        }
    }

    @Override
    public void dealTouchEvent(MotionEvent motionEvent) {
        int currentX = (int) motionEvent.getX();
        int currentY = (int) motionEvent.getY();

        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                mPreX = currentX;
                mPreY = currentY;
                mPath.moveTo(mPreX,mPreY);
                break;
            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(currentX,currentY);
                BitmapBufferUtil.getInstance().getCanvas()
                        .drawPath(mPath, PaintTools.getInstance().getPaint());
                mPreX = currentX;
                mPreY = currentY;
                mView.invalidate();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
    }


}
