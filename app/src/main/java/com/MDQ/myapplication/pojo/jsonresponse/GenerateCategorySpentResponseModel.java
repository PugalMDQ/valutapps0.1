package com.MDQ.myapplication.pojo.jsonresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GenerateCategorySpentResponseModel {

        @SerializedName("statuscode")
        @Expose
        private Integer statuscode;
        @SerializedName("msg")
        @Expose
        private String msg;
        @SerializedName("data")
        @Expose
        private List<DataForCategorySpent> data = null;

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

        public List<DataForCategorySpent> getData() {
            return data;
        }

        public void setData(List<DataForCategorySpent> data) {
            this.data = data;
        }

    }