package com.MDQ.myapplication.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.MDQ.myapplication.datamanager.OtpDataManager;
import com.MDQ.myapplication.datamanager.RegisterDataManager;
import com.MDQ.myapplication.interfaces.ResponseHandler;
import com.MDQ.myapplication.interfaces.viewinterface.RegisterRequestInterface;
import com.MDQ.myapplication.interfaces.viewresponceinterface.OtpResponseInterface;
import com.MDQ.myapplication.interfaces.viewresponceinterface.RegisterResponseInterface;
import com.MDQ.myapplication.pojo.jsonrequest.GenerateOtpRequestModel;
import com.MDQ.myapplication.pojo.jsonrequest.GenerateRegisterRequestModel;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateOtpResponseModel;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateRegisterResponseModel;
import com.MDQ.myapplication.utils.ApiClass;

public class RegisterRequestViewModel extends RegisterRequestBaseViewModel implements RegisterRequestInterface {


    private RegisterDataManager registerDataManager;
    private RegisterResponseInterface registerResponseInterface;
    private Context mContext;

    public RegisterRequestViewModel(Context mContext, RegisterResponseInterface registerResponseInterface) {
        this.registerResponseInterface = registerResponseInterface;
        this.registerDataManager = new RegisterDataManager(mContext);
        this.mContext = mContext;
    }

    private void goGenerateRegister() {
        GenerateRegisterRequestModel generateRegisterRequestModeler=new GenerateRegisterRequestModel();
        generateRegisterRequestModeler.email=getEmail();
        generateRegisterRequestModeler.user_name=getUser_name();
        generateRegisterRequestModeler.phone=getPhone();
        generateRegisterRequestModeler.country_code=getCountry_code();
        registerDataManager.callEnqueue(ApiClass.REGISTER_URL, generateRegisterRequestModeler, new ResponseHandler<GenerateRegisterResponseModel>() {
            @Override
            public void onSuccess(String message) {}
            @Override
            public void onSuccess(GenerateRegisterResponseModel item, String message) {
                Log.i("otpR","rr");
                if(item.getMsg()!=null) {
                    Log.i("otpRecevied", item.getMsg());
                    Toast.makeText(mContext, ""+item.getMsg(), Toast.LENGTH_SHORT).show();
                    registerResponseInterface.generateRegisterProcessed(item.getDataForRegister().token,item.getDataForRegister().getOtp());
                }

            }

            @Override
            public void onFailure(ErrorBody errorBody, int statusCode) {
                Log.i("error" ,errorBody.ErrorMessage);
                registerResponseInterface.onFailure(errorBody,statusCode);
            }
        });
    }

    @Override
    public void generateRegisterRequest() {
        goGenerateRegister();
    }
}


