package com.byzx.myhotel.bean;

import java.io.Serializable;

/**
 * 存放登记的信息
 * */
public class CheckInInfoBean implements Serializable{
	private static final long serialVersionUID = 1L;
    //声明变量
	private String mClientName;
	private String mClientSex;
	private String mClientId;
	private String mRoomNum;
	private String mRoomPrice;
	private String mCheckInTime;
	//构造方法
	public CheckInInfoBean(){};
	public CheckInInfoBean(String mClientName, String mClientSex,
			String mClientId, String mRoomNum, String mRoomPrice,
			String mCheckInTime) {
		super();
		this.mClientName = mClientName;
		this.mClientSex = mClientSex;
		this.mClientId = mClientId;
		this.mRoomNum = mRoomNum;
		this.mRoomPrice = mRoomPrice;
		this.mCheckInTime = mCheckInTime;
	}
	//get set 方法
	public String getmClientName() {
		return mClientName;
	}
	
	public void setmClientName(String mClientName) {
		this.mClientName = mClientName;
	}
	public String getmClientSex() {
		return mClientSex;
	}
	public void setmClientSex(String mClientSex) {
		this.mClientSex = mClientSex;
	}
	public String getmClientId() {
		return mClientId;
	}
	public void setmClientId(String mClientId) {
		this.mClientId = mClientId;
	}
	public String getmRoomNum() {
		return mRoomNum;
	}
	public void setmRoomNum(String mRoomNum) {
		this.mRoomNum = mRoomNum;
	}
	public String getmRoomPrice() {
		return mRoomPrice;
	}
	public void setmRoomPrice(String mRoomPrice) {
		this.mRoomPrice = mRoomPrice;
	}
	public String getmCheckInTime() {
		return mCheckInTime;
	}
	public void setmCheckInTime(String mCheckInTime) {
		this.mCheckInTime = mCheckInTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
