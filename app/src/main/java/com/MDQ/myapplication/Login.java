package com.MDQ.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.BlendMode;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.media.tv.TvContract;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.MDQ.myapplication.databinding.ActivityLoginBinding;
import com.MDQ.myapplication.enums.MessageViewType;
import com.MDQ.myapplication.enums.ViewType;
import com.MDQ.myapplication.interfaces.viewresponceinterface.LoginResponseInterface;
import com.MDQ.myapplication.interfaces.viewresponceinterface.RegisterResponseInterface;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.utils.PreferenceManager;
import com.MDQ.myapplication.viewmodel.LoginRequestViewModel;
import com.MDQ.myapplication.viewmodel.OtpRequestViewModel;
import com.MDQ.myapplication.viewmodel.RegisterRequestViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class Login extends AppCompatActivity implements LoginResponseInterface {
    ActivityLoginBinding al;
    LoginRequestViewModel loginRequestViewModel;
    EditText phoneinl;
    String phones;
    PreferenceManager preferenceManager;
    String otp,token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        al=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(al.getRoot());
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        Typeface tf=Typeface.createFromAsset(getAssets(),"fonts/ZillaSlab-Bold.ttf");
        al.welcome.setTypeface(tf);
        phoneinl=findViewById(R.id.Phonenumberl);
        al.Phonenumberl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ViewGroup.LayoutParams layoutParams= al.cardforphonenumberinlogin.getLayoutParams();
                al.cardforphonenumberinlogin.setLayoutParams(layoutParams);
                al.cardforphonenumberinlogin.setCardElevation(20f);
                return false;
            }
        });
        al.cardforphonenumberinlogin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ViewGroup.LayoutParams layoutParams= al.cardforphonenumberinlogin.getLayoutParams();
                al.cardforphonenumberinlogin.setLayoutParams(layoutParams);
                al.cardforphonenumberinlogin.setCardElevation(20f);

                return false;
            }
        });

        al.Logininl.setOnClickListener(new View.OnClickListener() {
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
                    declare();
                }
                else{
                    Toast.makeText(getApplicationContext(), "This App Require Internet ", Toast.LENGTH_SHORT).show();
                }


            }
        });
        al.Loginforregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,Register.class));
            }
        });


        loginRequestViewModel=new LoginRequestViewModel(getApplicationContext(),this);
    }

    private void declare() {
        phones=phoneinl.getText().toString();

        if(phones!=null) {
            getPreferenceManager().setPrefPhoneNum(phones);
            loginRequestViewModel.setPhone(phones);
            loginRequestViewModel.setCountry_code("91");
            loginRequestViewModel.generateLoginRequest();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Enter Correct Number", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void ShowErrorMessage(MessageViewType messageViewType, String errorMessage) {

    }

    @Override
    public void ShowErrorMessage(MessageViewType messageViewType, ViewType viewType, String errorMessage) {

    }

    @Override
    public void generateLoginProcessed(String Token, String Otp) {
        this.otp=Otp;
        this.token=Token;
        move();
    }

    private void move() {
        if(otp!=null&&token!=null) {
            Intent intent=new Intent(Login.this,PhoneNumVerfication.class);
            intent.putExtra("otp",otp);
            intent.putExtra("token",token);
            intent.putExtra("phone",phones);
            intent.putExtra("activity","Login");
            startActivity(intent);
            getPreferenceManager().setPrefToken(token);
            finish();
        }
    }

    @Override
    public void onFailure(ErrorBody errorBody, int statusCode) {}

    public PreferenceManager getPreferenceManager() {
        if (preferenceManager == null) {
            preferenceManager = PreferenceManager.getInstance();
            preferenceManager.initialize(getApplicationContext());
        }
        return preferenceManager;
    }
}