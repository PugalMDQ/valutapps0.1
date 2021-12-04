package com.vibro.care.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.vibro.care.BuildConfig;
import com.vibro.care.Config.AppPref;
import com.vibro.care.Config.Methods;
import com.vibro.care.R;

import java.util.List;
import java.util.Objects;

import static com.vibro.care.Config.Config.login;

public class SplashActivity extends AppCompatActivity {

    Context mContext;
    Activity mActivity;
    Dialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        setContentView(R.layout.activity_splash);

        Objects.requireNonNull(getSupportActionBar()).hide();

        mContext = this;
        mActivity = this;
        pDialog = new Dialog(mActivity, R.style.Dialog);
        pDialog.setContentView(R.layout.loading);

        TextView textVersion = findViewById(R.id.textVersion);
        textVersion.setText("Version: v" + BuildConfig.VERSION_NAME);
    }

    @Override
    protected void onStart() {
        super.onStart();
        askforPermissions();
    }

    private void askforPermissions(){
        Dexter.withContext(mContext)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            methodProceed();
                        } else if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        } else {
                            askforPermissions();
                        }

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();
    }

    private void methodProceed() {
        AppPref pref = new AppPref(mContext);

        if(Boolean.parseBoolean(pref.getResponse(login))){
            Methods.checkLogin(pref, mActivity, pDialog, "", 0);
        } else {
            new Handler().postDelayed(() -> {
                startActivity(new Intent(mContext, LoginActivity.class));
                finish();
            }, 3000);
        }
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.cancel();
            finish();
        });
        builder.show();

    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

    }
}
