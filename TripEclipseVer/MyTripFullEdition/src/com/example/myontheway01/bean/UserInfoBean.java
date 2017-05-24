package com.example.myontheway01.bean;

import java.io.Serializable;

/**
 *人物实体类
 *
 **/
public class UserInfoBean implements Serializable {
	private static final long serialVersionUID = 642225199212241050L;
	/**
	 * 姓名
	 */
	private String nickname;
	private String avatar;

	private String UserId;

	private String username;

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

}
