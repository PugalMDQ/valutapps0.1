package com.MDQ.myapplication.datamanager;

import static com.MDQ.myapplication.base.MyFinalystApp.getApp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.MDQ.myapplication.http.ApiInterface;
import com.MDQ.myapplication.interfaces.ResponseHandler;
import com.MDQ.myapplication.pojo.jsonrequest.GenerateMpinRequestModel;
import com.MDQ.myapplication.pojo.jsonrequest.GenerateMpinValidationRequestModel;
import com.MDQ.myapplication.pojo.jsonrequest.GenerateOtpRequestModel;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateMpinValidationResponseModel;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateOtpResponseModel;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MpinValidationDataManager {


    private final String TAG = MpinValidationDataManager.class.getSimpleName();
    private ApiInterface apiInterface;
    Context context;

    public MpinValidationDataManager(Context context) {
        this.context = context;
        this.apiInterface = getApp().getRetrofitInterface();
    }

    public void callEnqueue(String url, String Token, GenerateMpinValidationRequestModel generateMpinRequestModel, final ResponseHandler<GenerateMpinValidationResponseModel> dataresponse) {

        //calling the generatePostMpinValidationCall methode from call apiInterface
        Call<GenerateMpinValidationResponseModel> userMpinValidationCall = apiInterface.generatePostMpinValidationCall(url, Token, generateMpinRequestModel);
        userMpinValidationCall.enqueue(new Callback<GenerateMpinValidationResponseModel>() {


            /**
             * @param call
             * @param response
             * @breif getting response from api
             */
            @Override
            public void onResponse(Call<GenerateMpinValidationResponseModel> call, Response<GenerateMpinValidationResponseModel> response) {
                /**
                 * Invoked for a received HTTP response.
                 * <p>
                 * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
                 * Call {@link Response#isSuccessful()} to determine if the response indicates success.
                 *
                 * @param call
                 * @param response
                 */
                int statusCode = response.code();

                //if response is successful set the body of response to onSuccess methode in GenerateRegisterResponseModel else get the error body and set on onFailure in generateRegisterResponseModel
                if (response.isSuccessful()) {
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
             * @breif api call failure
             */
            @Override
            public void onFailure(Call<GenerateMpinValidationResponseModel> call, Throwable t) {
                Log.d(TAG, "onTokenExpired: " + t.getMessage());
                Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
