package com.vibro.care.Activity.fragment.common;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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

import static com.vibro.care.Config.Config.SupportURL;
import static com.vibro.care.Config.Config.email;
import static com.vibro.care.Config.Config.message;
import static com.vibro.care.Config.Config.name;
import static com.vibro.care.Config.Config.table;

public class SupportFragment extends Fragment {

    Activity mActivity;
    Context mContext;
    Dialog pDialog;
    InputMethodManager imm;

    String stringName, stringEmail, stringMsg;
    EditText supportName, supportEmail, supportMsg;
    Button supportSubmit;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_support, container, false);

        mActivity = getActivity();
        mContext = getContext();
        imm = (InputMethodManager) Objects.requireNonNull(mContext).getSystemService(Activity.INPUT_METHOD_SERVICE);

        pDialog = new Dialog(mActivity, R.style.Dialog);
        pDialog.setContentView(R.layout.loading);

        supportName = root.findViewById(R.id.supportName);
        supportEmail = root.findViewById(R.id.supportEmail);
        supportMsg = root.findViewById(R.id.supportMsg);

        supportSubmit = root.findViewById(R.id.supportSubmit);

        supportSubmit.setOnClickListener(v -> {
            stringName = supportName.getText().toString();
            stringEmail = supportEmail.getText().toString();
            stringMsg = supportMsg.getText().toString();

            if(stringName.isEmpty()){
                supportName.setError("Please enter your name");
                supportName.requestFocus();
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);

            } else if(stringEmail.isEmpty()){
                supportEmail.setError("Please enter your email ID");
                supportEmail.requestFocus();
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);

            } else if(stringMsg.isEmpty()){
                supportMsg.setError("Please enter your messege");
                supportMsg.requestFocus();
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);

            } else if(Methods.checkConnection(mContext)) {
                Methods.showDialog(pDialog);
                chatSupport();

            } else
                Methods.showSettingsAlert(mContext);
        });

        return root;
    }

    void chatSupport(){
        new Thread(() -> {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = RequestBody.create("", mediaType);
            Request request = new Request.Builder()
                    .url(SupportURL + "?" + name + "=" + stringName + "&" + email + "=" + stringEmail +
                            "&" + message + "=" + stringMsg + "&" + table + "=tbl_support")
                    .method("POST", body)
                    .build();
            try {
                String response = Objects.requireNonNull(client.newCall(request).execute().body()).string();
                JSONObject resultObject = new JSONObject(response);
                final boolean error = resultObject.getBoolean("error");
                final String msg = resultObject.getString("message");

                mActivity.runOnUiThread(() -> new Handler().postDelayed(() -> {
                    if(!error)
                        showSucess(msg);
                }, 3000));

            } catch (IOException | JSONException e) {
                mActivity.runOnUiThread(() -> new Handler().postDelayed(() -> {
                    Methods.hideDialog(pDialog, mActivity, true);
                    e.printStackTrace();
                    Toast.makeText(mActivity, "Submission failed!!! Try again...\nReason: " + e.toString(), Toast.LENGTH_SHORT).show();
                }, 3000));
            }
        }).start();
    }

     void showSucess(String msg) {
        Toast.makeText(mActivity, msg, Toast.LENGTH_LONG).show();
        supportName.setText("");
        supportEmail.setText("");
        supportMsg.setText("");
        supportSubmit.requestFocus();
        Methods.hideDialog(pDialog, mActivity, true);
    }
}
