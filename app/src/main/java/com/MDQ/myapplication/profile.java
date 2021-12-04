package com.MDQ.myapplication;

import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.MDQ.myapplication.databinding.ActivityProfileBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.lang.reflect.Field;

public class profile extends AppCompatActivity {

    ActivityProfileBinding ap;
    BottomSheetDialog bottomSheetDialog;
    LinearLayout personal,securities,help;
    LinearLayout transaction;

    LinearLayout linearhome,linearvalut,linearnotification,linearprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ap=ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(ap.getRoot());

            group();

    }

    private void group(){


        bottomSheetDialog=new BottomSheetDialog(profile.this);
        bottomSheetDialog.setContentView(R.layout.downprofile);
        bottomSheetDialog.setCanceledOnTouchOutside(false);
        personal=bottomSheetDialog.findViewById(R.id.personal);
        securities=bottomSheetDialog.findViewById(R.id.securities);
        help=bottomSheetDialog.findViewById(R.id.help);



        linearhome=bottomSheetDialog.findViewById(R.id.linearhome);
        linearvalut=bottomSheetDialog.findViewById(R.id.linearTransaction);
        linearnotification=bottomSheetDialog.findViewById(R.id.linearnotification);
        linearprofile=bottomSheetDialog.findViewById(R.id.linearprofile);
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
        personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(profile.this,personalinformation.class));
            }
        });

        securities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(profile.this,security.class));
            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(profile.this,helpandsupport.class));
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
                           startActivity(new Intent(profile.this,balancehome.class));
                           finish();
                    }

                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {

                }
            });

            bottomSheetDialog.show();
        }
        catch (Exception e){}

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
                startActivity(new Intent(profile.this,balancehome.class));

            }
        });
        linearvalut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(profile.this,cardslist.class));

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(profile.this,balancehome.class));

    }
}