package com.example.mytrip.ui.post.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

public class Likes extends BmobObject{
    private BmobUser likeAuthor;
    private Post post;
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
