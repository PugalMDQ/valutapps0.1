package com.MDQ.myapplication.pojo.jsonresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class bank_detailforListTransaction {

    @SerializedName("id")
    @Expose
    public String id;

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

   @SerializedName("acc_name")
    @Expose
    public String acc_name;

   @SerializedName("account_number")
    @Expose
    public String account_number;

     @SerializedName("current_balance")
    @Expose
    public String current_balance;



   }
