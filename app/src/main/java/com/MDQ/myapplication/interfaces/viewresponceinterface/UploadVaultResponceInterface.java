package com.MDQ.myapplication.interfaces.viewresponceinterface;

import com.MDQ.myapplication.interfaces.StateViewInterface;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateUploadVaultResponseModel;

public interface UploadVaultResponceInterface extends StateViewInterface {

    void generateUploadVaultProcessed(GenerateUploadVaultResponseModel generateUploadVaultResponseInterface);
    void onFailure(ErrorBody errorBody, int statusCode);
}
