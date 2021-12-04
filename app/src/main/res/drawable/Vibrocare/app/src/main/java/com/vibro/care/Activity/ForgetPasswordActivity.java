package com.vibro.care.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vibro.care.Config.Methods;
import com.vibro.care.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import static com.vibro.care.Config.Config.ForgetURL;

public class ForgetPasswordActivity extends AppCompatActivity {

    Button forgetReset;
    EditText forgetEmail, forgetPhone, forgetPassword, forgetPasswordConfirm;
    Spinner forgetUserType;

    String stringEmail, stringPhone, stringPassword, stringPasswordConfirm, typeString;
    Context mContext;
    Activity mActivity;
    Dialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.change_password));

        mContext = this;
        mActivity = this;
        pDialog = new Dialog(mActivity, R.style.Dialog);
        pDialog.setContentView(R.layout.loading);

        forgetReset = findViewById(R.id.forgetReset);

        forgetEmail = findViewById(R.id.forgetEmail);
        forgetPhone = findViewById(R.id.forgetPhone);
        forgetPassword = findViewById(R.id.forgetPassword);
        forgetPasswordConfirm = findViewById(R.id.forgetPasswordConfirm);

        forgetUserType = findViewById(R.id.forgetUserType);

        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(mContext, R.array.login_type, R.layout.child_spinner);
        typeAdapter.setDropDownViewResource(R.layout.child_spinner);
        forgetUserType.setAdapter(typeAdapter);

        forgetUserType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        forgetReset.setOnClickListener(v -> {
            stringEmail = forgetEmail.getText().toString();
            stringPhone = forgetPhone.getText().toString();
            stringPassword = forgetPassword.getText().toString();
            stringPasswordConfirm = forgetPasswordConfirm.getText().toString();

            if(typeString.length() == 0){

                Toast.makeText(mContext, "Please select user type", Toast.LENGTH_SHORT).show();
                forgetUserType.performClick();

            } else if(stringEmail.length()==0){

                forgetEmail.setError("Please enter registered email ID");
                forgetEmail.requestFocus();

            } else if(stringPhone.length()==0){

                forgetPhone.setError("Please enter registered phone number");
                forgetPhone.requestFocus();

            } else if(stringPassword.isEmpty() || stringPassword.length()<8){

                forgetPassword.setError("Please enter a valid minimum 8 character Password");
                forgetPassword.requestFocus();

            } else if(stringPasswordConfirm.isEmpty() || !stringPasswordConfirm.equals(stringPassword)){

                forgetPasswordConfirm.setError("Please enter the same Password again");
                forgetPasswordConfirm.requestFocus();

            } else if(Methods.checkConnection(mContext)) {
                //VolleyMethods.userLogin(stringPhone, stringPassword, typeString, mActivity, pDialog);

                changePassword();

                //Toast.makeText(mContext, "Email: " + stringEmail + "\nPhone: " + stringPhone + "\nPassword: " + stringPassword + "\nPassword Confirm: " + stringPasswordConfirm, Toast.LENGTH_LONG).show();

            } else {
                Methods.showSettingsAlert(mContext);
            }
        });
    }

    private void changePassword() {

        Methods.showDialog(pDialog);

        new Thread(() -> {

            Looper.prepare();

            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = RequestBody.create("", mediaType);
            Request request = new Request.Builder()
                    .url(ForgetURL  + "?type=" + typeString + "&email=" + stringEmail + "&phone=" + stringPhone + "&password=" + stringPassword)
                    .method("POST", body)
                    .build();
            try {
                String response = Objects.requireNonNull(client.newCall(request).execute().body()).string();
                JSONObject resultObject = new JSONObject(response);
                final boolean error = resultObject.getBoolean("error");
                final String msg = resultObject.getString("message");

                runOnUiThread(() -> {
                    Methods.hideDialog(pDialog, mActivity, true);
                    new Handler().postDelayed(() -> {
                        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
                        if(!error)
                            finish();
                    }, 3000);
                });

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }).start();
    }
}