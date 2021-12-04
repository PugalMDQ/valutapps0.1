package com.vibro.care.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.vibro.care.Config.AppPref;
import com.vibro.care.Config.Config;
import com.vibro.care.Config.Methods;
import com.vibro.care.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.vibro.care.Config.Config.GST;
import static com.vibro.care.Config.Config.MainURL;
import static com.vibro.care.Config.Config.PAN;
import static com.vibro.care.Config.Config.PIN;
import static com.vibro.care.Config.Config.accessid;
import static com.vibro.care.Config.Config.address;
import static com.vibro.care.Config.Config.city;
import static com.vibro.care.Config.Config.country;
import static com.vibro.care.Config.Config.email;
import static com.vibro.care.Config.Config.name;
import static com.vibro.care.Config.Config.password;
import static com.vibro.care.Config.Config.person;
import static com.vibro.care.Config.Config.phone;
import static com.vibro.care.Config.Config.state;
import static com.vibro.care.Config.Config.user_data;
import static com.vibro.care.Config.Config.website;

public class CompanyActivity extends AppCompatActivity {

    public static final Handler mHandler = new Handler(Looper.getMainLooper());
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 2;
    private static final int MEDIA_TYPE_IMAGE = 1;
    public static AppPref pref;
    public static Dialog pDialog;
    public static boolean uploading = false, editProfile = false, file = false;
    public static String path;
    public static Toolbar toolbar;
    static Context mContext;
    static Activity mActivity;
    static InputMethodManager imm;
    static int type = 0;
    static Dialog selectDialog, profileDialog;
    static String stringName, stringPerson, stringEmail, stringPhone, stringAddress, stringWebsite,
            stringGST, stringPAN, stringPassword, stringPasswordConfirm, stringCountry, stringState, stringCity, stringPIN;
    static JSONObject userResponse;

    static long back_pressed;
    static ImageView profileImage;
    private static Uri filePath = null, fileUri = null;

