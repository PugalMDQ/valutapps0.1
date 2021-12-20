package com.MDQ.myapplication.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.MDQ.myapplication.datamanager.RegisterSuccessDataManager;
import com.MDQ.myapplication.interfaces.ResponseHandler;
import com.MDQ.myapplication.interfaces.viewinterface.RegisterSuccessRequestInterface;
import com.MDQ.myapplication.interfaces.viewresponceinterface.RegisterSuccessResponseInterface;
import com.MDQ.myapplication.pojo.jsonrequest.GenerateRegisterRequestModel;
import com.MDQ.myapplication.pojo.jsonrequest.GenerateRegisterSuccessRequestModel;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateRegisterResponseModel;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateRegisterSuccessResponseModel;
import com.MDQ.myapplication.utils.ApiClass;


public class RegisterSuccessRequestViewModel extends RegisterSuccessRequestBaseViewModel implements RegisterSuccessRequestInterface {

    private RegisterSuccessDataManager registerSuccessDataManager;
    private RegisterSuccessResponseInterface registerSuccessResponseInterface;
    private Context mContext;

    public RegisterSuccessRequestViewModel(Context mContext, RegisterSuccessResponseInterface registerSuccessResponseInterface) {
        this.registerSuccessResponseInterface = registerSuccessResponseInterface;
        this.registerSuccessDataManager = new RegisterSuccessDataManager(mContext);
        this.mContext = mContext;
    }

    private void goGenerateRegisterSuccess() {
        GenerateRegisterSuccessRequestModel generateRegisterSuccessRequestModel=new GenerateRegisterSuccessRequestModel();
        generateRegisterSuccessRequestModel.token=getToken();
        registerSuccessDataManager.callEnqueue(ApiClass.REGISTER_SUCCESS_URL, getToken(), new ResponseHandler<GenerateRegisterSuccessResponseModel>() {
            @Override
            public void onSuccess(String message) {}
            @Override
            public void onSuccess(GenerateRegisterSuccessResponseModel item, String message) {
                Log.i("otpR","rr");
                if(item.getMsg()!=null) {
                    Log.i("otpRecevied", item.getMsg());
                    Toast.makeText(mContext, ""+item.getMsg(), Toast.LENGTH_SHORT).show();
                    registerSuccessResponseInterface.generateRegisterSuccessProcessed(item.getData().getToken(),item.getMsg());
                }

            }

            @Override
            public void onFailure(ErrorBody errorBody, int statusCode) {
                Log.i("error" ,errorBody.ErrorMessage);
                registerSuccessResponseInterface.onFailure(errorBody,statusCode);
            }
        });
    }
    @Override
    public void generateRegisterSuccessRequest() {
        goGenerateRegisterSuccess();
    }
}




