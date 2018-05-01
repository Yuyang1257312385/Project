package com.example.yu.jokyeassy.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yu.baselibrary.IOC.ChectNet;
import com.example.yu.baselibrary.IOC.OnClick;
import com.example.yu.baselibrary.IOC.ViewById;
import com.example.yu.baselibrary.IOC.ViewUtils;
import com.example.yu.jokyeassy.R;
import com.example.yu.jokyeassy.ui.sample.DialogSampleAct;
import com.example.yu.jokyeassy.ui.sample.IocSampleAct;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);
    }

    @OnClick({R.id.btn_ioc_sample,R.id.btn_dialog_sample})
    private void onClick(View view){
        switch (view.getId()){
            case R.id.btn_ioc_sample:
                IocSampleAct.actionStart(this);
                break;
            case R.id.btn_dialog_sample:
                DialogSampleAct.actionStart(this);
                break;
        }
    }
}
