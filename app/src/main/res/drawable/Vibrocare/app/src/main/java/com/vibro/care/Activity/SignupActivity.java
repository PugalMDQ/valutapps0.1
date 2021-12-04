package com.vibro.care.Activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.vibro.care.Config.Methods;
import com.vibro.care.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import static com.vibro.care.Config.Config.AADHAR;
import static com.vibro.care.Config.Config.GST;
import static com.vibro.care.Config.Config.MainURL;
import static com.vibro.care.Config.Config.PAN;
import static com.vibro.care.Config.Config.PIN;
import static com.vibro.care.Config.Config.RegistrationURL;
import static com.vibro.care.Config.Config.address;
import static com.vibro.care.Config.Config.business;
import static com.vibro.care.Config.Config.category;
import static com.vibro.care.Config.Config.city;
import static com.vibro.care.Config.Config.company_type;
import static com.vibro.care.Config.Config.country;
import static com.vibro.care.Config.Config.date;
import static com.vibro.care.Config.Config.email;
import static com.vibro.care.Config.Config.experience;
import static com.vibro.care.Config.Config.gender;
import static com.vibro.care.Config.Config.instruments;
import static com.vibro.care.Config.Config.name;
import static com.vibro.care.Config.Config.person;
import static com.vibro.care.Config.Config.phone;
import static com.vibro.care.Config.Config.projects;
import static com.vibro.care.Config.Config.service;
import static com.vibro.care.Config.Config.size;
import static com.vibro.care.Config.Config.state;
import static com.vibro.care.Config.Config.table;
import static com.vibro.care.Config.Config.website;

public class SignupActivity extends AppCompatActivity {

    long back_pressed;
    String params, stringName, stringPerson, stringEmail, stringPhone, stringGender = "", stringAddress, stringWebsite,
            stringGST, stringPAN, stringDate, stringAADHAR, stringCountry, stringState, stringCity, stringPIN,
            stringBusinessType, stringServiceOffered, stringExperience, stringInstruments = "", stringProjects,
            stringType = "", stringSize = "", stringCategory = "";
    String[] dataInstruments;
    int countInstruments = 0;

    Activity mActivity;
    Context mContext;
    Dialog pDialog;
    InputMethodManager imm;
    LayoutInflater inflater;

    RelativeLayout tabCompany, tabIndividual;
    LinearLayout linType, linDetails, linComInstruments, linIndInstruments;
    TextView textComInstruments, textIndInstruments, upComServiceOfferedTitle;
    Button butCompany, butIndividual, upComSign, upIndSign;
    EditText upComName, upComPerson, upComEmail, upComPhone, upComAddress,
            upComWebsite, upComGST, upComPAN, upComIncDate, upComCountry, upComState, upComCity, upComPin, upComExp, upComProjects;
    EditText upIndName, upIndEmail ;
    Spinner upComBusinessNature, upComType, upComSize, upComCategory;
            //upIndBusinessNature;
    CheckBox upComServiceOffered_1, upComServiceOffered_2, upComServiceOffered_3, upComServiceOffered_4, upComServiceOffered_5, upComServiceOffered_6, upComServiceOffered_7, upComServiceOffered_8,
            upIndServiceOffered_1, upIndServiceOffered_2, upIndServiceOffered_3, upIndServiceOffered_4, upIndServiceOffered_5, upIndServiceOffered_6, upIndServiceOffered_7, upIndServiceOffered_8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Registration");

        mActivity = this;
        mContext = this;
        imm = (InputMethodManager) mContext.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        pDialog = new Dialog(mActivity, R.style.Dialog);
        pDialog.setContentView(R.layout.loading);


        butCompany = findViewById(R.id.butCompany);
        butIndividual = findViewById(R.id.butIndividual);
        tabCompany = findViewById(R.id.tabCompany);
        tabIndividual = findViewById(R.id.tabIndividual);
        upComSign = findViewById(R.id.upComSign);
        upIndSign = findViewById(R.id.upIndSign);

