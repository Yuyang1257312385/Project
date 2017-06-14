package com.example.mytrip.base;

import android.app.Activity;
import android.os.Bundle;

import com.example.mytrip.tools.SPUtils;

/**
 * Created by yu on 2017/5/24.
 *
 * Activity的基类
 */

public class BaseActivity extends Activity {

    public SPUtils spUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBaseParams();
    }

    private void initBaseParams() {
        spUtils = new SPUtils(this);
    }
}
