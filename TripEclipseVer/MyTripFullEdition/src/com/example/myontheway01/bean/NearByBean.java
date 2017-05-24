package com.example.myontheway01.bean;
/**
 * 存放在网格布局中的每个item中的类
 * */
public class NearByBean {
   private int imgId;//图片id
   private String content;//内容
   //构造方法，方便创建对象
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
