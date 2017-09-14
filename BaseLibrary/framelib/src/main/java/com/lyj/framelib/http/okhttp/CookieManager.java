package com.lyj.framelib.http.okhttp;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Created by yu on 2017/8/15.
 */

public class CookieManager implements CookieJar {


    private Context mContext;

    public CookieManager(Context context){
        mContext = context;
    }

    private final PersistentCookieStore cookieStore = new PersistentCookieStore(mContext);

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies != null && cookies.size() > 0) {
            for (Cookie item : cookies) {
                // HttpUrl httpUrl = new HttpUrl.Builder().
//                HttpUrl httpUrl = new HttpUrl.Builder().host(Config.CTPBASEURL).build();
                cookieStore.add("suibian", item);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        // HttpUrl httpUrl = new HttpUrl.Builder().host(Config.CTPBASEURL).build();

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("Cookies_Prefs",Context.MODE_PRIVATE);
        List<Cookie> cookies = cookieStore.get("suibian");
        Cookie cookie = new Cookie.Builder()
                .domain(url.host())
                .name("Cookie")
                .value("MTPSESSIONID="+ sharedPreferences.getString("MTPSESSIONID","")).build();
        cookies.add(cookie);

        return cookies;
    }
}
