package com.example.batman.toolbarnavigationversion100500;



import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class Dialog_ListOfMatches extends DialogFragment implements OnClickListener{
    String stavka;
    String mat4;
    String login;

    Bundle bundle = new Bundle();
    ArrayList<String> grev = new ArrayList<String>();
    Button button_p1;
    Button button_x;
    Button button_p2;
    TextView txt;

    public Dialog_ListOfMatches() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        grev = this.getArguments().getStringArrayList("asd");
        for(String s:grev){
            Log.d("LOOOOOGTAAAG",s);
        }

        getDialog().setTitle("BET");

        View v =inflater.inflate(R.layout.dialog__list_of_matches,container,false);

        v.findViewById(R.id.btn_p1).setOnClickListener(this);
        v.findViewById(R.id.btn_x).setOnClickListener(this);
        v.findViewById(R.id.btn_p2).setOnClickListener(this);

        button_p1  = (Button) v.findViewById(R.id.btn_p1);
        button_x  = (Button) v.findViewById(R.id.btn_x);
        button_p2  = (Button) v.findViewById(R.id.btn_p2);
        txt = (TextView) v.findViewById(R.id.txt);

        txt.setText(grev.get(0));
        button_p1.setText(grev.get(1));
        button_x.setText(grev.get(2));
        button_p2.setText(grev.get(3));
        return v;
    }



    @Override
    public void onClick(View v) {
        DbHelper mDbHelper = new DbHelper(getActivity(),"myDataBase.db",null,1);
        SQLiteDatabase sdb = mDbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        mat4 = txt.getText().toString();
        stavka = ((Button)v).getText().toString();

        cv.put(DbHelper.History_Login,MainActivity.USER_LOGIN);
        cv.put(DbHelper.History_Match,mat4);
        cv.put(DbHelper.History_Bet,stavka);

        sdb.insert("History",null,cv);
        Log.d(")))))))))))","Match =  "+ mat4 + ", stavka = "+stavka);
        Log.d("LOG_TAG", "Dialog : " + ((Button) v).getText());
        dismiss();
    }
}