    public static void initProfile(){

        file = false;

        try{
            userResponse = new JSONObject(pref.getResponse(user_data));

            toolbar.setNavigationOnClickListener(view -> openProfile());

            String image = userResponse.getString(Config.image);

            if((image.length() == 0 || image.equals("null"))) {
                mHandler.postDelayed(() -> {
                    uploading = true;
                    showFileChooser();
                }, 3000);
            } else {

                Glide.with(mActivity)
                        .asDrawable()
                        .load(MainURL + image)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .placeholder(R.mipmap.account)
                        .apply(RequestOptions.circleCropTransform())
                        .override(60, 60)
                        .into(new CustomTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                toolbar.setNavigationIcon(resource);
                            }

                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {
                                toolbar.setNavigationIcon(R.mipmap.account);
                            }
                        });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        BottomNavigationView navView = mActivity.findViewById(R.id.nav_view_com);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.com_post, R.id.com_active, R.id.com_support, R.id.com_shop)
                .build();
        NavController navController = Navigation.findNavController(mActivity, R.id.nav_host_fragment_com);
        NavigationUI.setupActionBarWithNavController((AppCompatActivity) mActivity, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    private static void openProfile(){
        try {
            profileDialog = new Dialog(mActivity);
            profileDialog.setContentView(R.layout.dialog_profile);
            profileDialog.show();

            int width = mActivity.getResources().getDisplayMetrics().widthPixels;
            int height = (int)(mActivity.getResources().getDisplayMetrics().heightPixels * 0.95);
            profileDialog.getWindow().setLayout(width, height);
            profileDialog.getWindow().setGravity(Gravity.CENTER);

            LinearLayout linCompany = profileDialog.findViewById(R.id.linCompany);
            linCompany.setVisibility(View.VISIBLE);

            profileImage = profileDialog.findViewById(R.id.profileImage);
            final ImageView edit = profileDialog.findViewById(R.id.edit);
            final ImageView cancel = profileDialog.findViewById(R.id.cancel);
            final EditText profileComName = profileDialog.findViewById(R.id.profileComName);
            final EditText profileComPerson = profileDialog.findViewById(R.id.profileComPerson);
            final EditText profileComEmail = profileDialog.findViewById(R.id.profileComEmail);
            final EditText profileComPhone = profileDialog.findViewById(R.id.profileComPhone);
            final EditText profileComAddress = profileDialog.findViewById(R.id.profileComAddress);
            final EditText profileComWebsite = profileDialog.findViewById(R.id.profileComWebsite);
            final EditText profileComGST = profileDialog.findViewById(R.id.profileComGST);
            final EditText profileComPAN = profileDialog.findViewById(R.id.profileComPAN);
            final EditText profileComPassword = profileDialog.findViewById(R.id.profileComPassword);
            final EditText profileComPasswordConfirm = profileDialog.findViewById(R.id.profileComPasswordConfirm);
            final EditText profileComCountry = profileDialog.findViewById(R.id.profileComCountry);
            final EditText profileComState = profileDialog.findViewById(R.id.profileComState);
            final EditText profileComCity = profileDialog.findViewById(R.id.profileComCity);
            final EditText profileComPin = profileDialog.findViewById(R.id.profileComPin);

            setData(profileComName,
                    profileComPerson, profileComEmail,
                    profileComPhone, profileComAddress, profileComWebsite,
                    profileComGST, profileComPAN, profileComPassword,
                    profileComPasswordConfirm, profileComCountry,
                    profileComState, profileComCity, profileComPin, edit, cancel);


            profileComPassword.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(i1 != i2)
                        profileComPasswordConfirm.setText("");
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            cancel.setOnClickListener(v -> {
                try {
                    setData(profileComName,
                            profileComPerson, profileComEmail,
                            profileComPhone, profileComAddress, profileComWebsite,
                            profileComGST, profileComPAN, profileComPassword,
                            profileComPasswordConfirm, profileComCountry,
                            profileComState, profileComCity, profileComPin, edit, cancel);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });

            edit.setOnClickListener(v -> {
                if(!editProfile){
                    edit.setImageResource(R.mipmap.save);
                    edit.setImageTintList(ColorStateList.valueOf(Color.GREEN));
                    cancel.setVisibility(View.VISIBLE);
                    profileImage.setOnClickListener(v1 -> {
                        uploading = true;
                        showFileChooser();
                    });
                    profileComName.setEnabled(true);
                    profileComPerson.setEnabled(true);
                    profileComEmail.setEnabled(false);
                    profileComPhone.setEnabled(false);
                    profileComAddress.setEnabled(true);
                    profileComWebsite.setEnabled(true);
                    profileComGST.setEnabled(false);
                    profileComPAN.setEnabled(false);
                    profileComPassword.setEnabled(true);
                    profileComPasswordConfirm.setEnabled(true);
                    profileComCountry.setEnabled(true);
                    profileComState.setEnabled(true);
                    profileComCity.setEnabled(true);
                    profileComPin.setEnabled(true);
                } else {
                    stringName = profileComName.getText().toString();
                    stringPerson = profileComPerson.getText().toString();
                    stringEmail = profileComEmail.getText().toString();
                    stringPhone = profileComPhone.getText().toString();
                    stringAddress = profileComAddress.getText().toString();
                    stringWebsite = profileComWebsite.getText().toString();
                    stringGST = profileComGST.getText().toString();
                    stringPAN = profileComPAN.getText().toString();
                    stringPassword = profileComPassword.getText().toString();
                    stringPasswordConfirm = profileComPasswordConfirm.getText().toString();
                    stringCountry = profileComCountry.getText().toString();
                    stringState = profileComState.getText().toString();
                    stringCity = profileComCity.getText().toString();
                    stringPIN = profileComPin.getText().toString();

                    if(stringName.isEmpty()){
                        profileComName.setError("Please enter company name");
                        profileComName.requestFocus();
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);

                    } else if(stringPerson.isEmpty()){
                        profileComPerson.setError("Please enter contact person name");
                        profileComPerson.requestFocus();
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);

                    } else if(stringEmail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(stringEmail).matches()){
                        profileComEmail.setError("Please enter a valid email");
                        profileComEmail.requestFocus();
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);

                    } else if(stringPhone.length() != 10){
                        profileComPhone.setError("Please enter a valid phone no");
                        profileComPhone.requestFocus();
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);

                    } else if(stringAddress.isEmpty()){
                        profileComAddress.setError("Please enter a valid office address");
                        profileComAddress.requestFocus();
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);

                    } else if(stringGST.isEmpty()){
                        profileComGST.setError("Please enter a valid GST no");
                        profileComGST.requestFocus();
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);

                    } else if(stringPAN.isEmpty()){
                        profileComPAN.setError("Please enter a valid PAN no");
                        profileComPAN.requestFocus();
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);

                    } else if(stringPassword.isEmpty() || stringPassword.length()<8){
                        profileComPassword.setError("Please enter a valid minimum 8 character Password");
                        profileComPassword.requestFocus();
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);

                    } else if(stringPasswordConfirm.isEmpty() || !stringPasswordConfirm.equals(stringPassword)){
                        profileComPasswordConfirm.setError("Please enter the same Password again");
                        profileComPasswordConfirm.requestFocus();
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);

                    } else if(stringCountry.isEmpty()){
                        profileComCountry.setError("Please enter a valid country");
                        profileComCountry.requestFocus();
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);

                    } else if(stringState.isEmpty()){
                        profileComState.setError("Please enter a valid state");
                        profileComState.requestFocus();
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);

                    } else if(stringCity.isEmpty()){
                        profileComCity.setError("Please enter a valid city");
                        profileComCity.requestFocus();
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);

                    } else if(stringPIN.length()<6){
                        profileComPin.setError("Please enter a valid PIN code");
                        profileComPin.requestFocus();
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);

                    } else if(Methods.checkConnection(mContext)) {
                        if(type == 1)
                            path = getPath(filePath);
                        else if(type == 2)
                            path = fileUri.getPath();

                        editProfile(profileDialog);
                    } else
                        Methods.showSettingsAlert(mContext);
                }

                editProfile = !editProfile;
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static void editProfile(Dialog dialog) {

        String accessID = pref.getResponse(accessid);

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        if(file) {
            File sourceFile = new File(path);
            builder.addFormDataPart("image", sourceFile.getName(),
                    RequestBody.create(sourceFile, MediaType.parse("application/octet-stream")));
        }

        builder.addFormDataPart(accessid, accessID);

        builder.addFormDataPart(name, Base64.encodeToString(stringName.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT));
        builder.addFormDataPart(person, Base64.encodeToString(stringPerson.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT));
        builder.addFormDataPart(email, Base64.encodeToString(stringEmail.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT));
        builder.addFormDataPart(phone, Base64.encodeToString(stringPhone.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT));
        builder.addFormDataPart(address, Base64.encodeToString(stringAddress.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT));
        builder.addFormDataPart(website, Base64.encodeToString(stringWebsite.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT));
        builder.addFormDataPart(GST, Base64.encodeToString(stringGST.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT));
        builder.addFormDataPart(PAN, Base64.encodeToString(stringPAN.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT));
        builder.addFormDataPart(country, Base64.encodeToString(stringCountry.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT));
        builder.addFormDataPart(state, Base64.encodeToString(stringState.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT));
        builder.addFormDataPart(city, Base64.encodeToString(stringCity.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT));
        builder.addFormDataPart(PIN, Base64.encodeToString(stringPIN.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT));
        builder.addFormDataPart(password, Base64.encodeToString(stringPassword.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT));

        Methods.updateProfile(mActivity, pDialog, builder, accessID);

        dialog.dismiss();
    }

    private static void setData(EditText profileComName, EditText profileComPerson, EditText profileComEmail,
                                EditText profileComPhone, EditText profileComAddress, EditText profileComWebsite,
                                EditText profileComGST, EditText profileComPAN, EditText profileComPassword,
                                EditText profileComPasswordConfirm, EditText profileComCountry,
                                EditText profileComState, EditText profileComCity, EditText profileComPin,
                                ImageView edit, ImageView cancel) throws JSONException {

        edit.setImageResource(R.mipmap.edit1);
        edit.setImageTintList(ColorStateList.valueOf(Color.BLUE));
        cancel.setVisibility(View.GONE);
        profileImage.setOnClickListener(null);
        profileComName.setEnabled(false);
        profileComPerson.setEnabled(false);
        profileComEmail.setEnabled(false);
        profileComPhone.setEnabled(false);
        profileComAddress.setEnabled(false);
        profileComWebsite.setEnabled(false);
        profileComGST.setEnabled(false);
        profileComPAN.setEnabled(false);
        profileComPassword.setEnabled(false);
        profileComPasswordConfirm.setEnabled(false);
        profileComCountry.setEnabled(false);
        profileComState.setEnabled(false);
        profileComCity.setEnabled(false);
        profileComPin.setEnabled(false);

        Glide.with(mActivity)
                .load(MainURL + userResponse.getString(Config.image))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .placeholder(R.mipmap.account)
                .apply(RequestOptions.circleCropTransform())
                .into(profileImage);

        profileComName.setText(userResponse.getString(Config.name));
        profileComPerson.setText(userResponse.getString(Config.person));
        profileComEmail.setText(userResponse.getString(Config.email));
        profileComPhone.setText(userResponse.getString(Config.phone));
        profileComAddress.setText(userResponse.getString(Config.address));
        profileComWebsite.setText(userResponse.getString(Config.website));
        profileComGST.setText(userResponse.getString(Config.GST));
        profileComPAN.setText(userResponse.getString(Config.PAN));
        profileComPassword.setText(userResponse.getString(password));
        profileComPasswordConfirm.setText(userResponse.getString(password));
        profileComCountry.setText(userResponse.getString(Config.country));
        profileComState.setText(userResponse.getString(Config.state));
        profileComCity.setText(userResponse.getString(Config.city));
        profileComPin.setText(userResponse.getString(Config.PIN));

        uploading = false;
        editProfile = false;
        file = false;
    }

    static void showFileChooser(){
        path = "";
        filePath = fileUri = null;
        //Toast.makeText(mActivity, "showFileChooser()", Toast.LENGTH_SHORT).show();
        selectDialog = new Dialog(mActivity);
        selectDialog.setContentView(R.layout.dialog_image);
        //selectDialog.setCanceledOnTouchOutside(false);
        selectDialog.show();

        int width = (int)(mActivity.getResources().getDisplayMetrics().widthPixels*0.95);
        selectDialog.getWindow().setLayout(width, LinearLayout.LayoutParams.WRAP_CONTENT);
        selectDialog.getWindow().setGravity(Gravity.CENTER);

        final TextView imageCamera = selectDialog.findViewById(R.id.imageCamera);
        final TextView imageGallery = selectDialog.findViewById(R.id.imageGallery);

        imageCamera.setOnClickListener(view -> captureImage());

        imageGallery.setOnClickListener(view -> selectImage());

        selectDialog.setOnDismissListener(dialogInterface -> {
            filePath = null;
            fileUri = null;
            uploading = false;
        });
    }

    private static void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        mActivity.startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    public static Uri getOutputMediaFileUri(int type) {

        return FileProvider.getUriForFile(mContext, mContext.getApplicationContext().getPackageName() + ".provider", Objects.requireNonNull(getOutputMediaFile(type)));
    }

    private static File getOutputMediaFile(int type) {

        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                mActivity.getResources().getString(R.string.app_name));

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

    static void selectImage(){
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //intent.setType("image/*");
        //intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivity.startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    public static String getPath(Uri uri) {
        String filePath;
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);

        mContext = this;
        mActivity = this;
        imm = (InputMethodManager) mContext.getSystemService(Activity.INPUT_METHOD_SERVICE);
        pref = new AppPref(mContext);

        toolbar = mActivity.findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(mActivity.getResources().getColor(R.color.colorWhite));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.account);

        pDialog = new Dialog(mActivity, R.style.Dialog);
        pDialog.setContentView(R.layout.loading);

        initProfile();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.logout) {
            Methods.logout(mActivity, pDialog, pref.getResponse(accessid), "Logging out...");
            return false;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            mActivity.finish();
        } else {
            Toast.makeText(mActivity, "You are one tap away from EXIT!", Toast.LENGTH_SHORT).show();
        }

        back_pressed = System.currentTimeMillis();
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

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data.getData() != null) {
            filePath = data.getData();

            file = true;
            type = 1;

            previewImage(filePath);

        } else if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                file = true;
                type = 2;

                previewImage(fileUri);

            } else if (resultCode == RESULT_CANCELED) {

                Toast.makeText(getApplicationContext(), "User cancelled image capture", Toast.LENGTH_SHORT).show();

            } else {

                Toast.makeText(getApplicationContext(),"Sorry! Failed to capture image", Toast.LENGTH_SHORT).show();

            }

        }
    }

    private void previewImage(final Uri pathFile) {

        selectDialog.dismiss();

        if(editProfile) {
            Glide.with(mActivity)
                    .load(pathFile)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .placeholder(R.mipmap.account)
                    .apply(RequestOptions.circleCropTransform())
                    .into(profileImage);
        } else {

            final Dialog dialog_upload = new Dialog(mContext);
            dialog_upload.setContentView(R.layout.dialog_upload);
            dialog_upload.getWindow().setBackgroundDrawableResource(R.drawable.picture_bg);
            dialog_upload.setCanceledOnTouchOutside(false);
            dialog_upload.show();

            int width = (int)(mActivity.getResources().getDisplayMetrics().widthPixels*0.75);
            dialog_upload.getWindow().setLayout(width, LinearLayout.LayoutParams.WRAP_CONTENT);
            dialog_upload.getWindow().setGravity(Gravity.CENTER);

            final ImageView imageProfile = dialog_upload.findViewById(R.id.imageProfile);
            final Button buttonProfile = dialog_upload.findViewById(R.id.buttonProfile);
            final Button buttonRetry = dialog_upload.findViewById(R.id.buttonRetry);

            Glide.with(mContext)
                    .load(pathFile)
                    .apply(new RequestOptions().circleCrop())
                    .placeholder(R.mipmap.account)
                    .into(imageProfile);

            buttonProfile.setOnClickListener(view -> {
                if(type == 1)
                    path = getPath(pathFile);
                else if(type == 2)
                    path = pathFile.getPath();

                //Log.e("path", "URI: " + path);

                editProfile(dialog_upload);
            });

            buttonRetry.setOnClickListener(view -> {
                showFileChooser();
                dialog_upload.dismiss();
            });
        }
    }

}
