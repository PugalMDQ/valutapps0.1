package com.MDQ.myapplication.pojo.jsonresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GenerateDashBoardResponseModel {


    @SerializedName("total_amount")
    @Expose
    public String total_amount;
    @SerializedName("total_credit")
    @Expose
    public String total_credit;
    @SerializedName("total_debit")
    @Expose
    public String total_debit;

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getTotal_credit() {
        return total_credit;
    }

    public void setTotal_credit(String total_credit) {
        this.total_credit = total_credit;
    }

    public String getTotal_debit() {
        return total_debit;
    }

    public void setTotal_debit(String total_debit) {
        this.total_debit = total_debit;
    }

    public List<DataForDashBoard> getDay_data() {
        return day_data;
    }

    public void setDay_data(List<DataForDashBoard> day_data) {
        this.day_data = day_data;
    }

    public Integer getStatuscode() {
        return status_code;
    }

    public void setStatuscode(Integer statuscode) {
        this.status_code = statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @SerializedName("day_data")
    @Expose
    public List<DataForDashBoard> day_data;

    @SerializedName("status_code")
    @Expose
    public Integer status_code;
    @SerializedName("msg")
    @Expose
    public String msg;



}
