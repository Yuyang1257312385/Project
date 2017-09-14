package com.lyj.baselib.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Administrator on 2017/8/13.
 */

class AlertController {

    private BaseDialog mDialog;
    private Window mWindow;
    private DialogViewHelper mDialogViewHelper;

    public AlertController(BaseDialog alertDialog, Window window) {
        this.mDialog = alertDialog;
        this.mWindow = window;
    }

    /**
     * 获取dialog
     * @return
     */
    public BaseDialog getDialog() {
        return mDialog;
    }

    /**
     * 获取dialog的window
     * @return
     */
    public Window getWindow() {
        return mWindow;
    }

    public void setText(int viewId, CharSequence text) {
        mDialogViewHelper.setText(viewId,text);
    }


    public void setOnClickListener(int viewId, View.OnClickListener onClickListener) {
        mDialogViewHelper.setOnClickListener(viewId,onClickListener);
    }

    public  <T extends View> T getView(int viewId) {

        return (T)mDialogViewHelper.getView(viewId);
    }

    public void setDialogViewHelper(DialogViewHelper dialogViewHelper){
        this.mDialogViewHelper  = dialogViewHelper;

    }

    public static class AlertParams{

        public Context mContext;
        //主题
        public int mThemeResId;
        //点击空白是否能取消
        public boolean mCancelable =true;
        //dialog cancle监听
        public DialogInterface.OnCancelListener mOnCancelListener;
        //消失监听
        public DialogInterface.OnDismissListener mOnDismissListener;
        //dialog key监听
        public DialogInterface.OnKeyListener mOnKeyListener;
        //布局的VIew
        public View mView;
        //布局的layoutId
        public int mViewLayoutResId;
        // 存放文字   比hashmap高效，但key必须是int
        public SparseArray<CharSequence>  mTextArray = new SparseArray<>();
        //存放点击事件
        public SparseArray<View.OnClickListener> mClickArray = new SparseArray<>();
        public int mWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
        //动画
        public int mAnimation ;
        //位置
        public int mGravity = Gravity.CENTER;
        public int mHeight= ViewGroup.LayoutParams.WRAP_CONTENT;


        public AlertParams(Context context, int themeResId) {
            this.mContext = context;
            this.mThemeResId = themeResId;
        }

        /**
         * 绑定和设置参数
         * @param mAlert
         */
        public void apply(AlertController mAlert) {
            DialogViewHelper viewHelper = null;
            //1.设置布局 DialogViewHelper
            if(mViewLayoutResId != 0){
                viewHelper = new DialogViewHelper(mContext,mViewLayoutResId);
            }

            if(mView != null){
                viewHelper = new DialogViewHelper();
                viewHelper.setContentView(mView);
            }

            if(viewHelper == null){
                throw new IllegalArgumentException("未调用setContentView()");
            }

            //设置Controller的辅助类DialogViewHelper
            mAlert.setDialogViewHelper(viewHelper);

            //给dialog设置布局
            mAlert.getDialog().setContentView(viewHelper.getContentView());
            //2.设置文本
            int testArraySize = mTextArray.size();
            for(int i=0;i<testArraySize;i++){
                viewHelper.setText(mTextArray.keyAt(i),mTextArray.valueAt(i));
            }
            //3.设置点击
            int clickArraySize = mClickArray.size();
            for(int i=0;i<clickArraySize;i++){
                viewHelper.setOnClickListener(mClickArray.keyAt(i),mClickArray.valueAt(i));
            }
            //4.配置自定义效果（全屏，从底部弹出，默认动画，）
            Window window  = mAlert.getWindow();
            window.setGravity(mGravity);
            if(mAnimation != 0){
                window.setWindowAnimations(mAnimation);
            }
            //设置宽高
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = mWidth;
            params.height = mHeight;
            window.setAttributes(params);
        }
    }

}
