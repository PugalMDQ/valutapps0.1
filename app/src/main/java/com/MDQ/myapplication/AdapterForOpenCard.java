package com.MDQ.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.MDQ.myapplication.pojo.jsonrequest.GenerateListTransactionRequestModel;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateListTransactionResponseModel;
import com.bumptech.glide.Glide;

public class AdapterForOpenCard extends RecyclerView.Adapter<AdapterForOpenCard.mine> {

    Context context;
    GenerateListTransactionResponseModel generateListTransactionResponseModel;
    AdapterForOpenCard(Context context, GenerateListTransactionResponseModel generateListTransactionResponseModel) {
        this.context=context;
        this.generateListTransactionResponseModel=generateListTransactionResponseModel;
    }
    @NonNull
    @Override
    public mine onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutforopencardadapter,parent,false);

        return new mine(view);
    }

    @Override
    public void onBindViewHolder(@NonNull mine holder, int position) {

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().startActivity(new Intent(context,Edittransaction.class));
            }
        });

        Glide.with(context)
                .load(generateListTransactionResponseModel.getData().get(position).getImage())
                .into(holder.logos);
        holder.category.setText(generateListTransactionResponseModel.getData().get(position).getCategory());
        holder.secondCategory.setText(generateListTransactionResponseModel.getData().get(position).getSubcategory());
        holder.amounts.setText(generateListTransactionResponseModel.getData().get(position).getAmount());
        if(generateListTransactionResponseModel.getData().get(position).getType()=="credit"){
            holder.amounts.setTextColor(context.getResources().getColor(R.color.teal_700));
        }

    }

    @Override
    public int getItemCount() {
        return generateListTransactionResponseModel.getData().size();
    }

    public class mine extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        TextView category,secondCategory,amounts;
        ImageView logos;

        public mine(@NonNull View itemView) {
            super(itemView);
            linearLayout=itemView.findViewById(R.id.top);
            logos=itemView.findViewById(R.id.logos);
            category=itemView.findViewById(R.id.nameofincomeanddebit);
            secondCategory=itemView.findViewById(R.id.secondName);
            amounts=itemView.findViewById(R.id.amounts);
        }
    }
}
