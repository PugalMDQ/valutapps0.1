package com.vibro.care.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
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

import static android.view.View.GONE;
import static com.vibro.care.Config.Config.DetailsURL;
import static com.vibro.care.Config.Config.UserDetailsURL;
import static com.vibro.care.Config.Config.name;

public class JobDetailsActivity extends AppCompatActivity {

    ConstraintLayout detailsMain;
    int priorityType, statusType, cost, paid_amount, advance, discount, feedback;
    String uid, accessid, postID, type, params;
    String jobName, descriptionString, categoryString, serviceCategoryString, perDayString,
            perJobString, dateRequired, jobPlace, statusString, id_assigned, id_type, assignedString, remark, imageUrl, assignedDetails;
    Context mContext;
    Activity mActivity;
    AppPref pref;
    Dialog pDialog;
    TextView title, priority, description, category, serviceCategory, time, place, status, total_cost, remarks, assigned;
    //WebView image;
    CardView cardCost, cardRemarks, cardAssigned;
    LinearLayout linearImage;
    ImageView image;
    TableRow rowButtons;
    Button buttonApply, buttonAccept, buttonDecline, buttonDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        mContext = this;
        mActivity = this;
        pref = new AppPref(mContext);
        type = pref.getResponse(Config.company_type);
        uid = pref.getResponse(Config.id);
        accessid = pref.getResponse(Config.accessid);

        postID = getIntent().getStringExtra(Config.id);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Job details of #" + postID);

        pDialog = new Dialog(mActivity, R.style.Dialog);
        pDialog.setContentView(R.layout.loading);

        detailsMain = findViewById(R.id.detailsMain);
        buttonApply = findViewById(R.id.buttonApply);
        buttonAccept = findViewById(R.id.buttonAccept);
        buttonDecline = findViewById(R.id.buttonDecline);
        buttonDetails = findViewById(R.id.buttonDetails);
        rowButtons = findViewById(R.id.rowButtons);
        linearImage = findViewById(R.id.linearImage);
        image = findViewById(R.id.image);
        title = findViewById(R.id.title);
        priority = findViewById(R.id.priority);
        description = findViewById(R.id.description);
        category = findViewById(R.id.category);
        serviceCategory = findViewById(R.id.serviceCategory);
        time = findViewById(R.id.time);
        place = findViewById(R.id.place);
        status = findViewById(R.id.status);
        cardCost = findViewById(R.id.cardCost);
        total_cost = findViewById(R.id.cost);
        cardRemarks = findViewById(R.id.cardRemarks);
        remarks = findViewById(R.id.remarks);
        cardAssigned = findViewById(R.id.cardAssigned);
        assigned = findViewById(R.id.assigned);

        buttonApply.setOnClickListener(view -> updateStatus(-1));

        buttonAccept.setOnClickListener(view -> updateStatus(1));

