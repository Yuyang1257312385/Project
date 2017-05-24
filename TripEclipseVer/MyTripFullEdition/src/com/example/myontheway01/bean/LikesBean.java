package com.example.myontheway01.bean;
/**
 * 存放likes 表中的数据的类
 * */
public class LikesBean {
	private String postId;//赞的哪个帖子
	private String likeAuthorId;//点赞的人的id,用于和当前用户进行比较
	private String likeAuthorName;//点赞的人
	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
	public String getLikeAuthorId() {
		return likeAuthorId;
	}
	public void setLikeAuthorId(String likeAuthorId) {
		this.likeAuthorId = likeAuthorId;
	}
	public String getLikeAuthorName() {
		return likeAuthorName;
	}
	public void setLikeAuthorName(String likeAuthorName) {
		this.likeAuthorName = likeAuthorName;
	}
    

}
