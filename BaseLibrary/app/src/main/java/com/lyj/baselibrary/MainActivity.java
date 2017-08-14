package com.lyj.baselibrary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.lyj.baselib.CrashUtil.CrashHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CrashHandler.getInstance().init(this.getApplicationContext());

        //printLastException();
        intiView();
        initAction();




    }

    private void intiView() {
        btn_next = (Button) findViewById(R.id.btn_next);
    }

    private void initAction() {
        btn_next.setOnClickListener(this);
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
        }
    }
}
