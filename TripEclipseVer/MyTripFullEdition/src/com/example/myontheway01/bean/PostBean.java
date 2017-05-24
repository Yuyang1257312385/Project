package com.example.myontheway01.bean;

import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 存放动态内容的类
 * */
public class PostBean {
   private String postId;//帖子的iD
   private String headImgUrl;//头像的url
   private String postAuthor;//动态发表者
   private String postTime;//动态发表时间
   private String postContent;//动态内容
   private String postImg;//动态图片
   private List<LikesBean> postLikes;//点赞的人
   private List<CommentBean> commentList;//评论集合，存放评论的类（包括评论人和评论内容）
   private BmobFile postImageUrl;//帖子图片
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
