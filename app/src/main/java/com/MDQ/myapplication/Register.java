package com.MDQ.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.MDQ.myapplication.databinding.ActivityRegisterBinding;
import com.MDQ.myapplication.enums.MessageViewType;
import com.MDQ.myapplication.enums.ViewType;
import com.MDQ.myapplication.interfaces.viewresponceinterface.RegisterResponseInterface;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.utils.PreferenceManager;
import com.MDQ.myapplication.viewmodel.RegisterRequestViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity implements RegisterResponseInterface {
    ActivityRegisterBinding ar;

    CardView cardforu,cardfore,cardforp;
    EditText user,email,phone;

    String tokens,otp,users,phones,emails;

    PreferenceManager preferenceManager;

    RegisterRequestViewModel registerRequestViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ar=ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(ar.getRoot());

        //initialize with widgets
        cardforu=findViewById(R.id.cardforusername);
        cardfore=findViewById(R.id.cardforemail);
        cardforp=findViewById(R.id.cardforphonenumber);
        user=findViewById(R.id.username);
        phone=findViewById(R.id.Phonenumber);
        email=findViewById(R.id.email);

        //initialize registerRequestViewModel
        registerRequestViewModel=new RegisterRequestViewModel(getApplicationContext(),Register.this);

        //For transparent the parent color
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        ar.Loginforregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this,Login.class));
            }
        });

        touchListener();

        ar.getstarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checking internet connection ;
                ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext()
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                if ((connectivityManager
                        .getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null && connectivityManager
                        .getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)
                        || (connectivityManager
                        .getNetworkInfo(ConnectivityManager.TYPE_WIFI) != null && connectivityManager
                        .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                        .getState() == NetworkInfo.State.CONNECTED)) {
                    emails = email.getText().toString().trim();
                    phones = phone.getText().toString();
                    users = user.getText().toString();

                    Pattern mPattern = Pattern.compile("^([1-9][0-9]{0,2})?(\\.[0-9]?)?$");
                    Matcher matcher = mPattern.matcher(user.getText().toString());

                    if(user.getText().toString().contains(" ")){
                        Toast.makeText(getApplicationContext(), "Enter the name without space", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if(!Patterns.EMAIL_ADDRESS.matcher(emails).matches()){
                            Toast.makeText(getApplicationContext(), "Enter valid email", Toast.LENGTH_SHORT).show();
                        }else {
                            if(phones.length()<10 ) {
                                Toast.makeText(getApplicationContext(), "Enter valid mobile number", Toast.LENGTH_SHORT).show();
                            }else{
                                if(!phones.startsWith("0") && !phones.startsWith("1") && !phones.startsWith("2") && !phones.startsWith("3") && !phones.startsWith("4") && !phones.startsWith("5"))
                                {
                                    if(user.getText().toString().length()>3) {
                                        if (emails != null && phones != null && users != null) {
                                            getPreferenceManager().setPrefPhoneNum(phones);
                                            getPreferenceManager().setPrefEmail(emails);
                                            setDecler();

                                        }
                                    }else {
                                        Toast.makeText(getApplicationContext(),"Enter User Name At Least Five letters ", Toast.LENGTH_SHORT).show();
                                    }
                                }else {
                                    Toast.makeText(getApplicationContext(), "Enter valid mobile number", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "This App Require Internet", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    /**
     * @breif getting touchListener for changing cardElevation
     */
    @SuppressLint("ClickableViewAccessibility")
    private void touchListener() {

        ViewGroup.LayoutParams layoutParams=cardforu.getLayoutParams();
        ViewGroup.LayoutParams layoutParams1=cardfore.getLayoutParams();
        ViewGroup.LayoutParams layoutParams2=cardforp.getLayoutParams();


        user.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                cardfore.setLayoutParams(layoutParams1);
                cardfore.setCardElevation(0f);
                cardforp.setLayoutParams(layoutParams2);
                cardforp.setCardElevation(0f);
                cardforu.setLayoutParams(layoutParams);
                cardforu.setCardElevation(20f);
                return false;

            }
        });
        email.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                cardforu.setLayoutParams(layoutParams);
                cardforu.setCardElevation(0f);
                cardforp.setLayoutParams(layoutParams2);
                cardforp.setCardElevation(0f);

                cardfore.setLayoutParams(layoutParams1);
                cardfore.setCardElevation(20f);

                return false;
            }
        });
        phone.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                cardforu.setLayoutParams(layoutParams);
                cardforu.setCardElevation(0f);
                cardfore.setLayoutParams(layoutParams1);
                cardfore.setCardElevation(0f);

                cardforp.setLayoutParams(layoutParams2);
                cardforp.setCardElevation(20f);

                return false;
            }
        });
        cardfore.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                cardforu.setLayoutParams(layoutParams);
                cardforu.setCardElevation(0f);
                cardforp.setLayoutParams(layoutParams2);
                cardforp.setCardElevation(0f);

                cardfore.setLayoutParams(layoutParams1);
                cardfore.setCardElevation(20f);

                return false;
            }
        });
        cardforu.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                cardfore.setLayoutParams(layoutParams1);
                cardfore.setCardElevation(0f);
                cardforp.setLayoutParams(layoutParams2);
                cardforp.setCardElevation(0f);

                cardforu.setLayoutParams(layoutParams);
                cardforu.setCardElevation(20f);

                return false;
            }
        });
        cardforp.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                cardforu.setLayoutParams(layoutParams);
                cardforu.setCardElevation(0f);
                cardfore.setLayoutParams(layoutParams1);
                cardfore.setCardElevation(0f);

                cardforp.setLayoutParams(layoutParams2);
                cardforp.setCardElevation(20f);

                return false;
            }
        });

    }


    /**
     * @breif set api request items
     */
    private void setDecler() {
        registerRequestViewModel.setUser_name(users);
        registerRequestViewModel.setEmail(emails);
        registerRequestViewModel.setPhone(phones);
        registerRequestViewModel.setCountry_code("91");
        registerRequestViewModel.generateRegisterRequest();

    }

    /**
     * @breif if all field filled navigate to
     */
    private void Move() {
        if(emails!=null&&phones!=null&&users!=null&&tokens!=null) {
            Intent intent = new Intent(Register.this, PhoneNumVerfication.class);
            intent.putExtra("token", tokens);
            intent.putExtra("otp", otp);
            intent.putExtra("phone", phones);
            intent.putExtra("activity","Register");
            getPreferenceManager().setPrefEmail(emails);
            getPreferenceManager().setPrefPhoneNum(phones);
            startActivity(intent);
        }
        else if(emails==null&&phones==null&&users==null)
        {
            Toast.makeText(getApplicationContext(), "Enter All Fields", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void ShowErrorMessage(MessageViewType messageViewType, String errorMessage) {
        // do nothing

    }

    @Override
    public void ShowErrorMessage(MessageViewType messageViewType, ViewType viewType, String errorMessage) {
        // do nothing

    }

    /**
     * @param Token
     * @param otp
     * @breif getting response for register api
     */
    @Override
    public void generateRegisterProcessed(String Token,String otp) {
        this.tokens=Token;
        this.otp=otp;

        Move();
    }
    @Override
    public void onFailure(ErrorBody errorBody, int statusCode) {

        // do nothing
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}