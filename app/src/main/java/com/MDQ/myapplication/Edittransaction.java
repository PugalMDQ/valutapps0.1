package com.MDQ.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.MDQ.myapplication.databinding.ActivityEdittransactionBinding;

public class Edittransaction extends AppCompatActivity {


    ActivityEdittransactionBinding ae;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ae=ActivityEdittransactionBinding.inflate(getLayoutInflater());
        setContentView(ae.getRoot());

        ae.cardforgone.setBackground(getResources().getDrawable(R.drawable.consbackroundforaddaccount));
        ae.buttons.bringToFront();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        ae.editted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ae.close.setVisibility(View.VISIBLE);
                ae.hetting.setVisibility(View.VISIBLE);
                ae.editted.setVisibility(View.GONE);
                ae.deleted.setVisibility(View.GONE);
                ae.close.bringToFront();
            }
        });
    }
}