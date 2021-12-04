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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.MDQ.myapplication.interfaces.InterfaceForBalanceHome;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateAccountListResponseModel;
import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

import java.util.stream.Stream;

public class Adapterforrecyclerinhome extends RecyclerView.Adapter<Adapterforrecyclerinhome.mine> {

    Context context;
    GenerateAccountListResponseModel generateAccountListResponseModel;
    InterfaceForBalanceHome interfaceForBalanceHome;
    int id;
    int position;
    Adapterforrecyclerinhome(Context context, GenerateAccountListResponseModel generateAccountListResponseModel,InterfaceForBalanceHome interfaceForBalanceHome){
        this.interfaceForBalanceHome=interfaceForBalanceHome;
        this.context=context;
        this.generateAccountListResponseModel=generateAccountListResponseModel;
    }
    @NonNull
    @Override
    public mine onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutforadapterinhome,parent,false);
        return new mine(view);
    }

    @Override
    public void onBindViewHolder(@NonNull mine holder, @SuppressLint("RecyclerView") int position) {

        if(generateAccountListResponseModel.getData().size()>0) {
            String Color_Code = generateAccountListResponseModel.getData().get(position).getBrand_color_code().toUpperCase();
            holder.constraintLayout.setBackgroundColor(Color.parseColor(Color_Code));
            holder.balance.setText(generateAccountListResponseModel.getData().get(position).getCurrent_balance());
            Glide.with(context)
                    .load(generateAccountListResponseModel.getData().get(position).getBank_logo())
                    .into(holder.logo);
            id=generateAccountListResponseModel.getData().get(position).getId();
            this.position=position;
        }


        else {
            balancehome.noaccount.setVisibility(View.VISIBLE);
            balancehome.recyclerView.setVisibility(View.GONE);
        }

    }
    @Override
    public int getItemCount() {
        return generateAccountListResponseModel.getData().size();
    }

    public class mine extends RecyclerView.ViewHolder{
        CardView cards;
        ConstraintLayout constraintLayout;
        TextView balance,AvailableBalance;
        ImageView logo;
        public mine(@NonNull View itemView) {
            super(itemView);
            cards=itemView.findViewById(R.id.cards);
            constraintLayout=itemView.findViewById(R.id.constraintlays);
            balance=itemView.findViewById(R.id.balance);
            AvailableBalance=itemView.findViewById(R.id.available);
            logo=itemView.findViewById(R.id.logoinaccountlist);
            cards.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    interfaceForBalanceHome.OpenCard(position,id);
                }
            });
        }
    }
}
