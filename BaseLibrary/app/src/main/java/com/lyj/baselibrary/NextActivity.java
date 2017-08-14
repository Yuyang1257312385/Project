package com.lyj.baselibrary;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by yu on 2017/8/8.
 */

public class NextActivity extends Activity implements View.OnClickListener{

    private Button mBugBtn;
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_next);
        mBugBtn = (Button) findViewById(R.id.btn_bug);
        intiAction();
    }

    private void intiAction() {
        mBugBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_bug:
                //if(tv != null){
                    tv.setText("");
                //}
                break;
        }
    }
}
