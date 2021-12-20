package com.MDQ.myapplication.viewmodel;

import android.content.Context;
import android.util.Log;

import com.MDQ.myapplication.datamanager.BioMetricsDataManager;
import com.MDQ.myapplication.datamanager.CategorySpentDataManager;
import com.MDQ.myapplication.interfaces.ResponseHandler;
import com.MDQ.myapplication.interfaces.viewinterface.BioMetricsRequestInterface;
import com.MDQ.myapplication.interfaces.viewresponceinterface.BioMetricsResponseInterface;
import com.MDQ.myapplication.interfaces.viewresponceinterface.CategorySpentResponseInterface;
import com.MDQ.myapplication.pojo.jsonrequest.GenerateBioMetricsRequestModel;
import com.MDQ.myapplication.pojo.jsonrequest.GenerateCategorySpentRequestModel;
import com.MDQ.myapplication.pojo.jsonresponse.DataForCurrency;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateBioMetricsResponseModel;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateCategorySpentResponseModel;
import com.MDQ.myapplication.utils.ApiClass;

import java.util.List;

public class BioMetricsViewModel extends BioMetricsBaseViewModel implements BioMetricsRequestInterface {
    private BioMetricsDataManager bioMetricsDataManager;
    private BioMetricsResponseInterface bioMetricsResponseInterface;
    private Context mContext;

    public BioMetricsViewModel(Context mContext, BioMetricsResponseInterface bioMetricsResponseInterface) {
        this.bioMetricsResponseInterface = bioMetricsResponseInterface;
        this.bioMetricsDataManager = new BioMetricsDataManager(mContext);
        this.mContext = mContext;
    }

    private void goGenerateBioMetrics() {
        GenerateBioMetricsRequestModel generateBioMetricsRequestModel=new GenerateBioMetricsRequestModel();
        generateBioMetricsRequestModel.bio =getBio();
        bioMetricsDataManager.callEnqueue(ApiClass.BIO_METRICS, getToken(),generateBioMetricsRequestModel, new ResponseHandler<GenerateBioMetricsResponseModel>() {
            @Override
            public void onSuccess(String message) {}
            @Override
            public void onSuccess(GenerateBioMetricsResponseModel item, String message) {
                Log.i("otpR","rr");
                List<DataForCurrency> list = null;
                if(item.getMsg()!=null) {
                    Log.i("otpRecevied", item.getMsg());
                    bioMetricsResponseInterface.generateBioMetricsProcessed(item);
                }

            }

            @Override
            public void onFailure(ErrorBody errorBody, int statusCode) {
                Log.i("error" ,errorBody.ErrorMessage);
                bioMetricsResponseInterface.onFailure(errorBody,statusCode);
            }
        });
    }

    @Override
    public void generateBioMetricsRequest() {
        goGenerateBioMetrics();
    }

}
