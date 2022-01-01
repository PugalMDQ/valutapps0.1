package com.MDQ.myapplication.interfaces.viewresponceinterface;

import com.MDQ.myapplication.interfaces.StateViewInterface;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;

//Response interface for Login
public interface LoginResponseInterface extends StateViewInterface {
    void generateLoginProcessed(String Token,String Otp);
    void onFailure(ErrorBody errorBody, int statusCode);
}
