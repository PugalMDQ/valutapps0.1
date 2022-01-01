package com.MDQ.myapplication.interfaces.viewresponceinterface;

import com.MDQ.myapplication.interfaces.StateViewInterface;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateAccountTypeResponseModel;

//Response interface for Account type
public interface AccountTypeResponseInterface  extends StateViewInterface {

    void generateAccountTypeProcessed(GenerateAccountTypeResponseModel generateAccountTypeResponseModel);
    void onFailure(ErrorBody errorBody, int statusCode);
}
