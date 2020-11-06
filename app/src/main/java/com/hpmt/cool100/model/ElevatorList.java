package com.hpmt.cool100.model;

public class ElevatorList {
    private String EleId;
    private String ElevatorNum;
    private String InsideNum;
     public  ElevatorList(String ElevatorNum,String InsideNum,String EleId)
     {
         this.ElevatorNum=ElevatorNum;
         this.InsideNum=InsideNum;
         this.EleId=EleId;
     }
    public String getElevatorNum() {
        return ElevatorNum;
    }

    public String getInsideNum() {
        return InsideNum;
    }
    public String getEleId() {
        return EleId;
    }
}
