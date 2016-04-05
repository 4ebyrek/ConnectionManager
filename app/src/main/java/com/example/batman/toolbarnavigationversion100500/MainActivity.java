package com.example.batman.toolbarnavigationversion100500;

import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private String mActivityTitle;

    private ListView listView_navigation_drawer;
    private String[] items = {"News","Bets","Experts","About Us"};
    private ArrayAdapter<String> adapter;
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


        setupDrawer();


    }
    private void setupDrawer(){
        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.drawer_open,R.string.drawer_close){
          public void onDrawerOpened(View drawerView){
              super.onDrawerOpened(drawerView);
              getSupportActionBar().setTitle("Navigation");
              invalidateOptionsMenu();
          }
          public void onDrawerClosed(View drawerView){
              super.onDrawerClosed(drawerView);
              getSupportActionBar().setTitle("mActivityTitle");
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
}
