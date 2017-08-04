package com.ispring.planeghost;

import android.app.Activity;
import android.os.Bundle;

import com.ispring.gameplane.R;

import cn.waps.AppConnect;

/**
 * Created by yu on 2017/7/28.
 */

public class LaunchAct extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_launch);
        //预加载广告数据
        AppConnect.getInstance(this).initPopAd(this);
        //显示
        AppConnect.getInstance(this).showPopAd(this);
    }


}
