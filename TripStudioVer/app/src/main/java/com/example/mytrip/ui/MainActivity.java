package com.example.mytrip.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;


import com.example.mytrip.R;
import com.example.mytrip.tools.ImageLoaderUtil;
import com.example.mytrip.tools.ToastUtils;
import com.example.mytrip.ui.bmobdb.MyUser;
import com.example.mytrip.ui.footprint.FootPrintActivity;
import com.example.mytrip.ui.fragment.NearByFragment;
import com.example.mytrip.ui.fragment.NewsFragment;
import com.example.mytrip.ui.fragment.PostFragment;
import com.example.mytrip.ui.fragment.SightListFragment;
import com.example.mytrip.ui.login.LoginActivity;
import com.example.mytrip.ui.slideactivity.ExerciseActivity;
import com.example.mytrip.view.XCRoundImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.slidingmenu.lib.SlidingMenu;

import android.os.Bundle;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
//import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends FragmentActivity implements OnClickListener {

    private TextView mTitleTv;
    private ImageView mUserHeadIv;
    private Button mPostBtn;//发表说说
    private List<Fragment> fragments;
    private SightListFragment mMainFragment;//攻略
    private NewsFragment mNewsFragment;
    private PostFragment mPostFragment;//动态
    private NearByFragment mNearByFragment;//附近
    private RadioGroup mRG;


    private SlidingMenu mLeftMenu;
    private View mUserHeadRl, mFootPrintRl, mCollectRl, mDownloadMap,
            mExerciseRl, mSetRl, mFeedBackRl;
    private XCRoundImageView mLeftHeadIv;
    private Button mLoginBtn;
    private TextView mUserNameTv;

    private boolean mIsLogin = false;//是否登录

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();
        initAction();
        getUserInfo();
    }


    private void initView() {
        setSlideMenu();

        fragments = new ArrayList<Fragment>();
        mMainFragment = new SightListFragment();
        mNewsFragment = new NewsFragment();
        mPostFragment = new PostFragment();
        mNearByFragment = new NearByFragment();
        fragments.add(mMainFragment);
        fragments.add(mNewsFragment);
        fragments.add(mPostFragment);
        fragments.add(mNearByFragment);
        showFragment(R.id.frameLayout1, fragments.get(0));
        mRG = (RadioGroup) findViewById(R.id.rg_select);
        mTitleTv = (TextView) findViewById(R.id.tv_title);
        mTitleTv.setText("");
        mPostBtn = (Button) findViewById(R.id.btn_post);
        mUserHeadRl = findViewById(R.id.rl_user_head);
        mFootPrintRl = findViewById(R.id.rl_foot_print);
        mCollectRl = findViewById(R.id.rl_collect);
        mDownloadMap = findViewById(R.id.rl_download_map);
        mExerciseRl = findViewById(R.id.rl_exercise);
        mSetRl = findViewById(R.id.rl_set);
        mFeedBackRl = findViewById(R.id.rl_feed_back);

        mLeftHeadIv = (XCRoundImageView) findViewById(R.id.iv_head_img);
        mUserHeadIv = (ImageView) findViewById(R.id.iv_user_head);
        mLoginBtn = (Button) findViewById(R.id.btn_login_rightnow);
        mUserNameTv = (TextView) findViewById(R.id.tv_username);

    }

    /**
     * 设置侧滑
     */
    private void setSlideMenu() {
        mLeftMenu = new SlidingMenu(this);
        mLeftMenu.setMode(SlidingMenu.LEFT);
        mLeftMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        // menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        mLeftMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        mLeftMenu.setFadeDegree(1.0f);
        mLeftMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        View view = LayoutInflater.from(this).inflate(R.layout.left_menu, null);
        mLeftMenu.setMenu(view);
    }

    private void initAction() {
        setChechChangedListener();
        mUserHeadIv.setOnClickListener(this);
        mPostBtn.setOnClickListener(this);

        mLeftHeadIv.setOnClickListener(this);
        mLoginBtn.setOnClickListener(this);
        mUserHeadRl.setOnClickListener(this);
        mFootPrintRl.setOnClickListener(this);
        mCollectRl.setOnClickListener(this);
        mDownloadMap.setOnClickListener(this);
        mExerciseRl.setOnClickListener(this);
        mSetRl.setOnClickListener(this);
        mFeedBackRl.setOnClickListener(this);
    }

    private void setChechChangedListener() {
        mRG.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_main:
                        showFragment(R.id.frameLayout1, fragments.get(0));
                        mUserHeadIv.setVisibility(View.VISIBLE);
                        mTitleTv.setVisibility(View.VISIBLE);
                        mTitleTv.setText("");
                        mPostBtn.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.rb_news:
                        showFragment(R.id.frameLayout1, fragments.get(1));
                        mUserHeadIv.setVisibility(View.GONE);
                        mTitleTv.setVisibility(View.GONE);
                        mPostBtn.setVisibility(View.GONE);
                        break;
                    case R.id.rb_dynamic:
                        BmobUser user = BmobUser.getCurrentUser();
                        if (user != null) {
                            showFragment(R.id.frameLayout1, fragments.get(2));
                            mUserHeadIv.setVisibility(View.VISIBLE);
                            mTitleTv.setVisibility(View.VISIBLE);
                            mTitleTv.setText("");
                            mPostBtn.setVisibility(View.VISIBLE);
                        } else {
                            ToastUtils.showShortToast("请先登录");
                        }

                        break;
                    case R.id.rb_nearby:
                        showFragment(R.id.frameLayout1, fragments.get(3));
                        mUserHeadIv.setVisibility(View.GONE);
                        mTitleTv.setVisibility(View.GONE);
                        mPostBtn.setVisibility(View.GONE);
                        break;

                    default:
                        break;
                }
            }
        });
    }

    public void getUserInfo() {
        MyUser mUser = BmobUser.getCurrentUser(MyUser.class);
        if (mUser != null) {
            mUserNameTv.setVisibility(View.VISIBLE);
            mLoginBtn.setVisibility(View.GONE);
            mUserNameTv.setText(mUser.getUsername());
            BmobFile avatarFile = mUser.getHeadImage();
            if (null != avatarFile) {
                ImageLoaderUtil.displayImage(avatarFile.getFileUrl(),mLeftHeadIv);
            }
            mIsLogin = true;
        } else {
            mUserNameTv.setVisibility(View.GONE);
            mLoginBtn.setVisibility(View.VISIBLE);
            mIsLogin = false;
        }
    }

    public void showFragment(int id, Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(id, fragment);
        transaction.commit();
    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.iv_user_head:
                mLeftMenu.toggle();
                break;
            case R.id.iv_head_img:
                Intent intentSet = new Intent(MainActivity.this, SetActivity.class);
                startActivity(intentSet);
                break;
            case R.id.rl_download_map:
                Intent intentMap = new Intent(MainActivity.this, MapOfflineActivity.class);
                startActivity(intentMap);
                break;
            case R.id.rl_exercise:
                Intent intent6 = new Intent(MainActivity.this, ExerciseActivity.class);
                startActivity(intent6);
                break;
            case R.id.rl_set:
                Intent intent7 = new Intent(MainActivity.this, SetActivity.class);
                startActivity(intent7);
                break;
            case R.id.rl_feed_back:
                BmobUser user = new BmobUser();
                if (user != null) {
                    //todo 报错 暂时注掉
				Intent intent8=new Intent(MainActivity.this,FeedBackActivity.class);
				startActivity(intent8);
                } else {
                    Toast.makeText(this, "", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.btn_post:
                Intent intent1 = new Intent(MainActivity.this, PublishPostActivity.class);
                startActivity(intent1);
                break;
            case R.id.rl_foot_print:
                if (mIsLogin) {
                    Intent intent2 = new Intent(MainActivity.this, FootPrintActivity.class);
                    startActivity(intent2);
                }else{
                    ToastUtils.showShortToast("请先登录");
                }

                break;
            case R.id.btn_login_rightnow:
                Intent intentLogin = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intentLogin);
                break;
            default:
                break;
        }

    }



}
