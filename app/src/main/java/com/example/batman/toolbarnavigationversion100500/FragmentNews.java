package com.example.batman.toolbarnavigationversion100500;


import android.app.ListFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentNews extends ListFragment {
    private ArrayList<String> pereda4a = new ArrayList<String>();
    private ArrayAdapter<String> apater_frg_news;
    public FragmentNews() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        pereda4a = bundle.getStringArrayList("asd");
//        if(pereda4a ==null){
//            Log.d("@@@@@@@@@@@@@@@@@@@@","deeee");
//        }else{
//            Log.d("@@@@@@@@@@@@@@@@@@@@","Its working");
//            for(int i =0;i<pereda4a.size();i++){
//                Log.d("&&&&&&&&&&&&",pereda4a.get(i));
//            }
//        }
        apater_frg_news = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,pereda4a);
        setListAdapter(apater_frg_news);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

}
