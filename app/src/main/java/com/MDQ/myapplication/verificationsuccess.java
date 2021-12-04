package com.MDQ.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class verificationsuccess extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificationsuccess);

        Intent intent1=getIntent();
        String token=intent1.getStringExtra("token");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(verificationsuccess.this,setmpin.class);
                intent.putExtra("token",token);
                startActivity(intent);
                finish();
            }
        },3000);
    }
}