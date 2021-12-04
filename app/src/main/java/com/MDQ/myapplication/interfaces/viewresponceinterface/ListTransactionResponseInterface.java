package com.MDQ.myapplication.interfaces.viewresponceinterface;

import com.MDQ.myapplication.interfaces.StateViewInterface;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateBankListResponseModel;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateListTransactionResponseModel;

public interface ListTransactionResponseInterface extends StateViewInterface {


    void generateListTransactionProcessed(GenerateListTransactionResponseModel generateListTransactionResponseModel);
    void onFailure(ErrorBody errorBody, int statusCode);

}
