package com.MDQ.myapplication.interfaces.viewresponceinterface;

import com.MDQ.myapplication.interfaces.StateViewInterface;
import com.MDQ.myapplication.pojo.jsonresponse.DataForCurrency;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateCurrencyResponseModel;

import java.util.List;
//Response interface for Currency List
public interface CurrencyResponseInterface extends StateViewInterface {

    void generateCurrencyProcessed(GenerateCurrencyResponseModel generateCurrencyResponseModel);
    void onFailure(ErrorBody errorBody, int statusCode);
}
