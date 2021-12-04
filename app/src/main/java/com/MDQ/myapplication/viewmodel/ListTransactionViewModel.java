package com.MDQ.myapplication.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.MDQ.myapplication.datamanager.ListTransactionDataManager;
import com.MDQ.myapplication.datamanager.LoginDataManager;
import com.MDQ.myapplication.interfaces.ResponseHandler;
import com.MDQ.myapplication.interfaces.viewinterface.ListTransactionRequestInterface;
import com.MDQ.myapplication.interfaces.viewinterface.LoginRequestInterface;
import com.MDQ.myapplication.interfaces.viewresponceinterface.ListTransactionResponseInterface;
import com.MDQ.myapplication.interfaces.viewresponceinterface.LoginResponseInterface;
import com.MDQ.myapplication.pojo.jsonrequest.GenerateListTransactionRequestModel;
import com.MDQ.myapplication.pojo.jsonrequest.GenerateLoginRequestModel;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateListTransactionResponseModel;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateLoginResponseModel;
import com.MDQ.myapplication.utils.ApiClass;

public class ListTransactionViewModel extends ListTransactionBaseViewModel implements ListTransactionRequestInterface {


    private ListTransactionDataManager listTransactionDataManager;
    private ListTransactionResponseInterface listTransactionResponseInterface;
    private Context mContext;

    public ListTransactionViewModel(Context mContext, ListTransactionResponseInterface listTransactionResponseInterface) {
        this.listTransactionResponseInterface = listTransactionResponseInterface;
        this.listTransactionDataManager = new ListTransactionDataManager(mContext);
        this.mContext = mContext;
    }

    private void goGenerateListTransaction() {
        GenerateListTransactionRequestModel generateListTransactionRequestModel=new GenerateListTransactionRequestModel();
        generateListTransactionRequestModel.account_id=getAccount_id();
        generateListTransactionRequestModel.index=getIndex();
        listTransactionDataManager.callEnqueue(ApiClass.LOGIN_URL, getToken(),generateListTransactionRequestModel, new ResponseHandler<GenerateListTransactionResponseModel>() {
            @Override
            public void onSuccess(String message) {

            }

            @Override
            public void onSuccess(GenerateListTransactionResponseModel item, String message) {
                Log.i("otpR","rr");
                if(item.getMsg()!=null) {
                    Log.i("otpRecevied", item.getMsg());
                    Toast.makeText(mContext, ""+item.getMsg(), Toast.LENGTH_SHORT).show();
                    listTransactionResponseInterface.generateListTransactionProcessed(item);
                }

            }
            @Override
            public void onFailure(ErrorBody errorBody, int statusCode) {
                Log.i("error" ,errorBody.ErrorMessage);
                listTransactionResponseInterface.onFailure(errorBody,statusCode);

            }
        });
    }
    @Override
    public void generateListTransactionRequest() {
        goGenerateListTransaction();
    }
}

