package com.MDQ.myapplication.interfaces.viewresponceinterface;

import com.MDQ.myapplication.interfaces.StateViewInterface;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateAccountListResponseModel;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateDashBoardResponseModel;

//Response interface for Dashboard
public interface DashBoardResponseInterface extends StateViewInterface {

    void generateDashBoardProcessed(GenerateDashBoardResponseModel generateDashBoardResponseModel);
    void onFailure(ErrorBody errorBody, int statusCode);

}
