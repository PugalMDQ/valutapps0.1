package com.MDQ.myapplication.pojo.jsonresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//api response inner arrayList items
public class DataForListTransaction {

    @SerializedName("transaction_id")
    @Expose
    public String transaction_id;

 @SerializedName("user_account_type")
    @Expose
    public String user_account_type;

 @SerializedName("amount")
    @Expose
    public String amount;

 @SerializedName("Date")
    @Expose
    public String Date;

@SerializedName("category")
    @Expose
    public String category;

@SerializedName("category_logo")
    @Expose
    public String category_logo;

@SerializedName("subcategory")
    @Expose
    public String subcategory;

@SerializedName("subcategory_logo")
    @Expose
    public String subcategory_logo;

@SerializedName("note")
    @Expose
    public String note;

@SerializedName("tag")
    @Expose
    public String tag;

@SerializedName("image")
    @Expose
    public String image;

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getUser_account_type() {
        return user_account_type;
    }

    public void setUser_account_type(String user_account_type) {
        this.user_account_type = user_account_type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory_logo() {
        return category_logo;
    }

    public void setCategory_logo(String category_logo) {
        this.category_logo = category_logo;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getSubcategory_logo() {
        return subcategory_logo;
    }

    public void setSubcategory_logo(String subcategory_logo) {
        this.subcategory_logo = subcategory_logo;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getShared_with() {
        return shared_with;
    }

    public void setShared_with(String shared_with) {
        this.shared_with = shared_with;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @SerializedName("type")
    @Expose
    public String type;

@SerializedName("shared_with")
    @Expose
    public String shared_with;

@SerializedName("location")
    @Expose
    public String location;
}
