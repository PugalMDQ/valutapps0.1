package com.MDQ.myapplication;

import androidx.appcompat.app.AppCompatActivity;
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

import com.MDQ.myapplication.databinding.ActivityCardslistBinding;
import com.MDQ.myapplication.enums.MessageViewType;
import com.MDQ.myapplication.enums.ViewType;
import com.MDQ.myapplication.interfaces.InterfaceForCardList;
import com.MDQ.myapplication.interfaces.viewresponceinterface.AccountListResponseInterface;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateAccountListResponseModel;
import com.MDQ.myapplication.utils.PreferenceManager;
import com.MDQ.myapplication.viewmodel.AccountListViewModel;

public class cardslist extends AppCompatActivity  implements AccountListResponseInterface, InterfaceForCardList {

    ActivityCardslistBinding ac;
    adapterforcardlist afct;
    RecyclerView.LayoutManager layoutManager;
    AccountListViewModel accountListViewModel;
    PreferenceManager preferenceManager;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initialize with view
        ac=ActivityCardslistBinding.inflate(getLayoutInflater());
        setContentView(ac.getRoot());
        accountListViewModel=new AccountListViewModel(getApplicationContext(),this);

        //Checking internet connection if available call setDeclare methode else toast the error message
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

        //set the status bar color as transparent
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        //navigate to AddCardScreen
        ac.ADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(cardslist.this,AddCardScreen.class));
            }
        });

        //call onBackPressed methode
        ac.backc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    //set request for AccountList api
    private void setDeclare() {
        token=getPreferenceManager().getPrefToken();
        accountListViewModel.setToken(token);
        accountListViewModel.generateAccountListRequest();
    }

    //navigate to balance screen
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent=new Intent(cardslist.this,balancehome.class);
        startActivity(intent);
    }

    @Override
    public void ShowErrorMessage(MessageViewType messageViewType, String errorMessage) {
        //do nothing
    }

    @Override
    public void ShowErrorMessage(MessageViewType messageViewType, ViewType viewType, String errorMessage) {
        //do nothing

    }


    /**
     * @param generateAccountListResponseModel
     * @breif getting response from AccountList api
     */
    @Override
    public void generateAccountListProcessed(GenerateAccountListResponseModel generateAccountListResponseModel ) {

        layoutManager=new LinearLayoutManager(this);
        afct=new adapterforcardlist(generateAccountListResponseModel,getApplicationContext(),this);
        ac.rvincardlist.setAdapter(afct);
        ac.rvincardlist.setLayoutManager(layoutManager);

    }

    @Override
    public void onFailure(ErrorBody errorBody, int statusCode) {
        //do nothing
    }

    /**
     * @return
     * @brief initializing the preferenceManager from shared preference for local use in this activity
     */
    public PreferenceManager getPreferenceManager() {
        if (preferenceManager == null) {
            preferenceManager = PreferenceManager.getInstance();
            preferenceManager.initialize(getApplicationContext());
            Toast.makeText(getApplicationContext(), "defe", Toast.LENGTH_SHORT).show();
        }
        return preferenceManager;
    }

    @Override
    public void OpenCard(int index, int id) {
        startActivity(new Intent(getApplicationContext(),opencard.class));
    }
}