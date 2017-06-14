package com.example.mytrip.tools;


import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiSearch;

import static com.example.mytrip.base.MyApp.myApp;

/**
 * Created by yu on 2017/6/6.
 */

public class MapUtil {



    public static void poiSearchNearby(final String keyword, final int radius, final int pageSize, final int pageNum, OnGetPoiSearchResultListener onGetPoiSearchResultListener){
        final PoiSearch poiSearch = PoiSearch.newInstance();
        poiSearch.setOnGetPoiSearchResultListener(onGetPoiSearchResultListener);

        final LocationClient locationClient = new LocationClient(myApp);
        locationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                //定位失败会默认返回 4.9E-324，所以只有在成功的时候才停止定位
                if (!((4.9E-324) == (bdLocation.getLatitude()))) {
                    locationClient.stop();
                    poiSearch.searchNearby(new PoiNearbySearchOption()
                            .location(new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude()))
                            .keyword(keyword)
                            .radius(radius)
                            .pageNum(pageNum)
                            .pageCapacity(pageSize));
                }
            }
        });
        locationClient.start();

    }
}
