package com.example.mytrip.ui.bmobdb;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
/**
 * ���ݿ���Likes ��Ĳ�����
 * */
public class Likes extends BmobObject{
    private BmobUser likeAuthor;//������
    private Post post;//���޵�����
	public BmobUser getLikeAuthor() {
		return likeAuthor;
	}
	public void setLikeAuthor(BmobUser likeAuthor) {
		this.likeAuthor = likeAuthor;
	}
	public Post getPost() {
		return post;
	}
	public void setPost(Post post) {
		this.post = post;
	}
    
}
