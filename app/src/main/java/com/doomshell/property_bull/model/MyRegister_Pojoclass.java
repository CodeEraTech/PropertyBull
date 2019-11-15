package com.doomshell.property_bull.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Anuj on 2/16/2017.
 */

public class MyRegister_Pojoclass implements Serializable{

    @SerializedName("returned_name")
    @Expose
    String name;
    @SerializedName("returned_lname")
    @Expose
    String lname;
    @SerializedName("returned_username")
    @Expose
    String username;
    @SerializedName("returned_landline")
    @Expose
    String landline;

    @Override
    public String toString() {
        return "MyPojoclass{" +
                "name='" + name + '\'' +
                ", lname='" + lname + '\'' +
                ", username='" + username + '\'' +
                ", landline='" + landline + '\'' +
                ", mobile='" + mobile + '\'' +
                ", list_n='" + list_n + '\'' +
                ", list_p='" + list_p + '\'' +
                '}';
    }

    @SerializedName("returned_mobile")
    @Expose
    String mobile;
    @SerializedName("returned_listn")
    @Expose
    String list_n;
    @SerializedName("returned_listp")
    @Expose
    String list_p;

    public MyRegister_Pojoclass(String name, String lname, String username, String landline, String mobile, String list_n, String list_p) {
        this.name = name;
        this.lname = lname;
        this.username = username;
        this.landline = landline;
        this.mobile = mobile;
        this.list_n = list_n;
        this.list_p = list_p;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLandline() {
        return landline;
    }

    public void setLandline(String landline) {
        this.landline = landline;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getList_n() {
        return list_n;
    }

    public void setList_n(String list_n) {
        this.list_n = list_n;
    }

    public String getList_p() {
        return list_p;
    }

    public void setList_p(String list_p) {
        this.list_p = list_p;
    }
}
