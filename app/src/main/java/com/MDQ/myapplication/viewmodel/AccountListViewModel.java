package com.MDQ.myapplication.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.MDQ.myapplication.datamanager.AccountListDataManager;
import com.MDQ.myapplication.datamanager.AccountTypeDataManager;
import com.MDQ.myapplication.interfaces.ResponseHandler;
import com.MDQ.myapplication.interfaces.viewinterface.AccountListRequestInterface;
import com.MDQ.myapplication.interfaces.viewresponceinterface.AccountListResponseInterface;
import com.MDQ.myapplication.interfaces.viewresponceinterface.AccountTypeResponseInterface;
import com.MDQ.myapplication.pojo.jsonrequest.GenerateAccountListRequestModel;
import com.MDQ.myapplication.pojo.jsonrequest.GenerateAccountTypeRequestModel;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateAccountListResponseModel;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateAccountTypeResponseModel;
import com.MDQ.myapplication.utils.ApiClass;

public class AccountListViewModel extends AccountListBaseViewModel implements AccountListRequestInterface {

    private AccountListDataManager accountListDataManager;
    private AccountListResponseInterface accountListResponseInterface;
    private Context mContext;

    public AccountListViewModel(Context mContext, AccountListResponseInterface accountListResponseInterface) {
        this.accountListResponseInterface = accountListResponseInterface;
        this.accountListDataManager = new AccountListDataManager(mContext);
        this.mContext = mContext;
    }

    private void goGenerateAccountList() {
        GenerateAccountListRequestModel generateAccountListRequestModel=new GenerateAccountListRequestModel();
        generateAccountListRequestModel.token=getToken();
        accountListDataManager.callEnqueue(ApiClass.ACCOUNT_LIST, getToken(), new ResponseHandler<GenerateAccountListResponseModel>() {
            @Override
            public void onSuccess(String message) {}
            @Override
            public void onSuccess(GenerateAccountListResponseModel item, String message) {
                Log.i("otpR","rr");
                if(item.getMsg()!=null) {
                    Log.i("otpRecevied", item.getMsg());
                    //Toast.makeText(mContext, "" + item.getMsg(), Toast.LENGTH_SHORT).show();
                    accountListResponseInterface.generateAccountListProcessed(item);
                }

            }

            @Override
            public void onFailure(ErrorBody errorBody, int statusCode) {
                Log.i("error" ,errorBody.ErrorMessage);
                accountListResponseInterface.onFailure(errorBody,statusCode);
            }
        });
    }
    @Override
    public void generateAccountListRequest() {
        goGenerateAccountList();

    }
}


