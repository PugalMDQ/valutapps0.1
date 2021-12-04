package com.MDQ.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterFornamelistinedit extends RecyclerView.Adapter<AdapterFornamelistinedit.mine> {
    @NonNull
    @Override
    public mine onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutfornamelist,parent,false);

        return new mine(view);
    }

    @Override
    public void onBindViewHolder(@NonNull mine holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 22;
    }

    public class mine extends RecyclerView.ViewHolder {
        public mine(@NonNull View itemView) {
            super(itemView);
        }
    }
}
