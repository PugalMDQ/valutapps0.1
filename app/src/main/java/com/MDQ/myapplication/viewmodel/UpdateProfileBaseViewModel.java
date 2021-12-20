package com.MDQ.myapplication.viewmodel;

//api request items
public class UpdateProfileBaseViewModel {

    public String Authtoken;
    public String Fullname;
    public String Dob;
    public String Phone;
    public String Countrycode;
    public String Mpin;

    public String getAuthtoken() {
        return Authtoken;
    }

    public void setAuthtoken(String authtoken) {
        Authtoken = authtoken;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }

    public String getDob() {
        return Dob;
    }

    public void setDob(String dob) {
        Dob = dob;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getCountrycode() {
        return Countrycode;
    }

    public void setCountrycode(String countrycode) {
        Countrycode = countrycode;
    }

    public String getMpin() {
        return Mpin;
    }

    public void setMpin(String mpin) {
        Mpin = mpin;
    }
}
