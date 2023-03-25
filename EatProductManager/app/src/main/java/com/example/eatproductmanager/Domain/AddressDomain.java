package com.example.eatproductmanager.Domain;

public class AddressDomain {
    private String detail, extraInformation, name, userID;

    public AddressDomain() {
    }

    public AddressDomain(String detail, String extraInformation, String name, String userID) {
        this.detail = detail;
        this.extraInformation = extraInformation;
        this.name = name;
        this.userID = userID;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getExtraInformation() {
        return extraInformation;
    }

    public void setExtraInformation(String extraInformation) {
        this.extraInformation = extraInformation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
