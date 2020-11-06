package com.hpmt.cool100.model;

public class BuildingList {
    private String BuildId;
    private String BuildCityDistrict;
    private String BuildcompanyName;
     public BuildingList(String BuildId, String BuildCityDistrict, String BuildcompanyName)
     {
         this.BuildId=BuildId;
         this.BuildCityDistrict=BuildCityDistrict;
         this.BuildcompanyName=BuildcompanyName;
     }
    public String getBuildId() {
        return BuildId;
    }

    public String getBuildCityDistrict() {
        return BuildCityDistrict;
    }
    public String getBuildcompanyName() {
        return BuildcompanyName;
    }
}
