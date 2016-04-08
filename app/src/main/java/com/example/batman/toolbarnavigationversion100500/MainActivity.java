package com.example.batman.toolbarnavigationversion100500;

import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.app.Fragment;
import android.os.AsyncTask;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Elements content,cont;
    private Document document,doc;

    String urls;
    Fragment fragment = null;

    private FragmentTransaction fragmentTransaction;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private String mActivityTitle;

    private ListView listView_navigation_drawer;
    private String[] items = {"News","Bets","Experts","About Us"};
    private ArrayAdapter<String> adapter;
    private ArrayList<String> news_item_cont = new ArrayList<String>();
    private ArrayList<String> news_short_text = new ArrayList<String>();


    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        mActivityTitle = getTitle().toString();


        listView_navigation_drawer = (ListView) findViewById(R.id.navigationList);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
        listView_navigation_drawer.setAdapter(adapter);
        listView_navigation_drawer.setOnItemClickListener(new DrawerItemClickListener());


        setupDrawer();
        new JsoupThread().execute();

    }
    private void setupDrawer(){
        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.drawer_open,R.string.drawer_close){
          public void onDrawerOpened(View drawerView){
              super.onDrawerOpened(drawerView);
              getSupportActionBar().setTitle("Menu");
              invalidateOptionsMenu();
          }
          public void onDrawerClosed(View drawerView){
              super.onDrawerClosed(drawerView);
              getSupportActionBar().setTitle("SCRIPTONIT");
              invalidateOptionsMenu();
          }
        };
        toggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(toggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }
    private void selectItem(int position){

        switch (position){
            case 0 :
                Bundle args = new Bundle();
                fragment = new FragmentNews();
                args.putStringArrayList("asd", news_short_text);
                fragment.setArguments(args);

                break;
            case 1 :
                fragment = new FragmentBets();
                break;
            case 2 :
                fragment = new FragmentExperts();
                break;
            case 3 :
                fragment = new FragmentAbout();
                break;
            default:
                break;
        }
        if(fragment != null) {

            fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_holder, fragment);
            fragmentTransaction.commit();

        }else {
            // Error
            Log.e(this.getClass().getName(), "Error. Fragment is not created");
        }
        listView_navigation_drawer.setItemChecked(position, true);
        drawerLayout.closeDrawer(listView_navigation_drawer);
    }


    public class JsoupThread extends AsyncTask<String,Void,ArrayList<String>>{

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

            if(news_short_text != null){
                for(int i =0;i<news_short_text.size();i++) {
                    Log.d("****************", news_short_text.get(i));
                }
            }else{
                Log.d("****************","tam 4e to est;");
            }

        }
    }

}
