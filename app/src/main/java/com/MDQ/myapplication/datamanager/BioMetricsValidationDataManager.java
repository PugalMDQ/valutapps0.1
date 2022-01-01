package com.MDQ.myapplication.datamanager;

import static com.MDQ.myapplication.base.MyFinalystApp.getApp;

import android.content.Context;
import android.util.Log;

import com.MDQ.myapplication.http.ApiInterface;
import com.MDQ.myapplication.interfaces.ResponseHandler;
import com.MDQ.myapplication.pojo.jsonrequest.GenerateBioMetricsRequestModel;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateBioMetricsResponseModel;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateBioMetricsValidationResponseModel;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BioMetricsValidationDataManager {

    private final String TAG = BioMetricsValidationDataManager.class.getSimpleName();
    private ApiInterface apiInterface;
    Context context;

    public BioMetricsValidationDataManager(Context context) {
        this.context = context;
        this.apiInterface = getApp().getRetrofitInterface();
    }

    public void callEnqueue(String url, String token, final ResponseHandler<GenerateBioMetricsValidationResponseModel> dataresponse) {

        //calling the generateBioMetricsCall methode from call apiInterface
        Call<GenerateBioMetricsValidationResponseModel> userBioMetricsCall = apiInterface.generateBioMetricsValidationCall(url, token);
        userBioMetricsCall.enqueue(new Callback<GenerateBioMetricsValidationResponseModel>() {


            /**
             * @param call
             * @param response
             * @breif getting response from api
             */
            @Override
            public void onResponse(Call<GenerateBioMetricsValidationResponseModel> call, Response<GenerateBioMetricsValidationResponseModel> response) {
                /**
                 * Invoked for a received HTTP response.
                 * <p>
                 * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
                 * Call {@link Response#isSuccessful()} to determine if the response indicates success.
                 *
                 * @param call
                 * @param response
                 */
                Log.i("responce", "response get");
                int statusCode = response.code();

                //if response is successful set the body of response to onSuccess methode in GenerateRegisterResponseModel else get the error body and set on onFailure in generateRegisterResponseModel
                if (response.isSuccessful() && response != null) {
                    dataresponse.onSuccess(response.body(), "SuccessModel");
                } else {
                    String serviceResponse = null;
                    try {
                        serviceResponse = response.errorBody().string();
                        ErrorBody errorBody = new Gson().fromJson(serviceResponse, ErrorBody.class);
                        dataresponse.onFailure(errorBody, statusCode);
                    } catch (JsonSyntaxException e) {
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }


            /**
             * @param call
             * @param t
             * @breif when api call failure
             */
            @Override
            public void onFailure(Call<GenerateBioMetricsValidationResponseModel> call, Throwable t) {
                Log.d(TAG, "onTokenExpired: " + t.getMessage());

            }
        });
    }
}
