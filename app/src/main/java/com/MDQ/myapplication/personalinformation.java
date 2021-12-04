package com.MDQ.myapplication;

import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.MDQ.myapplication.enums.MessageViewType;
import com.MDQ.myapplication.enums.ViewType;
import com.MDQ.myapplication.interfaces.viewinterface.GetUserRequestInterface;
import com.MDQ.myapplication.interfaces.viewresponceinterface.GetUserResponseInterface;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.utils.PreferenceManager;
import com.MDQ.myapplication.viewmodel.GetUserViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.lang.reflect.Field;

public class personalinformation extends AppCompatActivity implements GetUserResponseInterface {
    BottomSheetDialog bottomSheetDialog;
    LinearLayout transaction;
    CardView cardfore, cardforu, cardforp;
    EditText email, phone, user;
    PreferenceManager preferenceManager;
    GetUserViewModel getUserViewModel;
    String token;
    ImageView backFor;
    LinearLayout linearhome,linearvalut,linearnotification,linearprofile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalinformation);
        bottom();
        getUserViewModel=new GetUserViewModel(getApplicationContext(),this);
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
            Toast.makeText(getApplicationContext(), "This App Require Internet", Toast.LENGTH_SHORT).show();
        }

    }
    private void declare() {

        token=getPreferenceManager().getPrefToken();
        if(token!=null) {
            getUserViewModel.setToken(token);
            getUserViewModel.generateGetUserRequest();
        }
    }


    private void bottom() {

        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.downforpersonalinformation);
        bottomSheetDialog.setCanceledOnTouchOutside(false);

        backFor=bottomSheetDialog.findViewById(R.id.backFor);
        linearhome=bottomSheetDialog.findViewById(R.id.linearhome);
        linearvalut=bottomSheetDialog.findViewById(R.id.linearTransaction);
        linearnotification=bottomSheetDialog.findViewById(R.id.linearnotification);
        linearprofile=bottomSheetDialog.findViewById(R.id.linearprofile);

        backFor.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_keyboard_arrow_left_24));
        setclick();
        cardforu = bottomSheetDialog.findViewById(R.id.cardforusername);

        cardfore = bottomSheetDialog.findViewById(R.id.cardforemail);

        cardforp = bottomSheetDialog.findViewById(R.id.cardforphonenumber);


        user = bottomSheetDialog.findViewById(R.id.username);
        email = bottomSheetDialog.findViewById(R.id.email);
        phone = bottomSheetDialog.findViewById(R.id.Phonenumber);


        ViewGroup.LayoutParams layoutParams = cardforu.getLayoutParams();
        ViewGroup.LayoutParams layoutParams1 = cardfore.getLayoutParams();
        ViewGroup.LayoutParams layoutParams2 = cardforp.getLayoutParams();


        bottomSheetDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    Log.i("keycodeback", "backed");
                    bottomSheetDialog.dismiss();
                    startActivity(new Intent(personalinformation.this, profile.class));
                }
                return true;
            }
        });

        try {
            Field behaviorField = bottomSheetDialog.getClass().getDeclaredField("behavior");
            behaviorField.setAccessible(true);
            final BottomSheetBehavior behavior = (BottomSheetBehavior) behaviorField.get(bottomSheetDialog);
            behavior.setState(STATE_EXPANDED);

            behavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                        startActivity(new Intent(personalinformation.this, balancehome.class));
                        finish();
                    }
                }
                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {

                }
            });

            bottomSheetDialog.show();
        } catch (Exception e) {

        }
        user.setOnTouchListener(new View.OnTouchListener() {
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

        bottomSheetDialog.show();
    }

    private void setclick() {
        linearnotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Notification.class));
            }
        });

        linearhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(personalinformation.this,balancehome.class));

            }
        });
        linearvalut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(personalinformation.this,cardslist.class));

            }
        });
        backFor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(personalinformation.this,profile.class));
            }
        });
        linearprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(personalinformation.this,profile.class));
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("onresume", "closed");

    }

    @Override
    public void generateGetUserProcessed(String Token, String User_name, String Email, String Phone,String biometrics_status) {

      if(Token!=null) {
          phone.setText(Phone);
          email.setText(Email);
          user.setText(User_name);
           }
    }
    @Override
    public void onFailure(ErrorBody errorBody, int statusCode) {

    }

    @Override
    public void ShowErrorMessage(MessageViewType messageViewType, String errorMessage) {

        Toast.makeText(getApplicationContext(), ""+errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ShowErrorMessage(MessageViewType messageViewType, ViewType viewType, String errorMessage) {
        Toast.makeText(getApplicationContext(), ""+errorMessage, Toast.LENGTH_SHORT).show();
    }
    public PreferenceManager getPreferenceManager() {
        if (preferenceManager == null) {
            preferenceManager = PreferenceManager.getInstance();
            preferenceManager.initialize(getApplicationContext());
        }
        return preferenceManager;
    }

}