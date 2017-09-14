package com.lyj.todolist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    private EditText mImportantUrgentEt,mImportantEt,mUrgentEt,mNormalEt;
    private ImageButton mSaveBtn;

    private final String IMPORTANT_URGENT = "important_urgent";
    private final String IMPORTANT = "important";
    private final String URGENT = "urgent";
    private final String NORMAL = "normal";
    private SharedPreferences mSp;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initAction();
    }

    private void initAction() {
        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(saveRecord()){
                    Toast.makeText(MainActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean saveRecord() {
        String importantUrgent = mImportantUrgentEt.getText().toString();
        String important = mImportantEt.getText().toString();
        String urgent = mUrgentEt.getText().toString();
        String normal = mNormalEt.getText().toString();
        mEditor.putString(IMPORTANT_URGENT,importantUrgent);
        mEditor.putString(IMPORTANT,important);
        mEditor.putString(URGENT,urgent);
        mEditor.putString(NORMAL,normal);
        mEditor.commit();
        return true;
    }

    private void initData() {
        mSp = getSharedPreferences("TODOLIST",MODE_PRIVATE);
        mEditor = mSp.edit();
        String importantUrgent = mSp.getString(IMPORTANT_URGENT,"");
        String important = mSp.getString(IMPORTANT,"");
        String urgent = mSp.getString(URGENT,"");
        String normal = mSp.getString(NORMAL,"");
        mImportantUrgentEt.setText(importantUrgent);
        if(!TextUtils.isEmpty(importantUrgent)){
            mImportantUrgentEt.setSelection(importantUrgent.length());
        }
        mImportantEt.setText(important);
        mUrgentEt.setText(urgent);
        mNormalEt.setText(normal);

    }

    private void initView() {
        mImportantUrgentEt = (EditText) findViewById(R.id.et_important_urgent);
        mUrgentEt = (EditText) findViewById(R.id.et_urgent);
        mImportantEt = (EditText) findViewById(R.id.et_important);
        mNormalEt = (EditText) findViewById(R.id.et_normal);
        mSaveBtn = (ImageButton) findViewById(R.id.btn_save);
    }

    @Override
    public void onBackPressed() {
        if(saveRecord()){
            finish();
        }
//        AlertDialog dialog = new AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
//                .setTitle("确认是否保存")
//                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
//                        dialog.dismiss();
//                    }
//                })
//                .setNegativeButton("退出", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
//                        dialog.dismiss();
//                        finish();
//                    }
//                })
//                .create();
//        dialog.show();
    }
}
