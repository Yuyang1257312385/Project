package com.example.yu.jokyeassy.ui.sample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yu.baselibrary.IOC.ChectNet;
import com.example.yu.baselibrary.IOC.OnClick;
import com.example.yu.baselibrary.IOC.ViewById;
import com.example.yu.baselibrary.IOC.ViewUtils;
import com.example.yu.jokyeassy.R;

/**
 * Created by Administrator on 2018/5/1.
 */

public class IocSampleAct extends Activity {

    @ViewById(R.id.tv_test)
    private TextView mTestTv;
    @ViewById(R.id.iv_test)
    private ImageView mTestIv;
    @ViewById(R.id.btn_test)
    private Button mTestBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_ioc_sample);
        ViewUtils.inject(this);
    }

    @ChectNet("哎呀，网络不好")
    @OnClick({R.id.tv_test,R.id.iv_test})
    private void onClick(View view){
        switch (view.getId()){
            case R.id.tv_test:
                Toast.makeText(this,"textView Click",Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_test:
                Toast.makeText(this,"imageView Click",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @ChectNet
    @OnClick(R.id.btn_test)
    private void onBtnClick(){
        Toast.makeText(this,"btn Click",Toast.LENGTH_SHORT).show();
    }

    public static void actionStart(Context context){
        Intent intent = new Intent(context,IocSampleAct.class);
        context.startActivity(intent);
    }
}
