package com.MDQ.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class verificationsuccess extends AppCompatActivity {

    String screen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificationsuccess);

        //getting token from previous screen using intent
        Intent intent1=getIntent();
        String token=intent1.getStringExtra("token");
        screen =intent1.getStringExtra("screen");
        //stop screen for three second and move to setmpin screen

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (screen!=null) {
                    startActivity(new Intent(getApplicationContext(), balancehome.class));
                } else {
                    Intent intent = new Intent(verificationsuccess.this, setmpin.class);
                    intent.putExtra("token", token);
                    startActivity(intent);
                    finish();
                }
            }
        },3000);
    }
}