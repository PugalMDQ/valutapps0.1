package com.MDQ.myapplication.interfaces.viewresponceinterface;

import com.MDQ.myapplication.interfaces.StateViewInterface;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;

public interface RegisterSuccessResponseInterface extends StateViewInterface {

    void generateRegisterSuccessProcessed(String Token,String msg);
    void onFailure(ErrorBody errorBody, int statusCode);
}
