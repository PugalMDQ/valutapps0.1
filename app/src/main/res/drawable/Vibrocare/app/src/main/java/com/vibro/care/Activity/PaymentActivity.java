package com.vibro.care.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.vibro.care.Config.AppPref;
import com.vibro.care.Config.Config;
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
import okhttp3.Response;

import static com.vibro.care.Config.Config.FeedbackURL;
import static com.vibro.care.Config.Config.MainURL;
import static com.vibro.care.Config.Config.TransactionURL;
import static com.vibro.care.Config.Config.UserDetailsURL;
import static com.vibro.care.Config.Config.razorpay;
import static com.vibro.care.Config.Config.user_data;

public class PaymentActivity extends AppCompatActivity implements PaymentResultListener {

    Activity mActivity;
    AppPref pref;
    InputMethodManager imm;
    Dialog pDialog;
    LinearLayout userDetails, feedback;
    CardView cardContact;
    RatingBar ratingFeedback;
    EditText editFeedback;
    Button buttonFeedback;
    TextView type, name, contact, phone_no, email_id, address;
    float ratingCount;
    String accessid, provider_id, provider_type, amount, description, phone, email, job_id, pay_type, feedbackString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        /*Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            for (String key : bundle.keySet()) {
                Log.e("TAG", key + " : " + (bundle.get(key) != null ? bundle.get(key) : "NULL"));
            }
        }*/

        mActivity = this;

        pref = new AppPref(mActivity);
        accessid = pref.getResponse(Config.accessid);
        imm = (InputMethodManager) mActivity.getSystemService(Activity.INPUT_METHOD_SERVICE);

        try {
            JSONObject userResponse = new JSONObject(pref.getResponse(user_data));
            phone = userResponse.getString(Config.phone);
            email = userResponse.getString(Config.email);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        pDialog = new Dialog(mActivity, R.style.Dialog);
        pDialog.setContentView(R.layout.loading);

        job_id = getIntent().getStringExtra("job_id");
        provider_id = getIntent().getStringExtra("provider_id");
        provider_type = getIntent().getStringExtra("provider_type");

        if(getIntent().hasExtra("pay_type")){

            Objects.requireNonNull(getSupportActionBar()).hide();

            amount = String.valueOf(getIntent().getIntExtra("amount", 0) * 100);
            pay_type = getIntent().getStringExtra("pay_type");

            if(pay_type.equals("advance")){
                description = "Paying advance amount";
            } else if(pay_type.equals("balance")){
                description = "Paying balance amount";
            }

            final Dialog termsDialog = new Dialog(mActivity);
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
                finish();
            });

