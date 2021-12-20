package com.MDQ.myapplication.interfaces.viewresponceinterface;

import com.MDQ.myapplication.interfaces.StateViewInterface;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateBioMetricsResponseModel;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateBioMetricsValidationResponseModel;

public interface BioMetricsValidationResponseInterface extends StateViewInterface {
    void generateBioMetricsValidationProcessed(GenerateBioMetricsValidationResponseModel generateBioMetricsValidationsResponseModel);
    void onFailure(ErrorBody errorBody, int statusCode);

}
