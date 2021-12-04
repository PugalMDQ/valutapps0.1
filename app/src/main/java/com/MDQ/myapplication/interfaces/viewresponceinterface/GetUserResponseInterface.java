package com.MDQ.myapplication.interfaces.viewresponceinterface;

import com.MDQ.myapplication.interfaces.StateViewInterface;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;

public interface GetUserResponseInterface  extends StateViewInterface {

    void generateGetUserProcessed(String Token,String User_name,String Email,String Phone,String biometrics_status);
    void onFailure(ErrorBody errorBody, int statusCode);


}
