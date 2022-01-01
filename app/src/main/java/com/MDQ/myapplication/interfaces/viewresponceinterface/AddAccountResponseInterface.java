package com.MDQ.myapplication.interfaces.viewresponceinterface;

import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;

//Response interface for add Account
public interface AddAccountResponseInterface {

    void generateAddAccountProcessed(String Msg );
    void onFailure(ErrorBody errorBody, int statusCode);

}
