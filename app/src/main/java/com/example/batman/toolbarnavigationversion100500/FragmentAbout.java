package com.example.batman.toolbarnavigationversion100500;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAbout extends Fragment {

TextView text_about;

    public FragmentAbout() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.fragment_about, container, false);
        Log.d("CONSTANTAAAAAAAAAAH",MainActivity.USER_LOGIN);
        text_about = (TextView) v.findViewById(R.id.about_text);
        return v;
    }

}
