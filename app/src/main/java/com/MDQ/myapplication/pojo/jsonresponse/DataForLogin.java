package com.MDQ.myapplication.pojo.jsonresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataForLogin {
    @SerializedName("token")
    @Expose
    public String token;


    @SerializedName("otp")
    @Expose
    public String otp;





    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

}

