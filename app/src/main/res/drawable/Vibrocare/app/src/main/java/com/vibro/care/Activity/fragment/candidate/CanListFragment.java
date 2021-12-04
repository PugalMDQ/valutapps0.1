package com.vibro.care.Activity.fragment.candidate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.vibro.care.Config.Methods;
import com.vibro.care.R;

import static com.vibro.care.Activity.CandidateActivity.pDialog;
import static com.vibro.care.Activity.CandidateActivity.pref;
import static com.vibro.care.Activity.CandidateActivity.uploading;
import static com.vibro.care.Config.Config.accessid;
import static com.vibro.care.Config.Config.company_type;

public class CanListFragment extends Fragment {

    LinearLayout can_list;
    TextView can_list_text;
    LayoutInflater inflater;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_can_list, container, false);

        can_list = root.findViewById(R.id.can_list);
        can_list_text = root.findViewById(R.id.can_list_text);
        this.inflater = inflater;

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!uploading){
            can_list.removeAllViews();
            can_list_text.setVisibility(View.GONE);
            Methods.getData(pref.getResponse(company_type), pref.getResponse(accessid), "0", inflater, getActivity(), pDialog, can_list, can_list_text);
        }
    }
}
