package com.MDQ.myapplication.pojo.jsonresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.stream.Stream;

public class DataForAccountList {


    @SerializedName("id")
    @Expose
    public int id;


      @SerializedName("account_type")
    @Expose
    public String account_type;


      @SerializedName("account_name")
    @Expose
    public String account_name;


      @SerializedName("account_number")
    @Expose
    public String account_number;


      @SerializedName("current_balance")
    @Expose
    public String current_balance;


      @SerializedName("currency")
    @Expose
    public String currency;



      @SerializedName("bank_name")
    @Expose
    public String bank_name;



      @SerializedName("bank_logo")
    @Expose
    public String bank_logo;



      @SerializedName("brand_color_code")
    @Expose
    public String brand_color_code;

      @SerializedName("bg_image")
    @Expose
    public String bg_image;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getCurrent_balance() {
        return current_balance;
    }

    public void setCurrent_balance(String current_balance) {
        this.current_balance = current_balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBank_logo() {
        return bank_logo;
    }

    public void setBank_logo(String bank_logo) {
        this.bank_logo = bank_logo;
    }

    public String getBrand_color_code() {
        return brand_color_code;
    }

    public void setBrand_color_code(String brand_color_code) {
        this.brand_color_code = brand_color_code;
    }

    public String getBg_image() {
        return bg_image;
    }

    public void setBg_image(String bg_image) {
        this.bg_image = bg_image;
    }
}
