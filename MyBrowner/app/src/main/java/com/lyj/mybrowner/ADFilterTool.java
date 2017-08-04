package com.lyj.mybrowner;

import android.content.Context;
import android.content.res.Resources;

/**
 * Created by yu on 2017/8/1.
 */

public class ADFilterTool  {
    public static boolean hasAd(Context context, String url) {
        Resources res = context.getResources();
        String[] adUrls = res.getStringArray(R.array.adBlockUrl);
        for (String adUrl : adUrls) {
            if (url.contains(adUrl)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasPorn(Context context, String content) {
        Resources res = context.getResources();
        String[] adUrls = res.getStringArray(R.array.pornBlockUrl);
        for (String adUrl : adUrls) {
            if (content.contains(adUrl)) {
                return true;
            }
        }
        return false;
    }
}
