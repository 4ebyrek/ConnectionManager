package com.example.batman.toolbarnavigationversion100500;




import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;


import android.support.v4.app.DialogFragment;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


public class Fragment_History extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    String[] from = new String[]{DbHelper.History_Login,DbHelper.History_Match,DbHelper.History_Bet};
    int[] to = new int[]{R.id.login_history,R.id.match_history,R.id.bet_history};
    ListView lv_history;
    Cursor cursor;
    SimpleCursorAdapter smpl_cursor_adapter;
    DbHelper mDbHelper;
    //SQLiteDatabase sdb = mDbHelper.getReadableDatabase();

    public Fragment_History() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(MainActivity.USER_LOGIN == "") {
            DialogFragment frg_signin = new Fragment_SIgn();
            frg_signin.show(getFragmentManager(), "frg_signin");
        }
        mDbHelper = new DbHelper(getActivity(),"myDataBase.db",null,1);
        smpl_cursor_adapter = new SimpleCursorAdapter(getActivity(),R.layout.for_history,null,from,to,0);
        //lv_history = (ListView) v.findViewById(R.id.lv_history);
        setListAdapter(smpl_cursor_adapter);
       // MainActivity.USER_LOGIN = "Eldar";
        getLoaderManager().initLoader(0, null,this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =inflater.inflate(R.layout.fragment__history, container, false);


        return v;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d("ebalashkaEldar", "onCreate");
        return new MyCursorLoader(getActivity(),mDbHelper);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        smpl_cursor_adapter.swapCursor(data);
        Log.d("ebalashkaEldar", "onLoadFinished");
    }

    @Override

    public void onLoaderReset(Loader<Cursor> loader) {
        Log.d("ebalashkaEldar", "onLoadReset");
        smpl_cursor_adapter.swapCursor(null);
    }


    public static class MyCursorLoader extends CursorLoader {

        DbHelper db;

        public MyCursorLoader(Context context,DbHelper db) {
            super(context);
            this.db =db;
        }

        @Override
        public Cursor loadInBackground() {
            String selection ="Login LIKE ?";
            String[] selectionArgs = new String[]{MainActivity.USER_LOGIN};
            Cursor cursor = db.getWritableDatabase().query("History", null, selection, selectionArgs, null, null, null);
            if(cursor != null && cursor.getCount()>0) {
                while (cursor.moveToNext()) {
                    Log.d("##########", cursor.getString(cursor.getColumnIndex(DbHelper.History_Login)));
                    Log.d("##########", cursor.getString(cursor.getColumnIndex(DbHelper.History_Match)));
                    Log.d("##########", cursor.getString(cursor.getColumnIndex(DbHelper.History_Bet)));

                }
            }
            return cursor;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mDbHelper.close();
    }
}

