package com.MDQ.myapplication.viewmodel;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;

public class UploadVaultBaseviewModel {

    public RequestBody getProof() {
        return Proof;
    }

    public void setProof(RequestBody proof) {
        Proof = proof;
    }

    private RequestBody Proof;
    private String Token;
    private MultipartBody.Part filename;

    public MultipartBody.Part getFilename() {
        return filename;
    }

    public void setFilename(MultipartBody.Part filename) {
        this.filename = filename;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }


}
