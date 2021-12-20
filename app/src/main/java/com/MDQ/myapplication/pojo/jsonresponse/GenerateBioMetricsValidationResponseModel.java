package com.MDQ.myapplication.pojo.jsonresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GenerateBioMetricsValidationResponseModel {

    @SerializedName("statuscode")
    @Expose
    public Integer statuscode;
    @SerializedName("msg")
    @Expose
    public String msg;

    public Integer getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(Integer statuscode) {
        this.statuscode = statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataForBioMetricsValidation getData() {
        return data;
    }

    public void setData(DataForBioMetricsValidation data) {
        this.data = data;
    }

    @SerializedName("data")
        @Expose
        public DataForBioMetricsValidation data;

}
