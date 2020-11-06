package com.hpmt.cool100.model;

public class UnitList {
    private String UnitId;
    private String  UnitcompanyName;
    private String UnitacountName;
     public UnitList(String UnitId, String UnitcompanyName, String UnitacountName)
     {
         this.UnitId=UnitId;
         this.UnitcompanyName=UnitcompanyName;
         this.UnitacountName=UnitacountName;
     }
    public String getUnitId() {
        return UnitId;
    }
    public String getUnitcompanyName() {
        return UnitcompanyName;
    }
    public String getUnitacountName() {
        return UnitacountName;
    }
}
