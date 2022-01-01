package com.MDQ.myapplication.interfaces.viewresponceinterface;

import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateAddSpendResponceModel;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateBankListResponseModel;

//Response interface for add spend
public interface AddSpendResponceInterface {

    void generateAddSpendProcessed(GenerateAddSpendResponceModel generateAddSpendResponceModel);
    void onFailure(ErrorBody errorBody, int statusCode);

}
