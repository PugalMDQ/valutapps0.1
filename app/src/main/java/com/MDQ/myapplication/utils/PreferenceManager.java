package com.MDQ.myapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context mContext;
    private int mPrivateMode = 0;

    private static final String PREF_NAME = "PREF_MYFINALYST";
    private static final String PREF_TOKEN = "PREF_TOKEN";
    private static final String PREF_MPIN = "PREF_MPIN";
    private static final String PREF_PHONE_NUM = "PREF_PHONE_NUM";
    private static final String PREF_IS_LOGGED_IN = "PREF_IS_LOGGED_IN";
    private static final String PREF_IMAGE_URL = "PREF_IMAGE_URL";
    private static final String PREF_NAME_IN_CATEGORY = "PREF_NAME";
    private static final String PREF_OTP = "PREF_OTP";
    private static final String PREF_BIOMETRIC = "PREF_BIOMETRIC";
    private static final String PREF_EMAIL = "PREF_EMAIL";

    private static PreferenceManager mInstance;

    public static PreferenceManager getInstance() {
        if (mInstance == null)
            mInstance = new PreferenceManager();

        return mInstance;
    }

    private PreferenceManager() {
    }

    public void initialize(Context context) {
        this.mContext = context;
        sharedPreferences = mContext.getSharedPreferences(PREF_NAME, mPrivateMode);
        editor = sharedPreferences.edit();
    }

    public void clearPreference() {
        //editor.clear();
        editor.remove(PREF_IS_LOGGED_IN);
        editor.remove(PREF_TOKEN);
        editor.apply();
    }

    public void setPrefPhoneNum(String Phone_Num){
        editor.putString(PREF_PHONE_NUM,Phone_Num);
        editor.commit();
    }

    public String getPrefPhoneNum(){
        return sharedPreferences.getString(PREF_PHONE_NUM,null);
    }
    public void setPrefToken(String Token){
        editor.putString(PREF_TOKEN,Token);
        editor.commit();
    }

    public String getPrefToken(){
        return   sharedPreferences.getString(PREF_TOKEN, null);
    }


    public  void setPrefMpin(String Mpin){
        editor.putString(PREF_MPIN,Mpin);
        editor.commit();
    }


    public String getPrefMpin(){
        return   sharedPreferences.getString(PREF_MPIN, null);
    }

    public  void setPrefImageUrl(String imageUrl){
        editor.putString(PREF_IMAGE_URL,imageUrl);
        editor.commit();
    }


    public String getPrefImageUrl(){
        return   sharedPreferences.getString(PREF_IMAGE_URL, null);
    }

  public  void setPrefNameInCategory(String name) {
      editor.putString(PREF_NAME_IN_CATEGORY, name);
      editor.commit();
  }

    public String getPrefNameInCategory(){
        return   sharedPreferences.getString(PREF_NAME_IN_CATEGORY, null);
    }

        public  void setPrefOtp(String otp){
                editor.putString(PREF_OTP,otp);
                editor.commit();
            }


            public String getPrefOtp(){
                return   sharedPreferences.getString(PREF_OTP, null);
            }

             public  void setPrefBiometric(String text){
                            editor.putString(PREF_BIOMETRIC,text);
                            editor.commit();
                        }

                        public String getPrefBiometric(){
                            return   sharedPreferences.getString(PREF_BIOMETRIC, null);
                        }

                        public  void setPrefEmail(String text){
                            editor.putString(PREF_EMAIL,text);
                            editor.commit();
                        }
                        public String getPrefEmail(){
                            return   sharedPreferences.getString(PREF_EMAIL, null);
                        }
}
