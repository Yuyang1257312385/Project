package com.example.mytrip.ui.bmobdb;

import java.util.ArrayList;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;
public class Post extends BmobObject {
	private String postContent;
	private BmobUser author;//发表动态的人（一条动态对应一个用户）
	private BmobRelation likeList;//点赞的人的集合（一条动态对应多条点赞的人）
	private ArrayList<Comments> commentList;//关联评论列表（一个动态可以有多条评论）
	private BmobFile postImage;//帖子中的图片

	public String getPostContent() {
		return postContent;
	}

	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}

	public BmobUser getAuthor() {
		return author;
	}

	public void setAuthor(BmobUser author) {
		this.author = author;
	}

	public BmobRelation getLikeList() {
		return likeList;
	}

	public void setLikeList(BmobRelation likeList) {
		this.likeList = likeList;
	}

	public ArrayList<Comments> getCommentList() {
		return commentList;
	}

	public void setCommentList(ArrayList<Comments> commentList) {
		this.commentList = commentList;
	}

	public BmobFile getPostImage() {
		return postImage;
	}

	public void setPostImage(BmobFile postImage) {
		this.postImage = postImage;
	}
}
