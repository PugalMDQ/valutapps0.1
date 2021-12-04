package com.MDQ.myapplication;

import static com.MDQ.myapplication.base.MyFinalystApp.getContext;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.MDQ.myapplication.databinding.ActivityOpencardBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheet extends BottomSheetDialogFragment {

    public static AdapterForOpenCard adapter;
    public  static LinearLayoutManager layoutManager;
    public static RecyclerView recyclerView;

    BottomSheetBehavior bottomSheetBehavior;

    Context context;
    BottomSheet(Context context){
        this.context=context;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final com.google.android.material.bottomsheet.BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

        Toast.makeText(getContext(), "dcdncj", Toast.LENGTH_SHORT).show();
        View view = View.inflate(getContext(), R.layout.bottomforopencard, null);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);

        bottomSheetBehavior = BottomSheetBehavior.from((View) (view.getParent()));

        ViewGroup.LayoutParams layoutParams=view.getLayoutParams();
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int height = metrics.heightPixels;

        layoutParams.height=height;
//        adapter=new AdapterForOpenCard();
//        layoutManager = new LinearLayoutManager(getContext());
//        recyclerView = dialog.findViewById(R.id.rvinopencard);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(layoutManager);
        return dialog;
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//
//        //setting max height of bottom sheet
//        DisplayMetrics metrics = new DisplayMetrics();
//        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
//        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, (int) (metrics.heightPixels * 0.50));// here i have fragment height 30% of window's height you can set it as per your requirement
//        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//    }
}

