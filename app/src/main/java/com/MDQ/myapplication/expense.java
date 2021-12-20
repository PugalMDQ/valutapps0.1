 package com.MDQ.myapplication;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.MDQ.myapplication.enums.MessageViewType;
import com.MDQ.myapplication.enums.ViewType;
import com.MDQ.myapplication.interfaces.viewresponceinterface.AddSpendResponceInterface;
import com.MDQ.myapplication.interfaces.viewresponceinterface.BankListResponseInterface;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateAddSpendResponceModel;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateBankListResponseModel;
import com.MDQ.myapplication.utils.PreferenceManager;
import com.MDQ.myapplication.viewmodel.AddSpentViewModel;
import com.MDQ.myapplication.viewmodel.BankListViewModel;
import com.bumptech.glide.Glide;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.onegravity.contactpicker.contact.Contact;
import com.onegravity.contactpicker.contact.ContactDescription;
import com.onegravity.contactpicker.contact.ContactSortOrder;
import com.onegravity.contactpicker.core.ContactPickerActivity;
import com.onegravity.contactpicker.picture.ContactPictureType;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.Permission;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

 public class expense extends Fragment implements BankListResponseInterface , AddSpendResponceInterface {
     TextView textView;
     ImageView imageView, contactpicker;
     CardView cardforgone, cardforamount, cardforaccounttype,
             cardforcategory, cardforday, cardfornotes;
     LinearLayout insta;
     int i = 0;
     int fitImage = 0;
     String nameOfContact;
     LinearLayout category;
     RecyclerView recyclerView;
     AdapterForRvInContact adapterForRvInContact;
     LinearLayoutManager layoutManager;
     AutoCompleteTextView accountType;
     ImageView ScroolAccountType;
     String token;
     BankListViewModel bankListViewModel;
     AddSpentViewModel addSpentViewModel;
     PreferenceManager preferenceManager;
     String[] banklist;
     EditText Category, today, amount,note;
     int[] id;
     String amounts, accounts, dates,categories,notes,newDate;
     String logo, name;
     ImageView logosi;
     List<String> nameForContact;
     ImageView GalleryImage,capture;
     ArrayAdapter<String> adapterforBankList;
     ImageView ImageForUserVisible,cross;
     RequestBody requestFile,description;
     MultipartBody.Part body;
     @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_expense, container, false);
         //initialize with view
         accountType = view.findViewById(R.id.Accounttypes);
         category = view.findViewById(R.id.categoryScroll);
         imageView = view.findViewById(R.id.additionaldetail);
         cardforgone = view.findViewById(R.id.cardforgone);
         insta = view.findViewById(R.id.insta);
         textView = view.findViewById(R.id.addaccountsubmit);
         cardforamount = view.findViewById(R.id.cardforamount);
         cardforcategory = view.findViewById(R.id.cardforcategory);
         cardforaccounttype = view.findViewById(R.id.Cardforaccounttype);
         cardforday = view.findViewById(R.id.cardforday);
         cardfornotes = view.findViewById(R.id.cardfornotes);
         recyclerView = view.findViewById(R.id.rvinnamelist);
         ScroolAccountType = view.findViewById(R.id.scrolforaccounttype);
         bankListViewModel = new BankListViewModel(getContext(), this);
         Category = view.findViewById(R.id.Category);
         today = view.findViewById(R.id.editdate);
         addSpentViewModel = new AddSpentViewModel(getContext(), this);
         token = getPreferenceManager().getPrefToken();
         amount = view.findViewById(R.id.Editamount);
         logosi = view.findViewById(R.id.choosecategory);
         contactpicker = view.findViewById(R.id.contactpicker);
         GalleryImage = view.findViewById(R.id.GalaryImage);
         capture=view.findViewById(R.id.capture);
         note=view.findViewById(R.id.Note);
         ImageForUserVisible=view.findViewById(R.id.ImageForUserVisible);
         cross=view.findViewById(R.id.cross);


         setclick();
         setbackround();
         setDeclare();

         //set inputType as null to editText
         accountType.setRawInputType(InputType.TYPE_NULL);
         accountType.setFocusable(true);
         Category.setRawInputType(InputType.TYPE_NULL);
         Category.setFocusable(true);
         today.setRawInputType(InputType.TYPE_NULL);
         today.setFocusable(true);

         //checking permission of READ_CONTACT if available calling getContact methode else calling getPermission
         contactpicker.setOnClickListener(new View.OnClickListener() {
             @RequiresApi(api = Build.VERSION_CODES.M)
             @Override
             public void onClick(View v) {
                 if (getContext().checkSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                     getContact();
                 } else {
                     getPermission();
                 }
             }
         });

         //checking permission of CAMERA if available calling getCameraImage methode else calling getPermissionCamera
         capture.setOnClickListener(new View.OnClickListener() {
             @RequiresApi(api = Build.VERSION_CODES.M)
             @Override
             public void onClick(View v) {
                 if (getContext().checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                 && getContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                     getCameraImage();
                 } else {
                     getPermissionCamera();
                 }
             }
         });

         //intent for getting image
         GalleryImage.setOnClickListener(new View.OnClickListener() {
             @RequiresApi(api = Build.VERSION_CODES.M)
             @Override
             public void onClick(View v) {
                 if (getContext().checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                     getFileImages();
                 }else {
                     getPermissionForSelectImage();
                 }
             }
         });

         cross.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 ImageForUserVisible.setImageDrawable(null);
                 fitImage=0;
                 ImageForUserVisible.setVisibility(View.GONE);
                 cross.setVisibility(View.GONE);
                 imageView.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                 cardforgone.setVisibility(View.VISIBLE);
                 insta.setVisibility(View.VISIBLE);
                 ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) AddTransactionscreen.constraintLayout.getLayoutParams();
                 layoutParams.topMargin = 312;
                 AddTransactionscreen.constraintLayout.setLayoutParams(layoutParams);
                 AddTransactionscreen.buttons.bringToFront();
             }
         });

         //set visibility of card for gone
         imageView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 cardforgone.setVisibility(View.VISIBLE);
             }
         });
         //changing visibility and layout params
         imageView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(fitImage!=1) {
                     if (i == 0) {
                         i = 1;
                         imageView.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                         cardforgone.setVisibility(View.VISIBLE);
                         insta.setVisibility(View.VISIBLE);
                         ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) AddTransactionscreen.constraintLayout.getLayoutParams();
                         layoutParams.topMargin = 312;
                         AddTransactionscreen.constraintLayout.setLayoutParams(layoutParams);
                         AddTransactionscreen.buttons.bringToFront();
                     } else {
                         i = 0;
                         imageView.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                         cardforgone.setVisibility(View.GONE);
                         insta.setVisibility(View.GONE);
                         ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) AddTransactionscreen.constraintLayout.getLayoutParams();
                         layoutParams.topMargin = 412;
                         AddTransactionscreen.constraintLayout.setLayoutParams(layoutParams);
                         AddTransactionscreen.buttons.bringToFront();

                     }
                 }
             }
         });

         //Checking internet connection and if available calling setDeclareFoeAddSpend method else toast an error message
         textView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                     ConnectivityManager connectivityManager = (ConnectivityManager) getContext()
                             .getSystemService(Context.CONNECTIVITY_SERVICE);
                     if ((connectivityManager
                             .getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null && connectivityManager
                             .getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)
                             || (connectivityManager
                             .getNetworkInfo(ConnectivityManager.TYPE_WIFI) != null && connectivityManager
                             .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                             .getState() == NetworkInfo.State.CONNECTED)) {
                         setDeclareForAddSpend();
                     }else{
                         Toast.makeText(getContext(), "This App Require Internet", Toast.LENGTH_SHORT).show();
                     }
                 }
         });
         return view;
     }

     private void getFileImages() {
         Intent intent = new Intent();
         intent.setType("image/*");
         intent.setAction(Intent.ACTION_GET_CONTENT);
         startActivityForResult(Intent.createChooser(intent,
                 "Select Picture"), 3);
     }

     private void getPermissionForSelectImage() {
         Dexter.withContext(getContext()).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
             @Override
             public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                 getFileImages();
             }

             @Override
             public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

             }

             @Override
             public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                 permissionToken.continuePermissionRequest();
             }
         }).check();
     }

     //get permission
     private void getPermissionCamera() {
         Dexter.withContext(getContext()).withPermissions(Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new MultiplePermissionsListener() {
             @Override
             public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                 getCameraImage();
             }

             @Override
             public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {

             }
         }).check();
     }

     //set request for the get image from camera
     private void getCameraImage() {
         Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
         startActivityForResult(cameraIntent, 1);

     }

     //getpermission
     private void getPermission() {
         Dexter.withContext(getContext()).withPermission(Manifest.permission.READ_CONTACTS)
                 .withListener(new PermissionListener() {
                     @Override
                     public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                         getContact();
                     }

                     @Override
                     public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                     }

                     @Override
                     public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                         permissionToken.continuePermissionRequest();
                     }
                 }).check();
     }

     //get request for contack picker
     private void getContact() {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
         intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
         startActivityForResult(intent, 2);
    }

     @Override
     public void onDestroy() {
         super.onDestroy();
         getPreferenceManager().setPrefImageUrl("");
         logosi.setVisibility(View.GONE);
         getPreferenceManager().setPrefNameInCategory("");
     }

     @Override
     public void onResume() {
         super.onResume();
         if (getPreferenceManager().getPrefImageUrl() != null && getPreferenceManager().getPrefNameInCategory() != null) {
             if (!getPreferenceManager().getPrefImageUrl().isEmpty() && !getPreferenceManager().getPrefNameInCategory().isEmpty()) {
                 logosi.setVisibility(View.VISIBLE);
                 Glide.with(this)
                         .load(getPreferenceManager().getPrefImageUrl())
                         .into(logosi);
                 Category.setText(getPreferenceManager().getPrefNameInCategory());
             }
         }
     }

     //set request to the AddSpend api
     private void setDeclareForAddSpend() {
         if(amounts!=null && accounts!=null&& dates!=null && Category!=null&& note!=null) {

             addSpentViewModel.setAmount(amounts);
             addSpentViewModel.setAccount_id(accounts);
             addSpentViewModel.setDate(dates);
             addSpentViewModel.setCategory(Category.getText().toString());
             addSpentViewModel.setLocation("chennai");
             addSpentViewModel.setNote(note.getText().toString());
             addSpentViewModel.setSubcategory("get");
             addSpentViewModel.setToken(getPreferenceManager().getPrefToken());
             addSpentViewModel.setProfile_picture(body);
             addSpentViewModel.setShare_with("sanjai");
             addSpentViewModel.setTag("sam");
             addSpentViewModel.setType("1");
             addSpentViewModel.generateAddSpendRequest();

         }else{
             Toast.makeText(getActivity(), "Enter All Fields", Toast.LENGTH_SHORT).show();
         }
     }

     //set request to the bankList api
     private void setDeclare() {
         bankListViewModel.setToken(token);
         bankListViewModel.generateBankListRequest();
     }

     @SuppressLint("ClickableViewAccessibility")
     private void setclick() {
         //day picker
         cardforday.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Calendar calendar = Calendar.getInstance();
                 int years = calendar.get(Calendar.YEAR);
                 int months = calendar.get(Calendar.MONTH);
                 int days = calendar.get(Calendar.DAY_OF_MONTH);

                 DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                     @Override
                     public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                         month=month+1;
                         dates = year + "-" + month + "-" + dayOfMonth;
                         today.setText(dates);
                     }
                 }, years, months, days);
                 int positiveColor = ContextCompat.getColor(getContext(), R.color.blue);
                 int negativeColor = ContextCompat.getColor(getContext(), R.color.blue);

                 datePickerDialog.show();
                 datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(positiveColor);
                 datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(negativeColor);

             }
         });

         //changing background
         cardforgone.setOnTouchListener(new View.OnTouchListener() {
             @Override
             public boolean onTouch(View v, MotionEvent event) {
                 cardforgone.setBackground(getResources().getDrawable(R.drawable.consbackroundforaddaccountforactive));
                 cardforamount.setBackground(getResources().getDrawable(R.drawable.consbackroundforaddaccount));
                 cardforaccounttype.setBackground(getResources().getDrawable(R.drawable.consbackroundforaddaccount));
                 cardforcategory.setBackground(getResources().getDrawable(R.drawable.consbackroundforaddaccount));
                 cardforday.setBackground(getResources().getDrawable(R.drawable.consbackroundforaddaccount));
                 cardfornotes.setBackground(getResources().getDrawable(R.drawable.consbackroundforaddaccount));
                 return false;

             }
         });

         //changing background
         cardforaccounttype.setOnTouchListener(new View.OnTouchListener() {
             @Override
             public boolean onTouch(View v, MotionEvent event) {
                 cardforgone.setBackground(getResources().getDrawable(R.drawable.consbackroundforaddaccount));
                 cardforamount.setBackground(getResources().getDrawable(R.drawable.consbackroundforaddaccount));
                 cardforaccounttype.setBackground(getResources().getDrawable(R.drawable.consbackroundforaddaccountforactive));
                 cardforcategory.setBackground(getResources().getDrawable(R.drawable.consbackroundforaddaccount));
                 cardforday.setBackground(getResources().getDrawable(R.drawable.consbackroundforaddaccount));
                 cardfornotes.setBackground(getResources().getDrawable(R.drawable.consbackroundforaddaccount));
                 return false;

             }
         });

         //changing background
         cardforamount.setOnTouchListener(new View.OnTouchListener() {
             @Override
             public boolean onTouch(View v, MotionEvent event) {
                 cardforgone.setBackground(getResources().getDrawable(R.drawable.consbackroundforaddaccount));
                 cardforamount.setBackground(getResources().getDrawable(R.drawable.consbackroundforaddaccountforactive));
                 cardforaccounttype.setBackground(getResources().getDrawable(R.drawable.consbackroundforaddaccount));
                 cardforcategory.setBackground(getResources().getDrawable(R.drawable.consbackroundforaddaccount));
                 cardforday.setBackground(getResources().getDrawable(R.drawable.consbackroundforaddaccount));
                 cardfornotes.setBackground(getResources().getDrawable(R.drawable.consbackroundforaddaccount));
                 return false;

             }
         });

         //changing background
         cardforday.setOnTouchListener(new View.OnTouchListener() {
             @Override
             public boolean onTouch(View v, MotionEvent event) {
                 cardforgone.setBackground(getResources().getDrawable(R.drawable.consbackroundforaddaccount));
                 cardforamount.setBackground(getResources().getDrawable(R.drawable.consbackroundforaddaccount));
                 cardforaccounttype.setBackground(getResources().getDrawable(R.drawable.consbackroundforaddaccount));
                 cardforcategory.setBackground(getResources().getDrawable(R.drawable.consbackroundforaddaccount));
                 cardforday.setBackground(getResources().getDrawable(R.drawable.consbackroundforaddaccountforactive));
                 cardfornotes.setBackground(getResources().getDrawable(R.drawable.consbackroundforaddaccount));
                 return false;

             }
         });

         //changing background
         cardforcategory.setOnTouchListener(new View.OnTouchListener() {
             @Override
             public boolean onTouch(View v, MotionEvent event) {
                 cardforgone.setBackground(getResources().getDrawable(R.drawable.consbackroundforaddaccount));
                 cardforamount.setBackground(getResources().getDrawable(R.drawable.consbackroundforaddaccount));
                 cardforaccounttype.setBackground(getResources().getDrawable(R.drawable.consbackroundforaddaccount));
                 cardforcategory.setBackground(getResources().getDrawable(R.drawable.consbackroundforaddaccountforactive));
                 cardforday.setBackground(getResources().getDrawable(R.drawable.consbackroundforaddaccount));
                 cardfornotes.setBackground(getResources().getDrawable(R.drawable.consbackroundforaddaccount));
                 return false;

             }
         });

         //changing background
         cardfornotes.setOnTouchListener(new View.OnTouchListener() {
             @Override
             public boolean onTouch(View v, MotionEvent event) {
                 cardforgone.setBackground(getResources().getDrawable(R.drawable.consbackroundforaddaccount));
                 cardforamount.setBackground(getResources().getDrawable(R.drawable.consbackroundforaddaccount));
                 cardforaccounttype.setBackground(getResources().getDrawable(R.drawable.consbackroundforaddaccount));
                 cardforcategory.setBackground(getResources().getDrawable(R.drawable.consbackroundforaddaccount));
                 cardforday.setBackground(getResources().getDrawable(R.drawable.consbackroundforaddaccount));
                 cardfornotes.setBackground(getResources().getDrawable(R.drawable.consbackroundforaddaccountforactive));
                 return false;

             }
         });

         //add position of selected item to the accounts
         accountType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 accounts = String.valueOf(position + 1);
             }
         });

         //ScrollDown for accountType
         ScroolAccountType.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 accountType.showDropDown();
             }
         });
         //ScrollDown for accountType
         accountType.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 accountType.showDropDown();
             }
         });

         //navigate to ChooseCategory screen
         cardforcategory.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(getActivity(), ChooseCategory.class));
             }
         });

         ///Navigate to chooseCategory screen
         category.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(getActivity(), ChooseCategory.class));
             }
         });
     }

     //set background for edit text
     private void setbackround() {
         cardforgone.setBackground(getResources().getDrawable(R.drawable.consbackroundforaddaccount));
         cardforamount.setBackground(getResources().getDrawable(R.drawable.consbackroundforaddaccount));
         cardforaccounttype.setBackground(getResources().getDrawable(R.drawable.consbackroundforaddaccount));
         cardforcategory.setBackground(getResources().getDrawable(R.drawable.consbackroundforaddaccount));
         cardforday.setBackground(getResources().getDrawable(R.drawable.consbackroundforaddaccount));
         cardfornotes.setBackground(getResources().getDrawable(R.drawable.consbackroundforaddaccount));
     }

     @Override
     public void ShowErrorMessage(MessageViewType messageViewType, String errorMessage) {
         //do nothing
     }

     @Override
     public void ShowErrorMessage(MessageViewType messageViewType, ViewType viewType, String errorMessage) {
         //do nothing

     }

     /**
      * @param generateBankListResponseModel
      * @breif get response from BankList api
      */
     @Override
     public void generateBankListProcessed(GenerateBankListResponseModel generateBankListResponseModel) {
         banklist = new String[generateBankListResponseModel.getData().size()];
         id = new int[generateBankListResponseModel.getData().size()];

         for (int i = 0; i < generateBankListResponseModel.getData().size(); i++) {
             banklist[i] = generateBankListResponseModel.getData().get(i).getName();
             id[i] = generateBankListResponseModel.getData().get(i).getId();
         }
         if(!banklist.equals(null)){
             try {
                 adapterforBankList= new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, banklist);
                 accountType.setAdapter(adapterforBankList);
             }
             catch (Exception e){

             }

         }
     }

     /**
      * @param generateAddSpendResponceModel
      * @breif get response from AddSpend api
      */
     @Override
     public void generateAddSpendProcessed(GenerateAddSpendResponceModel generateAddSpendResponceModel) {
         Toast.makeText(getContext(), generateAddSpendResponceModel.getMsg(), Toast.LENGTH_SHORT).show();
         startActivity(new Intent(getContext(),cardslist.class));
     }

     @Override
     public void onFailure(ErrorBody errorBody, int statusCode) {
         //do nothing
     }

     /**
      * @return
      * @brief initializing the preferenceManager from shared preference for local use in this activity
      */
     public PreferenceManager getPreferenceManager() {
         if (preferenceManager == null) {
             preferenceManager = PreferenceManager.getInstance();
             preferenceManager.initialize(getContext());
         }
         return preferenceManager;
     }


     //getting result for requested contact,GalleryImage,cameraImage
     @Override
     public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
         super.onActivityResult(requestCode, resultCode, data);
         if (requestCode == 2) {
             if (resultCode == RESULT_OK) {
                 if (data != null) {
                     Uri uri = data.getData();
                     String[] projection = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};

                     Cursor cursor = getActivity().getContentResolver().query(uri, projection,
                             null, null, null);
                     cursor.moveToFirst();
                     int nameColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                     nameForContact = new ArrayList<>();
                     nameForContact.add(cursor.getString(nameColumnIndex));
                     nameOfContact=cursor.getString(nameColumnIndex);
                     adapterForRvInContact = new AdapterForRvInContact(nameForContact);
                     layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                     recyclerView.setLayoutManager(layoutManager);
                     recyclerView.setAdapter(adapterForRvInContact);


                 }

             }
         }
         else if (requestCode == 3)
         {
             if(data!=null) {
                 Uri image = data.getData();
                 Bitmap bitmap = null;
                 try {
                     bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), image);

                 } catch (IOException e) {
                 }
                 insta.setVisibility(View.GONE);
                 cardforgone.setVisibility(View.GONE);
                 cross.setVisibility(View.VISIBLE);
                 ImageForUserVisible.setVisibility(View.VISIBLE);
                 ImageForUserVisible.setImageBitmap(bitmap);
                 fitImage=1;

                 File files=new File(image.getPath());
                 File f1= new File(files.getAbsolutePath());
                 File file = new File(FileUtils.getPath(getContext(), image));
                 // create RequestBody instance from file
                 requestFile =
                         RequestBody.create(
                                 MediaType.parse(getContext().getContentResolver().getType(image)),
                                 file
                         );
                 // MultipartBody.Part is used to send also the actual file name
                 body = MultipartBody.Part.createFormData("profile_picture", f1.getName(), requestFile);
                 // add another part within the multipart request
                 String descriptionString = "hello, this is description speaking";
                 description =
                         RequestBody.create(
                                 okhttp3.MultipartBody.FORM, descriptionString);
             }
         }else if(requestCode==1){
             Bitmap photo = null;
             if(data!=null ) {

                 Bundle extra = data.getExtras();
                 Bitmap photos =(Bitmap)extra.get("data");

                 Uri tempUri = getImageUri(getContext(), photos);

                 cardforgone.setVisibility(View.GONE);
                 insta.setVisibility(View.GONE);
                 cross.setVisibility(View.VISIBLE);
                 photo = (Bitmap) data.getExtras().get("data");
                 ImageForUserVisible.setVisibility(View.VISIBLE);
                 ImageForUserVisible.setImageBitmap(photo);
                 fitImage=1;

                 File files=new File(tempUri.getPath());
                 File f1= new File(files.getAbsolutePath());
                 File file = new File(FileUtils.getPath(getContext(), tempUri));
                 // create RequestBody instance from file
                 requestFile =
                         RequestBody.create(
                                 MediaType.parse(getContext().getContentResolver().getType(tempUri)),
                                 file
                         );
                 // MultipartBody.Part is used to send also the actual file name
                 body = MultipartBody.Part.createFormData("profile_picture", f1.getName(), requestFile);
                 // add another part within the multipart request
                 String descriptionString = "hello, this is description speaking";
                 description =
                         RequestBody.create(
                                 okhttp3.MultipartBody.FORM, descriptionString);
             }
         }

     }
     public Uri getImageUri(Context inContext, Bitmap inImage) {
         ByteArrayOutputStream bytes = new ByteArrayOutputStream();
         inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
         String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
         return Uri.parse(path);
     }


 }
