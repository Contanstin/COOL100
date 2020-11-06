package com.hpmt.cool100.model;

public class FloorModel {
	private String FloorNum;
	private String FloorStaus;// 0无登记 1：有登记 2：上行  3：下行

	public String getFloorNum() {
		return FloorNum;
	}

	public void setFloorNum(String floorNum) {
		FloorNum = floorNum;
	}

	public String getFloorStaus() {
		return FloorStaus;
	}

	public void setFloorStaus(String floorStaus) {
		FloorStaus = floorStaus;
	}
}
