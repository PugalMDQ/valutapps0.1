package com.MDQ.myapplication.http;

import com.MDQ.myapplication.pojo.jsonrequest.GenerateAddAccountRequestModel;
import com.MDQ.myapplication.pojo.jsonrequest.GenerateAddSpendRequestModel;
import com.MDQ.myapplication.pojo.jsonrequest.GenerateAuthenticationRequestModel;
import com.MDQ.myapplication.pojo.jsonrequest.GenerateCategorySpentRequestModel;
import com.MDQ.myapplication.pojo.jsonrequest.GenerateListTransactionRequestModel;
import com.MDQ.myapplication.pojo.jsonrequest.GenerateLoginRequestModel;
import com.MDQ.myapplication.pojo.jsonrequest.GenerateMpinRequestModel;
import com.MDQ.myapplication.pojo.jsonrequest.GenerateMpinValidationRequestModel;
import com.MDQ.myapplication.pojo.jsonrequest.GenerateOtpRequestModel;
import com.MDQ.myapplication.pojo.jsonrequest.GenerateRegisterRequestModel;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateAccountListResponseModel;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateAccountTypeResponseModel;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateAddAccountResponseModel;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateAddSpendResponceModel;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateAuthenticationResponseModel;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateBankListResponseModel;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateCategorySpentResponseModel;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateCurrencyResponseModel;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateGetUserResponseModel;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateListTransactionResponseModel;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateLoginResponseModel;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateMpinResponseModel;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateMpinValidationResponseModel;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateOtpResponseModel;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateRegisterResponseModel;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateRegisterSuccessResponseModel;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateUploadVaultResponseModel;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateVaultListResponseModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

public interface ApiInterface {


    @POST
    Call<GenerateOtpResponseModel> generatePostOtpCall(@Url String url, @Body GenerateOtpRequestModel generateOtpRequestModel);

    @POST
    Call<GenerateRegisterResponseModel> generatePostRegisterCall(@Url String url, @Body GenerateRegisterRequestModel generateRegisterRequestModel);

    @POST
    Call<GenerateMpinResponseModel> generatePostMpinCall(@Url String url, @Header("Authtoken") String authtoken, @Body GenerateMpinRequestModel generateMpinRequestModel);

    @POST
    Call<GenerateLoginResponseModel> generatePostLoginCall(@Url String url, @Body GenerateLoginRequestModel generateLoginRequestModel);


    @POST
    Call<GenerateMpinValidationResponseModel> generatePostMpinValidationCall(@Url String url, @Header("Authtoken") String authtoken, @Body GenerateMpinValidationRequestModel generateMpinValidationRequestModel);


    @POST
    Call<GenerateGetUserResponseModel> generatePostGetUserCall(@Url String url, @Header("Authtoken") String authtoken);


    @POST
    Call<GenerateAuthenticationResponseModel> generatePostAuthenticationCall(@Url String url, @Body GenerateAuthenticationRequestModel generateAuthenticationRequestModel);


    @POST
    Call<GenerateRegisterSuccessResponseModel> generatePostRegisterSuccessCall(@Url String url, @Header("Authtoken") String authtoken);

    @POST
    Call<GenerateBankListResponseModel> generatePostBankListCall(@Url String url, @Header("Authtoken") String authtoken);


    @POST
    Call<GenerateCurrencyResponseModel> generatePostCurrencyCall(@Url String url, @Header("Authtoken") String authtoken);


    @POST
    Call<GenerateAccountTypeResponseModel> generatePostAccountTypeCall(@Url String url, @Header("Authtoken") String authtoken);


    @POST
    Call<GenerateAddAccountResponseModel> generatePostAddAccountCall(@Url String url, @Header("Authtoken") String authtoken, @Body GenerateAddAccountRequestModel generateAddAccountRequestModel);


    @POST
    Call<GenerateAccountListResponseModel> generatePostAccountListCall(@Url String url, @Header("Authtoken") String authtoken);

    @POST
    Call<GenerateCategorySpentResponseModel> generatePostCategorySpentCall(@Url String url, @Header("Authtoken") String authtoken, @Body GenerateCategorySpentRequestModel generateCategorySpentRequestModel);

    @POST
    Call<GenerateListTransactionResponseModel> generatePostListTransactionCall(@Url String url, @Header("Authtoken") String authtoken, @Body GenerateListTransactionRequestModel generateListTransactionRequestModel);

    @POST
    Call<GenerateAddSpendResponceModel> generatePostAddSpendCall(@Url String url, @Header("Authtoken") String authtoken, @Body GenerateAddSpendRequestModel generateAddSpendRequestModel);

    @POST
    @Multipart
    Call<GenerateUploadVaultResponseModel> generateUploadVaultCall(@Url String url, @Header("Authtoken") String authtoken, @Part MultipartBody.Part proof);

    @POST
    Call<GenerateVaultListResponseModel> generateVaultListCall(@Url String url, @Header("Authtoken") String authtoken);


}

