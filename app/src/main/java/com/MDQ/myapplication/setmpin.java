package com.MDQ.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
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

import com.MDQ.myapplication.databinding.ActivitySetmpinBinding;
import com.MDQ.myapplication.enums.MessageViewType;
import com.MDQ.myapplication.enums.ViewType;
import com.MDQ.myapplication.interfaces.viewresponceinterface.MpinResponseInterface;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.utils.PreferenceManager;
import com.MDQ.myapplication.viewmodel.MpinRequestViewModel;

public class setmpin extends AppCompatActivity implements MpinResponseInterface {
    String mpins;
    String token;
    ActivitySetmpinBinding ap;
    MpinRequestViewModel mpinRequestViewModel;
    PreferenceManager preferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initialize with viewModel
        ap =ActivitySetmpinBinding.inflate(getLayoutInflater());
        setContentView(ap.getRoot());

        //setting font for textView
        Typeface tf=Typeface.createFromAsset(getAssets(),"fonts/ZillaSlab-Bold.ttf");
        ap.set.setTypeface(tf);

        //getting token from previous screen
        Intent intents=getIntent();
        token=intents.getStringExtra("token");

        //set edit text input type as null
        ap.editthree.requestFocus();
        ap.editthree.setRawInputType(InputType.TYPE_NULL);
        ap.editthree.setFocusable(true);
        ap.editfour.setRawInputType(InputType.TYPE_NULL);
        ap.editfour.setFocusable(true);
        ap.editfive.setRawInputType(InputType.TYPE_NULL);
        ap.editfive.setFocusable(true);
        ap.editsix.setRawInputType(InputType.TYPE_NULL);
        ap.editsix.setFocusable(true);

        //setting touchListener for edittext when its touched it will change to blue color
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

        //adding textChangeListener for edit text when the first edittext filled it will focus on next edit text
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

        //set for numbers textView the num have clicked it will show on edit text
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

        //backspace imageView when the view clicked it will delete the lastly typed number
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


        mpinRequestViewModel=new MpinRequestViewModel(getApplicationContext(),this);


        ap.Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mpins = ""+ ap.editthree.getText() + ap.editfour.getText() + ap.editfive.getText()
                        + ap.editsix.getText();

                //Checking for internet connection if available call declare methode else toast the error message
                if (mpins!=null) {

                    ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext()
                            .getSystemService(Context.CONNECTIVITY_SERVICE);
                    if ((connectivityManager
                            .getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null && connectivityManager
                            .getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)
                            || (connectivityManager
                            .getNetworkInfo(ConnectivityManager.TYPE_WIFI) != null && connectivityManager
                            .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                            .getState() == NetworkInfo.State.CONNECTED)) {
                        getPreferenceManager().setPrefMpin(mpins);
                        declare();


                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "This App Require Internet", Toast.LENGTH_SHORT).show();
                    }

                   }
                else{
                    Toast.makeText(getApplicationContext(), "Set mPin", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //setting request item for setMpin api
    private void declare() {
        mpinRequestViewModel.setToken(token);
        mpinRequestViewModel.setMpin(mpins);
        mpinRequestViewModel.generateMpinRequest();
    }

    /**
     * @param Msg
     * @breif getting response from set mpin api
     */
    @Override
    public void generateMpinProcessed(String Msg) {
        String mm=" MPIN updated successfuly";
        if(Msg.trim().equals(mm.trim())){
            startActivity(new Intent(setmpin.this, balancehome.class));
            finish();
        }
    }

    @Override
    public void onFailure(ErrorBody errorBody, int statusCode) {
        //do nothing
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