
package com.MDQ.myapplication;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.MDQ.myapplication.databinding.ActivityEnteryourmpinBinding;
import com.MDQ.myapplication.enums.MessageViewType;
import com.MDQ.myapplication.enums.ViewType;
import com.MDQ.myapplication.interfaces.viewresponceinterface.BioMetricsValidationResponseInterface;
import com.MDQ.myapplication.interfaces.viewresponceinterface.MpinValidationResponseInterfacce;
import com.MDQ.myapplication.interfaces.viewresponceinterface.OtpResponseInterface;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateBioMetricsValidationResponseModel;
import com.MDQ.myapplication.utils.PreferenceManager;
import com.MDQ.myapplication.viewmodel.BioMetricsValidationViewModel;
import com.MDQ.myapplication.viewmodel.MpinValidationViewModel;
import com.MDQ.myapplication.viewmodel.OtpRequestViewModel;

import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class enteryourmpin extends AppCompatActivity implements OtpResponseInterface, MpinValidationResponseInterfacce, BioMetricsValidationResponseInterface {

    ActivityEnteryourmpinBinding ap;
    private OtpRequestViewModel otpRequestViewModel;
    private MpinValidationViewModel mpinValidationViewModel;
    BioMetricsValidationViewModel bioMetricsValidationViewModel;
    PreferenceManager preferenceManager;
    String Token,mpins;
    String otp;

    ArrayList<String> pin=new ArrayList<>();
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initialize with View
        ap =ActivityEnteryourmpinBinding.inflate(getLayoutInflater());
        setContentView(ap.getRoot());

        //set underline for forgot text
        ap.forgot.setPaintFlags(ap.forgot.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        //get token from previous screen
        Intent intent=getIntent();
        Token=intent.getStringExtra("token");

        numberAndEditText();

        //get biometric is on or of information from local storage
//        String ttrue=getPreferenceManager().getPrefBiometric();
//        if(ttrue!="") {
//                biometricAuthentication();
//        }

        bioMetricsValidationViewModel=new BioMetricsValidationViewModel(getApplicationContext(),this);
        setdeclareforbioValidation();


        //backspace imageView when the view clicked it will delete the lastly typed number
        ap.backspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = pin.size();
                if (i != 0) {
                    Log.i("i", "" + i);
                    pin.remove(i - 1);
                    if (pin.size() == 3) {
                        ap.editsix.setText("");
                        ap.editsix.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_CLASS_TEXT);
                        ap.linearSix.setBackgroundColor(getResources().getColor(R.color.white));

                    }
                    if (pin.size() == 2) {
                        ap.editsix.setText("");
                        ap.editfive.setText("");
                        ap.editsix.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_CLASS_TEXT);
                        ap.editfive.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_CLASS_TEXT);
                        ap.linearSix.setBackgroundColor(getResources().getColor(R.color.white));
                        ap.linearFive.setBackgroundColor(getResources().getColor(R.color.white));
                    }
                    if (pin.size() == 1) {
                        ap.editsix.setText("");
                        ap.editfive.setText("");
                        ap.editfour.setText("");
                        ap.editsix.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_CLASS_TEXT);
                        ap.editfive.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_CLASS_TEXT);
                        ap.editfour.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_CLASS_TEXT);
                        ap.linearSix.setBackgroundColor(getResources().getColor(R.color.white));
                        ap.linearFive.setBackgroundColor(getResources().getColor(R.color.white));
                        ap.linearFour.setBackgroundColor(getResources().getColor(R.color.white));
                    }
                    if(pin.size()==0){
                        ap.editsix.setText("");
                        ap.editfive.setText("");
                        ap.editfour.setText("");
                        ap.editthree.setText("");
                        ap.editthree.setText("");
                        ap.editsix.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_CLASS_TEXT);
                        ap.editfive.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_CLASS_TEXT);
                        ap.editfour.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_CLASS_TEXT);
                        ap.editthree.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_CLASS_TEXT);
                        ap.linearSix.setBackgroundColor(getResources().getColor(R.color.white));
                        ap.linearFive.setBackgroundColor(getResources().getColor(R.color.white));
                        ap.linearFour.setBackgroundColor(getResources().getColor(R.color.white));
                        ap.linearThree.setBackgroundColor(getResources().getColor(R.color.white));
                    }
                }

            }
        });


        //checking internet connection if available call setDeclareForValidation methode else toast the error message
        ap.Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext()
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                if ((connectivityManager
                        .getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null && connectivityManager
                        .getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)
                        || (connectivityManager
                        .getNetworkInfo(ConnectivityManager.TYPE_WIFI) != null && connectivityManager
                        .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                        .getState() == NetworkInfo.State.CONNECTED)) {
                    setDeclareforvalidation();
                }
                else{
                    Toast.makeText(getApplicationContext(), "This App Require Internet ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //initialize with viewModel
        otpRequestViewModel=new OtpRequestViewModel(getApplicationContext(),this);
        mpinValidationViewModel=new MpinValidationViewModel(getApplicationContext(),this);

        //Checking for internet connection if available call setDeclare methode else toast error message
        ap.forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext()
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                if ((connectivityManager
                        .getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null && connectivityManager
                        .getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)
                        || (connectivityManager
                        .getNetworkInfo(ConnectivityManager.TYPE_WIFI) != null && connectivityManager
                        .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                        .getState() == NetworkInfo.State.CONNECTED)) {
                    setDecler();
                }
                else{
                    Toast.makeText(getApplicationContext(), "This App Require Internet ", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void setdeclareforbioValidation() {
        bioMetricsValidationViewModel.setToken(getPreferenceManager().getPrefToken());
        bioMetricsValidationViewModel.generateBioMetricsValidationRequest();
    }

    //Authenticate using biometric sensor
    private void biometricAuthentication() {
        executor = ContextCompat.getMainExecutor(this);
        BiometricManager biometricManager=BiometricManager.from(this);
        biometricPrompt = new BiometricPrompt(enteryourmpin.this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);

            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(),
                        "Authentication succeeded!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(enteryourmpin.this,balancehome.class));
                finish();

            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Authentication failed",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login for MyFinalyst")
                .setSubtitle("Log in using your biometric credential")
                .setNegativeButtonText("cancel")
                .build();

            biometricPrompt.authenticate(promptInfo);
    }

    private void numberAndEditText() {
        //set font type for edittext
        Typeface tf=Typeface.createFromAsset(getAssets(),"fonts/ZillaSlab-Bold.ttf");
        ap.editthree.requestFocus();

        //set input type as null for edit text
        ap.editthree.setRawInputType(InputType.TYPE_NULL);
        ap.editthree.setFocusable(true);
        ap.editfour.setRawInputType(InputType.TYPE_NULL);
        ap.editfour.setFocusable(true);
        ap.editfive.setRawInputType(InputType.TYPE_NULL);
        ap.editfive.setFocusable(true);
        ap.editsix.setRawInputType(InputType.TYPE_NULL);
        ap.editsix.setFocusable(true);

        //For numbers textView the num have clicked it will show on edit text
        ap.one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addpin("1");
            }
        });
        ap.two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addpin("2");

            }
        });
        ap.three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addpin("3");
            }
        });
        ap.four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addpin("4");
            }
        });
        ap.five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addpin("5");
            }
        });
        ap.six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addpin("6");
            }
        });
        ap.seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addpin("7");
            }
        });
        ap.eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addpin("8");
            }
        });
        ap.nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addpin("9");
            }
        });
        ap.zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addpin("0");
            }
        });

    }

    private void addpin(String s) {
        if(pin.size()<4){
        pin.add(s);}

        if (pin.size() == 1) {
            ap.editthree.setText(pin.get(0));
            ap.editthree.setTextColor(getResources().getColor(R.color.blue));
            ap.linearThree.setBackgroundColor(getResources().getColor(R.color.blue));
            new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                           ap.editthree.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                        }
                    },1000);
        }
        if (pin.size() == 2 ) {
            ap.editthree.setText(pin.get(0));
            ap.editfour.setText(pin.get(1));
            ap.editthree.setTextColor(getResources().getColor(R.color.blue));
            ap.editfour.setTextColor(getResources().getColor(R.color.blue));
            ap.linearThree.setBackgroundColor(getResources().getColor(R.color.blue));
            ap.linearFour.setBackgroundColor(getResources().getColor(R.color.blue));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ap.editthree.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                    ap.editfour.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                }
            },1000);
        }
        if (pin.size() ==3) {
            ap.editthree.setText(pin.get(0));
            ap.editfour.setText(pin.get(1));
            ap.editfive.setText(pin.get(2));
            ap.editthree.setTextColor(getResources().getColor(R.color.blue));
            ap.editfour.setTextColor(getResources().getColor(R.color.blue));
            ap.editfive.setTextColor(getResources().getColor(R.color.blue));
            ap.linearThree.setBackgroundColor(getResources().getColor(R.color.blue));
            ap.linearFour.setBackgroundColor(getResources().getColor(R.color.blue));
            ap.linearFive.setBackgroundColor(getResources().getColor(R.color.blue));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ap.editthree.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                    ap.editfour.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                    ap.editfive.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                }
            },1000);
        }
        if (pin.size() ==4) {
            ap.editthree.setText(pin.get(0));
            ap.editfour.setText(pin.get(1));
            ap.editfive.setText(pin.get(2));
            ap.editsix.setText(pin.get(3));
            ap.editthree.setTextColor(getResources().getColor(R.color.blue));
            ap.editfour.setTextColor(getResources().getColor(R.color.blue));
            ap.editfive.setTextColor(getResources().getColor(R.color.blue));
            ap.editsix.setTextColor(getResources().getColor(R.color.blue));
            ap.linearThree.setBackgroundColor(getResources().getColor(R.color.blue));
            ap.linearFour.setBackgroundColor(getResources().getColor(R.color.blue));
            ap.linearFive.setBackgroundColor(getResources().getColor(R.color.blue));
            ap.linearSix.setBackgroundColor(getResources().getColor(R.color.blue));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ap.editthree.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                    ap.editfour.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                    ap.editfive.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                    ap.editsix.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                }
            },1000);
        }
    }

    //set request for mpinValidaton api
    private void setDeclareforvalidation() {
        mpins = ""+ ap.editthree.getText() + ap.editfour.getText() + ap.editfive.getText()
                + ap.editsix.getText();
        Token=getPreferenceManager().getPrefToken();
        if(mpins.length()==4) {
            mpinValidationViewModel.setToken(Token);
            mpinValidationViewModel.setMpin(mpins);
            mpinValidationViewModel.generateMpinValidationRequest();
        }
    }

    //set request for resend otp api
    private void setDecler() {
        otpRequestViewModel.setPhone(getPreferenceManager().getPrefPhoneNum());
        otpRequestViewModel.setCountry_code("91");
        otpRequestViewModel.generateOtpRequest();
    }

    @Override
    public void ShowErrorMessage(MessageViewType messageViewType, String errorMessage) {
        //do nothing
    }

    @Override
    public void ShowErrorMessage(MessageViewType messageViewType, ViewType viewType, String errorMessage) {
        //do nothing

    }

    /**
     * @param Otp
     * @breif get response from resend otp api
     */
    @Override
    public void generateOtpProcessed(String Otp) {
        if(Otp!=null){
            otp=Otp;
            Intent intent=new Intent(enteryourmpin.this,phonenumberverfication2.class);
            intent.putExtra("otp",otp);
            startActivity(intent);


        }
        else {
            Log.i("Response","Nothing");
        }
    }

    /**
     * @param Token
     * @param msg
     * @breif get response from mpinValidation api
     */
    @Override
    public void generateMpinValidationProcessed(String Token,String msg)
    {
        Toast.makeText(getApplicationContext(), ""+msg, Toast.LENGTH_SHORT).show();
        if(Token!=null){
            startActivity(new Intent(enteryourmpin.this,balancehome.class));
            finish();
        }
    }

    @Override
    public void generateBioMetricsValidationProcessed(GenerateBioMetricsValidationResponseModel generateBioMetricsValidationsResponseModel) {

        if(generateBioMetricsValidationsResponseModel.getMsg().equals("Biometric Activated")){
            biometricAuthentication();
            String token=generateBioMetricsValidationsResponseModel.getData().getToken();
            getPreferenceManager().setPrefToken(token);
        }
    }

    /**
     * @param errorBody
     * @param statusCode
     * @breif if any error toast the error message
     */
    @Override
    public void onFailure(ErrorBody errorBody, int statusCode) {
        Toast.makeText(getApplicationContext(), ""+errorBody.ErrorMessage, Toast.LENGTH_SHORT).show();
    }

    /**
     * @return
     * @brief initializing the preferenceManager from shared preference for local use in this activity
     */
    public PreferenceManager getPreferenceManager() {
        if (preferenceManager == null) {
            preferenceManager = PreferenceManager.getInstance();
            preferenceManager.initialize(getApplicationContext());
        }
        return preferenceManager;
    }
}

