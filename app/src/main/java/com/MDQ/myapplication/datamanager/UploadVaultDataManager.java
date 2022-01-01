package com.MDQ.myapplication.datamanager;

import static com.MDQ.myapplication.base.MyFinalystApp.getApp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.MDQ.myapplication.http.ApiInterface;
import com.MDQ.myapplication.interfaces.ResponseHandler;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateUploadVaultResponseModel;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadVaultDataManager {
    private final String TAG = UploadVaultDataManager.class.getSimpleName();
    private ApiInterface apiInterface;
    Context context;

    public UploadVaultDataManager(Context context) {
        this.context = context;
        this.apiInterface = getApp().getRetrofitInterface();
    }

    public void callEnqueue(String url, String token, MultipartBody.Part file, RequestBody proof, String encoded, final ResponseHandler<GenerateUploadVaultResponseModel> dataresponse) {

        //calling the generateUploadVaultCall methode from call apiInterface
        Call<GenerateUploadVaultResponseModel> userRegisterSuccessCall = apiInterface.generateUploadVaultCall(url, token, file);
        userRegisterSuccessCall.enqueue(new Callback<GenerateUploadVaultResponseModel>() {

            /**
             * @param call
             * @param response
             * @breif getting response from api
             */
            @Override
            public void onResponse(Call<GenerateUploadVaultResponseModel> call, Response<GenerateUploadVaultResponseModel> response) {
                /**
                 * Invoked for a received HTTP response.
                 * <p>
                 * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
                 * Call {@link Response#isSuccessful()} to determine if the response indicates success.
                 *
                 * @param call
                 * @param response
                 */
                Log.i("response", "response get");
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
             * @breif when api call failure
             */
            @Override
            public void onFailure(Call<GenerateUploadVaultResponseModel> call, Throwable t) {
                Log.d(TAG, "onTokenExpired: " + t.getMessage());
                Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}



