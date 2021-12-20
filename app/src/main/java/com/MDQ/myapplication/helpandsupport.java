package com.MDQ.myapplication;

import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.MDQ.myapplication.enums.MessageViewType;
import com.MDQ.myapplication.enums.ViewType;
import com.MDQ.myapplication.interfaces.viewresponceinterface.GetUserResponseInterface;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.utils.PreferenceManager;
import com.MDQ.myapplication.viewmodel.GetUserViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.lang.reflect.Field;

public class helpandsupport extends AppCompatActivity implements GetUserResponseInterface {

    BottomSheetDialog  bottomSheetDialog;
    LinearLayout transaction;
    TextView email;
    LinearLayout linearhome,linearvalut,linearnotification,linearprofile;
    PreferenceManager preferenceManager;
    ImageView backFor;
    GetUserViewModel getUserViewModel;
    ImageView addtransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helpandsupport);
        //making status bar color as transparent
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        getUserViewModel=new GetUserViewModel(getApplicationContext(),this);
        getUserViewModel.setToken(getPreferenceManager().getPrefToken());
        getUserViewModel.generateGetUserRequest();
        bottom();
    }

    private void bottom() {

        bottomSheetDialog=new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.downhelpandsupport);
        bottomSheetDialog.setCanceledOnTouchOutside(false);

        backFor=bottomSheetDialog.findViewById(R.id.backFor);
        email=bottomSheetDialog.findViewById(R.id.emails);
        linearhome=bottomSheetDialog.findViewById(R.id.linearhome);
        linearvalut=bottomSheetDialog.findViewById(R.id.linearTransaction);
        linearnotification=bottomSheetDialog.findViewById(R.id.linearnotification);
        linearprofile=bottomSheetDialog.findViewById(R.id.linearprofile);
        addtransaction=bottomSheetDialog.findViewById(R.id.addtransaction);

        setclick();


        bottomSheetDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if(keyCode==KeyEvent.KEYCODE_BACK){
                    Log.i("keycodeback","backed");
                    bottomSheetDialog.dismiss();
                    onBackPressed();
                }
                return true;
            }
        });

        try{
            Field behaviorField = bottomSheetDialog.getClass().getDeclaredField("behavior");
            behaviorField.setAccessible(true);
            final BottomSheetBehavior behavior = (BottomSheetBehavior) behaviorField.get(bottomSheetDialog);
            behavior.setState(STATE_EXPANDED);

            behavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    if(newState==BottomSheetBehavior.STATE_HIDDEN){
                        startActivity(new Intent(helpandsupport.this,balancehome.class));
                        finish();
                }

                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {

                }
            });

            bottomSheetDialog.show();
        }
        catch (Exception e){

        }
        bottomSheetDialog.show();
    }



    private void setclick() {


        addtransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AddTransactionscreen.class));
            }
        });
        linearnotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Notification.class));
            }
        });
        linearhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(helpandsupport.this,balancehome.class));

            }
        });
        backFor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(helpandsupport.this,profile.class));
            }
        });
        linearvalut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(helpandsupport.this,cardslist.class));

            }
        });
        linearprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(helpandsupport.this,profile.class));
            }
        });
    }
    public PreferenceManager getPreferenceManager() {
        if (preferenceManager == null) {
            preferenceManager = PreferenceManager.getInstance();
            preferenceManager.initialize(getApplicationContext());
        }
        return preferenceManager;
    }

    @Override
    public void ShowErrorMessage(MessageViewType messageViewType, String errorMessage) {

    }

    @Override
    public void ShowErrorMessage(MessageViewType messageViewType, ViewType viewType, String errorMessage) {

    }

    @Override
    public void generateGetUserProcessed(String Token, String User_name, String Email, String Phone, String biometrics_status) {

        if(Email!=null){
            email.setText(Email);
        }
    }

    @Override
    public void onFailure(ErrorBody errorBody, int statusCode) {

    }
}