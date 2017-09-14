package com.lyj.baselibrary.wangluo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyj.baselib.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by yu on 2017/8/17.
 */

public class NetActOne extends Activity implements View.OnClickListener{

    private Button mClickBtn;
    private TextView mResultTv;
    private ImageView mShowIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_net);

        mClickBtn = (Button) findViewById(R.id.btn_down);
        mResultTv = (TextView) findViewById(R.id.tv_result);
        mShowIv = (ImageView) findViewById(R.id.iv_show);

        mClickBtn.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_down:
                loadData();
                break;
        }
    }

    private void loadData() {
        if(isNetConnect()){
            new LoadData().execute("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png");
        }else {
            mResultTv.setText("网络异常");
        }
    }

    public boolean isNetConnect(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isAvailable()){
            return true;
        }else {
            return false;
        }
    }

     class LoadData extends AsyncTask<String,Void,Bitmap>{

         @Override
         protected Bitmap doInBackground(String... urls) {

             Bitmap bitmap = loadUrl(urls[0]);
             return bitmap;
         }

         @Override
         protected void onPostExecute(Bitmap bitmap) {
            mShowIv.setImageBitmap(bitmap);
//             if(bitmap != null){
//                 bitmap.recycle();
//             }
         }
     }

    private Bitmap loadUrl(String url) {
        InputStream inputStream = null;
        Bitmap bitmap = null;
        try {
            URL url1 = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
            connection.setReadTimeout(50000);
            connection.setConnectTimeout(50000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            // Starts the query
            connection.connect();

            int response = connection.getResponseCode();
            if(response == 200){
                inputStream = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }


}
