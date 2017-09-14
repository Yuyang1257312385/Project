package com.lyj.projectlib.net.cookie;


import com.qh.hy.hzg.base.App;
import com.qh.hy.hzg.base.Config;
import com.qh.hy.hzg.tools.PreUtil;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Created by Administrator on 2017/2/10.
 */

public class CookiesManager implements CookieJar {

    private final PersistentCookieStore cookieStore = new PersistentCookieStore(App.getInstance().getApplicationContext());

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies != null && cookies.size() > 0) {
            for (Cookie item : cookies) {
               // HttpUrl httpUrl = new HttpUrl.Builder().
//                HttpUrl httpUrl = new HttpUrl.Builder().host(Config.CTPBASEURL).build();
                //// TODO: 2017/8/18
                cookieStore.add("test", item);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
       // HttpUrl httpUrl = new HttpUrl.Builder().host(Config.CTPBASEURL).build();
        //// TODO: 2017/8/18
        List<Cookie> cookies = cookieStore.get("test");
        Cookie cookie = new Cookie.Builder()
                .domain(url.host())
                .name("Cookie")
                .value("MTPSESSIONID="+ PreUtil.getPre("Cookies_Prefs").getString("MTPSESSIONID","")).build();
        cookies.add(cookie);

        return cookies;
    }
}