        upComName = findViewById(R.id.upComName);
        upComPerson = findViewById(R.id.upComPerson);
        upComEmail = findViewById(R.id.upComEmail);
        upComPhone = findViewById(R.id.upComPhone);
        upComAddress = findViewById(R.id.upComAddress);
        upComWebsite = findViewById(R.id.upComWebsite);
        upComGST = findViewById(R.id.upComGST);
        upComPAN = findViewById(R.id.upComPAN);
        upComIncDate = findViewById(R.id.upComIncDate);
        upComCountry = findViewById(R.id.upComCountry);
        upComState = findViewById(R.id.upComState);
        upComCity = findViewById(R.id.upComCity);
        upComPin = findViewById(R.id.upComPin);
        upComBusinessNature = findViewById(R.id.upComBusinessNature);
        upComServiceOfferedTitle = findViewById(R.id.upComServiceOfferedTitle);
        upComServiceOffered_1 = findViewById(R.id.upComServiceOffered_1);
        upComServiceOffered_2 = findViewById(R.id.upComServiceOffered_2);
        upComServiceOffered_3 = findViewById(R.id.upComServiceOffered_3);
        upComServiceOffered_4 = findViewById(R.id.upComServiceOffered_4);
        upComServiceOffered_5 = findViewById(R.id.upComServiceOffered_5);
        upComServiceOffered_6 = findViewById(R.id.upComServiceOffered_6);
        upComServiceOffered_7 = findViewById(R.id.upComServiceOffered_7);
        upComServiceOffered_8 = findViewById(R.id.upComServiceOffered_8);
        upComExp = findViewById(R.id.upComExp);
        linType = findViewById(R.id.linType);
        linDetails = findViewById(R.id.linDetails);
        linComInstruments = findViewById(R.id.linComInstruments);
        textComInstruments = findViewById(R.id.textComInstruments);
        upComProjects = findViewById(R.id.upComProjects);
        upComType = findViewById(R.id.upComType);
        upComSize = findViewById(R.id.upComSize);
        upComCategory = findViewById(R.id.upComCategory);

        upIndName = findViewById(R.id.upIndName);
        upIndEmail = findViewById(R.id.upIndEmail);
//        upIndPhone = findViewById(R.id.upIndPhone);
//        upIndGender = findViewById(R.id.upIndGender);
//        upIndAddress = findViewById(R.id.upIndAddress);
//        upIndPAN = findViewById(R.id.upIndPAN);
//        upIndWebsite = findViewById(R.id.upIndWebsite);
//        upIndAADHAR = findViewById(R.id.upIndAADHAR);
//        upIndCountry = findViewById(R.id.upIndCountry);
//        upIndState = findViewById(R.id.upIndState);
//        upIndCity = findViewById(R.id.upIndCity);
//        upIndPin = findViewById(R.id.upIndPin);
//        upIndExp = findViewById(R.id.upIndExp);
//        upIndBusinessNature = findViewById(R.id.upIndBusinessNature);
          upIndServiceOffered_1 = findViewById(R.id.upIndServiceOffered_1);
        upIndServiceOffered_2 = findViewById(R.id.upIndServiceOffered_2);
        upIndServiceOffered_3 = findViewById(R.id.upIndServiceOffered_3);
        upIndServiceOffered_4 = findViewById(R.id.upIndServiceOffered_4);
        upIndServiceOffered_5 = findViewById(R.id.upIndServiceOffered_5);
        upIndServiceOffered_6 = findViewById(R.id.upIndServiceOffered_6);
        upIndServiceOffered_7 = findViewById(R.id.upIndServiceOffered_7);
        upIndServiceOffered_8 = findViewById(R.id.upIndServiceOffered_8);
        linIndInstruments = findViewById(R.id.linIndInstruments);
        textIndInstruments = findViewById(R.id.textIndInstruments);
        //upIndProjects = findViewById(R.id.upIndProjects);

        tabIndividual.setVisibility(View.VISIBLE);
        initCompanySpinners();

