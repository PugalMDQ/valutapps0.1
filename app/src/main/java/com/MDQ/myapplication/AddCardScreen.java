package com.MDQ.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.MDQ.myapplication.databinding.ActivityAddCardScreenBinding;
import com.MDQ.myapplication.enums.MessageViewType;
import com.MDQ.myapplication.enums.ViewType;
import com.MDQ.myapplication.interfaces.viewresponceinterface.AccountTypeResponseInterface;
import com.MDQ.myapplication.interfaces.viewresponceinterface.AddAccountResponseInterface;
import com.MDQ.myapplication.interfaces.viewresponceinterface.BankListResponseInterface;
import com.MDQ.myapplication.interfaces.viewresponceinterface.CurrencyResponseInterface;
import com.MDQ.myapplication.pojo.jsonresponse.DataForCurrency;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateAccountTypeResponseModel;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateBankListResponseModel;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateCurrencyResponseModel;
import com.MDQ.myapplication.utils.PreferenceManager;
import com.MDQ.myapplication.viewmodel.AccountTypeViewModel;
import com.MDQ.myapplication.viewmodel.AddAccountViewModel;
import com.MDQ.myapplication.viewmodel.BankListViewModel;
import com.MDQ.myapplication.viewmodel.CurrencyListViewModel;

import java.util.List;

public class AddCardScreen extends AppCompatActivity implements AccountTypeResponseInterface, BankListResponseInterface, CurrencyResponseInterface, AddAccountResponseInterface {

    ActivityAddCardScreenBinding ad;

    AutoCompleteTextView BankName,BankType,Currency;
    ImageView ScrollForBank,ScrollForAccountType,ScrollForCurrency;
    int[] ids;
    String[] names;
    BankListViewModel bankListViewModel;
    CurrencyListViewModel currencyListViewModel;
    AccountTypeViewModel accountTypeViewModel;
    AddAccountViewModel addAccountViewModel;
    PreferenceManager preferenceManager;

    String[] BankNames;
    int[] id;

    String[] BankTypes;
    int[] ida;

    String idForCurrency,idForBankType,idForBankName;

