package com.example.batman.toolbarnavigationversion100500;


import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentExperts extends ListFragment {
    ArrayList<String> match_of_experts = new ArrayList<String>();
    String text_of_experts;
    ArrayAdapter<String> adapter;
    Map<String,String> match_text = new HashMap<String,String>();

    Elements content,cont;
    Document document,doc;

    String match_text_key;
    String href;

    int i = 0;

    Fragment fragment;
    private FragmentTransaction fragmentTransaction;

    public FragmentExperts() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,match_of_experts);
        if( match_of_experts.isEmpty())
            new JsoupExperts().execute();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_experts, container, false);
    }

    public class JsoupExperts extends AsyncTask<Void,Void,ArrayList<String>>{

        @Override
        protected ArrayList<String> doInBackground(Void... params) {
            match_of_experts.clear();
            try {
                document = Jsoup.connect("http://stavochka.com/forecasts/").get();
                content = document.select(".b-forecasts__th a");
                for(Element element:content){
                    match_text_key = element.text();
                    Log.d("###############", match_text_key);
                    match_of_experts.add(match_text_key);
                    href = "http://stavochka.com"+element.attr("href");
                    Log.d("$$$$$$$$$$$",href +  i);
                    doc = Jsoup.connect(href).get();
                    cont = doc.select(".b-forecast-text");
                    for(Element element2:cont){
                        text_of_experts = element2.text();
                    }
                    match_text.put(match_text_key,text_of_experts);

                    i++;
                    if(i==20){break;}
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            super.onPostExecute(strings);
            setListAdapter(adapter);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        TextView txtview=(TextView)v;
        String asd=txtview.getText().toString();
        fragment = new ListItemOfExperts();

        Bundle bundle = new Bundle();
        bundle.putString("expert",match_text.get(asd));
        fragment.setArguments(bundle);
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_holder,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


}
