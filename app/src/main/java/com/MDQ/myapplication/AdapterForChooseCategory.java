package com.MDQ.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.MDQ.myapplication.interfaces.InterfaceForChooseCategoryAdapter;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateCategorySpentResponseModel;
import com.MDQ.myapplication.utils.PreferenceManager;
import com.bumptech.glide.Glide;

public class AdapterForChooseCategory extends RecyclerView.Adapter<AdapterForChooseCategory.mine> {


    GenerateCategorySpentResponseModel generateCategorySpentResponseModel;
    Context context;
    InterfaceForChooseCategoryAdapter interfaceForChooseCategoryAdapter;
    AdapterForChooseCategory(Context context,GenerateCategorySpentResponseModel generateCategorySpentResponseModel,InterfaceForChooseCategoryAdapter interfaceForChooseCategoryAdapter){
        this.interfaceForChooseCategoryAdapter=interfaceForChooseCategoryAdapter;
        this.generateCategorySpentResponseModel=generateCategorySpentResponseModel;
        this.context=context;
    }
    @NonNull
    @Override
    public mine onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutforchoosec,parent,false);
        return new mine(view);
    }

    @Override
    public void onBindViewHolder(@NonNull mine holder, @SuppressLint("RecyclerView") int position) {
        if(generateCategorySpentResponseModel.getData().size()>0){
            holder.name.setText(generateCategorySpentResponseModel.getData().get(position).getName());
            Glide.with(context)
                    .load(generateCategorySpentResponseModel.getData().get(position).getLogo())
                    .into(holder.logo);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    interfaceForChooseCategoryAdapter.ChooseCategory(generateCategorySpentResponseModel.getData().get(position).getLogo(),
                            generateCategorySpentResponseModel.getData().get(position).getName());


                    }
            });
        }
    }

    @Override
    public int getItemCount() {
        return generateCategorySpentResponseModel.getData().size();
    }

    public class mine extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        ImageView logo;
        TextView name;
        PreferenceManager preferenceManager;
        public mine(@NonNull View itemView) {
            super(itemView);
            linearLayout=itemView.findViewById(R.id.linearlogo);
            logo=itemView.findViewById(R.id.logoss);
            name=itemView.findViewById(R.id.namess);


        }

    }



}
