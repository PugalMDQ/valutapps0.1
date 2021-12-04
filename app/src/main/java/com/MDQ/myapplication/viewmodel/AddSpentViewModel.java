package com.MDQ.myapplication.viewmodel;

import android.content.Context;
import android.util.Log;

import com.MDQ.myapplication.datamanager.AddSpendDataManager;
import com.MDQ.myapplication.datamanager.BankListDataManager;
import com.MDQ.myapplication.interfaces.ResponseHandler;
import com.MDQ.myapplication.interfaces.viewinterface.AddSpendRequestInterface;
import com.MDQ.myapplication.interfaces.viewresponceinterface.AddSpendResponceInterface;
import com.MDQ.myapplication.interfaces.viewresponceinterface.BankListResponseInterface;
import com.MDQ.myapplication.pojo.jsonrequest.GenerateAddSpendRequestModel;
import com.MDQ.myapplication.pojo.jsonrequest.GenerateBankListRequestModel;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateAddSpendResponceModel;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateBankListResponseModel;
import com.MDQ.myapplication.utils.ApiClass;

public class AddSpentViewModel extends AddSpentBaseViewModel implements AddSpendRequestInterface {


    private AddSpendDataManager addSpendDataManager;
    private AddSpendResponceInterface addSpendResponceInterface;
    private Context mContext;

    public AddSpentViewModel(Context mContext, AddSpendResponceInterface addSpendResponceInterface) {
        this.addSpendResponceInterface = addSpendResponceInterface;
        this.addSpendDataManager = new AddSpendDataManager(mContext);
        this.mContext = mContext;
    }

    private void goGenerateAddSpend() {
        GenerateAddSpendRequestModel generateAddSpendRequestModel=new GenerateAddSpendRequestModel();
        generateAddSpendRequestModel.amount=getAmount();
        generateAddSpendRequestModel.category=getCategory();
        generateAddSpendRequestModel.Date=getDate();
        generateAddSpendRequestModel.location=getLocation();
        generateAddSpendRequestModel.share_with=getShare_with();
        generateAddSpendRequestModel.tag=getTag();
        generateAddSpendRequestModel.subcategory=getSubcategory();
        generateAddSpendRequestModel.note=getNote();
        generateAddSpendRequestModel.profile_picture=getProfile_picture();
        addSpendDataManager.callEnqueue(ApiClass.ADD_SPEND, getToken(),generateAddSpendRequestModel, new ResponseHandler<GenerateAddSpendResponceModel>() {
            @Override
            public void onSuccess(String message) {}
            @Override
            public void onSuccess(GenerateAddSpendResponceModel item, String message) {
                Log.i("otpR","rr");
                if(item.getMsg()!=null) {
                    Log.i("otpRecevied", item.getMsg());
                    //   Toast.makeText(mContext, ""+item.getMsg(), Toast.LENGTH_SHORT).show();
                    addSpendResponceInterface.generateAddSpendProcessed(item);
                }

            }

            @Override
            public void onFailure(ErrorBody errorBody, int statusCode) {
                Log.i("error" ,errorBody.ErrorMessage);
                addSpendResponceInterface.onFailure(errorBody,statusCode);
            }
        });
    }
    @Override
    public void generateAddSpendRequest() {

        goGenerateAddSpend();
    }
}
