package com.example.mytrip.ui;

import android.os.Bundle;
import android.util.Log;

import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.example.mytrip.R;
import com.example.mytrip.base.ActBase;
import com.example.mytrip.tools.LogUtil;

import java.util.List;

/**
 * Created by yu on 2017/5/31.
 */

public class TestSearchAct extends ActBase implements PoiSearch.OnPoiSearchListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_search);

        PoiSearch.Query query = new PoiSearch.Query("","110000","310000");
        //keyWord表示搜索字符串，
//第二个参数表示POI搜索类型，二者选填其一，选用POI搜索类型时建议填写类型代码，码表可以参考下方（而非文字）
//cityCode表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(1);//设置查询页码

        //构造 PoiSearch 对象，并设置监听。
        PoiSearch poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);
        //调用 PoiSearch 的 searchPOIAsyn() 方法发送请求。
        poiSearch.searchPOIAsyn();
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        List<PoiItem> poiItems =  poiResult.getPois();
        if(null != poiItems && poiItems.size()>0){
            for(PoiItem poiItem : poiItems){
                LogUtil.d("TestSearch",poiItem.toString());
            }
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }
}
