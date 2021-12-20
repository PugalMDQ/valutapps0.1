package com.MDQ.myapplication.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.MDQ.myapplication.datamanager.UploadVaultDataManager;
import com.MDQ.myapplication.interfaces.ResponseHandler;
import com.MDQ.myapplication.interfaces.viewinterface.UploadVaultRequestInterface;
import com.MDQ.myapplication.interfaces.viewresponceinterface.UploadVaultResponceInterface;
import com.MDQ.myapplication.pojo.jsonrequest.GenerateUploadValutRequestModel;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateUploadVaultResponseModel;
import com.MDQ.myapplication.utils.ApiClass;

public class UploadVaultViewModel extends UploadVaultBaseviewModel implements UploadVaultRequestInterface {


    private UploadVaultDataManager uploadVaultDataManager;
    private UploadVaultResponceInterface uploadVaultResponceInterface;
    private Context mContext;

    public UploadVaultViewModel(Context mContext, UploadVaultResponceInterface uploadVaultResponceInterface) {
        this.uploadVaultResponceInterface = uploadVaultResponceInterface;
        this.uploadVaultDataManager = new UploadVaultDataManager(mContext);
        this.mContext = mContext;

    }

    private void goGenerateOtp() {
        GenerateUploadValutRequestModel generateUploadValutRequestModel=new GenerateUploadValutRequestModel();
        uploadVaultDataManager.callEnqueue(ApiClass.UPLOAD_VAULT,getToken(),getFilename(),getProof(),getEncoded(), new ResponseHandler<GenerateUploadVaultResponseModel>() {
            @Override
            public void onSuccess(String message) {

            }

            @Override
            public void onSuccess(GenerateUploadVaultResponseModel item, String message) {
                if(item.getMsg()!=null) {
                    Log.i("otpRecevied", item.getMsg());
                    Toast.makeText(mContext, ""+item.getMsg(), Toast.LENGTH_SHORT).show();
                    uploadVaultResponceInterface.generateUploadVaultProcessed(item);
                }
            }
            @Override
            public void onFailure(ErrorBody errorBody, int statusCode) {
                Log.i("error" ,errorBody.ErrorMessage);
                uploadVaultResponceInterface.onFailure(errorBody,statusCode);
            }
        });
    }
    @Override
    public void generateUploadVaultRequest() {
        goGenerateOtp();
    }
}



