package com.MDQ.myapplication.pojo.jsonresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataForBioMetricsValidation {
    @SerializedName("token")
    @Expose
    public String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
