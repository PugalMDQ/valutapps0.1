package com.MDQ.myapplication.interfaces.viewresponceinterface;

import com.MDQ.myapplication.interfaces.StateViewInterface;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;

//Response interface for Register Success
public interface RegisterSuccessResponseInterface extends StateViewInterface {

    void generateRegisterSuccessProcessed(String Token,String msg);
    void onFailure(ErrorBody errorBody, int statusCode);
}
