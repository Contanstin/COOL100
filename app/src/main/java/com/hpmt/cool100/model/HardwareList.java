package com.hpmt.cool100.model;

public class HardwareList {
    private String HardwareId;
    private String elevatorId;
    private String registerNum;
    private String elevatorNum;
     public HardwareList(String HardwareId, String elevatorId, String registerNum, String elevatorNum)
     {
         this.HardwareId=HardwareId;
         this.elevatorId=elevatorId;
         this.registerNum=registerNum;
         this.elevatorNum=elevatorNum;
     }
    public String getHardwareId() {
        return HardwareId;
    }
    public String getelevatorId() {
        return elevatorId;
    }
    public String getregisterNum() {
        return registerNum;
    }
    public String getelevatorNum() {
        return elevatorNum;
    }
}
