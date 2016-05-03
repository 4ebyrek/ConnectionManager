package com.example.batman.toolbarnavigationversion100500;



import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Fragment_SIgn extends DialogFragment implements OnClickListener {

    EditText signPassword, signLogin;
    String str_signPassword,str_signLogin;
    Button signIN, registration;
    Cursor cursor = null;

    public Fragment_SIgn() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_sign_in, container, false);
        signLogin = (EditText) v.findViewById(R.id.login);


        signPassword = (EditText) v.findViewById(R.id.password);


        signIN = (Button) v.findViewById(R.id.SignIn);
        v.findViewById(R.id.SignIn).setOnClickListener(this);

        registration = (Button) v.findViewById(R.id.Registration);
        v.findViewById(R.id.Registration).setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == signIN.getId()){
            DbHelper mDbHelper = new DbHelper(getActivity(),"myDataBase.db",null,1);
            SQLiteDatabase sdb = mDbHelper.getWritableDatabase();
            str_signLogin = signLogin.getText().toString();
            str_signPassword = signPassword.getText().toString();

            String selection ="Login LIKE ? AND Password LIKE ?";
            String[] selectionArgs = new String[]{str_signLogin,str_signPassword};

            cursor = sdb.query("User", null, selection, selectionArgs, null, null, null);

            if(cursor != null && cursor.getCount()>0){
                cursor.moveToFirst();
//                String user_login = cursor.getString(cursor.getColumnIndex(DbHelper.User_Login));
//                String user_password = cursor.getString(cursor.getColumnIndex(DbHelper.User_Password));
//                Log.d("###########", "User_login =" + user_login + "User_password =" + user_password);
                MainActivity.USER_LOGIN = str_signLogin;
                MainActivity.USER_PASSWORD = str_signPassword;
                Log.d("****Cursor****",""+cursor.getCount());
                Log.d("****USER_LOGIN",MainActivity.USER_LOGIN);
                Log.d("USER_PASSWORD",MainActivity.USER_PASSWORD);
                cursor.close();
                sdb.close();
                dismiss();

            }else{
                Log.d("****Cursor****",""+cursor.getCount());
                cursor.close();
                sdb.close();
                Toast.makeText(getActivity(),"Неверныйй Login или Password",Toast.LENGTH_SHORT);}
        }else{
            DialogFragment dlg_reg = new Fragment_Registration();
            dlg_reg.show(getFragmentManager(),"dlg_reg");
            Log.d("onclick srabotal", "Dialog: OnCLick");
            dismiss();}
        }


}
