package com.hpmt.cool100.dbhelp;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table FAULT_HELP.
 */
public class faultHelp {

    private Long id;
    private String Code;
    private String childCode;
    private String name;
    private String reason;
    private String solution;
    private String childName;
    private String type;

    public faultHelp() {
    }

    public faultHelp(Long id) {
        this.id = id;
    }

    public faultHelp(Long id, String Code, String childCode, String name, String reason, String solution, String childName, String type) {
        this.id = id;
        this.Code = Code;
        this.childCode = childCode;
        this.name = name;
        this.reason = reason;
        this.solution = solution;
        this.childName = childName;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public String getChildCode() {
        return childCode;
    }

    public void setChildCode(String childCode) {
        this.childCode = childCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