        butCompany.setOnClickListener(v -> {
            butCompany.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            butIndividual.setTextColor(getResources().getColor(R.color.colorWhite));
            butCompany.setBackgroundResource(R.drawable.select_dialog);
            butIndividual.setBackgroundResource(R.drawable.tab_dialog);
            tabCompany.setVisibility(View.VISIBLE);
            tabIndividual.setVisibility(View.GONE);
            initCompanySpinners();
        });

        butIndividual.setOnClickListener(v -> {
            butCompany.setTextColor(getResources().getColor(R.color.colorWhite));
            butIndividual.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            butCompany.setBackgroundResource(R.drawable.tab_dialog);
            butIndividual.setBackgroundResource(R.drawable.select_dialog);
            tabCompany.setVisibility(View.GONE);
            tabIndividual.setVisibility(View.VISIBLE);
//            initGender();
            //initIndividualSpinners();
        });

        upComIncDate.setOnClickListener(view -> pickDate());

        linComInstruments.setOnClickListener(view -> addInstruments(textComInstruments));

        linIndInstruments.setOnClickListener(view -> addInstruments(textIndInstruments));

        upComSign.setOnClickListener(v -> {

            stringName = upComName.getText().toString().trim();
            stringPerson = upComPerson.getText().toString().trim();
            stringEmail = upComEmail.getText().toString().trim();
            stringPhone = upComPhone.getText().toString().trim();
            stringAddress = upComAddress.getText().toString().trim();
            stringWebsite = upComWebsite.getText().toString().trim();
            stringGST = upComGST.getText().toString().trim();
            stringPAN = upComPAN.getText().toString().trim();
            stringDate = upComIncDate.getText().toString().trim();
            stringCountry = upComCountry.getText().toString().trim();
            stringState = upComState.getText().toString().trim();
            stringCity = upComCity.getText().toString().trim();
            stringPIN = upComPin.getText().toString().trim();
            stringExperience = upComExp.getText().toString().trim();
            stringProjects = upComProjects.getText().toString().trim();

            stringServiceOffered = "";

            if(!stringType.equals("Service Provider") && stringExperience.isEmpty())
                stringExperience = "0";

            if (upComServiceOffered_1.isChecked())
                stringServiceOffered += upComServiceOffered_1.getText().toString() + ",";

            if (upComServiceOffered_2.isChecked())
                stringServiceOffered += upComServiceOffered_2.getText().toString() + ",";

            if (upComServiceOffered_3.isChecked())
                stringServiceOffered += upComServiceOffered_3.getText().toString() + ",";

            if (upComServiceOffered_4.isChecked())
                stringServiceOffered += upComServiceOffered_4.getText().toString() + ",";

            if (upComServiceOffered_5.isChecked())
                stringServiceOffered += upComServiceOffered_5.getText().toString() + ",";

            if (upComServiceOffered_6.isChecked())
                stringServiceOffered += upComServiceOffered_6.getText().toString() + ",";

            if (upComServiceOffered_7.isChecked())
                stringServiceOffered += upComServiceOffered_7.getText().toString() + ",";

            if (upComServiceOffered_8.isChecked())
                stringServiceOffered += upComServiceOffered_8.getText().toString() + ",";

            if (stringServiceOffered.endsWith(","))
                stringServiceOffered = stringServiceOffered.substring(0, stringServiceOffered.length() - 1);

            if (stringType.isEmpty()) {
                Toast.makeText(mActivity, "Please select the type of company", Toast.LENGTH_SHORT).show();
                upComType.performClick();

            } else if (stringType.equals("Service User") && stringSize.isEmpty()) {
                Toast.makeText(mActivity, "Please select the size of company", Toast.LENGTH_SHORT).show();
                upComSize.performClick();

            } else if (stringType.equals("Service User") && stringCategory.isEmpty()) {
                Toast.makeText(mActivity, "Please select the category of company", Toast.LENGTH_SHORT).show();
                upComCategory.performClick();

            } else if (stringName.isEmpty()) {
                upComName.setError("Please enter company name");
                upComName.requestFocus();
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

            } else if (stringPerson.isEmpty()) {
                upComPerson.setError("Please enter contact person name");
                upComPerson.requestFocus();
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

            } else if (stringEmail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(stringEmail).matches()) {
                upComEmail.setError("Please enter a valid email");
                upComEmail.requestFocus();
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

            } else if (stringPhone.length() != 10) {
                upComPhone.setError("Please enter a valid phone no");
                upComPhone.requestFocus();
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

            } else if (stringAddress.isEmpty()) {
                upComAddress.setError("Please enter a valid office address");
                upComAddress.requestFocus();
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

            } else if (stringGST.isEmpty()) {
                upComGST.setError("Please enter a valid GST no");
                upComGST.requestFocus();
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

            } else if (stringPAN.isEmpty()) {
                upComPAN.setError("Please enter a valid PAN no");
                upComPAN.requestFocus();
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

            } else if (stringDate.isEmpty()) {
                pickDate();

            } else if (stringCountry.isEmpty()) {
                upComCountry.setError("Please enter a valid country");
                upComCountry.requestFocus();
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

            } else if (stringState.isEmpty()) {
                upComState.setError("Please enter a valid state");
                upComState.requestFocus();
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

            } else if (stringCity.isEmpty()) {
                upComCity.setError("Please enter a valid city");
                upComCity.requestFocus();
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

            } else if (stringPIN.length() < 6) {
                upComPin.setError("Please enter a valid PIN code");
                upComPin.requestFocus();
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

            } else if (stringBusinessType.isEmpty()) {
                Toast.makeText(mActivity, "Please select a business type", Toast.LENGTH_SHORT).show();
                upComBusinessNature.performClick();

            } else if (stringServiceOffered.isEmpty()) {
                Toast.makeText(mActivity, "Please select at least one offered service", Toast.LENGTH_SHORT).show();

            } else if (stringType.equals("Service Provider") && stringExperience.isEmpty()) {
                upComExp.setError("Please enter a valid experience");
                upComExp.requestFocus();
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

            } else if (stringType.equals("Service Provider") && stringInstruments.isEmpty()) {
                Toast.makeText(mActivity, "Please enter instruments details", Toast.LENGTH_SHORT).show();
                linComInstruments.performClick();

            } else if (stringType.equals("Service Provider") && stringProjects.isEmpty()) {
                upComProjects.setError("Please enter handled project details");
                upComProjects.requestFocus();
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

            } else if (Methods.checkConnection(mContext)) {
                showTerms("Individual");
            } else
                Methods.showSettingsAlert(mContext);
        });

