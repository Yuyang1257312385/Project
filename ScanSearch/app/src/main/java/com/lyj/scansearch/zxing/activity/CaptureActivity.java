package com.lyj.scansearch.zxing.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Handler;

import com.google.zxing.Result;
import com.lyj.scansearch.zxing.view.ViewfinderView;


/**
 * Created by yu on 2016/8/26.
 */
public abstract class CaptureActivity extends Activity {


    public abstract ViewfinderView getViewfinderView();
    public abstract Handler getHandler();
    /**
     * Handler scan result
     *
     * @param result
     * @param barcode
     */
    public abstract void handleDecode(Result result, Bitmap barcode);

    public abstract void drawViewfinder();
}
