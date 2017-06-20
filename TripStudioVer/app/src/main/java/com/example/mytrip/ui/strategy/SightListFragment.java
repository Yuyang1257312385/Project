package com.example.mytrip.ui.strategy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.example.mytrip.R;

import com.example.mytrip.tools.MapUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by yu on 2017/6/2.
 */

public class SightListFragment extends Fragment implements OnRefreshListener, OnLoadMoreListener {
    private static final int SEARCH_RADIUS = 500000;
    private static final String KEY_WORD = "景点";


    SwipeToLoadLayout swipeToLoadLayout;

    private RecyclerView mSightList;
    private List<PoiInfo> mPoiInfoList = new ArrayList<>();
    private SightAdapter mSightAdapter;

    private int mPageNum = 1;
    private int mPageSize = 10;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sight,container,false);
        initView(v);
        searchNearBy();
        return v;
    }

    private void initView(View v) {
        swipeToLoadLayout = (SwipeToLoadLayout) v.findViewById(R.id.swipeToLoadLayout);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        mSightList = (RecyclerView) v.findViewById(R.id.swipe_target);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mSightList.setLayoutManager(linearLayoutManager);
        mSightAdapter = new SightAdapter(getActivity());
        mSightList.setAdapter(mSightAdapter);

    }


    private void searchNearBy() {
        MapUtil.poiSearchNearby(KEY_WORD, SEARCH_RADIUS, mPageSize, mPageNum, new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                //获取POI检索结果
                PoiResult result1 = poiResult;
                List<PoiInfo> poiInfos = result1.getAllPoi();
                if(poiInfos !=null && poiInfos.size()>0){
                    mPageNum++;
                    mPoiInfoList.addAll(poiInfos);
                    mSightAdapter.setData(mPoiInfoList);
                }
                swipeToLoadLayout.setLoadingMore(false);
                swipeToLoadLayout.setRefreshing(false);
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

            }
        });
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLoadMore() {
        searchNearBy();
    }

    @Override
    public void onRefresh() {
        mPageNum = 1;
        mPoiInfoList.clear();
        searchNearBy();
    }
}
