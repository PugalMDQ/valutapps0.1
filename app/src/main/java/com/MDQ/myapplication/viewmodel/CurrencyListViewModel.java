package com.MDQ.myapplication.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.MDQ.myapplication.datamanager.BankListDataManager;
import com.MDQ.myapplication.datamanager.CurrencyDataManager;
import com.MDQ.myapplication.interfaces.ResponseHandler;
import com.MDQ.myapplication.interfaces.viewinterface.CurrencyRequestInterface;
import com.MDQ.myapplication.interfaces.viewresponceinterface.BankListResponseInterface;
import com.MDQ.myapplication.interfaces.viewresponceinterface.CurrencyResponseInterface;
import com.MDQ.myapplication.pojo.jsonrequest.GenerateBankListRequestModel;
import com.MDQ.myapplication.pojo.jsonrequest.GenerateCurrencyRequestModel;
import com.MDQ.myapplication.pojo.jsonresponse.DataForCurrency;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateBankListResponseModel;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateCurrencyResponseModel;
import com.MDQ.myapplication.utils.ApiClass;

import java.util.List;

public class CurrencyListViewModel extends CurrencyListBaseViewModel implements CurrencyRequestInterface
{
    private CurrencyDataManager currencyDataManager;
    private CurrencyResponseInterface currencyResponseInterface;
    private Context mContext;

    public CurrencyListViewModel(Context mContext, CurrencyResponseInterface currencyResponseInterface) {
        this.currencyResponseInterface = currencyResponseInterface;
        this.currencyDataManager = new CurrencyDataManager(mContext);
        this.mContext = mContext;
    }

    private void goGenerateCurrency() {
        GenerateCurrencyRequestModel generateCurrencyRequestModel=new GenerateCurrencyRequestModel();
        generateCurrencyRequestModel.token=getToken();
        currencyDataManager.callEnqueue(ApiClass.CURRENCY_URL, getToken(), new ResponseHandler<GenerateCurrencyResponseModel>() {
            @Override
            public void onSuccess(String message) {}
            @Override
            public void onSuccess(GenerateCurrencyResponseModel item, String message) {
                Log.i("otpR","rr");
                List<DataForCurrency> list = null;
                if(item.getMsg()!=null) {
                    Log.i("otpRecevied", item.getMsg());
                   // Toast.makeText(mContext, ""+item.getMsg(), Toast.LENGTH_SHORT).show();
                    currencyResponseInterface.generateCurrencyProcessed(item);
                }

            }

            @Override
            public void onFailure(ErrorBody errorBody, int statusCode) {
                Log.i("error" ,errorBody.ErrorMessage);
                currencyResponseInterface.onFailure(errorBody,statusCode);
            }
        });
    }
    @Override
    public void generateCurrencyRequest() {
        goGenerateCurrency();
    }

}
