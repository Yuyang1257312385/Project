package com.lyj.baselibrary;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lyj.baselib.CrashUtil.CrashHandler;
import com.lyj.baselib.dialog.BaseDialog;
import com.lyj.baselib.dialog.SampleAct;
import com.lyj.baselibrary.wangluo.NetActOne;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CrashHandler.getInstance().init(this.getApplicationContext());

        initAction();
    }


    private void initAction() {
        findViewById(R.id.btn_next).setOnClickListener(this);
        findViewById(R.id.btn_dialog).setOnClickListener(this);
        findViewById(R.id.btn_net).setOnClickListener(this);
    }

    private void printLastException() {
        File[] files = CrashHandler.getInstance().getCrashFiles();
        if(files != null && files.length>0){
            for (File file:files){
                FileReader fileReader = null;
                try {
                    fileReader = new FileReader(file);
                    //2.建立缓冲区
                    char[] buf = new char[1024];
                    int len = 0;
                    //3.循环读写
                    while ((len = fileReader.read(buf)) != -1){
                        Log.d("TAG",new String(buf,0,len));
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(fileReader != null){
                        try {
                            fileReader.close();
                            //file.delete();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_next:
                Intent intent = new Intent(this,NextActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_dialog:
                showCustomDialog();
                break;
//            case R.id.btn_net:
//                Intent intent1 = new Intent(this, NetActOne.class);
//                startActivity(intent1);
//                NetTestActivity.actionStart(this);
//                break;
        }
    }

    private void showCustomDialog() {
        View.OnClickListener onShareClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"share",Toast.LENGTH_LONG).show();
            }
        };

        final BaseDialog alertDialog = new BaseDialog.Builder(this)
                //设置dialog的布局，必须有，可以是id或view
                .setContentView(com.lyj.baselib.R.layout.dialog_layout)
                //宽度设置全屏
                //.setFullWidth()
                //设置自定义的宽高
                //.setWidthAndHeight(100,100)
                //设置在屏幕中的位置
                //.setGravity(Gravity.BOTTOM)

                //为R.id.btn_confirm 设置文字
                .setText(com.lyj.baselib.R.id.btn_confirm,"确认")

                //为viewid为R.id.btn_share 设置点击监听,或者可以直接对alderDialog设置监听
                .setOnClickListener(com.lyj.baselib.R.id.btn_share, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this,com.lyj.baselib.R.id.btn_share+"--"+which,Toast.LENGTH_SHORT).show();
                    }
                })
                //点击空白出是否可以取消，true 可以取消  false 不可取消
                .setCancelable(true)

                //设置默认的展示动画  从中心展开缩放动画
                //.setDefaultAnimation()
                //从底部进入，true展示动画，false不展示
                //.fromBottom(true)
                //设置自定义动画
                .setAnimation(R.style.dialog_from_bottom_anim)

                .show();

        alertDialog.setText(com.lyj.baselib.R.id.btn_share,"分享");
        //获取ViewId为R.id.et_input 的view控件
        final EditText editText = alertDialog.getView(com.lyj.baselib.R.id.et_input);
        //为R.id.btn_confirm设置监听事件
        alertDialog.setOnClickListener(com.lyj.baselib.R.id.btn_confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this,editText.getText().toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
