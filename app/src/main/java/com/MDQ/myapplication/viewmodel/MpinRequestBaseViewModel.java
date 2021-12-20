package com.MDQ.myapplication.viewmodel;

//api request items
public class MpinRequestBaseViewModel {
    //this  method also used for mpinValidation
    private String token;
    private String mpin;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMpin() {
        return mpin;
    }

    public void setMpin(String mpin) {
        this.mpin = mpin;
    }
}
