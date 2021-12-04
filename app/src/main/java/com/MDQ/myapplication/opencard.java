package com.MDQ.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.MDQ.myapplication.databinding.ActivityOpencardBinding;
import com.MDQ.myapplication.enums.MessageViewType;
import com.MDQ.myapplication.enums.ViewType;
import com.MDQ.myapplication.interfaces.viewresponceinterface.ListTransactionResponseInterface;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateListTransactionResponseModel;
import com.MDQ.myapplication.utils.PreferenceManager;
import com.MDQ.myapplication.viewmodel.ListTransactionViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class opencard extends AppCompatActivity implements ListTransactionResponseInterface {

    ActivityOpencardBinding ao;
    AdapterForOpenCard adapter;
    LinearLayoutManager layoutManager;
    RecyclerView recyclerView;
    ListTransactionViewModel listTransactionViewModel;
    String index,account_id,token;
    PreferenceManager preferenceManager;
    ConstraintLayout back;
    BottomSheetBehavior bottomSheetBehavior;
    ConstraintLayout constraintLayout,second,constraintLayout1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ao=ActivityOpencardBinding.inflate(getLayoutInflater());
        setContentView(ao.getRoot());
        recyclerView=findViewById(R.id.rvs);
        back=findViewById(R.id.backc);
        second=findViewById(R.id.contains);
        constraintLayout=findViewById(R.id.bottomsheet);
        bottomSheetBehavior=BottomSheetBehavior.from(constraintLayout);
        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState){
                    case BottomSheetBehavior.STATE_EXPANDED:
                        constraintLayout1=findViewById(R.id.secondLayout);
                        constraintLayout1.setBackground(getResources().getDrawable(R.drawable.backforregister));

                        constraintLayout.setBackgroundColor(getResources().getColor(R.color.new1));
                        second.setVisibility(View.VISIBLE);
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        constraintLayout1=findViewById(R.id.secondLayout);
                        constraintLayout.setBackground(getResources().getDrawable(R.drawable.backforregister));
                        second.setVisibility(View.GONE);
                        break;
                }

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        ao.back.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_keyboard_arrow_left_24));

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        Intent intent=getIntent();
        index=intent.getStringExtra("index");
        account_id=intent.getStringExtra("account_id");
        listTransactionViewModel=new ListTransactionViewModel(getApplicationContext(),this);


        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if ((connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)
                || (connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState() == NetworkInfo.State.CONNECTED)) {
            setDeclare();
        }
        else{
            Toast.makeText(getApplicationContext(), "This App Require Internet", Toast.LENGTH_SHORT).show();
        }





        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setDeclare() {
        token=getPreferenceManager().getPrefToken();
        if(token!=null) {
            listTransactionViewModel.setAccount_id(account_id);
            listTransactionViewModel.setIndex(index);
            listTransactionViewModel.generateListTransactionRequest();
        }

    }
    public PreferenceManager getPreferenceManager() {
        if (preferenceManager == null) {
            preferenceManager = PreferenceManager.getInstance();
            preferenceManager.initialize(getApplicationContext());
        }
        return preferenceManager;
    }

    @Override
    public void generateListTransactionProcessed(GenerateListTransactionResponseModel generateListTransactionResponseModel) {
        if(generateListTransactionResponseModel.data.size()>0){

            adapter=new AdapterForOpenCard(getApplicationContext(),generateListTransactionResponseModel);
            layoutManager=new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);

        }
    }

    @Override
    public void onFailure(ErrorBody errorBody, int statusCode) {

    }

    @Override
    public void ShowErrorMessage(MessageViewType messageViewType, String errorMessage) {

    }

    @Override
    public void ShowErrorMessage(MessageViewType messageViewType, ViewType viewType, String errorMessage) {

    }

}

