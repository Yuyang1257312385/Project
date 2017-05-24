package com.example.myontheway01.bmobdb;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
/**
 * 数据库中Likes 表的操作类
 * */
public class Likes extends BmobObject{
    private BmobUser likeAuthor;//点赞人
    private Post post;//点赞的帖子
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
