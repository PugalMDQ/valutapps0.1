package com.MDQ.myapplication.base;

import android.app.Application;
import android.content.Context;

import com.MDQ.myapplication.http.ApiInterface;
import com.MDQ.myapplication.http.MyFinalystApiClient;
//For setting the retrofit for api call to communicate with the backend server
public class MyFinalystApp  extends Application {

        public static Context mContext;
        public static MyFinalystApp mInstance;
        public static MyFinalystApiClient myFinalystApiClient ;

        @Override
        public void onCreate() {
            super.onCreate();
            mInstance = this;
            mContext = this;
            myFinalystApiClient = new MyFinalystApiClient();
        }

        public static synchronized Context getContext() {
            return mContext;
        }

        public static MyFinalystApp getApp() {
            if (mInstance != null && mInstance instanceof MyFinalystApp) {
                return mInstance;
            } else {
                mInstance = new MyFinalystApp();
                mInstance.onCreate();
                return mInstance;
            }
        }

        public ApiInterface getRetrofitInterface() {
            return myFinalystApiClient.apiinterface();
        }

        protected void attachBaseContext(Context base) {
            super.attachBaseContext(base);
        }



    }


