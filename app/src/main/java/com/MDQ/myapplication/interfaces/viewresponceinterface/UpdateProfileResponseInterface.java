package com.MDQ.myapplication.interfaces.viewresponceinterface;

import com.MDQ.myapplication.interfaces.StateViewInterface;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateUpdateProfileResponseModel;
//Response interface for update Profile
public interface UpdateProfileResponseInterface extends StateViewInterface {

    void generateUpdateProfileProcessed(GenerateUpdateProfileResponseModel generateUpdateProfileResponseModel);
    void onFailure(ErrorBody errorBody, int statusCode);

}
