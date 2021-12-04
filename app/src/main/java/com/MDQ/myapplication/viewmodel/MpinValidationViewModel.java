package com.MDQ.myapplication.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.MDQ.myapplication.datamanager.MpinDataManager;
import com.MDQ.myapplication.datamanager.MpinValidationDataManager;
import com.MDQ.myapplication.interfaces.ResponseHandler;
import com.MDQ.myapplication.interfaces.viewinterface.MpinValidationRequestInterface;
import com.MDQ.myapplication.interfaces.viewresponceinterface.MpinResponseInterface;
import com.MDQ.myapplication.interfaces.viewresponceinterface.MpinValidationResponseInterfacce;
import com.MDQ.myapplication.pojo.jsonrequest.GenerateMpinRequestModel;
import com.MDQ.myapplication.pojo.jsonrequest.GenerateMpinValidationRequestModel;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateMpinResponseModel;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateMpinValidationResponseModel;
import com.MDQ.myapplication.utils.ApiClass;

public class MpinValidationViewModel extends MpinRequestBaseViewModel implements MpinValidationRequestInterface {


    private MpinValidationDataManager mpinValidationDataManager;
    private MpinValidationResponseInterfacce mpinValidationResponseInterfacce;
    private Context mContext;

    public MpinValidationViewModel(Context mContext, MpinValidationResponseInterfacce mpinValidationResponseInterfacce) {
        this.mpinValidationResponseInterfacce = mpinValidationResponseInterfacce;
        this.mpinValidationDataManager = new MpinValidationDataManager(mContext);
        this.mContext = mContext;
    }

    private void goGenerateMpinValidation() {
        GenerateMpinValidationRequestModel generateMpinRequestModel=new GenerateMpinValidationRequestModel();
        generateMpinRequestModel.mpin=getMpin();
        mpinValidationDataManager.callEnqueue(ApiClass.MPIN_VALIDATION_URL, getToken(),generateMpinRequestModel, new ResponseHandler<GenerateMpinValidationResponseModel>() {
            @Override
            public void onSuccess(String message) {
            }

            @Override
            public void onSuccess(GenerateMpinValidationResponseModel item, String message) {
                Log.i("otpR","rr");
                if(item.getMsg()!=null) {
                    Log.i("otpRecevied", item.getMsg());
                    //Toast.makeText(mContext, ""+item.getMsg(), Toast.LENGTH_SHORT).show();
                    mpinValidationResponseInterfacce.generateMpinValidationProcessed(item.getData().getToken(),item.getMsg());
                }

            }
            @Override
            public void onFailure(ErrorBody errorBody, int statusCode) {
                Log.i("error" ,errorBody.ErrorMessage);
                mpinValidationResponseInterfacce.onFailure(errorBody,statusCode);

            }
        });
    }
    @Override
    public void generateMpinValidationRequest() {
        goGenerateMpinValidation();
    }
}
