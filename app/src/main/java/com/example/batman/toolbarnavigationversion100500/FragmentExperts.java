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
import android.widget.AdapterView;
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
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentExperts extends Fragment {

//    ArrayList<String> match_of_experts = new ArrayList<String>();
//    String text_of_experts;
//    ArrayAdapter<String> adapter;
//    Map<String,String> match_text = new HashMap<String,String>();
//
//    Elements content,cont;
//    Document document,doc;
//
//    String match_text_key;
//    String href;
//
//    int i = 0;
//
    Fragment fragment;
   private FragmentTransaction fragmentTransaction;

    ListView listik;
    TextView text;

    public FragmentExperts() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,match_of_experts);
//        if( match_of_experts.isEmpty())
//            new JsoupExperts().execute();
        View v = inflater.inflate(R.layout.fragment_experts, container, false);
        listik = (ListView) v.findViewById(R.id.listik_experts);
        ParseTitle_experts parseTitle_experts = new ParseTitle_experts();
        parseTitle_experts.execute();
        try {
            final HashMap<String,String> hashMap = parseTitle_experts.get();
            final ArrayList<String> arraylist = new ArrayList<>();
            for(Map.Entry entry : hashMap.entrySet()){
                arraylist.add(entry.getKey().toString());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,arraylist);
            listik.setAdapter(adapter);
            listik.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ParseText_experts parseText_experts = new ParseText_experts();
                    parseText_experts.execute(hashMap.get(arraylist.get(position)));
                    fragment = new ListItemOfExperts();
                    Bundle args3345 = new Bundle();
                    try {
                        args3345.putString("expert",parseText_experts.get());

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

        fragment.setArguments(args3345);
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_holder,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return v;
    }
//    public class JsoupExperts extends AsyncTask<Void,Void,ArrayList<String>>{
//
//        @Override
//        protected ArrayList<String> doInBackground(Void... params) {
//            match_of_experts.clear();
//            try {
//                document = Jsoup.connect("http://stavochka.com/forecasts/").get();
//                content = document.select(".b-forecasts__th a");
//                for(Element element:content){
//                    match_text_key = element.text();
//                    Log.d("###############", match_text_key);
//                    match_of_experts.add(match_text_key);
//                    href = "http://stavochka.com"+element.attr("href");
//                    Log.d("$$$$$$$$$$$",href +  i);
//                    doc = Jsoup.connect(href).get();
//                    cont = doc.select(".b-forecast-text");
//                    for(Element element2:cont){
//                        text_of_experts = element2.text();
//                    }
//                    match_text.put(match_text_key,text_of_experts);
//
//                    i++;
//                    if(i==20){break;}
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(ArrayList<String> strings) {
//            super.onPostExecute(strings);
//            setListAdapter(adapter);
//        }
//    }

//    @Override
//    public void onListItemClick(ListView l, View v, int position, long id) {
//        super.onListItemClick(l, v, position, id);
//        TextView txtview=(TextView)v;
//        String asd=txtview.getText().toString();
//        fragment = new ListItemOfExperts();
//
//        Bundle bundle = new Bundle();
//        bundle.putString("expert",match_text.get(asd));
//        fragment.setArguments(bundle);
//        fragmentTransaction = getFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.fragment_holder,fragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
//    }
class ParseTitle_experts extends AsyncTask<Void,Void,HashMap<String,String>>{

    @Override
    protected HashMap<String, String> doInBackground(Void... params) {
        HashMap<String,String> hashMap = new HashMap<>();
        try {
            Document document = Jsoup.connect("http://stavochka.com/forecasts/").get();
            Elements elements = document.select(".b-forecasts__th a");
            for(Element element :elements){
                Element element1 = element.select("a[href]").first();
                hashMap.put(element.text(),element1.attr("abs:href"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hashMap;
    }
}

    class ParseText_experts extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {
            String str = " ";
            try {
                Document document = Jsoup.connect(params[0]).get();
                Element elements = document.select(".b-forecast-text").first();
                str = elements.text();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return str;
        }
    }

}
