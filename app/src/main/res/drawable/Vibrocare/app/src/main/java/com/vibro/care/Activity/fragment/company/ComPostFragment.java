package com.vibro.care.Activity.fragment.company;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vibro.care.Activity.CreateJobActivity;
import com.vibro.care.Config.Methods;
import com.vibro.care.R;

import static com.vibro.care.Activity.CompanyActivity.pDialog;
import static com.vibro.care.Activity.CompanyActivity.pref;
import static com.vibro.care.Activity.CompanyActivity.uploading;
import static com.vibro.care.Config.Config.accessid;
import static com.vibro.care.Config.Config.company_type;

public class ComPostFragment extends Fragment {

    LinearLayout com_post;
    TextView com_post_text;
    LayoutInflater inflater;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_com_post, container, false);

        com_post = root.findViewById(R.id.com_post);
        com_post_text = root.findViewById(R.id.com_post_text);
        this.inflater = inflater;

        FloatingActionButton addPost = root.findViewById(R.id.addPost);
        addPost.setOnClickListener(v -> startActivity(new Intent(getActivity(), CreateJobActivity.class)));

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!uploading){
            com_post.removeAllViews();
            com_post_text.setVisibility(View.GONE);
            Methods.getData(pref.getResponse(company_type), pref.getResponse(accessid), "0", inflater, getActivity(), pDialog, com_post, com_post_text);
        }
    }
}
