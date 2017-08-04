package com.lyj.mybrowner;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.http.SslError;
import android.service.wallpaper.WallpaperService;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.util.Linkify;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.ClientCertRequest;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity{

    private EditText mInputEt;
    private ImageButton mSearchBtn;
    private ImageView mSetIv;

    private SharedPreferences mSp;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initAction();
        mSp = getSharedPreferences(Constant.ENGINE,MODE_PRIVATE);
        mEditor = mSp.edit();
    }

    private void initView() {
        mInputEt = (EditText) findViewById(R.id.et_input);
        mSearchBtn = (ImageButton) findViewById(R.id.ib_next);
        mSetIv = (ImageView) findViewById(R.id.iv_set);

    }

    private void initAction() {
        mSetIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("搜索引擎")
                        .setItems(R.array.select_search_engine, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case 0:
                                        mEditor.putString(Constant.SP_ENGINE,Constant.BAIDU_ENGINE);
                                        mEditor.commit();
                                        break;
                                    case 1:
                                        mEditor.putString(Constant.SP_ENGINE,Constant.BING_ENGINE);
                                        mEditor.commit();
                                        break;
                                    case 2:
                                        LayoutInflater factory = LayoutInflater.from(MainActivity.this);
                                        final View textEntryView = factory.inflate(R.layout.layout_entry, null);
                                        final EditText editText = (EditText) textEntryView.findViewById(R.id.et_input);
                                        AlertDialog inputDialog = new AlertDialog.Builder(MainActivity.this)
                                                .setTitle("自定义")
                                                .setView(textEntryView)
                                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int whichButton) {
                                                        String engine = editText.getText().toString().trim();
                                                        if(!TextUtils.isEmpty(engine)){
                                                            mEditor.putString(Constant.SP_ENGINE,engine);
                                                            mEditor.commit();
                                                        }
                                                        dialog.dismiss();
                                                    }
                                                })
                                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int whichButton) {
                                                        dialog.dismiss();
                                                    }
                                                })
                                                .create();
                                        inputDialog.show();
                                        break;
                                }
                                dialog.dismiss();
                            }
                        })
                        .create();
                alertDialog.show();
            }
        });

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //隐藏软键盘
                hideKeyBoard();

                String content = mInputEt.getText().toString().trim();
                mInputEt.setText("");
                if(TextUtils.isEmpty(content)){
                    Toast.makeText(MainActivity.this,"请输入搜索内容",Toast.LENGTH_LONG).show();
                    return;
                }
                if(ADFilterTool.hasPorn(MainActivity.this,content)){
                    Toast.makeText(MainActivity.this,"含有敏感信息",Toast.LENGTH_LONG).show();
                    return;
                }
                ActBrowser.actionStart(MainActivity.this,content);
            }
        });
    }

    private void hideKeyBoard() {
        ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(MainActivity.this.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
    }


}
