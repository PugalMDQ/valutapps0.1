package com.MDQ.myapplication.interfaces.viewresponceinterface;

import com.MDQ.myapplication.interfaces.StateViewInterface;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateAccountListResponseModel;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateVaultListResponseModel;

public interface VaultListResponceInterface extends StateViewInterface {


    void generateVaultListProcessed(GenerateVaultListResponseModel generateVaultListResponseModel);
    void onFailure(ErrorBody errorBody, int statusCode);

}