        upIndSign.setOnClickListener(v -> {

            stringName = upIndName.getText().toString();
            stringEmail = upIndEmail.getText().toString();
//            stringPhone = upIndPhone.getText().toString();
//            stringAddress = upIndAddress.getText().toString();
//            stringPAN = upIndPAN.getText().toString();
//            stringWebsite = upIndWebsite.getText().toString();
//            stringAADHAR = upIndAADHAR.getText().toString();
//            stringCountry = upIndCountry.getText().toString();
//            stringState = upIndState.getText().toString();
//            stringCity = upIndCity.getText().toString();
//            stringPIN = upIndPin.getText().toString();
//            stringExperience = upIndExp.getText().toString();
//            stringProjects = upIndProjects.getText().toString();

            stringServiceOffered = "";

            if (upIndServiceOffered_1.isChecked())
                stringServiceOffered += upIndServiceOffered_1.getText().toString() + ",";

            if (upIndServiceOffered_2.isChecked())
                stringServiceOffered += upIndServiceOffered_2.getText().toString() + ",";

            if (upIndServiceOffered_3.isChecked())
                stringServiceOffered += upIndServiceOffered_3.getText().toString() + ",";

            if (upIndServiceOffered_4.isChecked())
                stringServiceOffered += upIndServiceOffered_4.getText().toString() + ",";

            if (upIndServiceOffered_5.isChecked())
                stringServiceOffered += upIndServiceOffered_5.getText().toString() + ",";

            if (upIndServiceOffered_6.isChecked())
                stringServiceOffered += upIndServiceOffered_6.getText().toString() + ",";

            if (upIndServiceOffered_7.isChecked())
                stringServiceOffered += upIndServiceOffered_7.getText().toString() + ",";

            if (upIndServiceOffered_8.isChecked())
                stringServiceOffered += upIndServiceOffered_8.getText().toString() + ",";

            if (stringServiceOffered.endsWith(","))
                stringServiceOffered = stringServiceOffered.substring(0, stringServiceOffered.length() - 1);

            if (stringName.isEmpty()) {
                upIndName.setError("Please enter your name");
                upIndName.requestFocus();
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

            } else if (stringEmail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(stringEmail).matches()) {
                upIndEmail.setError("Please enter a valid email");
                upIndEmail.requestFocus();
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

//            } else if (stringPhone.length() != 10) {
//                upIndPhone.setError("Please enter a valid phone no");
//                upIndPhone.requestFocus();
//                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }
//            } else if (stringGender.length() == 0) {
//                Toast.makeText(mActivity, "Please select a gender", Toast.LENGTH_SHORT).show();
////                upIndGender.performClick();
//
//            } else if (stringAddress.isEmpty()) {
//                upIndAddress.setError("Please enter a valid address");
//                upIndAddress.requestFocus();
//                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
//
//            } else if (stringPAN.isEmpty()) {
//                upIndPAN.setError("Please enter a valid PAN no");
//                upIndPAN.requestFocus();
//                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
//
//            } else if (stringAADHAR.length() < 12) {
//                upIndAADHAR.setError("Please enter a valid AADHAR no");
//                upIndAADHAR.requestFocus();
//                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
//
//            } else if (stringCountry.isEmpty()) {
//                upIndCountry.setError("Please enter a valid country");
//                upIndCountry.requestFocus();
//                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
//
//            } else if (stringState.isEmpty()) {
//                upIndState.setError("Please enter a valid state");
//                upIndState.requestFocus();
//                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
//
//            } else if (stringCity.isEmpty()) {
//                upIndCity.setError("Please enter a valid city");
//                upIndCity.requestFocus();
//                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
//
//            } else if (stringPIN.length() < 6) {
//                upIndPin.setError("Please enter a valid PIN code");
//                upIndPin.requestFocus();
//                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

//             else if (stringBusinessType.isEmpty()) {
//                Toast.makeText(mActivity, "Please select a business type", Toast.LENGTH_SHORT).show();
//                upIndBusinessNature.performClick();}

             else if (stringServiceOffered.isEmpty()) {
                Toast.makeText(mActivity, "Please select at least one offered service", Toast.LENGTH_SHORT).show();

//            } else if (stringExperience.isEmpty()) {
//                upIndExp.setError("Please enter a valid experience");
//                upIndExp.requestFocus();
//                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

            } else if (stringInstruments.isEmpty()) {
                Toast.makeText(mActivity, "Please enter instruments details", Toast.LENGTH_SHORT).show();
                linIndInstruments.performClick();

//            } else if (stringProjects.isEmpty()) {
//                upIndProjects.setError("Please enter handled project details");
//                upIndProjects.requestFocus();
//                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

            } else if (Methods.checkConnection(mContext)) {
                showTerms("Individual");
            } else
                Methods.showSettingsAlert(mContext);
        });
    }

