package com.MDQ.myapplication.http;

import com.MDQ.myapplication.utils.ApiClass;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
//For making the connection with the backend server
    public class MyFinalystApiClient {

        private static Retrofit retrofit = null;
        private static ApiClass apiClass;

        public MyFinalystApiClient(){

        }
        public ApiInterface apiinterface() {
            apiClass = new ApiClass();
            if (retrofit == null) {
                OkHttpClient defaultHttpClient = new OkHttpClient.Builder()
                        .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                        .connectTimeout(120, TimeUnit.SECONDS)
                        .retryOnConnectionFailure(true)
                        .build();

                retrofit = new Retrofit.Builder()
                        .baseUrl(apiClass.BASE_URL)
                        .addConverterFactory(JacksonConverterFactory.create())
                        .client(defaultHttpClient)
                        .build();
            }
            return retrofit.create(ApiInterface.class);
        }
    }


