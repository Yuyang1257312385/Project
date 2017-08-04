package com.lyj.draw;

import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.lyj.draw.customwidget.DrawView;
import com.lyj.draw.tools.LineShapeDrawer;
import com.lyj.draw.tools.RectShapeDrawer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private ActionBarDrawerToggle mDrawerToggle;

    @BindView(R.id.tl_custom)
    Toolbar toolbar;
    @BindView(R.id.dl_left)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.view_draw)
    DrawView drawView;

    @BindView(R.id.btn_line)
    Button mLineBtn;
    @BindView(R.id.btn_rect)
    Button mRectBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initToolbar();

        drawView.setShapeDrawer(new LineShapeDrawer(drawView));
    }

    @OnClick({R.id.btn_rect,R.id.btn_line})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_rect:
                drawView.setShapeDrawer(new RectShapeDrawer(drawView));
                mDrawerLayout.closeDrawers();
                break;
            case R.id.btn_line:
                drawView.setShapeDrawer(new LineShapeDrawer(drawView));
                mDrawerLayout.closeDrawers();
                break;
        }
    }

    private void initToolbar() {
        toolbar.setTitle("Toolbar");//设置Toolbar标题
        toolbar.setTitleTextColor(Color.parseColor("#ffffff")); //设置标题颜色
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //创建返回键，并实现打开关/闭监听
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close) {
            //侧滑布局打开
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            //侧滑布局关闭
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerToggle.syncState();
        //设置监听
        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }
}
