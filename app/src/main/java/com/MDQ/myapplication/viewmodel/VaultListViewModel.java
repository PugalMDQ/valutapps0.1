package com.MDQ.myapplication.viewmodel;

import android.content.Context;
import android.util.Log;

import com.MDQ.myapplication.datamanager.AccountListDataManager;
import com.MDQ.myapplication.datamanager.VaultListDataManger;
import com.MDQ.myapplication.interfaces.ResponseHandler;
import com.MDQ.myapplication.interfaces.viewinterface.VaultListRequestInterface;
import com.MDQ.myapplication.interfaces.viewresponceinterface.AccountListResponseInterface;
import com.MDQ.myapplication.interfaces.viewresponceinterface.VaultListResponceInterface;
import com.MDQ.myapplication.pojo.jsonrequest.GenerateAccountListRequestModel;
import com.MDQ.myapplication.pojo.jsonrequest.GenerateVaultListRequestModel;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateAccountListResponseModel;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateVaultListResponseModel;
import com.MDQ.myapplication.utils.ApiClass;

public class VaultListViewModel  extends VaultListBaseViewModel implements VaultListRequestInterface {
    private VaultListDataManger vaultListDataManger;
    private VaultListResponceInterface vaultListResponceInterface;
    private Context mContext;

    /**
     * @param mContext
     * @param vaultListResponceInterface
     * @breif Constructor to use VaultListModel,vaultListResponseInterface
     */
    public VaultListViewModel(Context mContext, VaultListResponceInterface vaultListResponceInterface) {
        this.vaultListResponceInterface = vaultListResponceInterface;
        this.vaultListDataManger = new VaultListDataManger(mContext);
        this.mContext = mContext;
    }

    /**
     * @breif calling VaultList api.if success response pass the data vaultMain
     */
    private void goGenerateVaultList() {
        GenerateVaultListRequestModel generateVaultListRequestModel=new GenerateVaultListRequestModel();
        generateVaultListRequestModel.token=getToken();
        vaultListDataManger.callEnqueue(ApiClass.VAULT_LIST, getToken(), new ResponseHandler<GenerateVaultListResponseModel>() {
            @Override
            public void onSuccess(String message) {}
            @Override
            public void onSuccess(GenerateVaultListResponseModel item, String message) {
                Log.i("otpR","rr");
                if(item.getMsg()!=null) {
                    Log.i("otpRecevied", item.getMsg());
                    //Toast.makeText(mContext, "" + item.getMsg(), Toast.LENGTH_SHORT).show();
                    vaultListResponceInterface.generateVaultListProcessed(item);
                }
            }
            @Override
            public void onFailure(ErrorBody errorBody, int statusCode) {
                Log.i("error" ,errorBody.ErrorMessage);
                vaultListResponceInterface.onFailure(errorBody,statusCode);
            }
        });
    }

    /**
     * @breif method for call vaultList
     */
    @Override
    public void generateVaultListRequest() {
        goGenerateVaultList();
    }
}
