package com.MDQ.myapplication.viewmodel;

import android.content.Context;
import android.util.Log;

import com.MDQ.myapplication.datamanager.BioMetricsDataManager;
import com.MDQ.myapplication.datamanager.BioMetricsValidationDataManager;
import com.MDQ.myapplication.interfaces.ResponseHandler;
import com.MDQ.myapplication.interfaces.viewinterface.BioMetricsValidationRequestInterface;
import com.MDQ.myapplication.interfaces.viewresponceinterface.BioMetricsResponseInterface;
import com.MDQ.myapplication.interfaces.viewresponceinterface.BioMetricsValidationResponseInterface;
import com.MDQ.myapplication.pojo.jsonrequest.GenerateBioMetricsRequestModel;
import com.MDQ.myapplication.pojo.jsonresponse.DataForCurrency;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateBioMetricsResponseModel;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateBioMetricsValidationResponseModel;
import com.MDQ.myapplication.utils.ApiClass;

import java.util.List;

public class BioMetricsValidationViewModel extends BioMetricsValidationBaseViewModel implements BioMetricsValidationRequestInterface {
    private BioMetricsValidationDataManager bioMetricsValidationDataManager;
    private BioMetricsValidationResponseInterface bioMetricsValidationResponseInterface;
    private Context mContext;

    public BioMetricsValidationViewModel(Context mContext, BioMetricsValidationResponseInterface bioMetricsValidationResponseInterface) {
        this.bioMetricsValidationResponseInterface = bioMetricsValidationResponseInterface;
        this.bioMetricsValidationDataManager = new BioMetricsValidationDataManager(mContext);
        this.mContext = mContext;
    }

    private void goGenerateBioMetricsValidation() {
        bioMetricsValidationDataManager.callEnqueue(ApiClass.BIO_METRICS_VALIDATION, getToken(), new ResponseHandler<GenerateBioMetricsValidationResponseModel>() {
            @Override
            public void onSuccess(String message) {}
            @Override
            public void onSuccess(GenerateBioMetricsValidationResponseModel item, String message) {
                Log.i("otpR","rr");
                List<DataForCurrency> list = null;
                if(item.getMsg()!=null) {
                    Log.i("otpRecevied", item.getMsg());
                    // Toast.makeText(mContext, ""+item.getMsg(), Toast.LENGTH_SHORT).show();
                    bioMetricsValidationResponseInterface.generateBioMetricsValidationProcessed(item);
                }

            }

            @Override
            public void onFailure(ErrorBody errorBody, int statusCode) {
                Log.i("error" ,errorBody.ErrorMessage);
                bioMetricsValidationResponseInterface.onFailure(errorBody,statusCode);
            }
        });
    }
    @Override
    public void generateBioMetricsValidationRequest() {
        goGenerateBioMetricsValidation();
    }
}

