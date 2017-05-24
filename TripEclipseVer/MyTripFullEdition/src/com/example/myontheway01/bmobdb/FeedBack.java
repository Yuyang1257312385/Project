package com.example.myontheway01.bmobdb;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
//反馈
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
////	//用此方式可以构造一个BmobPointer对象。只需要设置objectId就行
////	Post post = new Post();
////	post.setObjectId("83ce274594");
////	query.addWhereEqualTo("post",new BmobPointer(post));        
////	//希望同时查询该评论的发布者的信息，以及该帖子的作者的信息，这里用到上面`include`的并列对象查询和内嵌对象的查询
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
