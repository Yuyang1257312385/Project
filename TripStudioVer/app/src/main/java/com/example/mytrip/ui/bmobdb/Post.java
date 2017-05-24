package com.example.mytrip.ui.bmobdb;

import java.util.ArrayList;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;
/**
 * ��̬��
 * */
public class Post extends BmobObject {
   private String postContent;
   private BmobUser author;//����̬���ˣ�һ����̬��Ӧһ���û���
   private BmobRelation likeList;//���޵��˵ļ��ϣ�һ����̬��Ӧ�������޵��ˣ�
   private ArrayList<Comments> commentList;//���������б�һ����̬�����ж������ۣ�
   private BmobFile postImage;//�����е�ͼƬ
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
