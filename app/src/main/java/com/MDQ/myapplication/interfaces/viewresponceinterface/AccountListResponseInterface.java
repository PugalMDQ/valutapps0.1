package com.MDQ.myapplication.interfaces.viewresponceinterface;

import com.MDQ.myapplication.interfaces.StateViewInterface;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateAccountListResponseModel;

//response interface for Account List
public interface AccountListResponseInterface extends StateViewInterface {

    void generateAccountListProcessed(GenerateAccountListResponseModel generateAccountListResponseModel);

    void onFailure(ErrorBody errorBody, int statusCode);

}
