package com.MDQ.myapplication.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.MDQ.myapplication.datamanager.RegisterDataManager;
import com.MDQ.myapplication.datamanager.UpdateProfileDataManager;
import com.MDQ.myapplication.interfaces.ResponseHandler;
import com.MDQ.myapplication.interfaces.viewinterface.UpdateProfileRequestInterface;
import com.MDQ.myapplication.interfaces.viewresponceinterface.RegisterResponseInterface;
import com.MDQ.myapplication.interfaces.viewresponceinterface.UpdateProfileResponseInterface;
import com.MDQ.myapplication.pojo.jsonrequest.GenerateRegisterRequestModel;
import com.MDQ.myapplication.pojo.jsonrequest.GenerateUpdateProfileRequestModel;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateRegisterResponseModel;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateUpdateProfileResponseModel;
import com.MDQ.myapplication.utils.ApiClass;

public class UpdateProfileViewModel extends UpdateProfileBaseViewModel implements UpdateProfileRequestInterface {
    private UpdateProfileDataManager updateProfileDataManager;
    private UpdateProfileResponseInterface updateProfileResponseInterface;
    private Context mContext;

    public UpdateProfileViewModel(Context mContext, UpdateProfileResponseInterface updateProfileResponseInterface) {
        this.updateProfileResponseInterface = updateProfileResponseInterface;
        this.updateProfileDataManager = new UpdateProfileDataManager(mContext);
        this.mContext = mContext;
    }

    private void goGenerateUpdateProfile() {
        GenerateUpdateProfileRequestModel generateUpdateProfileRequestModel=new GenerateUpdateProfileRequestModel();
        generateUpdateProfileRequestModel.Fullname=getFullname();
        generateUpdateProfileRequestModel.Phone=getPhone();
        generateUpdateProfileRequestModel.Countrycode=getCountrycode();
        generateUpdateProfileRequestModel.Dob=getDob();
        generateUpdateProfileRequestModel.Mpin=getMpin();
        updateProfileDataManager.callEnqueue(ApiClass.UPDATE_PROFILE,getAuthtoken(), generateUpdateProfileRequestModel, new ResponseHandler<GenerateUpdateProfileResponseModel>() {
            @Override
            public void onSuccess(String message) {}
            @Override
            public void onSuccess(GenerateUpdateProfileResponseModel item, String message) {
                Log.i("otpR","rr");
                if(item.getMsg()!=null) {
                    Log.i("otpRecevied", item.getMsg());
                    Toast.makeText(mContext, ""+item.getMsg(), Toast.LENGTH_SHORT).show();
                    updateProfileResponseInterface.generateUpdateProfileProcessed(item);
                }
            }

            @Override
            public void onFailure(ErrorBody errorBody, int statusCode) {
                Log.i("error" ,errorBody.ErrorMessage);
                updateProfileResponseInterface.onFailure(errorBody,statusCode);
            }
        });
    }

    @Override
    public void generateUpdateProfileRequest() {
        goGenerateUpdateProfile();
    }

}