    private void showTerms(String type) {
        Dialog termsDialog = new Dialog(mActivity);
        termsDialog.setContentView(R.layout.dialog_terms);
        termsDialog.getWindow().setBackgroundDrawableResource(R.drawable.corner);
        termsDialog.setCanceledOnTouchOutside(false);
        termsDialog.setCancelable(false);
        termsDialog.show();

        int width = (int)(mActivity.getResources().getDisplayMetrics().widthPixels*0.85);
        termsDialog.getWindow().setLayout(width, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        termsDialog.getWindow().setGravity(Gravity.CENTER);

        final WebView webviewTerms = termsDialog.findViewById(R.id.webviewTerms);
        final Button buttonDecline = termsDialog.findViewById(R.id.buttonDecline);
        final Button buttonAccept = termsDialog.findViewById(R.id.buttonAccept);


        webviewTerms.loadUrl(MainURL + "t_n_c.html");

        buttonDecline.setOnClickListener(view -> {
            termsDialog.dismiss();
            Toast.makeText(mActivity, "You must agree to the Terms & Conditions!", Toast.LENGTH_SHORT).show();
        });

        buttonAccept.setOnClickListener(view -> {
            termsDialog.dismiss();

            if(type.equals("Company")){

                params = "?" + table + "=" + "tbl_company" + "&" + name + "=" + stringName + "&" + person + "=" + stringPerson +
                        "&" + email + "=" + stringEmail + "&" + phone + "=" + stringPhone + "&" + address + "=" + stringAddress +
                        "&" + website + "=" + stringWebsite + "&" + GST + "=" + stringGST + "&" + PAN + "=" + stringPAN +
                        "&" + date + "=" + stringDate + "&" + country + "=" + stringCountry + "&" + state + "=" + stringState +
                        "&" + city + "=" + stringCity + "&" + PIN + "=" + stringPIN + "&" + business + "=" + stringBusinessType +
                        "&" + service + "=" + stringServiceOffered + "&" + experience + "=" + stringExperience +
                        "&" + instruments + "=" + stringInstruments + "&" + projects + "=" + stringProjects + "&" + company_type + "=" + stringType +
                        "&" + size + "=" + stringSize + "&" + category + "=" + stringCategory;

            } else if(type.equals("Individual")){

                params = "?" + table + "=tbl_individual&" + name + "=" + stringName + "&" + email + "=" + stringEmail +
                        "&" + phone + "=" + stringPhone + "&" + gender + "=" + stringGender + "&" + address + "=" + stringAddress +
                        "&" + PAN + "=" + stringPAN + "&" + website + "=" + stringWebsite + "&" + AADHAR + "=" + stringAADHAR +
                        "&" + country + "=" + stringCountry + "&" + state + "=" + stringState +
                        "&" + city + "=" + stringCity + "&" + PIN + "=" + stringPIN + "&" + business + "=" + stringBusinessType +
                        "&" + service + "=" + stringServiceOffered + "&" + experience + "=" + stringExperience +
                        "&" + instruments + "=" + stringInstruments + "&" + projects + "=" + stringProjects;

            }

            registration();
        });
    }

    private void addInstruments(final TextView textInstruments) {
        final boolean[] status = {true};

        final Dialog instrumentsDialog = new Dialog(mContext);
        instrumentsDialog.setContentView(R.layout.dialog_instruments);
        instrumentsDialog.getWindow().setBackgroundDrawable(null);
        instrumentsDialog.setCanceledOnTouchOutside(false);
        //instrumentsDialog.getWindow().getAttributes().windowAnimations = R.style.DialogSlideUpDown;
        instrumentsDialog.show();

        final TableLayout.LayoutParams tabLayoutParams
                = new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tabLayoutParams.setMargins(10, 10, 10, 10);

        final int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.95);
        final int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.8);
        instrumentsDialog.getWindow().setLayout(width, height);
        instrumentsDialog.getWindow().setGravity(Gravity.CENTER);