        buttonDecline.setOnClickListener(view -> updateStatus(0));

    }

    @Override
    protected void onResume() {
        super.onResume();

        getDetails();
    }

    private void getDetails() {
        Methods.showDialog(pDialog);

        new Thread(() -> {
            Looper.prepare();
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request request = new Request.Builder()
                    .url(DetailsURL + "?" + Config.accessid + "=" + accessid + "&PostID=" + postID)
                    .method("GET", null)
                    .build();
            try {
                final Response response = client.newCall(request).execute();
                JSONObject responseObject = new JSONObject(Objects.requireNonNull(response.body()).string());
                final boolean error = responseObject.getBoolean("error");

                advance = responseObject.getInt("advance");
                discount = responseObject.getInt("discount");

                JSONObject data = responseObject.getJSONObject("data");

                jobName = data.getString(name);
                priorityType = Integer.parseInt(data.getString("priority"));
                descriptionString = data.getString("description");
                categoryString = data.getString("category");

                serviceCategoryString = data.getString("service_category");
                perDayString = data.getString("per_day");
                perJobString = data.getString("per_job");

                if (perJobString.length() > 0) {
                    serviceCategoryString += " - " + perJobString;
                }

                /*if(!type.equals("10")){
                    int amnt = Integer.parseInt(serviceCategoryString.replaceAll("\\D+",""));
                    int disc_amnt = amnt * (100-discount)/100;
                    serviceCategoryString = serviceCategoryString.replace(String.valueOf(amnt), String.valueOf(disc_amnt));
                }*/

                if (!perDayString.equals("0")) {
                    serviceCategoryString += " for " + perDayString + " day(s)";
                }

                dateRequired = data.getString("date_req");
                jobPlace = data.getString("job_place");

                statusType = Integer.parseInt(data.getString("status"));

                id_assigned = data.getString("id_assigned");
                id_type = data.getString("id_type");

                cost = data.getInt("cost");
                paid_amount = data.getInt("paid_amount");
                feedback = data.getInt("feedback");

                if (!type.equals("10")) {
                    cost = cost * (100 - discount) / 100;
                }

                if (type.equals("0")) {
                    assignedString = data.getString("individual_accepted");
                } else if (type.equals("11")) {
                    assignedString = data.getString("company_accepted");
                }

                remark = data.getString("remarks");
                imageUrl = data.getString("image");

                runOnUiThread(() -> {
                    title.setText(jobName);

                    int color = 0;
                    String priorityString = "";

                    if (priorityType == 1) {
                        color = Color.RED;
                        priorityString = "High";
                    } else if (priorityType == 2) {
                        color = Color.parseColor("#FFA500");
                        priorityString = "Medium";
                    } else if (priorityType == 3) {
                        color = Color.GREEN;
                        priorityString = "Low";
                    }

                    priority.setText(priorityString);
                    priority.setTextColor(color);

                    description.setText(descriptionString);

                    category.setText(categoryString);

                    serviceCategory.setText(serviceCategoryString);

                    time.setText(dateRequired);
                    place.setText(jobPlace);

                    if (statusType == 0) {
                        statusString = "New Job";
                    } else if (statusType == 1) {
                        if (type.equals("10"))
                            statusString = "New Job";
                        else
                            statusString = "Job Accepted";
                    } else if (statusType == 2) {
                        if (type.equals("10"))
                            statusString = "Job Assigned, awaiting approval from Service Provider";
                        else
                            statusString = "Job Assigned";
                    } else if (statusType == 3) {
                        if (paid_amount > 0)
                            statusString = "Job Confirmed";
                        else
                            statusString = "Job is waiting to be Confirmed";
                    } else if (statusType == 4) {
                        if (paid_amount == cost && feedback == 1)
                            statusString = "Job Completed";
                        else
                            statusString = "Job is waiting to be Completed";
                    }

                    if (statusType > 1 && statusType < 4 && !type.equals("10")) {
                        if (uid.equals(id_assigned) && type.equals(id_type))
                            statusString += " to You";
                        else
                            statusString += " to Someone else";
                    }

                    if (!type.equals("10") && statusType == 0)
                        statusString = "New Job";

                    status.setText(statusString);

                    if (cost > 0) {
                        total_cost.setText("₹" + cost);
                        cardCost.setVisibility(View.VISIBLE);
                    }

                    if (type.equals("10")) {
                        buttonApply.setVisibility(GONE);

                        if (statusType > 2) {
                            buttonDetails.setVisibility(View.VISIBLE);
                            if (paid_amount > 0)
                                buttonDetails.setText("SEE ASSIGNED PROVIDER DETAILS");
                        }

                        if (statusType > 3) {
                            if (statusType == 4 && feedback == 1 && cost == paid_amount)
                                buttonDetails.setVisibility(GONE);
                            else if (cost == paid_amount)
                                buttonDetails.setText("SUBMIT FEEDBACK");
                            else if (statusType == 4)
                                buttonDetails.setText("PAY TO COMPLETE JOB");
                        }

                        if (statusType > 1) {
                            getUserDetails();

                            if (cost > 0) {
                                total_cost.setText(String.valueOf(cost));
                                cardCost.setVisibility(View.VISIBLE);
                            }
                        }

                        buttonDetails.setOnClickListener(view -> {
                            if (statusType > 3) {
                                if (cost == paid_amount)
                                    startActivity(
                                            new Intent(mContext, PaymentActivity.class)
                                                    .putExtra("provider_type", id_type)
                                                    .putExtra("provider_id", id_assigned)
                                                    .putExtra("job_id", postID)
                                                    .putExtra("type", "feedback")
                                    );
                                else
                                    startActivity(
                                            new Intent(mContext, PaymentActivity.class)
                                                    .putExtra("pay_type", "balance")
                                                    .putExtra("amount", (cost * (100 - advance)) / 100)
                                                    .putExtra("job_id", postID)
                                                    .putExtra("provider_type", id_type)
                                                    .putExtra("provider_id", id_assigned)
                                    );
                            } else {
                                if (paid_amount == 0) {
                                    startActivity(
                                            new Intent(mContext, PaymentActivity.class)
                                                    .putExtra("pay_type", "advance")
                                                    .putExtra("amount", (cost * advance) / 100)
                                                    .putExtra("job_id", postID)
                                                    .putExtra("provider_type", id_type)
                                                    .putExtra("provider_id", id_assigned)
                                    );
                                } else {
                                    startActivity(
                                            new Intent(mContext, PaymentActivity.class)
                                                    .putExtra("provider_type", id_type)
                                                    .putExtra("provider_id", id_assigned)
                                                    .putExtra("job_id", postID)
                                                    .putExtra("type", "details")
                                    );
                                }
                            }
                        });

                    } else {
                        if (statusType == 1 && assignedString.contains("," + uid + ",")) {
                            buttonApply.setVisibility(GONE);
                        } else if (statusType == 2) {
                            buttonApply.setVisibility(GONE);
                            if (uid.equals(id_assigned) && type.equals(id_type))
                                rowButtons.setVisibility(View.VISIBLE);
                        } else if (statusType == 3 && uid.equals(id_assigned) && type.equals(id_type)) {
                            if (paid_amount == 0) {
                                buttonApply.setText("JOB IS WAITING TO BE CONFIRMED");
                                buttonApply.setEnabled(false);
                            } else {
                                buttonApply.setText("COMPLETE JOB");
                                buttonApply.setEnabled(true);
                            }
                            buttonApply.setVisibility(View.VISIBLE);
                        } else if (statusType == 3 && !(uid.equals(id_assigned) && type.equals(id_type))) {
                            buttonApply.setVisibility(GONE);
                        } else if (statusType == 4) {
                            buttonApply.setVisibility(GONE);
                        } else {
                            buttonApply.setVisibility(View.VISIBLE);
                        }
                    }

                    if (remark.length() > 0)
                        remarks.setText(remark);
                    else
                        cardRemarks.setVisibility(GONE);

                    if (imageUrl.length() > 0) {
                        linearImage.setVisibility(View.VISIBLE);

                        final String url = Config.MainURL + imageUrl;

                        Glide.with(mContext)
                                .asBitmap()
                                .load(url)
                                .listener(new RequestListener<Bitmap>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                        runOnUiThread(() -> {
                                            Toast.makeText(mContext, "Failed to load image file", Toast.LENGTH_LONG).show();
                                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                                            params.setMargins(0, 30, 0, 30);
                                            image.setLayoutParams(params);
                                            image.setImageResource(R.mipmap.inf);
                                        });
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(final Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                        //bmp = resource;
                                        runOnUiThread(() -> {
                                            image.setImageBitmap(resource);

                                            image.setOnClickListener(v -> startActivity(new Intent(mContext, ImageViewActivity.class)
                                                    .putExtra("url", url)
                                                    .putExtra("text", descriptionString)));
                                        });
                                        return true;
                                    }
                                }).submit();
                    }

                    Methods.hideDialog(pDialog, mActivity, !error);
                    new Handler().postDelayed(() -> detailsMain.setVisibility(View.VISIBLE), 3000);
                });

            } catch (IOException | JSONException e) {
                e.printStackTrace();
                Toast.makeText(mContext, "Job not found!", Toast.LENGTH_SHORT).show();
                Methods.hideDialog(pDialog, mActivity, true);
            }
        }).start();
    }

    private void updateStatus(int action) {
        Methods.showDialog(pDialog);
        params = "?" + Config.accessid + "=" + accessid + "&PostID=" + postID;

        if (action > -1) {
            params += "&action=" + action;
        }

        new Thread(() -> {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = RequestBody.create("", mediaType);
            Request request = new Request.Builder()
                    .url(DetailsURL + params)
                    .method("POST", body)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                JSONObject responseObject = new JSONObject(Objects.requireNonNull(response.body()).string());

                boolean error = responseObject.getBoolean("error");
                final String message = responseObject.getString("message");

                if (!error) {
                    runOnUiThread(() -> {
                        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
                        detailsMain.setVisibility(GONE);
                        rowButtons.setVisibility(GONE);
                        status.setText("");
                        getDetails();
                    });
                }

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void getUserDetails() {

        new Thread(() -> {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request request = new Request.Builder()
                    .url(UserDetailsURL + "?accessid=" + accessid + "&provider_id=" + id_assigned + "&provider_type=" + id_type)
                    .method("GET", null)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                JSONObject responseObject = new JSONObject(Objects.requireNonNull(response.body()).string());

                final boolean error = responseObject.getBoolean("error");

                JSONObject data = responseObject.getJSONArray("data").getJSONObject(0);
                final String nameString = data.getString("name");

                String cont_name = "";
                if (id_type.equals("11"))
                    cont_name = data.getString("cont_name");

                String emailString = data.getString("email");
                String phoneString = data.getString("phone");

                if (statusType <= 3 && paid_amount == 0) {
                    phoneString = phoneString.substring(0, phoneString.length() - 5) + "*****";

                    String[] emailArray = emailString.split("@");
                    emailString = emailArray[0].substring(0, 2) + "********@" + emailArray[1];
                }

                final String cont_nameString = cont_name;
                String finalPhoneString = phoneString;
                String finalEmailString = emailString;

                runOnUiThread(() -> {
                    cardAssigned.setVisibility(View.VISIBLE);

                    assignedDetails = nameString;

                    if (id_type.equals("11")) {
                        assignedDetails += "\nContact name: " + cont_nameString;
                    }

                    assignedDetails += "\nPhone: " + finalPhoneString + "\nEmail ID: " + finalEmailString;

                    assigned.setText(assignedDetails);

                });

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }).start();
    }
}