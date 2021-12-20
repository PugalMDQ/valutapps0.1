package com.MDQ.myapplication.interfaces.viewresponceinterface;

import com.MDQ.myapplication.interfaces.StateViewInterface;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateBioMetricsResponseModel;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateDashBoardResponseModel;

public interface BioMetricsResponseInterface extends StateViewInterface {
    void generateBioMetricsProcessed(GenerateBioMetricsResponseModel generateBioMetricsResponseModel);
    void onFailure(ErrorBody errorBody, int statusCode);

}