        final ScrollView scrollInstruments = instrumentsDialog.findViewById(R.id.scrollInstruments);

        final LinearLayout linInstruments = instrumentsDialog.findViewById(R.id.linInstruments);

        int length = 0;
        if (!stringInstruments.isEmpty()) {
            dataInstruments = stringInstruments.split(";");
            length = dataInstruments.length;
        }


        if (length == 0) {
            countInstruments = 1;
        } else {
            for (int i = 0; i < length; i++) {
                countInstruments = i + 1;
                addRowInput(countInstruments, linInstruments, 1);
            }
            countInstruments++;
        }
        addRowInput(countInstruments, linInstruments, 0);

        LinearLayout addField = instrumentsDialog.findViewById(R.id.addField);
        addField.setOnClickListener(view -> {
            countInstruments++;
            addRowInput(countInstruments, linInstruments, 0);
            scrollInstruments.smoothScrollTo(0, scrollInstruments.getBottom());
        });

        final Button addInstruments = instrumentsDialog.findViewById(R.id.addInstruments);
        addInstruments.setOnClickListener(v -> {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            stringInstruments = "";
            boolean error = false;

            for (int i = 0; i < linInstruments.getChildCount(); i++) {
                LinearLayout view = (LinearLayout) linInstruments.getChildAt(i);

                EditText name = view.findViewById(R.id.addName);
                EditText make = view.findViewById(R.id.addMake);
                EditText qty = view.findViewById(R.id.addQty);

                if (name.getText().toString().isEmpty()) {
                    name.requestFocus();
                    name.setError("Please fill this field");
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                    error = true;
                } else if (make.getText().toString().isEmpty()) {
                    make.requestFocus();
                    make.setError("Please fill this field");
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                    error = true;
                } else if (qty.getText().toString().isEmpty()) {
                    qty.requestFocus();
                    make.setError("Please fill this field");
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                    error = true;
                } else {
                    stringInstruments += name.getText().toString() + "_" + make.getText().toString() + "_" + qty.getText().toString() + ";";
                }
            }

            if (!error)
                instrumentsDialog.dismiss();
        });

