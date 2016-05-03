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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentNews extends ListFragment {
    private ArrayList<String> pereda4a = new ArrayList<String>();
    private ArrayAdapter<String> apater_frg_news;



    Fragment fragment_news_item=null;

    private Elements content,cont;
    private Document document,doc;
    String urls;
    private ArrayList<String> news_item_cont = new ArrayList<String>();
    private ArrayList<String> news_short_text = new ArrayList<String>();
    private FragmentTransaction fragmentTransaction;


    public FragmentNews() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        if(pereda4a ==null){
//            Log.d("@@@@@@@@@@@@@@@@@@@@","deeee");
//        }else{
//            Log.d("@@@@@@@@@@@@@@@@@@@@","Its working");
//            for(int i =0;i<pereda4a.size();i++){
//                Log.d("&&&&&&&&&&&&",pereda4a.get(i));
//            }
//        }
        apater_frg_news = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,news_short_text);

        if(news_short_text.isEmpty()){
            Log.d("PROVERKAAAAAAA", "pustoi");
                new JsoupThread().execute();
            }else{Log.d("PROVERKAAAAAAA", "ne pustoi");
            for(int i =0;i<news_short_text.size();i++){
                    Log.d("nixyase",news_short_text.get(i));
                }
            }


    }
    public class JsoupThread extends AsyncTask<String,Void,ArrayList<String>> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<String> doInBackground(String... params) {

            try {
                document = Jsoup.connect("http://www.sports.ru/topnews/").get();
                content = document.select(".short-text");
                news_short_text.clear();
                news_item_cont.clear();


                for(Element element:content){
                    urls = "http://www.sports.ru"+element.attr("href");
                    doc = Jsoup.connect(urls).get();
                    cont = doc.select(".news-item__content");
                    for(Element elementi :cont){
                        news_item_cont.add(elementi.text());
                    }
                    news_short_text.add(element.text());



                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<String> s) {
            apater_frg_news.notifyDataSetChanged();
            setListAdapter(apater_frg_news);
            if(news_short_text != null){
                for(int i =0;i<news_short_text.size();i++) {
                    Log.d("****************", news_short_text.get(i));
                }
            }else{
                Log.d("****************","tam 4e to est;");
            }

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }



    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        fragment_news_item= new ListItemOfNews();
        Bundle args11 = new Bundle();
        //String item = this.getArguments().getString("Item_of_news");
        args11.putString("item_of_news",news_item_cont.get(position));
        fragment_news_item.setArguments(args11);

        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_holder, fragment_news_item);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
}
