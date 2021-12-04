package com.MDQ.myapplication.pojo.jsonresponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GenerateRegisterResponseModel {

    @SerializedName("statuscode")
    @Expose
    public Integer statuscode;
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("data")
    @Expose
    public DataForRegister data;


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

    public DataForRegister getDataForRegister() {
        return data;
    }

    public void setDataForRegister(DataForRegister data) {
        this.data = data;
    }
}
