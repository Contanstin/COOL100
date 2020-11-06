package com.hpmt.cool100.model;

import java.util.ArrayList;

public class BinaryModel {

	/**
	 * 二进制显示模型
	 */
	private String name;
	private boolean isCheck;
	private String trueStr;
	private String fasleStr;
	private ArrayList<String> bitArr;
	private int index;
	private String bitStr;
	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBitStr() {
		return bitStr;
	}

	public void setBitStr(String bitStr) {
		this.bitStr = bitStr;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public ArrayList<String> getBitArr() {
		return bitArr;
	}

	public void setBitArr(ArrayList<String> bitArr) {
		this.bitArr = bitArr;
	}

	public String getTrueStr() {
		return trueStr;
	}

	public void setTrueStr(String trueStr) {
		this.trueStr = trueStr;
	}

	public String getFasleStr() {
		return fasleStr;
	}

	public void setFasleStr(String fasleStr) {
		this.fasleStr = fasleStr;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isCheck() {
		return isCheck;
	}

	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}

}
