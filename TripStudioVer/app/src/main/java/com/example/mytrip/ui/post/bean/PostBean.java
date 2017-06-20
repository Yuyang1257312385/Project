package com.example.mytrip.ui.post.bean;

import com.example.mytrip.ui.post.bean.CommentBean;
import com.example.mytrip.ui.post.bean.LikesBean;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 *
 * */
public class PostBean {
   private String postId;//
   private String headImgUrl;//ͷ
   private String postAuthor;//
   private String postTime;//
   private String postContent;//
   private String postImg;//
   private List<LikesBean> postLikes;
   private List<CommentBean> commentList;
   private BmobFile postImageUrl;
   public PostBean(){}
   public PostBean(String headImgUrl, String postAuthor, String postTime,
		String postContent, String postImg, List<LikesBean> postLikes,
		List<CommentBean> commentList) {
	super();
	this.headImgUrl = headImgUrl;
	this.postAuthor = postAuthor;
	this.postTime = postTime;
	this.postContent = postContent;
	this.postImg = postImg;
	this.postLikes = postLikes;
	this.commentList = commentList;
}
   
public String getPostId() {
	return postId;
}

public void setPostId(String postId) {
	this.postId = postId;
}

public String getHeadImgUrl() {
	return headImgUrl;
}
public void setHeadImgUrl(String headImgUrl) {
	this.headImgUrl = headImgUrl;
}
public String getPostAuthor() {
	return postAuthor;
}
public void setPostAuthor(String postAuthor) {
	this.postAuthor = postAuthor;
}
public String getPostTime() {
	return postTime;
}
public void setPostTime(String postTime) {
	this.postTime = postTime;
}
public String getPostContent() {
	return postContent;
}
public void setPostContent(String postContent) {
	this.postContent = postContent;
}
public String getPostImg() {
	return postImg;
}
public void setPostImg(String postImg) {
	this.postImg = postImg;
}
public List<LikesBean> getPostLikes() {
	return postLikes;
}
public void setPostLikes(List<LikesBean> postLikes) {
	this.postLikes = postLikes;
}
public List<CommentBean> getCommentList() {
	return commentList;
}
public void setCommentList(List<CommentBean> commentList) {
	this.commentList = commentList;
}
public BmobFile getPostImageUrl() {
	return postImageUrl;
}
public void setPostImageUrl(BmobFile postImageUrl) {
	this.postImageUrl = postImageUrl;
}
}
