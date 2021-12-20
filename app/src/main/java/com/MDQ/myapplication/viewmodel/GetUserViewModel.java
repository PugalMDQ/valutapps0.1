package com.MDQ.myapplication.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.MDQ.myapplication.datamanager.GetUserDataManager;
import com.MDQ.myapplication.interfaces.ResponseHandler;
import com.MDQ.myapplication.interfaces.viewinterface.GetUserRequestInterface;
import com.MDQ.myapplication.interfaces.viewresponceinterface.GetUserResponseInterface;
import com.MDQ.myapplication.pojo.jsonrequest.GenerateGetUserRequestModel;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateGetUserResponseModel;
import com.MDQ.myapplication.utils.ApiClass;

public class GetUserViewModel extends GetUserBaseViewModel implements GetUserRequestInterface {
    private GetUserDataManager getUserDataManager;
    private GetUserResponseInterface getUserResponseInterface;
    private Context mContext;

    public GetUserViewModel(Context mContext, GetUserResponseInterface getUserResponseInterface) {
        this.getUserResponseInterface = getUserResponseInterface;
        this.getUserDataManager = new GetUserDataManager(mContext);
        this.mContext = mContext;
    }

    private void goGenerateGetUser() {
        GenerateGetUserRequestModel generateGetUserRequestModel = new GenerateGetUserRequestModel();
        generateGetUserRequestModel.token = getToken();
        getUserDataManager.callEnqueue(ApiClass.GET_USER_URL,getToken(), generateGetUserRequestModel, new ResponseHandler<GenerateGetUserResponseModel>() {
            @Override
            public void onSuccess(String message) {

            }

            @Override
            public void onSuccess(GenerateGetUserResponseModel item, String message) {
                Log.i("otpR", "rr");
                if (item.getMsg() != null) {
                    Log.i("otpRecevied", item.getMsg());
                    getUserResponseInterface.generateGetUserProcessed(item.getData().token,item.getData().user_name,item.getData().email,item.getData().phone,item.getData().getBiometrics_status());
                }

            }

            @Override
            public void onFailure(ErrorBody errorBody, int statusCode) {
                Log.i("error", errorBody.ErrorMessage);
                getUserResponseInterface.onFailure(errorBody, statusCode);

            }
        });
    }
    @Override
    public void generateGetUserRequest() {

        goGenerateGetUser();
    }


}
