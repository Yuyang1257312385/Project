package com.example.myontheway01.bmobdb;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
/**
 * ���۱�
 * */
public class Comments extends BmobObject {
   private BmobUser commentAuthor;//������
   private Post post;//���۵���һ����̬
   private String commentContent;//��������
   private String replyTo;//�ظ�����˭
   
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
