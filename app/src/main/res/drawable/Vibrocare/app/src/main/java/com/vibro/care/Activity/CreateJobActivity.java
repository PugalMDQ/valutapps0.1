package com.vibro.care.Activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.android.material.textfield.TextInputLayout;
import com.vibro.care.Config.AppPref;
import com.vibro.care.Config.Methods;
import com.vibro.care.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import static com.vibro.care.Config.Config.CreateURL;
import static com.vibro.care.Config.Config.accessid;
import static com.vibro.care.Config.Config.name;

public class CreateJobActivity extends AppCompatActivity {

    private static final int MEDIA_TYPE_IMAGE = 1;
    public static String nameString = "", descriptionString = "", priorityString = "",
            serviceCategoryString = "", perDayString = "", perJobString = "", pathString = "",
            path = null, categoryString = "",  dateString = "", placeString = "", remarksString = "";
    static Activity mActivity;
    static Context mContext;
    final Handler mHandler = new Handler();
    private final int PICK_IMAGE_REQUEST = 1;
    private final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 2;
    long back_pressed;
    int cost = 0;
    AppPref pref;
    Dialog pDialog;
    InputMethodManager imm;
    TextInputLayout jobPerDayLay;
    EditText jobName, jobDescription, jobPerDay, jobUpload, jobDate, jobPlace, jobRemarks;
    TableRow rowUpload;
    Spinner jobPriority, jobCategory, jobServiceCategory, jobPerJob;
    TextView jobCost;
    Button jobPost;
    private Uri filePath = null, fileUri = null;

    private static File getOutputMediaFile(int type) {

        File mediaStorageDir;

        if (android.os.Build.VERSION.SDK_INT < 29) {
            mediaStorageDir = new File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    mActivity.getResources().getString(R.string.app_name));
        } else {
            mediaStorageDir = mContext.getExternalFilesDir(null);
        }


        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.e("TAG", "Oops! Failed create " + mActivity.getResources().getString(R.string.app_name) + " directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_job);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Create Job");

        mActivity = this;
        mContext = this;
        pref = new AppPref(mContext);
        imm = (InputMethodManager) mContext.getSystemService(Activity.INPUT_METHOD_SERVICE);
        pDialog = new Dialog(mActivity, R.style.Dialog);
        pDialog.setContentView(R.layout.loading);

        jobName = findViewById(R.id.jobName);
        jobDescription = findViewById(R.id.jobDescription);
        jobRemarks = findViewById(R.id.jobRemarks);
        jobPerDay = findViewById(R.id.jobPerDay);

        jobPriority = findViewById(R.id.jobPriority);
        jobCategory = findViewById(R.id.jobCategory);
        jobServiceCategory = findViewById(R.id.jobServiceCategory);
        jobPerJob = findViewById(R.id.jobPerJob);

        jobPerDayLay = findViewById(R.id.jobPerDayLay);
        jobPerDayLay = findViewById(R.id.jobPerDayLay);

        rowUpload = findViewById(R.id.rowUpload);
        jobUpload = findViewById(R.id.jobUpload);

        jobDate = findViewById(R.id.jobDate);
        jobPlace = findViewById(R.id.jobPlace);

        jobCost = findViewById(R.id.jobCost);

        jobPost = findViewById(R.id.jobPost);

        initSpinner();

        rowUpload.setOnClickListener(view -> showFileChooser());

        jobUpload.setInputType(InputType.TYPE_NULL);
        jobUpload.setOnClickListener(view -> showFileChooser());

        jobDate.setInputType(InputType.TYPE_NULL);
        jobDate.setOnClickListener(view -> showDateChooser());

