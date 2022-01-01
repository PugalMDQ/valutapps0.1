package com.MDQ.myapplication.interfaces.viewresponceinterface;

import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateCategorySpentResponseModel;

//Response interface for Category Spend
public interface CategorySpentResponseInterface {

    void generateCategorySpendProcessed(GenerateCategorySpentResponseModel generateCategorySpentResponseModel);
    void onFailure(ErrorBody errorBody, int statusCode);


}
