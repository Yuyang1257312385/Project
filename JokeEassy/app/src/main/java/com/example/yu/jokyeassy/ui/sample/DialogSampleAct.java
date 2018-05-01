package com.example.yu.jokyeassy.ui.sample;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yu.baselibrary.IOC.OnClick;
import com.example.yu.baselibrary.IOC.ViewUtils;
import com.example.yu.baselibrary.dialog.BaseDialog;
import com.example.yu.jokyeassy.R;

/**
 * Created by Administrator on 2018/5/1.
 */

public class DialogSampleAct extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_dialog_sample);
        ViewUtils.inject(this);
    }

    @OnClick({R.id.btn_show_dialog})
    private void onClick(View v){
        switch (v.getId()){
            case R.id.btn_show_dialog:
                showDialog();
                break;
        }
    }

    private void showDialog(){


        DialogInterface.OnClickListener onShareClickListener = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(DialogSampleAct.this,"onShareClick",Toast.LENGTH_SHORT).show();
            }
        };

        final BaseDialog alertDialog = new BaseDialog.Builder(this)
                //设置dialog的布局，必须有，可以是id或view
                .setContentView(R.layout.dialog_layout)
                //宽度设置全屏
                .setFullWidth()
                //设置自定义的宽高
//                .setWidthAndHeight(100,100)

                //设置在屏幕中的位置
                .setGravity(Gravity.BOTTOM)

                //为R.id.btn_confirm 设置文字
                .setText(R.id.btn_confirm,"确认")

                //为viewid为R.id.btn_share 设置点击监听,或者可以直接对alderDialog设置监听
                .setOnClickListener(R.id.btn_share, onShareClickListener)
                //点击空白出是否可以取消，true 可以取消  false 不可取消
                .setCancelable(true)

                //设置默认的展示动画  从中心展开缩放动画
                .setDefaultAnimation()
                //从底部进入，true展示动画，false不展示
                //.fromBottom(true)
                //设置自定义动画
                .setAnimation(R.style.dialog_from_bottom_anim)

                .show();

        alertDialog.setText(R.id.btn_share,"改分享");
        //获取ViewId为R.id.et_input 的view控件
        final EditText editText = alertDialog.getView(R.id.et_input);
        //为R.id.btn_confirm设置监听事件
        alertDialog.setOnClickListener(R.id.btn_confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(DialogSampleAct.this,editText.getText().toString().trim(),Toast.LENGTH_SHORT).show();
            }
        });



    }

    public static void actionStart(Context context){
        Intent intent = new Intent(context,DialogSampleAct.class);
        context.startActivity(intent);
    }
}