        jobPost.setOnClickListener(view -> checkCondition());

    }

    public void checkCondition() {

        nameString = jobName.getText().toString().trim();
        descriptionString = jobDescription.getText().toString().trim();
        perDayString = jobPerDay.getText().toString().trim();
        dateString = jobDate.getText().toString();
        placeString = jobPlace.getText().toString().trim();
        remarksString = jobRemarks.getText().toString().trim();

        if (nameString.length() == 0) {
            jobName.setError("Please enter job name");
            jobName.requestFocus();
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        } else if (descriptionString.length() == 0) {
            jobDescription.setError("Please enter job description");
            jobDescription.requestFocus();
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        } else if (priorityString.length() == 0) {
            Toast.makeText(mActivity, "Please select a job priority", Toast.LENGTH_SHORT).show();
            jobPriority.performClick();
        } else if (serviceCategoryString.length() == 0) {
            Toast.makeText(mActivity, "Please select a service category", Toast.LENGTH_SHORT).show();
            jobServiceCategory.performClick();
        } else if (jobServiceCategory.getSelectedItemPosition() == 1 && perDayString.length() == 0) {
            jobPerDay.setError("Please enter no. of days");
            jobPerDay.requestFocus();
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        } else if (jobServiceCategory.getSelectedItemPosition() == 2 && perJobString.length() == 0) {
            Toast.makeText(mActivity, "Please select per job type", Toast.LENGTH_SHORT).show();
            jobPerJob.performClick();
        }/* else if (categoryString.length() == 0) {
            Toast.makeText(mActivity, "Please select a job category", Toast.LENGTH_SHORT).show();
            jobCategory.performClick();
        }*/ else if (dateString.length() == 0) {
            jobDate.setError("Please select Date of Requirement");
            showDateChooser();
        } else if (placeString.length() == 0) {
            jobPlace.setError("Please enter Place of Job");
            jobPlace.requestFocus();
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        } else {
            if (filePath != null)
                path = getPath(filePath);
            else if (fileUri != null)
                path = fileUri.getPath();

            jobPost.setEnabled(false);
            submitJob();
        }
    }

    private void showDateChooser() {
        int mYear, mMonth, mDay;

        if(dateString.length() == 0){
            Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        } else {
            mYear = Integer.parseInt(dateString.split("/")[2]);
            mMonth = Integer.parseInt(dateString.split("/")[1]) - 1;
            mDay = Integer.parseInt(dateString.split("/")[0]);
        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, R.style.datePicker,
                (view, year, month, dayOfMonth) -> {

                    if(dayOfMonth < 10)
                        dateString = "0" + dayOfMonth + "/";
                    else
                        dateString = dayOfMonth + "/";

                    if(month + 1 < 10)
                        dateString += "0";

                    dateString += (month + 1) + "/" + year;

                    jobDate.setText(dateString);
                    jobDate.setError(null);
                }, mYear, mMonth, mDay);

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());

        datePickerDialog.show();
        datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
        datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
    }

    private void initSpinner() {

        final List<String> priority_array = new ArrayList<>(Arrays.asList("Please select priority", "High", "Medium", "Low"));

        ArrayAdapter<String> priorityAdapter = new ArrayAdapter<>(mContext, R.layout.child_spinner, priority_array);
        priorityAdapter.setDropDownViewResource(R.layout.child_spinner);
        jobPriority.setAdapter(priorityAdapter);

        jobPriority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    priorityString = String.valueOf(position);
                } else
                    priorityString = "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final List<String> serviceCategory_array = new ArrayList<>
                (Arrays.asList("Please select Service Category", "On Call Basis: Per Day"/* (₹4000/day)", "On Call Basis: Per Job"*/));

        ArrayAdapter<String> serviceCategoryAdapter = new ArrayAdapter<>(mContext, R.layout.child_spinner, serviceCategory_array);
        serviceCategoryAdapter.setDropDownViewResource(R.layout.child_spinner);
        jobServiceCategory.setAdapter(serviceCategoryAdapter);

        jobServiceCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    serviceCategoryString = serviceCategory_array.get(position);
                    if(position == 1){
                        jobPerDayLay.setVisibility(View.VISIBLE);
                        jobPerJob.setVisibility(View.GONE);
                    } else {
                        jobPerJob.setVisibility(View.VISIBLE);
                        jobPerDayLay.setVisibility(View.GONE);
                    }
                } else
                    serviceCategoryString = "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final List<String> perJob_array = new ArrayList<>(Arrays.asList("Please select job type", "Vibration (₹2500)", "Balancing (₹3500)"));

        ArrayAdapter<String> perJobAdapter = new ArrayAdapter<>(mContext, R.layout.child_spinner, perJob_array);
        perJobAdapter.setDropDownViewResource(R.layout.child_spinner);
        jobPerJob.setAdapter(perJobAdapter);

        jobPerJob.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    perJobString = perJob_array.get(position);
                } else
                    perJobString = "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final List<String> category_array = new ArrayList<>(Arrays.asList("Please select category", "Category 1", "Category 2", "Category 3", "Category 4"));

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(mContext, R.layout.child_spinner, category_array);
        categoryAdapter.setDropDownViewResource(R.layout.child_spinner);
        jobCategory.setAdapter(categoryAdapter);

        jobCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    categoryString = category_array.get(position);
                } else
                    categoryString = "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void showFileChooser() {
        final Dialog selectDialog = new Dialog(mActivity);
        selectDialog.setContentView(R.layout.dialog_image);
        selectDialog.setCanceledOnTouchOutside(false);
        selectDialog.show();

        int width = (int) (mActivity.getResources().getDisplayMetrics().widthPixels * 0.95);
        selectDialog.getWindow().setLayout(width, LinearLayout.LayoutParams.WRAP_CONTENT);
        selectDialog.getWindow().setGravity(Gravity.CENTER);

        final TextView imageCamera = selectDialog.findViewById(R.id.imageCamera);
        final TextView imageGallery = selectDialog.findViewById(R.id.imageGallery);

        imageCamera.setOnClickListener(view -> {
            captureImage();
            selectDialog.dismiss();
        });

        imageGallery.setOnClickListener(view -> {
            selectImage();
            selectDialog.dismiss();
        });
    }

    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    public Uri getOutputMediaFileUri(int type) {

        return FileProvider.getUriForFile(mContext, mContext.getApplicationContext().getPackageName() + ".provider", Objects.requireNonNull(getOutputMediaFile(type)));
    }

    void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //intent.setType("image/*");
        //intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    public String getPath(Uri uri) {
        String filePath;
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = mContext.getContentResolver().query(uri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            filePath = cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return filePath;
    }

    @Override
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        fileUri = savedInstanceState.getParcelable("file_uri");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            pathString = getPath(filePath);

            pathString = pathString.substring(pathString.lastIndexOf("/") + 1);
            jobUpload.setText(pathString);

        } else if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                pathString = fileUri.getPath();

                pathString = pathString.substring(pathString.lastIndexOf("/") + 1);
                jobUpload.setText(pathString);

            } else if (resultCode == RESULT_CANCELED) {

                Toast.makeText(getApplicationContext(), "User cancelled image capture", Toast.LENGTH_SHORT).show();

            } else {

                Toast.makeText(getApplicationContext(), "Sorry! Failed to capture image", Toast.LENGTH_SHORT).show();

            }

        }
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

    private void submitJob() {

        new Thread(() -> {

            RequestBody body;

            if(path != null){
                File sourceFile = new File(path);
                body = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("image", sourceFile.getName(),
                                RequestBody.create(sourceFile, MediaType.parse("application/octet-stream")))
                        .addFormDataPart(accessid, pref.getResponse(accessid))
                        .addFormDataPart(name, Base64.encodeToString(nameString.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT))
                        .addFormDataPart("description", Base64.encodeToString(descriptionString.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT))
                        .addFormDataPart("priority", Base64.encodeToString(priorityString.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT))
                        .addFormDataPart("service_category", Base64.encodeToString(serviceCategoryString.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT))
                        .addFormDataPart("per_day", Base64.encodeToString(perDayString.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT))
                        .addFormDataPart("per_job", Base64.encodeToString(perJobString.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT))
                        .addFormDataPart("category", Base64.encodeToString(categoryString.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT))
                        .addFormDataPart("date_req", Base64.encodeToString(dateString.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT))
                        .addFormDataPart("job_place", Base64.encodeToString(placeString.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT))
                        .addFormDataPart("remarks", Base64.encodeToString(remarksString.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT))
                        .build();
            } else {
                body = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart(accessid, pref.getResponse(accessid))
                        .addFormDataPart(name, Base64.encodeToString(nameString.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT))
                        .addFormDataPart("description", Base64.encodeToString(descriptionString.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT))
                        .addFormDataPart("priority", Base64.encodeToString(priorityString.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT))
                        .addFormDataPart("service_category", Base64.encodeToString(serviceCategoryString.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT))
                        .addFormDataPart("per_day", Base64.encodeToString(perDayString.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT))
                        .addFormDataPart("per_job", Base64.encodeToString(perJobString.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT))
                        .addFormDataPart("category", Base64.encodeToString(categoryString.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT))
                        .addFormDataPart("date_req", Base64.encodeToString(dateString.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT))
                        .addFormDataPart("job_place", Base64.encodeToString(placeString.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT))
                        .addFormDataPart("remarks", Base64.encodeToString(remarksString.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT))
                        .build();
            }

            Looper.prepare();
            runOnUiThread(() -> pDialog.show());

            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();

            Request request = new Request.Builder()
                    .url(CreateURL)
                    .method("POST", body)
                    .build();

            try {
                String response = Objects.requireNonNull(client.newCall(request).execute().body()).string();
                JSONObject resultObject = new JSONObject(response);
                final boolean error = resultObject.getBoolean("error");
                final boolean valid = resultObject.getBoolean("valid");
                final String msg = resultObject.getString("message");

                runOnUiThread(() -> {
                    if(error){

                        mHandler.postDelayed(() -> {
                            Methods.hideDialog(pDialog, mActivity, true);
                            Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
                            pref.logoutUser();
                        }, 2900);

                    } else {

                        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();

                        if(valid)
                            runOnUiThread(() -> Methods.hideDialog(pDialog, mActivity, false));
                    }
                });


            } catch (final IOException | JSONException e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    Methods.hideDialog(pDialog, mActivity, true);
                    e.printStackTrace();
                    Toast.makeText(mContext, "Job submission failed!!! Try again...\nReason: " + e.toString(), Toast.LENGTH_SHORT).show();
                });
            }
        }).start();

    }
}