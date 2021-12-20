package com.MDQ.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.MDQ.myapplication.databinding.ActivityNewmpinBinding;
import com.MDQ.myapplication.enums.MessageViewType;
import com.MDQ.myapplication.enums.ViewType;
import com.MDQ.myapplication.interfaces.viewresponceinterface.MpinResponseInterface;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.utils.PreferenceManager;
import com.MDQ.myapplication.viewmodel.MpinRequestViewModel;
import com.MDQ.myapplication.viewmodel.MpinValidationViewModel;

public class newmpin extends AppCompatActivity implements MpinResponseInterface {

    ActivityNewmpinBinding ap;
    PreferenceManager preferenceManager;
    MpinRequestViewModel mpinRequestViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ap =ActivityNewmpinBinding.inflate(getLayoutInflater());
        setContentView(ap.getRoot());
        mpinRequestViewModel=new MpinRequestViewModel(getApplicationContext(),this);

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

                    ValidatePassword();
                }
                else{
                    Toast.makeText(getApplicationContext(), "This App Require Internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ap.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(newmpin.this,Login.class));
            }
        });
        Typeface tf=Typeface.createFromAsset(getAssets(),"fonts/ZillaSlab-Bold.ttf");

        ap.editthree.requestFocus();


        ap.editthree.setRawInputType(InputType.TYPE_NULL);
        ap.editthree.setFocusable(true);
        ap.editfour.setRawInputType(InputType.TYPE_NULL);
        ap.editfour.setFocusable(true);
        ap.editfive.setRawInputType(InputType.TYPE_NULL);
        ap.editfive.setFocusable(true);
        ap.editsix.setRawInputType(InputType.TYPE_NULL);
        ap.editsix.setFocusable(true);



        ap.editthree.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(ap.editthree.length()==0){
                    ap.editthree.setText("");
                    ap.linearThree.setBackgroundColor(getResources().getColor(R.color.blue));}
                return false;
            }
        });
        ap.editfour.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(ap.editfour.length()==0){
                    ap.editfour.setText("");
                    ap.linearFour.setBackgroundColor(getResources().getColor(R.color.blue));}
                return false;
            }
        });
        ap.editfive.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(ap.editfive.length()==0){
                    ap.editfive.setText("");
                    ap.linearFive.setBackgroundColor(getResources().getColor(R.color.blue));}
                return false;
            }
        });
        ap.editsix.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(ap.editsix.length()==0){
                    ap.editsix.setText("");
                    ap.linearSix.setBackgroundColor(getResources().getColor(R.color.blue));}
                return false;
            }
        });




        ap.editthree.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (ap.editthree.getText().length() == 1) {
                    ap.editthree.setTypeface(tf);
                    ap.editfour.requestFocus();
                    ap.editthree.setTextColor(getResources().getColor(R.color.blue));
                    ap.linearThree.setBackgroundColor(getResources().getColor(R.color.blue));
                } else {
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
                    ap.editfour.setTypeface(tf);
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
                    ap.editfive.setTypeface(tf);
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
                    ap.editsix.setTypeface(tf);
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
                if (ap.editthree.hasFocus()) {
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
                if (ap.editthree.hasFocus()) {
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
                if (ap.editthree.hasFocus()) {
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
                if (ap.editthree.hasFocus()) {
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
                if (ap.editthree.hasFocus()) {
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
                if (ap.editthree.hasFocus()) {
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
                if (ap.editthree.hasFocus()) {
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
                if (ap.editthree.hasFocus()) {
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
                if (ap.editthree.hasFocus()) {
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
                if (ap.editthree.hasFocus()) {
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
                if (ap.editthree.hasFocus()) {
                    if(ap.editthree.length()==1){
                        ap.editthree.setText("");
                    }
                } else if (ap.editfour.hasFocus()) {
                    if(ap.editfour.length()==1) {
                        ap.editfour.setText("");
                        ap.editthree.requestFocus();
                    } else {
                        ap.editthree.setText("");
                        ap.editthree.requestFocus();
                    }
                } else if (ap.editfive.hasFocus()) {
                    if(ap.editfive.length()==1){
                        ap.editfive.setText("");
                        ap.editfour.requestFocus();}
                    else {
                        ap.editfour.setText("");
                        ap.editfour.requestFocus();
                    }
                } else if (ap.editsix.hasFocus()) {
                    if(ap.editsix.length()==1){
                        ap.editsix.setText("");
                        ap.editfive.requestFocus();}
                    else {
                        ap.editfive.setText("");
                        ap.editfive.requestFocus();
                    }
                }
            }
        });

    }

    private void ValidatePassword() {
        String password = "" + ap.editthree.getText() + ap.editfour.getText() + ap.editfive.getText()
                + ap.editsix.getText();
        if(password.length()==4) {
            mpinRequestViewModel.setMpin(password);
            mpinRequestViewModel.setToken(getPreferenceManager().getPrefToken());
            mpinRequestViewModel.generateMpinRequest();
        }else
        {
            Toast.makeText(getApplicationContext(), "Enter password Correctly", Toast.LENGTH_SHORT).show();
        }
    }

    public PreferenceManager getPreferenceManager() {
        if (preferenceManager == null) {
            preferenceManager = PreferenceManager.getInstance();
            preferenceManager.initialize(getApplicationContext());
        }
        return preferenceManager;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),Login.class));
    }

    @Override
    public void ShowErrorMessage(MessageViewType messageViewType, String errorMessage) {

    }

    @Override
    public void ShowErrorMessage(MessageViewType messageViewType, ViewType viewType, String errorMessage) {

    }

    @Override
    public void generateMpinProcessed(String Msg) {
        String mm=" MPIN updated successfuly";
        if(Msg.trim().equals(mm.trim())) {
            Intent intent = new Intent(newmpin.this, verificationsuccess.class);
            intent.putExtra("token", getPreferenceManager().getPrefToken());
            intent.putExtra("screen", "new");
            startActivity(intent);
            finish();
        }
        }


    @Override
    public void onFailure(ErrorBody errorBody, int statusCode) {

    }
}