package com.example.mytrip.ui.post;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

import com.example.mytrip.R;
import com.example.mytrip.tools.ToastUtils;
import com.example.mytrip.tools.CacheUtils;
import com.example.mytrip.ui.post.bean.Post;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 发表说说Activity
 */
public class PublishPostActivity extends Activity implements OnClickListener {
    private Context mContext;
    private TextView mPublishTv;//发表说说
    private TextView mQuitPublishTv;//取消
    private EditText mPostContentEt;//动态输入框
    private String postContent;//动态的内容
    private ImageView mPostImageIv;//添加动态图片
    private AlertDialog albumDialog;//显示选择拍照或图库的dialog
    private String dateTime;//日期
    private String targeturl;//图片在本地的地址

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_post);
        intiView();
        //注册
    }

    /**
     * 初始化控件
     */
    private void intiView() {
        mContext = this;
        mPublishTv = (TextView) findViewById(R.id.tv_publish);
        mQuitPublishTv = (TextView) findViewById(R.id.tv_quit_publish);
        mPublishTv.setOnClickListener(this);
        mQuitPublishTv.setOnClickListener(this);
        mPostContentEt = (EditText) findViewById(R.id.et_post_content);
        mPostImageIv = (ImageView) findViewById(R.id.iv_post);
        mPostImageIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.tv_publish:
                //将数据写入数据库
                publish();

                finish();
                break;
            case R.id.tv_quit_publish:
                finish();
                break;
            case R.id.iv_post:
                //弹出选择相机或图库的dialog
                showAlbumDialog();

                break;
            default:
                break;
        }
    }

    /**
     * 将动态的数据保存到数据库，保存成功后发送广播刷新动态列表
     */
    private void insertPostData(BmobFile figureFile) {
        // TODO Auto-generated method stub
        Post post = new Post();
        postContent = mPostContentEt.getText().toString().trim();
        BmobUser user = BmobUser.getCurrentUser();
        if (user != null) {
            post.setAuthor(user);
        }
        post.setPostContent(postContent);
        post.setPostImage(figureFile);
        post.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Toast.makeText(mContext, "发表成功", Toast.LENGTH_LONG).show();
                    //发送广播至PostFragment,刷新动态列表
                    Intent mIntent = new Intent("com.example.myontheway01");//通过指定发送的频道
                    sendBroadcast(mIntent);
                } else {
                    //由于Bmob原因，有时数据保存成功，但提示是失败，故在此也发送广播
                    Intent mIntent = new Intent("com.example.myontheway01");//通过指定发送的频道
                    sendBroadcast(mIntent);
                    ToastUtils.showShortToast(e.getMessage() + "("+ e.getErrorCode()+")");
                }
            }
        });

    }

    public void showAlbumDialog() {
        albumDialog = new AlertDialog.Builder(mContext).create();
        albumDialog.setCanceledOnTouchOutside(true);
        View v = LayoutInflater.from(mContext).inflate(
                R.layout.dialog_usericon, null);
        albumDialog.show();
        albumDialog.setContentView(v);
        albumDialog.getWindow().setGravity(Gravity.CENTER);

        TextView albumPic = (TextView) v.findViewById(R.id.album_pic);
        TextView cameraPic = (TextView) v.findViewById(R.id.camera_pic);
        albumPic.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                albumDialog.dismiss();
                Date date1 = new Date(System.currentTimeMillis());
                dateTime = date1.getTime() + "";
                getAvataFromAlbum();
            }
        });
        cameraPic.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                albumDialog.dismiss();
                Date date = new Date(System.currentTimeMillis());
                dateTime = date.getTime() + "";
                getAvataFromCamera();
            }
        });
    }

    /**
     * 从相册中获取图片
     */
    private void getAvataFromAlbum() {
        Intent intent2 = new Intent(Intent.ACTION_GET_CONTENT);
        intent2.setType("image/*");
        startActivityForResult(intent2, 2);
    }

    /**
     * 拍照获取图片
     */
    private void getAvataFromCamera() {
        File f = new File(CacheUtils.getCacheDirectory(this, true, "icon")
                + dateTime);
        if (f.exists()) {
            f.delete();
        }
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Uri uri = Uri.fromFile(f);
        Log.e("uri", uri + "");

        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        camera.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(camera, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {

                case 1:
                    String files = CacheUtils.getCacheDirectory(mContext, true,
                            "icon") + dateTime;
                    File file = new File(files);
                    if (file.exists() && file.length() > 0) {
                        Uri uri = Uri.fromFile(file);
                        startPhotoZoom(uri);
                    } else {

                    }
                    break;
                case 2:
                    if (data == null) {
                        return;
                    }
                    startPhotoZoom(data.getData());
                    break;
                case 3:
                    if (data != null) {
                        Bundle extras = data.getExtras();
                        if (extras != null) {
                            Bitmap bitmap = extras.getParcelable("data");
                            targeturl = saveToSdCard(bitmap);
                            mPostImageIv.setImageBitmap(bitmap);
                        /*updateIcon(iconUrl);*/
                        }
                    }
                    break;

                default:
                    break;
            }
        }
    }

    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        intent.putExtra("outputX", 120);
        intent.putExtra("outputY", 120);
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);

        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);

    }

    public String saveToSdCard(Bitmap bitmap) {
        String files = CacheUtils.getCacheDirectory(mContext, true, "icon")
                + dateTime + "_12.jpg";
        File file = new File(files);
        try {
            FileOutputStream out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)) {
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return file.getAbsolutePath();
    }

    /*
     * 发表带图片
     */
    private void publish() {

        // final BmobFile figureFile = new BmobFile(QiangYu.class, new
        // File(targeturl));

        final BmobFile figureFile = new BmobFile(new File(targeturl));

        figureFile.uploadblock(new UploadFileListener() {


            @Override
            public void done(BmobException e) {
                if (e == null) {
                    //bmobFile.getFileUrl()--返回的上传文件的完整地址
                    ToastUtils.showShortToast("上传文件成功:" + figureFile.getFileUrl());
                    insertPostData(figureFile);
                } else {
                    ToastUtils.showShortToast("上传文件失败：" + e.getMessage());
                }

            }
        });

    }


}
