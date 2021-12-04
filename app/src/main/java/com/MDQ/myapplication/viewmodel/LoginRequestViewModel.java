package com.MDQ.myapplication.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.MDQ.myapplication.datamanager.LoginDataManager;
import com.MDQ.myapplication.datamanager.MpinDataManager;
import com.MDQ.myapplication.interfaces.ResponseHandler;
import com.MDQ.myapplication.interfaces.viewinterface.LoginRequestInterface;
import com.MDQ.myapplication.interfaces.viewresponceinterface.LoginResponseInterface;
import com.MDQ.myapplication.interfaces.viewresponceinterface.MpinResponseInterface;
import com.MDQ.myapplication.pojo.jsonrequest.GenerateLoginRequestModel;
import com.MDQ.myapplication.pojo.jsonrequest.GenerateMpinRequestModel;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateLoginResponseModel;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateMpinResponseModel;
import com.MDQ.myapplication.utils.ApiClass;

public class LoginRequestViewModel extends LoginRequestBaseViewModel implements LoginRequestInterface {

    private LoginDataManager loginDataManager;
    private LoginResponseInterface loginResponseInterface;
    private Context mContext;

    public LoginRequestViewModel(Context mContext, LoginResponseInterface loginResponseInterface) {
        this.loginResponseInterface = loginResponseInterface;
        this.loginDataManager = new LoginDataManager(mContext);
        this.mContext = mContext;
    }

    private void goGenerateLogin() {
        GenerateLoginRequestModel generateLoginRequestModel=new GenerateLoginRequestModel();
        generateLoginRequestModel.phone=getPhone();
        generateLoginRequestModel.country_code=getCountry_code();
        loginDataManager.callEnqueue(ApiClass.LOGIN_URL, generateLoginRequestModel, new ResponseHandler<GenerateLoginResponseModel>() {
            @Override
            public void onSuccess(String message) {

            }

            @Override
            public void onSuccess(GenerateLoginResponseModel item, String message) {
                Log.i("otpR","rr");
                if(item.getMsg()!=null) {
                    Log.i("otpRecevied", item.getMsg());
                    Toast.makeText(mContext, ""+item.getMsg(), Toast.LENGTH_SHORT).show();
                    loginResponseInterface.generateLoginProcessed(item.getDataForRegister().getToken(),item.getDataForRegister().getOtp());
                }

            }
            @Override
            public void onFailure(ErrorBody errorBody, int statusCode) {
                Log.i("error" ,errorBody.ErrorMessage);
                loginResponseInterface.onFailure(errorBody,statusCode);

            }
        });
    }

    @Override
    public void generateLoginRequest() {
        goGenerateLogin();
    }
}
