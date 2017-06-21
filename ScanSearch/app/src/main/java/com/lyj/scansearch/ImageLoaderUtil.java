package com.lyj.scansearch;

import android.graphics.Color;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;

/**
 * Created by yu on 2017/6/2.
 */

public class ImageLoaderUtil {
    public static void displayImage(String url,ImageView imageView){
        DisplayImageOptions options  = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_stub)
                .showImageForEmptyUri(R.drawable.ic_empty)
                .showImageOnFail(R.drawable.ic_error)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .considerExifParams(true)
                //.displayer(new CircleBitmapDisplayer(Color.WHITE, 5))
                .build();
        ImageLoader.getInstance().displayImage(url,imageView,options);
    }
}
