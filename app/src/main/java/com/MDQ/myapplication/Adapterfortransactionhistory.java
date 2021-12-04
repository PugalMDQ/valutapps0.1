package com.MDQ.myapplication;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Adapterfortransactionhistory extends RecyclerView.Adapter<Adapterfortransactionhistory.mine> {
    @NonNull
    @Override
    public mine onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull mine holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class mine extends RecyclerView.ViewHolder {
        public mine(@NonNull View itemView) {
            super(itemView);
        }
    }
}
