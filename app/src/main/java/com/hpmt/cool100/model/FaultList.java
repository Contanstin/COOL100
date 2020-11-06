package com.hpmt.cool100.model;

public class FaultList {
    private String faultcode;
    private String sub_code;
    private String faultfloor;
    private String faulttime;
    private String faultname;
    private String faultreason;
    private String faultmethod;
    public  FaultList(String faultcode,String sub_code,String faultfloor,String faulttime,String faultname,String faultreason,String faultmethod)
    {
        this.faultcode=faultcode;
        this.sub_code=sub_code;
        this.faultfloor=faultfloor;
        this.faulttime=faulttime;
        this.faultname=faultname;
        this.faultreason=faultreason;
        this.faultmethod=faultmethod;
    }
    public String getfaultcode() {
        return faultcode;
    }

    public String getsub_code() {
        return sub_code;
    }
    public String getfaultfloor() {
        return faultfloor;
    }

    public String getfaulttime() {
        return faulttime;
    }
    public String getfaultname() { return faultname; }
    public String getfaultreason() {
        return faultreason;
    }
    public String getfaultmethod() { return faultmethod; }
}
