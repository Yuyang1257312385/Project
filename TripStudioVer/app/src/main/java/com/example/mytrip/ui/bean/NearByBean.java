package com.example.mytrip.ui.bean;
/**
 * */
public class NearByBean {
   private int imgId;
   private String content;
public NearByBean(int imgId, String content) {
	super();
	this.imgId = imgId;
	this.content = content;
}
public int getImgId() {
	return imgId;
}
public void setImgId(int imgId) {
	this.imgId = imgId;
}
public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
}
   
}
