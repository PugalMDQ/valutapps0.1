package com.MDQ.myapplication.interfaces.viewresponceinterface;

import com.MDQ.myapplication.interfaces.StateViewInterface;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;

//Response interface for authentication
public interface AuthenticationResponseInterface extends StateViewInterface {

    void generateAuthenticationProcessed(String Token,String msg);
    void onFailure(ErrorBody errorBody, int statusCode);



}