        instrumentsDialog.setOnKeyListener((arg0, keyCode, event) -> {
            // TODO Auto-generated method stub
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                status[0] = false;
            }
            return false;
        });

        instrumentsDialog.setOnDismissListener(dialogInterface -> {
            if (status[0]) {
                if (countInstruments == 1)
                    textInstruments.setText(countInstruments + " instrument added. Click to add more...");
                else if (countInstruments > 1)
                    textInstruments.setText(countInstruments + " instruments added. Click to add more...");
                else {
                    textInstruments.setText("ADD INSTRUMENTS IN HAND");
                    countInstruments = 0;
                }
            }

        });
    }

    private void addRowInput(int count, LinearLayout linInstruments, int tag) {

        final LinearLayout child = (LinearLayout) inflater.inflate(R.layout.row_instrument, linInstruments, false);

        TextView textInstruments = child.findViewById(R.id.textInstruments);
        final EditText addName = child.findViewById(R.id.addName);
        final EditText addMake = child.findViewById(R.id.addMake);
        final EditText addQty = child.findViewById(R.id.addQty);

        textInstruments.setText(count + ". ");

        if (tag == 1) {
            String data = dataInstruments[count - 1];
            String[] instruments = data.split("_");
            addName.setText(instruments[0]);
            addMake.setText(instruments[1]);
            addQty.setText(instruments[2]);
        } else {
            addName.requestFocus();
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }

        linInstruments.addView(child);

    }

    private void pickDate() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        new DatePickerDialog(mContext,
                R.style.datePicker,
                (view, year, month, dayOfMonth) -> upComIncDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year),
                mYear, mMonth, mDay)
                .show();
    }

