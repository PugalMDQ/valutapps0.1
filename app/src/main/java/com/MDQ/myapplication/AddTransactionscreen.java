package com.MDQ.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentContainer;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.MDQ.myapplication.databinding.ActivityAddTransactionscreenBinding;
import com.MDQ.myapplication.enums.MessageViewType;
import com.MDQ.myapplication.enums.ViewType;
import com.MDQ.myapplication.interfaces.viewresponceinterface.BankListResponseInterface;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateBankListResponseModel;

public class AddTransactionscreen extends AppCompatActivity  {

    ActivityAddTransactionscreenBinding aab;
    public static ConstraintLayout constraintLayout;
    public static LinearLayout buttons;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aab=ActivityAddTransactionscreenBinding.inflate(getLayoutInflater());
        setContentView(aab.getRoot());
        buttons=findViewById(R.id.buttons);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if ((connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)
                || (connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState() == NetworkInfo.State.CONNECTED)) {}else {
            Toast.makeText(getApplicationContext(), "This App Require Internet", Toast.LENGTH_SHORT).show();
        }


        constraintLayout=findViewById(R.id.changeblecons);
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.framecontainer,new expense(),null).commit();



        aab.expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.framecontainer,new expense(),null).commit();
                aab.expense.setBackground(getDrawable(R.drawable.backfortabbutton));
                aab.expense.setTextColor(getResources().getColor(R.color.white));

                aab.income.setBackground(getDrawable(R.drawable.back_tabs));
                aab.income.setTextColor(getResources().getColor(R.color.black));
            }
        });

        aab.income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.framecontainer,new income(),null).commit();

                aab.income.setBackground(getDrawable(R.drawable.backfortabbutton));
                aab.income.setTextColor(getResources().getColor(R.color.white));

                aab.expense.setBackground(getDrawable(R.drawable.back_tabs));
                aab.expense.setTextColor(getResources().getColor(R.color.black));
            }
        });

        aab.backc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddTransactionscreen.this,cardslist.class));

            }
        });
    }
}