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
public class ListItemOfNews extends Fragment {



    public ListItemOfNews() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_item_of_news, container, false);
        String item_of_news = this.getArguments().getString("item_of_news");
        Log.d("@@@@@@@@@@@@@@@", item_of_news);
        TextView item_txt = (TextView)v.findViewById(R.id.item_txt);
        item_txt.setText(item_of_news);

       return  v;
    }

}
