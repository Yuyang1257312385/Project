package com.lyj.baselib.dialog;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.lyj.baselib.R;

import javax.xml.parsers.SAXParser;

/**
 * Created by yu on 2017/8/14.
 *
 * 在具体的项目中可以在根据需求封装一层，方便调用
 *
 * 若需自定义效果
 */

public class SampleAct extends Activity {
    private void showDialog(){
        View.OnClickListener onShareClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SampleAct.this,"share",Toast.LENGTH_LONG).show();
            }
        };

        final BaseDialog alertDialog = new BaseDialog.Builder(this)
                //设置dialog的布局，必须有，可以是id或view
                .setContentView(R.layout.dialog_layout)
                //宽度设置全屏
                .setFullWidth()
                //设置自定义的宽高
                .setWidthAndHeight(100,100)

                //设置在屏幕中的位置
                .setGravity(Gravity.BOTTOM)

                //为R.id.btn_confirm 设置文字
                .setText(R.id.btn_confirm,"确认")

                //为viewid为R.id.btn_share 设置点击监听,或者可以直接对alderDialog设置监听
                //.setOnClickListener(R.id.btn_share,onShareClickListener)
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
//        alertDialog.setOnClickListener(R.id.btn_confirm, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(SampleAct.this,editText.getText().toString(),Toast.LENGTH_LONG).show();
//                alertDialog.dismiss();
//            }
//        });



    }
}
