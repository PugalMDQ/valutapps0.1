package com.MDQ.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowId;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.MDQ.myapplication.databinding.ActivityPhoneNumVerficationBinding;
import com.MDQ.myapplication.enums.MessageViewType;
import com.MDQ.myapplication.enums.ViewType;
import com.MDQ.myapplication.interfaces.viewresponceinterface.AuthenticationResponseInterface;
import com.MDQ.myapplication.interfaces.viewresponceinterface.OtpResponseInterface;
import com.MDQ.myapplication.interfaces.viewresponceinterface.RegisterSuccessResponseInterface;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.viewmodel.AuthenticationRequestViewModel;
import com.MDQ.myapplication.viewmodel.OtpRequestViewModel;
import com.MDQ.myapplication.viewmodel.RegisterRequestViewModel;
import com.MDQ.myapplication.viewmodel.RegisterSuccessRequestViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class PhoneNumVerfication extends AppCompatActivity implements RegisterSuccessResponseInterface , OtpResponseInterface {
    ActivityPhoneNumVerficationBinding ap;
    String token, otp,phones,activities;

    String enteredotp;

    RegisterSuccessRequestViewModel registerSuccessRequestViewModel;

    OtpRequestViewModel otpRequestViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ap = ActivityPhoneNumVerficationBinding.inflate(getLayoutInflater());
        setContentView(ap.getRoot());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }


        //getting token and otp
        Intent intent = getIntent();
        token = intent.getStringExtra("token");
        otp = intent.getStringExtra("otp");
        phones=intent.getStringExtra("phone");
        activities=intent.getStringExtra("activity");

        if(phones!=null){
            ap.num.setText("+91 "+phones);
        }
        ap.Resend.setOnClickListener(new View.OnClickListener() {
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
                    setOtpDeclare();
                }
                else{
                    Toast.makeText(getApplicationContext(), "This App Require Internet", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ap.editone.requestFocus();


        ap.editone.setRawInputType(InputType.TYPE_NULL);
        ap.editone.setFocusable(true);
        ap.edittwo.setRawInputType(InputType.TYPE_NULL);
        ap.edittwo.setFocusable(true);
        ap.editthree.setRawInputType(InputType.TYPE_NULL);
        ap.editthree.setFocusable(true);
        ap.editfour.setRawInputType(InputType.TYPE_NULL);
        ap.editfour.setFocusable(true);
        ap.editfive.setRawInputType(InputType.TYPE_NULL);
        ap.editfive.setFocusable(true);
        ap.editsix.setRawInputType(InputType.TYPE_NULL);
        ap.editsix.setFocusable(true);


        ap.editone.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (ap.editone.length() == 0) {
                    ap.editone.setText("w");
                    ap.editone.setText("");
                    ap.linearOne.setBackgroundColor(getResources().getColor(R.color.blue));
                }
                return false;
            }
        });
        ap.edittwo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (ap.edittwo.length() == 0) {
                    ap.edittwo.setText("");
                    ap.linearTwo.setBackgroundColor(getResources().getColor(R.color.blue));
                }
                return false;
            }
        });
        ap.editthree.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (ap.editthree.length() == 0) {
                    ap.editthree.setText("");
                    ap.linearThree.setBackgroundColor(getResources().getColor(R.color.blue));
                }
                return false;
            }
        });
        ap.editfour.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (ap.editfour.length() == 0) {
                    ap.editfour.setText("");
                    ap.linearFour.setBackgroundColor(getResources().getColor(R.color.blue));
                }
                return false;
            }
        });
        ap.editfive.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (ap.editfive.length() == 0) {
                    ap.editfive.setText("");
                    ap.linearFive.setBackgroundColor(getResources().getColor(R.color.blue));
                }
                return false;
            }
        });
        ap.editsix.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (ap.editsix.length() == 0) {
                    ap.editsix.setText("");
                    ap.linearSix.setBackgroundColor(getResources().getColor(R.color.blue));
                }
                return false;
            }
        });


        ap.editone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (ap.editone.getText().length() == 1) {
                    ap.edittwo.requestFocus();
                    ap.editone.setTextColor(getResources().getColor(R.color.blue));
                    ap.linearOne.setBackgroundColor(getResources().getColor(R.color.blue));
                } else {
                    ap.editone.setTextColor(getResources().getColor(R.color.white));
                    ap.linearOne.setBackgroundColor(getResources().getColor(R.color.white));

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ap.edittwo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (ap.edittwo.getText().length() == 1) {
                    ap.editthree.requestFocus();
                    ap.edittwo.setTextColor(getResources().getColor(R.color.blue));
                    ap.linearTwo.setBackgroundColor(getResources().getColor(R.color.blue));
                } else {
                    ap.editone.requestFocus();
                    ap.edittwo.setTextColor(getResources().getColor(R.color.white));
                    ap.linearTwo.setBackgroundColor(getResources().getColor(R.color.white));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ap.editthree.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (ap.editthree.getText().length() == 1) {
                    ap.editfour.requestFocus();
                    ap.editthree.setTextColor(getResources().getColor(R.color.blue));
                    ap.linearThree.setBackgroundColor(getResources().getColor(R.color.blue));
                } else {
                    ap.edittwo.requestFocus();
                    ap.editthree.setTextColor(getResources().getColor(R.color.white));
                    ap.linearThree.setBackgroundColor(getResources().getColor(R.color.white));
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ap.editfour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (ap.editfour.getText().length() == 1) {
                    ap.editfive.requestFocus();
                    ap.editfour.setTextColor(getResources().getColor(R.color.blue));
                    ap.linearFour.setBackgroundColor(getResources().getColor(R.color.blue));

                } else {
                    ap.editthree.requestFocus();
                    ap.editfour.setTextColor(getResources().getColor(R.color.white));
                    ap.linearFour.setBackgroundColor(getResources().getColor(R.color.white));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ap.editfive.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (ap.editfive.getText().length() == 1) {
                    ap.editsix.requestFocus();
                    ap.editfive.setTextColor(getResources().getColor(R.color.blue));
                    ap.linearFive.setBackgroundColor(getResources().getColor(R.color.blue));

                } else {
                    ap.editfour.requestFocus();
                    ap.editfive.setTextColor(getResources().getColor(R.color.white));
                    ap.linearFive.setBackgroundColor(getResources().getColor(R.color.white));

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ap.editsix.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (ap.editsix.getText().length() == 1) {
                    ap.editsix.setTextColor(getResources().getColor(R.color.blue));
                    ap.linearSix.setBackgroundColor(getResources().getColor(R.color.blue));

                } else {
                    ap.editfive.requestFocus();
                    ap.editsix.setTextColor(getResources().getColor(R.color.white));
                    ap.linearSix.setBackgroundColor(getResources().getColor(R.color.white));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //for numbers
        ap.one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ap.editone.hasFocus()) {
                    ap.editone.setText("1");
                } else if (ap.edittwo.hasFocus()) {
                    ap.edittwo.setText("1");
                } else if (ap.editthree.hasFocus()) {
                    ap.editthree.setText("1");
                } else if (ap.editfour.hasFocus()) {
                    ap.editfour.setText("1");
                } else if (ap.editfive.hasFocus()) {
                    ap.editfive.setText("1");
                } else if (ap.editsix.hasFocus()) {
                    ap.editsix.setText("1");
                }
            }
        });
        ap.two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ap.editone.hasFocus()) {
                    ap.editone.setText("2");
                } else if (ap.edittwo.hasFocus()) {
                    ap.edittwo.setText("2");
                } else if (ap.editthree.hasFocus()) {
                    ap.editthree.setText("2");
                } else if (ap.editfour.hasFocus()) {
                    ap.editfour.setText("2");
                } else if (ap.editfive.hasFocus()) {
                    ap.editfive.setText("2");
                } else if (ap.editsix.hasFocus()) {
                    ap.editsix.setText("2");
                }
            }
        });
        ap.three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ap.editone.hasFocus()) {
                    ap.editone.setText("3");
                } else if (ap.edittwo.hasFocus()) {
                    ap.edittwo.setText("3");
                } else if (ap.editthree.hasFocus()) {
                    ap.editthree.setText("3");
                } else if (ap.editfour.hasFocus()) {
                    ap.editfour.setText("3");
                } else if (ap.editfive.hasFocus()) {
                    ap.editfive.setText("3");
                } else if (ap.editsix.hasFocus()) {
                    ap.editsix.setText("3");
                }
            }
        });
        ap.four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ap.editone.hasFocus()) {
                    ap.editone.setText("4");
                } else if (ap.edittwo.hasFocus()) {
                    ap.edittwo.setText("4");
                } else if (ap.editthree.hasFocus()) {
                    ap.editthree.setText("4");
                } else if (ap.editfour.hasFocus()) {
                    ap.editfour.setText("4");
                } else if (ap.editfive.hasFocus()) {
                    ap.editfive.setText("4");
                } else if (ap.editsix.hasFocus()) {
                    ap.editsix.setText("4");
                }
            }
        });
        ap.five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ap.editone.hasFocus()) {
                    ap.editone.setText("5");
                } else if (ap.edittwo.hasFocus()) {
                    ap.edittwo.setText("5");
                } else if (ap.editthree.hasFocus()) {
                    ap.editthree.setText("5");
                } else if (ap.editfour.hasFocus()) {
                    ap.editfour.setText("5");
                } else if (ap.editfive.hasFocus()) {
                    ap.editfive.setText("5");
                } else if (ap.editsix.hasFocus()) {
                    ap.editsix.setText("5");
                }
            }
        });
        ap.six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ap.editone.hasFocus()) {
                    ap.editone.setText("6");
                } else if (ap.edittwo.hasFocus()) {
                    ap.edittwo.setText("6");
                } else if (ap.editthree.hasFocus()) {
                    ap.editthree.setText("6");
                } else if (ap.editfour.hasFocus()) {
                    ap.editfour.setText("6");
                } else if (ap.editfive.hasFocus()) {
                    ap.editfive.setText("6");
                } else if (ap.editsix.hasFocus()) {
                    ap.editsix.setText("6");
                }
            }
        });
        ap.seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ap.editone.hasFocus()) {
                    ap.editone.setText("7");
                } else if (ap.edittwo.hasFocus()) {
                    ap.edittwo.setText("7");
                } else if (ap.editthree.hasFocus()) {
                    ap.editthree.setText("7");
                } else if (ap.editfour.hasFocus()) {
                    ap.editfour.setText("7");
                } else if (ap.editfive.hasFocus()) {
                    ap.editfive.setText("7");
                } else if (ap.editsix.hasFocus()) {
                    ap.editsix.setText("7");
                }
            }
        });
        ap.eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ap.editone.hasFocus()) {
                    ap.editone.setText("8");
                } else if (ap.edittwo.hasFocus()) {
                    ap.edittwo.setText("8");
                } else if (ap.editthree.hasFocus()) {
                    ap.editthree.setText("8");
                } else if (ap.editfour.hasFocus()) {
                    ap.editfour.setText("8");
                } else if (ap.editfive.hasFocus()) {
                    ap.editfive.setText("8");
                } else if (ap.editsix.hasFocus()) {
                    ap.editsix.setText("8");
                }
            }
        });
        ap.nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ap.editone.hasFocus()) {
                    ap.editone.setText("9");
                } else if (ap.edittwo.hasFocus()) {
                    ap.edittwo.setText("9");
                } else if (ap.editthree.hasFocus()) {
                    ap.editthree.setText("9");
                } else if (ap.editfour.hasFocus()) {
                    ap.editfour.setText("9");
                } else if (ap.editfive.hasFocus()) {
                    ap.editfive.setText("9");
                } else if (ap.editsix.hasFocus()) {
                    ap.editsix.setText("9");
                }
            }
        });
        ap.zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ap.editone.hasFocus()) {
                    ap.editone.setText("0");
                } else if (ap.edittwo.hasFocus()) {
                    ap.edittwo.setText("0");
                } else if (ap.editthree.hasFocus()) {
                    ap.editthree.setText("0");
                } else if (ap.editfour.hasFocus()) {
                    ap.editfour.setText("0");
                } else if (ap.editfive.hasFocus()) {
                    ap.editfive.setText("0");
                } else if (ap.editsix.hasFocus()) {
                    ap.editsix.setText("0");
                }
            }
        });

        //backspace
        ap.backspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ap.editone.hasFocus()) {
                    ap.editone.setText("");
                } else if (ap.edittwo.hasFocus()) {
                    if (ap.edittwo.length() == 1) {
                        ap.edittwo.setText("");
                        ap.editone.requestFocus();
                    } else {
                        ap.editone.setText("");
                        ap.editone.requestFocus();
                    }
                } else if (ap.editthree.hasFocus()) {
                    if (ap.editthree.length() == 1) {
                        ap.editthree.setText("");
                        ap.edittwo.requestFocus();
                    } else {
                        ap.edittwo.setText("");
                        ap.edittwo.requestFocus();
                    }
                } else if (ap.editfour.hasFocus()) {
                    if (ap.editfour.length() == 1) {
                        ap.editfour.setText("");
                        ap.editthree.requestFocus();
                    } else {
                        ap.editthree.setText("");
                        ap.editthree.requestFocus();
                    }
                } else if (ap.editfive.hasFocus()) {
                    if (ap.editfive.length() == 1) {
                        ap.editfive.setText("");
                        ap.editfour.requestFocus();
                    } else {
                        ap.editfour.setText("");
                        ap.editfour.requestFocus();
                    }
                } else if (ap.editsix.hasFocus()) {
                    if (ap.editsix.length() == 1) {
                        ap.editsix.setText("");
                        ap.editfive.requestFocus();
                    } else {
                        ap.editfive.setText("");
                        ap.editfive.requestFocus();
                    }
                }
            }
        });

        //done
        ap.Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enteredotp = "" + ap.editone.getText() + ap.edittwo.getText()
                        + ap.editthree.getText() + ap.editfour.getText() + ap.editfive.getText()
                        + ap.editsix.getText();
                String enterotpmain = enteredotp.trim();

                if(enterotpmain!=null) {

                    Log.i("enteredotp", enterotpmain);
                    if (otp.equals(enteredotp)) {

                        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext()
                                .getSystemService(Context.CONNECTIVITY_SERVICE);
                        if ((connectivityManager
                                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null && connectivityManager
                                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)
                                || (connectivityManager
                                .getNetworkInfo(ConnectivityManager.TYPE_WIFI) != null && connectivityManager
                                .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                                .getState() == NetworkInfo.State.CONNECTED)) {
                            setdeclare();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "This App Require Internet ", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Enter Correct OTP", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Enter OTP", Toast.LENGTH_SHORT).show();
                   // startActivity(new Intent(PhoneNumVerfication.this,verificationsuccess.class));
                }
            }


        });
        //back
        ap.backc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        registerSuccessRequestViewModel=new RegisterSuccessRequestViewModel(getApplicationContext(),this);
        otpRequestViewModel=new OtpRequestViewModel(getApplicationContext(),this);
    }

    private void setOtpDeclare() {
        otpRequestViewModel.setPhone(phones);
        otpRequestViewModel.setCountry_code("91");
        otpRequestViewModel.generateOtpRequest();
    }

    private void setdeclare() {
        registerSuccessRequestViewModel.setToken(token);
        registerSuccessRequestViewModel.generateRegisterSuccessRequest();
    }

    @Override
    public void generateRegisterSuccessProcessed(String Token, String msg) {

        if(Token!=null){
            if(activities.equals("Register")) {
                Intent intent1 = new Intent(PhoneNumVerfication.this, verificationsuccess.class);
                intent1.putExtra("token", token);
                startActivity(intent1);

            }
            else if(activities.equals("Login")){
                Intent intent2=new Intent(PhoneNumVerfication.this,enteryourmpin.class);
                intent2.putExtra("token",token);
                startActivity(intent2);
            }
            }
    }

    @Override
    public void generateOtpProcessed(String Otp) {

        if(Otp!=null)
        {
            otp=Otp;
        }
    }

    @Override
    public void onFailure(ErrorBody errorBody, int statusCode) {

    }

    @Override
    public void ShowErrorMessage(MessageViewType messageViewType, String errorMessage) {

    }

    @Override
    public void ShowErrorMessage(MessageViewType messageViewType, ViewType viewType, String errorMessage) {

    }
}