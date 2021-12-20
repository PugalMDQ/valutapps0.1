package com.MDQ.myapplication.viewmodel;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;

//api request items
public class UploadVaultBaseviewModel {

    public RequestBody getProof() {
        return Proof;
    }

    public void setProof(RequestBody proof) {
        Proof = proof;
    }

    private RequestBody Proof;
    private String Token;

    public String getEncoded() {
        return encoded;
    }

    public void setEncoded(String encoded) {
        this.encoded = encoded;
    }

    private String encoded;
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
