package com.example.batman.toolbarnavigationversion100500;





import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class FragmentBets extends ListFragment {

   // String[] sports = {"Footbal","Tennis","Basketbal","Hokey","Voleyball"};
    ArrayAdapter<String> adapter;


    Elements content,cont,con;
    Document document,docum,doc;


    ArrayList<String> leagues_matches_value = new ArrayList<String>();


    ArrayList<String> footbal_leagues = new ArrayList<String>();
    ArrayList<String> footbal_matches = new ArrayList<String>();
    ArrayList<String> footbal_cofficient = new ArrayList<String>();
    ArrayList<String> footbal_promejuto4nii = new ArrayList<String>();
    Map<String,ArrayList<String>> league_mathces = new HashMap<String,ArrayList<String>>();
    Map<String,ArrayList<String>> matches_cofficient = new HashMap<String,ArrayList<String>>();

    private ListOfMatches fragment_list_of_matches;
    private FragmentTransaction fragmentTransaction;



    public FragmentBets() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,footbal_leagues);
        if(footbal_leagues.isEmpty())
        new JsoupBets().execute();



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bets, container, false);
    }

    public class JsoupBets extends AsyncTask<Void,Void,ArrayList<String>>{

        @Override
        protected ArrayList<String> doInBackground(Void... params) {
            try {
                footbal_leagues.clear();
                footbal_matches.clear();
                footbal_cofficient.clear();
                footbal_promejuto4nii.clear();
                leagues_matches_value.clear();
                int i =0;
                int b=0;
                int c=0;
                String url;
                String url2;
                document = Jsoup.connect("http://olimp.kz/mobile/index.php?page=line&action=1&time=0&line_nums=0&sel[]=1").get();
                content = document.select(".row");
                for(Element element:content) {


                    String leagues_matches_key = element.text();


                    footbal_leagues.add(element.text());
                    url = "http://olimp.kz/mobile/"+element.attr("href");
                    docum = Jsoup.connect(url).get();
                    cont = docum.select(".row");
                    for(Element element1:cont){
                        leagues_matches_value.add(element1.text());

                       // footbal_promejuto4nii.add(element1.text());
                        String matches_cofficient_key = element1.text();

                        //Log.d("footbal_matches", element1.text());
                        url2 = "http://olimp.kz/mobile/"+element1.attr("href");
                        doc = Jsoup.connect(url2).get();
                        con = doc.select(".coefficient");
                        b=0;
                        for(Element element2:con){
                            footbal_cofficient.add(element2.text());
                            b++;
                            if(b==3) break;
//                            Log.d("footbal_cofficient",element2.text());
//                            Log.d("provero4_kakefov",""+b);
                        }

                        matches_cofficient.put(matches_cofficient_key,new ArrayList<String>(footbal_cofficient));
                        footbal_cofficient.clear();
                        matches_cofficient_key = "";
                    }

                    league_mathces.put(leagues_matches_key, new ArrayList<String>(leagues_matches_value));
    //String league = league_mathces.getKey(leagues_matches_key);
                   // ArrayList<String> prova = league_mathces.get(leagues_matches_key);
                    leagues_matches_value.clear();
                    leagues_matches_key = "";
    // Log.d("@@@@@@@",url);
                   // Log.d("footbal_leagues",element.text());
                    i++;
                  if(i==8)break;
                }
//                for (Map.Entry entry: league_mathces.entrySet()) {
//                    String key = (String) entry.getKey();
//                    Log.d("ЛИГААААААААААААА", key);
//                    ArrayList<String> value = (ArrayList<String>) entry.getValue();
//                    for(String s:value){
//                        Log.d("МАТЧИИИИИИИИИИИИ",s);
//                    }
//                }



                DbHelper mDbHelper = new DbHelper(getActivity(),"myDataBase.db",null,1);
                SQLiteDatabase sdb = mDbHelper.getWritableDatabase();
                sdb.delete("Bets", null, null);

                ContentValues cv = new ContentValues();
                for(String key :matches_cofficient.keySet()){
                    cv.put(DbHelper.Bets_match,key);
                    ArrayList<String> values = matches_cofficient.get(key);
                        cv.put(DbHelper.Bets_P1, values.get(0));
                        cv.put(DbHelper.Bets_X, values.get(1));
                    if(values.size() == 3) {
                        cv.put(DbHelper.Bets_P2, values.get(2));
                    }
                    sdb.insert("Bets", null, cv);
                }
                sdb.close();

                for (Map.Entry entry: matches_cofficient.entrySet()) {
                    String key = (String) entry.getKey();
                    Log.d("MAAATCH", key);
                    ArrayList<String> value = (ArrayList<String>) entry.getValue();
                    for(String s:value){
                        Log.d("CCOFFICIENT",s);
                    }
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

        if ( MainActivity.USER_LOGIN == "" ) {
            DialogFragment frg_signin = new Fragment_SIgn();
            frg_signin.show(getFragmentManager(),"frg_signin");
        }

        TextView txtview=(TextView)v;
        String asd=txtview.getText().toString();
        Toast.makeText(getActivity(),asd,Toast.LENGTH_SHORT ).show();
//        ArrayList<String> noname = new ArrayList<String>();
//        noname=league_mathces.get(asd);
//        for(String s:noname){
//            Log.d("%%%%%%%%%%%%%%%",s);
//        }

        fragment_list_of_matches = new ListOfMatches();
        Bundle args22 = new Bundle();


        args22.putStringArrayList("mat4i",league_mathces.get(asd));


        fragment_list_of_matches.setArguments(args22);


        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_holder, fragment_list_of_matches);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
}
