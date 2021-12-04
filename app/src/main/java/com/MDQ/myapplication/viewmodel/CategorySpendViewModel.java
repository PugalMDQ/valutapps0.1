package com.MDQ.myapplication.viewmodel;

import android.content.Context;
import android.util.Log;

import com.MDQ.myapplication.datamanager.CategorySpentDataManager;
import com.MDQ.myapplication.datamanager.CurrencyDataManager;
import com.MDQ.myapplication.interfaces.ResponseHandler;
import com.MDQ.myapplication.interfaces.viewinterface.CategorySpentRequestInterface;
import com.MDQ.myapplication.interfaces.viewresponceinterface.CategorySpentResponseInterface;
import com.MDQ.myapplication.interfaces.viewresponceinterface.CurrencyResponseInterface;
import com.MDQ.myapplication.pojo.jsonrequest.GenerateCategorySpentRequestModel;
import com.MDQ.myapplication.pojo.jsonrequest.GenerateCurrencyRequestModel;
import com.MDQ.myapplication.pojo.jsonresponse.DataForCurrency;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateCategorySpentResponseModel;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateCurrencyResponseModel;
import com.MDQ.myapplication.utils.ApiClass;

import java.util.List;

public class CategorySpendViewModel extends CategorySpendBaseViewModel implements CategorySpentRequestInterface {


    private CategorySpentDataManager categorySpentDataManager;
    private CategorySpentResponseInterface categorySpentResponseInterface;
    private Context mContext;

    public CategorySpendViewModel(Context mContext, CategorySpentResponseInterface categorySpentResponseInterface) {
        this.categorySpentResponseInterface = categorySpentResponseInterface;
        this.categorySpentDataManager = new CategorySpentDataManager(mContext);
        this.mContext = mContext;
    }

    private void goGenerateCategorySpent() {
        GenerateCategorySpentRequestModel generateCategorySpentRequestModel=new GenerateCategorySpentRequestModel();
        generateCategorySpentRequestModel.type=getType();
        categorySpentDataManager.callEnqueue(ApiClass.CATEGORY_SPEND, getToken(),generateCategorySpentRequestModel, new ResponseHandler<GenerateCategorySpentResponseModel>() {
            @Override
            public void onSuccess(String message) {}
            @Override
            public void onSuccess(GenerateCategorySpentResponseModel item, String message) {
                Log.i("otpR","rr");
                List<DataForCurrency> list = null;
                if(item.getMsg()!=null) {
                    Log.i("otpRecevied", item.getMsg());
                    // Toast.makeText(mContext, ""+item.getMsg(), Toast.LENGTH_SHORT).show();
                    categorySpentResponseInterface.generateCategorySpendProcessed(item);
                }

            }

            @Override
            public void onFailure(ErrorBody errorBody, int statusCode) {
                Log.i("error" ,errorBody.ErrorMessage);
                categorySpentResponseInterface.onFailure(errorBody,statusCode);
            }
        });
    }
    @Override
    public void generateCategorySpentRequest() {
        goGenerateCategorySpent();
    }

}
