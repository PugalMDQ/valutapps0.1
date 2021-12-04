package com.MDQ.myapplication.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.MDQ.myapplication.datamanager.AccountTypeDataManager;
import com.MDQ.myapplication.datamanager.BankListDataManager;
import com.MDQ.myapplication.interfaces.ResponseHandler;
import com.MDQ.myapplication.interfaces.viewinterface.AccountTypeRequestInterface;
import com.MDQ.myapplication.interfaces.viewresponceinterface.AccountTypeResponseInterface;
import com.MDQ.myapplication.interfaces.viewresponceinterface.BankListResponseInterface;
import com.MDQ.myapplication.pojo.jsonrequest.GenerateAccountTypeRequestModel;
import com.MDQ.myapplication.pojo.jsonrequest.GenerateBankListRequestModel;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateAccountTypeResponseModel;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateBankListResponseModel;
import com.MDQ.myapplication.utils.ApiClass;

public class AccountTypeViewModel extends AccountTypeBaseViewModel implements AccountTypeRequestInterface {


    private AccountTypeDataManager accountTypeDataManager;
    private AccountTypeResponseInterface accountTypeResponseInterface;
    private Context mContext;

    public AccountTypeViewModel(Context mContext, AccountTypeResponseInterface accountTypeResponseInterface) {
        this.accountTypeResponseInterface = accountTypeResponseInterface;
        this.accountTypeDataManager = new AccountTypeDataManager(mContext);
        this.mContext = mContext;
    }

    private void goGenerateBankList() {
        GenerateAccountTypeRequestModel generateAccountTypeRequestModel=new GenerateAccountTypeRequestModel();
        generateAccountTypeRequestModel.token=getToken();
        accountTypeDataManager.callEnqueue(ApiClass.ACCOUNT_TYPE, getToken(), new ResponseHandler<GenerateAccountTypeResponseModel>() {
            @Override
            public void onSuccess(String message) {}
            @Override
            public void onSuccess(GenerateAccountTypeResponseModel item, String message) {
                Log.i("otpR","rr");
                if(item.getMsg()!=null) {
                    Log.i("otpRecevied", item.getMsg());
                    //Toast.makeText(mContext, "" + item.getMsg(), Toast.LENGTH_SHORT).show();
                    accountTypeResponseInterface.generateAccountTypeProcessed(item);
                }

            }

            @Override
            public void onFailure(ErrorBody errorBody, int statusCode) {
                Log.i("error" ,errorBody.ErrorMessage);
                accountTypeResponseInterface.onFailure(errorBody,statusCode);
            }
        });
    }
    @Override
    public void generateAccountTypeRequest() {
        goGenerateBankList();

    }
}