    String AccountName,tokens;
    String CurrentBalance;
    ArrayAdapter<String> adapterForBankList;
    ArrayAdapter<String> adapterForCurrency;
    ArrayAdapter<String> adapterForAccountType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ad=ActivityAddCardScreenBinding.inflate(getLayoutInflater());
        setContentView(ad.getRoot());
        BankName=findViewById(R.id.Bankname);
        BankType=findViewById(R.id.Banktype);
        Currency=findViewById(R.id.ecurrency);
        ScrollForBank=findViewById(R.id.scrolforbank);
        ScrollForAccountType=findViewById(R.id.scrolforaccounttype);
        ScrollForCurrency=findViewById(R.id.scrolforcurrency);
        setclick();
        setbackround();
        currencyListViewModel=new CurrencyListViewModel(getApplicationContext(),this);
        accountTypeViewModel=new AccountTypeViewModel(getApplicationContext(),this);
        bankListViewModel=new BankListViewModel(getApplicationContext(),this);
        setDeclare();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }



        //check internet
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if ((connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)
                || (connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState() == NetworkInfo.State.CONNECTED)) {}else{
            Toast.makeText(getApplicationContext(), "This App Require Internet", Toast.LENGTH_SHORT).show();
        }

        ad.Bankname.setRawInputType(InputType.TYPE_NULL);
        ad.Bankname.setFocusable(true);
        ad.Banktype.setRawInputType(InputType.TYPE_NULL);
        ad.Banktype.setFocusable(true);
        ad.ecurrency.setRawInputType(InputType.TYPE_NULL);
        ad.ecurrency.setFocusable(true);



        ad.backc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ad.addaccountsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext()
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                if ((connectivityManager
                        .getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null && connectivityManager
                        .getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)
                        || (connectivityManager
                        .getNetworkInfo(ConnectivityManager.TYPE_WIFI) != null && connectivityManager
                        .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                        .getState() == NetworkInfo.State.CONNECTED)) {
                    setdeclareAddAccount();
                }
                else{
                    Toast.makeText(getApplicationContext(), "This App Require Internet ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ScrollForCurrency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Currency.showDropDown();
            }
        });

        Currency.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                idForCurrency= String.valueOf(position+1);

            }
        });
        ScrollForBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BankName.showDropDown();
            }
        });
        BankName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                idForBankName= String.valueOf(position+1);

            }
        });

        ScrollForAccountType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BankType.showDropDown();
            }
        });
        BankType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                idForBankType= String.valueOf(position+1);

            }
        });

        ad.Banktype.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                BankType.showDropDown();
                return true;
            }
        });
        ad.Bankname.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                BankName.showDropDown();
                return true;
            }
        });
        ad.ecurrency.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Currency.showDropDown();
                return true;
            }
        });


    }

    private void setdeclareAddAccount() {
        addAccountViewModel=new AddAccountViewModel(getApplicationContext(),this);
        AccountName=ad.Accountname.getText().toString();
        CurrentBalance= ad.ecurrentbalance.getText().toString();
        if(CurrentBalance!=null&&AccountName!=null&&idForBankName!=null&&idForCurrency!=null&&idForBankType!=null){
            addAccountViewModel.setAccountName(AccountName);
            addAccountViewModel.setToken(tokens);
            addAccountViewModel.setAccountType(idForBankType);
            addAccountViewModel.setBankName(idForBankName);
            addAccountViewModel.setCurrency(idForCurrency);
            addAccountViewModel.setCurrentBalance(CurrentBalance);
            addAccountViewModel.generateAddAccountRequest();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Enter All Fields", Toast.LENGTH_SHORT).show();
        }
    }

    private void setDeclare() {
        tokens=getPreferenceManager().getPrefToken();
        bankListViewModel.setToken(tokens);
        currencyListViewModel.setToken(tokens);
        accountTypeViewModel.setToken(tokens);
        bankListViewModel.generateBankListRequest();
        currencyListViewModel.generateCurrencyRequest();
        accountTypeViewModel.generateAccountTypeRequest();
    }

    private void setclick() {

        ad.cardforamount.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ad.cardforamount.setBackground(getDrawable(R.drawable.consbackroundforaddaccountforactive));
                ad.Cardforaccounttype.setBackground(getDrawable(R.drawable.consbackroundforaddaccount));
                ad.cardforcurrentbalance.setBackground(getDrawable(R.drawable.consbackroundforaddaccount));
                ad.cardforCurrency.setBackground(getDrawable(R.drawable.consbackroundforaddaccount));

                ad.cardforcategory.setBackground(getDrawable(R.drawable.consbackroundforaddaccount));
                return false;
            }
        });
        ad.cardforcurrentbalance.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ad.cardforamount.setBackground(getDrawable(R.drawable.consbackroundforaddaccount));
                ad.Cardforaccounttype.setBackground(getDrawable(R.drawable.consbackroundforaddaccount));
                ad.cardforcurrentbalance.setBackground(getDrawable(R.drawable.consbackroundforaddaccountforactive));
                ad.cardforCurrency.setBackground(getDrawable(R.drawable.consbackroundforaddaccount));
                ad.cardforcategory.setBackground(getDrawable(R.drawable.consbackroundforaddaccount));
                return false;
            }
        });
        ad.Cardforaccounttype.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ad.cardforamount.setBackground(getDrawable(R.drawable.consbackroundforaddaccount));
                ad.Cardforaccounttype.setBackground(getDrawable(R.drawable.consbackroundforaddaccountforactive));
                ad.cardforcurrentbalance.setBackground(getDrawable(R.drawable.consbackroundforaddaccount));
                ad.cardforCurrency.setBackground(getDrawable(R.drawable.consbackroundforaddaccount));
                ad.cardforcategory.setBackground(getDrawable(R.drawable.consbackroundforaddaccount));
                return false;
            }
        });
        ad.cardforCurrency.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ad.cardforamount.setBackground(getDrawable(R.drawable.consbackroundforaddaccount));
                ad.Cardforaccounttype.setBackground(getDrawable(R.drawable.consbackroundforaddaccount));
                ad.cardforcurrentbalance.setBackground(getDrawable(R.drawable.consbackroundforaddaccount));
                ad.cardforCurrency.setBackground(getDrawable(R.drawable.consbackroundforaddaccountforactive));
                ad.cardforcategory.setBackground(getDrawable(R.drawable.consbackroundforaddaccount));
                return false;
            }
        });
        ad.cardforcategory.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ad.cardforamount.setBackground(getDrawable(R.drawable.consbackroundforaddaccount));
                ad.Cardforaccounttype.setBackground(getDrawable(R.drawable.consbackroundforaddaccount));
                ad.cardforcurrentbalance.setBackground(getDrawable(R.drawable.consbackroundforaddaccount));
                ad.cardforCurrency.setBackground(getDrawable(R.drawable.consbackroundforaddaccount));
                ad.cardforcategory.setBackground(getDrawable(R.drawable.consbackroundforaddaccountforactive));
                return false;
            }
        });
    }

    private void setbackround() {
        ad.cardforamount.setBackground(getDrawable(R.drawable.consbackroundforaddaccount));
        ad.Cardforaccounttype.setBackground(getDrawable(R.drawable.consbackroundforaddaccount));
        ad.cardforcurrentbalance.setBackground(getDrawable(R.drawable.consbackroundforaddaccount));
        ad.cardforCurrency.setBackground(getDrawable(R.drawable.consbackroundforaddaccount));
        ad.cardforcategory.setBackground(getDrawable(R.drawable.consbackroundforaddaccount));

    }

    @Override
    public void ShowErrorMessage(MessageViewType messageViewType, String errorMessage) {

    }

    @Override
    public void ShowErrorMessage(MessageViewType messageViewType, ViewType viewType, String errorMessage) {

    }

    @Override
    public void generateAccountTypeProcessed(GenerateAccountTypeResponseModel generateAccountTypeResponseModel) {
        BankTypes=new String[generateAccountTypeResponseModel.getData().size()];
        ida=new int[generateAccountTypeResponseModel.getData().size()];
        for(int i=0;i<generateAccountTypeResponseModel.getData().size();i++){
            BankTypes[i]=generateAccountTypeResponseModel.getData().get(i).getName();
            ida[i]=generateAccountTypeResponseModel.getData().get(i).getId();

        }

        adapterForAccountType=new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,BankTypes);
        BankType.setText(adapterForAccountType.getItem(0).toString());
        BankType.setAdapter(adapterForAccountType);
        idForBankType="1";

    }
    @Override
    public void generateCurrencyProcessed(GenerateCurrencyResponseModel generateCurrencyResponseModel) {
        ids=new int[generateCurrencyResponseModel.getData().size()];
        names=new String[generateCurrencyResponseModel.getData().size()];
        for (int i=0;i<generateCurrencyResponseModel.getData().size();i++){
            ids[i]=generateCurrencyResponseModel.getData().get(i).getId();
            names[i]=generateCurrencyResponseModel.getData().get(i).getName();
        }
        adapterForCurrency=new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,names);
        Currency.setText(adapterForCurrency.getItem(0).toString());
        Currency.setAdapter(adapterForCurrency);
        idForCurrency="1";

    }

    @Override
    public void generateBankListProcessed(GenerateBankListResponseModel generateBankListResponseModel) {

        BankNames=new String[generateBankListResponseModel.getData().size()];
        id=new int[generateBankListResponseModel.getData().size()];
        for(int i=0;i<generateBankListResponseModel.getData().size();i++){
            BankNames[i]=generateBankListResponseModel.getData().get(i).getName();
            id[i]=generateBankListResponseModel.getData().get(i).getId();
        }
        adapterForBankList=new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,BankNames);
        BankName.setText(adapterForBankList.getItem(0).toString());
        BankName.setAdapter(adapterForBankList);
        idForBankName="1";
    }

    @Override
    public void generateAddAccountProcessed(String Msg) {

        String msg="This Account Added successfully";
        if(msg.trim().equals(Msg.trim())){
            startActivity(new Intent(AddCardScreen.this,cardslist.class));
        }
    }

    @Override
    public void onFailure(ErrorBody errorBody, int statusCode) {

    }

    public PreferenceManager getPreferenceManager() {
        if (preferenceManager == null) {
            preferenceManager = PreferenceManager.getInstance();
            preferenceManager.initialize(getApplicationContext());
        }
        return preferenceManager;
    }

}