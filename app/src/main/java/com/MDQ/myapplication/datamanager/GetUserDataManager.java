package com.MDQ.myapplication.datamanager;

import static com.MDQ.myapplication.base.MyFinalystApp.getApp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.MDQ.myapplication.http.ApiInterface;
import com.MDQ.myapplication.interfaces.ResponseHandler;
import com.MDQ.myapplication.pojo.jsonrequest.GenerateGetUserRequestModel;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateGetUserResponseModel;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetUserDataManager {


    private final String TAG = GetUserDataManager.class.getSimpleName();
    private ApiInterface apiInterface;
    Context context;
    public GetUserDataManager(Context  context) {
        this.context=context;
        this.apiInterface = getApp().getRetrofitInterface();
    }

    public void callEnqueue(String url,  String token, GenerateGetUserRequestModel generateGetUserRequestModel, final ResponseHandler<GenerateGetUserResponseModel> dataresponse) {
        Call<GenerateGetUserResponseModel> userMpinCall = apiInterface.generatePostGetUserCall(url,token);
        userMpinCall.enqueue(new Callback<GenerateGetUserResponseModel>() {
            @Override
            public void onResponse(Call<GenerateGetUserResponseModel> call, Response<GenerateGetUserResponseModel> response) {
                /**
                 * Invoked for a received HTTP response.
                 * <p>
                 * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
                 * Call {@link Response#isSuccessful()} to determine if the response indicates success.
                 *
                 * @param call
                 * @param response
                 */
                Log.i("responce","response get");
                int statusCode = response.code();
                if (response.isSuccessful()) {
                    dataresponse.onSuccess(response.body(), "SuccessModel");
                } else {
                    String serviceResponse = null;
                    try {
                        serviceResponse = response.errorBody().string();
                        ErrorBody errorBody = new Gson().fromJson(serviceResponse, ErrorBody.class);
                        dataresponse.onFailure(errorBody, statusCode);
                    } catch (JsonSyntaxException e) {
//                        dataresponse.onTokenExpired("Something went wrong - Error Code: " + statusCode);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<GenerateGetUserResponseModel> call, Throwable t) {
                Log.d(TAG, "onTokenExpired: " + t.getMessage());
                Toast.makeText(context, ""+t.getMessage(), Toast.LENGTH_LONG).show();
//                dataresponse.onTokenExpired(t.getMessage());
            }
        });

    }
}



