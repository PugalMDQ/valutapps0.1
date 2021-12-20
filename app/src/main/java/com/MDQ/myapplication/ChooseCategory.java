package com.MDQ.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.MDQ.myapplication.datamanager.CategorySpentDataManager;
import com.MDQ.myapplication.interfaces.InterfaceForChooseCategoryAdapter;
import com.MDQ.myapplication.interfaces.viewresponceinterface.CategorySpentResponseInterface;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateCategorySpentResponseModel;
import com.MDQ.myapplication.utils.PreferenceManager;
import com.MDQ.myapplication.viewmodel.CategorySpendViewModel;

public class ChooseCategory extends AppCompatActivity implements CategorySpentResponseInterface, InterfaceForChooseCategoryAdapter {

    CategorySpendViewModel categorySpendViewModel;
    String Token;
    PreferenceManager preferenceManager;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    AdapterForChooseCategory adapterForChooseCategory;
    CardView cardView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_category);
        //initialize with variables
        recyclerView=findViewById(R.id.rev);
        Token= getPreferenceManager().getPrefToken();
        cardView=findViewById(R.id.backc);

        //navigate to cardSList
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),cardslist.class));

            }
        });
        categorySpendViewModel=new CategorySpendViewModel(getApplicationContext(),this);

        //Checking internet connection if available calling setDeclare methode else toast an error message
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if ((connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)
                || (connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState() == NetworkInfo.State.CONNECTED)) {
            setdeclare();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "This App Require Internet", Toast.LENGTH_SHORT).show();
        }
    }

    //set request fro categorySpend api
    private void setdeclare() {
        categorySpendViewModel.setToken(Token);
        categorySpendViewModel.setType("“credit” or “debit”");
        categorySpendViewModel.generateCategorySpentRequest();
    }

    /**
     * @param generateCategorySpentResponseModel
     * @breif get response from categorySpend api
     */
    @Override
    public void generateCategorySpendProcessed(GenerateCategorySpentResponseModel generateCategorySpentResponseModel) {
        adapterForChooseCategory=new AdapterForChooseCategory(this,generateCategorySpentResponseModel,this);
        layoutManager=new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterForChooseCategory);

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
        }
        return preferenceManager;
    }

    //set logo and name in sharedPreference local storage
    @Override
    public void ChooseCategory(String logo, String name) {
        getPreferenceManager().setPrefImageUrl(logo);
        getPreferenceManager().setPrefNameInCategory(name);
        onBackPressed();
    }
}