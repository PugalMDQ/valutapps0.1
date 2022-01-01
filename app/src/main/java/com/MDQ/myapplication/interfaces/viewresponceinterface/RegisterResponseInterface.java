package com.MDQ.myapplication.interfaces.viewresponceinterface;

import com.MDQ.myapplication.interfaces.StateViewInterface;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;

//Response interface for Register
public interface RegisterResponseInterface extends StateViewInterface {

    void generateRegisterProcessed(String Token,String Otp);
    void onFailure(ErrorBody errorBody, int statusCode);

}
