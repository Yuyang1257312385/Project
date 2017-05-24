package com.example.myontheway01.bmobdb;

import cn.bmob.v3.BmobObject;
/**
 * 用户信息表
 * */
public class UserInfo extends BmobObject {
  private String userName;
  private String passWord;
  private String phoneNumber;
  private String headImg;
  private String sign;
public String getUserName() {
	return userName;
}
public void setUserName(String userName) {
	this.userName = userName;
}
public String getPassWord() {
	return passWord;
}
public void setPassWord(String passWord) {
	this.passWord = passWord;
}
public String getPhoneNumber() {
	return phoneNumber;
}
public void setPhoneNumber(String phoneNumber) {
	this.phoneNumber = phoneNumber;
}
public String getHeadImg() {
	return headImg;
}
public void setHeadImg(String headImg) {
	this.headImg = headImg;
}
public String getSign() {
	return sign;
}
public void setSign(String sign) {
	this.sign = sign;
}
  
}
