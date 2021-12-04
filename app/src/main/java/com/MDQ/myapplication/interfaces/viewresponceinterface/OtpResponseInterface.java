package com.MDQ.myapplication.interfaces.viewresponceinterface;


import com.MDQ.myapplication.interfaces.StateViewInterface;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;

public interface OtpResponseInterface extends StateViewInterface {
        void generateOtpProcessed(String Otp);
        void onFailure(ErrorBody errorBody, int statusCode);
    }


