package com.MDQ.myapplication.pojo.jsonresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataForBankList {

    @SerializedName("id")
    @Expose
    public int id;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("logo")
    @Expose
    public String logo;

    @SerializedName("brand_color_code")
    @Expose
    public String brand_color_code;

    @SerializedName("bg_image")
    @Expose
    public String bg_image;

    @SerializedName("token")
    @Expose
    public String token;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

