package com.example.mytrip.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.example.mytrip.R;
import com.example.mytrip.base.Constant;
import com.example.mytrip.base.EnviromentConfig;
import com.example.mytrip.tools.LogUtil;
import com.example.mytrip.tools.NetCallBack;
import com.example.mytrip.tools.NetUtil;
import com.example.mytrip.ui.BaiduSearch;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by yu on 2017/6/2.
 */

public class SightListFragment extends Fragment{

    PoiSearch mPoiSearch;

    private ListView mSightList;
    private List<PoiInfo> mPoiInfoList;
    private SightAdapter mSightAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sight,null);
        mSightList = (ListView) v.findViewById(R.id.lv_sight);
        mSightAdapter = new SightAdapter(getActivity());
        mSightList.setAdapter(mSightAdapter);

        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(poiListener);

        mPoiSearch.searchInCity((new PoiCitySearchOption())
                .city("北京")
                .keyword("景点")
                .pageNum(1)
                .pageCapacity(10));

        return v;
    }

    OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener(){
        public void onGetPoiResult(PoiResult result){
            //获取POI检索结果
            PoiResult result1 = result;
            List<PoiInfo> poiInfos = result1.getAllPoi();
            mSightAdapter.setData(poiInfos);
            //uid是POI检索中获取的POI ID信息
//            String uid = poiInfos.get(0).uid;
//            PoiInfo poiInfo = poiInfos.get(0);
//            String address = poiInfo.address;
//            String city = poiInfo.city;
//            String name = poiInfo.name;
//            String phoneNum = poiInfo.phoneNum;
//            String url = Constant.URL_SIGHT_DETAIL + "id=" + uid
//                    + "&ak=" + EnviromentConfig.BAIDU_KEY
//                    + "&mcode=" + EnviromentConfig.BAIDU_MCODE
//                    + "&output=json";
//            NetUtil.doGet(BaiduSearch.this,url, new NetCallBack() {
//                @Override
//                public void onSuccess(String s, Call call, Response response) {
//                    LogUtil.d("BaiduSearch",s);
//
//                    //{"status":200,"message":"APP不存在，AK有误请检查再重试"}
//
////                    {
////                        "error": 0,
////                        "status": "Success",
////                        "date": "2017-06-02",
////                        "result": {
////                            "name": "什刹海",
////                            "location": {
////                                "lng": 116.38098669482,
////                                "lat": 39.942519453449
////                            },
////                            "telephone": "010-66127652",
////                            "star": "5",
////                            "url": "http://lvyou.baidu.com/shichahai",
////                            "abstract": "后海湖面辽阔，风光绮丽，而且湖水比较干净。适合散步。距离南锣鼓巷很近。",
////                            "description": "什刹海也写作“十刹海”，又名前海，四周原有十座佛寺，故有此称。元代名海子，为一宽而长的水面，明初缩小，后逐渐形成西海﹑后海﹑前海，三海水道相通。自清代起就成为游乐消夏之所，为燕京胜景之一，被誉为“北方的水乡”。著名的《帝京景物略》中则以“西湖春，秦淮夏，洞庭秋”来赞美什刹海的神韵。 景区东起地安门外大街，西到新街口北大街，北起北二环，南至平安大街，总面积146.7公顷，是京城内老北京风貌保存最完好的地方，有大量典型的胡同和四合院，这一带也是原老北京主要的商业活动区。 历史上本地区曾建有王府、寺观、庵庙等多达30余座，现仍尚存十几处。主要代表有恭王府及花园、宋庆龄故居及醇王府、 郭沫若纪念馆、钟鼓楼、德胜门箭楼、会贤堂等。 依托胡同和四合院，什刹海地区自古以来就有许多富有特色的民裕活动，如放荷灯、泛舟游湖、宴饮赏荷、冰床围酌、大阅冰鞋等。至今，一些有生命力的民俗活动仍然在什刹海地区大量存在。“胡同游”即活跃在这片得天独厚的自然人文环境中。而后海的酒吧一条街，更是为什刹海地区增添了现代的韵味。 古典与现代相容，传统与前卫契合，自然景观与人文胜迹辉映。闲暇之余，或品酒泛舟，览湖光粼粼，或徜徉两岸，听杨柳婆娑，或搜寻美食，尝御膳家宴，或投宿胡同人家品着原汁的京味儿。",
////                            "ticket_info": {
////                            "price": "免费",
////                            "open_time": "全天开放"
////                            }
////                        }
////                    }
//                }
//
//                @Override
//                public void onError(Call call, Response response, Exception e) {
//                    LogUtil.d("BaiduSearch",e.toString());
//                }
//            });

        }
        public void onGetPoiDetailResult(PoiDetailResult result){
            //获取Place详情页检索结果
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPoiSearch.destroy();
    }
}
