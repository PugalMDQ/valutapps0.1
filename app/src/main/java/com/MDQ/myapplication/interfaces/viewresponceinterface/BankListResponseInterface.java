package com.MDQ.myapplication.interfaces.viewresponceinterface;

import com.MDQ.myapplication.interfaces.StateViewInterface;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateBankListResponseModel;

import java.util.List;

public interface BankListResponseInterface extends StateViewInterface {

    void generateBankListProcessed(GenerateBankListResponseModel generateBankListResponseModel);
    void onFailure(ErrorBody errorBody, int statusCode);
}
