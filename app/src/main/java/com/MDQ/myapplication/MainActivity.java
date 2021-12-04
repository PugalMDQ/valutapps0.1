package com.MDQ.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.MDQ.myapplication.databinding.ActivityMainBinding;
import com.MDQ.myapplication.utils.PreferenceManager;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding ab;
    adapter adapters;
    ViewPager viewPager;
    TabLayout tabLayout;
    PreferenceManager preferenceManager;
    String token=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ab=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(ab.getRoot());
        //initialized the widgets
        tabLayout=findViewById(R.id.tabs);
        viewPager=findViewById(R.id.photos_viewpager);
        //initialized the adapter for the tutorial of myFinalyst app
        adapters=new adapter(getSupportFragmentManager());
        viewPager.setAdapter(adapters);
        tabLayout.setupWithViewPager(viewPager);
        token=getPreferenceManager().getPrefToken();

        ab.getstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //For checking the internet connection and if connection is available navigate the  to user to register screen
                //else throw an error toast to user
                ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext()
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                if ((connectivityManager
                        .getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null && connectivityManager
                        .getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)
                        || (connectivityManager
                        .getNetworkInfo(ConnectivityManager.TYPE_WIFI) != null && connectivityManager
                        .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                        .getState() == NetworkInfo.State.CONNECTED)) {

                    startActivity(new Intent(MainActivity.this, Register.class));
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "This App Require Internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ab.Login.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
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
                        .getState() == NetworkInfo.State.CONNECTED))
                {

                    if(token!=null) {
                        startActivity(new Intent(MainActivity.this, enteryourmpin.class));
                        finish();

                    }
                    else {
                        startActivity(new Intent(MainActivity.this, Login.class));
                        finish();

                    }

                }
                else{

                    Toast.makeText(getApplicationContext(), "This App Require Internet", Toast.LENGTH_SHORT).show();
                }
            }
        });

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

}