            buttonAccept.setOnClickListener(view -> {
                termsDialog.dismiss();
                Checkout.preload(getApplicationContext());
                startPayment();
            });

        } else {
            String type = getIntent().getStringExtra("type");
            if(type.equals("details"))
                getUserDetails();
            else if(type.equals("feedback"))
                showFeedback();
//                submitFeedback();
        }

        userDetails = findViewById(R.id.user_details);
        cardContact = findViewById(R.id.cardContact);

        type = findViewById(R.id.type);
        name = findViewById(R.id.name);
        contact = findViewById(R.id.contact);
        phone_no = findViewById(R.id.phone);
        email_id = findViewById(R.id.email);
        address = findViewById(R.id.address);
    }

    private void showFeedback() {

        Objects.requireNonNull(getSupportActionBar()).show();
        getSupportActionBar().setTitle("Feedback for Job #" + job_id);

        feedback = findViewById(R.id.feedback);
        ratingFeedback = findViewById(R.id.ratingFeedback);
        editFeedback = findViewById(R.id.editFeedback);

        buttonFeedback = findViewById(R.id.buttonFeedback);

        buttonFeedback.setOnClickListener(view -> {
            ratingCount = ratingFeedback.getRating();
            feedbackString = editFeedback.getText().toString().trim();

            if(ratingCount == 0){
                Toast.makeText(mActivity, "Please select a rating", Toast.LENGTH_SHORT).show();
            } else if(feedbackString.length() == 0){
                editFeedback.setError("Please provide some feedback");
                editFeedback.requestFocus();
                imm.showSoftInput(editFeedback, InputMethodManager.SHOW_FORCED);
            } else {
                imm.hideSoftInputFromWindow(editFeedback.getWindowToken(), 0);
                submitFeedback();
                buttonFeedback.setEnabled(false);
            }
        });

        feedback.setVisibility(View.VISIBLE);
    }

    private void getUserDetails() {

        runOnUiThread(() -> {
            Objects.requireNonNull(getSupportActionBar()).show();
            getSupportActionBar().setTitle("Service Provider details of Job #" + job_id);
            Methods.showDialog(pDialog);
        });

        new Thread(() -> {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request request = new Request.Builder()
                    .url(UserDetailsURL + "?accessid=" + accessid + "&provider_id=" + provider_id + "&provider_type=" + provider_type)
                    .method("GET", null)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                JSONObject responseObject = new JSONObject(Objects.requireNonNull(response.body()).string());

                final boolean error = responseObject.getBoolean("error");

                JSONObject data = responseObject.getJSONArray("data").getJSONObject(0);
                final String nameString = data.getString("name");

                String cont_name = "";
                if(provider_type.equals("11"))
                    cont_name = data.getString("cont_name");

                final String emailString = data.getString("email");
                final String phoneString = data.getString("phone");
                final String addressString = data.getString("address");

                final String cont_nameString = cont_name;
                runOnUiThread(() -> {
                    if(provider_type.equals("0")){
                        type.setText("Individual");
                    } else if(provider_type.equals("11")){
                        type.setText("Company");
                        cardContact.setVisibility(View.VISIBLE);
                        contact.setText(cont_nameString);
                    }

                    name.setText(nameString);
                    email_id.setText(emailString);
                    phone_no.setText(phoneString);
                    address.setText(addressString);

                    Methods.hideDialog(pDialog, mActivity, !error);
                    new Handler().postDelayed(() -> userDetails.setVisibility(View.VISIBLE), 3000);
                });

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void startPayment() {

        final Checkout co = new Checkout();
        co.setKeyID(pref.getResponse(razorpay));
        co.setImage(R.mipmap.ic_launcher_old);

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Vibcons Services Pvt. Ltd.");
            options.put("description", description);
            options.put("send_sms_hash",true);
            options.put("allow_rotation", true);
            options.put("currency", "INR");
            options.put("amount", amount);

            JSONObject preFill = new JSONObject();
            preFill.put("email", email);
            preFill.put("contact", phone);

            options.put("prefill", preFill);

            co.open(mActivity, options);
        } catch (Exception e) {
            Toast.makeText(mActivity, "Error in payment!", Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        try {
            updateTransaction(razorpayPaymentID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentError(int code, String response) {
        try {
            final Dialog txnDialog = new Dialog(mActivity);
            txnDialog.setContentView(R.layout.dialog_failed);
            txnDialog.getWindow().setBackgroundDrawableResource(R.drawable.corner);
            txnDialog.setCancelable(false);
            int width = (int)(getResources().getDisplayMetrics().widthPixels*0.70);

            txnDialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
            txnDialog.show();

            Button buttonYes = txnDialog.findViewById(R.id.buttonYes);
            Button buttonNo = txnDialog.findViewById(R.id.buttonNo);

            buttonYes.setOnClickListener(view -> {
                txnDialog.dismiss();
                startPayment();
            });

            buttonNo.setOnClickListener(view -> {
                txnDialog.dismiss();
                Toast.makeText(mActivity, "Payment was not completed!", Toast.LENGTH_LONG).show();
                finish();
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateTransaction(final String txnID) {
        Methods.showDialog(pDialog);

        new Thread(() -> {
            Looper.prepare();

            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = RequestBody.create("", mediaType);
            Request request = new Request.Builder()
                    .url(TransactionURL + "?accessid=" + accessid + "&PostID=" + job_id
                            + "&TxnID=" + txnID + "&Amount=" + (Integer.parseInt(amount)/100) + "&Remarks=" + description)
                    .method("POST", body)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                JSONObject responseObject = new JSONObject(Objects.requireNonNull(response.body()).string());

                final boolean error = responseObject.getBoolean("error");
                String msg = responseObject.getString("message");

                Toast.makeText(mActivity, msg, Toast.LENGTH_LONG).show();

                runOnUiThread(() -> {
                    if(pay_type.equals("advance")){
                        getUserDetails();
                    } else if(pay_type.equals("balance")){
                        showFeedback();
                    }
                    Methods.hideDialog(pDialog, mActivity, !error);
                });

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void submitFeedback() {

        Methods.showDialog(pDialog);

        new Thread(() -> {

            Looper.prepare();

            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();

            MediaType mediaType = MediaType.parse("text/plain");

            RequestBody body = RequestBody.create("", mediaType);
            Request request = new Request.Builder()
                    .url(FeedbackURL
                            + "?accessid=" + accessid + "&provider_id=" + provider_id + "&provider_type=" + provider_type
                            + "&job_id=" + job_id + "&feedback_rating=" + ratingCount + "&feedback=" + feedbackString)
                    .method("POST", body)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                JSONObject responseObject = new JSONObject(Objects.requireNonNull(response.body()).string());

                final boolean error = responseObject.getBoolean("error");
                String msg = responseObject.getString("message");

                Toast.makeText(mActivity, msg, Toast.LENGTH_LONG).show();

                runOnUiThread(() -> {

                    Methods.hideDialog(pDialog, mActivity, !error);

                    new Handler().postDelayed(this::finish, 3500);
                });

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }).start();
    }
}