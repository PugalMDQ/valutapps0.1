package com.MDQ.myapplication.viewmodel;

import okhttp3.MultipartBody;

//api request items
public class AddSpentBaseViewModel {
    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
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

    public String getShare_with() {
        return share_with;
    }

    public void setShare_with(String share_with) {
        this.share_with = share_with;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public MultipartBody.Part getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(MultipartBody.Part profile_picture) {
        this.profile_picture = profile_picture;
    }

    private String account_id;
    private String amount;
    private String type;
    private String category;
    private String subcategory;
    private String Date;
    private String note;
    private String tag;
    private String share_with;
    private String location;
    private MultipartBody.Part profile_picture;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String token;

}
