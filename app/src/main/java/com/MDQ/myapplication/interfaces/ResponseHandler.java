package com.MDQ.myapplication.interfaces;

import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;

public interface ResponseHandler<T> {
        void onSuccess(String message);

        void onSuccess(T item, String message);

        void onFailure(ErrorBody errorBody, int statusCode);

}


