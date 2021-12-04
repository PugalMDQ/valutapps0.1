package com.MDQ.myapplication.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.MDQ.myapplication.datamanager.AddAccountDataManager;
import com.MDQ.myapplication.datamanager.MpinDataManager;
import com.MDQ.myapplication.interfaces.ResponseHandler;
import com.MDQ.myapplication.interfaces.viewinterface.AddAccountRequestInterface;
import com.MDQ.myapplication.interfaces.viewresponceinterface.AddAccountResponseInterface;
import com.MDQ.myapplication.interfaces.viewresponceinterface.MpinResponseInterface;
import com.MDQ.myapplication.pojo.jsonrequest.GenerateAddAccountRequestModel;
import com.MDQ.myapplication.pojo.jsonrequest.GenerateMpinRequestModel;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateAddAccountResponseModel;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateMpinResponseModel;
import com.MDQ.myapplication.utils.ApiClass;

public class AddAccountViewModel extends AddAccountBaseViewModel implements AddAccountRequestInterface {


    private AddAccountDataManager addAccountDataManager;
    private AddAccountResponseInterface addAccountResponseInterface;
    private Context mContext;

    public AddAccountViewModel(Context mContext, AddAccountResponseInterface addAccountResponseInterface) {
        this.addAccountResponseInterface = addAccountResponseInterface;
        this.addAccountDataManager = new AddAccountDataManager(mContext);
        this.mContext = mContext;
    }

    private void goGenerateAddAccount() {
        GenerateAddAccountRequestModel generateAddAccountRequestModel=new GenerateAddAccountRequestModel();
        generateAddAccountRequestModel.account_name=getAccountName();
        generateAddAccountRequestModel.account_type=getAccountType();
        generateAddAccountRequestModel.bank_name=getBankName();
        generateAddAccountRequestModel.current_balance=getCurrentBalance();
        generateAddAccountRequestModel.currency=getCurrency();
        addAccountDataManager.callEnqueue(ApiClass.ADD_ACCOUNT, getToken(),generateAddAccountRequestModel, new ResponseHandler<GenerateAddAccountResponseModel>() {
            @Override
            public void onSuccess(String message) {

            }

            @Override
            public void onSuccess(GenerateAddAccountResponseModel item, String message) {
                Log.i("otpR","rr");
                if(item.getMsg()!=null) {
                    Log.i("otpRecevied", item.getMsg());
                   // Toast.makeText(mContext, ""+item.getMsg(), Toast.LENGTH_SHORT).show();
                    addAccountResponseInterface.generateAddAccountProcessed(item.getMsg());
                }

            }
            @Override
            public void onFailure(ErrorBody errorBody, int statusCode) {
                Log.i("error" ,errorBody.ErrorMessage);
                addAccountResponseInterface.onFailure(errorBody,statusCode);

            }
        });
    }

    @Override
    public void generateAddAccountRequest() {
        goGenerateAddAccount();
    }

}
