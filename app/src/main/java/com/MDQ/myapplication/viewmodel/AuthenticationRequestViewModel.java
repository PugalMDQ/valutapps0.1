package com.MDQ.myapplication.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.MDQ.myapplication.datamanager.AuthenticationDataManager;
import com.MDQ.myapplication.interfaces.ResponseHandler;
import com.MDQ.myapplication.interfaces.viewinterface.AuthenticationRequestInterface;
import com.MDQ.myapplication.interfaces.viewresponceinterface.AuthenticationResponseInterface;
import com.MDQ.myapplication.pojo.jsonrequest.GenerateAuthenticationRequestModel;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateAuthenticationResponseModel;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateGetUserResponseModel;
import com.MDQ.myapplication.utils.ApiClass;

public class AuthenticationRequestViewModel extends AuthenticationBaseViewModel implements AuthenticationRequestInterface {
    private AuthenticationDataManager authenticationDataManager;
    private AuthenticationResponseInterface authenticationResponseInterface;
    private Context mContext;

    public AuthenticationRequestViewModel(Context mContext, AuthenticationResponseInterface authenticationResponseInterface) {
        this.authenticationResponseInterface = authenticationResponseInterface;
        this.authenticationDataManager = new AuthenticationDataManager(mContext);
        this.mContext = mContext;
    }

    private void goGenerateAuthentication() {
        GenerateAuthenticationRequestModel generateAuthenticationRequestModel = new GenerateAuthenticationRequestModel();
        generateAuthenticationRequestModel.username = getUsername();
        generateAuthenticationRequestModel.password=getPassword();
        authenticationDataManager.callEnqueue(ApiClass.AUTHENTICATION_URL, generateAuthenticationRequestModel, new ResponseHandler<GenerateAuthenticationResponseModel>() {
            @Override
            public void onSuccess(String message) {

            }

            @Override
            public void onSuccess(GenerateAuthenticationResponseModel item, String message) {
                Log.i("otpR", "rr");
                if (item.getMsg() != null) {
                    Log.i("otpRecevied", item.getMsg());
                 //   Toast.makeText(mContext, "" + item.getMsg(), Toast.LENGTH_SHORT).show();
                    authenticationResponseInterface.generateAuthenticationProcessed(item.getData().token,item.getMsg());
                }

            }

            @Override
            public void onFailure(ErrorBody errorBody, int statusCode) {
                Log.i("error", errorBody.ErrorMessage);
                authenticationResponseInterface.onFailure(errorBody, statusCode);

            }
        });
    }
    @Override
    public void generateAuthenticationRequest() {

        goGenerateAuthentication();
    }


}

