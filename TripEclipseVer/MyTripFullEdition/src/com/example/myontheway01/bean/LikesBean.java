package com.example.myontheway01.bean;
/**
 * ���likes ���е����ݵ���
 * */
public class LikesBean {
	private String postId;//�޵��ĸ�����
	private String likeAuthorId;//���޵��˵�id,���ں͵�ǰ�û����бȽ�
	private String likeAuthorName;//���޵���
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
