package com.example.batman.toolbarnavigationversion100500;





import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListOfMatches extends ListFragment {
    ArrayList<String> mat4i=new ArrayList<String>();
   // ArrayList<String> mat4i2=new ArrayList<String>();
    ArrayAdapter<String> adapter;
    ArrayList<String> isxod = new ArrayList<String>();


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mat4i = this.getArguments().getStringArrayList("mat4i");
       // mat4i2 = this.getArguments().getStringArrayList("ttt");

        for(String s:mat4i){
            Log.d("%%%%%%%%%%%%%%%",s);
        }
//        for(String s:mat4i2){
//            Log.d("-----------",s);
//        }

        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,mat4i);
        setListAdapter(adapter);

    }

    public ListOfMatches() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_list_of_matches, container, false);
        return v;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        TextView txt = (TextView)v;
        String item_list=txt.getText().toString();
        //Log.d("++++++++++++",item_list);

        DbHelper mDbHelper = new DbHelper(getActivity(),"myDataBase.db",null,1);
        SQLiteDatabase sdb = mDbHelper.getWritableDatabase();

        String selection ="Match LIKE ?";
        String[] selectionArgs = new String[]{item_list};

        Cursor cursor = sdb.query("Bets", null, selection, selectionArgs, null, null, null);

        cursor.moveToFirst();
        String Match = cursor.getString(cursor.getColumnIndex(DbHelper.Bets_match));
        String p1 = cursor.getString(cursor.getColumnIndex(DbHelper.Bets_P1));
        String x = cursor.getString(cursor.getColumnIndex(DbHelper.Bets_X));
        String p2 = cursor.getString(cursor.getColumnIndex(DbHelper.Bets_P2));


        Log.d("DAAAAAAVAAAAAIII JEEEEE", Match + " " + p1 + " " + x + " " + p2);
        cursor.close();
        sdb.close();

        isxod.add(Match);
        isxod.add(p1);
        isxod.add(x);
        isxod.add(p2);

        DialogFragment dlg = new Dialog_ListOfMatches();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("asd", new ArrayList<String>(isxod));
        dlg.setArguments(bundle);
        dlg.show(getFragmentManager(), "dlg");
        isxod.clear();

    }
}
