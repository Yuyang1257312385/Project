package com.example.myontheway01.bmobdb;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
/**
 * 评论表
 * */
public class Comments extends BmobObject {
   private BmobUser commentAuthor;//评论人
   private Post post;//评论的那一条动态
   private String commentContent;//评论内容
   private String replyTo;//回复的是谁
   
public String getReplyTo() {
	return replyTo;
}
public void setReplyTo(String replyTo) {
	this.replyTo = replyTo;
}
public BmobUser getCommentAuthor() {
	return commentAuthor;
}
public void setCommentAuthor(BmobUser commentAuthor) {
	this.commentAuthor = commentAuthor;
}
public Post getPost() {
	return post;
}
public void setPost(Post post) {
	this.post = post;
}
public String getCommentContent() {
	return commentContent;
}
public void setCommentContent(String commentContent) {
	this.commentContent = commentContent;
}
   
}
