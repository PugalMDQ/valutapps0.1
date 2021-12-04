package com.MDQ.myapplication;

import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.MDQ.myapplication.utils.PreferenceManager;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.lang.reflect.Field;

public class security extends AppCompatActivity {
    BottomSheetDialog bottomSheetDialog;
    LinearLayout transaction;
    PreferenceManager preferenceManager;
    ImageView backFor;
    LinearLayout linearhome,linearvalut,linearnotification,linearprofile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);
        bottom();


    }

    private void bottom() {
        LinearLayout changempin;
        SwitchCompat switchCompat;
        bottomSheetDialog=new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.downforsecurity);
        bottomSheetDialog.setCanceledOnTouchOutside(false);

        linearhome=bottomSheetDialog.findViewById(R.id.linearhome);
        linearvalut=bottomSheetDialog.findViewById(R.id.linearTransaction);
        linearnotification=bottomSheetDialog.findViewById(R.id.linearnotification);
        linearprofile=bottomSheetDialog.findViewById(R.id.linearprofile);
        backFor=bottomSheetDialog.findViewById(R.id.backFor);
        setclick();




        switchCompat=bottomSheetDialog.findViewById(R.id.swOnOff);
        if (getPreferenceManager().getPrefBiometric()!=""){
            switchCompat.setOnCheckedChangeListener(null);
            switchCompat.setChecked(true);
        }
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



        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    getPreferenceManager().setPrefBiometric("true1");
                }
                else {
                    getPreferenceManager().setPrefBiometric("");
                }
            }
        });

        bottomSheetDialog.show();


    }
    private void setclick() {

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
}