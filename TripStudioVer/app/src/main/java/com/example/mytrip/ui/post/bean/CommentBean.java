package com.example.mytrip.ui.post.bean;
/**
 * */
public class CommentBean {
	private String postId;
   private String commentAuthor;
   private String commentContent;
   private String replyTo;
   public CommentBean(){}
public CommentBean(String commentAuthor, String commentContent) {
	super();
	this.commentAuthor = commentAuthor;
	this.commentContent = commentContent;
}

public String getReplyTo() {
	return replyTo;
}
public void setReplyTo(String replyTo) {
	this.replyTo = replyTo;
}
public String getPostId() {
	return postId;
}
public void setPostId(String postId) {
	this.postId = postId;
}
public String getCommentAuthor() {
	return commentAuthor;
}
public void setCommentAuthor(String commentAuthor) {
	this.commentAuthor = commentAuthor;
}
public String getCommentContent() {
	return commentContent;
}
public void setCommentContent(String commentContent) {
	this.commentContent = commentContent;
}
   
}
