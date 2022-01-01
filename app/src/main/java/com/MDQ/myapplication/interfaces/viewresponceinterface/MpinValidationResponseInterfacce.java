package com.MDQ.myapplication.interfaces.viewresponceinterface;

import com.MDQ.myapplication.interfaces.StateViewInterface;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;

//Response interface for Mpin Validation
 public interface MpinValidationResponseInterfacce extends StateViewInterface {
    void generateMpinValidationProcessed(String Token,String msg);
    void onFailure(ErrorBody errorBody, int statusCode);


}
