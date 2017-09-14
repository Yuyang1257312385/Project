package com.lyj.baselib.dialog;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2017/8/13.
 *
 * dialogview的辅助处理类
 */

class DialogViewHelper {

    private View mContentView;
    //view缓存  弱引用防止内存泄漏
    private SparseArray<WeakReference<View>>  mCacheView;

    public DialogViewHelper() {
        mCacheView = new SparseArray<>();
    }

    public DialogViewHelper(Context context, int viewLayoutResId) {
        this();
        mContentView = LayoutInflater.from(context).inflate(viewLayoutResId,null);
    }


    public void setContentView(View view) {
        mContentView = view;
    }

    public void setText(int viewId, CharSequence text) {

        TextView tv = getView(viewId);
        if(tv != null){
            tv.setText(text);
        }
    }


    public void setOnClickListener(int viewId, View.OnClickListener onClickListener) {
        //每次findViewById比较麻烦，减少findViewById的次数
        //View view = mContentView.findViewById(viewId);
        View view = getView(viewId);
        if(view != null){
            view.setOnClickListener(onClickListener);
        }
    }

    public  <T extends View> T getView(int viewId) {
        WeakReference<View> viewWeakReference = mCacheView.get(viewId);
        View view = null;
        if(viewWeakReference != null){
            view = viewWeakReference.get();
        }
        if(view == null){
            view = mContentView.findViewById(viewId);
            if(view != null){
                mCacheView.put(viewId,new WeakReference<View>(view));
            }
        }
        return (T)view;
    }

    public View getContentView() {
        return mContentView;
    }
}
