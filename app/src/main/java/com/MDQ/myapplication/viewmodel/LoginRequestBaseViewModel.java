package com.MDQ.myapplication.viewmodel;

//api request items
public class LoginRequestBaseViewModel {

    private String phone;
    private String country_code;

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

}
