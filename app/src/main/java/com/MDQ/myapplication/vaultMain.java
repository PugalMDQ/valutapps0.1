package com.MDQ.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.URLUtil;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.MDQ.myapplication.databinding.ActivityValutMainBinding;
import com.MDQ.myapplication.enums.MessageViewType;
import com.MDQ.myapplication.enums.ViewType;
import com.MDQ.myapplication.interfaces.InterfaceForValut;
import com.MDQ.myapplication.interfaces.viewresponceinterface.VaultListResponceInterface;
import com.MDQ.myapplication.pojo.jsonresponse.DataForVaultList;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateVaultListResponseModel;
import com.MDQ.myapplication.utils.PreferenceManager;
import com.MDQ.myapplication.viewmodel.VaultListBaseViewModel;
import com.MDQ.myapplication.viewmodel.VaultListViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class vaultMain extends AppCompatActivity implements InterfaceForValut, VaultListResponceInterface {
    GenerateVaultListResponseModel generateVaultListResponseModel;
    RecyclerView recyclerView;
    Adapterforvalutrv adapterforvalutrv;
    GridLayoutManager layoutManager;
    public static BottomSheetDialog bottomSheetDialog;
    public static  TextView textindownvalut;
    VaultListViewModel vaultListViewModel;
    ActivityValutMainBinding av;
    PreferenceManager preferenceManager;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        av=ActivityValutMainBinding.inflate(getLayoutInflater());
        setContentView(av.getRoot());

        //making status bar color as transparent
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        vaultListViewModel=new VaultListViewModel(getApplicationContext(),this);
        String token=getPreferenceManager().getPrefToken().toString();
        if(token!=null) {
            vaultListViewModel.setToken(token);
            vaultListViewModel.generateVaultListRequest();
        }

        recyclerView=findViewById(R.id.rvcontainer);


        setclick();

        av.Search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filters(s.toString());
            }
        });

    }

    private void filters(String text) {

//        ArrayList<String> filterdNames = new ArrayList<>();
//
//        for ( DataForVaultList s : generateVaultListResponseModel.getData()) {
//            if (s.toLowerCase().contains(text.toLowerCase())) {
//                filterdNames.add(s);
//            }
//        }
//        adapter.filterList(filterdNames);
    }
    private void setclick() {

        av.linearnotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Notification.class));
            }
        });
        av.backc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),balancehome.class));
            }
        });
        av.linearhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(vaultMain.this,balancehome.class));
            }
        });
        av.linearprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(vaultMain.this,profile.class));
            }
        });

        av.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(vaultMain.this,FileUploading.class));
            }
        });
    }

    public void bottoms(){
        LinearLayout l2;
        bottomSheetDialog=new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.downforvalut);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        l2=bottomSheetDialog.findViewById(R.id.l2);
        l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getUrl="https:/"+generateVaultListResponseModel.getData().get(position).getUrl().trim();
                Log.i("url",getUrl);
                DownloadManager.Request request=new DownloadManager.Request(Uri.parse(getUrl.trim()));
                String title= URLUtil.guessFileName(getUrl,null,null);
                request.setTitle(title);
                String cookie= CookieManager.getInstance().getCookie(getUrl);
                request.addRequestHeader("cookie",cookie);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,title);
                DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                downloadManager.enqueue(request);
            }
        });

        textindownvalut=bottomSheetDialog.findViewById(R.id.textid);
        bottomSheetDialog.show();
    }

    @Override
    public void valut(String name,int position) {
    this.position=position;
        bottoms();
        textindownvalut.setText(name);

    }

    @Override
    public void ShowErrorMessage(MessageViewType messageViewType, String errorMessage) {

    }

    @Override
    public void ShowErrorMessage(MessageViewType messageViewType, ViewType viewType, String errorMessage) {

    }

    @Override
    public void generateVaultListProcessed(GenerateVaultListResponseModel generateVaultListResponseModel) {
        layoutManager=new GridLayoutManager(this,2);
        adapterforvalutrv=new Adapterforvalutrv(getApplicationContext(),this,generateVaultListResponseModel);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterforvalutrv);
        this.generateVaultListResponseModel=generateVaultListResponseModel;
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
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),balancehome.class));
    }
}