package com.example.yu.baselibrary.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.example.yu.baselibrary.R;

/**
 * Created by Administrator on 2017/8/13.
 *
 * 自定义的万能dialog
 *
 * 使用：见SampleAct
 * 若需要扩展，
 * 1.先在本类中的build中添加set方法，
 * 2.然后到AlertController中添加P的字段，并在apply（）中实现
 *
 */




public class BaseDialog extends Dialog {


    private AlertController mAlertController;

    public BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
        mAlertController = new AlertController(this,getWindow());
    }

    /**
     * 为viewId设置文本
     * @param viewId
     * @param text
     */
    public void setText(int viewId, CharSequence text) {
        mAlertController.setText(viewId,text);
    }


    /**
     * 为ViewId设置监听器
     *
     * @param viewId
     * @param onClickListener
     */
    public void setOnClickListener(int viewId, OnClickListener onClickListener) {
        mAlertController.setOnClickListener(viewId,onClickListener,this);
    }

    /**
     * 获取到ViewId的控件
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public  <T extends View> T getView(int viewId) {
        return (T)mAlertController.getView(viewId);
    }

    public static class Builder{

        private AlertController.AlertParams P;

        public Builder(Context context) {
            this(context, R.style.dialog);
        }

        public Builder(Context context,int themeResId){
            P = new AlertController.AlertParams(context,themeResId);
        }


        public BaseDialog create() {
            // Context has already been wrapped with the appropriate theme.
            final BaseDialog dialog = new BaseDialog(P.mContext,P.mThemeResId);
            P.apply(dialog.mAlertController);
            dialog.setCancelable(P.mCancelable);
            if (P.mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }
            dialog.setOnCancelListener(P.mOnCancelListener);
            dialog.setOnDismissListener(P.mOnDismissListener);
            if (P.mOnKeyListener != null) {
                dialog.setOnKeyListener(P.mOnKeyListener);
            }
            return dialog;
        }

        public BaseDialog show() {
            final BaseDialog dialog = create();
            dialog.show();
            return dialog;
        }

        /**
         * 为dialog设置布局,若需要圆角背景，只要布局的背景设置成圆角即可
         * @param layoutResId
         * @return
         */
        public Builder setContentView(int layoutResId) {
            P.mView = null;
            P.mViewLayoutResId = layoutResId;
            return this;
        }

        /**
         * 为dialog设置布局
         * @param view
         * @return
         */
        public Builder setContentView(View view){
            P.mView = null;
            P.mViewLayoutResId = 0;
            return this;
        }

        /**
         * 点击空白处是否可以取消
         * @param cancelable true 可以取消  false 不可以取消
         * @return
         */
        public Builder setCancelable(boolean cancelable) {
            P.mCancelable = cancelable;
            return this;
        }


        /**
         * 为viewId对应的view 设置文本内容
         * @param viewId
         * @param text
         * @return
         */
        public Builder setText(int viewId,CharSequence text){
            P.mTextArray.put(viewId,text);
            return this;
        }

        /**
         * 为viewId设置点击监听
         *
         * 注意内存泄漏 不要设置成Activity
         *
         * @param viewId
         * @param listener
         * @return
         */
        public Builder setOnClickListener(int viewId, OnClickListener listener){
            P.mClickArray.put(viewId,listener);
            return this;
        }

        /**
         * 宽度全屏
         * @return
         */
        public Builder setFullWidth(){
            P.mWidth = ViewGroup.LayoutParams.MATCH_PARENT;
            return this;
        }

        /**
         * 设置dialog宽高
         * @param width
         * @param heigth
         * @return
         */
        public Builder setWidthAndHeight(int width,int heigth){
            P.mWidth = width;
            P.mHeight = heigth;
            return this;
        }

        /**
         *
         * @param
         * @return
         */
//        public Builder fromBottom(boolean isAnimation){
//            if(isAnimation){
//                P.mAnimation = R.style.dialog_from_bottom_anim;
//            }
//            P.mGravity = Gravity.BOTTOM;
//            return this;
//        }

        /**
         * 在屏幕中的位置
         * @param gravity
         * @return
         */
        public Builder setGravity(int gravity){
            P.mGravity = gravity;
            return this;
        }



        /**
         * 设置默认动画 缩放动画
         * @return
         */
        public Builder setDefaultAnimation(){
            P.mAnimation = R.style.dialog_scale_anim;
            //P.mGravity = Gravity.CENTER;
            return this;
        }

        /**
         * 设置动画
         * @param styleAnimation
         * @return
         */
        public Builder setAnimation(int styleAnimation){
            if(styleAnimation != 0){
                P.mAnimation = styleAnimation;
            }
            return this;
        }

        public Builder setOnCancelListener(OnCancelListener onCancelListener) {
            P.mOnCancelListener = onCancelListener;
            return this;
        }


        public Builder setOnDismissListener(OnDismissListener onDismissListener) {
            P.mOnDismissListener = onDismissListener;
            return this;
        }


        public Builder setOnKeyListener(OnKeyListener onKeyListener) {
            P.mOnKeyListener = onKeyListener;
            return this;
        }

        //// TODO: 1 扩展功能在这里写set方法，然后到AlertController的apply（）中实现


    }


}
