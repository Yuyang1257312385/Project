package com.example.mytrip.ui.strategy;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.example.mytrip.R;

import java.util.List;

/**
 * Created by yu on 2017/6/2.
 */

public class SightAdapter extends RecyclerView.Adapter<SightAdapter.ViewHolder> {

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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sight,null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final PoiInfo poiInfo = mData.get(position);
        holder.mCityTv.setText(poiInfo.city);
        holder.mNameTv.setText(poiInfo.name);
        holder.mAddressTv.setText(poiInfo.address);
        holder.mPhoneNumTv.setText(poiInfo.phoneNum);
        holder.mPhoneNumTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = poiInfo.phoneNum.trim();
                if (!TextUtils.isEmpty(number)) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (context.checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                            return;
                        }
                    }
                    context.startActivity(intent);
                }
            }
        });

        holder.mDetailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SightDetailActivity.actionStart(context,poiInfo.uid);
            }
        });
    }


    @Override
    public int getItemCount() {
       return mData != null ? mData.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView mCityTv,mNameTv,mAddressTv,mPhoneNumTv;
        Button mDetailBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            mCityTv = (TextView) itemView.findViewById(R.id.tv_city);
            mNameTv = (TextView) itemView.findViewById(R.id.tv_name);
            mAddressTv = (TextView) itemView.findViewById(R.id.tv_address);
            mPhoneNumTv = (TextView) itemView.findViewById(R.id.tv_phone_num);
            mDetailBtn = (Button) itemView.findViewById(R.id.btn_detail);
        }
    }


}
