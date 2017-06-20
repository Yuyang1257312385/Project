package com.byzx.edu4001.interfac;
/**
 * 该接口用来在CheckInListActivity和CheckInAdaper之间传递方法和值
 * */
public interface CheckInInfoInterface {
	int i=0;
	public void onUpdateUIListener();
	/**
	 *将CheckInListActivity中获得的点击的那个按钮的值传给CheckInAdapter
	 * */
	public int getI();
}