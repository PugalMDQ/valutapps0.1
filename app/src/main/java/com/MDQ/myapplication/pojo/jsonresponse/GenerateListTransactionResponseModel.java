package com.MDQ.myapplication.pojo.jsonresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GenerateListTransactionResponseModel {

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

    public List<DataForListTransaction> getData() {
        return data;
    }

    public void setData(List<DataForListTransaction> data) {
        this.data = data;
    }

    @SerializedName("data")
    @Expose
    public List<DataForListTransaction> data;

    @SerializedName("statuscode")
    @Expose
    public Integer statuscode;
    @SerializedName("msg")
    @Expose
    public String msg;

 @SerializedName("bank_detail")
    @Expose
    public bank_detailforListTransaction bank_detail;

}
