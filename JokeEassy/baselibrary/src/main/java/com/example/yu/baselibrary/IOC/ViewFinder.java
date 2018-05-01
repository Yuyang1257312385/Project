package com.example.yu.baselibrary.IOC;

import android.app.Activity;
import android.view.View;

/**
 * Created by Administrator on 2018/4/30.
 */

public class ViewFinder {

    private Activity mActivity;
    private View mView;

    public ViewFinder(Activity activity) {
        mActivity = activity;
    }

    public ViewFinder(View view) {
        mView = view;
    }

    public View findViewById(int viewId){
        return mActivity != null ? mActivity.findViewById(viewId) : mView.findViewById(viewId);
    }
}
