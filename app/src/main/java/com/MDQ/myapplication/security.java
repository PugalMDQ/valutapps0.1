package com.MDQ.myapplication;

import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.MDQ.myapplication.enums.MessageViewType;
import com.MDQ.myapplication.enums.ViewType;
import com.MDQ.myapplication.interfaces.viewresponceinterface.BioMetricsResponseInterface;
import com.MDQ.myapplication.interfaces.viewresponceinterface.BioMetricsValidationResponseInterface;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateBioMetricsResponseModel;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateBioMetricsValidationResponseModel;
import com.MDQ.myapplication.utils.PreferenceManager;
import com.MDQ.myapplication.viewmodel.BioMetricsValidationViewModel;
import com.MDQ.myapplication.viewmodel.BioMetricsViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.lang.reflect.Field;

public class security extends AppCompatActivity implements BioMetricsResponseInterface, BioMetricsValidationResponseInterface {
    BottomSheetDialog bottomSheetDialog;
    LinearLayout transaction;
    PreferenceManager preferenceManager;
    ImageView backFor,addtransaction;
    SwitchCompat switchCompat;
    LinearLayout linearhome,linearvalut,linearnotification,linearprofile;
    BioMetricsViewModel bioMetricsViewModel;
    BioMetricsValidationViewModel bioMetricsValidationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);
        bioMetricsValidationViewModel=new BioMetricsValidationViewModel(getApplicationContext(),this);
        setDeclareForBioValidation();
        bioMetricsViewModel=new BioMetricsViewModel(getApplicationContext(),this);

        //making status bar color as transparent
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        bottom();

    }

    private void setDeclareForBioValidation() {
        bioMetricsValidationViewModel.setToken(getPreferenceManager().getPrefToken());
        bioMetricsValidationViewModel.generateBioMetricsValidationRequest();
    }

    private void bottom() {
        LinearLayout changempin;
        bottomSheetDialog=new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.downforsecurity);
        bottomSheetDialog.setCanceledOnTouchOutside(false);

        linearhome=bottomSheetDialog.findViewById(R.id.linearhome);
        linearvalut=bottomSheetDialog.findViewById(R.id.linearTransaction);
        linearnotification=bottomSheetDialog.findViewById(R.id.linearnotification);
        linearprofile=bottomSheetDialog.findViewById(R.id.linearprofile);
        backFor=bottomSheetDialog.findViewById(R.id.backFor);
        switchCompat=(SwitchCompat) bottomSheetDialog.findViewById(R.id.swOnOff);
        addtransaction=bottomSheetDialog.findViewById(R.id.addtransaction);
        setclick();

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.e("You are :", "EnChecked"+isChecked);

                if (isChecked) {
                    preferenceManager.setPrefBiometric("yes");
                    setDeclare("1");
                    Log.i("You are :", "Checked");
                }
                else{
                    getPreferenceManager().setPrefBiometric(null);
                    setDeclare("0");
                    Log.i("You are :", "UnChecked");
                }
            }
        });


        changempin=bottomSheetDialog.findViewById(R.id.personal);
        transaction=bottomSheetDialog.findViewById(R.id.linearTransaction);
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
        changempin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(security.this,currentmpin.class));
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
                        startActivity(new Intent(security.this,balancehome.class));
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

    private void setDeclare(String bio) {
        bioMetricsViewModel.setBio(bio);
        bioMetricsViewModel.setToken(getPreferenceManager().getPrefToken());
        bioMetricsViewModel.generateBioMetricsRequest();
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
                startActivity(new Intent(getApplicationContext(), Notification.class));
            }
        });
        linearhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(security.this,balancehome.class));

            }
        });
        linearvalut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(security.this,cardslist.class));

            }
        });
        backFor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(security.this,profile.class));
            }
        });
        linearprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(security.this,profile.class));
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
    public void onBackPressed() {
        startActivity(new Intent(security.this,profile.class));
    }

    @Override
    public void ShowErrorMessage(MessageViewType messageViewType, String errorMessage) {

    }

    @Override
    public void ShowErrorMessage(MessageViewType messageViewType, ViewType viewType, String errorMessage) {

    }

    @Override
    public void generateBioMetricsProcessed(GenerateBioMetricsResponseModel generateBioMetricsResponseModel) {
    }

    @Override
    public void generateBioMetricsValidationProcessed(GenerateBioMetricsValidationResponseModel generateBioMetricsValidationsResponseModel) {
        if(generateBioMetricsValidationsResponseModel.getMsg().equals("Biometric Activated")){
            switchCompat.setChecked(true);
            if(generateBioMetricsValidationsResponseModel.getData().getToken()!=null){
            getPreferenceManager().setPrefToken(generateBioMetricsValidationsResponseModel.getData().getToken());
        }
    }
    }

    @Override
    public void onFailure(ErrorBody errorBody, int statusCode) {
    }

}