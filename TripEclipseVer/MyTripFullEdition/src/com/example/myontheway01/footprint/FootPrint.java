package com.example.myontheway01.footprint;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

public class FootPrint extends BmobObject {
	
	private static final long serialVersionUID = 1L;
	
	private String mAdds;//发表的地点
	private BmobUser mUserName;//发表的用户
	private String mContent;//发表的消息内容
	
	public String getmAdds() {
		return mAdds;
	}
	public void setmAdds(String mAdds) {
		this.mAdds = mAdds;
	}
	public BmobUser getmUserName() {
		return mUserName;
	}
	public void setmUserName(BmobUser mUserName) {
		this.mUserName = mUserName;
	}
	public String getmContent() {
		return mContent;
	}
	public void setmContent(String mContent) {
		this.mContent = mContent;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	


	

}
