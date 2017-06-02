package com.example.mytrip.ui.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.example.mytrip.R;
import com.example.mytrip.ui.SightDetailAct;

import java.util.List;

/**
 * Created by yu on 2017/6/2.
 */

public class SightAdapter extends BaseAdapter {

    private Context context;
    private List<PoiInfo> mData;

    public SightAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<PoiInfo> poiInfos) {
        this.mData = poiInfos;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData != null ? mData.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder hoder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_sight, null);
            hoder = new ViewHolder();
            hoder.mCityTv = (TextView) convertView.findViewById(R.id.tv_city);
            hoder.mNameTv = (TextView) convertView.findViewById(R.id.tv_name);
            hoder.mAddressTv = (TextView) convertView.findViewById(R.id.tv_address);
            hoder.mPhoneNumTv = (TextView) convertView.findViewById(R.id.tv_phone_num);
            hoder.mDetailBtn = (Button) convertView.findViewById(R.id.btn_detail);
            convertView.setTag(hoder);
        } else {
            hoder = (ViewHolder) convertView.getTag();
        }
        final PoiInfo poiInfo = mData.get(position);
        hoder.mCityTv.setText(poiInfo.city);
        hoder.mNameTv.setText(poiInfo.name);
        hoder.mAddressTv.setText(poiInfo.address);
        hoder.mPhoneNumTv.setText(poiInfo.phoneNum);
        hoder.mPhoneNumTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = hoder.mPhoneNumTv.getText().toString().trim();
                if (!TextUtils.isEmpty(number)) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (context.checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    Activity#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for Activity#requestPermissions for more details.
                            return;
                        }
                    }
                    context.startActivity(intent);
                }
            }
        });

        hoder.mDetailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SightDetailAct.actionStart(context,poiInfo.uid);
            }
        });
        return convertView;
    }

    static class ViewHolder{
        TextView mCityTv,mNameTv,mAddressTv,mPhoneNumTv;
        Button mDetailBtn;
    }


}
