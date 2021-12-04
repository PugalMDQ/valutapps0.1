package com.MDQ.myapplication.interfaces.viewresponceinterface;

import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;

public interface MpinValidationResponseInterfacce {

    void generateMpinValidationProcessed(String Token,String msg);
    void onFailure(ErrorBody errorBody, int statusCode);


}
