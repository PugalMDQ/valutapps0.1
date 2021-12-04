package com.MDQ.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.MDQ.myapplication.databinding.ActivityBalancehomeBinding;
import com.MDQ.myapplication.enums.MessageViewType;
import com.MDQ.myapplication.enums.ViewType;
import com.MDQ.myapplication.interfaces.InterfaceForBalanceHome;
import com.MDQ.myapplication.interfaces.viewresponceinterface.AccountListResponseInterface;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateAccountListResponseModel;
import com.MDQ.myapplication.utils.PreferenceManager;
import com.MDQ.myapplication.viewmodel.AccountListViewModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class balancehome extends AppCompatActivity implements AccountListResponseInterface , InterfaceForBalanceHome {
    public static TabLayout tabs;
    public static ViewPager viewPager;
    public static LinearLayout linearLayout;
    public static TextView textView;
    public static ImageView imageView;
    public static LineChart chart;
    ActivityBalancehomeBinding ab;
    public static Adapterstab adapterstab;
    RecyclerView.LayoutManager layoutManager;
    Adapterforrecyclerinhome adapterforrecyclerinhomes;
    public static Context context;
    AccountListViewModel accountListViewModel;
    PreferenceManager preferenceManager;
    public static TextView noaccount;
    public static RecyclerView  recyclerView;
    AutoCompleteTextView autoCompleteTextView;
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ab=ActivityBalancehomeBinding.inflate(getLayoutInflater());
        setContentView(ab.getRoot());
        context=getApplication();
        tabs=findViewById(R.id.tabs);
        viewPager=findViewById(R.id.views);
        linearLayout=findViewById(R.id.list);
        chart=findViewById(R.id.line);
        textView=findViewById(R.id.values);
        imageView=findViewById(R.id.listimage);
        noaccount=findViewById(R.id.noaccount);
        recyclerView=findViewById(R.id.recyclerView);
        autoCompleteTextView=findViewById(R.id.values);
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
                .getState() == NetworkInfo.State.CONNECTED)) {}else {
            Toast.makeText(getApplicationContext(), "This App Require Internet ", Toast.LENGTH_SHORT).show();
        }

        adapterstab=new Adapterstab(getSupportFragmentManager());

        ab.addaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(balancehome.this,AddCardScreen.class));
            }
        });
        ab.linearvalut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(balancehome.this, vaultMain.class));
            }
        });
       ab.linearprofile.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
              startActivity(new Intent(balancehome.this,profile.class));
           }
       });
       ab.addtransaction.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(balancehome.this,AddTransactionscreen.class));
           }
       });
        charts();
        accountListViewModel =new AccountListViewModel(getApplicationContext(),this);
        setDeclare();
        ab.linearnotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Notification.class));
            }
        });

            String[] months = {"jan'2021", "bef'2021", "mar'2021","apr'2021","may'2021","jun'2021","jul'2021"};
            ArrayAdapter<String> mountlist=new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,months);
            autoCompleteTextView.setAdapter(mountlist);
            autoCompleteTextView.setText(months[0]);
            ab.listimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    autoCompleteTextView.showDropDown();
                }
            });

        }

    private void setDeclare() {
        String token=getPreferenceManager().getPrefToken();
        accountListViewModel.setToken(token);
        accountListViewModel.generateAccountListRequest();
    }

    private void charts() {

        chart.setDragEnabled(true);
        chart.setScaleEnabled(false);


        chart.getDescription().setEnabled(false);

        //code for removing grid lines
        chart.getXAxis().setDrawGridLines(false);
         chart.getAxisLeft().setDrawGridLines(false);
        chart.getAxisRight().setDrawGridLines(false);


        //code for removing outer lines
        chart.getAxisRight().setDrawAxisLine(false);
        chart.getAxisLeft().setDrawAxisLine(false);
        chart.getXAxis().setDrawAxisLine(false);

        chart.setDrawingCacheBackgroundColor(getResources().getColor(R.color.graphcolor));


        chart.setExtraOffsets(0f,50f,0f,10f);
        //to remove the marker value
        chart.highlightValue(null);

        //remove right side value
        YAxis yAxisRight = chart.getAxisRight();
        yAxisRight.setEnabled(false);

        //remove left side value
        YAxis yAxisLeft = chart.getAxisLeft();
        yAxisLeft.setEnabled(false);

        //remove x axis value
        XAxis xAxis=chart.getXAxis();
        xAxis.setEnabled(false);
        xAxis.setLabelCount(5);

        xAxis.setTextColor(getResources().getColor(R.color.white));

        xAxis.setTextSize(14f);

        //to remove the description box
        chart.getLegend().setEnabled(false);

        chart.setTouchEnabled(true);

        chart.setVisibleXRangeMaximum(3);
        chart.moveViewToX(1);

        ArrayList<Entry> yvalue=new ArrayList<>();

        yvalue.add(new Entry(0,60));
        yvalue.add(new Entry(1,65));
        yvalue.add(new Entry(2,40));
        yvalue.add(new Entry(3,45));
        yvalue.add(new Entry(5,70));
        yvalue.add(new Entry(6,60));

        LineDataSet set=new LineDataSet(yvalue,null);
        set.setFillDrawable(getResources().getDrawable(R.drawable.balancebackground));
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawFilled(true);
        set.setLabel(null);
        set.setDrawValues(false);
        set.setCircleColor(getResources().getColor(R.color.white));
        //outer circle
        set.setCircleColorHole(getResources().getColor(R.color.graphcolormain));
        set.setCircleHoleRadius(4f);
        set.setLineWidth(3f);
        set.setCircleRadius(6f);
        set.setDrawHorizontalHighlightIndicator(false);
        set.setDrawVerticalHighlightIndicator(false);
        set.setColor(getResources().getColor(R.color.graphcolor));
        ArrayList<ILineDataSet> dataSets=new ArrayList<>();
        dataSets.add(set);
        LineData data=new LineData(dataSets);
        chart.setData(data);
        IMarker iMarker=new YourMarkerView(this,R.layout.tvcontent);
        chart.setMarker(iMarker);

    }

    @Override
    public void generateAccountListProcessed(GenerateAccountListResponseModel generateAccountListResponseModel) {

        InterfaceForBalanceHome interfaceForBalanceHome;
        adapterforrecyclerinhomes=new Adapterforrecyclerinhome(getApplicationContext(),generateAccountListResponseModel,this);
        layoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        ab.recyclerView.setAdapter(adapterforrecyclerinhomes);
        ab.recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onFailure(ErrorBody errorBody, int statusCode) {
        Toast.makeText(getApplicationContext(), ""+errorBody, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void ShowErrorMessage(MessageViewType messageViewType, String errorMessage) {

    }

    @Override
    public void ShowErrorMessage(MessageViewType messageViewType, ViewType viewType, String errorMessage) {

    }


    public PreferenceManager getPreferenceManager() {
        if (preferenceManager == null) {
            preferenceManager = PreferenceManager.getInstance();
            preferenceManager.initialize(getApplicationContext());
        }
        return preferenceManager;
    }
    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        finish();
    }

    @Override
    public void OpenCard(int index, int id) {
        Intent intent=new Intent(balancehome.this,opencard.class);
        intent.putExtra("index",index);
        intent.putExtra("id",id);
        startActivity(intent);
    }
}