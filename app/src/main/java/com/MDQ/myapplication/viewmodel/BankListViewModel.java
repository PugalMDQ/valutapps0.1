package com.MDQ.myapplication.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.MDQ.myapplication.datamanager.BankListDataManager;
import com.MDQ.myapplication.datamanager.MpinValidationDataManager;
import com.MDQ.myapplication.interfaces.ResponseHandler;
import com.MDQ.myapplication.interfaces.viewinterface.BankListRequestInterface;
import com.MDQ.myapplication.interfaces.viewresponceinterface.BankListResponseInterface;
import com.MDQ.myapplication.interfaces.viewresponceinterface.MpinValidationResponseInterfacce;
import com.MDQ.myapplication.pojo.jsonrequest.GenerateBankListRequestModel;
import com.MDQ.myapplication.pojo.jsonrequest.GenerateMpinValidationRequestModel;
import com.MDQ.myapplication.pojo.jsonrequest.GenerateRegisterSuccessRequestModel;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateBankListResponseModel;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateMpinValidationResponseModel;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateRegisterSuccessResponseModel;
import com.MDQ.myapplication.utils.ApiClass;

public class BankListViewModel extends BankListBaseViewModel implements BankListRequestInterface {

    private BankListDataManager bankListDataManager;
    private BankListResponseInterface bankListResponseInterface;
    private Context mContext;

    public BankListViewModel(Context mContext, BankListResponseInterface bankListResponseInterface) {
        this.bankListResponseInterface = bankListResponseInterface;
        this.bankListDataManager = new BankListDataManager(mContext);
        this.mContext = mContext;
    }
    private void goGenerateBankList() {
        GenerateBankListRequestModel generateBankListRequestModel = new GenerateBankListRequestModel();
        generateBankListRequestModel.token = getToken();
        if (getToken() != null) {
            bankListDataManager.callEnqueue(ApiClass.BANK_LIST_URL, getToken(), new ResponseHandler<GenerateBankListResponseModel>() {
                @Override
                public void onSuccess(String message) {
                }

                @Override
                public void onSuccess(GenerateBankListResponseModel item, String message) {
                    Log.i("otpR", "rr");
                    if (item.getMsg() != null) {
                        Log.i("otpRecevied", item.getMsg());
                        //   Toast.makeText(mContext, ""+item.getMsg(), Toast.LENGTH_SHORT).show();
                        if (item != null) {
                            bankListResponseInterface.generateBankListProcessed(item);
                        }
                    }
                }

                @Override
                public void onFailure(ErrorBody errorBody, int statusCode) {
                    Log.i("error", errorBody.ErrorMessage);
                    bankListResponseInterface.onFailure(errorBody, statusCode);
                }
            });
        }
    }
    @Override
    public void generateBankListRequest() {
        goGenerateBankList();
    }

}
