package com.vibro.care.Config;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.vibro.care.Activity.CandidateActivity;
import com.vibro.care.Activity.CompanyActivity;
import com.vibro.care.Activity.JobDetailsActivity;
import com.vibro.care.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import static com.vibro.care.Config.Config.CheckURL;
import static com.vibro.care.Config.Config.DataURL;
import static com.vibro.care.Config.Config.LogoutURL;
import static com.vibro.care.Config.Config.UpdateURL;
import static com.vibro.care.Config.Config.accessid;
import static com.vibro.care.Config.Config.company_type;
import static com.vibro.care.Config.Config.id;
import static com.vibro.care.Config.Config.login;
import static com.vibro.care.Config.Config.name;
import static com.vibro.care.Config.Config.razorpay;
import static com.vibro.care.Config.Config.user_data;

public class Methods {

    static final Handler mHandler = new Handler();

    public static void showDialog(Dialog pDialog) {
        //pDialog.setMessage("Please wait");
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();
    }

    public static void hideDialog(final Dialog pDialog, final Activity mActivity, final boolean error) {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            pDialog.dismiss();
            if(!error)
                mActivity.finish();
        }, 3000);
    }

    public static boolean checkConnection(Context mContext){
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public static void showSettingsAlert(final Context mContext){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        // Setting Dialog Title
        alertDialog.setTitle("Data Connection is not available");

        // Setting Dialog Message
        alertDialog.setMessage("Enable it?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", (dialog, which) -> {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings$DataUsageSummaryActivity"));
            mContext.startActivity(intent);
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        // Showing Alert Message
        alertDialog.show();
    }

    public static void checkLogin(final AppPref appPref, final Activity mActivity, final Dialog pDialog, final String errorMsg, final int status) {

        String accessID = appPref.getResponse(accessid);

        new Thread(() -> {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request request = new Request.Builder()
                    .url(CheckURL + "?" + id + "=" + appPref.getResponse(id) + "&" + accessid + "=" + accessID)
                    .method("GET", null)
                    .build();
            try {
                String response = Objects.requireNonNull(client.newCall(request).execute().body()).string();

                final JSONObject checkResp = new JSONObject(response);
                boolean error = checkResp.getBoolean("Status");

                if (!error) {
                    mActivity.runOnUiThread(() -> logout(mActivity, pDialog, accessID, "Session expired! Logging out..."));
                } else {
                    String type = checkResp.getString("type");
                    String razorpay_id = checkResp.getString("razorpay");
                    JSONArray userResp = checkResp.getJSONArray("User");
                    JSONObject userResponse = userResp.getJSONObject(0);
                    appPref.putResponse(user_data, String.valueOf(userResponse));
                    appPref.putResponse(login, String.valueOf(true));

                    Class destiny = null;
                    String account = "0";

                    if(type.equals("1")){
                        String company_type = checkResp.getString("company_type");
                        if(company_type.equals("0")) {
                            destiny = CompanyActivity.class;
                            account = "10";
                        }else if(company_type.equals("1")){
                            destiny = CandidateActivity.class;
                            account = "11";
                        }
                    } else if(type.equals("2")){
                        destiny = CandidateActivity.class;
                    }

                    appPref.putResponse(company_type, account);
                    appPref.putResponse(razorpay, razorpay_id);

                    final Class finalDestiny = destiny;
                    mHandler.postDelayed(() -> {
                        if(status == 0) {
                            mActivity.startActivity(new Intent(mActivity, finalDestiny));
                            mActivity.finish();
                            //mActivity.startActivity(new Intent(mActivity, CandidateActivity.class));
                        } else if(status == 1)
                            CandidateActivity.initProfile();
                        else if(status == 2)
                            CompanyActivity.initProfile();
                        if(errorMsg.length()>0)
                            Toast.makeText(mActivity, errorMsg, Toast.LENGTH_SHORT).show();
                    }, 3000);

                    Methods.hideDialog(pDialog, mActivity, true);
                }

            } catch (IOException | JSONException e) {
                checkLogin(appPref, mActivity, pDialog, errorMsg, status);
                e.printStackTrace();
            }
        }).start();
    }

    public static void logout(Activity mActivity, Dialog pDialog, String accessID, String message){

        Methods.showDialog(pDialog);

        new Thread(() -> {
            Looper.prepare();
            Toast.makeText(mActivity, message, Toast.LENGTH_LONG).show();

            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = RequestBody.create("", mediaType);
            Request request = new Request.Builder()
                    .url(LogoutURL + "?" + accessid +"=" + accessID)
                    .method("POST", body)
                    .build();
            try {
                String response = Objects.requireNonNull(client.newCall(request).execute().body()).string();
                JSONObject resultObject = new JSONObject(response);

                JSONArray loginResponse = resultObject.getJSONArray("Login");

                final JSONObject logResponse = loginResponse.getJSONObject(0);

                final boolean error = logResponse.getBoolean("error");
                final String msg = logResponse.getString("message");

                mActivity.runOnUiThread(() -> {
                    Toast.makeText(mActivity, msg, Toast.LENGTH_LONG).show();
                    hideDialog(pDialog, mActivity, error);
                    new Handler().postDelayed(() -> new AppPref(mActivity).logoutUser(), 3000);
                });
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void getData(final String type, final String accessID, final String status, final LayoutInflater inflater,
                               final Activity mActivity, final Dialog pDialog, final LinearLayout linearLayout, final TextView textView) {

        Methods.showDialog(pDialog);

        new Thread(() -> {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request request = new Request.Builder()
                    .url(DataURL + "?" + accessid +"=" + accessID + "&" + Config.type + "=" + type + "&" + Config.status + "=" + status)
                    .method("GET", null)
                    .build();

            try {
                String response = Objects.requireNonNull(client.newCall(request).execute().body()).string();

                JSONObject data = new JSONObject(response);
                boolean error = data.getBoolean("error");

                if(error){
                    logout(mActivity, pDialog, accessID, "Session expired! Logging out...");
                } else {
                    final JSONArray dataResp = data.getJSONArray("data");
                    if(String.valueOf(dataResp).equals("") || dataResp.length() == 0){

                        mActivity.runOnUiThread(() -> textView.setVisibility(View.VISIBLE));

                    } else {
                        for(int i=0; i<dataResp.length(); i++){

                            LinearLayout child_job = (LinearLayout) inflater.inflate(R.layout.child_job, linearLayout, false);
                            CardView mainCard = child_job.findViewById(R.id.mainCard);
                            TextView title = child_job.findViewById(R.id.title);
                            TextView timedate = child_job.findViewById(R.id.timedate);
                            TextView priority = child_job.findViewById(R.id.priority);
                            TextView service_category = child_job.findViewById(R.id.service_category);
                            TextView place_of_job = child_job.findViewById(R.id.place_of_job);
                            TextView text_status = child_job.findViewById(R.id.status);
                            ImageView img = child_job.findViewById(R.id.img);
                            Button viewmore = child_job.findViewById(R.id.viewmore);

                            final JSONObject dataObject = dataResp.getJSONObject(i);

                            final String id = dataObject.getString("id");

                            final String jobName = dataObject.getString(name);
                            final String serviceCategory = dataObject.getString("service_category").split("₹")[0].split(":")[1];

                            title.setText(jobName);
                            timedate.setText("Required on " + dataObject.getString("date_req"));
                            service_category.setText(serviceCategory.replace("(", ""));
                            place_of_job.setText(dataObject.getString("job_place"));

                            int statusType = Integer.parseInt(dataObject.getString("status"));

                            int cost = dataObject.getInt("cost");
                            int paid_amount = dataObject.getInt("paid_amount");
                            int feedback = dataObject.getInt("feedback");

                            String statusString = "";

                            if(statusType == 0){
                                statusString = "New Job";
                            } else if(statusType == 1){
                                if(type.equals("10"))
                                    statusString = "New Job";
                                else
                                    statusString = "Job Accepted";
                            } else if(statusType == 2){
                                if(type.equals("10"))
                                    statusString = "Awaiting Approval";
                                else
                                    statusString = "Job Assigned";
                            } else if(statusType == 3){
                                if(paid_amount > 0)
                                    statusString = "Job Confirmed";
                                else
                                    statusString = "Waiting to be Confirmed";
                            } else if(statusType == 4){
                                if(paid_amount == cost && feedback == 1)
                                    statusString = "Job Completed";
                                else
                                    statusString = "Waiting to be Completed";
                            }

                            text_status.setText(statusString);

                            int priorityType = Integer.parseInt(dataObject.getString("priority"));
                            int image = 0;
                            String priorityString = "";

                            if(priorityType == 1){
                                image = R.mipmap.high;
                                priorityString = "High";
                            } else if(priorityType == 2){
                                image = R.mipmap.medium;
                                priorityString = "Medium";
                            } else if(priorityType == 3){
                                image = R.mipmap.low;
                                priorityString = "Low";
                            }

                            priority.setText(priorityString);

                            int finalImage = image;
                            mActivity.runOnUiThread(() -> Glide.with(mActivity)
                                    .load(finalImage)
                                    // .apply(new RequestOptions().circleCrop())
                                    .into(img));

                            viewmore.setOnClickListener(v -> mActivity.startActivity(
                                    new Intent(mActivity, JobDetailsActivity.class)
                                            .putExtra(Config.status, String.valueOf(status))
                                            .putExtra(Config.data, String.valueOf(dataObject))
                                            .putExtra(Config.id, id)));

                            mainCard.setOnClickListener(v -> mActivity.startActivity(
                                    new Intent(mActivity, JobDetailsActivity.class)
                                            .putExtra(Config.status, String.valueOf(status))
                                            .putExtra(Config.data, String.valueOf(dataObject))
                                            .putExtra(Config.id, id)));

                            mActivity.runOnUiThread(() -> linearLayout.addView(child_job));
                        }
                    }

                }

                mActivity.runOnUiThread(() -> Methods.hideDialog(pDialog, mActivity, !error));

            } catch (IOException | JSONException e) {
                e.printStackTrace();
                mActivity.runOnUiThread(() -> Methods.hideDialog(pDialog, mActivity, false));
            }
        }).start();
    }

    public static void updateProfile(Activity mActivity, Dialog pDialog, MultipartBody.Builder builder, String accessID){

        Methods.showDialog(pDialog);

        new Thread(() -> {
            Looper.prepare();

            RequestBody body = builder.build();

            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("text/plain");

            Request request = new Request.Builder()
                    .url(UpdateURL)
                    .method("POST", body)
                    .build();
            try {
                String response = Objects.requireNonNull(client.newCall(request).execute().body()).string();
                JSONObject resultObject = new JSONObject(response);
                final boolean error = resultObject.getBoolean("error");
                final boolean valid = resultObject.getBoolean("valid");
                final String msg = resultObject.getString("message");

                mActivity.runOnUiThread(() -> {
                    Methods.hideDialog(pDialog, mActivity, true);
                    if(error){

                        Toast.makeText(mActivity, msg, Toast.LENGTH_LONG).show();
                        logout(mActivity, pDialog, accessID, "Session expired! Logging out...");

                    } else if(valid) {

                        mHandler.postDelayed(() -> Methods.checkLogin(new AppPref(mActivity), mActivity, pDialog, msg, 1), 1000);

                    }
                });

            } catch (JSONException | IOException e) {
                e.printStackTrace();
                mActivity.runOnUiThread(() -> {
                    Methods.hideDialog(pDialog, mActivity, true);
                    e.printStackTrace();
                    Toast.makeText(mActivity, "Upload failed!!! Try again...\nReason: " + e.toString(), Toast.LENGTH_SHORT).show();
                });
            }
        }).start();
    }

}
