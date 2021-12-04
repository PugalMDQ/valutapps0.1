package com.vibro.care.Activity.fragment.common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vibro.care.R;

public class ShopFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_shop, container, false);

        FloatingActionButton cart = root.findViewById(R.id.cart);
        cart.setOnClickListener(v -> {
            //startActivity(new Intent(getActivity(), CreateJobActivity.class));
            Toast.makeText(getActivity(), "Cart items", Toast.LENGTH_SHORT).show();
        });

        return root;
    }
}