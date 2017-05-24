package com.example.mytrip.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.example.mytrip.R;
import com.example.mytrip.ui.bmobdb.MyUser;
import com.example.mytrip.tools.CacheUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SetActivity extends Activity 
                  implements OnClickListener,OnCheckedChangeListener{
	TextView logout;//�˳���¼
	RelativeLayout iconLayout;//ͷ�񲼾�
	ImageView userIcon;//�û�ͷ��
	RelativeLayout nickLayout;//�ǳƲ���
	TextView nickName;//�ǳ�
	RelativeLayout signLayout;//����ǩ������
	TextView signature;//����ǩ��
	 private String targeturl;//ͼƬ�ڱ��صĵ�ַ
	 boolean isLogin=false;
	 CheckBox sexSwitch;
	static final int UPDATE_SEX = 11;
	static final int UPDATE_ICON = 12;
	static final int GO_LOGIN = 13;
	static final int UPDATE_SIGN = 14;
	static final int EDIT_SIGN = 15;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private DisplayImageOptions options;//����ͼƬ���ʲ���
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_set);
    	options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.ic_stub)
		.showImageForEmptyUri(R.drawable.ic_empty)
		.showImageOnFail(R.drawable.ic_error)
		.cacheInMemory(true)
		.cacheOnDisc(true)
		.considerExifParams(true)
		//.displayer(new RoundedBitmapDisplayer(20))
		//.displayer(new CircleBitmapDisplayer())//���������Ϊ����ͼ�������ΪԲ��ͼ
		.build();
    	intiView();
    	initPersonalInfo();
    }
    
    /**
     * ��ʼ���ؼ�
     * */
	protected void intiView() {
		iconLayout = (RelativeLayout) findViewById(R.id.user_icon);
		userIcon = (ImageView) findViewById(R.id.user_icon_image);
		nickLayout = (RelativeLayout) findViewById(R.id.user_nick);
		nickName = (TextView) findViewById(R.id.user_nick_text);
		logout = (TextView) findViewById(R.id.user_logout);
		sexSwitch=(CheckBox) findViewById(R.id.sex_choice_switch);
		signLayout = (RelativeLayout)findViewById(R.id.user_sign);
		signature = (TextView) findViewById(R.id.user_sign_text);
		logout.setOnClickListener(this);
		iconLayout.setOnClickListener(this);
		nickLayout.setOnClickListener(this);
		signLayout.setOnClickListener(this);
		sexSwitch.setOnCheckedChangeListener(this);
	}
	
	private void initPersonalInfo() {
		MyUser user = BmobUser.getCurrentUser(this, MyUser.class);
		if (user != null) {
			//�����ǳ�
			nickName.setText(user.getUsername());
			//���ø���ǩ��
			signature.setText(user.getSign());
			isLogin=true;
			BmobFile avatarFile = user.getHeadImage();
			if (null != avatarFile) {
				imageLoader.displayImage(avatarFile.getFileUrl(this),userIcon , options, animateFirstListener);
			}
			if(user.getSex()!=null){
				if (user.getSex().equals("male")) {
					sexSwitch.setChecked(true);
				} else {
					sexSwitch.setChecked(false);
				}
			}
			
			logout.setText("�˳���¼");
		} else {
			logout.setText("��¼");
		}
		
	}
	
	
	//=====================================================
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			MyUser mUser=new MyUser();
			mUser.setSex("male");
			MyUser bmobUser = BmobUser.getCurrentUser(this,MyUser.class);
			mUser.update(this,bmobUser.getObjectId(),new UpdateListener() {
			    @Override
			    public void onSuccess() {
			    	 Toast.makeText(SetActivity.this, "�޸ĳɹ�", Toast.LENGTH_LONG).show();
			    }
			    @Override
			    public void onFailure(int code, String msg) {
			       Toast.makeText(SetActivity.this, "�޸�ʧ��", Toast.LENGTH_LONG).show();
			    }
			});
		} else {
			MyUser mUser=new MyUser();
			mUser.setSex("female");
			MyUser bmobUser = BmobUser.getCurrentUser(this,MyUser.class);
			mUser.update(this,bmobUser.getObjectId(),new UpdateListener() {
			    @Override
			    public void onSuccess() {
			    	 Toast.makeText(SetActivity.this, "�޸ĳɹ�", Toast.LENGTH_LONG).show();
			    }
			    @Override
			    public void onFailure(int code, String msg) {
			       Toast.makeText(SetActivity.this, "�޸�ʧ��", Toast.LENGTH_LONG).show();
			    }
			});
		}
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.user_icon:
	       
			if(isLogin){
				showAlbumDialog();
			}else{
				Intent intent=new Intent(this,LoginActivity.class);
				startActivity(intent);
			}
	       		
			break;
		case R.id.user_logout:
			if(logout.getText().toString().equals("�˳���¼")){
				MyUser.logOut(this);
				finish();
				Intent intent1=new Intent(this,SetActivity.class);
				startActivity(intent1);
				
			}else{
				Intent intent2=new Intent(this,LoginActivity.class);
				startActivity(intent2);
				finish();
			}
			break;
		case R.id.user_sign:
			Intent intent3=new Intent(this,EditSignActivity.class);
			startActivity(intent3);
			finish();
			break;
		default:
			break;
		}
	}
	String dateTime;
	AlertDialog albumDialog;
	public void showAlbumDialog() {
		albumDialog = new AlertDialog.Builder(this).create();
		albumDialog.setCanceledOnTouchOutside(true);
		View v = LayoutInflater.from(this).inflate(
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

	private void getAvataFromAlbum() {
		Intent intent2 = new Intent(Intent.ACTION_GET_CONTENT);
		intent2.setType("image/*");
		startActivityForResult(intent2, 2);
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			
			case 1:
				String files = CacheUtils.getCacheDirectory(this, true,
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
						userIcon.setImageBitmap(bitmap);
						updateIcon(targeturl);
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
		String files = CacheUtils.getCacheDirectory(this, true, "icon")
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
	/**
	 * �ϴ�����ͷ��
	 * */
	private void updateIcon(String avataPath) {
		if (avataPath != null) {
			final BmobFile file = new BmobFile(new File(avataPath));		
			file.upload(this, new UploadFileListener() {

				@Override
				public void onSuccess() {
					// TODO Auto-generated method stub
					MyUser currentUser = BmobUser.getCurrentUser(SetActivity.this,MyUser.class);
					currentUser.setHeadImage(file);
					currentUser.update(SetActivity.this, new UpdateListener() {

						@Override
						public void onSuccess() {
							Toast.makeText(SetActivity.this, "���ĳɹ�", Toast.LENGTH_LONG).show();
						}
						@Override
						public void onFailure(int arg0, String arg1) {
							// TODO Auto-generated method stub
							Toast.makeText(SetActivity.this, "����ʧ��", Toast.LENGTH_LONG).show();	
						}
					});
				}

				@Override
				public void onProgress(Integer arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onFailure(int arg0, String arg1) {
					
				}
			});
		}
	}
	private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}
}
