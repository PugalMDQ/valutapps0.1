package com.MDQ.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterForRvInContact extends RecyclerView.Adapter<AdapterForRvInContact.mine> {

    List<String> name;
    AdapterForRvInContact(List<String> name){

        this.name=name;

    }

    @NonNull
    @Override
    public mine onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutfornamelist,parent,false);

        return new mine(view);
    }

    @Override
    public void onBindViewHolder(@NonNull mine holder, int position) {
        holder.textView.setText(name.get(position));

    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    public class mine extends RecyclerView.ViewHolder {
        TextView textView;
        public mine(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.ContactName);
        }
    }
}
