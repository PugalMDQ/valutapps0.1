package com.MDQ.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.MDQ.myapplication.enums.MessageViewType;
import com.MDQ.myapplication.enums.ViewType;
import com.MDQ.myapplication.interfaces.viewresponceinterface.UploadVaultResponceInterface;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateUploadVaultResponseModel;
import com.MDQ.myapplication.utils.PreferenceManager;
import com.MDQ.myapplication.viewmodel.UploadVaultViewModel;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class FileUploading extends AppCompatActivity implements UploadVaultResponceInterface {

    UploadVaultViewModel uploadVaultViewModel;
    LinearLayout chooser;
    TextView nameOfFile;
    File file;
    RequestBody somedata;
    RequestBody requestBody;
    String selected;
    MultipartBody.Part parts;
    PreferenceManager preferenceManager;
    RequestBody type;
    MultipartBody.Part filePart;
    MultipartBody.Part partImage;
    RequestBody reqBody;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_uploading);
        chooser=findViewById(R.id.chooser);
        nameOfFile=findViewById(R.id.NameOfFile);


        chooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent filesIntent;
                filesIntent = new Intent(Intent.ACTION_GET_CONTENT);
                filesIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                filesIntent.addCategory(Intent.CATEGORY_OPENABLE);
                filesIntent.setType("*/*");
                startActivityForResult(filesIntent, 2);  }
        });
        permission();

    }

    private void permission() {
        Dexter.withContext(this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission
        .WRITE_EXTERNAL_STORAGE).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                get();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }

    private void get() {

        uploadVaultViewModel=new UploadVaultViewModel(getApplicationContext(),this);
        nameOfFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadVaultViewModel.setToken(getPreferenceManager().getPrefToken());
                uploadVaultViewModel.setProof(reqBody);
                uploadVaultViewModel.setFilename(partImage);
                uploadVaultViewModel.generateUploadVaultRequest();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode==2 && data!=null) {
            Uri returnUri = data.getData();
            Cursor returnCursor =
                    getContentResolver().query(returnUri, null, null, null, null);
            returnCursor.moveToFirst();
            int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);

            file = new File(returnCursor.getString(nameIndex));
            File file1 = new File(file.getAbsolutePath());
            nameOfFile.setText(returnCursor.getString(nameIndex));

            //selected=FileUtils.getPath(FileUploading.this,getUri(FileUploading.this,returnUri));

            Toast.makeText(getApplicationContext(), "" + file, Toast.LENGTH_SHORT).show();

            reqBody = RequestBody.create(MediaType.parse("multipart/form-file"), file1);
            partImage = MultipartBody.Part.createFormData("file", file1.getName(), reqBody);

//        RequestBody tokenRequest = RequestBody.create(MediaType.parse("multipart/form-data"), file1);
//        type = RequestBody.create(MediaType.parse("text/plain"), true + "proof");
//        filePart = MultipartBody.Part.createFormData("file", file.getName(),tokenRequest);

        }
    }

    private Uri getUri(FileUploading fileUploading, Uri returnUri) {
        String pathss = null;
        try {
             pathss= MediaStore.Images.Media.insertImage(fileUploading.getContentResolver(),selected,"file","");
      
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return Uri.parse(pathss);
    }

    @Override
    public void ShowErrorMessage(MessageViewType messageViewType, String errorMessage) {

    }

    @Override
    public void ShowErrorMessage(MessageViewType messageViewType, ViewType viewType, String errorMessage) {

    }

    @Override
    public void generateUploadVaultProcessed(GenerateUploadVaultResponseModel generateUploadVaultResponseInterface) {
        Toast.makeText(getApplicationContext(), ""+generateUploadVaultResponseInterface.getMsg(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(ErrorBody errorBody, int statusCode) {

    }
    public PreferenceManager getPreferenceManager() {
        if (preferenceManager == null) {
            preferenceManager = PreferenceManager.getInstance();
            preferenceManager.initialize(getApplicationContext());
        }
        return preferenceManager;
    }

}