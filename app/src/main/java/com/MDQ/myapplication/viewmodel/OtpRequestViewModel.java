package com.MDQ.myapplication.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.MDQ.myapplication.datamanager.OtpDataManager;
import com.MDQ.myapplication.interfaces.ResponseHandler;
import com.MDQ.myapplication.interfaces.viewinterface.OtpRequestInterface;
import com.MDQ.myapplication.interfaces.viewresponceinterface.OtpResponseInterface;
import com.MDQ.myapplication.pojo.jsonrequest.GenerateOtpRequestModel;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateOtpResponseModel;
import com.MDQ.myapplication.utils.ApiClass;

public class OtpRequestViewModel extends OtpRequestBaseViewModel implements OtpRequestInterface {

    private OtpDataManager otpDataManager;
    private OtpResponseInterface otpResponseInterface;
    private Context mContext;

    public OtpRequestViewModel(Context mContext, OtpResponseInterface otpResponseInterface) {
        this.otpResponseInterface = otpResponseInterface;
        this.otpDataManager = new OtpDataManager(mContext);
        this.mContext = mContext;

    }
    @Override
    public void generateOtpRequest() {
        goGenerateOtp();
    }

    private void goGenerateOtp() {
        GenerateOtpRequestModel generateOtpRequestModel=new GenerateOtpRequestModel();
        generateOtpRequestModel.phone=getPhone();
        generateOtpRequestModel.country_code=getCountry_code();
        otpDataManager.callEnqueue(ApiClass.RESEND_URL, generateOtpRequestModel, new ResponseHandler<GenerateOtpResponseModel>() {
            @Override
            public void onSuccess(String message) {

            }

            @Override
            public void onSuccess(GenerateOtpResponseModel item, String message) {
                if(item.getMsg()!=null) {
                    Log.i("otpRecevied", item.getMsg());
                    Toast.makeText(mContext, ""+item.getMsg(), Toast.LENGTH_SHORT).show();
                    otpResponseInterface.generateOtpProcessed(item.data.getOtp());
                }

            }


            @Override
            public void onFailure(ErrorBody errorBody, int statusCode) {
                Log.i("error" ,errorBody.ErrorMessage);
                otpResponseInterface.onFailure(errorBody,statusCode);
            }
        });
    }
}