//    private void initGender() {
//
//        final List<String> gender_array = new ArrayList<>(Arrays.asList("Please select gender", "Female", "Male", "Other"));
//
////        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(mContext, R.layout.child_spinner, gender_array);
////        genderAdapter.setDropDownViewResource(R.layout.child_spinner);
////        upIndGender.setAdapter(genderAdapter);
//
////        upIndGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
////            @Override
////            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
////                if (position > 0) {
////                    stringGender = gender_array.get(position);
////                } else
////                    stringGender = "";
////            }
////
////            @Override
////            public void onNothingSelected(AdapterView<?> parent) {
////
////            }
////        });
//    }

    private void initCompanySpinners() {

        final List<String> businesstype_array = new ArrayList<>(
                Arrays.asList("Please select Nature of Business", "Proprietorship",
                        "Private Limited", "Public Limited", "Consultancy", "Partnership", "Corporation"));

        ArrayAdapter<String> businesstypeAdapter = new ArrayAdapter<>(mContext, R.layout.child_spinner, businesstype_array);
        businesstypeAdapter.setDropDownViewResource(R.layout.child_spinner);
        upComBusinessNature.setAdapter(businesstypeAdapter);

        upComBusinessNature.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    stringBusinessType = businesstype_array.get(position);
                } else
                    stringBusinessType = "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final List<String> companytype_array = new ArrayList<>(
                Arrays.asList("Please select Type of Company", "Service User", "Service Provider"));

        ArrayAdapter<String> companytypeAdapter = new ArrayAdapter<>(mContext, R.layout.child_spinner, companytype_array);
        companytypeAdapter.setDropDownViewResource(R.layout.child_spinner);
        upComType.setAdapter(companytypeAdapter);

        upComType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    stringType = String.valueOf(position - 1);
                    if (position == 1) {
                        linDetails.setVisibility(View.GONE);
                        linType.setVisibility(View.VISIBLE);
                        upComServiceOfferedTitle.setText(getString(R.string.service_required));
                    } else if (position == 2) {
                        linDetails.setVisibility(View.VISIBLE);
                        linType.setVisibility(View.GONE);
                        upComServiceOfferedTitle.setText(getString(R.string.service_offered));
                    }
                } else {
                    stringType = "";
                    linDetails.setVisibility(View.GONE);
                    linType.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final List<String> companysize_array = new ArrayList<>(
                Arrays.asList("Please select Size of Company", "Large", "Medium", "Small"));

        ArrayAdapter<String> companysizeAdapter = new ArrayAdapter<>(mContext, R.layout.child_spinner, companysize_array);
        companysizeAdapter.setDropDownViewResource(R.layout.child_spinner);
        upComSize.setAdapter(companysizeAdapter);

        upComSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    stringSize = companysize_array.get(position);
                } else
                    stringSize = "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final List<String> companycategory_array = new ArrayList<>(
                Arrays.asList("Please select Category of Company", "Automotive", "Boiler Industries", "Cement",
                        "Chemical", "Fertilizers", "Oil and Gas", "Others"));

        ArrayAdapter<String> companycategoryAdapter = new ArrayAdapter<>(mContext, R.layout.child_spinner, companycategory_array);
        companycategoryAdapter.setDropDownViewResource(R.layout.child_spinner);
        upComCategory.setAdapter(companycategoryAdapter);

        upComCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    stringCategory = companycategory_array.get(position);
                } else
                    stringCategory = "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

//    private void initIndividualSpinners() {
//
//        final List<String> businesstype_array = new ArrayList<>(
//                Arrays.asList("Please select Nature of Business", "Entrepreneur", "Employer", "Full Time", "Part Time "));
//
//        ArrayAdapter<String> businesstypeAdapter = new ArrayAdapter<>(mContext, R.layout.child_spinner, businesstype_array);
//        businesstypeAdapter.setDropDownViewResource(R.layout.child_spinner);
//        upIndBusinessNature.setAdapter(businesstypeAdapter);
//
//        upIndBusinessNature.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (position > 0) {
//                    stringBusinessType = businesstype_array.get(position);
//                } else
//                    stringBusinessType = "";
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//    }

    void registration() {
        Methods.showDialog(pDialog);

        new Thread(() -> {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = RequestBody.create("", mediaType);
            Request request = new Request.Builder()
                    .url(RegistrationURL + params)
                    .method("POST", body)
                    .build();
            try {
                String response = Objects.requireNonNull(client.newCall(request).execute().body()).string();
                JSONObject resultObject = new JSONObject(response);
                final boolean error = resultObject.getBoolean("error");
                final String msg = resultObject.getString("message");

                runOnUiThread(() -> {
                    Methods.hideDialog(pDialog, mActivity, error);
                    new Handler().postDelayed(() -> Toast.makeText(mActivity, msg, Toast.LENGTH_LONG).show(), 3000);
                });
            } catch (IOException | JSONException e) {
                runOnUiThread(() -> {
                    Methods.hideDialog(pDialog, mActivity, true);
                    e.printStackTrace();
                    Toast.makeText(mActivity, "Registration failed!!! Try again...\nReason: " + e.toString(), Toast.LENGTH_SHORT).show();
                });
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            finish();
        } else {
            Toast.makeText(mActivity, "You are one tap away from leaving the page!", Toast.LENGTH_SHORT).show();
        }

        back_pressed = System.currentTimeMillis();
    }
}
