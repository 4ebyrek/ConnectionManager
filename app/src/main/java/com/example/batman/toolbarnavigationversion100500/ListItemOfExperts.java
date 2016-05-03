package com.example.batman.toolbarnavigationversion100500;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListItemOfExperts extends Fragment {

    String text_experts;
    TextView txt;


    public ListItemOfExperts() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.list_item_of_experts, container, false);
        text_experts = this.getArguments().getString("expert");
        txt = (TextView) v.findViewById(R.id.expert_text);
        txt.setText(text_experts);
        return v;
    }

}
