package com.MDQ.myapplication.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.MDQ.myapplication.datamanager.CurrencyDataManager;
import com.MDQ.myapplication.datamanager.DashBoardDataManager;
import com.MDQ.myapplication.interfaces.ResponseHandler;
import com.MDQ.myapplication.interfaces.viewinterface.DashBoardRequestInterface;
import com.MDQ.myapplication.interfaces.viewresponceinterface.CurrencyResponseInterface;
import com.MDQ.myapplication.interfaces.viewresponceinterface.DashBoardResponseInterface;
import com.MDQ.myapplication.pojo.jsonrequest.GenerateCurrencyRequestModel;
import com.MDQ.myapplication.pojo.jsonrequest.GenerateDashBoardRequestModel;
import com.MDQ.myapplication.pojo.jsonresponse.DataForCurrency;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateCurrencyResponseModel;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateDashBoardResponseModel;
import com.MDQ.myapplication.utils.ApiClass;

import java.util.List;

public class DashBoardViewModel extends DashBoardBaseViewModel implements DashBoardRequestInterface {
    private DashBoardDataManager dashBoardDataManager;
    private DashBoardResponseInterface dashBoardResponseInterface;
    private Context mContext;

    public DashBoardViewModel(Context mContext, DashBoardResponseInterface dashBoardResponseInterface) {
        this.dashBoardResponseInterface = dashBoardResponseInterface;
        this.dashBoardDataManager = new DashBoardDataManager(mContext);
        this.mContext = mContext;
    }

    private void goGenerateDashBoard() {
        GenerateDashBoardRequestModel generateDashBoardRequestModel=new GenerateDashBoardRequestModel();
        generateDashBoardRequestModel.token=getToken();
        generateDashBoardRequestModel.year=getYear();
        generateDashBoardRequestModel.month=getMonth();
        dashBoardDataManager.callEnqueue(ApiClass.DASH_BOARD, getToken(),generateDashBoardRequestModel, new ResponseHandler<GenerateDashBoardResponseModel>() {
            @Override
            public void onSuccess(String message) {}
            @Override
            public void onSuccess(GenerateDashBoardResponseModel item, String message) {
                Log.i("otpR","rr");
                List<DataForCurrency> list = null;
                if(item.getMsg()!=null) {
                    dashBoardResponseInterface.generateDashBoardProcessed(item);
                }

            }

            @Override
            public void onFailure(ErrorBody errorBody, int statusCode) {
                if(errorBody!=null) {
                    Log.i("error", errorBody.ErrorMessage);
                    dashBoardResponseInterface.onFailure(errorBody, statusCode);
                }
            }
        });
    }

    @Override
    public void generateDashBoardRequest() {
        goGenerateDashBoard();
    }

}
