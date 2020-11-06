package com.hpmt.cool100.model;

public class ManList {
    private String ManId;
    private String ManName;
    private String Manphone;
     public ManList(String ManId, String ManName, String Manphone)
     {
         this.ManId=ManId;
         this.ManName=ManName;
         this.Manphone=Manphone;
     }
    public String getManId() {
        return ManId;
    }
    public String getManName() {
        return ManName;
    }
    public String getManphone() {
        return Manphone;
    }
}
