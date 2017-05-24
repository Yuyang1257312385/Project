package com.example.mytrip.ui.bmobdb;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
//����
public class FeedBack extends BmobObject{
	private BmobUser feedBackUser;
	private String feedBackContent;
	public BmobUser getFeedBackUser() {
		return feedBackUser;
	}
	public void setFeedBackUser(BmobUser feedBackUser) {
		this.feedBackUser = feedBackUser;
	}
	public String getFeedBackContent() {
		return feedBackContent;
	}
	public void setFeedBackContent(String feedBackContent) {
		this.feedBackContent = feedBackContent;
	}

	
////=======================================
//	BmobQuery<FeedBack> query = new BmobQuery<FeedBack>();
////	//�ô˷�ʽ���Թ���һ��BmobPointer����ֻ��Ҫ����objectId����
////	Post post = new Post();
////	post.setObjectId("83ce274594");
////	query.addWhereEqualTo("post",new BmobPointer(post));        
////	//ϣ��ͬʱ��ѯ�����۵ķ����ߵ���Ϣ���Լ������ӵ����ߵ���Ϣ�������õ�����`include`�Ĳ��ж����ѯ����Ƕ����Ĳ�ѯ
//	query.include("user");
//	query.findObjects(this, new FindListener<FeedBack>() {
//
//	    @Override
//	    public void onSuccess(List<FeedBack> object) {
//	        // TODO Auto-generated method stub
//	        ...
//	    }
//
//	    @Override
//	    public void onError(int code, String msg) {
//	        // TODO Auto-generated method stub
//	        ...
//	    }
//	});
}
