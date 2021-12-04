package com.vibro.care.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.onesignal.OneSignal;
import com.vibro.care.Config.AppPref;
import com.vibro.care.Config.Methods;
import com.vibro.care.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import static com.vibro.care.Config.Config.LoginURL;
import static com.vibro.care.Config.Config.accessid;
import static com.vibro.care.Config.Config.device_id;
import static com.vibro.care.Config.Config.id;
import static com.vibro.care.Config.Config.password;
import static com.vibro.care.Config.Config.phone;
import static com.vibro.care.Config.Config.type;
import static com.vibro.care.Config.Methods.checkLogin;

public class LoginActivity extends AppCompatActivity {

    final Handler mHandler = new Handler();
    Button inForget, inOTPLogin, inSignIn;
    TextView inSignUp;
    EditText inPhone, inPassword;
    Spinner inUserType;
    String stringPhone, stringPassword, typeString;
    Context mContext;
    Activity mActivity;
    AppPref pref;
    Dialog pDialog;
    String accessID, userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Objects.requireNonNull(getSupportActionBar()).hide();

        mContext = this;
        mActivity = this;
        pref = new AppPref(mActivity);
        pDialog = new Dialog(mActivity, R.style.Dialog);
        pDialog.setContentView(R.layout.loading);

        inForget = findViewById(R.id.inForget);
        inOTPLogin = findViewById(R.id.inOTPLogin);

        inSignUp = findViewById(R.id.inSignUp);
        inSignIn = findViewById(R.id.inSignIn);

        inPhone = findViewById(R.id.inPhone);
        inPassword = findViewById(R.id.inPassword);
        inUserType = findViewById(R.id.inUserType);

        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(mContext, R.array.login_type, R.layout.child_spinner);
        typeAdapter.setDropDownViewResource(R.layout.child_spinner);
        inUserType.setAdapter(typeAdapter);

        inUserType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0){
                    typeString = String.valueOf(position);
                } else
                    typeString = "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        inSignIn.setOnClickListener(v -> {
            stringPhone = inPhone.getText().toString();
            stringPassword = inPassword.getText().toString();

            if(stringPhone.length()==0 || stringPhone.length() < 10){
                inPhone.setError("Please enter a valid phone number");
                inPhone.requestFocus();
            } else if(stringPassword.length()==0){
                inPassword.setError("Please enter a password");
                inPassword.requestFocus();
            } else if(typeString.length()==0){
                Toast.makeText(mContext, "Please select user type", Toast.LENGTH_SHORT).show();
                inUserType.performClick();
            } else if(Methods.checkConnection(mContext)) {
                userLogin(stringPhone, stringPassword, typeString, mActivity, pDialog);
            } else {
                Methods.showSettingsAlert(mContext);
            }
        });

        inSignUp.setOnClickListener(v -> startActivity(new Intent(mContext, SignupActivity.class)));

        View.OnClickListener notAvailable = view -> Toast.makeText(mContext, "Not Available!", Toast.LENGTH_SHORT).show();

        inForget.setOnClickListener(v -> startActivity(new Intent(mContext, ForgetPasswordActivity.class)));

        inOTPLogin.setOnClickListener(notAvailable);

    }

    void userLogin(final String stringPhone, final String stringPassword,
                   final String typeString, final Activity mActivity, final Dialog pDialog) {

        Methods.showDialog(pDialog);

        new Thread(() -> {

            Looper.prepare();

            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = RequestBody.create("", mediaType);
            Request request = new Request.Builder()
                    .url(LoginURL + "?" + phone + "=" + stringPhone + "&" + password + "=" + stringPassword
                            + "&" + type + "=" + typeString + "&" +  device_id + "=" + Objects.requireNonNull(OneSignal.getDeviceState()).getUserId())
                    .method("POST", body)
                    .build();
            try {
                String response = Objects.requireNonNull(client.newCall(request).execute().body()).string();

                JSONObject loginResp = new JSONObject(response);
                JSONArray loginResponse = loginResp.getJSONArray("Login");

                final JSONObject logResponse = loginResponse.getJSONObject(0);
                boolean error = logResponse.getBoolean("ERROR");
                //final boolean old = logResponse.getBoolean("old");
                final String errorMsg = logResponse.getString("ERRORMSG");

                if(!error){
                    accessID = logResponse.getString(accessid);
                    userID = logResponse.getString(id);
                }

                runOnUiThread(() -> {
                    if (!error) {
                        pref.putResponse(accessid, accessID);
                        pref.putResponse(id, userID);
                        pref.putResponse(type, typeString);

                            /*JSONArray userResp = logResponse.getJSONArray("User");
                            JSONObject userResponse = userResp.getJSONObject(0);

                            putData(pref, userResponse);*/

                        mHandler.postDelayed(() -> checkLogin(pref, mActivity, pDialog, errorMsg, 0), 1000);

                    } else {

                        Methods.hideDialog(pDialog, mActivity, true);
                        Toast.makeText(mActivity, errorMsg, Toast.LENGTH_LONG).show();
                            /*if(!old)
                                Toast.makeText(mActivity, errorMsg, Toast.LENGTH_LONG).show();
                            else
                                LoginActivity.showSnack(errorMsg);*/
                        //mActivity.startActivity(new Intent(mActivity, LoginActivity.class));
                        //mActivity.finish();

                    }
                });

            } catch (IOException|JSONException e) {
                runOnUiThread(() -> {
                    e.printStackTrace();
                    Methods.hideDialog(pDialog, mActivity, true);
                    Toast.makeText(mActivity, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
            }
        }).start();
    }
}
