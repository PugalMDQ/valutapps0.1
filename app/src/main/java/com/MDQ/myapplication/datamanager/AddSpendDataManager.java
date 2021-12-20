package com.MDQ.myapplication.datamanager;

import static com.MDQ.myapplication.base.MyFinalystApp.getApp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.MDQ.myapplication.http.ApiInterface;
import com.MDQ.myapplication.interfaces.ResponseHandler;
import com.MDQ.myapplication.pojo.jsonrequest.GenerateAddAccountRequestModel;
import com.MDQ.myapplication.pojo.jsonrequest.GenerateAddSpendRequestModel;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateAddAccountResponseModel;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateAddSpendResponceModel;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSpendDataManager {
    private final String TAG = AddSpendDataManager.class.getSimpleName();
    private ApiInterface apiInterface;
    Context context;
    public AddSpendDataManager(Context  context) {
        this.context=context;
        this.apiInterface = getApp().getRetrofitInterface();
    }

    public void callEnqueue(String url, String token, String account_id, String amount, String type, String category, String subcategory, String Date, String note, String tag, String share_with, String location, MultipartBody.Part profile_picture, GenerateAddSpendRequestModel generateAddSpendRequestModel, final ResponseHandler<GenerateAddSpendResponceModel> dataresponse) {

        //calling the generatePostAddSpendCall methode from call apiInterface
        Call<GenerateAddSpendResponceModel> userAddSpendCall = apiInterface.generatePostAddSpendCall(url,token,account_id,amount,type,category,subcategory,Date,note,tag,share_with,profile_picture);
        userAddSpendCall.enqueue(new Callback<GenerateAddSpendResponceModel>() {


            /**
             * @param call
             * @param response
             * @breif getting response from api
             */
            @Override
            public void onResponse(Call<GenerateAddSpendResponceModel> call, Response<GenerateAddSpendResponceModel> response) {
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
            public void onFailure(Call<GenerateAddSpendResponceModel> call, Throwable t) {
                Log.d(TAG, "onTokenExpired: " + t.getMessage());
                Toast.makeText(context, ""+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}