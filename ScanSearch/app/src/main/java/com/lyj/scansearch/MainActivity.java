package com.lyj.scansearch;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int GET_SCAN_RESULT = 0x01;

    private static final int REQUEST_CAMERA = 0x02;

    @BindView(R.id.btn_scan)
    Button btn_scan;
    @BindView(R.id.tv_ean)
    TextView tv_ean;
    @BindView(R.id.tv_guobie)
    TextView tv_guobie;
    @BindView(R.id.tv_place)
    TextView tv_place;
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.tv_supplier)
    TextView tv_supplier;
    @BindView(R.id.tv_sort_id)
    TextView tv_sort_id;
    @BindView(R.id.tv_faccode)
    TextView tv_faccode;
    @BindView(R.id.tv_fac_name)
    TextView tv_fac_name;
    @BindView(R.id.tv_fac_status)
    TextView tv_fac_status;
    @BindView(R.id.iv_title_src)
    ImageView iv_title_src;

    @BindView(R.id.et_price)
    EditText et_price;
    @BindView(R.id.btn_confirm)
    Button btn_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initAction();
    }


    private void initAction() {
        btn_scan.setOnClickListener(this);
        btn_confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_scan:

                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED)
                {

                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.CAMERA},REQUEST_CAMERA
                            );
                } else
                {
                    Intent intent = new Intent(this,ScanActivity.class);
                    startActivityForResult(intent,GET_SCAN_RESULT);
                }

                break;
            case R.id.btn_confirm:
                String price = et_price.getText().toString().trim();
                String code = tv_ean.getText().toString().trim();
                if(TextUtils.isEmpty(code)){
                    Toast.makeText(this,"请先扫一扫",Toast.LENGTH_LONG).show();
                    return;
                }
                if(!TextUtils.isEmpty(price)){
                    CodePrice codePrice = new CodePrice();
                    codePrice.setCode(code);
                    codePrice.setPrice(price);
                    if(codePrice.save()){
                        Toast.makeText(this,"保存成功",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this,"保存失败",Toast.LENGTH_SHORT).show();
                    }
                    codePrice.updateAll("code = ?",code);

                }else {
                    Toast.makeText(this,"请输入金额",Toast.LENGTH_LONG).show();
                }
                break;

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {

        if (requestCode == REQUEST_CAMERA)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Intent intent = new Intent(this,ScanActivity.class);
                startActivityForResult(intent,GET_SCAN_RESULT);
            } else
            {
                // Permission Denied
                Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case GET_SCAN_RESULT:
                if (data == null || TextUtils.isEmpty(data.getStringExtra("result"))) {
                    Toast.makeText(this,"扫码失败",Toast.LENGTH_LONG).show();
                    return;
                }
                String result = data.getStringExtra("result");
                tv_ean.setText(result);
                String url = "http://www.liantu.com/tiaoma/query.php?ean="+ result;
                List<CodePrice> codePrices = DataSupport.where("code = ?",result).find(CodePrice.class);
                if(codePrices!=null && codePrices.size()>0){
                    et_price.setText(codePrices.get(0).getPrice());
                }
                getAsynHttp(url);
                break;
            default:
                break;
        }
    }

    private void getAsynHttp(String url) {
        OkHttpClient okHttpClient=new OkHttpClient();
        Request.Builder requestBuilder = new Request.Builder().url(url);
        //可以省略，默认是GET请求
        requestBuilder.method("GET",null);
        Request request = requestBuilder.build();
        Call mcall= okHttpClient.newCall(request);
        mcall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (null != response.cacheResponse()) {
                    String str = response.cacheResponse().toString();
                } else {
                    final String result = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                //JSONObject jsonObject = new JSONObject(result);
                                    GoodsBean goodsBean = (GoodsBean) JsonUtil.json2Obj(result,GoodsBean.class);
                                    if(null != goodsBean){
                                        tv_guobie.setText(goodsBean.getGuobie());
                                        tv_place.setText(goodsBean.getPlace());
                                        tv_price.setText(goodsBean.getPrice() + "");
                                        tv_supplier.setText(goodsBean.getSupplier());
                                        tv_sort_id.setText(goodsBean.getSort_id()+"");
                                        tv_faccode.setText(goodsBean.getFaccode());
                                        tv_fac_name.setText(goodsBean.getFac_name());
                                        tv_fac_status.setText(goodsBean.getFac_status());
                                        ImageLoaderUtil.displayImage(goodsBean.getTitleSrc(),iv_title_src);
                                    }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
//                    result = response.body().string();
//                    String str = response.networkResponse().toString();
                }

            }
        });
    }
}
