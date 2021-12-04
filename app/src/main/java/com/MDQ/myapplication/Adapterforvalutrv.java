package com.MDQ.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.MDQ.myapplication.interfaces.InterfaceForValut;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateVaultListResponseModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Adapterforvalutrv extends RecyclerView.Adapter<Adapterforvalutrv.mine> {

    Context context;
    InterfaceForValut interfaceForValut;
    GenerateVaultListResponseModel generateVaultListResponseModel;
    List<Integer> colorcodes=new ArrayList<>();

    Adapterforvalutrv(Context context, InterfaceForValut interfaceForValut,GenerateVaultListResponseModel generateVaultListResponseModel){
        this.interfaceForValut=interfaceForValut;
        this.generateVaultListResponseModel=generateVaultListResponseModel;
        this.context=context;
    }

    @NonNull
    @Override
    public mine onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutforvalutadapter,parent,false);
        return new mine(view);

    }

    @Override
    public void onBindViewHolder(@NonNull mine holder, int position) {

        getcolorcode();
        holder.detailname.setText(generateVaultListResponseModel.data.get(position).getName());
        holder.detailname.setTextColor(context.getResources().getColor(colorcodes.get(position)));
        holder.dots.setTextColor(context.getResources().getColor(colorcodes.get(position)));

    }
    @Override
    public int getItemCount() {
        return generateVaultListResponseModel.data.size();
    }

    private int getcolorcode() {
        colorcodes.add(R.color.color1);
        colorcodes.add(R.color.color2);
        colorcodes.add(R.color.color3);
        colorcodes.add(R.color.color4);
        colorcodes.add(R.color.color5);
        colorcodes.add(R.color.color6);
        Random random=new Random();
        int num=random.nextInt(colorcodes.size());
        return colorcodes.get(num);
    }
    public class mine extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView detailname,dots,secondName;
        vaultMain vm;
        public mine(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.cardsindown);
            dots=itemView.findViewById(R.id.dots);
            secondName=itemView.findViewById(R.id.secondName);
            vm=new vaultMain();
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name=detailname.getText().toString();
                    interfaceForValut.valut(name,getAdapterPosition());
                }
            });
            detailname=itemView.findViewById(R.id.detailName);
        }

        }
}
