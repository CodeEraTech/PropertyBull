package com.doomshell.property_bull.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Anuj on 2/17/2017.
 */

public class OtpVerify_model implements Serializable{
    @SerializedName("returned_name")
    @Expose
    String mobile;
    @SerializedName("returned_name")
    @Expose
    String otp;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public OtpVerify_model(String mobile, String otp) {

        this.mobile = mobile;
        this.otp = otp;
    }
}
