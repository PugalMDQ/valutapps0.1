package com.MDQ.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.MDQ.myapplication.interfaces.InterfaceForCardList;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateAccountListResponseModel;
import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

import java.util.zip.Inflater;

public class adapterforcardlist extends RecyclerView.Adapter<adapterforcardlist.mine> {

    int a;
    InterfaceForCardList interfaceForCardList;
    Context context;
    GenerateAccountListResponseModel generateAccountListResponseModel;
    adapterforcardlist(GenerateAccountListResponseModel generateAccountListResponseModel, Context context, InterfaceForCardList interfaceForCardList){
        this.interfaceForCardList=interfaceForCardList;
        this.generateAccountListResponseModel=generateAccountListResponseModel;
        this.context=context;
          }
    @NonNull
    @Override
    public adapterforcardlist.mine onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutforcardlist,parent,false);
        mine mm=new mine(view);
        return mm;
    }

    @Override
    public void onBindViewHolder(@NonNull adapterforcardlist.mine holder, @SuppressLint("RecyclerView") int position) {
        if(generateAccountListResponseModel.getData().size()>0){
            Glide.with(context)
                    .load(generateAccountListResponseModel.getData().get(position).getBank_logo())
                    .into(holder.logo);
            String header=generateAccountListResponseModel.getData().get(position).getBank_name()+" "+
                    generateAccountListResponseModel.getData().get(position).getAccount_type()+" account";
            holder.heading.setText(header);
            String Color_Code = generateAccountListResponseModel.getData().get(position).getBrand_color_code().toUpperCase();
            holder.constraintLayout.setBackgroundColor(Color.parseColor(Color_Code));

            if(generateAccountListResponseModel.getData().get(position).getAccount_number()!=null){
                holder.Accountnum.setText(generateAccountListResponseModel.getData().get(position).getAccount_number());
            }


            holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    interfaceForCardList.OpenCard(position,generateAccountListResponseModel.getData().get(position).getId());
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return generateAccountListResponseModel.getData().size();
    }

    public class mine extends RecyclerView.ViewHolder {
        ImageView logo;
        TextView heading,Accountnum;
        ConstraintLayout constraintLayout;

        public mine(@NonNull View itemView) {
            super(itemView);
            logo=itemView.findViewById(R.id.logo);
            heading=itemView.findViewById(R.id.heading);
            Accountnum=itemView.findViewById(R.id.Accountnum);
            constraintLayout=itemView.findViewById(R.id.constaraint);



        }
    }
}
