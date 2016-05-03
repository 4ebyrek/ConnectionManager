package com.example.batman.toolbarnavigationversion100500;



import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.support.v4.app.DialogFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;



public class Fragment_Registration extends DialogFragment implements View.OnClickListener {
    EditText Login;
    EditText Password;
    EditText Email;

    public Fragment_Registration() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment__registration, container, false);

        getDialog().setTitle("Registration");
        v.findViewById(R.id.go).setOnClickListener(this);
        Login = (EditText) v.findViewById(R.id.login_reg);
        Password = (EditText) v.findViewById(R.id.password_reg);
        Email = (EditText) v.findViewById(R.id.email);
        return v;
    }

    @Override
    public void onClick(View v) {

        Log.d("555555555","Login = "+ Login.getText().toString() + "Password = " +Password.getText().toString() +"Email = "+Email.getText().toString());

        MainActivity.USER_LOGIN = Login.getText().toString();
        DbHelper mDbHelper = new DbHelper(getActivity(),"myDataBase.db",null,1);
        SQLiteDatabase sdb = mDbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DbHelper.User_Login,Login.getText().toString());
        cv.put(DbHelper.User_Password,Password.getText().toString());
        cv.put(DbHelper.User_Email,Email.getText().toString());
        sdb.insert("User", null, cv);
        sdb.close();
        dismiss();
    }
}
