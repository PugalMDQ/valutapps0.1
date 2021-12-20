package com.MDQ.myapplication.pojo.jsonresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//api response inner arrayList items
public class DataForGetUser {

    @SerializedName("user_name")
    @Expose
    public String user_name;


    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    @SerializedName("DOB")
    @Expose
    public String DOB;

    @SerializedName("email")
    @Expose
    public String email;

    @SerializedName("phone")
    @Expose
    public String phone;

    @SerializedName("country_code")
    @Expose
    public String country_code;


    public String getBiometrics_status() {
        return biometrics_status;
    }

    public void setBiometrics_status(String biometrics_status) {
        this.biometrics_status = biometrics_status;
    }

    @SerializedName("biometrics_status")
    @Expose
    public String biometrics_status;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @SerializedName("token")
    @Expose
    public String token;


}
