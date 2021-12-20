package com.MDQ.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.DatePickerDialog;
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
import com.MDQ.myapplication.interfaces.viewresponceinterface.DashBoardResponseInterface;
import com.MDQ.myapplication.pojo.jsonresponse.DataForDashBoard;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateAccountListResponseModel;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateDashBoardResponseModel;
import com.MDQ.myapplication.utils.PreferenceManager;
import com.MDQ.myapplication.viewmodel.AccountListViewModel;
import com.MDQ.myapplication.viewmodel.DashBoardViewModel;
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
import java.util.Calendar;
import java.util.List;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.widget.DatePicker;

public class balancehome extends AppCompatActivity implements AccountListResponseInterface , InterfaceForBalanceHome, DashBoardResponseInterface {
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
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    String years,months;
    DashBoardViewModel dashBoardViewModel;
    List<DataForDashBoard> dataForDashBoardList;
    List<String> days ;
    List<Integer> total;
    int year,month,day;
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ab=ActivityBalancehomeBinding.inflate(getLayoutInflater());
        setContentView(ab.getRoot());
        //initializing the variables
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
        days=new ArrayList<>();
        total=new ArrayList<>();


        dashBoardViewModel=new DashBoardViewModel(getApplicationContext(),this);

        //set status bar color as transparent
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }


        //checking internet connection if available proceeds  further else toast error message
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

        //set with adapter
        adapterstab=new Adapterstab(getSupportFragmentManager());

        //navigate to AddCardScreen screen
        ab.addaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(balancehome.this,AddCardScreen.class));
            }
        });

        //navigate to vaultMain screen
        ab.linearvalut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(balancehome.this, vaultMain.class));
            }
        });

        //navigate to profile screen
       ab.linearprofile.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
              startActivity(new Intent(balancehome.this,profile.class));
           }
       });

       //navigate to AddTransaction screen
       ab.addtransaction.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(balancehome.this,AddTransactionscreen.class));
           }
       });

        accountListViewModel =new AccountListViewModel(getApplicationContext(),this);
        setDeclare();

        //navigate to notification screen
        ab.linearnotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Notification.class));
            }
        });





        ab.values.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                 year = cal.get(Calendar.YEAR);
                 month = cal.get(Calendar.MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        balancehome.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getDatePicker().findViewById(getResources().getIdentifier("day","id","android")).setVisibility(View.GONE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                int positiveColor = ContextCompat.getColor(getApplicationContext(), R.color.blue);
                int negativeColor = ContextCompat.getColor(getApplicationContext(), R.color.blue);

                dialog.show();
                dialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(positiveColor);
                dialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(negativeColor);



                years = String.valueOf(year);
                months = String.valueOf(month);

                String monthName = null;
                switch (months){
                    case "1":monthName="jan";
                        break;
                    case "2":monthName="feb";
                        break;
                    case "3":monthName="mar";
                        break;
                    case "4":monthName="apr";
                        break;
                    case "5":monthName="may";
                        break;
                    case "6":monthName="jun";
                        break;
                    case "7":monthName="jul";
                        break;
                    case "8":monthName="aug";
                        break;
                    case "9":monthName="sep";
                        break;
                    case "10":monthName="oct";
                        break;
                    case "11":monthName="nov";
                        break;
                    case "12":monthName="dec";
                        break;

                }
                autoCompleteTextView.setText(monthName+"'"+years);
                serDeclareForDashBoard();
            }
        });



            //select month and year from datePicker
            ab.listimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar cal = Calendar.getInstance();
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog dialog = new DatePickerDialog(
                            balancehome.this,
                            android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                            mDateSetListener,
                            year,month,day);
                    dialog.getDatePicker().findViewById(getResources().getIdentifier("day","id","android")).setVisibility(View.GONE);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    int positiveColor = ContextCompat.getColor(getApplicationContext(), R.color.blue);
                    int negativeColor = ContextCompat.getColor(getApplicationContext(), R.color.blue);

                    dialog.show();
                    dialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(positiveColor);
                    dialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(negativeColor);

                }
            });

            mDateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    month = month + 1;
                    months= String.valueOf(month);
                    years= String.valueOf(year);
                    String monthName = null;
                    switch (month){
                        case 1:monthName="jan";
                            break;
                             case 2:monthName="feb";
                        break;
                             case 3:monthName="mar";
                        break;
                             case 4:monthName="apr";
                        break;
                             case 5:monthName="may";
                        break;
                             case 6:monthName="jun";
                        break;
                             case 7:monthName="jul";
                        break;
                             case 8:monthName="aug";
                        break;
                             case 9:monthName="sep";
                        break;
                             case 10:monthName="oct";
                        break;
                             case 11:monthName="nov";
                        break;
                        case 12:monthName="dec";
                            break;
                            
                    }
                    autoCompleteTextView.setText(monthName+"'"+year);
                    serDeclareForDashBoard();

                }
            };
        }

    /**
     * @breif set request for accountList api
     */
    private void serDeclareForDashBoard() {
            dashBoardViewModel.setMonth(months);
            dashBoardViewModel.setYear(years);
            dashBoardViewModel.setToken(getPreferenceManager().getPrefToken());
            dashBoardViewModel.generateDashBoardRequest();
    }

    //set request for accountList api
    private void setDeclare() {
        String token=getPreferenceManager().getPrefToken();
        accountListViewModel.setToken(token);
        accountListViewModel.generateAccountListRequest();
    }

    //chart for trnsaction
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

        //set data, we got from api in chart
        for(int i=0;i<days.size();i++) {
            yvalue.add(new Entry(i, total.get(i)));
        }
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
        //setting iMarker for chart
        IMarker iMarker=new YourMarkerView(this,R.layout.tvcontent, dataForDashBoardList);
        chart.setMarker(iMarker);

        //set max no of dots visible in x and y axis
        float minXRange = 10;
        float maxXRange = 10;
        chart.setVisibleXRange(minXRange, maxXRange);
    }

    /**
     * @param generateAccountListResponseModel
     * @breif get response from AccountList api
     */
    @Override
    public void generateAccountListProcessed(GenerateAccountListResponseModel generateAccountListResponseModel) {

        InterfaceForBalanceHome interfaceForBalanceHome;
        adapterforrecyclerinhomes=new Adapterforrecyclerinhome(getApplicationContext(),generateAccountListResponseModel,this);
        layoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        ab.recyclerView.setAdapter(adapterforrecyclerinhomes);
        ab.recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void generateDashBoardProcessed(GenerateDashBoardResponseModel generateDashBoardResponseModel) {
        ab.totalamount.setText(generateDashBoardResponseModel.total_amount);
        ab.credit.setText(generateDashBoardResponseModel.total_credit);
        ab.debit.setText(generateDashBoardResponseModel.total_debit);
       dataForDashBoardList=generateDashBoardResponseModel.getDay_data();
       days.removeAll(days);
       total.removeAll(total);
       for(int i=0;i<generateDashBoardResponseModel.getDay_data().size();i++){
           days.add(generateDashBoardResponseModel.getDay_data().get(i).getDay());
           total.add(generateDashBoardResponseModel.getDay_data().get(i).getTotal());
       }
       charts();
    }

    /**
     * @param errorBody
     * @param statusCode
     * @breif if any error during api process toast that error
     */
    @Override
    public void onFailure(ErrorBody errorBody, int statusCode) {
        //Toast.makeText(getApplicationContext(), ""+errorBody, Toast.LENGTH_SHORT).show();

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

    /**
     * @breif close the app when back button has clicked
     */
    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    /**
     * @param index
     * @param id
     * @breif when card has clicked navigate to opencard screen
     */
    @Override
    public void OpenCard(int index, int id) {
        Intent intent=new Intent(balancehome.this,opencard.class);
        intent.putExtra("index",index);
        intent.putExtra("id",id);
        startActivity(intent);
    }
}