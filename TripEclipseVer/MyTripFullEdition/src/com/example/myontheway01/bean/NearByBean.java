package com.example.myontheway01.bean;
/**
 * ��������񲼾��е�ÿ��item�е���
 * */
public class NearByBean {
   private int imgId;//ͼƬid
   private String content;//����
   //���췽�������㴴������
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
