package com.byzx.myhotel.bean;
/**
 * ��ŷ���źͷ���״̬
 * 
 * */
public class RoomNumBean {
   private String roomNum;
   private int state;

   
   public RoomNumBean(String roomNum, int state) {
	super();
	this.roomNum = roomNum;
	this.state = state;
}
public String getRoomNum() {
	return roomNum;
}
public void setRoomNum(String roomNum) {
	this.roomNum = roomNum;
}
public int getState() {
	return state;
}
public void setState(int state) {
	this.state = state;
}
   
}
